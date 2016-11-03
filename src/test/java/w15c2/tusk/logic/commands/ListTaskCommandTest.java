package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.ListTaskCommand;
import w15c2.tusk.model.task.Model;
import w15c2.tusk.testutil.TestUtil;

//@@author A0139708W
public class ListTaskCommandTest {
    
    @Test
    public void listCommand_noTasks() throws IllegalValueException{
        /* CommandResult should return a string that denotes that 
         * there are no tasks to list.
         */
        Model model;
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
        Model model;
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
        Model model;
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
        Model model;
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
    	Model model;
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
        Model model;
        model = TestUtil.setupSomeCompletedTasksInTaskList(3);
        ListTaskCommand command = new ListTaskCommand("completed");
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_COMPLETED_SUCCESS));
    }
	
    
}
