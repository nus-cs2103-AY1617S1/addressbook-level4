package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import harmony.mastermind.logic.commands.EditCommand;
import harmony.mastermind.testutil.TestTask;
import harmony.mastermind.testutil.TestUtil;
import harmony.mastermind.testutil.TypicalTestTasks;

public class EditCommandTest extends TaskManagerGuiTest {

    //@@author A0124797R
    @Test
    public void editUndoRedo_EditNameTag_Success() {

        //edit the 4th task in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 4;
        String editArgs = "name to past year papers, tags to #examPrep";
        assertEditSuccess(targetIndex, editArgs, currentList);
        
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
        
        commandBox.runCommand("redo");
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndex);
        expectedRemainder = TestUtil.addTasksToList(expectedRemainder, TypicalTestTasks.task5);
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }

    
    
    /**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndex e.g. to edit the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before edit).
     */
    private void assertEditSuccess(int targetIndex, String argsToEdit, final TestTask[] currentList) {
        TestTask taskToEdit = currentList[targetIndex-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndex);
        expectedRemainder = TestUtil.addTasksToList(expectedRemainder, TypicalTestTasks.task5);
        
        commandBox.runCommand("edit " + targetIndex + argsToEdit);

        //confirm the list now contains the updated task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_PROMPT,taskToEdit));
    }
}