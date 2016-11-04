package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandBoxTest extends TaskManagerGuiTest {

    @Test
    public void commandBox_commandFails_blankAdd() {
        commandBox.runCommand("invalid");
        assertEquals(commandBox.getCommandInput(), "invalid");
    }



}
