package seedu.address.logic.commands;

import seedu.address.model.TaskMaster;

/**
 * Clears the task list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskMaster.getEmptyTaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
