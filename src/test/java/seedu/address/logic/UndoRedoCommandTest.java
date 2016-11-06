package seedu.address.logic;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

//@@author A0146123R
/**
 * Responsible for testing the correct execution of UndoCommand and RedoCommand
 */
public class UndoRedoCommandTest extends CommandTest {

    /*
     * UndoCommand format: undo
     * 
     * RedoCommand format: redo
     */
    // -------------------------test for invalid commands------------------------------------------------

    @Test
    public void undo_noCommandToUndo_errorMessageShown() throws Exception {
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_FAILED);
    }

    @Test
    public void redo_noCommandToRedo_errorMessageShown() throws Exception {
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_FAILED);
    }

    // ------------------------------test for valid cases------------------------------------------------
    /*
     * Valid test scenarios
     * 
     * Possible scenarios: 
     * - Undo/Redo add command 
     * - Undo/Redo clear command 
     * - Undo/Redo delete command 
     * - Undo/Redo done command 
     * - Undo/Redo edit command
     *
     * As the undo and redo commands depend on other commands,
     * they will be tested as system tests
     */
    
}