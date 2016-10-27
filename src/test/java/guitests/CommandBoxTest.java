package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.flexitrack.testutil.TypicalTestTasks;

public class CommandBoxTest extends FlexiTrackGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(TypicalTestTasks.homework2.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textStays() {
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        // TODO: confirm the text box color turns to red
    }

}
