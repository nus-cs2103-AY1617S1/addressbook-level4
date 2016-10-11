package seedu.address.logic.commands;

/**
 * Template for the two other add commands to follow
 * Stores messages that are shared by both add commands
 * @author User
 *
 */
public abstract class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
}
