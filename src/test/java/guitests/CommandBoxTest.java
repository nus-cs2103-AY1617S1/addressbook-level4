package guitests;

import org.junit.Test;

import seedu.tasklist.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends TaskListGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(TypicalTestTasks.task2.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        //TODO: confirm the text box color turns to red
    }

}
