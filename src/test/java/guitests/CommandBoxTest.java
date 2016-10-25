package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandBoxTest extends TaskMasterGuiTest {

    @Test
    public void commandBox_commandExecutionSucceeds_textCleared() {
        commandBox.runCommand(td.hoon.getAddFloatingCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandIncorrect_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
        assertTrue(commandBox.getStyleClass().contains("error"));
    }
    
    @Test
    public void commandBox_commandExecutionFails_textStays(){
    	commandBox.runCommand(td.book.getAddFloatingCommand());
        assertEquals(commandBox.getCommandInput(), td.book.getAddFloatingCommand());
        //TODO: confirm the text box color turns to orange
        assertTrue(commandBox.getStyleClass().contains("fail"));
    }

}
