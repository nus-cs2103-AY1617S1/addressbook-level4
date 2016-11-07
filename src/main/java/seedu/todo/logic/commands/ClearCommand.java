package seedu.todo.logic.commands;

import seedu.todo.model.DoDoBird;
//@@author A0121643R
/**
 * Clears data in DoDo-Bird
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks have been cleared!";

    /**
     * Executes the clear command
     */
    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(DoDoBird.getEmptyToDoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
