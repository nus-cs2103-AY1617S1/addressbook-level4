package seedu.unburden.logic;

import com.google.common.eventbus.Subscribe;

import seedu.unburden.commons.core.EventsCenter;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.events.model.ListOfTaskChangedEvent;
import seedu.unburden.commons.events.ui.JumpToListRequestEvent;
import seedu.unburden.commons.events.ui.ShowHelpRequestEvent;
import seedu.unburden.commons.util.StringUtil;
import seedu.unburden.logic.Logic;
import seedu.unburden.logic.LogicManager;
import seedu.unburden.logic.commands.*;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.Model;
import seedu.unburden.model.ModelManager;
import seedu.unburden.model.ReadOnlyListOfTask;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.*;
import seedu.unburden.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.unburden.storage.StorageManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.unburden.commons.core.Messages.*;

public class LogicManagerTest {
	
	/**
	 * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
	 */
	@Rule
	public TemporaryFolder saveFolder = new TemporaryFolder();

	private Model model;
	private Logic logic;

	// These are for checking the correctness of the events raised
	private ReadOnlyListOfTask latestSavedAddressBook;
	private boolean helpShown;
	private int targetedJumpIndex;

	@Subscribe
	private void handleLocalModelChangedEvent(ListOfTaskChangedEvent abce) {
		latestSavedAddressBook = new ListOfTask(abce.data);
	}

