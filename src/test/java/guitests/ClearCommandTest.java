package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
//@@author A0141006B
public class ClearCommandTest extends ToDoListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.grocery.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.grocery));
        
        runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }
    
    //run commands other than add
    private void runCommand(String input){
    	commandBox.runCommand(input);
    }

    private void assertClearCommandSuccess() {
        runCommand("clear");
        assertListSize(0);
    }
}
