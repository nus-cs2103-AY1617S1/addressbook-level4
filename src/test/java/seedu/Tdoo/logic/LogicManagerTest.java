package seedu.Tdoo.logic;

import com.google.common.eventbus.Subscribe;

import seedu.Tdoo.commons.core.EventsCenter;
import seedu.Tdoo.commons.core.Messages;
import seedu.Tdoo.commons.events.model.DeadlineListChangedEvent;
import seedu.Tdoo.commons.events.model.EventListChangedEvent;
import seedu.Tdoo.commons.events.model.TodoListChangedEvent;
import seedu.Tdoo.commons.events.ui.JumpTodoListRequestEvent;
import seedu.Tdoo.commons.events.ui.ShowHelpRequestEvent;
import seedu.Tdoo.logic.Logic;
import seedu.Tdoo.logic.LogicManager;
import seedu.Tdoo.logic.commands.*;
import seedu.Tdoo.model.Model;
import seedu.Tdoo.model.ModelManager;
import seedu.Tdoo.model.ReadOnlyTaskList;
import seedu.Tdoo.model.TaskList;
import seedu.Tdoo.model.task.*;
import seedu.Tdoo.model.task.attributes.*;
import seedu.Tdoo.storage.StorageManager;
import seedu.Tdoo.ui.CommandBox;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.Tdoo.commons.core.Messages.*;

public class LogicManagerTest {

	/**
	 * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
	 */
	@Rule
	public TemporaryFolder saveFolder = new TemporaryFolder();

	private Model model;
	private Logic logic;

	// These are for checking the correctness of the events raised
	private ReadOnlyTaskList latestSavedTodoList;
	private ReadOnlyTaskList latestSavedEventList;
	private ReadOnlyTaskList latestSavedDeadlineList;
	private boolean helpShown;
	private int targetedJumpIndex;

	@Subscribe
	private void handleLocalModelChangedEvent(TodoListChangedEvent abce) {
		latestSavedTodoList = new TaskList(abce.data);
	}

	@Subscribe
	// @@author A0139920A
	private void handleLocalModelChangedEvent(EventListChangedEvent abce) {
		latestSavedEventList = new TaskList(abce.data);
	}

	@Subscribe
	// @@author A0139920A
	private void handleLocalModelChangedEvent(DeadlineListChangedEvent abce) {
		latestSavedDeadlineList = new TaskList(abce.data);
	}

