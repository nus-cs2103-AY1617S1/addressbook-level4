
package seedu.ggist.logic;

import com.google.common.eventbus.Subscribe;

import seedu.ggist.commons.core.EventsCenter;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.events.model.TaskManagerChangedEvent;
import seedu.ggist.commons.events.ui.JumpToListRequestEvent;
import seedu.ggist.commons.events.ui.ShowHelpRequestEvent;
import seedu.ggist.logic.Logic;
import seedu.ggist.logic.LogicManager;
import seedu.ggist.logic.commands.*;
import seedu.ggist.model.TaskManager;
import seedu.ggist.model.Model;
import seedu.ggist.model.ModelManager;
import seedu.ggist.model.ReadOnlyTaskManager;
import seedu.ggist.model.tag.Tag;
import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.*;
import seedu.ggist.storage.StorageManager;

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
import static seedu.ggist.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     **/
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
        model = new ModelManager();
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
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task manager' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskManager(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task manager data are same as those in the {@code expectedTaskManager} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskManager} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
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
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add", expectedMessage);
  /*      assertCommandBehavior(
                "add Valid TaskName, dec 12", expectedMessage);
        assertCommandBehavior(
                "add Valid TaskName, 12 dec, 0000-1234, abcd", expectedMessage); */
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add Valid TaskName, abcd, 1800", TaskDate.MESSAGE_DATE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid TaskName, 12 dec, 5555", TaskTime.MESSAGE_TIME_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.bungee();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS,"floating", toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

/*    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.bungee();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task manager

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());
                
    } */

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
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
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskManager());
        for (Task t : taskList) {
            model.addTask(t);
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
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
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
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
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

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_search_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE);
        assertCommandBehavior("search ", expectedMessage);
    }

 /*   @Test
    public void execute_search_onlyMatchesFullWordsInTaskNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithTaskName("bla bla KEY bla");
        Task tTarget2 = helper.generateTaskWithTaskName("bla KEY bla bceofeia");
        Task t1 = helper.generateTaskWithTaskName("KE Y");
        Task t2 = helper.generateTaskWithTaskName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(t1, tTarget1, t2, tTarget2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("search KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
*/
    @Test
    public void execute_search_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithTaskName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithTaskName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithTaskName("key key");
        Task p4 = helper.generateTaskWithTaskName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, t1, p4, p2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("search KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_search_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithTaskName("bla bla KEY bla");
        Task tTarget2 = helper.generateTaskWithTaskName("bla rAnDoM bla bceofeia");
        Task tTarget3 = helper.generateTaskWithTaskName("key key");
        Task t1 = helper.generateTaskWithTaskName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(tTarget1, t1, tTarget2, tTarget3);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("search key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task bungee() throws Exception {
            TaskName taskName = new TaskName("go bungee jumping");
            TaskDate startDate = new TaskDate("Sun, 16 Dec 16");
            TaskTime startTime = new TaskTime("18:00 pm");
            TaskDate endDate = new TaskDate("Tue, 18 Dec 16");
            TaskTime endTime = new TaskTime("20:00 pm");
            Priority priority = new Priority("high");
            return new Task(taskName, startDate, startTime, endDate, endTime, priority);
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
                    new TaskDate("Wed, 16 Nov 2" + Math.abs(seed)),
                    new TaskTime("12:3"+seed + " pm"),
                    new TaskDate("Thu, 20 Nov 2" + Math.abs(seed)),
                    new TaskTime("21:3" + seed +" pm"),
                    new Priority("high")
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getTaskName().toString());
            cmd.append(",").append(p.getStartDate());
            cmd.append(" ").append(p.getStartTime());
            cmd.append(",").append(p.getEndDate());
            cmd.append(" ").append(p.getEndTime());
            cmd.append(",").append(p.getPriority().toString());
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
         * Generates an TaskManager based on the list of tasks given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception{
            for(Task t: tasksToAdd){
                taskManager.addTask(t);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of tasks based on the flags.
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
         * Generates a Task object with given TaskName. Other fields will have some dummy values.
         */
        Task generateTaskWithTaskName(String taskName) throws Exception {
            return new Task(
                    new TaskName(taskName),
                    new TaskDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED),
                    new TaskTime(Messages.MESSAGE_NO_START_TIME_SET),
                    new TaskDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED),
                    new TaskTime(Messages.MESSAGE_NO_START_TIME_SET),
                    new Priority ("high")
            );
        }
    }
}
