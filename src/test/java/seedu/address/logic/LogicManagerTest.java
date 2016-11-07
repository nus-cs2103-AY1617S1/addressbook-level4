package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.Config.DublicatedValueCustomCommandsException;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.events.ui.ShowHelpRequestEvent;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.logic.Logic;
import seedu.task.logic.LogicManager;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.HistoryCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.model.Model;
import seedu.task.model.ModelManager;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Interval;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.Status;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskColor;
import seedu.task.model.task.TimeInterval;
import seedu.task.model.task.Title;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.storage.StorageManager;

public class LogicManagerTest {

	/**
	 * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
	 */
	@Rule
	public TemporaryFolder saveFolder = new TemporaryFolder();

	private Model model;
	private Logic logic;

	// These are for checking the correctness of the events raised
	private ReadOnlyTaskManager latestSavedTaskManager;
	private boolean helpShown;
	private int targetedJumpIndex;

	TestDataHelper helper;

	@Subscribe
	private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) throws ParseException {
		latestSavedTaskManager = new TaskManager(abce.data);
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
	public void setup() throws ParseException {
		model = new ModelManager();
		helper = new TestDataHelper();
		String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
		String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
		logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
		EventsCenter.getInstance().registerHandler(this);

		latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date before.
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
	 * @see #assertCommandBehavior(String, String, ReadOnlyTaskManager, List)
	 */
	private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
		assertCommandBehavior(inputCommand, expectedMessage, new TaskManager(), Collections.emptyList());
	}

	/**
	 * Executes the command and confirms that the result message is correct and
	 * also confirms that the following three parts of the LogicManager object's
	 * state are as expected:<br>
	 * - the internal address book data are same as those in the
	 * {@code expectedTaskManager} <br>
	 * - the backing list shown by UI matches the {@code shownList} <br>
	 * - {@code expectedTaskManager} was saved to the storage file. <br>
	 */
	private void assertCommandBehavior(String inputCommand, String expectedMessage,
			ReadOnlyTaskManager expectedTaskManager, List<? extends ReadOnlyTask> expectedShownList) throws Exception {
		// Execute the command
		CommandResult result = logic.execute(inputCommand);

		// Confirm the ui display elements should contain the right data
		assertEquals(expectedMessage, result.feedbackToUser);
		assertEquals(expectedShownList, model.getFilteredTaskList());

		// Confirm the state of data (saved and in-memory) is as expected
		assertEquals(expectedTaskManager, model.getTaskManager());
		assertEquals(expectedTaskManager, latestSavedTaskManager);
	}
	
	/**
	 * Execute undo and redo command and confirms that the state of taskmanager
	 * is the same. 
	 */
	private void assertUndoAndRedoCommandBehavior(ReadOnlyTaskManager expectedTaskManager, List<? extends ReadOnlyTask> expectedShownList) throws ParseException{
		logic.execute("undo");
		logic.execute("redo");
		// Confirm the ui display elements should contain the right data
		assertEquals(expectedShownList, model.getFilteredTaskList());
		// Confirm the state of data (saved and in-memory) is as expected
		assertEquals(expectedTaskManager, model.getTaskManager());
		assertEquals(expectedTaskManager, latestSavedTaskManager);
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
	public void execute_clear() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		model.addTask(helper.generateTask(1));
		model.addTask(helper.generateTask(2));
		model.addTask(helper.generateTask(3));

		assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
	}

	@Test
	public void execute_add_noTitle() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
		assertCommandBehavior("add d/description", expectedMessage);
	}

	// @@author A0148083A
	@Test
	public void execute_add_event_noDueDate() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
				String.format(AddCommand.MESSAGE_TASK_USAGE, "due date"));
		assertCommandBehavior("add event d/without due date sd/12-12-2016", expectedMessage);
	}

	@Test
	public void execute_add_event_dueDateBeforeStartDate() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_INVALID_DATE);
		assertCommandBehavior("add event d/due date before start date sd/12-12-2016 dd/01-01-2016", expectedMessage);
	}
	// @@author

	// @@author A0153411W
		
	@Test
	public void execute_add_successful() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();
		Task toBeAdded = helper.homework();
		TaskManager expectedAB = new TaskManager();
		String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getAsText());
		expectedAB.addTask(toBeAdded);

		// execute command and verify result
		assertCommandBehavior(helper.generateAddCommand(toBeAdded), expectedMessage, expectedAB,
				expectedAB.getTaskList());
	}

	@Test
	public void execute_undo_add_successful() throws Exception {
		TaskManager expectedManager = new TaskManager();

		// Generate one task for add and undo commands
		Task toBeAdded = helper.homework();

		addTaskToManagerHelper(toBeAdded, expectedManager);
		assertAddAndUndoBehaviour(toBeAdded, expectedManager);
	}
	
	@Test
	public void execute_redo_add_successful() throws Exception {
		TaskManager expectedManager = new TaskManager();

		// Generate one task for add and undo commands
		Task toBeAdded = helper.homework();

		addTaskToManagerHelper(toBeAdded, expectedManager);
		assertAddAndUndoBehaviour(toBeAdded, expectedManager);
	}
	
	@Test
	public void execute_redo_delete_successful() throws Exception {
		TaskManager expectedManager = new TaskManager();
		Task toBeAdded = helper.homework();
		
		//Add task to manager
		addTaskToManagerHelper(toBeAdded, expectedManager);

		logic.execute(helper.generateDeleteCommand(0));
		
		assertAddAndUndoBehaviour(toBeAdded, expectedManager);
	}

	@Test
	public void execute_undo_manyAdds_successful() throws Exception {
		TaskManager expectedManager = new TaskManager();

		// Generate 10 tasks for add and undo commands
		List<Task> toBeAdded = helper.generateTaskList(10);
		for (Task toAdd : toBeAdded) {
			addTaskToManagerHelper(toAdd, expectedManager);
		}
		// Undo every add commands and verify result
		for (int i = 1; i <= toBeAdded.size(); i++) {
			Task taskToRemove = toBeAdded.get(toBeAdded.size() - i);
			assertAddAndUndoBehaviour(taskToRemove, expectedManager);
		}
	}

	@Test
	public void execute_undo_edit_successful() throws Exception {
		TaskManager expectedManager = new TaskManager();

		Task addedTask = helper.homework();
		Task editedTask = helper.generateTask(1);

		addTaskToManagerHelper(addedTask, expectedManager);
		editTaskInManagerHelper(1, addedTask, editedTask, expectedManager);
		assertEditAndUndoBehaviour(1, addedTask, editedTask, expectedManager);
	}
	

	@Test
	public void execute_undo_done_successful() throws Exception {
		TaskManager expectedManager = new TaskManager();

		Task task= helper.homework();
		addTaskToManagerHelper(task, expectedManager);
		assertDoneAndUndoTaskInManager(1, task, expectedManager); 
	}
	
	@Test
	public void execute_undo_delete_successful() throws Exception {
		TaskManager expectedManager = new TaskManager();

		Task task= helper.homework();
		addTaskToManagerHelper(task, expectedManager);
		assertDeleteAndUndoTaskInManager(1, task, expectedManager); 
	}
	
	@Test
	public void execute_undo_clear_successful() throws Exception {
		TaskManager expectedManager = new TaskManager();

		// Add tasks to delete them later
		List<Task> toBeAdded = helper.generateTaskList(10);
		for (Task toAdd : toBeAdded) {
			addTaskToManagerHelper(toAdd, expectedManager);
		}
		
		assertClearAndUndoTaskInManager(expectedManager); 
	}
	
	@Test
	public void execute_undo_redo_success() throws Exception {
		//Init task and task manager
		TaskManager expectedManager = new TaskManager();
		Task task= helper.homework();
		
		//Add task to task manager
		addTaskToManagerHelper(task, expectedManager);
		
		//Undo, redo and verify that state of task manager is the same
		assertUndoAndRedoCommandBehavior(expectedManager, expectedManager.getTaskList());		
	}
	
	@Test
	public void execute_custom_add_successful() throws Exception {
		//Init custom command for add
		final String CUSTOM_ADD_WORD_COMMAND = "a";
		helper.setCustomCommandHelper(AddCommand.COMMAND_WORD, CUSTOM_ADD_WORD_COMMAND);
		
		//Init task and task manager
		Task toBeAdded = helper.homework();
		TaskManager expectedAB = new TaskManager();
		
		//Add task to manager
		expectedAB.addTask(toBeAdded);
		
		String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getAsText());

		// execute command and verify result
		assertCommandBehavior(helper.generateAddCommand(CUSTOM_ADD_WORD_COMMAND, toBeAdded), expectedMessage, expectedAB,
				expectedAB.getTaskList());
	}
	
	@Test
	public void execute_custom_done_successful() throws Exception {
		//Set custom command for done
		final String CUSTOM_DONE_WORD_COMMAND = "D";
		helper.setCustomCommandHelper(DoneCommand.COMMAND_WORD, CUSTOM_DONE_WORD_COMMAND);
		
		//Init task and task manager
		Task toBeAdded = helper.homework();
		TaskManager manager = new TaskManager();
		
		//Add task to manager
		manager.addTask(toBeAdded);
		logic.execute(helper.generateAddCommand(toBeAdded));
		
		//Init expected Manager and task
		TaskManager expectedManager = new TaskManager(manager);
		ReadOnlyTask expectedTask=expectedManager.getTaskList().get(0);
		expectedTask.setStatus(new Status("COMPLETED"));
		
		String expectedMessage = String.format(DoneCommand.MESSAGE_COMPLETED_TASK_SUCCESS, expectedTask.getAsText());
		
		// execute command and verify result
		assertCommandBehavior(helper.generateDoneCommand(CUSTOM_DONE_WORD_COMMAND, 1), expectedMessage, expectedManager,
				expectedManager.getTaskList());
	}
	
	@Test
	public void execute_history_successful() throws Exception{
		StringBuilder history = new StringBuilder();
		for(int i =0; i<6;i++){
			executeAndAppendCommand("command "+i, history);
		}
		assertCommandBehavior(helper.generateHistoryCommand(), history.toString());	
	}
	
	private void executeAndAppendCommand(String command, StringBuilder history) throws ParseException{
		history.insert(0, command +"\n");
		logic.execute(command);
	}
	// @@author

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
		assertCommandBehavior(commandWord + " +1", expectedMessage); // index should be unsigned
		assertCommandBehavior(commandWord + " -1", expectedMessage); // index should be unsigned
		assertCommandBehavior(commandWord + " 0", expectedMessage); // index cannot be  0
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
	private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		List<Task> taskList = helper.generateTaskList(2);

		// set AB state to 2 tasks
		model.resetData(new TaskManager());
		for (Task p : taskList) {
			model.addTask(p);
		}

		assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskManager(), taskList);
	}

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
	public void execute_select_jumpsToCorrectTask() throws Exception {
		List<Task> threeTasks = helper.generateTaskList(3);

		TaskManager expectedAB = helper.generateTaskManager(threeTasks);
		helper.addToModel(model, threeTasks);

		assertCommandBehavior("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedAB,
				expectedAB.getTaskList());
		assertEquals(1, targetedJumpIndex);
		assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
	}

	@Test
	public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
	}

	@Test
	public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("delete");
	}

	@Test
	public void execute_delete_removesCorrectTask() throws Exception {
		List<Task> threeTasks = helper.generateTaskList(3);

		TaskManager expectedAB = helper.generateTaskManager(threeTasks);
		expectedAB.removeTask(threeTasks.get(1));
		helper.addToModel(model, threeTasks);

		assertCommandBehavior("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
				expectedAB, expectedAB.getTaskList());
	}

	@Test
	public void execute_find_invalidArgsFormat() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
		assertCommandBehavior("find ", expectedMessage);
	}

	// @@author A0139932X
	@Test
	public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
		Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
		Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
		Task p1 = helper.generateTaskWithName("KE Y");
		Task pTarget3 = helper.generateTaskWithName("KEYKEYKEY sduauo");

		List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, pTarget2, pTarget3);
		TaskManager expectedAB = helper.generateTaskManager(fourTasks);
		List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourTasks);

		assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}
	// author

	@Test
	public void execute_find_isNotCaseSensitive() throws Exception {
		Task p1 = helper.generateTaskWithName("bla bla KEY bla");
		Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
		Task p3 = helper.generateTaskWithName("key key");
		Task p4 = helper.generateTaskWithName("KEy sduauo");

		List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
		TaskManager expectedAB = helper.generateTaskManager(fourTasks);
		List<Task> expectedList = fourTasks;
		helper.addToModel(model, fourTasks);

		assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}

	@Test
	public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
		Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
		Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
		Task pTarget3 = helper.generateTaskWithName("key key");
		Task p1 = helper.generateTaskWithName("sduauo");

		List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
		TaskManager expectedAB = helper.generateTaskManager(fourTasks);
		List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourTasks);

		assertCommandBehavior("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
				expectedAB, expectedList);
	}

	// author A0139932X
	@Test
	public void execute_save_invalidArgsFormat() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE);
		assertCommandBehavior("save", expectedMessage);
	}

	@Test
	public void execute_save_successful() throws Exception {

		Config config = new Config();

		String expectedMessage = "Change save path:.\\test Updated";
		assertCommandBehavior("save .\\test", expectedMessage);
		config.setTaskManagerFilePath("data/taskmanager.xml");
		ConfigUtil.saveConfig(config, "config.json");

	}
	// @@author

	// @@author A0148083A
	@Test
	public void execute_doneInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
	}

	@Test
	public void execute_doneIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("done");
	}

	@Test
	public void execute_doneOnCompletedTask() throws Exception {
		List<Task> oneTask = helper.generateTaskList(1);
		helper.addToModel(model, oneTask);
		String expectedMessage = DoneCommand.MESSAGE_ALREADY_COMPLETED;
		CommandResult result = logic.execute("done 1");

		TaskManager expectedAB = helper.generateTaskManager(oneTask);

		assertCommandBehavior("done 1", expectedMessage, expectedAB, expectedAB.getTaskList());

	}

	@Test
	public void execute_done_success() throws Exception {
		List<Task> oneTasks = helper.generateTaskList(1);
		helper.addToModel(model, oneTasks);
		String expectedTask = oneTasks.get(0).getAsText().replaceAll("ONGOING", "COMPLETED");
		String expectedMessage = String.format(DoneCommand.MESSAGE_COMPLETED_TASK_SUCCESS, expectedTask);
		TaskManager expectedAB = helper.generateTaskManager(oneTasks);
		assertCommandBehavior("done 1", expectedMessage, expectedAB, expectedAB.getTaskList());
	}
	// @@author
	
	// @@author A0153411W
	/**
	 * Executes the undo command for delete and confirms that the result undo
	 * message and state of task manager are correct
	 */
	private void assertDeleteAndUndoTaskInManager(int taskId, Task task,TaskManager expectedManager)
			throws Exception {
		String commadForDelete= helper.generateDeleteCommand(taskId);
		logic.execute(commadForDelete);
		String expectedMessage = UndoCommand.PRE_MESSAGE + String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, task);
		// execute undo command and verify result
		assertCommandBehavior(helper.generateUndoCommand(), expectedMessage, expectedManager,
				expectedManager.getTaskList());
	}

	/**
	 * Executes the undo command for clear and confirms that the result undo
	 * message and state of task manager are correct
	 */
	private void assertClearAndUndoTaskInManager(TaskManager expectedManager)
			throws Exception {
		String commadForClear= helper.generateClearCommand();
		logic.execute(commadForClear);
		String expectedMessage = UndoCommand.PRE_MESSAGE + ClearCommand.MESSAGE_SUCCESS;
		// execute undo command and verify result
		assertCommandBehavior(helper.generateUndoCommand(), expectedMessage, expectedManager,
				expectedManager.getTaskList());
	}
	
	/**
	 * Executes the undo command for done and confirms that the result undo
	 * message and state of task manager are correct
	 */
	private void assertDoneAndUndoTaskInManager(int taskId, Task task,TaskManager expectedManager)
			throws Exception {
		String commadForDone= helper.generateDoneCommand(taskId);
		expectedManager.removeTask(task);
		Task doneTask = new Task(task); 
		doneTask.setStatus(new Status("COMPETED"));
		expectedManager.addAtSpecificPlace(task, taskId-1);
		logic.execute(commadForDone);
		String expectedMessage = UndoCommand.PRE_MESSAGE + String.format(DoneCommand.MESSAGE_COMPLETED_TASK_SUCCESS, task);
		// execute undo command and verify result
		assertCommandBehavior(helper.generateUndoCommand(), expectedMessage, expectedManager,
				expectedManager.getTaskList());
	}
	
	/**
	 * Executes the undo command for add and confirms that the result undo
	 * message and state of task manager are correct
	 */
	private void assertAddAndUndoBehaviour(Task toRemove, TaskManager expectedManager) throws Exception {
		// opposite command for add is to remove task
		expectedManager.removeTask(toRemove);
		String expectedMessage = UndoCommand.PRE_MESSAGE + String.format(AddCommand.MESSAGE_SUCCESS, toRemove);
		assertCommandBehavior(helper.generateUndoCommand(), expectedMessage, expectedManager,
				expectedManager.getTaskList());
	}
	
	/**
	 * Executes the undo command for edit and confirms that the result undo
	 * message and state of task manager are correct
	 */
	private void assertEditAndUndoBehaviour(int taskId, Task addedTask, Task editedTask, TaskManager expectedManager) throws Exception {
		// opposite command for add is to remove task
		expectedManager.removeTask(editedTask);
		expectedManager.addAtSpecificPlace(addedTask, taskId-1);
		String expectedMessage = UndoCommand.PRE_MESSAGE + String.format(EditCommand.MESSAGE_SUCCESS, addedTask);
		assertCommandBehavior(helper.generateUndoCommand(), expectedMessage, expectedManager,
				expectedManager.getTaskList());
	}
	
	/**
	 * Add task to TaskManager
	 */
	private void addTaskToManagerHelper(Task toAdd, TaskManager expectedManager)
			throws DuplicateTaskException, ParseException {
		String commadForAdd = helper.generateAddCommand(toAdd);
		logic.execute(commadForAdd);
		expectedManager.addTask(toAdd);
	}

	/**
	 * Edit task in TaskManager
	 * @throws TaskNotFoundException 
	 */
	private void editTaskInManagerHelper(int taskId, Task task, Task expectedTask, TaskManager expectedManager)
			throws DuplicateTaskException, ParseException, TaskNotFoundException {
		String commadForEdit = helper.generateEditCommand(expectedTask, taskId);
		expectedManager.removeTask(task);
		expectedManager.addAtSpecificPlace(expectedTask, taskId-1);
		logic.execute(commadForEdit);
	}
	// @@author

	/**
	 * A utility class to generate test data.
	 */
	class TestDataHelper {

		Task homework() throws Exception {
			Title title = new Title("Homework");
			Description description = new Description("Database Tutorials.");
			StartDate startDate = new StartDate("11-01-2012 00:00");
			DueDate dueDate = new DueDate("11-01-2012 23:59");
			Interval interval = new Interval("1");
			TimeInterval timeInterval = new TimeInterval("7");
			Status status = new Status("ONGOING");
			// @@author A0153751H
			TaskColor taskColor = new TaskColor("none");
			// @@author
			UniqueTagList tags = new UniqueTagList();
			return new Task(title, description, startDate, dueDate, interval, timeInterval, status, taskColor, tags);
		}

		/**
		 * Generates a valid task using the given seed. Running this function
		 * with the same parameter values guarantees the returned task will have
		 * the same state. Each unique seed will generate a unique Task object.
		 *
		 * @param seed
		 *            used to generate the task data field values
		 */
		// @@author A0153751H
		Task generateTask(int seed) throws Exception {
			return new Task(new Title("Task " + seed), new Description("Description " + seed),
					new StartDate("01-01-2016 00:00"), new DueDate("01-01-2016 23:59"), new Interval("1"),
					new TimeInterval("" + seed), new Status("ONGOING"), new TaskColor("none"),
					new UniqueTagList());
		}
		// @@author

		
		/** Generates the add correct command based on the task given */
		String generateAddCommand(Task t) {
			StringBuffer cmd = new StringBuffer();

			cmd.append("add ");

			cmd.append(t.getTitle().toString());
			cmd.append(" d/").append(t.getDescription());
			cmd.append(" sd/").append(t.getStartDate());
			cmd.append(" dd/").append(t.getDueDate());
			cmd.append(" i/").append(t.getInterval());
			cmd.append(" ti/").append(t.getTimeInterval());
			UniqueTagList tags = t.getTags();
			for (Tag tag : tags) {
				cmd.append(" t/").append(tag.tagName);
			}

			return cmd.toString();
		}
		
		// @@author A0153411W
		/** Generates the correct edit command based on the task given */
		String generateEditCommand(Task t, int index) {
			StringBuffer cmd = new StringBuffer();

			cmd.append("edit ");

			cmd.append(index);
			cmd.append(" t/").append(t.getTitle());
			cmd.append(" d/").append(t.getDescription());
			cmd.append(" sd/").append(t.getStartDate());
			cmd.append(" dd/").append(t.getDueDate());
			cmd.append(" i/").append(t.getInterval());
			cmd.append(" ti/").append(t.getTimeInterval());
			UniqueTagList tags = t.getTags();
			for (Tag tag : tags) {
				cmd.append(" ts/").append(tag.tagName);
			}
			
			return cmd.toString();
		}
		
		String generateAddCommand(String customWord, Task t) {
			StringBuffer cmd = new StringBuffer();

			cmd.append(customWord+" ");

			cmd.append(t.getTitle().toString());
			cmd.append(" d/").append(t.getDescription());
			cmd.append(" sd/").append(t.getStartDate());
			cmd.append(" dd/").append(t.getDueDate());
			cmd.append(" i/").append(t.getInterval());
			cmd.append(" ti/").append(t.getTimeInterval());
			UniqueTagList tags = t.getTags();
			for (Tag tag : tags) {
				cmd.append(" t/").append(tag.tagName);
			}
			return cmd.toString();
		}
		
		/** Sets custom word command */
		void setCustomCommandHelper(String commandWord, String userCommand) throws DublicatedValueCustomCommandsException{ 
			Config.getInstance().setCustomCommandFormat(commandWord, userCommand);
		}
		
	
		/** Generates the correct done command based on the index given */
		String generateDoneCommand(int index) {
			return DoneCommand.COMMAND_WORD+" "+index;
		}
		
		/** Generates the correct done command based on the index given */
		String generateDoneCommand(String customWord, int index) {
			return customWord+" "+index;
		}
		
		/** Generates the correct delete command based on the index given */
		String generateDeleteCommand(int index) {
			return DeleteCommand.COMMAND_WORD+" "+index;
		}
		
		/** Generates the correct delete command based on the index given */
		String generateDeleteCommand(String customWord, int index) {
			return customWord+" "+index;
		}
		
		/** Generates the correct delete command based on the index given */
		String generateClearCommand() {
			return ClearCommand.COMMAND_WORD;
		}
		// @@author

		/** Generates the correct undo command */
		String generateUndoCommand() {
			return UndoCommand.COMMAND_WORD;
		}

		/** Generates the correct undo command */
		String generateRedoCommand() {
			return RedoCommand.COMMAND_WORD;
		}
		
		/** Generates the history command */
		String generateHistoryCommand() {
			return HistoryCommand.COMMAND_WORD;
		}

		/**
		 * Generates an TaskManager with auto-generated tasks.
		 */
		TaskManager generateTaskManager(int numGenerated) throws Exception {
			TaskManager addressBook = new TaskManager();
			addToTaskManager(addressBook, numGenerated);
			return addressBook;
		}

		/**
		 * Generates an TaskManager based on the list of Tasks given.
		 */
		TaskManager generateTaskManager(List<Task> tasks) throws Exception {
			TaskManager addressBook = new TaskManager();
			addToTaskManager(addressBook, tasks);
			return addressBook;
		}

		/**
		 * Adds auto-generated Task objects to the given TaskManager
		 * 
		 * @param addressBook
		 *            The TaskManager to which the Tasks will be added
		 */
		void addToTaskManager(TaskManager addressBook, int numGenerated) throws Exception {
			addToTaskManager(addressBook, generateTaskList(numGenerated));
		}

		/**
		 * Adds the given list of Tasks to the given TaskManager
		 */
		void addToTaskManager(TaskManager addressBook, List<Task> tasksToAdd) throws Exception {
			for (Task p : tasksToAdd) {
				addressBook.addTask(p);
			}
		}

		/**
		 * Adds auto-generated Task objects to the given model
		 * 
		 * @param model
		 *            The model to which the Tasks will be added
		 */
		void addToModel(Model model, int numGenerated) throws Exception {
			addToModel(model, generateTaskList(numGenerated));
		}

		/**
		 * Adds the given list of Tasks to the given model
		 */
		void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
			for (Task p : tasksToAdd) {
				model.addTask(p);
			}
		}

		/**
		 * Generates a list of Tasks based on the flags.
		 */
		List<Task> generateTaskList(int numGenerated) throws Exception {
			List<Task> tasks = new ArrayList<>();
			for (int i = 1; i <= numGenerated; i++) {
				tasks.add(generateTask(i));
			}
			return tasks;
		}
		
		/**
		 * Generates a list of Tasks based on the flags and seed.
		 */
		List<Task> generateTaskList(int numGenerated, int seed) throws Exception {
			List<Task> tasks = new ArrayList<>();
			for (int i = 1; i <= numGenerated; i++) {
				tasks.add(generateTask(seed++));
			}
			return tasks;
		}

		List<Task> generateTaskList(Task... tasks) {
			return Arrays.asList(tasks);
		}

		/**
		 * Generates a Task object with given name. Other fields will have some
		 * dummy values.
		 */
		Task generateTaskWithName(String name) throws Exception {
			return new Task(new Title(name), new Description("Description"), new StartDate("11-01-2012 00:00"),
					new DueDate("11-01-2012 23:59"), new Interval("7"), new TimeInterval("1"), new Status("ONGOING"),
					new TaskColor("none"), new UniqueTagList());
		}
	}
}
