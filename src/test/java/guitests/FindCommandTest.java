package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.FindCommand;
import seedu.todolist.model.task.Status;

import static org.junit.Assert.assertTrue;

//@@author A0153736B
public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void findEitherAtNonEmptyList() {
    	//find non existent task
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find either NonExistentTask", currentList);
    	
    	//find multiple tasks that contain the keyword entered
    	currentList = new TestTaskList (new TestTask[] {td.eventWithoutParameter, td.eventWithLocation, td.eventWithParameters});
    	assertFindCommandSuccess("find either event", currentList);
    	
    	//find tasks that contain one or more keywords entered
    	currentList = new TestTaskList(new TestTask[] {td.deadlineWithoutTime, td.floatWithoutParameter, td.floatWithParameters});
    	assertFindCommandSuccess("find either time float", currentList);

        //find after deleting one result
        commandBox.runCommand("delete 1");
    	currentList = new TestTaskList (new TestTask[] {td.eventWithoutParameter, td.eventWithLocation, td.eventWithParameters});
        assertFindCommandSuccess("find either Event", currentList);
        
        //find tasks from both incomplete and complete list
        commandBox.runCommand("done 1");
        currentList.markTasksFromList(new int[]{1}, Status.Type.Incomplete);
        assertFindCommandSuccess("find either Event", currentList);        
    }

    @Test
    public void findEitherAtEmptyList() {
        commandBox.runCommand("clear");
        TestTaskList currentList = new TestTaskList();
        assertFindCommandSuccess("find either event", currentList); //no results
    }
    
    @Test
    public void findAllAtNonEmptyList() {
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
        currentList.markTasksFromList(new int[]{1}, Status.Type.Incomplete);
        assertFindCommandSuccess("find all Two Three", currentList);        
    }
    
    @Test
    public void findAllAtEmptyList() {
    	commandBox.runCommand("clear");
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find all Two Three", currentList); //no results
    }
    
    @Test
    public void findPhraseAtNonEmptyList() {
    	commandBox.runCommand(td.taskOneToTestFind.getAddCommand());
    	commandBox.runCommand(td.taskTwoToTestFind.getAddCommand());
    	commandBox.runCommand(td.taskThreeToTestFind.getAddCommand());
    	
    	//find non existent task
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find phrase three four five", currentList);

    	//find tasks that contain the exact keywords entered
    	currentList = new TestTaskList(new TestTask[] {td.taskOneToTestFind});
    	assertFindCommandSuccess("find phrase two three", currentList);

        //find after deleting one result
        commandBox.runCommand("delete 1");
    	currentList = new TestTaskList (new TestTask[] {td.taskTwoToTestFind, td.taskThreeToTestFind});
        assertFindCommandSuccess("find phrase One three", currentList);
        
        //find tasks from both incomplete and complete list
        commandBox.runCommand("done 1");
        currentList.markTasksFromList(new int[]{1}, Status.Type.Incomplete);
        assertFindCommandSuccess("find phrase one three", currentList);  
    }
    
    @Test
    public void findPhraseAtEmptyList() {
    	commandBox.runCommand("clear");
    	TestTaskList currentList = new TestTaskList();
    	assertFindCommandSuccess("find phrase one three", currentList); //no results
    }

    @Test
    public void enterInvalidCommand() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    @Test
    public void enterEmptyKeywords() {
        commandBox.runCommand("find phrase");
        assertResultMessage(FindCommand.MESSAGE_KEYWORDS_NOT_PROVIDED);
    }
    
    @Test
    public void enterInvalidFindtype() {
        commandBox.runCommand("find something");
        assertResultMessage(FindCommand.MESSAGE_INVALID_FINDTYPE);
    }

    private void assertFindCommandSuccess(String command, TestTaskList expectedList) {
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(Status.Type.Incomplete, expectedList.getIncompleteList()));
        assertTrue(taskListPanel.isListMatching(Status.Type.Complete, expectedList.getCompleteList()));
        assertTrue(taskListPanel.isListMatching(Status.Type.Overdue, expectedList.getOverdueList()));
        assertResultMessage(String.format(Messages.MESSAGE_INCOMPLETE_TASKS_LISTED_OVERVIEW, expectedList.getIncompleteList().length)
        		+ String.format(Messages.MESSAGE_COMPLETED_TASKS_LISTED_OVERVIEW, expectedList.getCompleteList().length) 
        		+ String.format(Messages.MESSAGE_OVERDUE_TASKS_LISTED_OVERVIEW, expectedList.getOverdueList().length));

    }
}
