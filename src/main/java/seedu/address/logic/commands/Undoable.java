package seedu.address.logic.commands;

/**
 * Commands that implement Undoable have actions that can be undo-ed
 */
public interface Undoable {
    
    /** Populates undo with the appropriate data required */
    void populateUndo();
}
