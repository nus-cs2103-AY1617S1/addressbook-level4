//@@author A0093896H
package seedu.todo.logic.commands;

import seedu.todo.commons.core.Messages;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.Completion;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todo.model.tag.UniqueTagList.DuplicateTagException;

/**
 * Marks a task identified using it's last displayed index from the to do list.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Mark Task: Name : %1$s";

    public final int targetIndex;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
        
        try {
            Task toMark = model.getTask(taskToMark);
            
            toMark.setCompletion(new Completion(true));

            model.updateTask(taskToMark, toMark);
            model.updateFilteredListToShowAll();
            
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be found";
        } 

        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToMark.getName()));
    }
}
