package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends TaskBookGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.todo1.getAddCommand());
        assertEquals("", commandBox.getCommandInput());
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals("invalid command", commandBox.getCommandInput());
        //TODO: confirm the text box color turns to red
    }

}
