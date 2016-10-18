package seedu.task.logic.commands;

import seedu.task.model.TaskManager;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Previous action has been undone!";
    public static final String MESSAGE_NO_ACTION_TO_UNDO = "No action was executed!";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        if (previousCommand == null) {
            return new CommandResult(false, MESSAGE_NO_ACTION_TO_UNDO);
        }
        
        previousCommand.rollback();
        
        return new CommandResult(true, MESSAGE_SUCCESS);
    }
}
