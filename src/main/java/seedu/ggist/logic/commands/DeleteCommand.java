package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the current listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int[] targetIndexes;

    public DeleteCommand(int[] integers) {
        this.targetIndexes = integers;
    }


    @Override
    public CommandResult execute() {
        for(int i = 0; i < targetIndexes.length; i++){
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndexes[0]) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndexes[0] - 1);
        try {
            model.deleteTask(taskToDelete);
            listOfCommands.push(COMMAND_WORD);
            listOfTasks.push(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        }
       
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, targetIndexes.toString()));
    }


    @Override
    public  String toString(){
        return COMMAND_WORD;
    }

}
