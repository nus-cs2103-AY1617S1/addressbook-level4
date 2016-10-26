package seedu.malitio.model.history;

//@@author A0129595N
public abstract class InputHistory {
    
    protected String commandForUndo;
    
    public String getUndoCommand() {
        return commandForUndo;
    }

}
