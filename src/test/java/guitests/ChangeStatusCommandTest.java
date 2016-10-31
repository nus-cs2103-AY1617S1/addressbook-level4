package guitests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.model.task.Status;

//@@author A0139339W
public class ChangeStatusCommandTest extends TaskManagerGuiTest {
	
	TestTask[] doneList;
	TestTask[] pendingList;
	ArrayList<TestTask[]> updatedList;
	
	@Test
	public void done() {
		// mark tasks as done
		commandBox.runCommand("list pending");
		pendingList = td.getNotDoneTasks();
		doneList = td.getDoneTasks();
		String command = "done 1 8";
		int[] indices = new int[] { 1, 8 };
		updatedList = doneTasks(indices, pendingList, doneList);
		pendingList = updatedList.get(0);
		doneList = updatedList.get(1);
		assertChangeStatusSuccess(command, pendingList, doneList);

		// mark index out of bound
		command = "done 9";
		commandBox.runCommand(command);
		String message = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertResultMessage(message);
	}

//	@Test
//	public void pending() {
//		// mark tasks as pending
//		commandBox.runCommand("list done");
//		doneList = td.getDoneTasks();
//		String command = "pending 3 2";
//		int[] indices = new int[] { 3, 2 };
//		assertChangeStatusSuccess(command, "pending", indices, currentList);
//
//		// mark index out of bound
//		command = "pending 0";
//		commandBox.runCommand(command);
//		String message = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
//		assertResultMessage(message);
//	}

	public void assertChangeStatusSuccess(String command, 
			TestTask[] pendingList, TestTask[] doneList) {
		commandBox.runCommand(command);
		commandBox.runCommand("list pending");
		assertTrue(taskListPanel.isListMatching(pendingList));
		commandBox.runCommand("list done");
		assertTrue(taskListPanel.isListMatching(doneList));
	}
	
	public ArrayList<TestTask[]> doneTasks(int[] indices, 
			TestTask[] pendingList, TestTask[] doneList) {
		TestTask[] tasksTargeted = new TestTask[0];
		TestTask[] tasksDone = new TestTask[0];
		for(int i = 0; i < indices.length; i++) {
			TestTask task = pendingList[indices[i] - 1];
			tasksTargeted = TestUtil.addTasksToList(tasksTargeted, task);
			task.setStatus(new Status("done"));
			tasksDone = TestUtil.addTasksToList(tasksDone, task);
		}
		pendingList = TestUtil.removeTasksFromList(pendingList, tasksTargeted);
		doneList = TestUtil.addTasksToList(doneList, tasksDone);
		ArrayList<TestTask[]> updatedList = new ArrayList<TestTask[]>();
		updatedList.add(pendingList);
		updatedList.add(doneList);
		return updatedList;
	}
//	public TestTask[] assertChangeStatusSuccess(String command, String newStatus, int[] indices,
//			TestTask[] pendingList, TestTask[] doneList) {
//		commandBox.runCommand(command);
//
//		ArrayList<TestTask> changedTasks = new ArrayList<TestTask>();
//
//		for (int i = 0; i < indices.length; i++) {
//			TestTask taskChanged = currentList[indices[i] - 1];
//			taskChanged.setStatus(new Status(newStatus));
//			changedTasks.add(taskChanged);
//			currentList = TestUtil.replaceTaskFromList(currentList, taskChanged, indices[i] - 1);
//			//to remove the done tasks from
//			currentList = (TestTask[]) Arrays.copyOfRange(currentList, 0, currentList.length);
//		}
//
//		String message;
//		if (newStatus.equals("done")) {
//			message = String.format(ChangeStatusCommand.MESSAGE_TASK_SUCCESS_DONE, changedTasks);
//		} else {
//			message = String.format(ChangeStatusCommand.MESSAGE_TASK_SUCCESS_PENDING, changedTasks);
//		}
//
//		// confirm the targeted tasks are replaced
//		assertTrue(taskListPanel.isListMatching(currentList));
//
//		// confirm the result message is correct
//		assertResultMessage(message);
//
//		return currentList;
//	}

}
