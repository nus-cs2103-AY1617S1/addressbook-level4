package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.commands.taskcommands.ListTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.TaskManager;

public class ListTaskCommandTest {
    
    InMemoryTaskList model;
    
    @Before
    public void setup() {
        model = new TaskManager();
    }
    
    @Test
    public void listCommand_noTasks() {
        /* CommandResult should return a string that denotes that 
         * there are no tasks to list.
         */
        setupEmptyTaskList();
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
        setupOneTaskInTaskList();
        ListTaskCommand command = new ListTaskCommand();
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(String.format(ListTaskCommand.MESSAGE_SUCCESS, 1)));
    }
    
    @Test
    public void listCommand_multipleTasks() throws IllegalValueException {
        /* CommandResult should return a string that denotes that 
         * one task has been listed.
         */
        setupSomeTasksInTaskList();
        ListTaskCommand command = new ListTaskCommand();
        command.setData(model);
        CommandResult result = command.execute();
        String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(String.format(ListTaskCommand.MESSAGE_SUCCESS, 3)));
    }
    
    
    /*
     * Utility Functions
     */
    public void setupEmptyTaskList() {
        model = new TaskManager();
    }
    
    // Setting up one task in the TaskList in order to list it in the tests
    public void setupOneTaskInTaskList() throws IllegalValueException {
        model = new TaskManager();
        AddTaskCommand command = new AddTaskCommand("test");
        command.setData(model);
        command.execute();
    }
    
    // Setting up tasks in the TaskList in order to list them in the tests
    public void setupSomeTasksInTaskList() throws IllegalValueException {
        model = new TaskManager();
        // Add 3 tasks into the task manager
        for (int i = 0; i < 3; i++) {
            AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
            command.setData(model);
            command.execute();
        }
    }
    

}
