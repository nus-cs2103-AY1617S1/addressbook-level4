package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.testplanner.testutil.TestTask;
//@@author A0139102U
public class UndoCommandTest extends DailyPlannerGuiTest {

	TestTask[] currentList = td.getTypicalTasks();

	@Test
	public void undo_add() {
		TestTask taskToAdd = td.learnSpanish;
		commandBox.runCommand(taskToAdd.getAddCommand());
		commandBox.runCommand("undo");
		assertUndoAddDeleteSuccess();
	}

	@Test
	public void undo_delete() {
		TestTask taskToDelete = td.CS2103_Project;
		commandBox.runCommand("delete 1");
		commandBox.runCommand("undo");
		assertUndoAddDeleteSuccess();
	}

	@Test
	public void undo_edit() {
		TestTask taskToEdit = td.CS2103_Project;
		commandBox.runCommand("edit 1 s/next year");
		commandBox.runCommand("undo");
		assertEditSuccess(taskToEdit);
	}

	private void assertEditSuccess(TestTask taskToEdit) {
		TaskCardHandle editedCard = taskListPanel.navigateToPerson(taskToEdit.getName());
		assertMatching(taskToEdit, editedCard);
	}

	@Test
	public void undo_pin() {
		TestTask taskToPin = td.MA1101R_Homework;
		commandBox.runCommand("pin 3");
		commandBox.runCommand("undo");
		int expectedPinBoardLength = 0;
		assertUndoPinSuccess(expectedPinBoardLength);
	}

	@Test
	public void undo_unpin() {
		commandBox.runCommand("pin 5");
		TestTask taskToUnpin = td.BuyGroceries;
		commandBox.runCommand("unpin 1");
		commandBox.runCommand("undo");
		int expectedPinBoardLength = 1;
		assertUndoPinSuccess(expectedPinBoardLength);
	}

	@Test
	public void undo_complete() {
		TestTask taskToComplete = td.CS2103_Project;
		boolean originalCompletionStatus = taskToComplete.isComplete();
		commandBox.runCommand("complete 1");
		commandBox.runCommand("undo");
		assertUndoCompleteSuccess(taskToComplete, originalCompletionStatus);
	}

	@Test
	public void undo_uncomplete() {
		TestTask taskToUncomplete = td.CS2103_Lecture;
		boolean originalCompletionStatus = taskToUncomplete.isComplete();
		commandBox.runCommand("uncomplete 2");
		commandBox.runCommand("undo");
		assertUndoCompleteSuccess(taskToUncomplete, originalCompletionStatus);
	}

	private void assertUndoCompleteSuccess(TestTask taskToCheck, boolean originalCompletionStatus) {
		TaskCardHandle resultCard = taskListPanel.navigateToPerson(taskToCheck.getName());
		if (originalCompletionStatus) {
			assertEquals("Task is complete", resultCard.getCompletion(), "COMPLETE");
		} else {
			 assertTrue(!resultCard.getCompletion().equals("COMPLETE"));
		}
	}

	private void assertUndoAddDeleteSuccess() {
		assertTrue(taskListPanel.isListMatching(currentList));
	}

	private void assertUndoPinSuccess(int expectedPinBoardLength) {
		assertEquals(pinnedListPanel.getNumberOfPeople(), expectedPinBoardLength);

	}

}
