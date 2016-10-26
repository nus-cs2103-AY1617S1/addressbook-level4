package guitests;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.menion.commons.core.Messages;
import seedu.menion.logic.commands.AddCommand;
import seedu.menion.model.activity.Activity;
import seedu.menion.testutil.TestActivity;
import seedu.menion.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends ActivityManagerGuiTest {

    @Test
    public void add() {
    	
//    	commandBox.runCommand("clear");
        //add one task
        TestActivity[] taskList = td.getTypicalTask();
        TestActivity taskToAdd = td.task2;
        
        assertAddSuccess(taskToAdd, taskList);
        taskList = TestUtil.addActivitiesToList(taskList, taskToAdd);
        
        //@@author A0139515A
        //add floating task
        TestActivity[] floatingTaskList = td.getTypicalFloatingTask();
        TestActivity floatingTaskToAdd = td.floatingTask2;
        assertAddSuccess(floatingTaskToAdd, floatingTaskList);
        floatingTaskList = TestUtil.addActivitiesToList(floatingTaskList, floatingTaskToAdd);
         
        //add event
        TestActivity[] eventList = td.getTypicalEvent();
        TestActivity eventToAdd = td.event2;
        assertAddSuccess(eventToAdd, eventList);
        floatingTaskList = TestUtil.addActivitiesToList(eventList, eventToAdd);
    
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.task);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestActivity activityToAdd, TestActivity... currentList) {
        
        commandBox.runCommand(activityToAdd.getAddCommand());
        
        if (activityToAdd.getActivityType().equals(Activity.TASK_TYPE)) {
	        //confirm the new task card contains the right data
	        TaskCardHandle addedCard = activityListPanel.navigateToTask(activityToAdd.getActivityName().fullName);
	        assertTaskMatching(activityToAdd, addedCard);
	
	        //confirm the list now contains all previous activities plus the new activity
	        TestActivity[] expectedList = TestUtil.addActivitiesToList(currentList, activityToAdd);
	        assertTrue(activityListPanel.isTaskListMatching(expectedList));
        }
        else if (activityToAdd.getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
	        //confirm the new floating task card contains the right data
	        FloatingTaskCardHandle addedCard = activityListPanel.navigateToFloatingTask(activityToAdd.getActivityName().fullName);
	        assertFloatingTaskMatching(activityToAdd, addedCard);
	
	        //confirm the list now contains all previous activities plus the new activity
	        TestActivity[] expectedList = TestUtil.addActivitiesToList(currentList, activityToAdd);
	        assertTrue(activityListPanel.isFloatingTaskListMatching(expectedList));
        }
        else { 
	        //confirm the new event card contains the right data
	        EventCardHandle addedCard = activityListPanel.navigateToEvent(activityToAdd.getActivityName().fullName);
	        assertEventMatching(activityToAdd, addedCard);
	
	        //confirm the list now contains all previous activities plus the new activity
	        TestActivity[] expectedList = TestUtil.addActivitiesToList(currentList, activityToAdd);
	        assertTrue(activityListPanel.isEventListMatching(expectedList));
        }
    }

}