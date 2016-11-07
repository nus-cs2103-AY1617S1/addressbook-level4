package seedu.toDoList.logic.commands;

import seedu.toDoList.model.TaskManager;

/**
 * Clears the toDoList.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "To-do List has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskManager.getEmptyTaskManager());
        String message = MESSAGE_SUCCESS;
        model.saveState(message);
        return new CommandResult(message);
    }
}
