//@@author A0148096W

package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import teamfour.tasc.logic.commands.UndoCommand;
import teamfour.tasc.testutil.TestTask;

public class UndoCommandTest extends AddressBookGuiTest {
    
    public void prepare() {
        commandBox.runCommand("add \"undo test case 1\"");
        commandBox.runCommand("add \"undo test case 2\"");
        commandBox.runCommand("add \"undo test case 3\"");
    }
    
    /* Equivalence Partitions:
     * - undo (no argument)
     *     - undo 1 if has available history
     *     - unchanged if no available history
     *     
     * - undo (positive integer)
     *     - undo all history if more than available history
     *     - undo count if less than or equal to available history
     *     - unchanged if no available history
     *     
     * - undo (illegal arguments)
     *     - nonpositive integer: command fails, error message shown
     *     - not an integer: command fails, error message shown
     *     
     * Boundary value analysis:
     *     - For 0 history: test with 0, 1
     *     - For 3 history: test with 3, 4
     */
    
    //---------------- undo (no argument) ----------------------
    
    @Test
    public void undo_noArg_hasThreeHistory_undoOne() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        assertUndoResult("undo", createUndoSuccessResultMessage(1), 
                td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void undo_noArg_hasZeroHistory_remainsUnchanged() {
        assertUndoResult("undo", UndoCommand.MESSAGE_NO_PAST_COMMAND_TO_UNDO,
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    //---------------- undo (positive integer) ----------------------
    
    @Test
    public void undo_four_hasThreeHistory_undoAll() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        assertUndoResult("undo 4", createUndoSuccessResultMessage(3), 
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void undo_three_hasThreeHistory_undoThree() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        assertUndoResult("undo 3", createUndoSuccessResultMessage(3), 
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void undo_three_hasThreeHistory_undoOneByOneUntilOriginal() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        assertUndoResult("undo 1", createUndoSuccessResultMessage(1), 
                td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
        assertUndoResult("undo 1", createUndoSuccessResultMessage(1), 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
        assertUndoResult("undo 1", createUndoSuccessResultMessage(1), 
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
        assertUndoResult("undo 1", UndoCommand.MESSAGE_NO_PAST_COMMAND_TO_UNDO,
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void undo_three_hasZeroHistory_remainsUnchanged() {
        assertUndoResult("undo 3", UndoCommand.MESSAGE_NO_PAST_COMMAND_TO_UNDO,
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    //---------------- undo (illegal arguments) ----------------------
    
    @Test
    public void undo_nonpositiveInteger_hasThreeHistory_remainsUnchanged_errorMessageShown() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        assertUndoResult("undo -3", "Invalid command format! \n" 
                                    + UndoCommand.MESSAGE_USAGE,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void undo_notAnInteger_hasThreeHistory_remainsUnchanged_errorMessageShown() {
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        assertUndoResult("undo string", "Invalid command format! \n" 
                                        + UndoCommand.MESSAGE_USAGE,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    //---------------- Utility methods ----------------------
    
    private void assertUndoResult(String command, String expectedMessage, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedMessage);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
    
    private String createUndoSuccessResultMessage(int numCommandsUndone) {
        return String.format(UndoCommand.MESSAGE_SUCCESS, 
                numCommandsUndone == 1 ? 
                "command" : numCommandsUndone + " commands");
    }
}
