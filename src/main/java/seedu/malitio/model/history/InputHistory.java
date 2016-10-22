package seedu.malitio.model.history;

public abstract class InputHistory {
    
    protected String commandForUndo;
    
    public String getUndoCommand() {
        return commandForUndo;
    }

}
