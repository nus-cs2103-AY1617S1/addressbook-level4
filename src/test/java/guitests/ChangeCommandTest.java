package guitests;

import org.junit.Test;

import seedu.address.commons.util.XmlUtil;
import seedu.address.logic.commands.ChangeCommand;
import seedu.address.storage.XmlSerializableTaskManager;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

public class ChangeCommandTest extends TaskManagerGuiTest {
    private static String newFile = "./src/test/data/XmlAddressBookStorageTest/newFile.xml";
    private static String testData = TestUtil.getFilePathInSandboxFolder("sampleData.xml");

    @Test
    public void change() throws Exception {
        // verify that the storage location for a non-empty task list can be changed
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertChangeCommandSuccess();

        // verify other commands can work after a change command
        commandBox.runCommand(td.project.getAddCommand());
        assertListSize(9);
        assertStorageFileSame(new File(testData), 9);
    }
    
    private void assertChangeCommandSuccess() throws Exception {
        commandBox.runCommand("change " + newFile);
        File file = new File(newFile);
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertStorageFileSame(file, 8);
        assertResultMessage("Storage location has been changed!");
        
        commandBox.runCommand("change " + testData + " clear");
        assertStorageFileSame(new File(testData), 8);
        assert !file.exists();
    }
    
    private void assertStorageFileSame(File file, int n) throws Exception {
        XmlSerializableTaskManager dataFromFile = XmlUtil.getDataFromFile(file, XmlSerializableTaskManager.class);
        assertEquals(n, dataFromFile.getTaskList().size());
        assertEquals(2, dataFromFile.getTagList().size());
    }
    
    @Test
    public void change_invalidCommand_fail() {
        commandBox.runCommand("change dummyfile");
        assertResultMessage(ChangeCommand.MESSAGE_INVALID_FILE_PATH);
        commandBox.runCommand("change " + testData + " clean");
        assertResultMessage(ChangeCommand.MESSAGE_INVALID_CLEAR_DATA);
    }

}
