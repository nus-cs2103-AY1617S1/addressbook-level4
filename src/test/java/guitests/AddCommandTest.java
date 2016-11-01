package guitests;

import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.SomedayTaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.task.TaskType;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //Adds a someday task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] somedayList = td.getSomedayTasks();
        TestTask taskToAdd = TypicalTestTasks.somedayAdd;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAddSuccess(taskToAdd, "typical", currentList);
        assertAddSuccess(taskToAdd, "someday", somedayList);
        
        //Adds a deadline task that is a today task
        TestTask[] todayList = td.getTodayTasks();
        taskToAdd = TypicalTestTasks.deadlineTodayAdd;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAddSuccess(taskToAdd, "typical", currentList);
        assertAddSuccess(taskToAdd, "today", todayList);
       
        //Adds a deadline task that is an tomorrow task
        TestTask[] tomorrowList = td.getTomorrowTasks();
        taskToAdd = TypicalTestTasks.deadlineTomorrowAdd;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAddSuccess(taskToAdd, "typical", currentList);
        assertAddSuccess(taskToAdd, "tomorrow", tomorrowList);

        //Adds an event task that is a in-7-day task
        TestTask[] in7DaysList = td.getIn7DaysTasks();
        taskToAdd = TypicalTestTasks.eventIn7DaysAdd;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAddSuccess(taskToAdd, "typical", currentList);
        assertAddSuccess(taskToAdd, "in 7 days", in7DaysList);
        
        //Adds an event task that is a in-30-days task
        TestTask[] in30DaysList = td.getIn30DaysTasks();
        taskToAdd = TypicalTestTasks.eventIn30DaysAdd;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAddSuccess(taskToAdd, "typical", currentList);
        assertAddSuccess(taskToAdd, "in 30 days", in30DaysList);
        
        //Adds a duplicate someday task
        commandBox.runCommand(TypicalTestTasks.somedayAdd.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //Adds a duplicate deadline task
        commandBox.runCommand(TypicalTestTasks.deadlineTodayAdd.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //Adds a duplicate event task
        commandBox.runCommand(TypicalTestTasks.eventIn7DaysAdd.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //Adds an event task that has startDateTime after endDateTime 
        commandBox.runCommand(TypicalTestTasks.eventStartDateTimeAfterEndDateTime.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_START_DATE_TIME_AFTER_END_DATE_TIME);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //Adds an event task that has identical startDateTime and endDateTime
        commandBox.runCommand(TypicalTestTasks.eventStartDateTimeEqualsEndDateTime.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_START_DATE_TIME_EQUALS_END_DATE_TIME);
        assertTrue(taskListPanel.isListMatching(currentList));
	
        //Adds to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.somedayAdd, "typical");

        //Invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

	private void assertAddSuccess(TestTask taskToAdd, String listType, TestTask... list) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //Confirms that the new task card contains the right data
        if (taskToAdd.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
        	SomedayTaskCardHandle addedCard = taskListPanel.navigateToSomedayTask(taskToAdd.getName().value);
        	assertSomedayTaskMatching(taskToAdd, addedCard);
        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
        	DeadlineTaskCardHandle addedCard = taskListPanel.navigateToDeadlineTask(taskToAdd.getName().value);
        	assertDeadlineTaskMatching(taskToAdd, addedCard);
        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.EVENT)) {
        	EventTaskCardHandle addedCard = taskListPanel.navigateToEventTask(taskToAdd.getName().value);
        	assertEventTaskMatching(taskToAdd, addedCard);
        } 

        //Confirms that the list now contains all previous tasks and the new task
        TestTask[] expectedList = TestUtil.addTasksToList(list, taskToAdd);
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
        
        //Confirms that the result message is correct
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
	}
}
