package guitests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.model.task.Status;
//@@author A0139339W
public class DoneCommandTest extends TaskManagerGuiTest{

	@Test
	public void done() {
		TestTask[] currentList = td.getSortedTypicalTasks();
		int[] doneIndices;
		int[] notDoneIndices = new int[0];
		
		//mark tasks as done
		String command = "done 1 2 3 14";
		doneIndices = new int[] {1,2,3,14};
		assertDoneSuccess(command, doneIndices, notDoneIndices, currentList);
		
		//mark tasks as not done
		command = "done not 1 2";
		doneIndices = new int[0];
		notDoneIndices = new int[] {1,2};
		assertDoneSuccess(command, doneIndices, notDoneIndices, currentList);
		
		//mark some tasks as done and some tasks as not done
		command = "done 6 7 not 3 4";
		doneIndices = new int[] {6,7};
		notDoneIndices = new int[] {3,4};
		assertDoneSuccess(command, doneIndices, notDoneIndices, currentList);
		
		//mark the same task as done and not done
		command = "done 3 not 3";
		doneIndices = new int[] {3};
		notDoneIndices = new int[] {3};
		assertDoneSuccess(command, doneIndices, notDoneIndices, currentList);
		
		//mark index out of bound
		command = "done 15";
		commandBox.runCommand(command);
		String doneMessage = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX 
				+ " for done command"; 
		String notDoneMessage = String.format(DoneCommand.MESSAGE_NOT_DONE_TASK_SUCCESS, 
				new ArrayList<TestTask>());
		assertResultMessage(doneMessage + "\n" + notDoneMessage);
		
	}
	
	public TestTask[] assertDoneSuccess (String command, int[] doneIndices, 
			int[] notDoneIndices, TestTask... currentList) {
		commandBox.runCommand(command);
		
		ArrayList<TestTask> taskDone = new ArrayList<TestTask>();
		ArrayList<TestTask> taskNotDone = new ArrayList<TestTask>();
		
		for(int i = 0; i < doneIndices.length; i++) {
			TestTask taskChanged = currentList[doneIndices[i]-1];
			taskChanged.setStatus(new Status("done"));
			taskDone.add(taskChanged);
			currentList = TestUtil.replaceTaskFromList(currentList, taskChanged, doneIndices[i]-1);
		}
		String doneMessage = String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, taskDone);
		
		for(int i = 0; i < notDoneIndices.length; i++) {
			TestTask taskChanged = currentList[notDoneIndices[i]-1];
			taskChanged.setStatus(new Status("not done"));
			taskNotDone.add(taskChanged);
			currentList = TestUtil.replaceTaskFromList(currentList, taskChanged, notDoneIndices[i]-1);
		}
		String notDoneMessage = String.format(DoneCommand.MESSAGE_NOT_DONE_TASK_SUCCESS, taskNotDone);

        //confirm the targeted tasks are replaced
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the result message is correct
        assertResultMessage(doneMessage + "\n" + notDoneMessage);
        
        return currentList;
	}

}
