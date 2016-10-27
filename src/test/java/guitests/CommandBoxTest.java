package guitests;

import org.junit.Test;

import javafx.scene.input.KeyCode;

import static org.junit.Assert.assertEquals;

/*
 * GUI test for CommandBox
 */

public class CommandBoxTest extends TaskBookGuiTest {
    
    // test input for valid command
    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.cs1020.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    // test input for invalid command
    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
    }
    
    //@@author A0121608N
    // test for DELETE key press
    @Test
    public void commandBox_del_keyPress(){
        commandBox.enterCommand("please delete this");
        commandBox.keyPress(KeyCode.DELETE);
        assertEquals(commandBox.getCommandInput(), "");
    }

    
    // test for UP key press
    @Test
    public void commandBox_UP_keyPress(){
        commandBox.runCommand(td.cs1020.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
        commandBox.keyPress(KeyCode.UP);
        assertEquals(commandBox.getCommandInput(), td.cs1020.getAddCommand());
    }
    
 // test for DOWN key press
    @Test
    public void commandBox_DOWN_keyPress(){
        commandBox.runCommand(td.cs1020.getAddCommand());
        commandBox.runCommand(td.engine.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
        commandBox.keyPress(KeyCode.UP);
        commandBox.keyPress(KeyCode.UP);
        assertEquals(commandBox.getCommandInput(), td.cs1020.getAddCommand());
        commandBox.keyPress(KeyCode.DOWN);
        assertEquals(commandBox.getCommandInput(), td.engine.getAddCommand());
    }
    //@@author
}
