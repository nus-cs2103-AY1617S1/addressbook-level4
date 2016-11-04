package guitests;

import org.junit.Test;

import seedu.oneline.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends TaskBookGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(TypicalTestTasks.todoExtra.getAddCommand());
        assertEquals("", commandBox.getCommandInput());
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals("invalid command", commandBox.getCommandInput());
    }

}
