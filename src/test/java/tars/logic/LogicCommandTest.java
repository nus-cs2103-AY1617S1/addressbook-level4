package tars.logic;

import static org.junit.Assert.assertEquals;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static tars.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import tars.commons.core.Config;
import tars.commons.core.EventsCenter;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.ui.ShowHelpRequestEvent;
import tars.commons.exceptions.DataConversionException;
import tars.commons.util.ConfigUtil;
import tars.logic.commands.ClearCommand;
import tars.logic.commands.CommandResult;
import tars.logic.commands.EditCommand;
import tars.logic.commands.HelpCommand;
import tars.logic.parser.Prefix;
import tars.model.Model;
import tars.model.ModelManager;
import tars.model.ReadOnlyTars;
import tars.model.Tars;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Task;
import tars.model.task.rsv.RsvTask;
import tars.storage.StorageManager;

public class LogicCommandTest {
    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    protected Model model;
    protected Logic logic;
    private Config originalConfig;

    private static final String configFilePath = "config.json";

    protected static final Prefix namePrefix = new Prefix("/n");
    protected static final Prefix priorityPrefix = new Prefix("/p");
    protected static final Prefix dateTimePrefix = new Prefix("/dt");
    protected static final Prefix addTagPrefix = new Prefix("/ta");
    protected static final Prefix removeTagPrefix = new Prefix("/tr");

    // These are for checking the correctness of the events raised
    private ReadOnlyTars latestSavedTars;
    protected boolean helpShown;

    @Subscribe
    private void handleLocalModelChangedEvent(TarsChangedEvent tce) {
        latestSavedTars = new Tars(tce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Before
    public void setUp() {
        try {
            originalConfig = ConfigUtil.readConfig(configFilePath).get();
        } catch (DataConversionException e) {
            e.printStackTrace();
        }
        model = new ModelManager();
        String tempTarsFile = saveFolder.getRoot().getPath() + "TempTars.xml";
        String tempPreferencesFile =
                saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model,
                new StorageManager(tempTarsFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTars = new Tars(model.getTars()); // last saved assumed to be up to date before.
        helpShown = false;
    }

    @After
    public void tearDown() throws IOException {
        undoChangeInTarsFilePath();
        EventsCenter.clearSubscribers();
    }

    /*
     * A method to undo any changes to the Tars File Path during tests
     */
    protected void undoChangeInTarsFilePath() throws IOException {
        ConfigUtil.saveConfig(originalConfig, configFilePath);
    }

    /**
     * Executes the command and confirms that the result message is correct. Both the 'tars' and the
     * 'last shown list' are expected to be empty.
     * 
     * @see #assertCommandBehavior(String, String, ReadOnlyTars, List)
     */
    protected void assertCommandBehavior(String inputCommand,
            String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new Tars(),
                Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and also confirms that
     * the following three parts of the LogicManager object's state are as expected:<br>
     * - the internal tars data are same as those in the {@code expectedTars} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedTars} was saved to the storage file. <br>
     */
    protected void assertCommandBehavior(String inputCommand,
            String expectedMessage, ReadOnlyTars expectedTars,
            List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTars, model.getTars());
        assertEquals(expectedTars, latestSavedTars);
    }

    // @@author A0140022H
    protected void assertCommandBehaviorForList(String inputCommand,
            String expectedMessage, ReadOnlyTars expectedTars,
            List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTars, latestSavedTars);
    }

    protected void assertCommandBehaviorWithRsvTaskList(String inputCommand,
            String expectedMessage, ReadOnlyTars expectedTars,
            List<? extends ReadOnlyTask> expectedShownTaskList,
            List<? extends RsvTask> expectedShownRsvTaskList) throws Exception {

        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownTaskList, model.getFilteredTaskList());
        assertEquals(expectedShownRsvTaskList, model.getFilteredRsvTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTars, model.getTars());
        assertEquals(expectedTars, latestSavedTars);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command targeting a
     * single task in the shown list, using visible index.
     * 
     * @param commandWord to test assuming it targets a single task in the last shown list based on
     *        visible index.
     */
    protected void assertIncorrectIndexFormatBehaviorForCommand(
            String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord, expectedMessage); // index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); // index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); // index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); // index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour for the given command targeting a
     * single task in the shown list, using visible index.
     * 
     * @param commandWord to test assuming it targets a single task in the last shown list based on
     *        visible index.
     */
    protected void assertIndexNotFoundBehaviorForCommand(String commandWord)
            throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set TARS state to 2 tasks
        model.resetData(new Tars());
        for (Task p : taskList) {
            model.addTask(p);
        }

        if (EditCommand.COMMAND_WORD.equals(commandWord)) { // Only For Edit Command
            assertCommandBehavior(commandWord + " 3 /n changeTaskName",
                    expectedMessage, model.getTars(), taskList);
        } else { // For Select & Delete Commands
            assertCommandBehavior(commandWord + " 3", expectedMessage,
                    model.getTars(), taskList);
        }
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_clear() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new Tars(),
                Collections.emptyList());
    }

    @Test
    public void check_task_equals() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task taskA = helper.meetAdam();
        Task taskB = taskA;

        assertEquals(taskA, taskB);
        assertEquals(taskA.hashCode(), taskB.hashCode());
    }

    @Test
    public void check_name_equals() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task taskA = helper.meetAdam();
        Task taskB = taskA;

        assertEquals(taskA.getName(), taskB.getName());
        assertEquals(taskA.getName().hashCode(), taskB.getName().hashCode());
    }
}
