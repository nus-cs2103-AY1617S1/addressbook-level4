package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.ChangeCommand;
import seedu.address.logic.commands.RedoChangeCommand;
import seedu.address.logic.commands.UndoChangeCommand;

//@@author A0146123R
/**
 * Responsible for testing the correct execution of ChangeCommand and its undo and redo
 */
public class ChangeCommandTest extends CommandTest{
    
    /*
     * ChangeCommmand format: change FILE_PATH [clear]
     * 
     * UndoChangeCommand format: undochange [clear]
     * 
     * RedoChangeCommand format: redochange
     * 
     * Equivalence partitions for file path: invalid path, valid path
     * 
     * Equivalence partitions for clear argument: absent, "clear", invalid clear argument (any other string)
     */
   //-------------------------test for invalid commands------------------------------------------------
    
    @Test
    public void change_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE);
        assertAbsenceKeywordFormatBehaviorForCommand("change", expectedMessage);
        assertCommandBehavior("change a.xml clear b", expectedMessage);
    }
    
    @Test
    public void change_invalidFilePath_errorMessageShown() throws Exception{
        String expectedMessage = ChangeCommand.MESSAGE_INVALID_FILE_PATH;
        assertCommandBehavior("change !@#$%^&*", expectedMessage); 
        assertCommandBehavior("change a.txt", expectedMessage);
    }

    @Test
    public void change_invalidClearArg_errorMessageShown() throws Exception {
        assertCommandBehavior("change a.xml c", ChangeCommand.MESSAGE_INVALID_CLEAR_DATA);
    }
    
    @Test
    public void undoChange_invalidClearArg_errorMessageShown() throws Exception {
        assertCommandBehavior("undochange c", UndoChangeCommand.MESSAGE_INVALID_CLEAR_DATA);
    }
    
    /**
     * Confirms the 'no change command to undo' behavior for the undochange command
     */
    @Test
    public void undoChange_noChangeToUndo_errorMessageShown() throws Exception {
        assertCommandBehavior("undochange", UndoChangeCommand.MESSAGE_UNDO_FAILED);
    }
    
    /**
     * Confirms the 'no undo change command to redo' behavior for the redochange command
     */
    @Test
    public void redoChange_noUndoChangeToRedo_errorMessgeShown() throws Exception {
        assertCommandBehavior("redochange", RedoChangeCommand.MESSAGE_REDO_FAILED);
    }
    
    //------------------------------test for valid cases------------------------------------------------
    
    /*
     * As the change command, undochanage command and redochange command 
     * involve multiple components, they will be tested as system tests
     */
    
}
