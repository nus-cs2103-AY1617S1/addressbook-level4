package seedu.taskitty.logic.commands;

import seedu.taskitty.model.TaskManager;

/**
 * Clears the taskManager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    
    public static final String MESSAGE_PARAMETER = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "This command clears all tasks from TasKitty, Meow!";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.storeClearCommandInfo();
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
