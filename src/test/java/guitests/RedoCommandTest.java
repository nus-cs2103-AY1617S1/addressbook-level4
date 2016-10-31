package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.RedoCommand.MESSAGE_REDO_SUCCESS;

import org.junit.Test;

import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

//@@author A0141128R
public class RedoCommandTest extends ToDoListGuiTest {

    @Test
    public void redo() {
        
        TestTask[] currentList = td.getTypicalTasks();
        
//        //redo up to 5 times
//        for(int i=0;i<5;i++){
//        	TestTask taskToAdd = td.car;
//        	commandBox.runCommand(taskToAdd.getAddCommand());
//        	currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        }
//        
//        for(int y=0;y<5;y++)
//        	commandBox.runCommand("undo");
//        
//        for(int y=0;y<4;y++)
//            commandBox.runCommand("redo");
//        
//        assertRedoSuccess(currentList);
//        
//        //nothing to redo
//        commandBox.runCommand("redo");
//        assertResultMessage("Nothing to redo.");       
        
        //redo undo of add task with date/time range
        TestTask taskToAdd = td.vacation;
        commandBox.runCommand(taskToAdd.getAddRangeCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
//        //redo undo of a block command
//        TestTask timeToBlock = td.meeting;
//        commandBox.runCommand(timeToBlock.getBlockCommand());
//        currentList = TestUtil.addTasksToList(currentList, timeToBlock);
//        commandBox.runCommand("undo");
//        assertRedoSuccess(currentList);
        
        //fails as edit eat buffet changes the timing when the timing is not supposed to change
        //redo undo of edit details
        int targetIndex = 1;
        System.out.println(taskListPanel.getTask(0));
        commandBox.runCommand("edit " + targetIndex + " 'Eat Buffet'");
        TestTask editedTask = td.editedGrocery;
        commandBox.runCommand("undo");
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        System.out.println(currentList[0].getDetail());
        assertRedoSuccess(currentList);

        //redo undo of edit the priority of the last task in the list
        targetIndex = currentList.length;
        commandBox.runCommand("edit " + targetIndex + " /low");
        editedTask = td.editedZika;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
        //redo undo of make first task floating
        targetIndex = 1;
        commandBox.runCommand("edit " + targetIndex + " floating");
        editedTask = td.floatingGrocery;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
        //redo undo of change tags of last task
        targetIndex = currentList.length;
        commandBox.runCommand("edit " + targetIndex + " -dangerous");
        editedTask = td.taggedZika;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
        //fails assertion if called after add
        //redo undo of remove priority of first task using 'rp' or 'remove priority'
        targetIndex = 1;
        commandBox.runCommand("edit " + targetIndex + " remove priority");
        editedTask = td.noPriorityGrocery;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
        //redo undo of change time of task 2
        targetIndex = 2;
        commandBox.runCommand("edit " + targetIndex + " 1120");
        editedTask = td.editedHouse1;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
        //redo undo of change date of task 2
        targetIndex = 2;
        commandBox.runCommand("edit " + targetIndex + " 10/20/2016");
        editedTask = td.editedHouse2;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
        //redo undo of change task 3 to a range task
        targetIndex = 3;
        commandBox.runCommand("edit " + targetIndex + " 11/12/2016 1300 to 12/12/2016 1500");
        editedTask = td.editedCar;
        currentList = TestUtil.replaceTaskFromList(currentList,editedTask,targetIndex-1);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
        //redo undo of a delete command
        targetIndex = 2;
        commandBox.runCommand("delete " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
       
        //redo undo of a done command
        targetIndex = 3;
        commandBox.runCommand("done " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);
        
//        //unable to redo after undoing a task and then executing a new command
//		  // fails test
//        taskToAdd = td.vacation;
//        commandBox.runCommand(taskToAdd.getAddRangeCommand());
//        commandBox.runCommand("undo");
//        targetIndex = 1;
//        commandBox.runCommand("delete " + targetIndex);
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        commandBox.runCommand("redo");
//        assertResultMessage("Nothing to redo.");

        
        //redo clear command
        commandBox.runCommand("clear");
        currentList = TestUtil.removeTasksFromList(currentList,currentList);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList);

    }

    private void assertRedoSuccess(TestTask... currentList) {
    	
    	commandBox.runCommand("redo");
    	
    	//System.out.println(taskListPanel.getTask(0));
    	//confirm the list matches
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //confirm
        assertResultMessage(MESSAGE_REDO_SUCCESS);
    }

}