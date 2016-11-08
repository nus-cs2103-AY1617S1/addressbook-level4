package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.testplanner.testutil.TestTask;

//@@author A0146749N
public class PinCommandTest extends DailyPlannerGuiTest {

    @Test
    public void pin_success() {

	TestTask taskToPin = td.CS2103_Project;
	assertPinSuccess("pin 1", taskToPin);
    }

    @Test
    public void pin_task_already_pinned() {

	TestTask taskToPin = td.WatchMovie;
	commandBox.runCommand("pin " + 6);
	assertResultMessage("Task is already pinned.");
    }

    private void assertPinSuccess(String command, TestTask taskToPin) {

	commandBox.runCommand(command);

	// confirm there is now 1 task in the pinned list
	assertEquals(pinnedListPanel.getNumberOfPeople(), 2);
    }
}
