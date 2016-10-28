package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;

import static org.junit.Assert.assertTrue;

//@@author A0153736B
public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void findAny_nonEmptyList() {
    	//find non existent task
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find NonExistentTask", currentList);
    	
    	//find multiple tasks that contain the keyword entered
    	currentList = new TestTaskList (new TestTask[] {td.eventWithoutParameters, td.eventWithLocation, 
    			td.eventWithRemarks, td.eventWithLocationAndRemarks});
    	assertFindCommandSuccess("find event", currentList);
    	
    	//find tasks that contain one or more keywords entered
    	currentList = new TestTaskList(new TestTask[] {td.eventWithRemarks, td.deadlineWithoutParameter});
    	assertFindCommandSuccess("find withRemarks parameter", currentList);

        //find after deleting one result
        commandBox.runCommand("delete 1");
    	currentList = new TestTaskList (new TestTask[] {td.eventWithoutParameters, td.eventWithLocation, 
    			td.eventWithLocationAndRemarks});
        assertFindCommandSuccess("find Event", currentList);
        
        //find tasks from both incomplete and complete list
        commandBox.runCommand("done 1");
        currentList.markTasksFromList(new TestTask[] {td.eventWithoutParameters});
        assertFindCommandSuccess("find Event", currentList);        
    }

    @Test
    public void findAny_emptyList() {
        commandBox.runCommand("clear");
        TestTaskList currentList = new TestTaskList();
        assertFindCommandSuccess("find event", currentList); //no results
    }
    
    @Test
    public void findAll_nonEmptyList() {
    	commandBox.runCommand(td.taskOneToTestFind.getAddCommand());
    	commandBox.runCommand(td.taskTwoToTestFind.getAddCommand());
    	commandBox.runCommand(td.taskThreeToTestFind.getAddCommand());
    	
    	//find non existent task
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find all three four five", currentList);

    	//find tasks that contain all keywords entered
    	currentList = new TestTaskList(new TestTask[] {td.taskOneToTestFind, td.taskTwoToTestFind,
    			td.taskThreeToTestFind});
    	assertFindCommandSuccess("find all two three", currentList);

        //find after deleting one result
        commandBox.runCommand("delete 1");
    	currentList = new TestTaskList (new TestTask[] {td.taskTwoToTestFind, td.taskThreeToTestFind});
        assertFindCommandSuccess("find all three two", currentList);
        
        //find tasks from both incomplete and complete list
        commandBox.runCommand("done 1");
        currentList.markTasksFromList(new TestTask[] {td.taskTwoToTestFind});
        assertFindCommandSuccess("find all Two Three", currentList);        
    }
    
    @Test
    public void findAll_emptyList() {
    	commandBox.runCommand("clear");
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find all Two Three", currentList); //no results
    }
    
    @Test
    public void findExactly_nonEmptyList() {
    	commandBox.runCommand(td.taskOneToTestFind.getAddCommand());
    	commandBox.runCommand(td.taskTwoToTestFind.getAddCommand());
    	commandBox.runCommand(td.taskThreeToTestFind.getAddCommand());
    	
    	//find non existent task
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find exactly three four five", currentList);

    	//find tasks that contain the exact keywords entered
    	currentList = new TestTaskList(new TestTask[] {td.taskOneToTestFind});
    	assertFindCommandSuccess("find exactly two three", currentList);

        //find after deleting one result
        commandBox.runCommand("delete 1");
    	currentList = new TestTaskList (new TestTask[] {td.taskTwoToTestFind, td.taskThreeToTestFind});
        assertFindCommandSuccess("find exactly One three", currentList);
        
        //find tasks from both incomplete and complete list
        commandBox.runCommand("done 1");
        currentList.markTasksFromList(new TestTask[] {td.taskTwoToTestFind});
        assertFindCommandSuccess("find exactly one three", currentList);  
    }
    
    @Test
    public void findExactly_emptyList() {
    	commandBox.runCommand("clear");
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find exactly one three", currentList); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindCommandSuccess(String command, TestTaskList expectedList) {
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(expectedList.getIncompleteList()));
        assertTrue(completeTaskListPanel.isListMatching(expectedList.getCompleteList()));
        assertResultMessage(expectedList.getNumberOfTask() + " tasks listed!");
    }
}
