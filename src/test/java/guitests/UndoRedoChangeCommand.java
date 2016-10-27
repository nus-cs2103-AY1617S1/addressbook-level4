package guitests;

import org.junit.Test;

import seedu.address.commons.util.XmlUtil;
import seedu.address.logic.commands.RedoChangeCommand;
import seedu.address.logic.commands.UndoChangeCommand;
import seedu.address.storage.XmlSerializableTaskManager;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

public class UndoRedoChangeCommand extends TaskManagerGuiTest {
    private static String newFile = "./src/test/data/XmlAddressBookStorageTest/newFile.xml";
    private static String testData = TestUtil.getFilePathInSandboxFolder("sampleData.xml");

    @Test
    public void change() throws Exception {
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertUndoChangeCommandSuccess();
        assertRedoChangeCommandSuccess();
        
        // verify other commands can work after an undo/redochange command
        commandBox.runCommand("undochange clear");
        assert !new File(newFile).exists();
        commandBox.runCommand(td.project.getAddCommand());
        assertListSize(9);
        assertStorageFileSame(new File(testData), 9);
    }
    
    private void assertUndoChangeCommandSuccess() throws Exception {
        commandBox.runCommand("change " + newFile + " clear");
        commandBox.runCommand("undochange");
        assertResultMessage("Storage location has been changed back!");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertStorageFileSame(new File(testData), 8);
        assert new File(newFile).exists();
    }
    
    private void assertRedoChangeCommandSuccess() throws Exception {
        commandBox.runCommand("redochange");
        assertResultMessage("Storage location has been changed!");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertStorageFileSame(new File(newFile), 8);
        assert !new File(testData).exists();
    }
    
    private void assertStorageFileSame(File file, int n) throws Exception {
        XmlSerializableTaskManager dataFromFile = XmlUtil.getDataFromFile(file, XmlSerializableTaskManager.class);
        assertEquals(n, dataFromFile.getTaskList().size());
        assertEquals(2, dataFromFile.getTagList().size());
    }
    
    @Test
    public void change_invalidCommand_fail() {
        commandBox.runCommand("undochange");
        assertResultMessage(UndoChangeCommand.MESSAGE_UNDO_FAILED);
        
        commandBox.runCommand("change " + testData);
        commandBox.runCommand("undochange d");
        assertResultMessage(UndoChangeCommand.MESSAGE_INVALID_CLEAR_DATA);
        
        commandBox.runCommand("redochange");
        assertResultMessage(RedoChangeCommand.MESSAGE_REDO_FAILED);
    }

}
