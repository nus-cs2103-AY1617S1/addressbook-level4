package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniquePersonList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the current listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

<<<<<<< HEAD:src/main/java/seedu/address/logic/commands/DeleteCommand.java
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
=======
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Task: %1$s";
>>>>>>> 2196a4f91cbd3b9663c1ef7ca7f3551168fa35e4:src/main/java/seedu/ggist/logic/commands/DeleteCommand.java

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

<<<<<<< HEAD:src/main/java/seedu/address/logic/commands/DeleteCommand.java
        UnmodifiableObservableList<ReadOnlyPerson> lastShownList = model.getFilteredTaskList();
=======
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
>>>>>>> 2196a4f91cbd3b9663c1ef7ca7f3551168fa35e4:src/main/java/seedu/ggist/logic/commands/DeleteCommand.java

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

<<<<<<< HEAD:src/main/java/seedu/address/logic/commands/DeleteCommand.java
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
=======
        ReadOnlyTask personToDelete = lastShownList.get(targetIndex - 1);
>>>>>>> 2196a4f91cbd3b9663c1ef7ca7f3551168fa35e4:src/main/java/seedu/ggist/logic/commands/DeleteCommand.java

        try {
            model.deleteTask(taskToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
