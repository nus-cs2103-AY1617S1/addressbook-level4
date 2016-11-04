package harmony.mastermind.logic.commands;

import java.util.ArrayList;
import java.util.Date;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.exceptions.NotRecurringTaskException;
import harmony.mastermind.commons.exceptions.TaskAlreadyMarkedException;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;
import javafx.collections.ObservableList;

//@@author A0124797R
/**
 * marks a task as complete and moves it to the archives tab
 */
public class MarkCommand extends Command implements Undoable, Redoable {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": mark a task as done "
                                               + "Parameters: INDEX (must be a positive integer)\n"
                                               + "Example: "
                                               + COMMAND_WORD
                                               + " 1";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " INDEX";
    public static final String COMMAND_DESCRIPTION = "Marking a task as done";

    public static final String MESSAGE_MARK_SUCCESS = "%1$s has been archived";
    public static final String MESSAGE_MARK_DUE_SUCCESS = "All Tasks that are due has been archived";
    public static final String MESSAGE_MARK_FAILURE = "Selected is already marked";
    public static final String MESSAGE_MARK_RECURRING_FAILURE = "Unable to add recurring Task";
    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Mark Command] %1$s has been unmarked";
    public static final String MESSAGE_REDO_SUCCESS = "[Redo Mark Command] %1$s has been archived";

    private int targetIndex;
    private String type;
    private ArrayList<Task> tasksToMark;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.type = "empty";
        tasksToMark = new ArrayList<Task>();
    }
    
    public MarkCommand(String taskType) {
        this.type = taskType;
        tasksToMark = new ArrayList<Task>();
    }

    @Override
    public CommandResult execute() {
        try {
            executeMark();

            model.pushToUndoHistory(this);
            model.clearRedoHistory();

            if (type.equals("due")) {
                return new CommandResult(COMMAND_WORD, MESSAGE_MARK_DUE_SUCCESS);
            } else {
                return new CommandResult(COMMAND_WORD, String.format(MESSAGE_MARK_SUCCESS, tasksToMark.get(0)));
            }
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(COMMAND_WORD,Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (DuplicateTaskException e) {
            return new CommandResult(COMMAND_WORD,MESSAGE_MARK_RECURRING_FAILURE);
        } catch (NotRecurringTaskException e) {
            return new CommandResult(COMMAND_WORD,MESSAGE_MARK_RECURRING_FAILURE);
        }

    }

    // @@author A0138862W
    @Override
    /*
     * Strategy to undo mark command
     * 
     * @see harmony.mastermind.logic.commands.Undoable#undo()
     */
    public CommandResult undo() {
        try {
            
            for (Task t:tasksToMark) {
                model.unmarkTask(t);

                requestHighlightLastActionedRow(t);                
            }
            model.pushToRedoHistory(this);

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNDO_SUCCESS, tasksToMark.get(0)));
        } catch (DuplicateTaskException dte) {
            return new CommandResult(COMMAND_WORD, String.format(UnmarkCommand.MESSAGE_DUPLICATE_UNMARK_TASK, tasksToMark.get(0)));
        } catch (ArchiveTaskList.TaskNotFoundException tnfe) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    // @@author A0138862W
    @Override
    /*
     * Strategy to redo mark command
     * 
     * @see harmony.mastermind.logic.commands.Redoable#redo()
     */
    public CommandResult redo() {
        try {
            executeMark();

            model.pushToUndoHistory(this);

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_REDO_SUCCESS, tasksToMark.get(0)));

        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(COMMAND_WORD,Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        } catch (DuplicateTaskException | NotRecurringTaskException e) {
            return new CommandResult(COMMAND_WORD,MESSAGE_MARK_RECURRING_FAILURE);
        }
    }

    //@@author A0124797R
    private void executeMark() throws TaskNotFoundException, DuplicateTaskException, NotRecurringTaskException {
        ObservableList<Task> lastShownList = model.getListToMark();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new TaskNotFoundException();
        }

        if (type.equals("empty")) {
            tasksToMark.add(lastShownList.get(targetIndex - 1));
            model.markTask(tasksToMark.get(0));
            if (tasksToMark.get(0).isRecur()) {
                model.addNextTask(tasksToMark.get(0));
            }
        } else if (type.equals("due")){
            model.updateFilteredListToShowUpcoming(new Date().getTime(),type);
            lastShownList = model.getListToMark();
            for (Task task: lastShownList) {
                tasksToMark.add(task);
            }
            
            model.markDue(tasksToMark);
        }
        

    }
}
