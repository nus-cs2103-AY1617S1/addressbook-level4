package guitests;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskType;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;


public class EditCommandTest extends TaskManagerGuiTest{
	
	@Test
	public void edit() {
		//edit the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        String name = "Aid hoon";
        assertEditSuccess(targetIndex, name, currentList);

        //edit the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertEditSuccess(targetIndex, name, currentList);

        //edit from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertEditSuccess(targetIndex, name, currentList);

        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
	}
	

    /**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to edit the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before editing).
     */
    private void assertEditSuccess(int targetIndexOneIndexed, String name, final TestTask[] currentList) {
        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask postEdit = td.hoon;

        commandBox.runCommand("edit " + targetIndexOneIndexed + " " + "'Aid hoon'");

        //TODO: uncomment after Optional.empty() error solved
        //confirm the new card contains the right data
        //TaskCardHandle EditedCard = taskListPanel.navigateToTask(postEdit.getName().fullName);
        //assertMatching(postEdit, EditedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.replaceTaskFromList(currentList, postEdit, targetIndexOneIndexed-1);
        System.out.println("expectedList[0]: " + expectedList[0]);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }


}
