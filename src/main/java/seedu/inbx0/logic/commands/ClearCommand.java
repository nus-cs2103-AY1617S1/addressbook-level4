package seedu.inbx0.logic.commands;

import seedu.inbx0.model.TaskList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clr";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskList.getEmptyTaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
