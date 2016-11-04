package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import guitests.guihandles.TaskCardHandle;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

//@@author A0141128R  tested and passed
public class BlockCommandTest extends ToDoListGuiTest {

    @Test
    public void block() {
        //block a time slot without range
        TestTask[] currentList = td.getTypicalTasks();
        TestTask timeToBlock = td.meeting;
        assertBlockSuccess(timeToBlock, currentList);
        currentList = updateList(timeToBlock, currentList);
        
        //add block a timeslot with date/time range
        timeToBlock = td.businessDeal;
        assertBlockSuccess(timeToBlock, currentList);
        currentList = updateList(timeToBlock, currentList);

        
        //cannot add task to a blocked timeslot with same timing
        TestTask taskToAdd = td.eat;
        runCommand(taskToAdd.getAddCommand());
        assertResultMessage(Messages.MESSAGE_TIMESLOT_BLOCKED);
        runCommand("list all");//to go back to general list
        
        //cannot done a block task
        runCommand("done 5");
        assertResultMessage(Messages.MESSAGE_CANNOT_DONE);
        
        //invalid detail parameter
        runCommand("block 'ppp");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        runCommand("block ppp'");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        runCommand("block ''");
        assertResultMessage(Messages.MESSAGE_BLANK_DETAIL_WARNING);
        
        //block a time slot in an empty list
        runCommand("clear");
        assertBlockSuccess(td.deal);

        //invalid command
        runCommand("blocks meeting with OCBC");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    //confirm the new card contains the right details
    private void checkCard(TestTask timeToBlock){
    	 TaskCardHandle addedCard = taskListPanel.navigateToTask(timeToBlock.getDetail().details);
         assertMatching(timeToBlock, addedCard);
    }
    
    //run other commands
    private void runCommand(String input){
    	commandBox.runCommand(input);
    }
    
    //run command
    private void runBlockCommand(TestTask timeToBlock){
    	commandBox.runCommand(timeToBlock.getBlockCommand());
    }
    
    //update list
    private TestTask[] updateList(TestTask timeToBlock, TestTask... currentList){
    	return TestUtil.addTasksToList(currentList, timeToBlock);
    }
    
    //sort list
    private TestTask[] sortList(TestTask... expectedList){
    	ArrayList<TestTask> list = new ArrayList<TestTask>(Arrays.asList(expectedList));
    	Collections.sort(list);
    	return list.toArray(new TestTask[expectedList.length]);
    }
    
    private void assertBlockSuccess(TestTask timeToBlock, TestTask... currentList) {
 
    	runBlockCommand(timeToBlock);
    	
    	//update list
    	TestTask[] expectedList = updateList(timeToBlock, currentList);
    	
    	//sort list
    	expectedList = sortList(expectedList);
    	
        //confirm the new card contains the right data
        checkCard(timeToBlock);

        //confirm the list now contains the new blocked slot
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
