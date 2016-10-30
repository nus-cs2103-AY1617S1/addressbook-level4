package guitests;

import guitests.guihandles.*;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.model.TaskManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;

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

    protected TypicalTestTasks td = new TypicalTestTasks();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle taskListPanel;
    protected TaskListPanelHandle todayTaskListTabPanel;
    protected TaskListPanelHandle tomorrowTaskListTabPanel;
    protected TaskListPanelHandle in7DaysTaskListTabPanel;
    protected TaskListPanelHandle in30DaysTaskListTabPanel;
    protected TaskListPanelHandle somedayTaskListTabPanel;
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
            taskListPanel = mainGui.getTaskListPanel();
            //@@author A0142184L
            todayTaskListTabPanel = mainGui.getTodayTaskListTabPanel();
            tomorrowTaskListTabPanel = mainGui.getTomorrowTaskListTabPanel();
            in7DaysTaskListTabPanel = mainGui.getIn7DaysTaskListTabPanel();
            in30DaysTaskListTabPanel = mainGui.getIn30DaysTaskListTabPanel();
            somedayTaskListTabPanel = mainGui.getSomedayTaskListTabPanel();
            //@@author
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
        TypicalTestTasks.loadTaskManagerWithSampleData(ab);
        return ab;
    }

    /**
     * Override this in child classes to set the data file location.
     * @return
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }
    
    //@@author A0142184L
    /**
     * Asserts the someday task shown in the card is same as the given someday task
     */
    public void assertSomedayTaskMatching(ReadOnlyTask task, SomedayTaskCardHandle card) {
        assertTrue(TestUtil.compareSomedayCardAndTask(card, task));
    }
    
    /**
     * Asserts the deadline task shown in the card is same as the given deadline task
     */
    public void assertDeadlineTaskMatching(ReadOnlyTask task, DeadlineTaskCardHandle card) {
        assertTrue(TestUtil.compareDeadlineCardAndTask(card, task));
    }
    
    /**
     * Asserts the event task shown in the card is same as the given event task
     */
    public void assertEventTaskMatching(ReadOnlyTask task, EventTaskCardHandle card) {
        assertTrue(TestUtil.compareEventCardAndTask(card, task));
    }
    //@@author
    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfTasks = taskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    //@@author A0142184L
    /**
     * Asserts the size of the today task list in the tab pane is equal to the given number.
     */
    protected void assertTodayListSize(int size) {
        int numberOfTasks = todayTaskListTabPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    
    /**
     * Asserts the size of the tomorrow task list in the tab pane is equal to the given number.
     */
    protected void assertTomorrowListSize(int size) {
        int numberOfTasks = tomorrowTaskListTabPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    
    /**
     * Asserts the size of the in-7-days task list in the tab pane is equal to the given number.
     */
    protected void assertIn7DaysListSize(int size) {
        int numberOfTasks = in7DaysTaskListTabPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    
    /**
     * Asserts the size of the in-30-days task list in the tab pane is equal to the given number.
     */
    protected void assertIn30DaysListSize(int size) {
        int numberOfTasks = in30DaysTaskListTabPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    
    /**
     * Asserts the size of the someday task list in the tab pane is equal to the given number.
     */
    protected void assertSomedayListSize(int size) {
        int numberOfTasks = somedayTaskListTabPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    //@@author

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     * @param expected
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
}
