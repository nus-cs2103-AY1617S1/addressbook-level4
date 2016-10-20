package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
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
        assertCommandBehavior_task(
                "add", expectedMessage);
        
        //empty deadline string
        assertCommandBehavior_task(
                "add validName /desc validDescription /by   ", expectedMessage);
    }


    
    /*
	 * Possible EP of Valid Deadline 
	 * 	 With the flexibility built-in the NLP parsing, the partition is not total. 
	 * 	 - Relative dates phrases with numbers representing time
	 * 	 - Numbers with or without ':' representing time, ie: 12:30:10 
	 *   - M/D/Y with valid range of M, D
	 *   - M/D with valid range of M, D
	 *   - Number Day, ie: 2 Monday, meaning the Monday in 2 week's time.
	 *   - Invalid words followed by number, ie: haha 1pm.
	 *   
	 * Possible EP of Invalid Deadline 
	 * 	 - Phrases in abbreviation or not referring to  relative date.
	 *   - Characters that are not related to date/time.
	 *   - null
	 *   - Empty String 
	 *   
	 * Due to the flexibility of prettytime library, the constrain on the deadline format will be looser.
	 * We will not report invalid formats for deadlines, but provide elaborative feedback to users 
	 * on the parsed result. 
	 * 
	 * Possible valid use cases:
	 * 	 - DAY HH:MM
	 * 	 - M/D[/Y]
	 * 	 - RELATIVE_DAY TIME[pm|am]
	 *   - NO_WEEKS_LATER DAY
	 *   - DATE
	 *   - DATE HHMM
	 * 	
	 * Possible invalid use cases:
	 * 	 - RANDOM_WORD
	 *   - INVALID_ABBREVIATION 
	 */
    @Test 
    public void execute_addTask_invalidTaskData() throws Exception {
    	//Invalid Name 
    	assertCommandBehavior_task(
                "add []\\[;] /desc nil /by 30-12-16", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior_task(
                "add []\\[;] /desc nil", Name.MESSAGE_NAME_CONSTRAINTS);
        
        //Invalid Deadline
        assertCommandBehavior_task(
                "add validName /desc validDesc /by randOmWord123", Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        
        //invalid abbreviation
        assertCommandBehavior_task(
                "add validName /desc validDesc /by Septem", Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
    }
    
    @Test
    public void execute_addEvent_invalidEventData() throws Exception {
        assertCommandBehavior_task(
                "add []\\[;] /desc nil /from 30-12-16 31-12-16", Name.MESSAGE_NAME_CONSTRAINTS);
        
        //invalid seperator
        assertCommandBehavior_task("add valideventName /desc nil /from today >> yesterday", EventDuration.MESSAGE_DURATION_CONSTRAINTS);
        
        // no start time not allowed. 
        assertCommandBehavior_task("add valideventName /desc nil /from  > today 5pm", EventDuration.MESSAGE_DURATION_CONSTRAINTS);
        
        //invalid start time not allowed. 
        assertCommandBehavior_task("add valideventName /desc nil /from  hahaha > today 5pm", EventDuration.MESSAGE_DURATION_CONSTRAINTS);
    }

    @Test
    public void execute_addTaskWithDeadline_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        
        // different argument to cover use cases as mentioned above
        Task tTarget1 = helper.generateTaskWithDeadline("Friday 11:01");
        Task tTarget2 = helper.generateTaskWithDeadline("November 11");
        Task tTarget3 = helper.generateTaskWithDeadline("next Friday 2pm");
        Task tTarget4 = helper.generateTaskWithDeadline("2 Monday");
        Task tTarget5 = helper.generateTaskWithDeadline("12/30/2016");
        Task tTarget6 = helper.generateTaskWithDeadline("12/30/2016 11:12");
        
        TaskBook expectedAB = new TaskBook();
        List<Task> targetList = helper.generateTaskList(tTarget1, tTarget2, tTarget3, tTarget4, tTarget5, tTarget6);
        
        for(Task target: targetList) {
        	expectedAB.addTask(target);
        	assertTaskCommandBehavior(helper.generateAddTaskCommand(target),
                    String.format(AddTaskCommand.MESSAGE_SUCCESS, target),
                    expectedAB,
                    expectedAB.getTaskList());
        }
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