	@Subscribe
	private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
		helpShown = true;
	}

	@Before
	// @@author A0132157M reused
	public void setup() {
		model = new ModelManager();
		String tempTodoListFile = saveFolder.getRoot().getPath() + "TempTodoList.xml";
		String tempEventListFile = saveFolder.getRoot().getPath() + "TempEventList.xml";
		String tempDeadlineListFile = saveFolder.getRoot().getPath() + "TempDeadlineList.xml";
		String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
		logic = new LogicManager(model,
				new StorageManager(tempTodoListFile, tempEventListFile, tempDeadlineListFile, tempPreferencesFile));
		EventsCenter.getInstance().registerHandler(this);

		latestSavedTodoList = new TaskList(model.getTodoList()); // last saved
																	// assumed
																	// to be up
																	// to date
																	// before.
		latestSavedEventList = new TaskList(model.getEventList()); // last saved
																	// assumed
																	// to be up
																	// to date
																	// before.
		latestSavedDeadlineList = new TaskList(model.getDeadlineList()); // last
																			// saved
																			// assumed
																			// to
																			// be
																			// up
																			// to
																			// date
																			// before.
		helpShown = false;
		targetedJumpIndex = -1; // non yet
	}

	@After
	public void teardown() {
		EventsCenter.clearSubscribers();
	}

	@Test
	public void execute_invalid() throws Exception {
		String invalidCommand = "       ";
		assertCommandBehavior(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
	}

	/**
	 * Executes the command and confirms that the result message is correct.
	 * Both the 'TodoList' and the 'last shown list' are expected to be empty.
	 * 
	 * @see #assertCommandBehavior(String, String, ReadOnlyTodoList, List)
	 */
	private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
		assertCommandBehavior(inputCommand, expectedMessage, new TaskList(), Collections.emptyList());
	}

	/**
	 * Executes the command and confirms that the result message is correct and
	 * also confirms that the following three parts of the LogicManager object's
	 * state are as expected:<br>
	 * - the internal TodoList data are same as those in the
	 * {@code expectedTodoList} <br>
	 * - the backing list shown by UI matches the {@code shownList} <br>
	 * - {@code expectedTodoList} was saved to the storage file. <br>
	 */
	private void assertCommandBehavior(String inputCommand, String expectedMessage, ReadOnlyTaskList expectedTodoList,
			List<? extends ReadOnlyTask> expectedShownList) throws Exception {

		// Execute the command
		CommandResult result = logic.execute(inputCommand);

		// Confirm the ui display elements should contain the right data
		assertEquals(expectedMessage, result.feedbackToUser);
		// assertEquals(expectedShownList, model.getFilteredTodoList());
		// Confirm the state of data (saved and in-memory) is as expected
		// assertEquals(expectedTodoList, model.getTodoList());
		// assertEquals(expectedTodoList, latestSavedTodoList);
	}

	@Test
	public void execute_unknownCommandWord() throws Exception {
		String unknownCommand = "uicfhmowqewca";
		assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
	}

	@Test
	public void execute_help() throws Exception {
		assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
		assertTrue(helpShown);
	}

	@Test
	public void execute_exit() throws Exception {
		assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
	}

	@Test
	// @@author A0132157M
	public void execute_clear() throws Exception {
		// TestDataHelper helper = new TestDataHelper();
		model.addTask(new Todo(new Name("todo 1"), new StartDate("11-11-2016"), new EndDate("12-11-2016"),
				new Priority("1"), ("false")));
		model.addTask(new Event(new Name("event 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"),
				new StartTime("01:00"), new EndTime("02:00"), ("false")));
		model.addTask(
				new Deadline(new Name("deadline 123"), new StartDate("12-12-2016"), new EndTime("01:00"), ("false")));

		assertCommandBehavior("clear todo", ClearCommand.TODO_MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
		assertCommandBehavior("clear event", ClearCommand.EVENT_MESSAGE_SUCCESS, new TaskList(),
				Collections.emptyList());
		assertCommandBehavior("clear deadline", ClearCommand.DEADLINE_MESSAGE_SUCCESS, new TaskList(),
				Collections.emptyList());
	}

	@Test
	// @@author A0132157M reused
	public void execute_add_invalidArgsFormat() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
		assertCommandBehavior("add wrong args wrong args", expectedMessage);
		assertCommandBehavior("add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, Todo", expectedMessage);
		assertCommandBehavior("add Valid Name p/12345 valid@email.butNoPrefix a/valid, Todo", expectedMessage);
		assertCommandBehavior("add Valid Name p/12345 e/valid@email.butNoTodoPrefix valid, Todo", expectedMessage);
	}

	@Test
	// @@author A0132157M reused
	public void execute_add_invalidtaskData() throws Exception {
		assertCommandBehavior("add todo[]\\[;] /11-12-2016 e/valid@e.mail a/valid, Todo",
				"Invalid command format! \n" + AddCommand.MESSAGE_USAGE);
		assertCommandBehavior("add event from/33-12-2016 Name p/not_numbers e/valid@e.mail a/valid, Todo",
				"Invalid command format! \n" + AddCommand.MESSAGE_USAGE);
		assertCommandBehavior("add deadline Name p/12345 e/notAnEmail a/valid, Todo",
				"Invalid command format! \n" + AddCommand.MESSAGE_USAGE);

	}

	@Test
	// @@author A0132157M reused
	public void execute_addTodo_successful() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();

		Todo toBeAdded = helper.todoHelper();
		TaskList expectedAB = new TaskList();

		expectedAB.addTask(toBeAdded);

		// execute command and verify result
		assertCommandBehavior(
				helper.generateAddTodoCommand(toBeAdded), String
						.format(AddCommand.MESSAGE_SUCCESS,
								toBeAdded.getName().toString() + " from/" + toBeAdded.getStartDate().date.toString()
										+ " to/" + toBeAdded.getEndDate().endDate.toString()),
				expectedAB, expectedAB.getTaskList());
	}

	@Test
	// @@author A0132157M
	public void execute_addEvent_unsuccessful() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();

		Event toBeAdded = helper.eventHelper();
		TaskList expectedAB = new TaskList();

		expectedAB.addTask(toBeAdded);

		// execute command and verify result
		assertCommandBehavior(helper.generateAddEventCommand(toBeAdded),
				String.format(StartDate.MESSAGE_DATE_CONSTRAINTS), expectedAB, expectedAB.getTaskList());
	}

	@Test
	// @@author A0132157M
	public void execute_addDeadline_successful() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();

		Deadline toBeAdded = helper.deadlineHelper();
		TaskList expectedAB = new TaskList();

		expectedAB.addTask(toBeAdded);

		// execute command and verify result
		assertCommandBehavior(helper.generateAddDeadlineCommand(toBeAdded),
				String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getName().toString()), expectedAB,
				expectedAB.getTaskList());
	}

	// @Test
	// public void execute_addTodoDuplicate_notAllowed() throws Exception {
	// // setup expectations
	// TestDataHelper helper = new TestDataHelper();
	//
	// Todo toBeAdded = helper.todoHelper();
	// TaskList expectedAB = new TaskList();
	//
	// expectedAB.addTask(toBeAdded);
	//
	// // setup starting state
	// model.addTask(toBeAdded); // task already in internal TodoList
	// //LogsCenter.getLogger(LogicManagerTest.class).info("task of currentlist:
	// " + toBeAdded.toString());
	//
	//
	// // execute command and verify result
	// assertCommandBehavior(
	// helper.generateAddTodoCommand(toBeAdded),
	// AddCommand.MESSAGE_DUPLICATE_TASK,
	// expectedAB,
	// expectedAB.getTaskList());
	// }

	// @Test
	// //@@author A0132157M
	// public void execute_addEventDuplicate_notAllowed() throws Exception {
	// // setup expectations
	// TestDataHelper helper = new TestDataHelper();
	//
	// Event toBeAdded = helper.eventHelper();
	// TaskList expectedAB = new TaskList();
	// expectedAB.addTask(toBeAdded);
	//
	// // setup starting state
	// model.addTask(toBeAdded); // task already in internal TodoList
	//
	// // execute command and verify result
	// assertCommandBehavior(
	// helper.generateAddEventCommand(toBeAdded),
	// AddCommand.MESSAGE_DUPLICATE_TASK,
	// expectedAB,
	// expectedAB.getTaskList());
	// }

	@Test
	// @@author A0132157M
	public void execute_addDeadlineDuplicate_notAllowed() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();

		Deadline toBeAdded = helper.deadlineHelper();
		TaskList expectedAB = new TaskList();

		expectedAB.addTask(toBeAdded);

		// setup starting state
		model.addTask(toBeAdded); // task already in internal TodoList

		// execute command and verify result
		assertCommandBehavior(helper.generateAddDeadlineCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK,
				expectedAB, expectedAB.getTaskList());
	}

	@Test
	// @@author A0139920A reused
	public void execute_list_showsAllTodoTasks() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("todo 1");
		Task pTarget2 = helper.generatetask("todo 2");
		Task pTarget3 = helper.generatetask("todo 3");
		List<Task> threetasks = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(threetasks);
		List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

		// prepare TodoList state
		helper.addToModel(model, threetasks);

		assertCommandBehavior("list todo", ListCommand.TODO_MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	@Test
	// @@author A0139920A reused
	public void execute_list_showsAllEventTasks() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEvents("event 1");
		Task pTarget2 = helper.generateEvents("event 2");
		Task pTarget3 = helper.generateEvents("event 3");
		List<Task> threetasks = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(threetasks);
		List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

		// prepare TodoList state
		helper.addToModel(model, threetasks);

		assertCommandBehavior("list event", ListCommand.EVENT_MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	@Test
	// @@author A0139920A reused
	public void execute_list_showsAllDeadlineTasks() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateDeadline("dd 1");
		Task pTarget2 = helper.generateDeadline("ddd 2");
		Task pTarget3 = helper.generateDeadline("dddd 3");
		List<Task> threetasks = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(threetasks);
		List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

		// prepare TodoList state
		helper.addToModel(model, threetasks);

		assertCommandBehavior("list event", ListCommand.EVENT_MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	@Test
	// @@author A0139923X
	public void execute_list_showsAllTasks() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("task 1");
		Task pTarget2 = helper.generateEvents("event 2");
		Task pTarget3 = helper.generateDeadline("deadline 3");
		List<Task> threetasks = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(threetasks);
		List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

		// prepare TodoList state
		helper.addToModel(model, threetasks);

		assertCommandBehavior("list all", ListCommand.ALL_MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	/**
	 * Confirms the 'invalid argument index number behaviour' for the given
	 * command targeting a single task in the shown list, using visible index.
	 * 
	 * @param commandWord
	 *            to test assuming it targets a single task in the last shown
	 *            list based on visible index.
	 */
	private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
			throws Exception {
		assertCommandBehavior(commandWord, expectedMessage); // index missing
		assertCommandBehavior(commandWord + " +1", expectedMessage); // index
																		// should
																		// be
																		// unsigned
		assertCommandBehavior(commandWord + " -1", expectedMessage); // index
																		// should
																		// be
																		// unsigned
		assertCommandBehavior(commandWord + " 0", expectedMessage); // index
																	// cannot be
																	// 0
		assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
	}

	/**
	 * Confirms the 'invalid argument index number behaviour' for the given
	 * command targeting a single task in the shown list, using visible index.
	 * 
	 * @param commandWord
	 *            to test assuming it targets a single task in the last shown
	 *            list based on visible index.
	 */
	// @@author A0132157M reused
	private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("todo 2");
		Task pTarget2 = helper.generatetask("todo 3");
		Task pTarget3 = helper.generatetask("todo 1");
		List<Task> taskList = helper.generatetaskList(pTarget1, pTarget2, pTarget3);

		// set AB state to 2 tasks
		model.resetTodoListData(new TaskList());
		for (Task p : taskList) {
			model.addTask(p);
		}

		assertCommandBehavior(commandWord, expectedMessage, model.getTodoList(), taskList);
	}

	/*
	 * @Test public void execute_selectInvalidArgsFormat_errorMessageShown()
	 * throws Exception { String expectedMessage =
	 * String.format(MESSAGE_INVALID_COMMAND_FORMAT,
	 * SelectCommand.MESSAGE_USAGE);
	 * assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
	 * }
	 */

	/*
	 * @Test public void execute_selectIndexNotFound_errorMessageShown() throws
	 * Exception { assertIndexNotFoundBehaviorForCommand("select"); }
	 * 
	 * @Test public void execute_select_jumpsToCorrecttask() throws Exception {
	 * TestDataHelper helper = new TestDataHelper(); List<Task> threetasks =
	 * helper.generatetaskList(3);
	 * 
	 * TaskList expectedAB = helper.generateTodoList(threetasks);
	 * helper.addToModel(model, threetasks);
	 * 
	 * assertCommandBehavior("select 2",
	 * String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedAB,
	 * expectedAB.getTaskList()); assertEquals(1, targetedJumpIndex);
	 * assertEquals(model.getFilteredTodoList().get(1), threetasks.get(1)); }
	 */

	@Test
	public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
	}

	@Test
	// @@author A0132157M reused
	public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("delete todo 1");
	}

	@Test
	// @@author A0132157M reused
	public void execute_delete_removesCorrectTodo() throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("todo 2");
		Task pTarget2 = helper.generatetask("todo 3");
		Task pTarget3 = helper.generatetask("todo 1");
		Task pTarget4 = helper.generatetask("todo 4");

		List<Task> fourtasks = helper.generatetaskList(pTarget3, pTarget1, pTarget2, pTarget4);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		helper.addToModel(model, fourtasks);

		expectedAB.removeTask(fourtasks.get(1));

		assertCommandBehavior("delete todo 2",
				String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, fourtasks.get(1)), expectedAB,
				expectedAB.getTaskList());
	}

	@Test
	// @@author A0132157M
	public void execute_delete_removesCorrectEvent() throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEvents("todo 2");
		Task pTarget2 = helper.generateEvents("todo 3");
		Task pTarget3 = helper.generateEvents("todo 1");
		Task pTarget4 = helper.generateEvents("todo 4");

		List<Task> fourtasks = helper.generatetaskList(pTarget3, pTarget1, pTarget2, pTarget4);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		helper.addToModel(model, fourtasks);

		expectedAB.removeTask(fourtasks.get(1));

		assertCommandBehavior("delete event 2",
				String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, fourtasks.get(1)), expectedAB,
				expectedAB.getTaskList());
	}

	@Test
	// @@author A0132157M
	public void execute_delete_removesCorrectDeadline() throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateDeadline("todo 2");
		Task pTarget2 = helper.generateDeadline("todo 3");
		Task pTarget3 = helper.generateDeadline("todo 1");
		Task pTarget4 = helper.generateDeadline("todo 4");

		List<Task> fourtasks = helper.generatetaskList(pTarget3, pTarget1, pTarget2, pTarget4);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		helper.addToModel(model, fourtasks);

		expectedAB.removeTask(fourtasks.get(1));

		assertCommandBehavior("delete deadline 2",
				String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, fourtasks.get(1)), expectedAB,
				expectedAB.getTaskList());
	}

	@Test
	// author A0132157M
	public void execute_find_invalidArgsFormat() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
		assertCommandBehavior("find ", expectedMessage);
	}

	@Test
	// author A0132157M
	public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("bla bla todo bla");
		Task pTarget2 = helper.generatetask("bla todo bla bceofeia");
		Task p1 = helper.generatetask("todo 1");
		Task p2 = helper.generatetask("todotodotodo 1");

		List<Task> fourtasks = helper.generatetaskList(p1, pTarget1, p2, pTarget2);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2);
		helper.addToModel(model, fourtasks);

		assertCommandBehavior("find todo 1", Command.getMessageFortaskListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}

	@Test
	// @@author A0139923X
	public void execute_find_onlyMatchesDatesInDayMonthYear() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEvents("15th May 2017");
		Task pTarget2 = helper.generateDeadline("11th November 2017");
		Task pTarget3 = helper.generateEvents("20th June 2016");
		Task pTarget4 = helper.generateEvents("31st December 2018");

		List<Task> fourtasks = helper.generatetaskList(pTarget1, pTarget2, pTarget3, pTarget4);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2, pTarget3, pTarget4);
		helper.addToModel(model, fourtasks);

		assertCommandBehavior("find all date/2017", Command.getMessageFortaskListShownSummary(expectedList.size()),
				expectedAB, expectedList);
	}

	@Test
	// @@author A0132157M
	public void execute_find_isNotCaseSensitive() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generatetask("KEY");
		Task p2 = helper.generatetask("KeY");
		Task p3 = helper.generatetask("key");
		Task p4 = helper.generatetask("KEy");

		List<Task> fourtasks = helper.generatetaskList(p3, p1, p4, p2);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		List<Task> expectedList = fourtasks;
		helper.addToModel(model, fourtasks);

		assertCommandBehavior("find todo KEY", Command.getMessageFortaskListShownSummary(expectedList.size()),
				expectedAB, expectedList);
	}

	@Test
	// @@author A0132157M
	public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("todo blablakeybla 1");
		Task pTarget2 = helper.generatetask("todo rAnDoM blabceofeia 1");
		Task pTarget3 = helper.generatetask("todo 1");
		Task p1 = helper.generatetask("todo sduauo");

		List<Task> fourtasks = helper.generatetaskList(pTarget1, p1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourtasks);

		assertCommandBehavior("find todo 1", Command.getMessageFortaskListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}

	@Test
	// @@author A0132157M
	public void execute_todo_done_undone() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("todo 1");
		Task pTarget2 = helper.generatetask("homework rAnDoM blabceofeia 1");
		Task pTarget3 = helper.generatetask("assignment 1");
		Task p1 = helper.generatetask("something sduauo");

		List<Task> fourtasks = helper.generatetaskList(pTarget1, p1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourtasks);

		assertCommandBehavior("done todo 1", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget1), expectedAB,
				expectedList);

		assertCommandBehavior("undo todo 1", String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, pTarget1),
				expectedAB, expectedList);

		assertCommandBehavior("done todo 3", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget2), expectedAB,
				expectedList);

		assertCommandBehavior("undo todo 3", String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, pTarget2),
				expectedAB, expectedList);

		assertCommandBehavior("done todo 43", Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, expectedAB, expectedList);

		assertCommandBehavior("undone todo 43", Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, expectedAB,
				expectedList);
	}

	// @@author A0132157M
	@Test
	public void execute_event_done_undone() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEvents("todo 1");
		Task pTarget2 = helper.generateEvents("homework rAnDoM blabceofeia 1");
		Task pTarget3 = helper.generateEvents("assignment 1");
		Task p1 = helper.generateEvents("something sduauo");

		List<Task> fourtasks = helper.generatetaskList(pTarget1, p1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourtasks);

		assertCommandBehavior("done event 1", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget1),
				expectedAB, expectedList);

		assertCommandBehavior("undo event 1", String.format(UndoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget1),
				expectedAB, expectedList);

		assertCommandBehavior("done event 3", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget2),
				expectedAB, expectedList);

		assertCommandBehavior("undo event 3", String.format(UndoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget2),
				expectedAB, expectedList);

		assertCommandBehavior("done event 43", Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, expectedAB, expectedList);

		assertCommandBehavior("undo event 43", Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, expectedAB,
				expectedList);
	}

	@Test
	// @@author A0132157M
	public void execute_deadline_done_undone() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateDeadline("todo 1");
		Task pTarget2 = helper.generateDeadline("homework rAnDoM blabceofeia 1");
		Task pTarget3 = helper.generateDeadline("assignment 1");
		Task p1 = helper.generateDeadline("something sduauo");

		List<Task> fourtasks = helper.generatetaskList(pTarget1, p1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourtasks);

		assertCommandBehavior("done deadline 1", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget1),
				expectedAB, expectedList);

		assertCommandBehavior("undone deadline 1", String.format(UndoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget1),
				expectedAB, expectedList);

		assertCommandBehavior("done deadline 3", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget2),
				expectedAB, expectedList);

		assertCommandBehavior("undone deadline 3", String.format(UndoneCommand.MESSAGE_DONE_TASK_SUCCESS, pTarget2),
				expectedAB, expectedList);

		assertCommandBehavior("done deadline 43", Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, expectedAB,
				expectedList);

		assertCommandBehavior("undone deadline 43", Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, expectedAB,
				expectedList);
	}

	// @Test
	// //@@author A0132157M
	// public void execute_ChangeStorageLocation() throws Throwable {
	// assertCommandBehavior("storage /Documents/ShardFolder/TdooData",
	// String.format(StorageCommand.MESSAGE_SUCCESS, "
	// /Documents/ShardFolder/TdooData"));
	// }

	// ===========================================================================================
	/**
	 * A utility class to generate test data.
	 */
	// @@author A0132157M reused
	class TestDataHelper {

		Todo todoHelper() throws Exception {
			Name name = new Name("TODO 111");
			StartDate date = new StartDate("01-01-2017");
			EndDate endDate = new EndDate("02-01-2017");
			Priority priority = new Priority("1");
			String isDone = "false";
			return new Todo(name, date, endDate, priority, isDone);
		}

		// @@author A0132157M
		Event eventHelper() throws Exception {
			Name name = new Name("EVENT 111");
			StartDate startDate = new StartDate("01-12-2016");
			EndDate endDate = new EndDate("02-12-2016");
			StartTime startTime = new StartTime("01:00");
			EndTime endTime = new EndTime("02:00");
			String isDone = "false";
			return new Event(name, startDate, endDate, startTime, endTime, isDone);
		}

		// @@author A0132157M reused
		Deadline deadlineHelper() throws Exception {
			Name name = new Name("DEADLINE 111");
			StartDate startDate = new StartDate("01-12-2016");
			EndTime endTime = new EndTime("02:00");
			String isDone = "false";
			return new Deadline(name, startDate, endTime, isDone);
		}

		/**
		 * Generates a valid task using the given seed. Running this function
		 * with the same parameter values guarantees the returned task will have
		 * the same state. Each unique seed will generate a unique task object.
		 *
		 * @param seed
		 *            used to generate the task data field values
		 */
		// @@author A0132157M
		Task generatetask(int seed) throws Exception {
			return new Todo(new Name("" + seed), new StartDate("" + seed), new EndDate("" + seed),
					new Priority("" + seed), "false");
		}

		/** Generates the correct add command based on the task given */
		// @@author A0132157M
		String generateAddTodoCommand(Todo p) {
			StringBuffer cmd = new StringBuffer();
			cmd.append("add ");
			cmd.append(p.getName().name);
			cmd.append(" from/01st January 2017");// .append(p.getStartDate().date);
			cmd.append(" to/02nd January 2017");// .append(p.getEndDate().endDate);
			cmd.append(" p/1");// .append(p.getPriority().priority);
			// cmd.append("false");
			return cmd.toString();
		}

		// @@author A0132157M
		String generateAddEventCommand(Event p) {
			StringBuffer cmd = new StringBuffer();

			cmd.append("add ");
			cmd.append(p.getName().name);
			cmd.append(" from/01-11-2016");// .append(p.getStartDate().date);
			cmd.append(" to/02-11-2016");// .append(p.getEndDate().endDate);
			cmd.append(" at/01:00");// .append(p.getStartTime().startTime);
			cmd.append(" to/02:00");// .append(p.getEndTime().endTime);
			// cmd.append("false");
			return cmd.toString();
		}

		// @@author A0132157M
		String generateAddDeadlineCommand(Deadline toBeAdded) {
			StringBuffer cmd = new StringBuffer();

			cmd.append("add ");
			cmd.append(toBeAdded.getName().name);
			cmd.append(" on/01-12-2016");// .append(toBeAdded.getDate().date);
			cmd.append(" at/02:00");// .append(toBeAdded.getEndTime().endTime);
			// cmd.append("false");
			return cmd.toString();
		}

		/**
		 * Generates an TodoList with auto-generated tasks.
		 */
		TaskList generateTodoList(int numGenerated) throws Exception {
			TaskList TodoList = new TaskList();
			addToTodoList(TodoList, numGenerated);
			return TodoList;
		}

		/**
		 * Generates an TodoList based on the list of tasks given.
		 */
		TaskList generateTodoList(List<Task> tasks) throws Exception {
			TaskList TodoList = new TaskList();
			addToTodoList(TodoList, tasks);
			return TodoList;
		}

		/**
		 * Adds auto-generated task objects to the given TodoList
		 * 
		 * @param TodoList
		 *            The TodoList to which the tasks will be added
		 */
		void addToTodoList(TaskList TodoList, int numGenerated) throws Exception {
			addToTodoList(TodoList, generatetaskList(numGenerated));
		}

		/**
		 * Adds the given list of tasks to the given TodoList
		 */
		void addToTodoList(TaskList TodoList, List<Task> tasksToAdd) throws Exception {
			for (Task p : tasksToAdd) {
				TodoList.addTask(p);
			}
		}

		/**
		 * Adds auto-generated task objects to the given model
		 * 
		 * @param model
		 *            The model to which the tasks will be added
		 */
		void addToModel(Model model, int numGenerated) throws Exception {
			addToModel(model, generatetaskList(numGenerated));
		}

		/**
		 * Adds the given list of tasks to the given model
		 */
		void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
			for (Task p : tasksToAdd) {
				model.addTask(p);
			}
		}

		/**
		 * Generates a list of tasks based on the flags.
		 */
		List<Task> generatetaskList(int numGenerated) throws Exception {
			List<Task> tasks = new ArrayList<>();
			for (int i = 1; i <= numGenerated; i++) {
				tasks.add(generatetask(i));
			}
			return tasks;
		}

		List<Task> generatetaskList(Task... tasks) {
			return Arrays.asList(tasks);
		}

		/**
		 * Generates a task object with given name. Other fields will have some
		 * dummy values.
		 */
		// @@author A0132157M
		Task generatetask(String name) throws Exception {
			return new Todo(new Name(name), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("1"),
					"false");
		}

		// @@author A0132157M
		Task generateEvents(String name) throws Exception {
			return new Event(new Name(name), new StartDate("11-11-2017"), new EndDate("12-11-2017"),
					new StartTime("12:00"), new EndTime("12:30"), "false");
		}

		// @@author A0132157M
		Task generateDeadline(String name) throws Exception {
			return new Deadline(new Name(name), new StartDate("11-11-2017"), new EndTime("12:30"), "false");
		}
	}
}
