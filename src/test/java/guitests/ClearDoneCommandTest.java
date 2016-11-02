package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearDoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void clearDone() {

        //verify a non-empty list can be cleared
        commandBox.runCommand("clear done");
        assertClearDoneCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hoon.getAddCommand());
        commandBox.runCommand("delete 1");

        //verify clear command works when the list is empty
        commandBox.runCommand("clear");
        commandBox.runCommand("clear done");
        assertClearDoneCommandSuccess();
    }

    private void assertClearDoneCommandSuccess() {
        commandBox.runCommand("clear done");
        assertResultMessage("Done tasks has been cleared!");
        commandBox.runCommand("show done"); // go to done list
        assertListSize(0);
    }
}
