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
	 * 
	 *  * EQ of Valid Find Command:
	 * 	1. with valid similar keyword of word distance 1 less than 1
	 * 	2. contains one of keywords with word distance 1 under power search mode 
	 * 
	 * Tested Invalid Find Commands:
	 * 	1. No argument
	 * 	2. Unknown Command
	 * 
	 * Tested Valid Use cases:
	 * 	1. similar keywords match task/events only
	 * 	2. similar keywords match task and events both
	 * 	3. case-insensitive match on tasks and events.
	 * 	4. have similar words under power search 
	 * 	4. no match
	 */
	
    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior_task("find ", expectedMessage);
    }


    @Test
    public void execute_findWithPower_matchesSimiliarWordsInNamesOrDescription() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        //prepare Tasks
        Task tTarget1 = helper.generateTaskWithName("TargetA");
        Task tTarget2 = helper.generateTaskWithDescription("TargetB");
        
        Task t1 = helper.generateTaskWithName("NotaTarget");
        Task t2 = helper.generateTaskWithDescription("NotATarget");

        //prepare Events
        Event eTarget1 = helper.generateEventWithName("TargetA");
        Event eTarget2 = helper.generateEventWithDescription("TargetB");

        Event e1 = helper.generateEventWithName("EventAAA");
        Event e2 = helper.generateEventWithDescription("EventBBB");
        
        
        List<Task> fourTasks = helper.generateTaskList(t1, tTarget1, t2, tTarget2);
        List<Event> fourEvents = helper.generateEventList(e1, eTarget1, e2, eTarget2);
        
        TaskBook expectedAB = helper.generateTaskBookTasksAndEvents(fourTasks, fourEvents);
        
        List<Task> expectedTaskList = helper.generateTaskList(tTarget1, tTarget2);
        List<Event> expectedEventList = helper.generateEventList(eTarget1, eTarget2);
        
        helper.addTaskToModel(model,fourTasks);
        helper.addEventToModel(model, fourEvents);
        
        expectedTaskList = expectedTaskList.stream().sorted(Task.getAscComparator()).collect(Collectors.toList());
        expectedEventList = expectedEventList.stream().sorted(Event.getAscComparator()).collect(Collectors.toList());
        assertTaskAndEventCommandBehavior("find TargetX /power",
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
        Task t1 = helper.generateTaskWithName("I am not a target lah");
        Task tTarget1 = helper.generateTaskWithName("I AM A TARGET");
        
        Task t2 = helper.generateTaskWithDescription("I am not a target");
        Task tTarget2 = helper.generateTaskWithDescription("I am a target as well");
        
        //Events
        Event e1 = helper.generateEventWithName("I AM NOT A TARGET");
        Event eTarget1 = helper.generateEventWithName("I AM A TARGET");
        
        Event e2 = helper.generateEventWithDescription("I am NOT a target");
        Event eTarget2 = helper.generateEventWithDescription("I am a target");
        
        
        List<Task> fourTasks = helper.generateTaskList(t1, tTarget1, t2, tTarget2);
        List<Event> fourEvents = helper.generateEventList(e1, eTarget1,e2, eTarget2);
        
        TaskBook expectedAB = helper.generateTaskBookTasksAndEvents(fourTasks, fourEvents);
        
        List<Task> expectedTaskList = helper.generateTaskList(tTarget1, tTarget2);
        List<Event> expectedEventList = helper.generateEventList(eTarget1, eTarget2);
        
        expectedTaskList = expectedTaskList.stream().sorted(Task.getAscComparator()).collect(Collectors.toList());
        expectedEventList = expectedEventList.stream().sorted(Event.getAscComparator()).collect(Collectors.toList());
        
        helper.addTaskToModel(model, fourTasks);
        helper.addEventToModel(model, fourEvents);

        assertTaskAndEventCommandBehavior("find I am a target",
                Command.getMessageForTaskListShownSummary(expectedTaskList.size())
                +"\n"
                + Command.getMessageForEventListShownSummary(expectedEventList.size()),
                expectedAB,
                expectedTaskList, expectedEventList);
    }
    
    @Test
    public void execute_find_noMatch() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        //Tasks
        Task t1 = helper.generateTaskWithName("I am not a target lah");
        Task t2 = helper.generateTaskWithDescription("I am not a target");
        
        //Events
        Event e1 = helper.generateEventWithName("I AM NOT A TARGET");
        Event e2 = helper.generateEventWithDescription("I am NOT a target");
        
        
        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        List<Event> twoEvents = helper.generateEventList(e1, e2);
        
        TaskBook expectedAB = helper.generateTaskBookTasksAndEvents(twoTasks, twoEvents);
        
        List<Task> expectedTaskList = helper.generateTaskList();
        List<Event> expectedEventList = helper.generateEventList();
        
        helper.addTaskToModel(model, twoTasks);
        helper.addEventToModel(model, twoEvents);

        assertTaskAndEventCommandBehavior("find I am a target X",
                Command.getMessageForTaskListShownSummary(expectedTaskList.size())
                +"\n"
                + Command.getMessageForEventListShownSummary(expectedEventList.size()),
                expectedAB,
                expectedTaskList, expectedEventList);
    }
    
    

}
