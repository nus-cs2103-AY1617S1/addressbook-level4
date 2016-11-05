package guitests;

import org.junit.Test;

import seedu.address.logic.commands.RedoChangeCommand;
import seedu.address.logic.commands.UndoChangeCommand;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.io.File;

//@@author A0146123R
/**
 * gui tests for undo and redo change commands.
 */
public class UndoRedoChangeCommand extends TaskManagerGuiTest {
    private static String file1 = "./src/test/data/XmlAddressBookStorageTest/newFile1.xml";
    private static String file2 = "./src/test/data/XmlAddressBookStorageTest/newFile2.xml";
    private static String testData = TestUtil.getFilePathInSandboxFolder("sampleData.xml");

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

    @Test
    public void undoRedoChange_validCommand_success() throws Exception {
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        commandBox.runCommand("change " + file1);
        commandBox.runCommand("change " + file2 + " clear");

        // verify that the change for storage location can be undone and redone
        // for multiple times
        assertUndoChangeCommandSuccess(file2, file1, true);
        assertUndoChangeCommandSuccess(file1, testData, true);

        assertRedoChangeCommandSuccess(testData, file1, true);
        assertRedoChangeCommandSuccess(file1, file2, false);

        assertUndoChangeCommandSuccess(file2, file1, false);
        assertUndoChangeCommandSuccess(file1, testData, false);

        // verify other commands can work after an undo/redochange command
        commandBox.runCommand(td.project.getAddCommand());
        assertListSize(9);
        assertStorageFileSame(new File(testData));
    }

    /**
     * Runs the undochange command to change back the storage location and clear
     * data saved in the current location if specified and confirms the new
     * storage location is correct.
     */
    private void assertUndoChangeCommandSuccess(String previousFilePath, String newFilePath, boolean isToClear)
            throws Exception {
        commandBox.runCommand("undochange " + (isToClear ? "" : "clear"));
        assertResultMessage("Storage location changed back.");
        assertChangeSusscess(previousFilePath, newFilePath, isToClear);
    }

    /**
     * Runs the redochange command to redo change the storage location and clear
     * data saved in the previous location if specified in the corresponding
     * change command and confirms the new storage location is correct.
     */
    private void assertRedoChangeCommandSuccess(String previousFilePath, String newFilePath, boolean shouldClear)
            throws Exception {
        commandBox.runCommand("redochange");
        assertResultMessage("Storage location changed.");
        assertChangeSusscess(previousFilePath, newFilePath, shouldClear);
    }

    private void assertChangeSusscess(String previousFilePath, String newFilePath, boolean previousFileExists)
            throws Exception {
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertStorageFileSame(new File(newFilePath));
        assert new File(previousFilePath).exists() == previousFileExists;
    }

}