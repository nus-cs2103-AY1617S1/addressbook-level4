package seedu.task.logic.commands;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "All tasks listed";

    @Override
    public CommandResult execute(boolean isUndo) {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
