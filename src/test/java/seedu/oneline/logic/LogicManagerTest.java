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
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;
import seedu.oneline.storage.StorageManager;

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
import static seedu.oneline.commons.core.Messages.*;

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
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

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
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.myTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.myTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedTB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare task book state
        helper.addToModel(model, 2);

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
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

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
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook(threeTasks);
        helper.addToModel(model, threeTasks);

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
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

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
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskBook expectedAB = helper.generateTaskBook(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskBook expectedAB = helper.generateTaskBook(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskBook expectedAB = helper.generateTaskBook(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_undo_redo() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithName("Simple task");
        Task task2 = helper.generateTaskWithName("Harder task");
        Task task3 = helper.generateTaskWithName("Hardest task");
        TaskBook expectedTaskBook1 = new TaskBook(model.getTaskBook());
        logic.execute(helper.generateAddCommand(task1));
        TaskBook expectedTaskBook2 = new TaskBook(model.getTaskBook());
        logic.execute(helper.generateAddCommand(task2));
        TaskBook expectedTaskBook3 = new TaskBook(model.getTaskBook());
        logic.execute(helper.generateAddCommand(task3));
        
        // Undo command
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook3, Arrays.asList(task1, task2));
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook2, Arrays.asList(task1));
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook1, Collections.emptyList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_NO_PREVIOUS_STATE, expectedTaskBook1, Collections.emptyList());
        
        // Redo command
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_SUCCESS, expectedTaskBook2, Arrays.asList(task1));
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_SUCCESS, expectedTaskBook3, Arrays.asList(task1, task2));
        Task task4 = helper.generateTaskWithName("Crazy task");
        logic.execute(helper.generateAddCommand(task4));
        TaskBook expectedTaskBook4 = new TaskBook(model.getTaskBook());
        assertCommandBehavior("redo", RedoCommand.MESSAGE_NO_NEXT_STATE, expectedTaskBook4, Arrays.asList(task1, task2, task4));
        
        // Undo find command
        assertCommandBehavior(FindCommand.COMMAND_WORD + " harder", FindCommand.getMessageForTaskListShownSummary(1), expectedTaskBook4, Arrays.asList(task2));
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook4, Arrays.asList(task1, task2, task4));
        assertCommandBehavior(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_REDO_SUCCESS, expectedTaskBook4, Arrays.asList(task2));
        
        
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task myTask() throws Exception {
            TaskName name = new TaskName("Do seagull stuff");
            TaskTime startTime = new TaskTime("Sun Oct 16 21:35:45");
            TaskTime endTime = new TaskTime("Mon Oct 17 21:35:45");
            TaskTime deadline = new TaskTime("Sun Oct 23 21:35:45");
            TaskRecurrence recurrence = new TaskRecurrence("X");
            Tag tag = new Tag("tag1");
            return new Task(name, startTime, endTime, deadline, recurrence, tag);
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
                    new TaskName("Task " + seed),
                    new TaskTime("" + Math.abs(seed)),
                    new TaskTime("" + seed),
                    new TaskTime("" + seed),
                    new TaskRecurrence("" + seed),
                    new Tag("tag" + Math.abs(seed))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" .from ").append(p.getStartTime());
            cmd.append(" .to ").append(p.getEndTime());
            cmd.append(" .due ").append(p.getDeadline());
            cmd.append(" .every ").append(p.getRecurrence());
            cmd.append(" #").append(p.getTag().tagName);

            return cmd.toString();
        }

        /**
         * Generates an TaskBook with auto-generated tasks.
         */
        TaskBook generateTaskBook(int numGenerated) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, numGenerated);
            return taskBook;
        }

        /**
         * Generates an AddressBook based on the list of Tasks given.
         */
        TaskBook generateTaskBook(List<Task> tasks) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, tasks);
            return taskBook;
        }

        /**
         * Adds auto-generated Task objects to the given Task Book
         * @param taskBook The Task Book to which the Tasks will be added
         */
        void addToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
            addToTaskBook(taskBook, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given Task Book
         */
        void addToTaskBook(TaskBook taskBook, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskBook.addTask(p);
            }
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
                    new TaskName(name),
                    new TaskTime(""),
                    new TaskTime(""),
                    new TaskTime(""),
                    new TaskRecurrence(""),
                    new Tag("tag")
            );
        }
    }
}
