//@@author A0093896H
package seedu.todo.logic;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.AddCommand;
import seedu.todo.model.task.Name;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.TaskDate;

/**
 * Test class for the add command's logic
 */
public class AddLogicTest extends CommandLogicTest {

    @Before
    public void add_setup() {}
    
    @Test
    public void execute_add_invalidTaskData() throws IllegalValueException {
        assertCommandBehavior(
                "add Valid Name on vdvd ; a line of details", TaskDate.MESSAGE_DATETIME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name on 12/12/1234 by asdasdsad ; a line of details", TaskDate.MESSAGE_DATETIME_CONSTRAINTS);

    }

    @Test
    public void execute_add_fullTask_successful() throws IllegalValueException {
        Task toBeAdded = helper.generateFullTask(0);
        Task toBeAddedRecur = helper.generateFullTask(1);
        
        expectedTDL.addTask(toBeAdded);
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedTDL,
                expectedTDL.getTaskList());
        
        expectedTDL.addTask(toBeAddedRecur);
        assertCommandBehavior(helper.generateAddCommandRecurring(toBeAddedRecur),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedRecur),
                expectedTDL, expectedTDL.getTaskList());
    }
    
    @Test
    public void execute_add_onTask_successful() throws IllegalValueException {
        
        Task toBeAdded = helper.generateTaskWithDates("today", null);
        Task toBeAddedRecur = helper.generateTaskWithDates("tomorrow", null);
        
        expectedTDL.addTask(toBeAdded);        
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTDL,
                expectedTDL.getTaskList());
        
        expectedTDL.addTask(toBeAddedRecur);
        assertCommandBehavior(helper.generateAddCommand(toBeAddedRecur),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAddedRecur),
                expectedTDL,
                expectedTDL.getTaskList());
    }

    @Test
    public void execute_add_floatingTask_successful() throws IllegalValueException {
        // setup expectations
        Task toBeAdded = helper.generateTaskWithDates(null, null);        
        expectedTDL.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTDL,
                expectedTDL.getTaskList());
    }
    
    @Test
    public void execute_add_deadlineTask_successful() throws IllegalValueException {
        Task toBeAdded = helper.generateTaskWithDates(null, "Tomorrow");        
        expectedTDL.addTask(toBeAdded);
        
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTDL,
                expectedTDL.getTaskList());
    }
    
    @Test
    public void execute_addDuplicate_notAllowed() throws IllegalValueException {
        Task toBeAdded = helper.generateFullTask(0);
        expectedTDL.addTask(toBeAdded);

        model.addTask(toBeAdded); // task already in internal address book
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedTDL,
                expectedTDL.getTaskList());
    }
}
