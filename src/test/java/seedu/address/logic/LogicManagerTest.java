package seedu.address.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.EventsCenter;
import seedu.address.logic.commands.*;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.model.TaskManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.AliasManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.task.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.storage.StorageManager;
import seedu.address.commons.core.Config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
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
    public void setup() {
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        String tempAliasManagerFile = saveFolder.getRoot().getPath() + "TempAliasManager.xml";
        model = new ModelManager(new TaskManager(), new Config(tempTaskManagerFile, tempPreferencesFile), new UserPrefs(), new AliasManager());
        logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile, tempAliasManagerFile));

        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1;
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
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
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.meetAdamSomeday();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddSomedayCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.meetAdamSomeday();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task manager
        
        System.out.println("model taskmgr task list: " + model.getTaskManager().getTaskList());
        for (ReadOnlyTask t : model.getFilteredTaskList()) {
        	System.out.println("filtered list task: " + t);
    	}
        System.out.println("add cmd: " + helper.generateAddSomedayCommand(toBeAdded));
        
        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddSomedayCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = MESSAGE_INVALID_DISPLAYED_INDEX;
        assertIncorrectIndexFormatBehaviorForCommand("del", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("del 500");
    }
    
//    @Test
//    public void execute_delete_removesCorrectTask() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        List<Task> threeTasks = helper.generateTaskList(3);
//
//        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
//        expectedAB.removeTask(threeTasks.get(1));
//        helper.addToModel(model, threeTasks);
//        
//        // to past in a list instead of a task
//        List<Task> taskToDelete = new ArrayList<>();
//        taskToDelete.add(threeTasks.get(1));
//
//        assertCommandBehavior("del 2",
//                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete),
//                expectedAB,
//                expectedAB.getTaskList());
//    }


//    @Test
//    public void execute_find_invalidArgsFormat() throws Exception {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
//        assertCommandBehavior("find ", expectedMessage);
//    }

    //@@author A0141019U-unused
//    @Test
//    public void execute_find_matchesPartialWordsInNames() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
//        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
//        Task pTarget3 = helper.generateTaskWithName("KEYKEYKEY sduauo");
//        Task p1 = helper.generateTaskWithName("KE Y");
//
//        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
//        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
//        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
//        Collections.sort(expectedList);
//        helper.addToModel(model, fourTasks);
//
//        assertCommandBehavior("find KEY",
//                Command.getMessageForTaskListShownSummary(expectedList.size()),
//                expectedAB,
//                expectedList);
//    }
    //@@author

