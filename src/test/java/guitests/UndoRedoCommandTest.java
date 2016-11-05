package guitests;

import guitests.guihandles.TaskCardHandle;
import seedu.menion.commons.core.Config;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.logic.commands.ModifyStoragePathCommand;
import seedu.menion.logic.commands.RedoCommand;
import seedu.menion.logic.commands.UndoCommand;
import seedu.menion.testutil.TestActivity;
import seedu.menion.testutil.TestUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

//@@author A0139515A
public class UndoRedoCommandTest extends ActivityManagerGuiTest {

    @Test
    public void undoRedo() throws FileNotFoundException, DataConversionException {
    	
        //add one activity
        TestActivity[] originalList = td.getTypicalTask();
        TestActivity activityToAdd = td.task2;
        
        assertAddSuccess(activityToAdd, originalList);
        TestActivity[] currentList = TestUtil.addActivitiesToList(originalList, activityToAdd);

        //testing undo command for adding of task
        commandBox.runCommand("undo n");
        assertTrue(activityListPanel.isTaskListMatching(originalList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        
        //testing redo command 
        commandBox.runCommand("redo n");
        assertTrue(activityListPanel.isTaskListMatching(currentList));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        
        
        //testing undo command for deleting of task
        TestActivity[] beforeDeleteList = currentList;
       
        commandBox.runCommand("delete task 2");
        assertTrue(activityListPanel.isTaskListMatching(originalList));
        
        commandBox.runCommand("undo n");
        assertTrue(activityListPanel.isTaskListMatching(beforeDeleteList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        
        //testing redo command
        commandBox.runCommand("redo n");
        assertTrue(activityListPanel.isTaskListMatching(originalList));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
       
    
        //testing undo for clear command
        commandBox.runCommand("clear");
        commandBox.runCommand("undo n");
        assertTrue(activityListPanel.isTaskListMatching(originalList));
        assertResultMessage("Menion successfully undo your previous changes");
        
        //testing redo command
        commandBox.runCommand("redo n");
        assertListSize(0);
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        
        //testing undo for modify storage path
        String originalTestFilePath = new File(ModifyStoragePathCommand.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + File.separator + FileUtil.getPath("src/test/data/sandbox/sampleData.xml");
        commandBox.runCommand("modify test");
        commandBox.runCommand("undo m");
        assertEquals(originalTestFilePath, Config.getInstance().getActivityManagerFilePath());
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        
        //testing redo command
        String testFilePath = new File(ModifyStoragePathCommand.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + File.separator + FileUtil.getPath("src/test/data/ModifyStoragePathTest/test.xml");
        commandBox.runCommand("redo m");
        assertEquals(testFilePath, Config.getInstance().getActivityManagerFilePath());
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        
        //revert to original file path
        commandBox.runCommand("undo m");
        commandBox.runCommand("undo m");
       
        //invalid command
        //there is 3 states to undo, undo 4 times to check message
        commandBox.runCommand("undo n");
        commandBox.runCommand("undo n");
        commandBox.runCommand("undo n");
        commandBox.runCommand("undo n");
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
        
        //there is 3 states to redo, redo 4 times to check message
        commandBox.runCommand("redo n");
        commandBox.runCommand("redo n");
        commandBox.runCommand("redo n");
        commandBox.runCommand("redo n");
        commandBox.runCommand("redo n");
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);
    }

    private void assertAddSuccess(TestActivity activityToAdd, TestActivity... currentList) {
        
        commandBox.runCommand(activityToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = activityListPanel.navigateToTask(activityToAdd.getActivityName().fullName);
        assertTaskMatching(activityToAdd, addedCard);

        //confirm the list now contains all previous activities plus the new activity
        TestActivity[] expectedList = TestUtil.addActivitiesToList(currentList, activityToAdd);
        assertTrue(activityListPanel.isTaskListMatching(expectedList));
    }
}
