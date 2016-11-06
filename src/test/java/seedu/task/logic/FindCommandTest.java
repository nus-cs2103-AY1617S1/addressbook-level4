package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
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
	
	/** Private fields for testing **/
	private TaskBook expectedTB;
	private TestDataHelper helper;
	private Task tTarget1;
	private Task tTarget2;
	private Task t1;
	private Task t2;
	
	private Event eTarget1;
	private Event eTarget2;
	private Event e1;
	private Event e2;
	private List<Task> fourTasks;
	private List<Event> fourEvents;
	
	
	@Before
	public void setupHelper() throws Exception {
		helper = new TestDataHelper();
		
		tTarget1 = helper.generateTaskWithName("TargetA");
        tTarget2 = helper.generateTaskWithDescription("TargetB");
        t1 = helper.generateTaskWithName("TaskAAA");
        t2 = helper.generateTaskWithDescription("TaskBBB");
        
        eTarget1 = helper.generateEventWithName("TargetA");
        eTarget2 = helper.generateEventWithDescription("TargetB");
        e1 = helper.generateEventWithName("EventAAA");
        e2 = helper.generateEventWithDescription("EventBBB");
        
        fourTasks = helper.generateTaskList(t1, tTarget1, t2, tTarget2);
        fourEvents = helper.generateEventList(e1, eTarget1,e2, eTarget2);
        expectedTB = helper.generateTaskBookTasksAndEvents(fourTasks, fourEvents);
	}
	
    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior_task("find ", expectedMessage);
    }


    @Test
    public void execute_findWithPower_matchesSimiliarWordsInNamesOrDescription() throws Exception {
        List<Task> expectedTaskList = helper.generateTaskList(tTarget1, tTarget2);
        List<Event> expectedEventList = helper.generateEventList(eTarget1, eTarget2);
        
        helper.addTaskToModel(model, fourTasks);
        helper.addEventToModel(model, fourEvents);
        
        expectedTaskList = expectedTaskList.stream().sorted(Task.getAscComparator()).collect(Collectors.toList());
        expectedEventList = expectedEventList.stream().sorted(Event.getAscComparator()).collect(Collectors.toList());
        assertTaskAndEventCommandBehavior("find TargetX /power",
                Command.getMessageForTaskListShownSummary(expectedTaskList.size()) 
                + "\n"
                + Command.getMessageForEventListShownSummary(expectedEventList.size()),
                expectedTB,
                expectedTaskList, expectedEventList);
    }

    
    @Test
    public void execute_find_isNotCaseSensitive() throws Exception { 
        List<Task> expectedTaskList = helper.generateTaskList(tTarget1, tTarget2);
        List<Event> expectedEventList = helper.generateEventList(eTarget1, eTarget2);
        
        expectedTaskList = expectedTaskList.stream().sorted(Task.getAscComparator()).collect(Collectors.toList());
        expectedEventList = expectedEventList.stream().sorted(Event.getAscComparator()).collect(Collectors.toList());
        
        helper.addTaskToModel(model, fourTasks);
        helper.addEventToModel(model, fourEvents);
        
        assertTaskAndEventCommandBehavior("find taRgEt ",
                Command.getMessageForTaskListShownSummary(expectedTaskList.size())
                +"\n"
                + Command.getMessageForEventListShownSummary(expectedEventList.size()),
                expectedTB,
                expectedTaskList, expectedEventList);
    }
    
    @Test
    public void execute_find_noMatch() throws Exception {
        List<Task> expectedTaskList = helper.generateTaskList();
        List<Event> expectedEventList = helper.generateEventList();

        helper.addTaskToModel(model, fourTasks);
        helper.addEventToModel(model, fourEvents);
        
        assertTaskAndEventCommandBehavior("find 404NotFound /power",
                Command.getMessageForTaskListShownSummary(expectedTaskList.size())
                +"\n"
                + Command.getMessageForEventListShownSummary(expectedEventList.size()),
                expectedTB,
                expectedTaskList, expectedEventList);
    }
}
