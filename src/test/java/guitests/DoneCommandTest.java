package guitests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.model.task.Status;

public class DoneCommandTest extends TaskManagerGuiTest{

	@Test
	public void done() {
		TestTask[] currentList = td.getTypicalTasks();
		int[] doneIndices;
		int[] notDoneIndices = new int[0];
		
		//mark tasks as done
		String command = "done 1 2";
		doneIndices = new int[] {1,2};
		assertDoneSuccess(command, doneIndices, notDoneIndices, currentList);
		
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
		
		for(int i = 0; i < notDoneIndices.length; i++) {
			TestTask taskChanged = currentList[notDoneIndices[i]-1];
			taskChanged.setStatus(new Status("not done"));
			taskNotDone.add(taskChanged);
			currentList = TestUtil.replaceTaskFromList(currentList, taskChanged, notDoneIndices[i]-1);
		}

        //confirm the targeted tasks are replaced
        assertTrue(taskListPanel.isListMatching(currentList));

        //confirm the result message is correct
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, taskDone) + "\n"
        		+ String.format(DoneCommand.MESSAGE_NOT_DONE_TASK_SUCCESS, taskNotDone));
        
        return currentList;
	}

}
