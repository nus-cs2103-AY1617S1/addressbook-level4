package guitests;
import org.junit.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_SAME_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_NAME;

import java.util.ArrayList;

//@@author A0142325R

/**
 * test for delete command on gui
 * @author LiXiaowei
 * 
 * use cases: 1) delete by index 2) delete by name
 *  
 */

public class DeleteCommandTest extends TaskManagerGuiTest {
    
    //----------------------------------valid cases-----------------------------------------
    
    //test for use scenario 1: delete by index

    @Test
    public void deleteByIndex_successful() {

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

    }
    
    //test for use scenario 2: delete by name
    
    @Test
    public void deleteByName_successful(){
    	TestTask[] currentList=td.getTypicalTasks();
    	
    	//delete task with an unique name
    	assertDeleteSuccess("Read book",currentList);
    	
    	//delete task duplicated name
    	assertDeleteSuccess("Meet old friends",currentList);
    	
    }
    
    //test for invalid index entered
    
    @Test
    public void deleteByInvalidIndex_fail(){
        TestTask[] currentList=td.getTypicalTasks();
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }
    
    //------------------------------------invalid cases----------------------------------
    
    //test for delete task or event name that does not exist
    
    @Test
    public void deleteByNonExistantName_fail(){
        commandBox.runCommand("delete bason");
        assertResultMessage(DeleteCommand.MESSAGE_DELETE_NOT_FOUND);
    }

    /**
     * Runs the delete command to delete the task or event at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }
    
    
    /**
     * Runs the delete command to delete the item of specific name. A list of possible tasks
     * or events with one or more of the input parameters will be shown.
     * @param taskName
     * @param currentList
     */

   
    private void assertDeleteSuccess(String taskName,final TestTask[] currentList){
    	ArrayList<TestTask> tasksToDelete=new ArrayList<TestTask>();
    	for(TestTask e:currentList){
    		if(taskName.equals(e.getName().taskName))
    			tasksToDelete.add(e);
    	}
    	
    	if(tasksToDelete.size()==0){
    		commandBox.runCommand("delete "+taskName);
    		assertResultMessage(String.format(MESSAGE_INVALID_TASK_NAME));
    	}
    	else{
    		commandBox.runCommand("delete "+taskName);
    		assertResultMessage(String.format(MESSAGE_DELETE_SAME_NAME));
    	}
   
    }

}
