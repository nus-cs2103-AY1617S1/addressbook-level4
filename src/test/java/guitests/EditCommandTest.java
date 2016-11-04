package guitests;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import guitests.guihandles.TaskCardHandle;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.EditCommand.MESSAGE_EDITED_TASK_SUCCESS;

import java.util.ArrayList;

// @@author A0141128R tested and passed
public class EditCommandTest extends ToDoListGuiTest {
	
    @Test
    public void edit() {
    	TestTask[] currentList = td.getTypicalTasks();
    	currentList = sortList(currentList);
        
    	//edit the detail of the 2nd task in the list to Eat buffet
    	int targetIndex = 2;
    	currentList = execute(targetIndex, currentList, "'Eat Buffet'",td.editedGrocery);


        //edit the priority of the last task in the list to low
        targetIndex = currentList.length;
        currentList = execute(targetIndex, currentList, "/low",td.editedZika);
        
        //make 2nd task floating
        targetIndex = 2;
        currentList = execute(targetIndex, currentList, "floating",td.floatingGrocery);
        
        //fails here as mosquito goes to 2 instead of expected 3
        //change tags of 3 task to dangerous
        targetIndex = 3;
        currentList = execute(targetIndex, currentList, "-dangerous",td.taggedZika);
        
        //remove priority of last task 
        targetIndex = currentList.length;
        currentList = execute(targetIndex, currentList, "no priority",td.noPriorityGrocery);
        
        //change time of task 1 to 1120
        targetIndex = 1;
        currentList = execute(targetIndex, currentList, "1120",td.editedHouse1);
        
        //change date of task 1 to 10/20/2016
        currentList = execute(targetIndex, currentList, "10/20/2016",td.editedHouse2);
        
        //change task 1 to a range task
        targetIndex = 1;
        currentList = execute(targetIndex, currentList, "11/12/2016 1300 to 12/12/2016 1500",td.editedCar);
        
        //invalid priority parameter
        runEditCommand(1, "/yolo");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY);
        
        //cannot edit a done task
        commandBox.runCommand("done 1");
        commandBox.runCommand("ld");
        runEditCommand(1, "/high");
        assertResultMessage("Cannot edit a done task!");
        
        //invalid index
        invalidCommand(currentList.length + 1, "/high");
        
        //edit something from an empty list
        commandBox.runCommand("clear");
        invalidCommand(1, "/high");

    }
    //invalid index execution
    private void invalidCommand(int targetIndex, String changes){
    	 runEditCommand(targetIndex,changes);
    	 assertResultMessage("The task index provided is invalid");
    }
    
    
    
    //executes successful edits
    private TestTask[] execute(int targetIndex, TestTask[] currentList, String change, TestTask editedTask){
        assertEditSuccess(targetIndex, currentList,change,editedTask);
        return updateList(currentList,editedTask,targetIndex);
    }
    
    //run commands
    private void runEditCommand(int index, String change){
    	commandBox.runCommand("edit " + index +" " + change);
    }
    
    //update list
    private TestTask[] updateList(TestTask[] listToUpdate, TestTask editedTask, int targetIndex){
    	TestTask[] list = TestUtil.replaceTaskFromList(listToUpdate,editedTask,targetIndex-1);
    	list = sortList(list);
    	return list;
    }
    
     //confirm the new card contains the right data
    private void checkCard(TestTask editedTask){
    	TaskCardHandle EditedCard = taskListPanel.navigateToTask(editedTask.getDetail().details);
    	assertMatching(editedTask, EditedCard);
    }
    
    //confirm the list now contains all tasks after edit
    private void compareList(TestTask[] expectedRemainder){
    	  assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }
    
    //sort list
    private TestTask[] sortList(TestTask... RemainderList){
    	ArrayList<TestTask> list = new ArrayList<TestTask>(Arrays.asList(RemainderList));
    	Collections.sort(list);
    	return list.toArray(new TestTask[RemainderList.length]);
    }

    /**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to edit the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before edit).
     */
    private void assertEditSuccess(int targetIndexOneIndexed, final TestTask[] currentList, String change, TestTask ed) {
    	
        runEditCommand(targetIndexOneIndexed, change);
        
        //updateList
        TestTask[] expectedRemainder = updateList(currentList,ed,targetIndexOneIndexed);
        
        //sort list
        expectedRemainder = sortList(expectedRemainder);
        
        //confirm the new card contains the right data
        checkCard(ed);

        //confirm the list now contains all previous tasks except the edited task
        compareList(expectedRemainder);
        
        //confirm the result message is correct
        assertResultMessage(MESSAGE_EDITED_TASK_SUCCESS);
    }

}