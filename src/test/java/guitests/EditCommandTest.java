package guitests;
//
//import static org.junit.Assert.*;
//import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import org.junit.Test;
//
//import guitests.guihandles.DeadlineTaskCardHandle;
//import guitests.guihandles.EventTaskCardHandle;
//import guitests.guihandles.SomedayTaskCardHandle;
//import guitests.guihandles.TaskCardHandle;
//import seedu.address.model.tag.UniqueTagList;
//import seedu.address.model.task.Task;
//import seedu.address.model.task.TaskType;
//import seedu.address.testutil.TestTask;
//import seedu.address.testutil.TestUtil;
//import seedu.address.testutil.TypicalTestTasks;
//
//
public class EditCommandTest extends TaskManagerGuiTest{
//	
//	@Test
//	public void edit() {
//		//edit the first in the list
//        TestTask[] currentList = td.getTypicalTasks();
//        TestTask postEdit = TypicalTestTasks.somedayAdd;
//        int targetIndex = 1;
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList, currentList);
//
//        //edit the last in the list
//        postEdit = TypicalTestTasks.eventIn7DaysAdd;
//        targetIndex = currentList.length;
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList, currentList);
//
//        //edit from the middle of the list
//        postEdit = TypicalTestTasks.deadlineTodayAdd;
//        targetIndex = currentList.length/2;
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList, currentList);
//        
//        //edit a someday task from the list
//        TestTask[] somedayList = td.getSomedayTasks();
//        postEdit = TypicalTestTasks.sdAdd;
//        targetIndex = 6; 
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList, currentList);
//        assertEditSuccess(targetIndex, postEdit, "someday", currentList, somedayList);
//        
//        //edit a deadline task from the list
//        postEdit = TypicalTestTasks.deadlineTomorrowAdd;
//        targetIndex = 3;
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList, currentList);
//        
//        //edit an event task from the list
//        postEdit = TypicalTestTasks.eventIn30DaysAdd;
//        targetIndex = 12;
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList, currentList);
//        
//        //edit a today task from the list
//        TestTask[] todayList = td.getTodayTasks();
//        postEdit = TypicalTestTasks.eventTodayAdd;
//        targetIndex = 9;
//        assertEditSuccess(targetIndex, postEdit, "today", currentList, todayList);
//        
//        //edit a tomorrow task from the list
//        TestTask[] tomorrowList = td.getTomorrowTasks();
//        postEdit = TypicalTestTasks.eventTomorrowAdd;
//        targetIndex = 10;        
//        assertEditSuccess(targetIndex, postEdit, "tomorrow", currentList, tomorrowList);
//        
//        //edit an in-7-days task from the list
//        TestTask[] in7DaysList = td.getIn7DaysTasks();
//        postEdit = TypicalTestTasks.deadlineIn7DaysAdd;
//        targetIndex = 4;
//        assertEditSuccess(targetIndex, postEdit, "in 7 days", currentList, in7DaysList);
//        
//        //edit an in-30-days task from the list
//        TestTask[] in30DaysList = td.getIn30DaysTasks();
//        postEdit = TypicalTestTasks.deadlineIn30DaysAdd;
//        targetIndex = 5;
//        assertEditSuccess(targetIndex, postEdit, "in 30 days", currentList, in30DaysList);
//
//        //invalid index
//        commandBox.runCommand("edit " + currentList.length + 1);
//        assertResultMessage("The task index provided is invalid");
//	}
//	
//
//    /**
//     * Runs the edit command to edit the task at specified index and confirms the result is correct.
//     * @param targetIndexOneIndexed e.g. to edit the first task in the list, 1 should be given as the target index.
//     * @param currentList A copy of the current list of tasks (before editing).
//     */
//    private void assertEditSuccess(int targetIndexOneIndexed, TestTask postEdit, String listType, final TestTask[] currentList, TestTask... list) {
//        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
//
//        commandBox.runCommand(postEdit.getEditCommand(targetIndexOneIndexed));
//
//        //confirm the new card contains the right data
//        if (postEdit.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
//        	SomedayTaskCardHandle EditedCard = taskListPanel.navigateToSomedayTask(postEdit.getName().fullName);
//        	assertSomedayTaskMatching(postEdit, EditedCard);
//        } else if (postEdit.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
//        	DeadlineTaskCardHandle EditedCard = taskListPanel.navigateToDeadlineTask(postEdit.getName().fullName);
//        	assertDeadlineTaskMatching(postEdit, EditedCard);
//        } else if (postEdit.getTaskType().value.equals(TaskType.Type.EVENT)) {
//        	EventTaskCardHandle EditedCard = taskListPanel.navigateToEventTask(postEdit.getName().fullName);
//        	assertEventTaskMatching(postEdit, EditedCard);
//        } 
//
//        //confirm the list now contains all previous tasks plus the new task
//        TestTask[] expectedList = TestUtil.replaceTaskFromList(list, postEdit, targetIndexOneIndexed-1);
//        System.out.println("expectedList[0]: " + expectedList[0]);
//        switch (listType) {
//        case "typical":
//        	assertTrue(taskListPanel.isListMatching(expectedList));
//        case "today":
//            assertTrue(todayTaskListTabPanel.isListMatching(expectedList));
//        case "tomorrow":
//            assertTrue(tomorrowTaskListTabPanel.isListMatching(expectedList));
//        case "in 7 days":
//            assertTrue(in7DaysTaskListTabPanel.isListMatching(expectedList));
//        case "in 30 days":
//            assertTrue(in30DaysTaskListTabPanel.isListMatching(expectedList));
//        case "someday":
//            assertTrue(somedayTaskListTabPanel.isListMatching(expectedList));
//        default:
//        }
//        
//        //confirm the result message is correct
//        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
//    }
//
//
}
