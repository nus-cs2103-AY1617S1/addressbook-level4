package w15c2.tusk.logic;

import com.google.common.eventbus.Subscribe;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.model.TaskManagerChangedEvent;
import w15c2.tusk.commons.events.ui.JumpToListRequestEvent;
import w15c2.tusk.commons.events.ui.ShowHelpRequestEvent;
import w15c2.tusk.logic.Logic;
import w15c2.tusk.logic.LogicManager;
import w15c2.tusk.logic.commands.*;
import w15c2.tusk.model.task.InMemoryTaskList;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.model.task.TaskManager;
import w15c2.tusk.storage.task.TaskStorageManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.*;

public class LogicManagerTest {
    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private InMemoryTaskList model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private UniqueItemCollection<Task> latestSavedTaskManager;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedTaskManager = abce.data;
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
        model = new TaskManager();
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempAliasesFile = saveFolder.getRoot().getPath() + "TempAliases.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new TaskStorageManager(tempTaskManagerFile, tempAliasesFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);
     
        latestSavedTaskManager = model.getTasks(); // last saved assumed to be up to date before.
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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,"Type help if you want to know the list of commands."));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new UniqueItemCollection<Task>(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedTaskManager} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskManager} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       UniqueItemCollection<Task> expectedTaskManager,
                                       List<? extends Task> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getCurrentFilteredTasks());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTasks());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }
    
}
