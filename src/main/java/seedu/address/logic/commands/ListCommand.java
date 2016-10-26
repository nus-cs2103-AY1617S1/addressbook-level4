package seedu.address.logic.commands;

import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Lists all tasks that is not completed in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all incomplete tasks";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        try {
			model.updateFilteredListToShowIncompleteTask();
		} catch (DuplicateTaskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
