package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.ListTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

public class ListTaskCommandTest {
    
    @Test
    public void listCommand_noTasks() {
        /* CommandResult should return a string that denotes that 
         * there are no tasks to list.
         */
        InMemoryTaskList model;
        model = TestUtil.setupEmptyTaskList();
        ListTaskCommand command = new ListTaskCommand();
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_NOTASKS));
    }
    
    @Test
    public void listCommand_oneTask() throws IllegalValueException {
        /* CommandResult should return a string that denotes that 
         * one task has been listed.
         */
        InMemoryTaskList model;
        model = TestUtil.setupSomeTasksInTaskList(1);
        ListTaskCommand command = new ListTaskCommand();
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_SUCCESS));
    }
    
    @Test
    public void listCommand_multipleTasks() throws IllegalValueException {
        /* CommandResult should return a string that denotes that 
         * one task has been listed.
         */
        InMemoryTaskList model;
        model = TestUtil.setupSomeTasksInTaskList(3);
        ListTaskCommand command = new ListTaskCommand();
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(ListTaskCommand.MESSAGE_SUCCESS));
    }
    

}
