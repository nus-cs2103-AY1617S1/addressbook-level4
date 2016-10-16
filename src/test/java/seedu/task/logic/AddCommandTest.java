package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.task.logic.TestDataHelper;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.AddEventCommand;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Deadline;
import seedu.task.model.item.Event;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Name;
import seedu.task.model.item.Task;

public class AddCommandTest extends CommandTest{

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add", expectedMessage);
    }

    @Test
    public void execute_addTask_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] /desc nil /by 30-12-16", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add []\\[;] /desc nil", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name /desc nil /by 30-12-111", Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
    }
    
    @Test
    public void execute_addEvent_invalidEventData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] /desc nil /from 30-12-16 31-12-16", Name.MESSAGE_NAME_CONSTRAINTS);
        
        //invalid seperator
        assertCommandBehavior("add valideventName /desc nil /from today >> yesterday", EventDuration.MESSAGE_DURATION_CONSTRAINTS);
        
        // no start time not allowed. 
        assertCommandBehavior("add valideventName /desc nil /from  > today 5pm", EventDuration.MESSAGE_DURATION_CONSTRAINTS);
        
        //invalid start time not allowed. 
        assertCommandBehavior("add valideventName /desc nil /from  hahaha > today 5pm", EventDuration.MESSAGE_DURATION_CONSTRAINTS);
    }

    @Test
    public void execute_addTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),
                String.format(AddTaskCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_addFloatTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingFloatTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertTaskCommandBehavior(helper.generateAddFloatTaskCommand(toBeAdded),
                String.format(AddTaskCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_addEvent_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);

        // execute command and verify result
        assertEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),
                String.format(AddEventCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getEventList());

    }

    @Test
    public void execute_addTaskDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task book

        // execute command and verify result
        assertTaskCommandBehavior(
                helper.generateAddTaskCommand(toBeAdded),
                AddTaskCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_addEventDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);

        // setup starting state
        model.addEvent(toBeAdded); // event already in internal task book

        // execute command and verify result
        assertEventCommandBehavior(
                helper.generateAddEventCommand(toBeAdded),
                AddEventCommand.MESSAGE_DUPLICATE_EVENT,
                expectedAB,
                expectedAB.getEventList());

    }
    

}
