package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.RedoCommand.MESSAGE_REDO_SUCCESS;

import java.util.ArrayList;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

//@@author A0141128R
public class RedoCommandTest extends ToDoListGuiTest {

    @Test
    public void redo() {
        
        TestTask[] currentList = td.getTypicalTasks();
        
        //redo up to 3 times
        for(int i=0;i<3;i++){
        	TestTask taskToAdd = td.car;
        	commandBox.runCommand(taskToAdd.getAddCommand());
        	currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        }
        
        for(int y=0;y<3;y++)
        	commandBox.runCommand("undo");
        
        for(int y=0;y<2;y++)
            commandBox.runCommand("redo");
        
        assertRedoSuccess(currentList);
        
        //nothing to redo
        commandBox.runCommand("redo");
        assertResultMessage("Nothing to redo.");       
        
        //redo undo of add task with date/time range
        TestTask taskToAdd = td.vacation;
        commandBox.runCommand(taskToAdd.getAddRangeCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        execute(currentList);
        
        //redo undo of a block command
        TestTask timeToBlock = td.meeting;
        commandBox.runCommand(timeToBlock.getBlockCommand());
        currentList = TestUtil.addTasksToList(currentList, timeToBlock);
        execute(currentList);
        currentList = sortList(currentList);
        
        //redo undo of edit details
        int targetIndex = 2;
        commandBox.runCommand("edit " + targetIndex + " 'Eat Buffet'");
        TestTask editedTask = td.editedGrocery;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        execute(currentList);
        currentList = sortList(currentList);

        //redo undo of edit the priority of the 7th task in the list
        targetIndex = 7;
        commandBox.runCommand("edit " + targetIndex + " /low");
        editedTask = td.editedZika;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        execute(currentList);
        currentList = sortList(currentList);
        
        //redo undo of make 2nd task floating
        targetIndex = 2;
        commandBox.runCommand("edit " + targetIndex + " floating");
        editedTask = td.floatingGrocery;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        execute(currentList);
        currentList = sortList(currentList);
        
        //redo undo of change tags of 6th task
        targetIndex = 6;
        commandBox.runCommand("edit " + targetIndex + " -dangerous");
        editedTask = td.taggedZika;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        execute(currentList);
        currentList = sortList(currentList);
        
        //redo undo of change time of task 1
        targetIndex = 1;
        commandBox.runCommand("edit " + targetIndex + " 1120");
        editedTask = td.editedHouse1;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        execute(currentList);
        currentList = sortList(currentList);
        
        //redo undo of change date of task 1
        targetIndex = 1;
        commandBox.runCommand("edit " + targetIndex + " 10/20/2016");
        editedTask = td.editedHouse2;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        execute(currentList);
        currentList = sortList(currentList);
        
        //redo undo of a delete command
        targetIndex = currentList.length -1;
        commandBox.runCommand("delete " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        execute(currentList);
        currentList = sortList(currentList);
       
        //redo undo of a done command
        targetIndex = currentList.length;
        commandBox.runCommand("done " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        execute(currentList);
        currentList = sortList(currentList);
        
        //unable to redo after undoing a task and then executing a new command
        taskToAdd = td.vacation;
        commandBox.runCommand(taskToAdd.getAddRangeCommand());
        commandBox.runCommand("undo");
        targetIndex = 1;
        commandBox.runCommand("delete " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        commandBox.runCommand("redo");
        assertResultMessage("Nothing to redo.");

        
        //redo clear command
        commandBox.runCommand("clear");
        currentList = TestUtil.removeTasksFromList(currentList,currentList);
        execute(currentList);

    }
    
    //confirm the list now contains all previous tasks except the deleted task
    private void compareList(TestTask[] currentList){
    	  assertTrue(taskListPanel.isListMatching(currentList));
    }
    
    //execute undo then redo,to check if redo works in those scenarios
    private void execute(TestTask... currentList){
    	commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
    }
    
    //sort list
    private TestTask[] sortList(TestTask... currentList){
    	ArrayList<TestTask> list = new ArrayList<TestTask>(Arrays.asList(currentList));
    	Collections.sort(list);
    	return list.toArray(new TestTask[currentList.length]);
    }
    
    private void assertRedoSuccess(TestTask... currentList) {
    	
    	commandBox.runCommand("redo");
    	
    	//sort list
        currentList = sortList(currentList);
 
    	//confirm the list matches
        compareList(currentList);
        
        //confirm
        assertResultMessage(MESSAGE_REDO_SUCCESS);
    }

}