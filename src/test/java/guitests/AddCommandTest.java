package guitests;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.address.testutil.TestActivity;
import seedu.address.testutil.TestUtil;
import seedu.menion.commons.core.Messages;
import seedu.menion.logic.commands.AddCommand;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends ActivityManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestActivity[] taskList = td.getTypicalTask();
        TestActivity taskToAdd = td.task;
        
        assertAddSuccess(taskToAdd, taskList);
        taskList = TestUtil.addTasksToList(taskList, taskToAdd);
        
        //add floating task
        TestActivity[] floatingTaskList = td.getTypicalFloatingTask();
        TestActivity floatingTaskToAdd = td.floatingTask;
        assertAddSuccess(floatingTaskToAdd, floatingTaskList);
        floatingTaskList = TestUtil.addTasksToList(floatingTaskList, floatingTaskToAdd);
         
        //add event
        TestActivity[] eventList = td.getTypicalEvent();
        TestActivity eventToAdd = td.event;
        assertAddSuccess(eventToAdd, eventList);
        floatingTaskList = TestUtil.addTasksToList(eventList, eventToAdd);
    
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.task);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestActivity activityToAdd, TestActivity... currentList) {
        
        commandBox.runCommand(activityToAdd.getAddCommand());
        
        if (activityToAdd.getActivityType().equals("task")) {
	        //confirm the new task card contains the right data
	        TaskCardHandle addedCard = activityListPanel.navigateToTask(activityToAdd.getActivityName().fullName);
	        assertTaskMatching(activityToAdd, addedCard);
	
	        //confirm the list now contains all previous activities plus the new activity
	        TestActivity[] expectedList = TestUtil.addTasksToList(currentList, activityToAdd);
	        assertTrue(activityListPanel.isTaskListMatching(expectedList));
        }
        else if (activityToAdd.getActivityType().equals("floatingTask")) {
	        //confirm the new floating task card contains the right data
	        FloatingTaskCardHandle addedCard = activityListPanel.navigateToFloatingTask(activityToAdd.getActivityName().fullName);
	        assertFloatingTaskMatching(activityToAdd, addedCard);
	
	        //confirm the list now contains all previous activities plus the new activity
	        TestActivity[] expectedList = TestUtil.addFloatingTasksToList(currentList, activityToAdd);
	        assertTrue(activityListPanel.isFloatingTaskListMatching(expectedList));
        }
        else { 
	        //confirm the new event card contains the right data
	        EventCardHandle addedCard = activityListPanel.navigateToEvent(activityToAdd.getActivityName().fullName);
	        assertEventMatching(activityToAdd, addedCard);
	
	        //confirm the list now contains all previous activities plus the new activity
	        TestActivity[] expectedList = TestUtil.addEventsToList(currentList, activityToAdd);
	        assertTrue(activityListPanel.isEventListMatching(expectedList));
        }
    }

}