package seedu.taskman.logic.commands;

import seedu.taskman.model.TaskMan;

/**
 * Clears the task man.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task man has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskMan.getEmptyTaskMan());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
