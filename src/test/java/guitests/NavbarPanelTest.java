package guitests;

import org.junit.Test;

import seedu.address.model.task.TaskOccurrence;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

//@@author A0147967J
public class NavbarPanelTest extends TaskMasterGuiTest {

    private final String NAVBAR_TASKS = " Tasks";
    private final String NAVBAR_DEADLINES = " Deadlines";
    private final String NAVBAR_INCOMING_DEADLINES = " Incoming Deadlines";
    private final String NAVBAR_FLOATING_TASKS = " Floating Tasks";
    private final String NAVBAR_COMPLETED = " Completed";

    @Test
    public void navigateToFloatingTasks() {
        TaskOccurrence[] expected;
        // Navigate to all floating tasks
        expected = Arrays.copyOfRange(td.getTypicalTaskComponents(), 0, 6);
        assertResult(NAVBAR_FLOATING_TASKS, expected);
    }

    @Test
    public void navigateToDeadlines() {
        // Navigate to deadlines
        assertResult(NAVBAR_DEADLINES, td.labDeadline.getLastAppendedComponent(),
                td.essayDeadline.getLastAppendedComponent());
    }

    @Test
    public void navigateToIncomingDeadlines() {
        // Navigate to incoming deadlines
        commandBox.runCommand(td.incoming.getAddNonFloatingCommand());
        assertResult(NAVBAR_INCOMING_DEADLINES, td.labDeadline.getLastAppendedComponent(),
                td.essayDeadline.getLastAppendedComponent(), td.incoming.getLastAppendedComponent());
    }

    @Test
    public void navigateToAllTasks() {
        // Navigate to all tasks
        TaskOccurrence[] expected = td.getTypicalTaskComponents();
        assertResult(NAVBAR_TASKS, expected);
    }

    @Test
    public void navigateToCompletedOnes() {
        // Navigate to completed ones
        commandBox.runCommand("done 1");
        assertResult(NAVBAR_COMPLETED, td.trash.getLastAppendedComponent());
    }

    private void assertResult(String navigation, TaskOccurrence... expectedHits) {
        navbar.navigateTo(navigation);
        assertListSize(expectedHits.length);
        if (navigation.equals(NAVBAR_TASKS))
            assertResultMessage("Listed all tasks");
        else
            assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
