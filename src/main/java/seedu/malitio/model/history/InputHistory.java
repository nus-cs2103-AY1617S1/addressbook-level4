package seedu.malitio.model.history;

//@@author A0129595N
public abstract class InputHistory {
    
    protected String commandForUndo;
    protected String commandForRedo;
    
    public String getUndoCommand() {
        return commandForUndo;
    }
    
    public String getRedoCommand() {
        return commandForRedo;
    }

}
