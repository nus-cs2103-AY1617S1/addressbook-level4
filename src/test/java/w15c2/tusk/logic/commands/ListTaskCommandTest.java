package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.ListTaskCommand;
import w15c2.tusk.model.Model;
import w15c2.tusk.testutil.TestUtil;

//@@author A0139708W
/**
 * Tests for ListTaskCommand.
 *
 */
public class ListTaskCommandTest {
    
    /** 
     * CommandResult should return a string that denotes that 
     * there are no tasks to list.
     */
    @Test
    public void listCommand_noTasks() throws IllegalValueException{
        Model model;
        model = TestUtil.setupEmptyTaskList();
        ListTaskCommand command = new ListTaskCommand("");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_NOTASKS));
    }
    
    /**
     *  CommandResult should return a string that denotes that 
     * all tasks have been listed.
     */
    @Test
    public void listCommand_oneTask() throws IllegalValueException {
        Model model;
        model = TestUtil.setupFloatingTasks(1);
        ListTaskCommand command = new ListTaskCommand("");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_SUCCESS));
    }
    
    /**
     *  CommandResult should return a string that denotes that 
     * all tasks have been listed.
     */
    @Test
    public void listCommand_multipleTasks() throws IllegalValueException {
        Model model;
        model = TestUtil.setupFloatingTasks(3);
        ListTaskCommand command = new ListTaskCommand("");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_SUCCESS));
    }
    
    /**
     *  CommandResult should return a string that denotes that 
     * the aliases have been listed.
     */
    @Test
    public void listCommand_alias() throws IllegalValueException {
        Model model;
        model = TestUtil.setupFloatingTasks(3);
        ListTaskCommand command = new ListTaskCommand("alias");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_ALIAS_SUCCESS));
    }
    
    /**
     * CommandResult should return a string that indicates 0 completed tasks found
     * (since there are no completed tasks).
     */
    @Test
	public void listCompletedTask_noTasksAdded() throws IllegalValueException{
    	Model model;
		model = TestUtil.setupEmptyTaskList();
		ListTaskCommand command = new ListTaskCommand("completed");
		command.setData(model);
		
		CommandResult result = command.execute();
	    String feedback = result.feedbackToUser;
	    assertTrue(feedback.equals(ListTaskCommand.MESSAGE_NO_COMPLETED_TASKS));
	}
    
    /**
     *  CommandResult should return a string that denotes that 
     * the aliases have been listed.
     */
    @Test
    public void listCompletedCommand_valid() throws IllegalValueException {
        Model model;
        model = TestUtil.setupSomeCompletedTasksInTaskList(3);
        ListTaskCommand command = new ListTaskCommand("completed");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_COMPLETED_SUCCESS));
    }
	
    
}
