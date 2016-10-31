package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.AddCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

//@@author A0139661Y
public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void add() {
     
        TestTask[] currentList = td.getTypicalTasks();
        
        //add one task
        TestTask taskToAdd = td.car;
        currentList = execute(taskToAdd, currentList);

        //add another task
        taskToAdd = td.dog;
        currentList = execute(taskToAdd, currentList);
        
        //add task with date/time range
        taskToAdd = td.vacation;
        currentList = execute(taskToAdd, currentList);
        
        //invalid detail parameter
        commandBox.runCommand("add 'ppp");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("add ppp'");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("add ''");
        assertResultMessage(Messages.MESSAGE_BLANK_DETAIL_WARNING);
        
        //invalid priority parameter
        commandBox.runCommand("add 'new' /yolo");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY);
        commandBox.runCommand("add 'new'/high");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY_SPACE);
        
        //add to empty list
        commandBox.runCommand("clear");
        currentList = td.getEmptyTasks();
        currentList = execute(taskToAdd, currentList);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    //runs the add command,updates the list and asserts add success
    private TestTask[] execute(TestTask taskToAdd, TestTask... currentList){
    	assertAddSuccess(taskToAdd, currentList);
        return TestUtil.addTasksToList(currentList, taskToAdd);
    }
    
    //confirm the new card contains the right data
    private void checkCard(TestTask taskToAdd){
    	TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDetail().details);
    	assertMatching(taskToAdd, addedCard);
   }
   
    //confirm the list now contains all tasks after edit
    private void compareList(TestTask[] expectedList){
    	  assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        if (taskToAdd.getDueByDate().isRange() || taskToAdd.getDueByTime().isRange())
        	commandBox.runCommand(taskToAdd.getAddRangeCommand());
        else commandBox.runCommand(taskToAdd.getAddCommand());
        
        //update expected list
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);

        //confirm the new card contains the right data
        checkCard(taskToAdd);
        
        //confirm the list now contains all previous tasks plus the new task
        compareList(expectedList);
        
        //confirm
        assertResultMessage(String.format(MESSAGE_SUCCESS,taskToAdd));
    }

}
