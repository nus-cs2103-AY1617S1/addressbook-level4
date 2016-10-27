package guitests;

import org.junit.Test;

import seedu.task.testutil.TypicalTestEvents;
import seedu.task.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

/**
 * Tests Clear Command for GUI Test.
 * @@author A0121608N
 */

public class ClearCommandTest extends TaskBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearAllCommandSuccess();
        
        //verify clearing of completed tasks and events for non-empty list
        commandBox.runCommand(TypicalTestTasks.arts.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.science.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.engine.getAddCommand());
        commandBox.runCommand("mark 1");
        commandBox.runCommand(TypicalTestEvents.meeting1.getAddCommand());
        commandBox.runCommand(TypicalTestEvents.meeting2.getAddCommand());
        commandBox.runCommand(TypicalTestEvents.meeting3.getAddCommand());
        assertClearAllCompletedCommandSuccess();
        commandBox.runCommand("clear /a");
        
        //verify clearing of completed tasks for non-empty list
        commandBox.runCommand(TypicalTestTasks.arts.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.science.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.engine.getAddCommand());
        commandBox.runCommand("mark 1");
        assertClearCompletedTasksCommandSuccess();
        commandBox.runCommand("clear /a");
        
        //verify clearing of all tasks for non-empty list
        commandBox.runCommand(TypicalTestTasks.arts.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.science.getAddCommand());
        commandBox.runCommand("mark 1");
        assertClearAllTasksCommandSuccess();
        
        //verify clearing of completed events for non-empty list
        commandBox.runCommand(TypicalTestEvents.meeting1.getAddCommand());
        commandBox.runCommand(TypicalTestEvents.meeting2.getAddCommand());
        commandBox.runCommand(TypicalTestEvents.meeting3.getAddCommand());
        assertClearCompletedEventsCommandSuccess();
        commandBox.runCommand("clear /a");
        
        //verify clearing of all events for non-empty list
        commandBox.runCommand(TypicalTestEvents.meeting1.getAddCommand());
        commandBox.runCommand(TypicalTestEvents.meeting2.getAddCommand());
        assertClearAllEventsCommandSuccess();
        commandBox.runCommand("clear /a");

        //verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.arts.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.arts));
        commandBox.runCommand("delete /t 1");
        assertTaskListSize(0);

        //verify clear command works when the list is empty
        assertClearAllCommandSuccess();
    }

    private void assertClearAllCommandSuccess() {
        commandBox.runCommand("clear /a");
        assertTaskListSize(0);
        assertEventListSize(0);
        assertResultMessage("All completed and uncompleted tasks and events has been cleared!");
    }
    
    private void assertClearAllCompletedCommandSuccess() {
        commandBox.runCommand("clear");
        assertTaskListSize(2);
        assertEventListSize(2);
        assertResultMessage("All completed tasks and events has been cleared!");
    }
    
    private void assertClearCompletedTasksCommandSuccess() {
        commandBox.runCommand("clear /t");
        assertTaskListSize(2);
        assertEventListSize(0);
        assertResultMessage("All completed tasks has been cleared!");
    }
    
    private void assertClearCompletedEventsCommandSuccess() {
        commandBox.runCommand("clear /e");
        assertTaskListSize(0);
        assertEventListSize(2);
        assertResultMessage("All completed events has been cleared!");
    }
    
    private void assertClearAllTasksCommandSuccess() {
        commandBox.runCommand("clear /t /a");
        assertTaskListSize(0);
        assertEventListSize(0);
        assertResultMessage("All completed and uncompleted tasks has been cleared!");
    }
    
    private void assertClearAllEventsCommandSuccess() {
        commandBox.runCommand("clear /e /a");
        assertTaskListSize(0);
        assertEventListSize(0);
        assertResultMessage("All completed and uncompleted events has been cleared!");
    }
}
