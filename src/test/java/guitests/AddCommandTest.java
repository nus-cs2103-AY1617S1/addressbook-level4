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

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one someday task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] somedayList = td.getSomedayTasks();
        TestTask taskToAdd1 = td.hoon;
        assertAddSuccess(taskToAdd1, "typical", currentList);
        assertAddSuccess(taskToAdd1, "someday", somedayList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd1);

        //add a deadline task that is a today task
        TestTask[] todayList = td.getTodayTasks();
        TestTask taskToAdd2 = td.;//TODO
        assertAddSuccess(taskToAdd2, "typical", currentList);
        assertAddSuccess(taskToAdd2, "today", todayList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd2);
        
        //add a deadline task that is an tomorrow task
        TestTask[] tomorrowList = td.getTomorrowTasks();
        TestTask taskToAdd3 = td.;//TODO
        assertAddSuccess(taskToAdd3, "typical", currentList);
        assertAddSuccess(taskToAdd3, "tomorrow", tomorrowList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd3);

        //add an event task that is a in-7-day task
        TestTask[] in7DaysList = td.getIn7DaysTasks();
        TestTask taskToAdd4 = td.;//TODO
        assertAddSuccess(taskToAdd4, "typical", currentList);
        assertAddSuccess(taskToAdd4, "in 7 days", in7DaysList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd4);
        
        //add an event task that is a in-30-days task
        TestTask[] in30DaysList = td.getIn30DaysTasks();
        TestTask taskToAdd5 = td.;//TODO
        assertAddSuccess(taskToAdd5, "typical", currentList);
        assertAddSuccess(taskToAdd5, "in 30 days", in30DaysList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd5);
        
        //add duplicate someday task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add duplicate deadline task
        commandBox.runCommand(td..getAddCommand()); //TODO
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add duplicate event task
        commandBox.runCommand(td..getAddCommand()); //TODO
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice, "typical");

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

	private void assertAddSuccess(TestTask taskToAdd, String listType, TestTask... list) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        if (taskToAdd.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
        	SomedayTaskCardHandle addedCard = taskListPanel.navigateToSomedayTask(taskToAdd.getName().fullName);
        	assertSomedayTaskMatching(taskToAdd, addedCard);
        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
        	DeadlineTaskCardHandle addedCard = taskListPanel.navigateToDeadlineTask(taskToAdd.getName().fullName);
        	assertDeadlineTaskMatching(taskToAdd, addedCard);
        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.EVENT)) {
        	EventTaskCardHandle addedCard = taskListPanel.navigateToEventTask(taskToAdd.getName().fullName);
        	assertEventTaskMatching(taskToAdd, addedCard);
        } 

        //confirm the list now contains all previous tasks plus the new task
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
	}
}
