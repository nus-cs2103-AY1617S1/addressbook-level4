package guitests;

import guitests.guihandles.*;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import seedu.taskitty.TestApp;
import seedu.taskitty.commons.core.EventsCenter;
import seedu.taskitty.model.TaskManager;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.testutil.TestUtil;
import seedu.taskitty.testutil.TypicalTestTask;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A GUI Test class for TaskManager.
 */
public abstract class TaskManagerGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTask td = new TypicalTestTask();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle taskListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    private Stage stage;

    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            mainMenu = mainGui.getMainMenu();
            taskListPanel = mainGui.getPersonListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TaskManager getInitialData() {
        TaskManager ab = TestUtil.generateEmptyTaskManager();
        TypicalTestTask.loadTaskManagerWithSampleData(ab);
        return ab;
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the person shown in the card is same as the given person
     */
    public void assertMatching(ReadOnlyTask person, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndPerson(card, person));
    }
    
    //@@author A0130853L
    /**
     * Asserts the task shown is marked as done by verifying that it has the "#done" CSS style.
     */
    public void assertMarkAsDone(TaskCardHandle card) {
    	assertEquals("-fx-text-fill: white", card.getStyle());
    }
    //@@author

    /**
     * Asserts the size of the todo list is equal to the given number.
     */
    protected void assertTodoListSize(int size) {
        int numberOfPeople = taskListPanel.getNumberOfTasks(Task.TASK_COMPONENT_COUNT);
        assertEquals(size, numberOfPeople);
    }
    
    /**
     * Asserts the size of the deadline list is equal to the given number.
     */
    protected void assertDeadlineListSize(int size) {
        int numberOfPeople = taskListPanel.getNumberOfTasks(Task.DEADLINE_COMPONENT_COUNT);
        assertEquals(size, numberOfPeople);
    }
    
    /**
     * Asserts the size of the event list is equal to the given number.
     */
    protected void assertEventListSize(int size) {
        int numberOfPeople = taskListPanel.getNumberOfTasks(Task.EVENT_COMPONENT_COUNT);
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
}
