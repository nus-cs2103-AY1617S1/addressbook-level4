package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TypicalTestEvents;
import seedu.task.testutil.TypicalTestTasks;
import seedu.task.testutil.TestEvent;

import seedu.taskcommons.core.Messages;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;

public class FindCommandTest extends TaskBookGuiTest {


	@Test
    public void find_nonEmptyList() {
		//Tasks only
        assertFindResultTask("find cs2010", 0, 0); //no results
        
        assertFindResultTask("find cs1010", 1, 0, TypicalTestTasks.cs1010); 
        
        assertFindResultTask("find Lecture 7", 2, 0, TypicalTestTasks.cs1010, TypicalTestTasks.cs1020); //multiple tasks result
        
        //Events only
        assertFindResultEvent("find random", 0, 0); //no results
        
        assertFindResultEvent("find discussion", 0, 1, TypicalTestEvents.meeting3);
        
        assertFindResultEvent("find cs2103t", 0, 2, TypicalTestEvents.meeting1, TypicalTestEvents.meeting2); // two events
        
        //Both events and tasks
        assertFindResultTask("find project", 2, 2, TypicalTestTasks.engine, TypicalTestTasks.music);
        assertFindResultEvent("find project", 2, 2, TypicalTestEvents.meeting1, TypicalTestEvents.meeting2);
        
        //find after deleting one result
        commandBox.runCommand("delete /t 1");
        assertFindResultTask("find my part", 1, 0, TypicalTestTasks.music);
    }

    
    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear /a");
        assertFindResultTask("find cs1010", 0, 0); //no results
    }


    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findcs1010");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResultTask(String command,int tasksSize, int eventsSize, TestTask...expectedTasks) {
        commandBox.runCommand(command);
        
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, tasksSize) + "\n" 
                + String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, eventsSize));
        
        assertTaskListSize(expectedTasks.length);
        assertTrue(taskListPanel.isListMatching(expectedTasks));
    }
    
    private void assertFindResultEvent(String command, int tasksSize, int eventsSize, TestEvent...expectedEvents) {

    	commandBox.runCommand(command);
        
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, tasksSize) + "\n" 
                + String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, eventsSize));
        
        assertEventListSize(expectedEvents.length);
        assertTrue(eventListPanel.isListMatching(expectedEvents));
    }
}
