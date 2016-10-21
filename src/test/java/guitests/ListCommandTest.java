package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {

	@Test
	public void list() {
		TestTask[] testList = td.getTypicalTasks();
		//list without parameters
		assertListResult("list", testList);
	}
	
	private void assertListResult(String command, TestTask... list) {
		commandBox.runCommand(command);
		assertTrue(taskListPanel.isListMatching(list));
	}

}
