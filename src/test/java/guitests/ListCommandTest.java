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
		
		//list someday
		testList = td.getSomedayTasks();
		assertListResult("list someday", testList);
		
		//list deadline
		assertListResult("list deadline" );
	}
	
	private void assertListResult(String command, TestTask... expectedList) {
		commandBox.runCommand(command);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}

}
