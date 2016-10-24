package seedu.taskmanager.logic.commands;

import seedu.taskmanager.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "cl";
    //@@author 
    
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskManager.getEmptyTaskManager(), MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
