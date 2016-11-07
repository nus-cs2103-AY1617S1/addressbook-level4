package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.task.testutil.TypicalTestTasks;

/*
 * GUI test for CommandBox
 */

public class CommandBoxTest extends TaskBookGuiTest {
    
    // test input for valid command
    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(TypicalTestTasks.cs1020.getAddCommand());
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
    public void deleteKeyPress_success(){
        commandBox.enterCommand("please delete this");
        commandBox.keyPress(KeyCode.DELETE);
        assertEquals(commandBox.getCommandInput(), "");
    }

    
    // test for UP key press
    @Test
    public void upKeyPress_success(){
        commandBox.runCommand(TypicalTestTasks.cs1020.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
        commandBox.keyPress(KeyCode.UP);
        assertEquals(commandBox.getCommandInput(), TypicalTestTasks.cs1020.getAddCommand());
    }
    
    // test for DOWN key press
    @Test
    public void downKeyPress_success(){
        commandBox.runCommand(TypicalTestTasks.cs1020.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.engine.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
        
        // verify that the pointer will not go out of bounds
        commandBox.keyPress(KeyCode.DOWN);
        assertEquals(commandBox.getCommandInput(), "");
        
        // verify that the pointer will not go out of bounds after traversing command history
        commandBox.keyPress(KeyCode.UP);
        commandBox.keyPress(KeyCode.UP);
        assertEquals(commandBox.getCommandInput(), TypicalTestTasks.cs1020.getAddCommand());
        commandBox.keyPress(KeyCode.DOWN);
        assertEquals(commandBox.getCommandInput(), TypicalTestTasks.engine.getAddCommand());

        commandBox.keyPress(KeyCode.DOWN);
        commandBox.keyPress(KeyCode.DOWN);
        commandBox.keyPress(KeyCode.DOWN);
        assertEquals(commandBox.getCommandInput(), TypicalTestTasks.engine.getAddCommand());
    }
    //@@author
}
