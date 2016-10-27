package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandBoxTest extends TarsGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.taskB.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textStays() {
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
    }

    @Test
    public void commandBox_cycleThroughCommandTextHist() {
        commandBox.runCommand("User Input Command");
        commandBox.runCommand("User Input Command 2");
        commandBox.runCommand("User Input Command 3");
        commandBox.enterCommand("Temp User Input Command");

        commandBox.pressUpKey();
        assertEquals(commandBox.getCommandInput(), "User Input Command 3");
        commandBox.pressUpKey();
        assertEquals(commandBox.getCommandInput(), "User Input Command 2");
        commandBox.pressUpKey();
        assertEquals(commandBox.getCommandInput(), "User Input Command");
        commandBox.pressUpKey();
        assertEquals(commandBox.getCommandInput(), "User Input Command");
        commandBox.pressDownKey();
        assertEquals(commandBox.getCommandInput(), "User Input Command 2");
        commandBox.pressDownKey();
        assertEquals(commandBox.getCommandInput(), "User Input Command 3");
        commandBox.pressDownKey();
        assertEquals(commandBox.getCommandInput(), "Temp User Input Command");

    }
    
    @Test
    public void commandBox_cycleThroughTabPane() {
        commandBox.pressCtrlRightArrowKeys();
        assertTrue(commandBox.getRsvTaskListPanelHandle().isTabSelected());
        commandBox.pressCtrlRightArrowKeys();
        assertTrue(commandBox.getHelpPanelHandle().isTabSelected());
        commandBox.pressCtrlRightArrowKeys();
        assertTrue(commandBox.getOverviewPanelHandle().isTabSelected());
        commandBox.pressCtrlLeftArrowKeys();
        assertTrue(commandBox.getHelpPanelHandle().isTabSelected());
        commandBox.pressCtrlLeftArrowKeys();
        assertTrue(commandBox.getRsvTaskListPanelHandle().isTabSelected());      
    }
    
    @Test
    public void commandBox_textFieldValueChangesEvents_success() {
        commandBox.enterCommand("rsv");
        assertTrue(commandBox.getRsvTaskListPanelHandle().isTabSelected());
    }

}
