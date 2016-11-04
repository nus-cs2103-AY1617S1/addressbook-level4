package seedu.simply.logic.commands;

import static seedu.simply.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0147890U
/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_DONE_SUCCESS = "Listed all completed tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List Task in Simply\n"
            + "To show completed Task :        Example: " + COMMAND_WORD + " done\n"
            + "To show incompleted Task :     Example: " + COMMAND_WORD + "\n";

    private final String keyword;

    public ListCommand(String args) {
        this.keyword = args.trim();
    }

    @Override
    public CommandResult execute() {
        if (keyword.equals("")) {
            model.updateFilteredListToShowAllUncompleted();
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (keyword.equals("done")) {
            model.updateFilteredListToShowAllCompleted();
            return new CommandResult(MESSAGE_DONE_SUCCESS);
        } else {
            Command command = new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            return command.execute();
        }
    }
}
