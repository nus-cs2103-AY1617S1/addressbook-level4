package seedu.todolist.logic.commands;

import seedu.todolist.model.ToDoList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears tasks permanently.\n\t"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Incomplete task has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(ToDoList.getEmptyToDoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
