package guitests;
//
//import org.junit.Test;
//import seedu.address.testutil.TestTask;
//import seedu.address.testutil.TestUtil;
//
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
//
public class DeleteCommandTest extends TaskManagerGuiTest {
//
//    @Test
//    public void delete() {
//
//        //delete the first in the list
//        TestTask[] currentList = td.getTypicalTasks();
//        int targetIndex = 1;
//        assertDeleteSuccess(targetIndex, "typical", currentList);
//
//        //delete the last in the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        targetIndex = currentList.length;
//        assertDeleteSuccess(targetIndex, "typical", currentList);
//
//        //delete from the middle of the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        targetIndex = currentList.length/2;
//        assertDeleteSuccess(targetIndex, "typical", currentList);
//
//        //delete a someday task from the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        TestTask[] somedayList = td.getSomedayTasks();
//        targetIndex = 5; 
//        assertDeleteSuccess(targetIndex, "typical", currentList);
//        assertDeleteSuccess(targetIndex, "someday", somedayList);
//        
//        //delete a deadline task from the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        targetIndex = 5; 
//        assertDeleteSuccess(targetIndex, "typical", currentList);
//        
//        //delete an event task from the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        targetIndex = 10; 
//        assertDeleteSuccess(targetIndex, "typical", currentList);
//        
//        //delete a today task from the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        TestTask[] todayList = td.getTodayTasks();
//        targetIndex = 1;
//        assertDeleteSuccess(targetIndex, "today", todayList);
//        
//        //delete a tomorrow task from the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        TestTask[] tomorrowList = td.getTomorrowTasks();
//        targetIndex = 1; 
//        assertDeleteSuccess(targetIndex, "tomorrow", tomorrowList);
//        
//        //delete an in-7-days task from the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        TestTask[] in7DaysList = td.getIn7DaysTasks();
//        targetIndex = 5; 
//        assertDeleteSuccess(targetIndex, "in 7 days", in7DaysList);
//        
//        //delete an in-30-days task from the list
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        TestTask[] in30DaysList = td.getIn30DaysTasks();
//        targetIndex = 5; 
//        assertDeleteSuccess(targetIndex, "in 30 days", in30DaysList);
//        
//        //invalid index
//        commandBox.runCommand("del " + currentList.length + 1);
//        assertResultMessage("The task index provided is invalid");
//
//    }
//
//    /**
//     * Runs the delete command to delete the task at specified index and confirms the result is correct.
//     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
//     * @param currentList A copy of the current list of tasks (before deletion).
//     */
//    private void assertDeleteSuccess(int targetIndexOneIndexed, String listType, final TestTask[] currentList) {
//        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
//        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
//
//        commandBox.runCommand("del " + targetIndexOneIndexed);
//
//        //confirm the list now contains all previous tasks except the deleted task
//        switch (listType) {
//        case "typical":
//        	assertTrue(taskListPanel.isListMatching(expectedRemainder));
//        case "today":
//            assertTrue(todayTaskListTabPanel.isListMatching(expectedRemainder));
//        case "tomorrow":
//            assertTrue(tomorrowTaskListTabPanel.isListMatching(expectedRemainder));
//        case "in 7 days":
//            assertTrue(in7DaysTaskListTabPanel.isListMatching(expectedRemainder));
//        case "in 30 days":
//            assertTrue(in30DaysTaskListTabPanel.isListMatching(expectedRemainder));
//        case "someday":
//            assertTrue(somedayTaskListTabPanel.isListMatching(expectedRemainder));
//        default:
//        }
//
//        //confirm the result message is correct
//        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
//    }
//
}