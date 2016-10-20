package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class EditCommandTest extends FlexiTrackGuiTest {

    @Test
    public void editPass() {
    	TestTask[] currentList = td.getTypicalTasks();
    	TestTask editedTask;
    	int index;
    	String command;
    	
    	//edit a task name
    	editedTask = td.homework1EditName;
    	index = 1;
    	command = " n/ Name Edited";
    	assertEditSuccess(editedTask, currentList, index, command);
    	currentList = TestUtil.editTasksToList(currentList, index-1, editedTask);
    	
    	//edit a task duedate
    	editedTask = td.homework1EditDueDate;
    	index = 1;
    	command = " by/ Jan 14 2016 10am";
    	assertEditSuccess(editedTask, currentList, index, command);  
    	currentList = TestUtil.editTasksToList(currentList, index-1, editedTask);
    	
    	//edit an event name
    	editedTask = td.soccerEditName;
    	index = 4;
    	command = " n/ Name Edited";
    	assertEditSuccess(editedTask, currentList, index, command);  
    	currentList = TestUtil.editTasksToList(currentList, index-1, editedTask);
    	
    	//edit an event start time
    	editedTask = td.soccerEditStartTime;
    	index = 4;
    	command = " from/ June 10 2016 9pm";
    	assertEditSuccess(editedTask, currentList, index, command);  
    	currentList = TestUtil.editTasksToList(currentList, index-1, editedTask);
    	
    	//edit an event end time
    	editedTask = td.soccerEditEndTime;
    	index = 4;
    	command = " to/ June 30 2020 6am";
    	assertEditSuccess(editedTask, currentList, index, command);  
    	currentList = TestUtil.editTasksToList(currentList, index-1, editedTask);
    	
    	//edit a floating task name
    	editedTask = td.homework3EditName;
    	index = 3;
    	command = " n/ Name Edited";
    	assertEditSuccess(editedTask, currentList, index, command);  
    	currentList = TestUtil.editTasksToList(currentList, index-1, editedTask);
    	
    	//edit a floating task into a task
    	editedTask = td.homework3EditToTask;
    	index = 3;
    	command = " by/ Jun 10 2016 9pm";
    	assertEditSuccess(editedTask, currentList, index, command);  
    	currentList = TestUtil.editTasksToList(currentList, index-1, editedTask);
    	
    	//edit a floating task into an event
    	editedTask = td.eventEditToEvent;
    	index = 8;
    	command = " from/ Jun 10 2016 21:00 to/ Jun 30 2016 23:00";
    	assertEditSuccess(editedTask, currentList, index, command);  
    	currentList = TestUtil.editTasksToList(currentList, index-1, editedTask);
    	
    }
    
    @Test
    public void editFail(){
    	//errors
    	
    	//index not found
    	
//        //edit duplicate task
//        commandBox.runCommand(td.basketball.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//
//        
//        //invalid command
//        commandBox.runCommand("adds cs tutorial");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    	
    }

    private void assertEditSuccess(TestTask editedTask, final TestTask[] currentList, int indexOneIndexed, String command)  {
        commandBox.runCommand("edit " + indexOneIndexed + command);

        int index = indexOneIndexed - 1;
        //confirm the edited card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskListPanel.getTask(index));
        assertMatching(editedTask, editedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.editTasksToList(currentList, index, editedTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}