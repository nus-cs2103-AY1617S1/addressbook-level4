package guitests;

import org.junit.Test;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.ViewCommand;
import seedu.taskitty.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//@@author A0130853L
/**
 * This command test tests 4 types of command functionalities under the view command: view, view all, view done, and view date.
 *
 */
public class ViewCommandTest extends TaskManagerGuiTest {

	/**
	 * Type 1: `view all` tests.
	 */
    @Test
    public void viewAll_nonEmptyList() {
    	
    	TestTask[] expectedTodos = {td.read};
    	TestTask[] expectedDeadlines = {td.spring};
    	TestTask[] expectedEvents = {td.shop, td.dinner};
        assertViewAllResult("view all", expectedTodos, expectedDeadlines, expectedEvents); // shows original list

        //add a todo task and then view all
        commandBox.runCommand("add todo");
        TestTask[] expectedTodosAfterAddCommand = {td.read, td.todo};
    	TestTask[] expectedDeadlinesAfterAddCommand = {td.spring};
    	TestTask[] expectedEventsAfterAddCommand = {td.shop, td.dinner};
        assertViewAllResult("view all", expectedTodosAfterAddCommand, 
        		expectedDeadlinesAfterAddCommand, expectedEventsAfterAddCommand);
    }

    @Test
    public void viewAll_emptyList(){
        commandBox.runCommand("clear");
        assertViewAllResult("view all", new TestTask[0], new TestTask[0], new TestTask[0]); // empty list
    }

    @Test
    public void viewAll_invalidCommand_fail() {
        commandBox.runCommand("viewall");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * Type 2: `view done` tests.
     */
    @Test
    public void viewDone_nonEmptyList() {
    	
    	// no tasks are done
    	assertViewDoneOrDateResult("view done", new TestTask[0], new TestTask[0], new TestTask[0]); 

        // mark one task as done and then view done
        commandBox.runCommand("view all");
    	commandBox.runCommand("done e1");
    	TestTask[] expectedEventsAfterDoneCommand = {td.shop};
    	assertViewDoneOrDateResult("view done", new TestTask[0], new TestTask[0], expectedEventsAfterDoneCommand);
    }
    
    @Test
    public void viewDone_emptyList(){
        commandBox.runCommand("clear");
        assertViewDoneOrDateResult("view done", new TestTask[0], new TestTask[0], new TestTask[0]);
    }
    
    @Test
    public void viewDone_invalidCommand_fail() {
        commandBox.runCommand("viewdone");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * Type 3: `view DATE` tests.
     */
    @Test
    public void viewDate_nonEmptyList() {
    	
    	// view today 
    	TestTask[] expectedTodos = {td.read};
        assertViewDoneOrDateResult("view today", expectedTodos, new TestTask[0], new TestTask[0]); 
        
        // view xmas which natty will translate into a date
        TestTask[] expectedTodosSpecialDate = {td.read};
    	TestTask[] expectedEventsSpecialDate = {td.dinner};
    	assertViewDoneOrDateResult("view xmas", expectedTodosSpecialDate, new TestTask[0], expectedEventsSpecialDate);
        
    	// view actual date with 3 different formats
    	TestTask[] expectedTodosDate = {td.read};
    	TestTask[] expectedDeadlinesDate = {td.spring};
    	assertViewDoneOrDateResult("view 1 Jan 2017", expectedTodosDate, expectedDeadlinesDate, new TestTask[0]);
    	assertViewDoneOrDateResult("view 1-Jan-2017", expectedTodosDate, expectedDeadlinesDate, new TestTask[0]);
    	assertViewDoneOrDateResult("view 1-1-2017", expectedTodosDate, expectedDeadlinesDate, new TestTask[0]);

    	// add an event that spans over a few days and then view a date that 
    	//is in between the start and end date of that event
    	TestTask multiDayEvent = td.event;
    	commandBox.runCommand(multiDayEvent.getAddCommand());
    	TestTask[] expectedTodosAfterAddCommand = {td.read};
    	TestTask[] expectedEventsAfterDoneCommand = {td.event};
    	assertViewDoneOrDateResult("view 14 dec", expectedTodosAfterAddCommand, 
    			new TestTask[0], expectedEventsAfterDoneCommand);
        
    }
    
    @Test
    public void viewDate_emptyList(){
        commandBox.runCommand("clear");
        assertViewDoneOrDateResult("view 10 Jan", new TestTask[0], new TestTask[0], new TestTask[0]);
    }
    
    @Test
    public void viewDate_invalidCommand_fail() {
        commandBox.runCommand("view10jan");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     *  Type 4: `view` tests.
     */
    @Test
    public void view_nonEmptyList() {

    	// add an event that is already over, then run `view`
    	TestTask overEvent = td.overEvent;
    	commandBox.runCommand(overEvent.getAddCommand());
    	TestTask[] expectedTodosAfterAddCommand = {td.read};
    	TestTask[] expectedDeadlinesAfterAddCommand = {td.spring};
    	TestTask[] expectedEventsAfterAddCommand = {td.shop, td.dinner};
    	assertViewDoneOrDateResult("view", expectedTodosAfterAddCommand, 
    			expectedDeadlinesAfterAddCommand, expectedEventsAfterAddCommand);
        
    	// mark a task as done, then run `view`
    	commandBox.runCommand("done t1");
    	TestTask[] expectedDeadlinesAfterDoneCommand = {td.spring};
    	TestTask[] expectedEventsAfterDoneCommand = {td.shop, td.dinner};
    	assertViewDoneOrDateResult("view", new TestTask[0], 
    			expectedDeadlinesAfterDoneCommand, expectedEventsAfterDoneCommand);
    }
    
    @Test
    public void view_invalidCommand_fail() {
        commandBox.runCommand("vieww");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    
    /**
     * Invalid command test for all 4 functionalities.
     */
    @Test
    public void view_invalidSuffix_fail() {
        commandBox.runCommand("view date");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
    
    /**
     * Assert result method for `view done` or `view date` or `view`.
     */
    private void assertViewDoneOrDateResult(String command, TestTask[] expectedTodos,
            TestTask[] expectedDeadlines, TestTask[] expectedEvents) {
        commandBox.runCommand(command);
        assertTodoListSize(expectedTodos.length);
        assertDeadlineListSize(expectedDeadlines.length);
        assertEventListSize(expectedEvents.length);
        assertResultMessage(expectedTodos.length + expectedDeadlines.length + expectedEvents.length + " tasks listed!");
        
        assertTrue(taskListPanel.isTodoListMatching(expectedTodos));
        assertTrue(taskListPanel.isDeadlineListMatching(expectedDeadlines));
        assertTrue(taskListPanel.isEventListMatching(expectedEvents));
    }
    
    /**
     * Assert result method for `view all`.
     */
    private void assertViewAllResult(String command, TestTask[] expectedTodos,
            TestTask[] expectedDeadlines, TestTask[] expectedEvents) {
        commandBox.runCommand(command);
        assertTodoListSize(expectedTodos.length);
        assertDeadlineListSize(expectedDeadlines.length);
        assertEventListSize(expectedEvents.length);
        assertResultMessage(ViewCommand.VIEW_ALL_MESSAGE_SUCCESS);
        
        assertTrue(taskListPanel.isTodoListMatching(expectedTodos));
        assertTrue(taskListPanel.isDeadlineListMatching(expectedDeadlines));
        assertTrue(taskListPanel.isEventListMatching(expectedEvents));
    }
    
}
