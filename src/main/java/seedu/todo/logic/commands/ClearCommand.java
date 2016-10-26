package seedu.todo.logic.commands;

import seedu.todo.model.DoDoBird;

/**
 * Clears DoDo-Bird
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "To Do List has been cleared!";

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(DoDoBird.getEmptyToDoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
