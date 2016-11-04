package guitests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.commons.core.Messages;
import seedu.address.model.task.Status;
import seedu.address.model.task.Status.StatusType;

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

	@Test
	public void pending() {
		// mark tasks as pending
		commandBox.runCommand("list done");
		doneList = td.getDoneTasks();
		pendingList = td.getNotDoneTasks();
		String command = "pending 3 2";
		int[] indices = new int[] { 3, 2 };
		updatedList = pendingTasks(indices, pendingList, doneList);
		pendingList = updatedList.get(0);
		doneList = updatedList.get(1);
		assertChangeStatusSuccess(command, pendingList, doneList);

		// mark index out of bound
		command = "pending 0";
		commandBox.runCommand(command);
		String message = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertResultMessage(message);
	}

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
	
	public ArrayList<TestTask[]> pendingTasks(int[] indices, 
			TestTask[] pendingList, TestTask[] doneList) {
		TestTask[] tasksTargeted = new TestTask[0];
		TestTask[] tasksPending = new TestTask[0];
		for(int i = 0; i < indices.length; i++) {
			TestTask task = doneList[indices[i] - 1];
			tasksTargeted = TestUtil.addTasksToList(tasksTargeted, task);
			task.setStatus(new Status("pending"));
			// Check for overdue cases
			if(task.getStatus().equals(StatusType.PENDING)) {
				tasksPending = TestUtil.addTasksToList(tasksPending, task);
			}
		}
		doneList = TestUtil.removeTasksFromList(doneList, tasksTargeted);
		pendingList = TestUtil.addTasksToList(pendingList, tasksPending);
		ArrayList<TestTask[]> updatedList = new ArrayList<TestTask[]>();
		updatedList.add(pendingList);
		updatedList.add(doneList);
		return updatedList;
	}

}