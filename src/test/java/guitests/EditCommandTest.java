package guitests;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.SomedayTaskCardHandle;
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
        assertEditSuccess(targetIndex, "typical", currentList);

        //edit the last in the list
        targetIndex = currentList.length;
        assertEditSuccess(targetIndex, "typical", currentList);

        //edit from the middle of the list
        targetIndex = currentList.length/2;
        assertEditSuccess(targetIndex, "typical", currentList);
        
        //edit a someday task from the list
        TestTask[] somedayList = td.getSomedayTasks();
        int targetIndex = ?; //TO-DO: index must not one of the above three
        assertEditSuccess(targetIndex, "typical", currentList);
        assertEditSuccess(targetIndex, "someday", somedayList);
        
        //edit a deadline task from the list
        int targetIndex = ?; //TO-DO: index must not one of the above three
        assertEditSuccess(targetIndex, "typical", currentList);
        
        //edit an event task from the list
        int targetIndex = ?; //TO-DO: index must not one of the above three
        assertEditSuccess(targetIndex, "typical", currentList);
        
        //edit a today task from the list
        TestTask[] todayList = td.getTodayTasks();
        int targetIndex = ?; //TO-DO: index must not one of the above three
        assertEditSuccess(targetIndex, "today", todayList);
        
        //edit a tomorrow task from the list
        TestTask[] tomorrowList = td.getTomorrowTasks();
        int targetIndex = ?; //TO-DO: index must not one of the above three
        assertEditSuccess(targetIndex, "tomorrow", tomorrowList);
        
        //edit an in-7-days task from the list
        TestTask[] in7DaysList = td.getIn7DaysTasks();
        int targetIndex = ?; //TO-DO: index must not one of the above three
        assertEditSuccess(targetIndex, "in 7 days", in7DaysList);
        
        //edit an in-30-days task from the list
        TestTask[] in30DaysList = td.getIn30DaysTasks();
        int targetIndex = ?; //TO-DO: index must not one of the above three
        assertEditSuccess(targetIndex, "in 30 days", in30DaysList);

        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
	}
	

    /**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to edit the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before editing).
     */
    private void assertEditSuccess(int targetIndexOneIndexed, TestTask postEdit, String listType, final TestTask[] currentList, TestTask...list) {
        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing

        commandBox.runCommand(postEdit.getEditCommand(targetIndexOneIndexed));

        //confirm the new card contains the right data
        if (postEdit.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
        	SomedayTaskCardHandle EditedCard = taskListPanel.navigateToSomedayTask(postEdit.getName().fullName);
        	assertSomedayTaskMatching(postEdit, EditedCard);
        } else if (postEdit.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
        	DeadlineTaskCardHandle EditedCard = taskListPanel.navigateToDeadlineTask(postEdit.getName().fullName);
        	assertDeadlineTaskMatching(postEdit, EditedCard);
        } else if (postEdit.getTaskType().value.equals(TaskType.Type.EVENT)) {
        	EventTaskCardHandle EditedCard = taskListPanel.navigateToEventTask(postEdit.getName().fullName);
        	assertEventTaskMatching(postEdit, EditedCard);
        } 

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.replaceTaskFromList(list, postEdit, targetIndexOneIndexed-1);
        System.out.println("expectedList[0]: " + expectedList[0]);
        switch (listType) {
        case "typical":
        	assertTrue(taskListPanel.isListMatching(expectedList));
        case "today":
            assertTrue(todayTaskListTabPanel.isListMatching(expectedList));
        case "tomorrow":
            assertTrue(tomorrowTaskListTabPanel.isListMatching(expectedList));
        case "in 7 days":
            assertTrue(in7DaysTaskListTabPanel.isListMatching(expectedList));
        case "in 30 days":
            assertTrue(in30DaysTaskListTabPanel.isListMatching(expectedList));
        case "someday":
            assertTrue(somedayTaskListTabPanel.isListMatching(expectedList));
        default:
        }
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }


}
