package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.commons.exceptions.NotRecurringTaskException;
import harmony.mastermind.commons.exceptions.TaskAlreadyMarkedException;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;
import javafx.collections.ObservableList;

//@@author A0124797R
public class MarkCommand extends Command implements Undoable, Redoable {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": mark a task as done "
                                               + "Parameters: INDEX (must be a positive integer)\n"
                                               + "Example: "
                                               + COMMAND_WORD
                                               + " 1";

    public static final String COMMAND_SUMMARY = "Marking a task as done:"
                                                 + "\n"
                                                 + COMMAND_WORD
                                                 + " INDEX";

    public static final String MESSAGE_SUCCESS = "%1$s has been archived";
    public static final String MESSAGE_MARKED_TASK = "%1$s is already marked";
    public static final String MESSAGE_MARK_RECURRING_FAILURE = "Unable to add recurring Task";

    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Mark Command] %1$s has been unmarked";
    public static final String MESSAGE_REDO_SUCCESS = "[Redo Mark Command] %1$s has been archived";

    public final int targetIndex;

    public Task taskToMark;

    public MarkCommand(int targetIndex, String currentTab) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        try {
            executeMark();
            model.pushToUndoHistory(this);
            model.clearRedoHistory();

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_SUCCESS, taskToMark));
        } catch (TaskAlreadyMarkedException ex) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_MARKED_TASK, taskToMark));
        } catch (IndexOutOfBoundsException ex) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(COMMAND_WORD,Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        } catch (DuplicateTaskException e) {
            return new CommandResult(COMMAND_WORD,MESSAGE_MARK_RECURRING_FAILURE);
        } catch (NotRecurringTaskException e) {
            return new CommandResult(COMMAND_WORD,MESSAGE_MARK_RECURRING_FAILURE);
        }

    }

    @Override
    // @@author A0138862W
    public CommandResult undo() {
        try {
            // remove the task that's previously added.
            model.unmarkTask(taskToMark);

            model.pushToRedoHistory(this);

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNDO_SUCCESS, taskToMark));
        } catch (DuplicateTaskException e) {
            return new CommandResult(COMMAND_WORD, String.format(UnmarkCommand.MESSAGE_DUPLICATE_UNMARK_TASK, taskToMark));
        } catch (ArchiveTaskList.TaskNotFoundException e) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        }
    }

    @Override
    // @@author A0138862W
    public CommandResult redo() {
        try {
            executeMark();

            model.pushToUndoHistory(this);

            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_REDO_SUCCESS, taskToMark));
        } catch (TaskAlreadyMarkedException ex) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_MARKED_TASK, taskToMark));
        } catch (IndexOutOfBoundsException ex) {
            return new CommandResult(COMMAND_WORD, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(COMMAND_WORD,Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        } catch (DuplicateTaskException e) {
            return new CommandResult(COMMAND_WORD,MESSAGE_MARK_RECURRING_FAILURE);
        } catch (NotRecurringTaskException e) {
            return new CommandResult(COMMAND_WORD,MESSAGE_MARK_RECURRING_FAILURE);

        }
    }

    //@@author A0124797R
    private void executeMark() throws TaskAlreadyMarkedException, IndexOutOfBoundsException, TaskNotFoundException, DuplicateTaskException, NotRecurringTaskException {
        ObservableList<Task> lastShownList = model.getListToMark();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new IndexOutOfBoundsException();
        }
        
        taskToMark = lastShownList.get(targetIndex - 1);

        if (taskToMark.isMarked()) {
            throw new TaskAlreadyMarkedException();
        }

        model.markTask(taskToMark);
        if (taskToMark.isRecur()) {
            model.addNextTask(taskToMark);
        }

    }
}
