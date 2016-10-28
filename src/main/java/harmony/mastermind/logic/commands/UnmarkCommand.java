package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.commons.events.ui.HighlightLastActionedRowRequestEvent;
import harmony.mastermind.commons.exceptions.TaskAlreadyUnmarkedException;
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

    public static final String COMMAND_SUMMARY = "marks a task as not complete:"
                                                 + "\n"
                                                 + COMMAND_WORD
                                                 + " INDEX";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "%1$s has been unmarked";
    public static final String MESSAGE_DUPLICATE_UNMARK_TASK = "%1$s already exist in not completed list";
    public static final String MESSAGE_UNMARK_TASK_FAILURE = "Tasks in current tab has not been marked";

    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Unmark Command] %1$s has been archived";
    public static final String MESSAGE_REDO_SUCCESS = "[Redo Unmark Command] %1$s has been unmarked";

    public final int targetIndex;

    Task taskToUnmark;

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

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
        } catch (TaskAlreadyUnmarkedException tau) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNMARK_TASK_FAILURE, taskToUnmark));
        } catch (DuplicateTaskException dte) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_DUPLICATE_UNMARK_TASK, taskToUnmark));
        } catch (TaskNotFoundException tnfe) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    @Override
    //@@author A0138862W
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
    public CommandResult redo() {
        try {
            executeUnmark();
            
            model.pushToUndoHistory(this);
            
            requestHighlightLastActionedRow(taskToUnmark);

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
        } catch (TaskAlreadyUnmarkedException tau) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNMARK_TASK_FAILURE, taskToUnmark));
        } catch (DuplicateTaskException dte) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_DUPLICATE_UNMARK_TASK, taskToUnmark));
        } catch (TaskNotFoundException tnfe) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    //@@author A0124797R
    private void executeUnmark() throws IndexOutOfBoundsException, TaskNotFoundException,
        TaskAlreadyUnmarkedException, DuplicateTaskException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredArchiveList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new TaskNotFoundException();
        }
        
        taskToUnmark = (Task) lastShownList.get(targetIndex - 1);
        
        if (!taskToUnmark.isMarked()) {
            throw new TaskAlreadyUnmarkedException();
        }
        
        model.unmarkTask(taskToUnmark);
    }
    
    // @@author A0138862W
    private void requestHighlightLastActionedRow(Task task){
        EventsCenter.getInstance().post(new HighlightLastActionedRowRequestEvent(task));
    }
}
