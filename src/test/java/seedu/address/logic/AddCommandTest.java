package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.CommandTest.TestDataHelper;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Name;
import seedu.address.model.task.Recurring;
import seedu.address.model.task.Task;

public class AddCommandTest extends CommandTest{
    /*
     * 
     */
    
    //Invalid argument format
    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add Valid Name 02.03.2014 e/02.03.2014 t/validtag.butNoStartDatePrefix", expectedMessage);
        assertCommandBehavior(
                "add n/Valid Name s/02.03.2014 02.03.2014 t/validtag.butNoPrefix", expectedMessage);
        assertCommandBehavior(
                "add d/01.01.2016 t/validTag", expectedMessage);
        assertCommandBehavior("add",expectedMessage);
    }
    
    
    //Invalid data field
    @Test
    public void execute_add_invalidTaskData() throws Exception {
        //Invalid name
        assertCommandBehavior(
                "add n/[]\\[;] d/11.12.2016", Name.MESSAGE_NAME_CONSTRAINTS);
        //invalid recurring frequency
        assertCommandBehavior(
                "add n/Valid Name d/01.01.2016 r/everyday",Recurring.MESSAGE_RECURRING_CONSTRAINTS);
        //Invalid tag
        assertCommandBehavior(
                "add n/Valid Name d/11.12.2016-14 t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
        
    }
    /*
     * 2) Successful adding of floating task, deadline task and events
     *  - add floating task
     *  - add deadline task
     *  - add events
     *  - add recurring deadline task
     *  - add recurring event
     */

    @Test
    public void execute_addFloatingTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.getFloatingTask();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
  
        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_TASK_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    

    @Test
    public void execute_addDeadlineTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.getDeadlineTask();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
  
        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_TASK_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    

    @Test
    public void execute_addEvent_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.getEvent();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
  
        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_EVENT_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_addRecurringDeadlineTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.getRecurringDeadlineTask();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
  
        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_TASK_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_addRecurringEvent_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.getRecurringEvent();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
  
        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_EVENT_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_flexiAddEvent_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.getRecurringEvent();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);
  
        // execute command and verify result
        assertCommandBehavior(helper.generateFlexiAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_EVENT_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    


}
