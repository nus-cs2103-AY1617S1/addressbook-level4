package guitests;

import static org.junit.Assert.*;
import static seedu.todolist.logic.commands.EditCommand.MESSAGE_SUCCESS;
import static seedu.todolist.logic.commands.EditCommand.MESSAGE_USAGE;
import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.Name;

//@@author A0146682X
public class EditCommandTest extends ToDoListGuiTest {

	@Test
	public void edit() {

		TestTaskList currentList = new TestTaskList(td.getTypicalTasks());

		//edit the first in the list of incomplete tasks
		int targetIndex = 1;
		String partialCommand = "test task first";
		assertEditSuccess(targetIndex, partialCommand, currentList, true);

		//edit the last in the list of incomplete tasks
		targetIndex = currentList.getIncompleteList().length;
		partialCommand = "test task last";
		assertEditSuccess(targetIndex, partialCommand, currentList, true);
	}

	/**
	 * Runs the edit command to edit incomplete tasks at the specified indices and confirms the result is correct.
	 * @param targetIndexes a list of indexes to be edited
	 * @param currentList A copy of the current list of tasks (before edition).
	 */
	private void assertEditSuccess(int targetIndex, String partialCommand, TestTaskList currentList, boolean isFromIncompleteList) {

		int listLength = isFromIncompleteList ? currentList.getIncompleteList().length : currentList.getCompleteList().length;
		if(targetIndex < 1 || targetIndex > listLength) assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		else {

			TestTask newTask = isFromIncompleteList ? currentList.getIncompleteList()[targetIndex-1] : currentList.getCompleteList()[targetIndex-1];
			try {
				newTask.setName(new Name(partialCommand));
			} catch (IllegalValueException ive) {
				System.out.println(MESSAGE_USAGE);
			}

			commandBox.runCommand(getCommand(targetIndex, partialCommand));

			assertTrue(taskListPanel.isListMatching(currentList.getIncompleteList()));
			assertTrue(completeTaskListPanel.isListMatching(currentList.getCompleteList()));
			assertResultMessage(String.format(MESSAGE_SUCCESS, newTask));
		}
	}

	/**
	 * Returns the command to be entered
	 */
	private String getCommand(int targetIndex, String string) {
		StringBuilder builder = new StringBuilder();
		builder.append("edit ");
		builder.append(targetIndex);
		builder.append(" " + string);
		return builder.toString();
	}
}
