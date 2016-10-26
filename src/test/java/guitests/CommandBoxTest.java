package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.TypicalTestTasks;

public class CommandBoxTest extends TaskManagerGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(TypicalTestTasks.task5.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }

}
