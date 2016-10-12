package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends TaskManagerGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.milk.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "add go buy milk,No date or day specified,No start time set,No end time set,");
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }

}
