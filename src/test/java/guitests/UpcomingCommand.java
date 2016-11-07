package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import harmony.mastermind.testutil.TestTask;
import harmony.mastermind.testutil.TypicalTestTasks;

public class UpcomingCommand extends TaskManagerGuiTest {

    //@@author A0124797R
    @Test
    public void upcoming_dueTasks_emptyList() {
        //remove floating tasks
        commandBox.runCommand("delete 3");
        commandBox.runCommand("delete 3");
        TestTask[] emptyTask = new TestTask[]{};
        
        assertUpcomingSuccess("", emptyTask);
        
        
    }
    
    @Test
    public void upcoming_deadline_listDeadlines() {
        TestTask[] expectedList = new TestTask[] {TypicalTestTasks.task11};
        commandBox.runCommand(TypicalTestTasks.task11.getAddCommand());
        
        assertUpcomingSuccess(" deadlines", expectedList);
        
        
    }
    
    @Test
    public void upcoming_event_listEvents() {
        TestTask[] expectedList = new TestTask[] {TypicalTestTasks.task12};
        commandBox.runCommand(TypicalTestTasks.task12.getAddCommand());
        
        assertUpcomingSuccess(" events", expectedList);
        
        
    }
    
    
    private void assertUpcomingSuccess(String args, final TestTask[] expectedList) {
        commandBox.runCommand("upcoming" + args);
        
        assertTrue(taskListPanel.isListMatching(expectedList));
        
    }
}
