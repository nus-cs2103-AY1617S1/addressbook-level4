package seedu.forgetmenot.logic.commands;

import seedu.forgetmenot.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ForgetMeNot has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Clears ForgetMeNot of all tasks.";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.saveToHistory();
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
