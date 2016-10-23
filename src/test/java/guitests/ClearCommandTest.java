package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends MalitioGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalFloatingTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.manualFloatingTask1.getAddCommand());
        assertTrue(floatingTaskListPanel.isListMatching(td.manualFloatingTask1));
        commandBox.runCommand("delete f1");
        assertTotalListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertTotalListSize(0);
        assertResultMessage("Malitio has been cleared!");
    }
}
