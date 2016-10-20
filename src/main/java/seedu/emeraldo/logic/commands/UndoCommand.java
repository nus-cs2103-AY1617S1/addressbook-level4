package seedu.emeraldo.logic.commands;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_UNSUCCESSFUL =  "Cannot undo further";

    public UndoCommand() {}
    
    @Override
    public CommandResult execute() {
    	assert model != null;
        model.undoChanges();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
