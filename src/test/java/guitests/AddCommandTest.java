//package guitests;
//
//import guitests.guihandles.DeadlineTaskCardHandle;
//import guitests.guihandles.EventTaskCardHandle;
//import guitests.guihandles.SomedayTaskCardHandle;
//import org.junit.Test;
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.model.task.TaskType;
//import seedu.address.commons.core.Messages;
//import seedu.address.testutil.TestTask;
//import seedu.address.testutil.TestUtil;
//import seedu.address.testutil.TypicalTestTasks;
//
//import static org.junit.Assert.assertTrue;
//
//public class AddCommandTest extends TaskManagerGuiTest {
//
//    @Test
//    public void add() {
//        //add one someday task
//        TestTask[] currentList = td.getTypicalTasks();
//        TestTask[] somedayList = td.getSomedayTasks();
//        TestTask taskToAdd = TypicalTestTasks.somedayAdd;
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "someday", somedayList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//
//        //add a deadline task that is a today task
//        TestTask[] todayList = td.getTodayTasks();
//        taskToAdd = TypicalTestTasks.deadlineTodayAdd;
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "today", todayList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add a deadline task that is an tomorrow task
//        TestTask[] tomorrowList = td.getTomorrowTasks();
//        taskToAdd = TypicalTestTasks.deadlineTomorrowAdd;
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "tomorrow", tomorrowList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//
//        //add an event task that is a in-7-day task
//        TestTask[] in7DaysList = td.getIn7DaysTasks();
//        taskToAdd = TypicalTestTasks.eventIn7DaysAdd;
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "in 7 days", in7DaysList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add an event task that is a in-30-days task
//        TestTask[] in30DaysList = td.getIn30DaysTasks();
//        taskToAdd = TypicalTestTasks.eventIn30DaysAdd;
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "in 30 days", in30DaysList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        
//        //add duplicate someday task
//        commandBox.runCommand(TypicalTestTasks.somedayAdd.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//        
//        //add duplicate deadline task
//        commandBox.runCommand(TypicalTestTasks.deadlineTodayAdd.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//        
//        //add duplicate event task
//        commandBox.runCommand(TypicalTestTasks.eventIn7DaysAdd.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//
//        //add to empty list
//        commandBox.runCommand("clear");
//        assertAddSuccess(TypicalTestTasks.somedayAdd, "typical");
//
//        //invalid command
//        commandBox.runCommand("adds Johnny");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }
//
//	private void assertAddSuccess(TestTask taskToAdd, String listType, TestTask... list) {
//        commandBox.runCommand(taskToAdd.getAddCommand());
//
//        //confirm the new card contains the right data
//        if (taskToAdd.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
//        	SomedayTaskCardHandle addedCard = taskListPanel.navigateToSomedayTask(taskToAdd.getName().fullName);
//        	assertSomedayTaskMatching(taskToAdd, addedCard);
//        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
//        	DeadlineTaskCardHandle addedCard = taskListPanel.navigateToDeadlineTask(taskToAdd.getName().fullName);
//        	assertDeadlineTaskMatching(taskToAdd, addedCard);
//        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.EVENT)) {
//        	EventTaskCardHandle addedCard = taskListPanel.navigateToEventTask(taskToAdd.getName().fullName);
//        	assertEventTaskMatching(taskToAdd, addedCard);
//        } 
//
//        //confirm the list now contains all previous tasks plus the new task
//        TestTask[] expectedList = TestUtil.addTasksToList(list, taskToAdd);
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
//	}
//}
