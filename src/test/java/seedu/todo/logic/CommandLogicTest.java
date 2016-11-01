package seedu.todo.logic;

import static org.junit.Assert.assertEquals;
import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.model.ToDoListChangedEvent;
import seedu.todo.commons.events.ui.JumpToListRequestEvent;
import seedu.todo.commons.events.ui.ShowHelpRequestEvent;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.Model;
import seedu.todo.model.ModelManager;
import seedu.todo.model.ReadOnlyToDoList;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.storage.StorageManager;
import seedu.todo.testutil.TestDataHelper;

/**
 * Parent class for all command logic test
 */
public class CommandLogicTest {
    
    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();
    
    @Rule
    public TemporaryFolder alternateFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    protected Model model;
    protected Logic logic;
    protected Config config;

    //These are for checking the correctness of the events raised
    protected ReadOnlyToDoList latestSavedToDoList;
    protected boolean helpShown;
    protected int targetedJumpIndex;

    protected TestDataHelper helper;
    protected DoDoBird expectedTDL;
    
    @Subscribe
    protected void handleLocalModelChangedEvent(ToDoListChangedEvent tdlce) {
        latestSavedToDoList = new DoDoBird(tdlce.data);
    }

    @Subscribe
    protected void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    protected void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        config = new Config();
        String tempDoDoBirdFile = saveFolder.getRoot().getPath() + "TempDoDoBird.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, config, new StorageManager(tempDoDoBirdFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedToDoList = new DoDoBird(model.getToDoList()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
        
        helper = new TestDataHelper();
        expectedTDL = new DoDoBird();
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }
    
    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'do do bird' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyToDoList, List)
     */
    protected void assertCommandBehavior(String inputCommand, String expectedMessage) throws IllegalValueException {
        assertCommandBehavior(inputCommand, expectedMessage, new DoDoBird(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal do do bird data are same as those in the {@code expectedToDoList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedToDoList} was saved to the storage file. <br>
     */
    protected void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyToDoList expectedToDoList,
                                       List<? extends ReadOnlyTask> expectedShownList) throws IllegalValueException {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        
        assertEquals(expectedMessage, result.feedbackToUser);
        System.out.println(model.getFilteredTaskList());
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedToDoList, model.getToDoList());

    }
    
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    protected void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) 
            throws IllegalValueException {
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
    protected void assertIndexNotFoundBehaviorForCommand(String commandWord) throws IllegalValueException {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);
        List<Task> rTaskList = helper.generateReverseTaskList(2);
        
        // set TDL state to 2 tasks
        model.resetData(new DoDoBird());
        for (Task p : taskList) {
            model.addTask(p);
        }

        if ("tag".equals(commandWord) || "untag".equals(commandWord)) {
            assertCommandBehavior(commandWord + " 3 hello", expectedMessage, model.getToDoList(), rTaskList);
        } else {
            assertCommandBehavior(commandWord + " 3", expectedMessage, model.getToDoList(), rTaskList);
        }
        
    }
}

