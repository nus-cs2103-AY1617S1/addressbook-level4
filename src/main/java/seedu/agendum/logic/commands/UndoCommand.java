package seedu.agendum.logic.commands;

//@@author A0133367E
/**
 * Undo the last successful command that mutate the to do list
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
	public static final String COMMAND_FORMAT = "undo";
	public static final String COMMAND_DESCRIPTION = "Undo last command!";
	
    public static final String MESSAGE_SUCCESS = "Previous command undone!";
    public static final String MESSAGE_FAILURE = "Nothing to undo!";

    public UndoCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        if (model.restorePreviousToDoList()) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
	
    @Override
    public String getName() {
        return COMMAND_WORD;
    }
	
    @Override
    public String getFormat() {
        return COMMAND_FORMAT;
    }
	
    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}