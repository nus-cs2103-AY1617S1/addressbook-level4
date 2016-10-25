package teamfour.tasc.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import teamfour.tasc.logic.commands.*;
import teamfour.tasc.model.task.*;
import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.events.model.TaskListChangedEvent;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.commons.events.ui.ShowHelpRequestEvent;
import teamfour.tasc.commons.util.DateUtil;
import teamfour.tasc.logic.Logic;
import teamfour.tasc.logic.LogicManager;
import teamfour.tasc.logic.commands.AddCommand;
import teamfour.tasc.logic.commands.ClearCommand;
import teamfour.tasc.logic.commands.Command;
import teamfour.tasc.logic.commands.CommandHelper;
import teamfour.tasc.logic.commands.CommandResult;
import teamfour.tasc.logic.commands.CompleteCommand;
import teamfour.tasc.logic.commands.DeleteCommand;
import teamfour.tasc.logic.commands.ExitCommand;
import teamfour.tasc.logic.commands.FindCommand;
import teamfour.tasc.logic.commands.HelpCommand;
import teamfour.tasc.logic.commands.HideCommand;
import teamfour.tasc.logic.commands.SelectCommand;
import teamfour.tasc.logic.commands.ShowCommand;
import teamfour.tasc.logic.commands.UpdateCommand;
import teamfour.tasc.model.Model;
import teamfour.tasc.model.ModelManager;
import teamfour.tasc.model.ReadOnlyTaskList;
import teamfour.tasc.model.TaskList;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Name;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.util.TaskCompleteConverter;
import teamfour.tasc.storage.StorageManager;
import teamfour.tasc.testutil.TaskBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.text.DateFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static teamfour.tasc.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskList latestSavedTaskList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskListChangedEvent abce) {
        latestSavedTaskList = new TaskList(abce.data);
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

        latestSavedTaskList = new TaskList(model.getTaskList()); // last saved assumed to be up to date before.
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
     * Both the 'task list' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskList, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskList(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task list data are same as those in the {@code expectedTaskList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskList expectedTaskList,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskList, model.getTaskList());
        assertEquals(expectedTaskList, latestSavedTaskList);
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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add \"valid\" tag invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskList expectedAB = new TaskList();
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
        Task toBeAdded = helper.adam();
        TaskList expectedAB = new TaskList();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // Task already in internal task list

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_list_showsDefaultUncompletedTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskList expectedAB = helper.generateTaskList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task list state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_withSorting_showsUncompleted() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskList expectedAB = helper.generateTaskList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task list state
        helper.addToModel(model, 2);

        assertCommandBehavior("list sort earliest first",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_showsCompletedTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list completed",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_showsOverdue() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list overdue",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_showsRecurring() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList();

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list recurring",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_showsByDeadline() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list by 1 jan 2015",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_showsFromStartTime() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list from 1 jan 2020",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_showsToEndTime() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list to 1 jan 1950",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_showsOneTag() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list tag tag1",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_showsMultipleTags() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1, task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list tag tag1 tag2 tag3 tag4",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_list_MultipleFilters_showsCompletedFromStartTimeOneTag() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("list completed from 1 jan 1998 tag tag3",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_show_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE);
        assertCommandBehavior("show", expectedMessage);
    }

    @Test
    public void execute_show_showsCompletedTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("show completed",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_show_showsOnDate() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList();

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("show on 27 dec 2000",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_show_showsByDeadline() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("show by 1 jan 2015",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_show_showsFromStartTime() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("show from 1 jan 2020",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_show_showsToEndTime() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("show to 1 jan 1950",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_show_showsOneTag() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("show tag tag1",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_show_showsMultipleTags() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1, task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("show tag tag1 tag2 tag3 tag4",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_show_MultipleFilters_showsCompletedFromStartTimeOneTag() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("show completed from 1 jan 1998 tag tag3",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_hide_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE);
        assertCommandBehavior("hide", expectedMessage);
    }

    @Test
    public void execute_hide_hidesCompletedTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("hide completed",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_hide_hidesOnDate() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1, task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("hide on 27 dec 2000",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_hide_hidesByDeadline() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task1);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("hide by 1 jan 2015",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_hide_hidesFromStartTime() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("hide from 1 jan 2020",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_hide_hidesToEndTime() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("hide to 1 jan 1950",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_hide_hidesOneTag() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList(task2);

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("hide tag tag1",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_hide_hidesMultipleTags() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList();

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("hide tag tag1 tag2 tag3 tag4",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_hide_MultipleFilters_showsCompletedFromStartTimeOneTag() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.adam();
        Task task2 = helper.john();
        task2 = new TaskCompleteConverter(task2, DateUtil.getCurrentTime()).getCompletedTask();
        List<Task> list = helper.generateTaskList(task1, task2);
        TaskList expectedAB = helper.generateTaskList(list);
        List<? extends ReadOnlyTask> expectedList = helper.generateTaskList();

        // prepare task list state
        helper.addToModel(model, list);

        assertCommandBehavior("hide completed from 1 jan 1998 tag tag3",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single Task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single Task in the last shown list based on visible index.
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
     * targeting a single Task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single Task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTasksList(2);

        // set AB state to 2 Tasks
        model.resetData(new TaskList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskList(), taskList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the select command
     * targeting a single Task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single Task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommandSelect(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX + "\n" + "Valid index range: 1 to 2";
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTasksList(2);

        // set AB state to 2 Tasks
        model.resetData(new TaskList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskList(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommandSelect("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTasksList(3);

        TaskList expectedAB = helper.generateTaskList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }



    @Test
    public void execute_updateInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("update", expectedMessage);
    }

    @Test
    public void execute_updateIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("update");
    }

    @Test
    public void execute_update_updatesCorrectTaskWithDeletedInfo() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        List<Task> threeTasks = helper.generateTasksList(3);
        helper.addToModel(model, threeTasks);

        final int taskIndexToUpdate = 1;

        UniqueTagList newTagList = threeTasks.get(taskIndexToUpdate).getTags();
        Tag tagToRemove = newTagList.iterator().next();
        newTagList.remove(tagToRemove);

        TaskList expectedTaskList = helper.generateTaskList(threeTasks);
        Task newTask = new Task(threeTasks.get(taskIndexToUpdate).getName(),
                new Complete(false),
                new Deadline(),
                new Period(),
                new Recurrence(),
                newTagList);
        expectedTaskList.updateTask(threeTasks.get(taskIndexToUpdate), newTask);

        String inputString = "update 2 removeby removefrom removeto removerepeatdeadline "
                + "removerepeattime removetag " + tagToRemove.tagName;

        assertCommandBehavior(inputString,
                String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, newTask),
                expectedTaskList,
                expectedTaskList.getTaskList());
    }

    @Test
    public void execute_update_updatesCorrectTaskWithNewInfo() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        List<Task> threeTasks = helper.generateTasksList(3);
        helper.addToModel(model, threeTasks);

        final int taskIndexToUpdate = 1;

        String deadlineString = "12 May 2000 00:00:00";
        String startTimeString = "9 May 2000 00:00:00";
        String endTimeString = "10 May 2000 00:00:00";

        Date deadline = CommandHelper.convertStringToDate(deadlineString);
        Date startTime = CommandHelper.convertStringToDate(startTimeString);
        Date endTime = CommandHelper.convertStringToDate(endTimeString);

        UniqueTagList newTagList = threeTasks.get(taskIndexToUpdate).getTags();
        newTagList.add(new Tag("Hey"));

        TaskList expectedTaskList = helper.generateTaskList(threeTasks);
        Task newTask = new Task(new Name("New Val"), new Complete(false), new Deadline(deadline),
                new Period(startTime, endTime), new Recurrence(Recurrence.Pattern.DAILY, 3),
                newTagList);
        expectedTaskList.updateTask(threeTasks.get(taskIndexToUpdate), newTask);

        String inputString = "update 2 name New Val by " + deadlineString + " from "
                + startTimeString + " to " + endTimeString
                + " repeat daily 3 tag Hey";

        assertCommandBehavior(inputString,
                String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, newTask),
                expectedTaskList,
                expectedTaskList.getTaskList());
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
        List<Task> threeTasks = helper.generateTasksList(3);

        TaskList expectedAB = helper.generateTaskList(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_completeInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("complete", expectedMessage);
    }

    @Test
    public void execute_completeIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("complete");
    }

    @Test
    public void execute_complete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTasksList(3);

        TaskList expectedTL = helper.generateTaskList(threeTasks);
        Task targetedTask = threeTasks.get(1);
        Task completedTask = new TaskCompleteConverter(targetedTask, DateUtil.getCurrentTime()).getCompletedTask();
        expectedTL.updateTask(targetedTask, completedTask);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("complete 2",
                String.format(CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS, completedTask),
                expectedTL,
                expectedTL.getTaskList());
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
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
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, p2, pTarget2);
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
        TaskList expectedAB = helper.generateTaskList(fourTasks);
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
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        //Edited for floating tasks
        Task adam() throws Exception {
            Name name = new Name("Adam Brown");

            // TODO update test case
            Complete complete = new Complete(false);
            Deadline deadline = new Deadline();
            Period period = new Period();
            Recurrence recurrence = new Recurrence();

            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, complete, deadline, period, recurrence, tags);
        }

        // This is a copy of Adam Brown with slightly different values
        Task john() throws Exception {
            Name name = new Name("John Doe");

            // TODO update test case
            Complete complete = new Complete(false);
            Calendar c = Calendar.getInstance();
            c.set(2000, 12, 27, 12, 0, 0);
            Date d1 = c.getTime();
            c.set(2000, 12, 30, 12, 0, 0);
            Date d2 = c.getTime();
            Deadline deadline = new Deadline(d1);
            Period period = new Period(d1, d2);
            Recurrence recurrence = new Recurrence();

            Tag tag1 = new Tag("tag2");
            Tag tag2 = new Tag("tag3");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, complete, deadline, period, recurrence, tags);
        }

        /**
         * Generates a valid Task using the given seed.
         * Running this function with the same parameter values guarantees the returned Task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the Task data field values
         */
        Task generateTask(int seed) throws Exception {
            // TODO update test case
            return new Task(
                    new Name("Task " + seed),
                    new Complete(false),
                    new Deadline(),
                    new Period(),
                    new Recurrence(),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the Task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append( "\"" + p.getName().toString() + "\" ");

            UniqueTagList tags = p.getTags();
            cmd.append("tag ");
            for(Tag t: tags){
                cmd.append(t.tagName + " ");
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskList with auto-generated Tasks.
         */
        TaskList generateTaskList(int numGenerated) throws Exception{
            TaskList taskList = new TaskList();
            addToTaskList(taskList, numGenerated);
            return taskList;
        }

        /**
         * Generates an TaskList based on the list of Tasks given.
         */
        TaskList generateTaskList(List<Task> tasks) throws Exception{
            TaskList taskList = new TaskList();
            addToTaskList(taskList, tasks);
            return taskList;
        }

        /**
         * Adds auto-generated Task objects to the given TaskList
         * @param taskList The TaskList to which the Tasks will be added
         */
        void addToTaskList(TaskList taskList, int numGenerated) throws Exception{
            addToTaskList(taskList, generateTasksList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskList
         */
        void addToTaskList(TaskList taskList, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskList.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTasksList(numGenerated));
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
        List<Task> generateTasksList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        // TODO update test case
        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Complete(false),
                    new Deadline(),
                    new Period(),
                    new Recurrence(),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
