package guitests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.model.task.Status;

//@@author A0139339W
public class ChangeStatusCommandTest extends TaskManagerGuiTest {
	
	TestTask[] currentList = td.getSortedTypicalTasks();
	
	@Test
	public void done() {
		// mark tasks as done
		String command = "done 1 2 3 14";
		int[] indices = new int[] { 1, 2, 3, 14 };
		assertChangeStatusSuccess(command, "done", indices, currentList);

		// mark index out of bound
		command = "done 15";
		commandBox.runCommand(command);
		String message = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertResultMessage(message);
	}

	@Test
	public void pending() {
		// mark tasks as pending
		String command = "pending 3 6 4";
		int[] indices = new int[] { 3, 6, 4 };
		assertChangeStatusSuccess(command, "pending", indices, currentList);

		// mark index out of bound
		command = "pending 0";
		commandBox.runCommand(command);
		String message = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertResultMessage(message);
	}

	
	public TestTask[] assertChangeStatusSuccess(String command, String newStatus, int[] indices,
			TestTask... currentList) {
		commandBox.runCommand(command);

		ArrayList<TestTask> changedTasks = new ArrayList<TestTask>();

		for (int i = 0; i < indices.length; i++) {
			TestTask taskChanged = currentList[indices[i] - 1];
			taskChanged.setStatus(new Status(newStatus));
			changedTasks.add(taskChanged);
			currentList = TestUtil.replaceTaskFromList(currentList, taskChanged, indices[i] - 1);
		}

		String message;
		if (newStatus.equals("done")) {
			message = String.format(ChangeStatusCommand.MESSAGE_TASK_SUCCESS_DONE, changedTasks);
		} else {
			message = String.format(ChangeStatusCommand.MESSAGE_TASK_SUCCESS_PENDING, changedTasks);
		}

		// confirm the targeted tasks are replaced
		assertTrue(taskListPanel.isListMatching(currentList));

		// confirm the result message is correct
		assertResultMessage(message);

		return currentList;
	}

}
