package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import teamfour.tasc.logic.commands.RedoCommand;
import teamfour.tasc.testutil.TestTask;

public class RedoCommandTest extends AddressBookGuiTest {
    
    public void prepare() {
        commandBox.runCommand("add \"redo test case 1\"");
        commandBox.runCommand("add \"redo test case 2\"");
        commandBox.runCommand("add \"redo test case 3\"");
    }
    
    /* Equivalence Partitions:
     * - redo (no argument)
     *     - redo 1 if has available history
     *     - unchanged if no available history
     *     - redoable command history is reset if 
     *       undoable commands are entered after undo
     *     
     * - redo (positive integer)
     *     - redo all history if more than available history
     *     - redo count if less than or equal to available history
     *     - unchanged if no available history
     *     
     * - redo (illegal arguments)
     *     - nonpositive integer: command fails, error message shown
     *     - not an integer: command fails, error message shown
     *     
     * Boundary value analysis:
     *     - For 0 history: test with 0, 1
     *     - For 3 history: test with 3, 4
     */
    
    //---------------- redo (no argument) ----------------------
    
    @Test
    public void redo_noArg_hasThreeHistory_redoOne() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo 3");
        assertRedoResult("redo", createRedoSuccessResultMessage(1), 
                td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void redo_noArg_hasZeroHistory_remainsUnchanged() {
        commandBox.runCommand("delete 1");
        assertRedoResult("redo", RedoCommand.MESSAGE_NO_PAST_COMMAND_TO_REDO,
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void redo_noArg_resetHistory_remainsUnchanged() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("delete 1");
        assertRedoResult("redo", RedoCommand.MESSAGE_NO_PAST_COMMAND_TO_REDO,
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    //---------------- redo (positive integer) ----------------------
    
    @Test
    public void redo_four_hasThreeHistory_redoAll() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo 3");
        assertRedoResult("redo 4", createRedoSuccessResultMessage(3), 
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void redo_three_hasThreeHistory_redoThree() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo 3");
        assertRedoResult("redo 3", createRedoSuccessResultMessage(3), 
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void redo_three_hasThreeHistory_redoOneByOneUntilOriginal() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo 3");
        assertRedoResult("redo 1", createRedoSuccessResultMessage(1), 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
        assertRedoResult("redo 1", createRedoSuccessResultMessage(1), 
                td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
        assertRedoResult("redo 1", createRedoSuccessResultMessage(1), 
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
        assertRedoResult("redo 1", RedoCommand.MESSAGE_NO_PAST_COMMAND_TO_REDO,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void redo_three_hasZeroHistory_remainsUnchanged() {
        assertRedoResult("redo 3", RedoCommand.MESSAGE_NO_PAST_COMMAND_TO_REDO,
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    //---------------- redo (illegal arguments) ----------------------
    
    @Test
    public void redo_nonpositiveInteger_hasThreeHistory_remainsUnchanged_errorMessageShown() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo 3");
        assertRedoResult("redo -3", "Invalid command format! \n" 
                                    + RedoCommand.MESSAGE_USAGE,
                                    td.submitPrototype, 
                                    td.submitProgressReport, td.developerMeeting,
                                    td.researchWhales, td.learnVim, 
                                    td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void redo_notAnInteger_hasThreeHistory_remainsUnchanged_errorMessageShown() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo 3");
        assertRedoResult("redo string", "Invalid command format! \n" 
                                        + RedoCommand.MESSAGE_USAGE,
                                        td.submitPrototype, 
                                        td.submitProgressReport, td.developerMeeting,
                                        td.researchWhales, td.learnVim, 
                                        td.buyBirthdayGift, td.signUpForYoga);
    }
    
    //---------------- Utility methods ----------------------
    
    private void assertRedoResult(String command, String expectedMessage, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedMessage);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
    
    private String createRedoSuccessResultMessage(int numCommandsRedone) {
        return String.format(RedoCommand.MESSAGE_SUCCESS, 
                numCommandsRedone == 1 ? 
                "command" : numCommandsRedone + " commands");
    }
}