//    @Test
//    public void execute_find_isNotCaseSensitive() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
//        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
//        Task p3 = helper.generateTaskWithName("key key");
//        Task p4 = helper.generateTaskWithName("KEy sduauo");
//
//        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
//        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
//        Collections.sort(fourTasks);
//        List<Task> expectedList = fourTasks;
//        helper.addToModel(model, fourTasks);
//
//        assertCommandBehavior("find KEY",
//                Command.getMessageForTaskListShownSummary(expectedList.size()),
//                expectedAB,
//                expectedList);
//    }

      
//    @Test
//    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
//        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
//        Task pTarget3 = helper.generateTaskWithName("key key");
//        Task p1 = helper.generateTaskWithName("sduauo");
//
//        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
//        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
//        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
//        helper.addToModel(model, fourTasks);
//
//        assertCommandBehavior("find key, rAnDoM",
//                Command.getMessageForTaskListShownSummary(expectedList.size()),
//                expectedAB,
//                expectedList);
//    }
    
 // @@author A0141019U
    @Test
	public void execute_undo_nothingToUndo() throws Exception {
		Stack<TaskManager> expectedStateHistory = new Stack<>();
		Stack<TaskManager> expectedUndoHistory = new Stack<>();

		assertUndoRedoCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_FAIL, new TaskManager(), expectedStateHistory,
				expectedUndoHistory);
	}
    
	@Test
	public void execute_multipleUndo_successful() throws Exception {
		TestDataHelper helper = new TestDataHelper();

		Task task1 = helper.meetAdamSomeday();
		Task task2 = helper.readBookBy5pmTomorrow();

		TaskManager emptyTaskManager = new TaskManager();

		TaskManager taskManagerOneTask = new TaskManager();
		helper.addToTaskManager(taskManagerOneTask, task1);

		TaskManager taskManagerTwoTasks = new TaskManager();
		helper.addToTaskManager(taskManagerTwoTasks, task1);
		helper.addToTaskManager(taskManagerTwoTasks, task2);

		
		// Current state [1 task], state history [0 tasks]
		Stack<TaskManager> expectedStateHistory = new Stack<>();
		expectedStateHistory.push(emptyTaskManager);
		Stack<TaskManager> expectedUndoHistory = new Stack<>();

		assertUndoRedoCommandBehavior(helper.generateAddSomedayCommand(task1),
				String.format(AddCommand.MESSAGE_SUCCESS, task1), taskManagerOneTask, expectedStateHistory,
				expectedUndoHistory);

		
		// Current state [2 tasks], state history [0 tasks, 1 task]
		expectedStateHistory.push(taskManagerOneTask);

		assertUndoRedoCommandBehavior(helper.generateAddDeadlineCommand(task2),
				String.format(AddCommand.MESSAGE_SUCCESS, task2), taskManagerTwoTasks, expectedStateHistory,
				expectedUndoHistory);

		
		// Current state [1 task], State history [0 tasks], undo history [2 tasks]
		expectedStateHistory.pop();
		expectedUndoHistory.push(taskManagerTwoTasks);

		assertUndoRedoCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, taskManagerOneTask,
				expectedStateHistory, expectedUndoHistory);

		
		// Current state [0 tasks], State history [], undo history [2 tasks, 1 tasks]
		expectedStateHistory.pop();
		expectedUndoHistory.push(taskManagerOneTask);

		assertUndoRedoCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, emptyTaskManager, expectedStateHistory,
				expectedUndoHistory);
	}
    
    @Test
	public void execute_redo_nothingToRedo() throws Exception {
		Stack<TaskManager> expectedStateHistory = new Stack<>();
		Stack<TaskManager> expectedUndoHistory = new Stack<>();

		assertUndoRedoCommandBehavior("redo", RedoCommand.MESSAGE_REDO_FAIL, new TaskManager(), expectedStateHistory,
				expectedUndoHistory);
	}
    
    @Test
	public void execute_redo_successful() throws Exception {
		TestDataHelper helper = new TestDataHelper();

		Task task1 = helper.meetAdamSomeday();

		TaskManager emptyTaskManager = new TaskManager();

		TaskManager taskManagerOneTask = new TaskManager();
		helper.addToTaskManager(taskManagerOneTask, task1);

		
		// Current state [1 task], state history [0 tasks]
		Stack<TaskManager> expectedStateHistory = new Stack<>();
		expectedStateHistory.push(emptyTaskManager);
		Stack<TaskManager> expectedUndoHistory = new Stack<>();

		assertUndoRedoCommandBehavior(helper.generateAddSomedayCommand(task1),
				String.format(AddCommand.MESSAGE_SUCCESS, task1), taskManagerOneTask, expectedStateHistory,
				expectedUndoHistory);
		
		// Current state [0 tasks], State history [], undo history [1 task]
		expectedStateHistory.pop();
		expectedUndoHistory.push(taskManagerOneTask);

		assertUndoRedoCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, emptyTaskManager,
				expectedStateHistory, expectedUndoHistory);

		
		// Current state [1 task], State history [0 tasks], undo history []
		expectedStateHistory.push(emptyTaskManager);
		expectedUndoHistory.pop();

		assertUndoRedoCommandBehavior("redo", RedoCommand.MESSAGE_REDO_SUCCESS, taskManagerOneTask, expectedStateHistory,
				expectedUndoHistory);
	}

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal state history stack <br>
     *      - the internal undo history stack <br>
     */
	private void assertUndoRedoCommandBehavior(String inputCommand, String expectedMessage,
			TaskManager expectedTaskManager, Stack<TaskManager> expectedStateHistory,
			Stack<TaskManager> expectedUndoHistory) throws Exception {

		// Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the message displayed is correct
        assertEquals(expectedMessage, result.feedbackToUser);
        
        //Confirm the current task manager state is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
        
        //Confirm the history stack contain the right data in the right order
        assertEquals(expectedStateHistory, model.getStateHistoryStack());
        assertEquals(expectedUndoHistory, model.getUndoHistoryStack());
    }
    //@@author
    
    
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskManager());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskManager(), taskList);
    }
    
    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskManager(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedTaskManager} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskManager} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements contain the right data in the right order
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getInternalTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task meetAdamSomeday() throws Exception {
            Name name = new Name("Meet Adam Brown");
            TaskType publicType = new TaskType("someday");
            Status status = new Status("pending");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, publicType, status, Optional.empty(), Optional.empty(), tags);
        }
        
        Task readBookBy5pmTomorrow() throws Exception {
            Name name = new Name("Read 50 Shades of Grey");
            TaskType publicType = new TaskType("deadline");
            Status status = new Status("pending");
            LocalDateTime tomorrow5pm = LocalDateTime.now().plusDays(1).withHour(17).truncatedTo(ChronoUnit.HOURS);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, publicType, status, Optional.empty(), Optional.of(tomorrow5pm), tags);
        }

		/**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new TaskType("someday"),
                    new Status("pending"),
                    Optional.empty(),
                    Optional.empty(),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates an add someday command based on the task given */
        String generateAddSomedayCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add '");
            cmd.append(p.getName().toString() + "'");
            
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }
        
        //@@author A0141019U-reused
        /** Generates an add deadline command based on the task given */
        String generateAddDeadlineCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add '");
            cmd.append(p.getName().toString() + "'");
            
            cmd.append("by " + p.getEndDate().get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskManager with auto-generated tasks.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of Tasks given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskManager.addTask(p);
            }
        }
        
        void addToTaskManager(TaskManager taskManager, Task toBeAdded) throws Exception {
        	taskManager.addTask(toBeAdded);
		}

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new TaskType("someday"),
                    new Status("pending"),
                    Optional.empty(),
                    Optional.empty(),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
