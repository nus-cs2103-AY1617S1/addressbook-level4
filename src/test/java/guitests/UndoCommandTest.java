package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.UndoCommand.MESSAGE_UNDO_SUCCESS;

import org.junit.Test;

import seedu.cmdo.testutil.TestTask;

//@@author A0141128R tested and passed
public class UndoCommandTest extends ToDoListGuiTest {

    @Test
    public void undo() {
        
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] expectedList = currentList;
        
        //undo up to 5 times
        for(int i=0;i<5;i++){
        	TestTask taskToAdd = td.car;
        	commandBox.runCommand(taskToAdd.getAddCommand());	
        }
        for(int y=0;y<4;y++)
        commandBox.runCommand("undo");
        
        assertUndoSuccess(expectedList, currentList);
        
        //nothing to undo
        commandBox.runCommand("undo");
        assertResultMessage("Nothing to undo.");
        
        //undo one add command
        TestTask taskToAdd = td.car;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess(expectedList, currentList);
        
        //undo add task with date/time range
        taskToAdd = td.vacation;
        commandBox.runCommand(taskToAdd.getAddRangeCommand());
        assertUndoSuccess(expectedList, currentList);
        
        //undo a block command
        TestTask timeToBlock = td.meeting;
        commandBox.runCommand(timeToBlock.getBlockCommand());
        assertUndoSuccess(expectedList, currentList);
        
        //undo a delete command
        commandBox.runCommand("delete " + "1");
        assertUndoSuccess(expectedList, currentList);
       
        //undo a done command
        commandBox.runCommand("done " + "2");
        assertUndoSuccess(expectedList, currentList);
        
        //undo a redo
        taskToAdd = td.dog;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");
        assertUndoSuccess(expectedList, currentList);
      
        //undo clear command
        commandBox.runCommand("clear");
        assertUndoSuccess(expectedList, currentList);
        
        //undo the edit the time of the first task in the list  
        commandBox.runCommand("edit " + "2 " + "'Eat Buffet'");
        assertUndoSuccess(expectedList, currentList);


        //undo the edit the priority of the last task in the list
        int targetIndex = currentList.length;
        commandBox.runCommand("edit " + targetIndex + " /low");
        assertUndoSuccess(expectedList, currentList);
        
        //undo the action of making last task floating
        targetIndex = 1;
        commandBox.runCommand("edit " + targetIndex + " floating");
        assertUndoSuccess(expectedList, currentList);
        
        //undo the change tags of last task
        targetIndex = currentList.length;
        commandBox.runCommand("edit " + targetIndex + " -dangerous");
        assertUndoSuccess(expectedList, currentList);
        
        //undo the edit of removing priority of first task using 'rp' or 'remove priority'
        targetIndex = 1;
        commandBox.runCommand("edit " + targetIndex + " rp");
        assertUndoSuccess(expectedList, currentList);
        commandBox.runCommand("edit " + targetIndex + " remove priority");
        assertUndoSuccess(expectedList, currentList);
        
        //undo the edit of time of task 2
        targetIndex = 2;
        commandBox.runCommand("edit " + targetIndex + " 1120");
        assertUndoSuccess(expectedList, currentList);
        
        //undo the edit of date of task 2
        targetIndex = 2;
        commandBox.runCommand("edit " + targetIndex + " 10/20/2016");
        assertUndoSuccess(expectedList, currentList);
        
        //undo the edit of task 3 to a range task
        targetIndex = 3;
        commandBox.runCommand("edit " + targetIndex + " 11/12/2016 1300 to 12/12/2016 1500");
        assertUndoSuccess(expectedList, currentList);

    }

    private void assertUndoSuccess(TestTask[] expectedList, TestTask... currentList) {
    	
    	commandBox.runCommand("undo");
    	
    	//confirm the list matches
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm
        assertResultMessage(MESSAGE_UNDO_SUCCESS);
    }

}