package guitests;

import guitests.guihandles.TaskCardHandle;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import seedu.address.model.task.*;

public class UpdateCommandTest extends AddressBookGuiTest {

	@Test
    public void update() {

      
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        TestTask taskToUpdate=td.hoon;
        assertUpdateSuccess(targetIndex,taskToUpdate,currentList);
        currentList[targetIndex-1]=taskToUpdate;
        currentList=TestUtil.addTasksToList(currentList);
        
        
        /*currentList = TestUtil.replaceTaskFromList(currentList, TestTask Task, int index);
        targetIndex = currentList.length;
        assertUpdateSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertUpdateSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("update " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");*/

    }
	
	private void assertUpdateSuccess(int targetIndex, TestTask taskToUpdate, TestTask... currentList) {
		commandBox.runCommand("update " + targetIndex + taskToUpdate.getArgs() );//change to get args
		
		//confirm the new card contains the right data
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(targetIndex-1);//cannot search by name
        assertMatching(taskToUpdate, updatedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList);
        expectedList[targetIndex-1]=taskToUpdate;
        assertTrue(taskListPanel.isListMatching(expectedList));
        

      
        //confirm the result message is correct
        //assertResultMessage(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }
	
	
}
