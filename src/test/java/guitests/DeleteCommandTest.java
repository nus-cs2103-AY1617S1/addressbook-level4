package guitests;
//@@author A0142325R
import org.junit.Test;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_SAME_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_NAME;

import java.util.ArrayList;


public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void delete_by_index() {

        //delete the first in the list

        TestTask[] currentList = td.getTypicalTasks();

        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }
    @Test
    public void delete_by_name(){
    	TestTask[] currentList=td.getTypicalTasks();
    	
    	//delete task with an unique name
    	assertDeleteSuccess("Read book",currentList);
    	
    	//delete task duplicated name
    	assertDeleteSuccess("Meet old friends",currentList);
    	
    	//delete a non-existant task 
    	assertDeleteSuccess("bason",currentList);
    	
    	
    }

    /**
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }

   
    private void assertDeleteSuccess(String taskName,final TestTask[] currentList){
    	ArrayList<TestTask> tasksToDelete=new ArrayList<TestTask>();
    	for(TestTask e:currentList){
    		if(taskName.equals(e.getName().taskName))
    			tasksToDelete.add(e);
    	}
    	if(tasksToDelete.size()==1){
    		TestTask[] expectedRemainder=TestUtil.removeTasksFromList(currentList, tasksToDelete.get(0));
    		commandBox.runCommand("delete "+taskName);
    		assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, tasksToDelete.get(0)));
    		assertTrue(taskListPanel.isListMatching(expectedRemainder));
    	}
    	else if(tasksToDelete.size()==0){
    		commandBox.runCommand("delete "+taskName);
    		assertResultMessage(String.format(MESSAGE_INVALID_TASK_NAME));
    	}
    	else{
    		commandBox.runCommand("delete "+taskName);
    		assertResultMessage(String.format(MESSAGE_DELETE_SAME_NAME));
    	}
   
    }

}
