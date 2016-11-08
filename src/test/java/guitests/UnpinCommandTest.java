package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.testplanner.testutil.TestTask;
//@@author A0139102U
public class UnpinCommandTest extends DailyPlannerGuiTest {

	@Test
	public void unpin() {

		TestTask taskToUnpin = td.CS2103_Project;
		
		commandBox.runCommand("pin 1");
		assertUnpinSuccess("unpin 1", taskToUnpin);

	}

	private void assertUnpinSuccess(String command, TestTask taskToUnpin) {

		commandBox.runCommand(command);

		// confirm there is only 1 task remaining in the pinned list
		assertEquals(pinnedListPanel.getNumberOfPeople(), 1);
	}
}
