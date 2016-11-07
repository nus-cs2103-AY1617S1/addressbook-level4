package seedu.task.logic;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.SelectEventCommand;
import seedu.task.logic.commands.SelectTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.Task;

//@@author A0125534L

/**
 * Responsible for testing the execution of SelectCommand
 * 
 */

public class SelectCommandTest extends CommandTest {

	// ------------------------Tests for invalid arguments----------------
	/*
	 * Command input: "select (type) (index)   "
	 * 
	 * Valid arguments: "/t", "/e" 
	 * index: "1,2,3" - numerical index of task and event list
	 * 
	 * 
	 * Invalid arguments:
	 * type: "p@ssw0rd", "/z", "/ K" 
	 * index: "a", "@"
	 * index out of range
	 *
	 */

	@Test
	public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
		assertIncorrectTypeFormatBehaviorForCommand("select p@ssw0rd", expectedMessage);
		assertIncorrectTypeFormatBehaviorForCommand("select /z", expectedMessage);
		assertIncorrectTypeFormatBehaviorForCommand("select / K", expectedMessage);
		assertIncorrectTypeFormatBehaviorForCommand("select /t a", expectedMessage);
		assertIncorrectTypeFormatBehaviorForCommand("select /e @", expectedMessage);

	}

	@Test
	public void execute_selectTask_indexOutOfRange() throws Exception {
		assertTaskIndexNotFoundBehaviorForCommand("select /t");
	}

	@Test
	public void execute_selectEvent_indexOutOfRange() throws Exception {
		assertEventIndexNotFoundBehaviorForCommand("select /e");
	}

	// ------------------------Tests for correct execution of selectcommand----------------
	/*
	 * Valid arguments
	 * 
	 * selecting valid index from the list of task selecting valid index from
	 * the list of events when index <= list size
	 */

	@Test
	public void execute_select_task_completed_successful() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task taskTesting1 = helper.generateTaskWithName("Task1");
		Task taskTesting2 = helper.generateTaskWithName("Task2");
		Task taskTesting3 = helper.generateTaskWithName("Task3");

		List<Task> threeTasks = helper.generateTaskList(taskTesting1, taskTesting2, taskTesting3);

		helper.addTaskToModel(model, threeTasks);

		TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);

		String.format(SelectTaskCommand.MESSAGE_SELECT_TASK_SUCCESS, threeTasks.get(1), expectedTB,
				expectedTB.getTaskList());
	}

	@Test
	public void execute_select_event_completed_successful() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();

		Event eventTesting1 = helper.generateEvent(1);
		Event eventTesting2 = helper.generatePastEvent(2);
		Event eventTesting3 = helper.generateEvent(3);

		List<Event> threeEvents = helper.generateEventList(eventTesting1, eventTesting2, eventTesting3);
		helper.addEventToModel(model, threeEvents);

		TaskBook expectedTB = helper.generateTaskBook_Events(threeEvents);

		String.format(SelectEventCommand.MESSAGE_SELECT_EVENT_SUCCESS, threeEvents.get(1), expectedTB,
				expectedTB.getEventList());
	}

}