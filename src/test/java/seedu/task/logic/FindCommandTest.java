package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FindCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.Task;

//@@author A0144702N

public class FindCommandTest extends CommandTest {
	/*
	 * Test heuristics refer to FindCommand GuiTest
	 */
	
    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior_task("find ", expectedMessage);
    }


    @Test
    public void execute_find_onlyMatchesFullWordsInNamesOrDescription() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        //prepare Tasks
        Task tTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task tTarget2 = helper.generateTaskWithDescription("KEY bla bla bla");
        
        Task t1 = helper.generateTaskWithName("KE Y");
        Task t2 = helper.generateTaskWithDescription("KE Y");

        //prepare Events
        Event eTarget1 = helper.generateEventWithName("bla bla KEY bla");
        Event eTarget2 = helper.generateEventWithDescription("KEY bla bla bla");

        Event e1 = helper.generateEventWithName("KE Y");
        Event e2 = helper.generateEventWithDescription("KE YYYY");
        
        
        List<Task> fourTasks = helper.generateTaskList(t1, tTarget1, t2, tTarget2);
        List<Event> fourEvents = helper.generateEventList(e1, eTarget1, e2, eTarget2);
        
        TaskBook expectedAB = helper.generateTaskBookTasksAndEvents(fourTasks, fourEvents);
        
        List<Task> expectedTaskList = helper.generateTaskList(tTarget1, tTarget2);
        List<Event> expectedEventList = helper.generateEventList(eTarget1, eTarget2);
        
        helper.addTaskToModel(model,fourTasks);
        helper.addEventToModel(model, fourEvents);
        
        expectedTaskList = expectedTaskList.stream().sorted(Task.getAscComparator()).collect(Collectors.toList());
        expectedEventList = expectedEventList.stream().sorted(Event.getAscComparator()).collect(Collectors.toList());
        assertTaskAndEventCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedTaskList.size()) 
                + "\n"
                + Command.getMessageForEventListShownSummary(expectedEventList.size()),
                expectedAB,
                expectedTaskList, expectedEventList);
    }

    
    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        //Tasks
        Task t1 = helper.generateTaskWithName("bla bla Key non capital bla");
        Task tTarget1 = helper.generateTaskWithName("KEY haha");
        
        Task t2 = helper.generateTaskWithDescription("bla dsda Key haa");
        Task tTarget2 = helper.generateTaskWithDescription("blada KEY haa");
        
        //Events
        Event e1 = helper.generateEventWithName("blabla kEY keY");
        Event eTarget1 = helper.generateEventWithName("blabla KEY keY");
        
        Event e2 = helper.generateEventWithDescription("key key KEy");
        Event eTarget2 = helper.generateEventWithDescription("keasdsy KEY");
        
        
        List<Task> fourTasks = helper.generateTaskList(t1, tTarget1, t2, tTarget2);
        List<Event> fourEvents = helper.generateEventList(e1, eTarget1, e2, eTarget2);
        
        TaskBook expectedAB = helper.generateTaskBookTasksAndEvents(fourTasks, fourEvents);
        
        List<Task> expectedTaskList = fourTasks;
        expectedTaskList = expectedTaskList.stream().sorted(Task.getAscComparator()).collect(Collectors.toList());
        
        List<Event> expectedEventList = fourEvents;
        expectedEventList = expectedEventList.stream().sorted(Event.getAscComparator()).collect(Collectors.toList());
        helper.addTaskToModel(model, fourTasks);
        helper.addEventToModel(model, fourEvents);

        assertTaskAndEventCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedTaskList.size())
                +"\n"
                + Command.getMessageForEventListShownSummary(expectedEventList.size()),
                expectedAB,
                expectedTaskList, expectedEventList);
    }

    
    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
        //Tasks
        Task t1 = helper.generateTaskWithName("bla bla Key non capital bla");
        Task tTarget1 = helper.generateTaskWithName("KEY haha");
        
        Task t2 = helper.generateTaskWithDescription("bla dsda Key haa");
        Task tTarget2 = helper.generateTaskWithDescription("blada KEY haa");
        
        //Events
        Event e1 = helper.generateEventWithName("blabla kEY keY");
        Event eTarget1 = helper.generateEventWithName("blabla KEY keY");
        
        Event e2 = helper.generateEventWithDescription("key key KEy");
        Event eTarget2 = helper.generateEventWithDescription("keasdsy KEY");
        
        
        List<Task> fourTasks = helper.generateTaskList(t1, tTarget1, t2, tTarget2);
        List<Event> fourEvents = helper.generateEventList(e1, eTarget1, e2, eTarget2);
        
        TaskBook expectedAB = helper.generateTaskBookTasksAndEvents(fourTasks, fourEvents);
        
        List<Task> expectedTaskList = fourTasks;
        expectedTaskList = expectedTaskList.stream().sorted(Task.getAscComparator()).collect(Collectors.toList());
        List<Event> expectedEventList = fourEvents;
        expectedEventList = expectedEventList.stream().sorted(Event.getAscComparator()).collect(Collectors.toList());
        
        helper.addTaskToModel(model, fourTasks);
        helper.addEventToModel(model, fourEvents);


			        assertTaskAndEventCommandBehavior("find KEY rAnDom",
                Command.getMessageForTaskListShownSummary(expectedTaskList.size())
                +"\n"
                + Command.getMessageForEventListShownSummary(expectedEventList.size()),
                expectedAB,
                expectedTaskList, expectedEventList);
    }
}
