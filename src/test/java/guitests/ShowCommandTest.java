package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.testplanner.testutil.TestTask;
//@@author A0146749N
public class ShowCommandTest extends DailyPlannerGuiTest {

	@Test
	public void show() {

		TestTask[] currentList = td.getTypicalTasks();

		assertShowResult("show", currentList); // show all tasks

		assertShowResult("show today", td.SoccerWithFriends, td.BuyGroceries); // show
																				// tasks
																				// today
		assertShowResult("show completed", td.CS2103_Lecture, td.BuyGroceries); // show
																				// completed
	}

	private void assertShowResult(String command, TestTask... expectedHits) {
		commandBox.runCommand(command);
		assertListSize(expectedHits.length);
		if (command.equals("show")) {
			assertResultMessage("Showing all tasks");
		} else {
			assertResultMessage("Showing " + expectedHits.length + " tasks");
		}
		assertTrue(taskListPanel.isListMatching(expectedHits));

	}

}
