package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends ToDoListGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.benson.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }
    
    @Test
    public void commandBox_CommandHistory_notExists() {
        commandBox.scrollToPreviousCommand();
        assertEquals(commandBox.getCommandInput(), "");
        commandBox.scrollToPreviousCommand();
        assertEquals(commandBox.getCommandInput(), "");
        commandBox.scrollToNextCommand();
        assertEquals(commandBox.getCommandInput(), "");
    }
    
    @Test
    public void commandBox_CommandHistory_exists() {
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        commandBox.scrollToPreviousCommand();
        assertEquals(commandBox.getCommandInput(), "undo");
        commandBox.scrollToPreviousCommand();
        assertEquals(commandBox.getCommandInput(), "clear");
        commandBox.scrollToNextCommand();
        assertEquals(commandBox.getCommandInput(), "undo");
        commandBox.scrollToNextCommand();
        assertEquals(commandBox.getCommandInput(), "");
    }

}
