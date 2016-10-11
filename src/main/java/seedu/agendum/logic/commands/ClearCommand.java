package seedu.agendum.logic.commands;

import seedu.agendum.model.ToDoList;

/**
 * Clears the to do list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Your tasks have been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(ToDoList.getEmptyToDoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
