package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.ListEventCommand;
import seedu.task.logic.commands.ListTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.Task;

public class ListCommandTest extends CommandTest{

    @Test
    public void execute_list_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);
        
        // not indicating which list not allowed
        assertCommandBehavior_task("list", expectedMessage);
        
        assertCommandBehavior_task("list -wrongFlag", expectedMessage);
        
        assertCommandBehavior_task("list -e -wrongFlag", expectedMessage);
    }

    @Test
    public void execute_list_showsUncompletedTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithName("Task1");
        Task tTarget2 = helper.generateTaskWithName("Task2");
        Task tTarget3 = helper.completedTask();
        
        List<Task> threeTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2);

        // prepare address book state
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("list -t",
                ListTaskCommand.MESSAGE_INCOMPLETED_SUCCESS,
                expectedTB,
                expectedList);
    }
    
    @Test
    public void execute_list_showsUncompletedEvents() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Event tTarget1 = helper.generateEventWithNameAndDuration("Event1", "yesterday 1pm","tomorrow 2pm");
        Event tTarget2 = helper.generateEventWithNameAndDuration("Event2", "Friday 4pm","Friday 5pm");
        Event tTarget3 = helper.completedEvent();
        
        List<Event> threeEvents = helper.generateEventList(tTarget1, tTarget2, tTarget3);
        TaskBook expectedTB = helper.generateTaskBook_Events(threeEvents);
        List<Event> expectedList = helper.generateEventList(tTarget1, tTarget2);

        // prepare address book state
        helper.addEventToModel(model, threeEvents);

        assertEventCommandBehavior("list -e",
                ListEventCommand.MESSAGE_INCOMPLETED_SUCCESS,
                expectedTB,
                expectedList);
    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithName("Task1");
        Task tTarget2 = helper.generateTaskWithName("Task2");
        Task tTarget3 = helper.completedTask();
        
        List<Task> threeTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2,tTarget3);

        // prepare address book state
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("list -t -a",
                ListTaskCommand.MESSAGE_ALL_SUCCESS,
                expectedTB,
                expectedList);
    }
    
    @Test
    public void execute_list_showsAllEvents() throws Exception {
        // prepare expectations
    	TestDataHelper helper = new TestDataHelper();
        Event eTarget1 = helper.generateEventWithNameAndDuration("Event1", "yesterday 1pm","tomorrow 2pm");
        Event eTarget2 = helper.generateEventWithNameAndDuration("Event2", "Friday 4pm","Friday 5pm");
        Event eTarget3 = helper.completedEvent();
        
        List<Event> threeEvents = helper.generateEventList(eTarget1, eTarget2, eTarget3);
        TaskBook expectedTB = helper.generateTaskBook_Events(threeEvents);
        List<Event> expectedList = helper.generateEventList(eTarget1, eTarget2, eTarget3);
        
        expectedList = expectedList.stream().sorted(Event::sortAsc).collect(Collectors.toList());
        // prepare address book state
        helper.addEventToModel(model, threeEvents);

        assertEventCommandBehavior("list -e -a",
                ListEventCommand.MESSAGE_ALL_SUCCESS,
                expectedTB,
                expectedList);
    }

}
