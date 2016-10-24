package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.ListTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

public class ListTaskCommandTest {
    
    @Test
    public void listCommand_noTasks() throws IllegalValueException{
        /* CommandResult should return a string that denotes that 
         * there are no tasks to list.
         */
        InMemoryTaskList model;
        model = TestUtil.setupEmptyTaskList();
        ListTaskCommand command = new ListTaskCommand("");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_NOTASKS));
    }
    
    @Test
    public void listCommand_oneTask() throws IllegalValueException {
        /* CommandResult should return a string that denotes that 
         * all tasks have been listed.
         */
        InMemoryTaskList model;
        model = TestUtil.setupFloatingTasks(1);
        ListTaskCommand command = new ListTaskCommand("");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_SUCCESS));
    }
    
    @Test
    public void listCommand_multipleTasks() throws IllegalValueException {
        /* CommandResult should return a string that denotes that 
         * all tasks have been listed.
         */
        InMemoryTaskList model;
        model = TestUtil.setupFloatingTasks(3);
        ListTaskCommand command = new ListTaskCommand("");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_SUCCESS));
    }
    
    @Test
    public void listCommand_alias() throws IllegalValueException {
        /* CommandResult should return a string that denotes that 
         * the aliases have been listed.
         */
        InMemoryTaskList model;
        model = TestUtil.setupFloatingTasks(3);
        ListTaskCommand command = new ListTaskCommand("alias");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_ALIAS_SUCCESS));
    }
    
    @Test
	/*
	 * CommandResult should return a string that indicates 0 completed tasks found
	 * (since there are no completed tasks).
	 */
	public void listCompletedTask_noTasksAdded() throws IllegalValueException{
    	InMemoryTaskList model;
		model = TestUtil.setupEmptyTaskList();
		ListTaskCommand command = new ListTaskCommand("completed");
		command.setData(model);
		
		CommandResult result = command.execute();
	    String feedback = result.feedbackToUser;
	    assertTrue(feedback.equals(ListTaskCommand.MESSAGE_NO_COMPLETED_TASKS));
	}
    
    @Test
    public void listCompletedCommand_valid() throws IllegalValueException {
        /* CommandResult should return a string that denotes that 
         * the aliases have been listed.
         */
        InMemoryTaskList model;
        model = TestUtil.setupSomeCompletedTasksInTaskList(3);
        ListTaskCommand command = new ListTaskCommand("completed");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_COMPLETED_SUCCESS));
    }
	
    
}