	@Subscribe
	private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
		helpShown = true;
	}

	@Subscribe
	private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
		targetedJumpIndex = je.targetIndex;
	}

	@Before
	public void setup() {
		model = new ModelManager();
		String tempTaskListFile = saveFolder.getRoot().getPath() + "TempTaskList.xml";
		String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
		logic = new LogicManager(model, new StorageManager(tempTaskListFile, tempPreferencesFile));
		EventsCenter.getInstance().registerHandler(this);

		latestSavedAddressBook = new ListOfTask(model.getListOfTask()); // last
																		// saved
																		// assumed
																		// to be
																		// up to
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
	 * Both the 'address book' and the 'last shown list' are expected to be
	 * empty.
	 * 
	 * @see #assertCommandBehavior(String, String, ReadOnlyListOfTask, List)
	 */
	private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
		assertCommandBehavior(inputCommand, expectedMessage, new ListOfTask(), Collections.emptyList());
	}

	/**
	 * Executes the command and confirms that the result message is correct and
	 * also confirms that the following three parts of the LogicManager object's
	 * state are as expected:<br>
	 * - the internal address book data are same as those in the
	 * {@code expectedAddressBook} <br>
	 * - the backing list shown by UI max5ches the {@code shownList} <br>
	 * - {@code expectedAddressBook} was saved to the storage file. <br>
	 */
	private void assertCommandBehavior(String inputCommand, String expectedMessage,
			ReadOnlyListOfTask expectedAddressBook, List<? extends ReadOnlyTask> expectedShownList) throws Exception {

		// Execute the command
		CommandResult result = logic.execute(inputCommand);

		// Confirm the ui display elements should contain the right data
		assertEquals(expectedMessage, result.feedbackToUser);
		assertEquals(expectedShownList, model.getFilteredTaskList());

		// Confirm the state of data (saved and in-memory) is as expected
		assertEquals(expectedAddressBook, model.getListOfTask());
		assertEquals(expectedAddressBook, latestSavedAddressBook);
	}
	
	/**
	 * Similar to the above
	 * created to facilitate checking for commands that require a previous command
	 * E.g. undo redo
	 */
	private void assertTwoPartCommandBehavior(String inputCommand, String nextInputCommand, String expectedMessage,
			ReadOnlyListOfTask expectedAddressBook, List<? extends ReadOnlyTask> expectedShownList) throws Exception {

		// Execute the command
		CommandResult prevResult = logic.execute(inputCommand);
		CommandResult currResult = logic.execute(nextInputCommand);
		

		// Confirm the ui display elements should contain the right data
		assertEquals(expectedMessage, currResult.feedbackToUser);
		assertEquals(expectedShownList, model.getFilteredTaskList());

		// Confirm the state of data (saved and in-memory) is as expected
		assertEquals(expectedAddressBook, model.getListOfTask());
		assertEquals(expectedAddressBook, latestSavedAddressBook);
	}
	
	/**
	 * Similar to the above
	 * created to facilitate checking for commands that require "List all" and previous command
	 * E.g. undoing the UnDoneCommand requires "list all" to see all tasks first 
	 */
	private void assertThreePartCommandBehavior(String firstInputCommand, String secondInputCommand, String thirdInputCommand, 
			String expectedMessage, ReadOnlyListOfTask expectedAddressBook, List<? extends ReadOnlyTask> expectedShownList) throws Exception {

		// Execute the command
		CommandResult firstResult = logic.execute(firstInputCommand);
		CommandResult secondResult = logic.execute(secondInputCommand);
		CommandResult thirdResult = logic.execute(thirdInputCommand);
		
		

		// Confirm the ui display elements should contain the right data
		assertEquals(expectedMessage, thirdResult.feedbackToUser);
		assertEquals(expectedShownList, model.getFilteredTaskList());

		// Confirm the state of data (saved and in-memory) is as expected
		assertEquals(expectedAddressBook, model.getListOfTask());
		assertEquals(expectedAddressBook, latestSavedAddressBook);
	}
	
	/**
	 * Similar to the above
	 * created to facilitate checking for commands that require "List all" and previous command
	 * E.g. redoing the UndoCommand, which undo the UnDoneCommand, which requires the list all command
	 */
	private void assertFourPartCommandBehavior(String firstInputCommand, String secondInputCommand, String thirdInputCommand, 
			String fourthInputCommand, String expectedMessage, 
			ReadOnlyListOfTask expectedAddressBook, List<? extends ReadOnlyTask> expectedShownList) throws Exception {

		// Execute the command
		CommandResult firstResult = logic.execute(firstInputCommand);
		CommandResult secondResult = logic.execute(secondInputCommand);
		CommandResult thirdResult = logic.execute(thirdInputCommand);
		CommandResult fourthResult = logic.execute(fourthInputCommand);
		
		

		// Confirm the ui display elements should contain the right data
		assertEquals(expectedMessage, fourthResult.feedbackToUser);
		assertEquals(expectedShownList, model.getFilteredTaskList());

		// Confirm the state of data (saved and in-memory) is as expected
		assertEquals(expectedAddressBook, model.getListOfTask());
		assertEquals(expectedAddressBook, latestSavedAddressBook);
	}

	@Test
	public void execute_unknownCommandWord() throws Exception {
		String unknownCommand = "uicfhmowqewca";
		assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
	}

	@Test
	public void execute_help() throws Exception {
		assertCommandBehavior("help", HelpCommand.HELP_MESSAGE_HELP);
		assertTrue(helpShown);
	}
	
	//@@author A0143095H
	@Test
	public void execute_help_add() throws Exception {
		assertCommandBehavior("help add", HelpCommand.HELP_MESSAGE_ADD);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_delete() throws Exception {
		assertCommandBehavior("help delete", HelpCommand.HELP_MESSAGE_DELETE);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_find() throws Exception {
		assertCommandBehavior("help find", HelpCommand.HELP_MESSAGE_FIND);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_edit() throws Exception {
		assertCommandBehavior("help edit", HelpCommand.HELP_MESSAGE_EDIT);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_list() throws Exception {
		assertCommandBehavior("help list", HelpCommand.HELP_MESSAGE_LIST);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_clear() throws Exception {
		assertCommandBehavior("help clear", HelpCommand.HELP_MESSAGE_CLEAR);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_undo() throws Exception {
		assertCommandBehavior("help undo", HelpCommand.HELP_MESSAGE_UNDO);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_redo() throws Exception {
		assertCommandBehavior("help redo", HelpCommand.HELP_MESSAGE_REDO);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_done() throws Exception {
		assertCommandBehavior("help done", HelpCommand.HELP_MESSAGE_DONE);
		assertTrue(helpShown);
	}
	
	@Test
	public void execute_help_exit() throws Exception {
		assertCommandBehavior("help exit", HelpCommand.HELP_MESSAGE_EXIT);
		assertTrue(helpShown);
	}
	
	
	
	//@author A0143095H 
	@Test 
	public void execute_doneCorrectly() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task t0 = helper.generateTask(0);
		Task t1 = helper.generateTask(1);
		Task t2 = helper.generateTask(2);
		
		List<Task> list = helper.generateTaskList(3);
		List<Task> emptyList = helper.generateTaskList(0);
		ListOfTask expected = helper.generateListOfTask(list);
		expected.doneTask(list.get(1), true);
		expected.doneTask(list.get(1), true);
		expected.doneTask(list.get(1), true);
		
		
		helper.addToModel(model, list);
		assertThreePartCommandBehavior("done 3","done 2","done 1", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS),
				expected, emptyList);
		//assertTrue(list.get(1).getDone());
		
	}
	
	//@author A0143095H 
	@Test
	public void execute_doneAllCorrectly() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		List<Task> list = helper.generateTaskList(3);
		List<Task> emptyList = helper.generateTaskList(0);
		ListOfTask expected = helper.generateListOfTask(list);
		expected.doneTask(list.get(1), true);
		expected.doneTask(list.get(1), true);
		expected.doneTask(list.get(1), true);
		
		helper.addToModel(model, list);
		assertCommandBehavior("done all", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS),
				expected, emptyList);
			
	}
	
	
	//@author A0143095H 
	@Test
	public void execute_undoneCorrectly() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		List<Task> list = helper.generateTaskList(3);
		ListOfTask expected = helper.generateListOfTask(list);
		
		expected.doneTask(list.get(1), true);
		expected.doneTask(list.get(1), true);
		expected.doneTask(list.get(1), true);
		
		expected.undoneTask(list.get(1), false);
		expected.undoneTask(list.get(1), false);
		expected.undoneTask(list.get(1), false);
		
		helper.addToModel(model, list);
		assertThreePartCommandBehavior("undone 3","undone 2","undone 1", String.format(UnDoneCommand.MESSAGE_UNDONE_TASK_SUCCESS),
				expected, list);
		
	}
	
	

	@Test
	public void execute_exit() throws Exception {
		assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
	}

	@Test
	public void execute_clear() throws Exception {
		TestDataHelper helper = new TestDataHelper();

		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p4 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedList = helper.generateListOfTask(fourTasks);
		
		helper.addToModel(model, fourTasks);

		expectedList.resetData(new ListOfTask());
		
		assertCommandBehavior("clear", String.format(ClearCommand.MESSAGE_SUCCESS), expectedList, expectedList.getTaskList());
	}
	
	//@@author A0139714B
	@Test
	public void execute_clear_done() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p4 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		List<Task> threeTasks = helper.generateTaskList(p1, p2, p3);
		ListOfTask expectedAB = helper.generateListOfTask(fourTasks);
		List<Task> expectedList = helper.generateTaskList(0);
		
		helper.addToModel(model, fourTasks);

		expectedAB.doneTask(p4, true);
		expectedAB.removeTask(p4);
		
		assertThreePartCommandBehavior("done 4", "list done", "clear",
				String.format(ClearCommand.MESSAGE_SUCCESS),
				expectedAB,
				expectedList);
		
	}
	
	//@@author A0139714B
	@Test
	public void execute_clear_undone() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p4 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(fourTasks);
		
		helper.addToModel(model, fourTasks);

		Task updatedTask = (Task) expectedAB.getTaskList().get(3);
		List<Task> expectedList = helper.generateTaskList(0);
		expectedAB.removeTask(p1);
		expectedAB.removeTask(p2);
		expectedAB.removeTask(p3);
	
		assertTwoPartCommandBehavior("done 4", "clear", 
				String.format(ClearCommand.MESSAGE_SUCCESS),
				expectedAB,
				expectedList);
	}
	
	// @@author A0139714B
	@Test
	public void execute_clear_certainDate() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAllWithoutTag("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateEventTaskWithAllWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800");
		Task p3 = helper.generateEventTaskWithAllWithoutTag("KE Y", "say goodbye", "12-10-2016", "1500", "1800");
		Task p4 = helper.generateEventTaskWithAllWithoutTag("keyKEY sduauo", "move", "12-10-2016", "1500", "1800");
		Task p5 = helper.generateEventTaskWithAllWithoutTag("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> twoTasks = helper.generateTaskList(p1, p5);
		ListOfTask expectedAB = helper.generateListOfTask(twoTasks);
		List<Task> expectedList = helper.generateTaskList(0);
	
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		assertTwoPartCommandBehavior("find 12-10-2016", "clear",
				String.format(ClearCommand.MESSAGE_SUCCESS), expectedAB, expectedList);
	}		
	
	@Test
	public void execute_add_invalidArgsFormat() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
		assertCommandBehavior("add Valid Name 12-12-2010 s/2300 e/2359", expectedMessage);
		assertCommandBehavior("add Valid Name d/12-12-2010 s/2300 2359", expectedMessage);
	}

	@Test
	public void execute_add_invalidTaskData() throws Exception {
		// TODO : add test case to check if start time later than end time
		assertCommandBehavior("add []\\[;] i/Valid Task Description d/12-12-2016 s/2300 e/2359",
				Name.MESSAGE_NAME_CONSTRAINTS);
		assertCommandBehavior("add Valid Name i/[]\\[;] d/12-12-2016 s/2300 e/2359",
				TaskDescription.MESSAGE_TASK_CONSTRAINTS);
		assertCommandBehavior("add Valid Name i/Valid Task Description d/12-12-2010 s/2300 e/2359",
				Date.MESSAGE_DATE_CONSTRAINTS);
		assertCommandBehavior("add Valid Name i/Valid Task Description d/12-12-2016 s/2300 e/2400",
				Time.MESSAGE_TIME_CONSTRAINTS);
		assertCommandBehavior("add Valid Name i/Valid Task Description d/12-12-2016 s/2400 e/2359",
				Time.MESSAGE_TIME_CONSTRAINTS);
		assertCommandBehavior("add Valid Name i/Valid Task Description d/12-12-2010 s/2300 e/2359 t/invalid_-[.tag",
				Tag.MESSAGE_TAG_CONSTRAINTS);
	}
	
	//s@@author A0139678J
	@Test
	public void execute_add_deadline() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task t1 = helper.generateDeadlineTask("Hi hi", "bye bye", "11-10-2016", "bored");
		ListOfTask expected = new ListOfTask();
		expected.addTask(t1);

		assertCommandBehavior("add Hi hi i/bye bye d/11-10-2016 t/bored", String.format(AddCommand.MESSAGE_SUCCESS, t1),
				expected, expected.getTaskList());

	}

	// @@author A0139678J
	@Test
	public void execute_add_floatingTask() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task t1 = helper.generateFloatingTask("I'm so tired", "I haven't sleep", "sleep");
		ListOfTask expected = new ListOfTask();
		expected.addTask(t1);

		assertCommandBehavior("Add I'm so tired i/I haven't sleep t/sleep",
				String.format(AddCommand.MESSAGE_SUCCESS, t1), expected, expected.getTaskList());
	}

	@Test
	public void execute_add_floating_task_without_tags() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateFloatingTaskWithoutTag("Hello", "It's me");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Hello i/It's me", String.format(AddCommand.MESSAGE_SUCCESS, task), expected,
				expected.getTaskList());
	}

	@Test
	public void execute_add_event_task_without_tags() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateEventTaskWithAllWithoutTag("Hi", "there", "12-12-2016", "1400", "1500");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Hi i/there d/12-12-2016 s/1400 e/1500",
				String.format(AddCommand.MESSAGE_SUCCESS, task), expected, expected.getTaskList());
	}

	@Test
	public void execute_add_floating_task_without_description() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateFloatingTaskWithoutTaskDescription("Joey", "Tribbiani");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Joey t/Tribbiani", String.format(AddCommand.MESSAGE_SUCCESS, task), expected,
				expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_without_description_and_tags() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescriptionWithoutTag("Monica", "13-11-2017",
				"0137");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Monica d/13-11-2017 e/0137", String.format(AddCommand.MESSAGE_SUCCESS, task),
				expected, expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_without_description() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateDeadlineTaskWithoutTaskDescription("Chandler", "22-12-2018", "Friends");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("Add Chandler d/22-12-2018 t/Friends", String.format(AddCommand.MESSAGE_SUCCESS, task),
				expected, expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_without_tags() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateDeadlineTask("Chandler", "Bing", "13-12-2017");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Chandler i/Bing d/13-12-2017", String.format(AddCommand.MESSAGE_SUCCESS, task),
				expected, expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_with_everything() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateDeadlineTask("How you doin?", "Joey", "14-11-2100", "Friends");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add How you doin? i/Joey d/14-11-2100 t/Friends",
				String.format(AddCommand.MESSAGE_SUCCESS, task), expected, expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_with_end_time() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateDeadlineTaskWithEndTime("Chandler", "Bing", "14-12-2019", "1800", "Friends");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Chandler i/Bing d/14-12-2019 e/1800 t/Friends",
				String.format(AddCommand.MESSAGE_SUCCESS, task), expected, expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_by_today() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");
		Task task = helper.generateDeadlineTaskWithoutTaskDescription("Chandler",
				DATEFORMATTER.format(calendar.getTime()), "friends");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Chandler by today t/friends", String.format(AddCommand.MESSAGE_SUCCESS, task),
				expected, expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_by_tomorrow() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");
		Task task = helper.generateDeadlineTaskWithoutTaskDescription("Rachel",
				DATEFORMATTER.format(calendar.getTime()), "friends");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Rachel by tomorrow t/friends", String.format(AddCommand.MESSAGE_SUCCESS, task),
				expected, expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_by_next_week() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");
		Task task = helper.generateDeadlineTaskWithoutTaskDescription("Somebody ate my sandwich",
				DATEFORMATTER.format(calendar.getTime()), "friends");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Somebody ate my sandwich by next week t/friends",
				String.format(AddCommand.MESSAGE_SUCCESS, task), expected, expected.getTaskList());
	}

	@Test
	public void execute_add_deadline_by_next_month() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");
		Task task = helper.generateDeadlineTaskWithoutTaskDescription("Pheobe",
				DATEFORMATTER.format(calendar.getTime()), "friends");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);

		assertCommandBehavior("add Pheobe by next month t/friends", String.format(AddCommand.MESSAGE_SUCCESS, task),
				expected, expected.getTaskList());
	}

	@Test
	public void execute_add_successful() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();
		Task toBeAdded = helper.adam();
		ListOfTask expectedAB = new ListOfTask();
		expectedAB.addTask(toBeAdded);

		// execute command and verify result
		assertCommandBehavior(helper.generateAddCommand(toBeAdded),
				String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());

	}

	@Test
	public void execute_addDuplicate_notAllowed() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();
		Task toBeAdded = helper.adam();
		ListOfTask expectedAB = new ListOfTask();
		expectedAB.addTask(toBeAdded);

		// setup starting state
		model.addTask(toBeAdded); // person already in internal address book

		// execute command and verify result
		assertCommandBehavior(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK, expectedAB,
				expectedAB.getTaskList());

	}

	@Test
	public void execute_list_showsAllPersons() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expectedAB = helper.generateListOfTask(2);
		List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

		// prepare Unburden state
		helper.addToModel(model, 2);

		assertCommandBehavior("list all", ListCommand.MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	@Test
	public void execute_List_Shows_Undone() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expected = helper.generateListOfTask(3);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();

		// prepare Unburden state
		helper.addToModel(model, 3);

		assertCommandBehavior("list", "3 tasks listed!", expected, expectedList);
	}

	@Test
	public void execute_List_Shows_Undone_Empty() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expected = helper.generateListOfTask(0);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();

		helper.addToModel(model, 0);

		assertCommandBehavior("list", ListCommand.MESSAGE_NO_MATCHES_UNDONE, expected, expectedList);
	}

	@Test
	public void execute_List_Shows_Done_Empty() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expected = helper.generateListOfTask(0);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();

		helper.addToModel(model, 0);

		assertCommandBehavior("list done", ListCommand.MESSAGE_NO_MATCHES_DONE, expected, expectedList);
	}

	@Test
	public void execute_List_Shows_Overdue_Empty() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expected = helper.generateListOfTask(0);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();

		helper.addToModel(model, 0);

		assertCommandBehavior("list overdue", ListCommand.MESSAGE_NO_MATCHES_OVERDUE, expected, expectedList);
	}

	@Test
	public void execute_List_Shows_Date_Empty() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expected = helper.generateListOfTask(0);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();

		helper.addToModel(model, 0);

		assertCommandBehavior("list 13-12-2022", ListCommand.MESSAGE_NO_MATCHES_DATE, expected, expectedList);
	}

	@Test
	public void execute_List_Shows_All_Empty() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expected = helper.generateListOfTask(0);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();

		helper.addToModel(model, 0);

		assertCommandBehavior("List all", String.format(Messages.MESSAGE_NO_TASKS_FOUND, ListCommand.MESSAGE_USAGE),
				expected, expectedList);
	}
	
	@Test
	public void execute_List_Shows_Deadline() throws Exception{
		TestDataHelper helper= new TestDataHelper();
		Task task = helper.generateDeadlineTask("Chanadler bong", "Friends is life", "12-12-2016");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);
		List <?extends ReadOnlyTask> expectedList = expected.getTaskList();
		
		helper.addToModel_ReadOnlyTask(model, expectedList);
		
		assertCommandBehavior("List 10-12-2016 to 13-12-2016", "1 tasks listed!", expected, expectedList);
	}
	
	@Test
	public void execute_List_Shows_Deadline_2() throws Exception{
		TestDataHelper helper= new TestDataHelper();
		Task task = helper.generateDeadlineTask("Drake Remoray", "Friends is life", "10-12-2016");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);
		List <?extends ReadOnlyTask> expectedList = expected.getTaskList();
		
		helper.addToModel_ReadOnlyTask(model, expectedList);
		
		assertCommandBehavior("List 10-12-2016 to 13-12-2016", "1 tasks listed!", expected, expectedList);
	}

	@Test
	public void execute_List_Shows_Deadline_3() throws Exception{
		TestDataHelper helper= new TestDataHelper();
		Task task = helper.generateDeadlineTask("Morning's here", "Friends is life", "13-12-2016");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);
		List <?extends ReadOnlyTask> expectedList = expected.getTaskList();
		
		helper.addToModel_ReadOnlyTask(model, expectedList);
		
		assertCommandBehavior("List 10-12-2016 to 13-12-2016", "1 tasks listed!", expected, expectedList);
	}
	
	@Test
	public void execute_List_Shows_Overdue() throws Exception{
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateDeadlineTask("Bamboolzed", "Best game ever", "01-11-2016");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();
		
		helper.addToModel_ReadOnlyTask(model, expectedList);
		
		assertCommandBehavior("List overdue", "1 tasks listed!", expected, expectedList);
		
	}
	
	@Test
	public void execute_List_Shows_Done() throws Exception{
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expected1 = helper.generateListOfTask(0);
		List<? extends ReadOnlyTask> expectedList1 = expected1.getTaskList();
		
		helper.addToModel_ReadOnlyTask(model, expectedList1);
		
		assertCommandBehavior("List done", ListCommand.MESSAGE_NO_MATCHES_DONE, expected1, expectedList1);
		
	}
	
	
	@Test
	public void execute_List_Tomorrow() throws Exception{
		TestDataHelper helper = new TestDataHelper();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");
		Task task = helper.generateDeadlineTask("We were on a break!", "Ross and Rachel", DATEFORMATTER.format(calendar.getTime()));
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();
		
		helper.addToModel_ReadOnlyTask(model, expectedList);
		
		assertCommandBehavior("List tomorrow", "1 tasks listed!", expected, expectedList);
	}
	
	@Test
	public void execute_List_NextWeek() throws Exception{
		TestDataHelper helper = new TestDataHelper();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");
		Task task = helper.generateDeadlineTask("Three lead clover", "Stupid team member", DATEFORMATTER.format(calendar.getTime()));
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();
		
		helper.addToModel_ReadOnlyTask(model, expectedList);
		
		assertCommandBehavior("List next week", "1 tasks listed!", expected, expectedList);
	}
	
	@Test
	public void execute_List_wrongly() throws Exception{
		TestDataHelper helper = new TestDataHelper();
		ListOfTask expected = helper.generateListOfTask(1);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();
		
		helper.addToModel(model, 1);
		
		assertCommandBehavior("List !!!", "Try List, or List followed by \"done\" or \"all\" or a date", expected, expectedList);
	}
	
	@Test
	public void execute_List_wrongly2() throws Exception{
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateDeadlineTask("Chandler", "is funny", "12-12-2022");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList(); 
		
		helper.addToModel_ReadOnlyTask(model, expectedList);
		
		assertCommandBehavior("List <><)(_*", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), expected, expectedList);
	}
	
	
	@Test
	public void execute_List_Undone() throws Exception{
		TestDataHelper helper = new TestDataHelper();
		Task task = helper.generateDeadlineTask("Carol", "Susan", "13-12-2016");
		ListOfTask expected = new ListOfTask();
		expected.addTask(task);
		List<? extends ReadOnlyTask> expectedList = expected.getTaskList();
		
		helper.addToModel_ReadOnlyTask(model, expectedList);
		
		assertCommandBehavior("LIST undone", "1 tasks listed!", expected, expectedList);
	}
	/**
	 * Confirms the 'invalid argument index number behaviour' for the given
	 * command targeting a single person in the shown list, using visible index.
	 * 
	 * @param commandWord
	 *            to test assuming it targets a single person in the last shown
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
	 * command targeting a single person in the shown list, using visible index.
	 * 
	 * @param commandWord
	 *            to test assuming it targets a single person in the last shown
	 *            list based on visible index.
	 */
	private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		TestDataHelper helper = new TestDataHelper();
		List<Task> taskList = helper.generateTaskList(2);

		// set AB state to 2 persons
		model.resetData(new ListOfTask());
		for (Task p : taskList) {
			model.addTask(p);
		}

		if (commandWord.equals(EditCommand.COMMAND_WORD)) {
			assertCommandBehavior(commandWord + " 3 e/2359", expectedMessage, model.getListOfTask(), taskList);
		} else {
			assertCommandBehavior(commandWord + " 3", expectedMessage, model.getListOfTask(), taskList);
		}
	}

	//@@author A0147986H-unused
		/**
		 * test the select command
		 * 
		 */
		@Test
		public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
			String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
			assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
		}

		@Test
		public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
			assertIndexNotFoundBehaviorForCommand("select");
		}
		
		@Test
		public void execute_select_jumpsToCorrectPerson() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, threeTasks.get(1)), expectedAB,
					expectedAB.getTaskList());
			assertEquals(1, targetedJumpIndex);
			assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
		}
				
		@Test
		public void execute_deleteInvalidArgsFormat_errorMessageShown_Original() throws Exception {
			String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
			assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
		}

		@Test
		public void execute_deleteIndexNotFound_errorMessageShown_Original() throws Exception {
			assertIndexNotFoundBehaviorForCommand("delete");
		}

		@Test
		public void execute_delete_removesCorrectPerson() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(1));
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
					expectedAB, expectedAB.getTaskList());
		}

		
		//@@author A0147986H-unused
		/**test the multiple delete command. 
		 * test both reverse indexes and any kind 
		 * if format 
		 * @throws Exception
		 *  This is unused because I did not discuss with my teammates in advanced so they decided 
         * not to include this method
		 */
		/*
		@Test
		public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
			String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE);
			assertIncorrectIndexFormatBehaviorForCommand("multipledelete", expectedMessage);
		}
		
		@Test
		public void execute_deleteInvalidArgsFormat_errorMessageShownZero() throws Exception {
			String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE);
			assertIncorrectIndexFormatBehaviorForCommand("multipledelete 0", expectedMessage);
		}

		@Test
		public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
			assertIndexNotFoundBehaviorForCommand("multipledelete");
		}	

		@Test
		public void execute_delete_removesCorrectTask() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(1));
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("multipledelete 2", String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB, expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesCorrectTaskWithDuplicate() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(1));
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("multipledelete 2 2 2 2 2 2", String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB, expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesCorrectTaskWithDuplicate2() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(1));
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("multipledelete 2-2", String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB, expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasks() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 1 2 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasksWithZero() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 01 02 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasksWithDuplicate() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 1 2 1",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasksReverse() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 2 1 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasksReverseWithZero() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 02 01 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		
		@Test
		public void execute_delete_removesMultipleTasksWithDash() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 1-2 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}

		@Test
		public void execute_delete_removesMultipleTasksWithDashReverse() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 2-1 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}

		@Test
		public void execute_delete_removesMultipleTasksWithDashReverseWithZero() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 02-01 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		*/
				
	// @@author A0139714B
	@Test
	public void execute_editIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("edit");
	}
	
	// @@author A0139714B
	@Test
	public void execute_edit_validAllFields() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");
		Task toEdit = helper.generateEventTaskWithAll("blahblahblah", "hi", "16-11-2016", "1200", "1900", "yo");
		Task updatedTask = helper.generateEventTaskWithAll("blahblahblah", "hi", "16-11-2016", "1200", "1900", "yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, updatedTask);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		expectedAB.editTask((ReadOnlyTask) fiveTasks.get(4), toEdit);

		assertCommandBehavior("edit 5 blahblahblah i/hi d/16-11-2016 s/1200 e/1900",
				String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask), expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_validName() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");
		Task toEdit = helper.generateEventTaskWithAll("blahblahblah", "", "", "", "", "yo");
		Task updatedTask = helper.generateEventTaskWithAll("blahblahblah", "move", "14-10-2016", "1500", "1800", "bye");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, updatedTask, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		expectedAB.editTask((ReadOnlyTask) fiveTasks.get(3), toEdit);

		assertCommandBehavior("edit 4 blahblahblah", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_validTaskDescription() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");
		Task toEdit = helper.generateEventTaskWithAll("", "blahblahblah", "", "", "", "yo");
		Task updatedTask = helper.generateEventTaskWithAll("KE Y", "blahblahblah", "13-10-2016", "1500", "1800", "hi");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, updatedTask, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		expectedAB.editTask((ReadOnlyTask) fiveTasks.get(3), toEdit);

		assertCommandBehavior("edit 3 i/blahblahblah",
				String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask), expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_validDate() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");
		Task toEdit = helper.generateEventTaskWithAll("", "", "16-10-2016", "", "", "yo");
		Task updatedTask = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "16-10-2016", "1500", "1800",
				"yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, updatedTask);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		expectedAB.editTask((ReadOnlyTask) fiveTasks.get(4), toEdit);

		assertCommandBehavior("edit 5 d/16-10-2016", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_validStartTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");
		Task toEdit = helper.generateEventTaskWithAll("", "", "", "1200", "", "blah");
		Task updatedTask = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1200",
				"1800", "blah");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, updatedTask, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		expectedAB.editTask((ReadOnlyTask) fiveTasks.get(2), toEdit);

		assertCommandBehavior("edit 2 s/1200", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_validEndTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");
		Task toEdit = helper.generateEventTaskWithAll("", "", "", "", "1900", "blah");
		Task updatedTask = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500",
				"1900", "blah");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, updatedTask, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		expectedAB.editTask((ReadOnlyTask) fiveTasks.get(2), toEdit);

		assertCommandBehavior("edit 2 e/1900", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_fail_addEndTimeToATaskWithNoDate() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateFloatingTask("bla bla KEY bla", "blah blah blah", "tag");
		Task p2 = helper.generateFloatingTask("bla KEY bla bceofeia", "hello world", "blah");
		Task p3 = helper.generateFloatingTask("KE Y", "say goodbye", "hi");
		Task p4 = helper.generateFloatingTask("keyKEY sduauo", "move", "bye");
		Task p5 = helper.generateFloatingTask("K EY sduauo", "high kneel", "yo");
		Task toEdit = helper.generateEventTaskWithAllWithoutTag("", "", "", "", "1900");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		assertCommandBehavior("edit 5 e/1900", String.format(Messages.MESSAGE_CANNOT_ADD_ENDTIME_WITH_NO_DATE),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_fail_addStartTimeToATaskWithoutEndTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateDeadlineTask("bla bla KEY bla", "blah blah blah", "11-10-2016", "tag");
		Task p2 = helper.generateDeadlineTask("bla KEY bla bceofeia", "hello world", "12-10-2016", "blah");
		Task p3 = helper.generateDeadlineTask("KE Y", "say goodbye", "13-10-2016", "hi");
		Task p4 = helper.generateDeadlineTask("keyKEY sduauo", "move", "14-10-2016", "bye");
		Task p5 = helper.generateDeadlineTask("K EY sduauo", "high kneel", "15-10-2016", "yo");

		Task toEdit = helper.generateEventTaskWithAll("", "", "", "1900", "", "yo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		expectedAB.editTask(p5, toEdit);

		assertCommandBehavior("edit 5 s/1900", String.format(Messages.MESSAGE_CANNOT_ADD_STARTTIME_WITH_NO_ENDTIME),
				expectedAB, expectedList);
	}
	
	// @@author A0139714B
	@Test
	public void execute_edit_fail_startTimeAfterEndTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		assertCommandBehavior("edit 2 s/2000", String.format(MESSAGE_STARTTIME_AFTER_ENDTIME), expectedAB,
				expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_removeTaskDescription() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1800", "1900",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1600", "1900", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1900", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1000", "1500", "yo");
		Task updatedTask = helper.generateEventTaskWithoutTaskDescription("bla bla KEY bla", "11-10-2016", "1800",
				"1900", "tag");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(updatedTask, p2, p3, p4, p5);

		// EditCommand.reset();
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		assertCommandBehavior("edit 1 i/rm", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_removeDate() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateDeadlineTask("bla bla KEY bla", "blah blah blah", "11-10-2016", "tag");
		Task p2 = helper.generateDeadlineTask("bla KEY bla bceofeia", "hello world", "12-10-2016", "blah");
		Task p3 = helper.generateDeadlineTask("KE Y", "say goodbye", "13-10-2016", "hi");
		Task p4 = helper.generateDeadlineTask("keyKEY sduauo", "move", "14-10-2016", "bye");
		Task p5 = helper.generateDeadlineTask("K EY sduauo", "high kneel", "15-10-2016", "yo");
		Task updatedTask = helper.generateFloatingTask("K EY sduauo", "high kneel", "yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, updatedTask);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		assertCommandBehavior("edit 5 d/rm", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_removeStartTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1800", "1900",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1600", "1900", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1900", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1000", "1500", "yo");
		Task updatedTask = helper.generateDeadlineTaskWithEndTime("bla bla KEY bla", "blah blah blah", "11-10-2016",
				"1900", "tag");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(updatedTask, p2, p3, p4, p5);

		// EditCommand.reset();
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		assertCommandBehavior("edit 1 s/rm", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_removeEndTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateDeadlineTaskWithEndTime("bla bla KEY bla", "blah blah blah", "11-10-2016", "1900",
				"tag");
		Task p2 = helper.generateDeadlineTaskWithEndTime("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800",
				"blah");
		Task p3 = helper.generateDeadlineTaskWithEndTime("KE Y", "say goodbye", "13-10-2016", "1900", "hi");
		Task p4 = helper.generateDeadlineTaskWithEndTime("keyKEY sduauo", "move", "14-10-2016", "1900", "bye");
		Task p5 = helper.generateDeadlineTaskWithEndTime("K EY sduauo", "high kneel", "15-10-2016", "1500", "yo");
		Task updatedTask = helper.generateDeadlineTask("bla bla KEY bla", "blah blah blah", "11-10-2016", "tag");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(updatedTask, p2, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		assertCommandBehavior("edit 1 e/rm", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_fail_removeEndTimeOnTaskWithStartTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1800", "1900",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1600", "1900", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1900", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1000", "1500", "yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		assertCommandBehavior("edit 1 e/rm",
				String.format(Messages.MESSAGE_CANNOT_REMOVE_ENDTIME_WHEN_THERE_IS_STARTTIME), expectedAB,
				expectedList);
	}
	
	// @@author A0139714B
	@Test
	public void execute_edit_fail_removeDateOnTaskWithEndTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1800", "1900",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1600", "1900", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1900", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1000", "1500", "yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		assertCommandBehavior("edit 1 d/rm",
				String.format(Messages.MESSAGE_CANNOT_REMOVE_DATE_WHEN_THERE_IS_STARTTIME_AND_ENDTIME), expectedAB,
				expectedList);
	}

	// @@author A0139714B
	@Test
	public void execute_edit_InvalidIndex() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		assertCommandBehavior("edit 7 e/1900", String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX), expectedAB,
				expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_undo_add() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(fourTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4);
		
		model.resetData(new ListOfTask());
		for (Task t : fourTasks) {
			model.addTask(t);
		}
		
		assertTwoPartCommandBehavior("add KEY sduauo", "undo", 
							  String.format(UndoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_undo_delete() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		assertTwoPartCommandBehavior("delete 5", "undo", 
							  String.format(UndoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_undo_edit() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		Task toEdit = helper.generateEventTaskWithAllWithoutTag("", "blahblahblah", "", "", "");
		
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		assertTwoPartCommandBehavior("edit 5  i/blahblahblah", "undo", 
							  String.format(UndoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_undo_done() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		assertTwoPartCommandBehavior("done 5", "undo", 
							  String.format(UndoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}

	//@@author A0139714B
	@Test
	public void execute_undo_undone() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		model.doneTask(p5, true);
		
		assertThreePartCommandBehavior("list all", "undone 5", "undo", 
							  String.format(UndoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_undo_clear() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		assertTwoPartCommandBehavior("clear", "undo", 
							  String.format(UndoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_undo_emptyStack() throws Exception {
		assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_EMPTY_STACK));
	}
	
	//@@author A0139714B
	@Test
	public void execute_redo_emptyStack() throws Exception {
		assertCommandBehavior("redo", String.format(RedoCommand.MESSAGE_EMPTY_STACK));
	}
	
	//@@author A0139714B
	@Test
	public void execute_redo_add() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
		
		model.resetData(new ListOfTask());
		for (Task t : fourTasks) {
			model.addTask(t);
		}
		
		assertThreePartCommandBehavior("add KEY sduauo", "undo", "redo", 
							  String.format(RedoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_redo_delete() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(fourTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		assertThreePartCommandBehavior("delete 5", "undo", "redo",
							  String.format(RedoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_redo_edit() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		Task toEdit = helper.generateEventTaskWithAllWithoutTag("", "", "15-10-2016", "", "");
		
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		expectedAB.editTask(p5, toEdit);
		
		assertThreePartCommandBehavior("edit 5 d/15-10-2016", "undo", "redo",
							  String.format(RedoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_redo_done() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
	
		expectedAB.doneTask(p5, true);
		
		assertThreePartCommandBehavior("done 5", "undo", "redo",
							  String.format(RedoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
			
	
	//@@author A0139714B
	@Test
	public void execute_redo_undone() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescription("KE Y", "13-10-2016", "1800", "hi");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		model.doneTask(p5, true);
		
		assertFourPartCommandBehavior("list all", "undone 5", "undo", "redo",
							  String.format(RedoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_redo_clear() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithoutTaskDescriptionWithoutTag("bla bla KEY bla", "11-10-2016", "1500", "1800");
		Task p2 = helper.generateDeadlineTaskWithEndTimeWithoutTag("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800");
		Task p3 = helper.generateDeadlineTaskWithEndTimeWithoutTaskDescriptionWithoutTag("KE Y", "13-10-2016", "1800");
		Task p4 = helper.generateDeadlineTaskWithoutTaskDescriptionWithoutTag("keyKEY sduauo", "14-10-2016");
		Task p5 = helper.generateFloatingTaskWithoutTaskDescriptionWithoutTag("KEY sduauo");
		
		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, p4);
		ListOfTask expectedAB = helper.generateListOfTask(0);
		List<Task> expectedList = helper.generateTaskList(0);
		
		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}
		
		assertThreePartCommandBehavior("clear", "undo", "redo",
							  String.format(RedoCommand.MESSAGE_SUCCESS), 
							  expectedAB, 
							  expectedList);
	}
	
	//@@author A0139714B
	@Test
	public void execute_setdir_reset() throws Exception {
		logic.execute("setdir reset"); //reset directory to original file path
		assertCommandBehavior("setdir reset", SetDirectoryCommand.MESSAGE_SAME_AS_CURRENT);
	}
	
	//@@author A0139714B
	//@Test
	public void execute_setdir_invalidpath() throws Exception {
		logic.execute("setdir reset"); //reset directory to original file path
		assertCommandBehavior("setdir C:::\\/blah.xml", SetDirectoryCommand.MESSAGE_INVALID_PATH);
	}
	
	//@@author A0139714B
	@Test
	public void execute_setdir_validpath() throws Exception {
		logic.execute("setdir reset"); //reset directory to original file path
		String filePath = "C:/blah.xml";
		assertCommandBehavior("setdir C:/blah.xml", String.format(SetDirectoryCommand.MESSAGE_SUCCESS, filePath));
	}
	
	
	@Test
	public void execute_find_invalidArgsFormat() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
		assertCommandBehavior("find ", expectedMessage);
	}

	@Test
	public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500",
				"1800", "tag");
		Task pTarget2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "11-10-2016", "1500",
				"1800", "blah");
		Task p1 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "11-10-2016", "1500", "1800", "hi");
		Task pTarget3 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "11-10-2016", "1500", "1800", "bye");
		Task p2 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "11-10-2016", "1500", "1800", "yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2, pTarget3);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fiveTasks);

		assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}
	
	

	// @Test
	// TODO:fix this test
	public void execute_find_isNotCaseSensitive() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "06-12-2016", "1800", "1900",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("key key", "say goodbye", "03-10-2016", "1300", "1400", "hi");
		Task p4 = helper.generateEventTaskWithAll("KEy sduauo", "move", "10-09-2016", "1200", "1800", "bye");

		List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
		ListOfTask expectedAB = helper.generateListOfTask(fourTasks);
		List<Task> expectedList = fourTasks;
		helper.addToModel(model, fourTasks);

		assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}

	// @Test
	// TODO:fix this test
	public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500",
				"1800", "tag");
		Task pTarget2 = helper.generateEventTaskWithAll("bla rAnDoM bla bceofeia", "hello world", "22-09-2016", "1100",
				"1800", "blah");
		Task pTarget3 = helper.generateEventTaskWithAll("key key", "move around", "06-10-2017", "1100", "1200", "hi");
		Task p1 = helper.generateEventTaskWithAll("sduauo", "jump", "02-03-2016", "1300", "1400", "bye");

		List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
		ListOfTask expectedAB = helper.generateListOfTask(fourTasks);
		List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourTasks);

		assertCommandBehavior("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
				expectedAB, expectedList);
	}

	/**
	 * A utility class to generate test data.
	 */
	class TestDataHelper {

		Task adam() throws Exception {
			Name name = new Name("Adam Brown");
			Date date = new Date("23-06-2016");
			Time startTime = new Time("1900");
			Time endTime = new Time("2200");
			Tag tag1 = new Tag("tag1");
			Tag tag2 = new Tag("tag2");
			UniqueTagList tags = new UniqueTagList(tag1, tag2);
			return new Task(name, date, startTime, endTime, tags);
		}

		/**
		 * Generates a valid task using the given seed. Running this function
		 * with the same parameter values guarantees the returned task will have
		 * the same state. Each unique seed will generate a unique Task object.
		 *
		 * @param seed
		 *            used to generate the task data field values
		 */
		Task generateTask(int seed) throws Exception {
			return new Task(new Name("Task " + seed),
					new Date((seed % 2 == 1) ? "1" + seed + "-12-2" + seed + "22" : "1" + seed + "-12-212" + seed),
					new Time("0" + seed + "00"), new Time("0" + seed + "0" + seed),
					new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
		}

		/** Generates the correct add command based on the person given */
		String generateAddCommand(Task p) {
			StringBuffer cmd = new StringBuffer();

			cmd.append("add ");

			cmd.append(p.getName().toString());
			cmd.append(" d/").append(p.getDate().toString());
			cmd.append(" s/").append(p.getStartTime().toString());
			cmd.append(" e/").append(p.getEndTime().toString());
			UniqueTagList tags = p.getTags();
			for (Tag t : tags) {
				cmd.append(" t/").append(t.tagName);
			}

			return cmd.toString();
		}

		/**
		 * Generates an ListOfTask with auto-generated persons.
		 */
		ListOfTask generateListOfTask(int numGenerated) throws Exception {
			ListOfTask listOfTask = new ListOfTask();
			addToListOfTask(listOfTask, numGenerated);
			return listOfTask;
		}

		/**
		 * Generates an ListOfTask based on the list of Persons given.
		 */
		ListOfTask generateListOfTask(List<Task> tasks) throws Exception {
			ListOfTask listOfTask = new ListOfTask();
			addToListOfTask(listOfTask, tasks);
			return listOfTask;
		}

		/**
		 * Adds auto-generated Task objects to the given ListOfTask
		 * 
		 * @param listOfTask
		 *            The ListOfTask to which the Persons will be added
		 */
		void addToListOfTask(ListOfTask listOfTask, int numGenerated) throws Exception {
			addToListOfTask(listOfTask, generateTaskList(numGenerated));
		}

		/**
		 * Adds the given list of Persons to the given ListOfTask
		 */
		void addToListOfTask(ListOfTask listOfTask, List<Task> tasksToAdd) throws Exception {
			for (Task p : tasksToAdd) {
				listOfTask.addTask(p);
			}
		}

		/**
		 * Adds auto-generated Task objects to the given model
		 * 
		 * @param model
		 *            The model to which the Persons will be added
		 */
		void addToModel(Model model, int numGenerated) throws Exception {
			addToModel(model, generateTaskList(numGenerated));
		}

		/**
		 * Adds the given list of Persons to the given model
		 */
		void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
			for (Task p : tasksToAdd) {
				model.addTask(p);
			}
		}
		
		public void addToModel_ReadOnlyTask(Model model, List<? extends ReadOnlyTask> expectedList) {
			for(ReadOnlyTask task: expectedList){
				try {
					model.addTask((Task) task);
				} catch (DuplicateTaskException e) {
					e.printStackTrace();
				}
			}
			
		}

		/**
		 * Generates a list of Persons based on the flags.
		 */
		List<Task> generateTaskList(int numGenerated) throws Exception {
			List<Task> tasks = new ArrayList<>();
			for (int i = 1; i <= numGenerated; i++) {
				tasks.add(generateTask(i));
			}
			return tasks;
		}

		List<Task> generateTaskList(Task... persons) {
			return Arrays.asList(persons);
		}

		/**
		 * Generates a Task object with given name. Other fields will have some
		 * dummy values.
		 */
		Task generateEventTaskWithAll(String name, String taskDescription, String date, String startTime,
				String endTime, String tag) throws Exception {
			return new Task(new Name(name), new TaskDescription(taskDescription), new Date(date), new Time(startTime),
					new Time(endTime), new UniqueTagList(new Tag(tag)));
		}

		Task generateEventTaskWithAllWithoutTag(String name, String taskDescription, String date, String startTime,
				String endTime) throws Exception {
			return new Task(new Name(name), new TaskDescription(taskDescription), new Date(date), new Time(startTime),
					new Time(endTime), new UniqueTagList());
		}

		Task generateEventTaskWithoutTaskDescription(String name, String date, String startTime, String endTime,
				String tag) throws Exception {
			return new Task(new Name(name), new Date(date), new Time(startTime), new Time(endTime),
					new UniqueTagList(new Tag(tag)));
		}

		Task generateEventTaskWithoutTaskDescriptionWithoutTag(String name, String date, String startTime,
				String endTime) throws Exception {
			return new Task(new Name(name), new Date(date), new Time(startTime), new Time(endTime),
					new UniqueTagList());
		}

		Task generateDeadlineTaskWithEndTime(String name, String taskDescription, String date, String endTime,
				String tag) throws Exception {
			return new Task(new Name(name), new TaskDescription(taskDescription), new Date(date), new Time(endTime),
					new UniqueTagList(new Tag(tag)));
		}

		Task generateDeadlineTaskWithEndTimeWithoutTag(String name, String taskDescription, String date, String endTime) throws Exception {
			return new Task(new Name(name), new TaskDescription(taskDescription), new Date(date), new Time(endTime),
					new UniqueTagList());
		}

		Task generateDeadlineTaskWithEndTimeWithoutTaskDescription(String name, String date, String endTime, String tag)
				throws Exception {
			return new Task(new Name(name), new Date(date), new Time(endTime), new UniqueTagList(new Tag(tag)));
		}

		Task generateDeadlineTaskWithEndTimeWithoutTaskDescriptionWithoutTag(String name, String date, String endTime)
				throws Exception {
			return new Task(new Name(name), new Date(date), new Time(endTime), new UniqueTagList());
		}

		Task generateDeadlineTask(String name, String taskDescription, String date, String tag) throws Exception {
			return new Task(new Name(name), new TaskDescription(taskDescription), new Date(date),
					new UniqueTagList(new Tag(tag)));
		}

		Task generateDeadlineTask(String name, String taskDescription, String date) throws Exception {
			return new Task(new Name(name), new TaskDescription(taskDescription), new Date(date), new UniqueTagList());
		}

		Task generateDeadlineTaskWithoutTaskDescription(String name, String date, String tag) throws Exception {
			return new Task(new Name(name), new Date(date), new UniqueTagList(new Tag(tag)));
		}

		Task generateDeadlineTaskWithoutTaskDescriptionWithoutTag(String name, String date) throws Exception {
			return new Task(new Name(name), new Date(date), new UniqueTagList());
		}

		Task generateFloatingTask(String name, String taskDescription, String tag) throws Exception {
			return new Task(new Name(name), new TaskDescription(taskDescription), new UniqueTagList(new Tag(tag)));
		}

		Task generateFloatingTaskWithoutTag(String name, String taskDescription) throws Exception {
			return new Task(new Name(name), new TaskDescription(taskDescription), new UniqueTagList());
		}

		Task generateFloatingTaskWithoutTaskDescription(String name, String tag) throws Exception {
			return new Task(new Name(name), new UniqueTagList(new Tag(tag)));
		}

		Task generateFloatingTaskWithoutTaskDescriptionWithoutTag(String name) throws Exception {
			return new Task(new Name(name), new UniqueTagList());
		}
	}
	
		
}
