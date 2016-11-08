package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.testplanner.testutil.TestTask;
import seedu.testplanner.testutil.TestUtil;
//@@author A0139102U
public class DeleteCompletedCommand extends DailyPlannerGuiTest {

	@Test
	public void deleteCompleted() {
		
		TestTask[] currentList = td.getTypicalTasks();
		commandBox.runCommand("delete complete");
		
		// Remove completed tasks
		currentList = TestUtil.removeTasksFromList(currentList, td.CS2103_Lecture, td.BuyGroceries);
		assertDeleteCompleteSuccess(currentList);
	}

	private void assertDeleteCompleteSuccess(TestTask[] expectedRemainder) {
		assertTrue(taskListPanel.isListMatching(expectedRemainder));
	}
}
