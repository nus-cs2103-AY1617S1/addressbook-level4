package guitests;

import org.junit.Test;

import seedu.task.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends TaskManagerGuiTest {

    @Test
    public void commandBoxCommandSucceedsTextCleared() {
        commandBox.runCommand(TypicalTestTasks.taskB.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBoxCommandFailsTextStays() {
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
        // TODO: confirm the text box color turns to red
    }

}
