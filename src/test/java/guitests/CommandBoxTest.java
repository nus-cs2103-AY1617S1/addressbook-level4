package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends DearJimGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.benson.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textCleared(){
        commandBox.runCommand("add funny name repeat every day");
        assertEquals(commandBox.getCommandInput(), "");
    }
    
}
