
package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@@author A0148145E

public class UpDownButtonTest extends TaskSchedulerGuiTest {

    @Test
    public void buttonTest() {
        
        String rubbishCommand = "23r23r23534423";
        commandBox.runCommand(td.hoon.getAddCommand());

        //retrieve previous entered command
        commandBox.pressUp();
        assertEquals(commandBox.getCommandInput(), td.hoon.getAddCommand()); 
        
        commandBox.runCommand(rubbishCommand);
        
        //retrieve previous entered rubbish command
        commandBox.pressUp();
        assertEquals(commandBox.getCommandInput(), rubbishCommand); 
        
        //holds at first ever command
        for (int i = 0; i < 3; i++) {
            commandBox.pressUp();
        }
        assertEquals(commandBox.getCommandInput(), td.hoon.getAddCommand()); 
        
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