package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends TaskManGuiTest {

    //@Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.taskCS2103T.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    //@Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }

}
