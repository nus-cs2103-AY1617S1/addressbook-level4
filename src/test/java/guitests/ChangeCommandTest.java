package guitests;

import org.junit.Test;

import seedu.address.logic.commands.ChangeCommand;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.io.File;

//@@author A0146123R
/**
 * gui tests for change command.
 */
public class ChangeCommandTest extends TaskManagerGuiTest {
    private static String newFile = "./src/test/data/XmlAddressBookStorageTest/newFile.xml";
    private static String sampleData = TestUtil.getFilePathInSandboxFolder("sampleData.xml");

    @Test
    public void change_invalidCommand_fail() {
        commandBox.runCommand("change dummyfile");
        assertResultMessage(ChangeCommand.MESSAGE_INVALID_FILE_PATH);
        commandBox.runCommand("change " + sampleData + " clean");
        assertResultMessage(ChangeCommand.MESSAGE_INVALID_CLEAR_DATA);
    }

    @Test
    public void change_validCommand_success() throws Exception {
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));

        // verify that the storage location can be changed
        assertChangeCommandSuccess(newFile);
        assertChangeClearCommandSuccess(newFile, sampleData);

        // verify other commands can work after a change command
        commandBox.runCommand(td.project.getAddCommand());
        assertListSize(9);
        assertStorageFileSame(new File(sampleData));
    }

    /**
     * Runs the change command to change the storage location and confirms the
     * new storage location is correct.
     */
    private void assertChangeCommandSuccess(String newFilePath) throws Exception {
        commandBox.runCommand("change " + newFilePath);
        assertResultMessage("Storage location changed: " + newFilePath);
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertStorageFileSame(new File(newFilePath));
    }

    /**
     * Runs the change command to change the storage location and clear data
     * saved in the previous location and confirms the new storage location is
     * correct and the previous data file is deleted.
     */
    private void assertChangeClearCommandSuccess(String previousFilePath, String newFilePath) throws Exception {
        commandBox.runCommand("change " + newFilePath + " clear");
        assertChangeCommandSuccess(newFilePath);
        assert !new File(previousFilePath).exists();
    }

}