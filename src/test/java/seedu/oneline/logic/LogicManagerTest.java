package seedu.oneline.logic;

import com.google.common.eventbus.Subscribe;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.events.model.TaskBookChangedEvent;
import seedu.oneline.commons.events.ui.JumpToListRequestEvent;
import seedu.oneline.commons.events.ui.ShowHelpRequestEvent;
import seedu.oneline.logic.Logic;
import seedu.oneline.logic.LogicManager;
import seedu.oneline.logic.commands.*;
import seedu.oneline.model.Model;
import seedu.oneline.model.ModelManager;
import seedu.oneline.model.ReadOnlyTaskBook;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.*;
import seedu.oneline.storage.StorageManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.oneline.commons.core.Messages.*;
import seedu.oneline.testutil.TestDataHelper;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskBook latestSavedTaskBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskBookChangedEvent abce) {
        latestSavedTaskBook = new TaskBook(abce.data);
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
        String tempTaskBookFile = saveFolder.getRoot().getPath() + "TempTaskBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskBook = new TaskBook(model.getTaskBook()); // last saved assumed to be up to date before.
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
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
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
        model.addTask(TestDataHelper.generateTask(1));
        model.addTask(TestDataHelper.generateTask(2));
        model.addTask(TestDataHelper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBook(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] .from A .to B .due C .every D", TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);
        // TODO: ADD PROPER CONSTRAINTS
        //assertCommandBehavior(
        //        "add Valid Name p/not_numbers e/valid@e.mail a/valid, address", Phone.MESSAGE_PHONE_CONSTRAINTS);
        //assertCommandBehavior(
        //        "add Valid Name p/12345 e/notAnEmail a/valid, address", Email.MESSAGE_EMAIL_CONSTRAINTS);
        //assertCommandBehavior(
        //        "add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        Task toBeAdded = TestDataHelper.myTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(TestDataHelper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        Task toBeAdded = TestDataHelper.myTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task book

        // execute command and verify result
        assertCommandBehavior(
                TestDataHelper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        TaskBook expectedTB = TestDataHelper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare task book state
        TestDataHelper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedTB,
                expectedList);
    }


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
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        List<Task> taskList = TestDataHelper.generateTaskList(2);

        // set Task book state to 2 tasks
        model.resetData(new TaskBook());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskBook(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        List<Task> threeTasks = TestDataHelper.generateTaskList(3);

        TaskBook expectedAB = TestDataHelper.generateTaskBook(threeTasks);
        TestDataHelper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        List<Task> threeTasks = TestDataHelper.generateTaskList(3);

        TaskBook expectedAB = TestDataHelper.generateTaskBook(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        TestDataHelper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        Task pTarget1 = TestDataHelper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = TestDataHelper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = TestDataHelper.generateTaskWithName("KE Y");
        Task p2 = TestDataHelper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = TestDataHelper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskBook expectedAB = TestDataHelper.generateTaskBook(fourTasks);
        List<Task> expectedList = TestDataHelper.generateTaskList(pTarget1, pTarget2);
        TestDataHelper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        Task p1 = TestDataHelper.generateTaskWithName("bla bla KEY bla");
        Task p2 = TestDataHelper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = TestDataHelper.generateTaskWithName("key key");
        Task p4 = TestDataHelper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = TestDataHelper.generateTaskList(p3, p1, p4, p2);
        TaskBook expectedAB = TestDataHelper.generateTaskBook(fourTasks);
        List<Task> expectedList = fourTasks;
        TestDataHelper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        Task pTarget1 = TestDataHelper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = TestDataHelper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = TestDataHelper.generateTaskWithName("key key");
        Task p1 = TestDataHelper.generateTaskWithName("sduauo");

        List<Task> fourTasks = TestDataHelper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskBook expectedAB = TestDataHelper.generateTaskBook(fourTasks);
        List<Task> expectedList = TestDataHelper.generateTaskList(pTarget1, pTarget2, pTarget3);
        TestDataHelper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    
    //---------------- Tests for doneCommand --------------------------------------
    /*
     * Invalid equivalence partitions for index: null, signed integer, non-numeric characters
     * Invalid equivalence partitions for index: index larger than no. of tasks in taskBook
     * The two test cases below test invalid input above one by one.
     */
    
    @Test
    public void execute_doneInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(DoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }
    
    @Test
    public void execute_doneIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
    }
    
    @Test
    public void execute_undo_redo() throws Exception {
        Task task1 = TestDataHelper.generateTask(1);
        Task task2 = TestDataHelper.generateTask(2);
        Task task3 = TestDataHelper.generateTask(3);
        TaskBook expectedTaskBook1 = new TaskBook(model.getTaskBook());
        logic.execute(TestDataHelper.generateAddCommand(task1));
        TaskBook expectedTaskBook2 = new TaskBook(model.getTaskBook());
        logic.execute(TestDataHelper.generateAddCommand(task2));
        TaskBook expectedTaskBook3 = new TaskBook(model.getTaskBook());
        logic.execute(TestDataHelper.generateAddCommand(task3));
        
        // Undo command
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook3, Arrays.asList(task1, task2));
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook2, Arrays.asList(task1));
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook1, Collections.emptyList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_NO_PREVIOUS_STATE, expectedTaskBook1, Collections.emptyList());
        
        // Redo command
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_SUCCESS, expectedTaskBook2, Arrays.asList(task1));
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_SUCCESS, expectedTaskBook3, Arrays.asList(task1, task2));
        Task task4 = TestDataHelper.generateTask(4);
        logic.execute(TestDataHelper.generateAddCommand(task4));
        TaskBook expectedTaskBook4 = new TaskBook(model.getTaskBook());
        assertCommandBehavior("redo", RedoCommand.MESSAGE_NO_NEXT_STATE, expectedTaskBook4, Arrays.asList(task1, task2, task4));
    }

}
