package seedu.address.logic.commands;

public abstract class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
}
