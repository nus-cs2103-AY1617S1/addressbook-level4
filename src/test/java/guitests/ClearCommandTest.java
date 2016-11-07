package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TypicalTestTasks;

/**
 * @@author A0121608N
 * Tests Clear Command for GUI Test.
 * 
 */

public class ClearCommandTest extends TaskBookGuiTest {

    @Test
    //verify clearing of completed tasks and events for non-empty list can be cleared
    public void clearAll_nonEmptyList_success(){
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearAllCommandSuccess();
    }
    
    @Test
    //verify clearing of completed tasks and events for non-empty list
    public void clearCompletedTasksAndEvents_nonEmptyList_success(){
        commandBox.runCommand("mark 1");
        commandBox.runCommand("mark 1");
        assertClearAllCompletedCommandSuccess();
    }
    
    @Test
    //verify clearing of completed tasks for non-empty list
    public void clearCompletedTasks_nonEmptyList_success(){
        commandBox.runCommand("mark 1");
        assertClearCompletedTasksCommandSuccess();
    }
    
    @Test
    //verify clearing of all tasks for non-empty list
    public void clearAllTasks_nonEmptyList_success(){
        commandBox.runCommand("mark 1");
        assertClearAllTasksCommandSuccess();
    }
    
    @Test
    //verify clearing of completed events for non-empty list
    public void clearCompletedEvents_nonEmptyList_success(){
        assertClearCompletedEventsCommandSuccess();
    }
    
    @Test
    //verify clearing of all events for non-empty list
    public void clearAllEvents_nonEmptyList_success(){
        assertClearAllEventsCommandSuccess();
    }
    
    @Test
    //verify other commands can work after a clear command
    public void clearAll_newCommandInput_success(){
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearAllCommandSuccess();

        commandBox.runCommand(TypicalTestTasks.arts.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.arts));
        commandBox.runCommand("delete /t 1");
        assertTaskListSize(0);
    }
    
    
    @Test
    //verify that the various clear commands work when the list is empty
    public void clear_emptyList_success() {
        assertClearAllCommandSuccess();
        
        commandBox.runCommand("clear");
        assertTaskListSize(0);
        assertEventListSize(0);
        assertResultMessage("All completed tasks and events has been cleared!");

        commandBox.runCommand("clear /t");
        assertTaskListSize(0);
        assertEventListSize(0);
        assertResultMessage("All completed tasks has been cleared!");

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
        assertTaskListSize(3);
        assertEventListSize(2);
        assertResultMessage("All completed tasks has been cleared!");
    }
    
    private void assertClearCompletedEventsCommandSuccess() {
        commandBox.runCommand("clear /e");
        assertTaskListSize(4);
        assertEventListSize(2);
        assertResultMessage("All completed events has been cleared!");
    }
    
    private void assertClearAllTasksCommandSuccess() {
        commandBox.runCommand("clear /t /a");
        assertTaskListSize(0);
        assertEventListSize(2);
        assertResultMessage("All completed and uncompleted tasks has been cleared!");
    }
    
    private void assertClearAllEventsCommandSuccess() {
        commandBox.runCommand("clear /e /a");
        assertTaskListSize(4);
        assertEventListSize(0);
        assertResultMessage("All completed and uncompleted events has been cleared!");
    }
}
