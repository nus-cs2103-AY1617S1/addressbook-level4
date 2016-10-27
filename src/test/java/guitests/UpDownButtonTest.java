
package guitests;

import org.junit.Test;

import seedu.taskscheduler.logic.commands.CommandHistory;

import static org.junit.Assert.assertEquals;

//@@author A0148145E

public class UpDownButtonTest extends TaskSchedulerGuiTest {

    @Test
    public void buttonTest() {
        
        CommandHistory.flushNextCommands();
        CommandHistory.flushPrevCommands();
        
        String rubbishCommand = "23r23r23534423";
        commandBox.runCommand(td.event.getAddCommand());

        //retrieve previous entered command
        commandBox.pressUp();
        assertEquals(commandBox.getCommandInput(), td.event.getAddCommand()); 
        
        commandBox.runCommand(rubbishCommand);
        
        //retrieve previous entered rubbish command
        commandBox.pressUp();
        assertEquals(commandBox.getCommandInput(), rubbishCommand); 
        
        //holds at first ever command
        for (int i = 0; i < 3; i++) {
            commandBox.pressUp();
        }
        assertEquals(commandBox.getCommandInput(), td.event.getAddCommand()); 
        
        //retrieve the entered rubbish command
        commandBox.pressDown();
        assertEquals(commandBox.getCommandInput(), rubbishCommand); 
        
        //goes back to empty
        for (int i = 0; i < 3; i++) {
            commandBox.pressDown();
        }
        assertEquals(commandBox.getCommandInput(),""); 
        
        //goes back to the entered rubbish command
        commandBox.pressUp();
        assertEquals(commandBox.getCommandInput(), rubbishCommand); 
    }

}