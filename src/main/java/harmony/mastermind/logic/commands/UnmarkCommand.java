package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.model.task.ArchiveTaskList.TaskNotFoundException;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0124797R
public class UnmarkCommand extends Command implements Undoable, Redoable{
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": undo marking of task as done"
                                               + "Parameters: INDEX (must be a positive integer)\n"
                                               + "Example: "
                                               + COMMAND_WORD
                                               + " 1";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " INDEX";

    public static final String MESSAGE_UNMARK_SUCCESS = "%1$s has been unmarked";
    public static final String MESSAGE_DUPLICATE_UNMARK_TASK = "%1$s already exist in not completed list";
    public static final String MESSAGE_UNMARK_FAILURE = "Tasks in current tab has not been marked";

    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Unmark Command] %1$s has been archived";
    public static final String MESSAGE_REDO_SUCCESS = "[Redo Unmark Command] %1$s has been unmarked";

    private final int targetIndex;
    private Task taskToUnmark;

    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        try {
            executeUnmark();
            
            model.pushToUndoHistory(this);
            
            model.clearRedoHistory();
            
            requestHighlightLastActionedRow(taskToUnmark);

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNMARK_SUCCESS, taskToUnmark));
        } catch (DuplicateTaskException dte) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_DUPLICATE_UNMARK_TASK, taskToUnmark));
        } catch (TaskNotFoundException tnfe) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    //@@author A0138862W
    @Override
    /*
     * Strategy to undo unmark command
     * 
     * @see harmony.mastermind.logic.commands.Undoable#undo()
     */
    public CommandResult undo() {
        try {
            // remove the task that's previously added.
            model.markTask(taskToUnmark);
            
            model.pushToRedoHistory(this);

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNDO_SUCCESS, taskToUnmark));
        } catch (UniqueTaskList.TaskNotFoundException e) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        }
    }

    @Override
    //@@author A0138862W
    /*
     * 
     * Strategy to redo unmark command
     * 
     * @see harmony.mastermind.logic.commands.Redoable#redo()
     */
    public CommandResult redo() {
        try {
            executeUnmark();
            
            model.pushToUndoHistory(this);
            
            requestHighlightLastActionedRow(taskToUnmark);

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNMARK_SUCCESS, taskToUnmark));
        } catch (DuplicateTaskException dte) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_DUPLICATE_UNMARK_TASK, taskToUnmark));
        } catch (TaskNotFoundException tnfe) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    //@@author A0124797R
    private void executeUnmark() throws IndexOutOfBoundsException, TaskNotFoundException,
        DuplicateTaskException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredArchiveList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new TaskNotFoundException();
        }
        
        taskToUnmark = (Task) lastShownList.get(targetIndex - 1);
        
        model.unmarkTask(taskToUnmark);
    }
    
}
