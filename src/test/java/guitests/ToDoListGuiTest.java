package guitests;

import guitests.guihandles.*;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;
import seedu.agendum.TestApp;
import seedu.agendum.commons.core.EventsCenter;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.testutil.TestTask;
import seedu.agendum.testutil.TestUtil;
import seedu.agendum.testutil.TypicalTestTasks;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A GUI Test class for ToDoList.
 */
public abstract class ToDoListGuiTest {

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
    protected DoItSoonPanelHandle doItSoonPanel;
    protected DoItAnytimePanelHandle doItAnytimePanel;
    protected CompletedTasksPanelHandle completedTasksPanel;
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
            doItSoonPanel = mainGui.getDoItSoonPanel();
            doItAnytimePanel = mainGui.getDoItAnytimePanel();
            completedTasksPanel = mainGui.getCompletedTasksPanel();
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
    protected ToDoList getInitialData() {
        ToDoList ab = TestUtil.generateEmptyToDoList();
        TypicalTestTasks.loadToDoListWithSampleData(ab);
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
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfTasks = doItSoonPanel.getNumberOfTasks()
                          + doItAnytimePanel.getNumberOfTasks()
                          + completedTasksPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
    
    /**
     * expectedList is a sorted list of all tasks.
     * Asserts the task shown in each panel will match
     */
    protected void assertAllPanelsMatch(TestTask[] expectedList) {
        TestTask[] expectedDoItSoonTasks = TestUtil.getDoItSoonTasks(expectedList);
        TestTask[] expectedDoItAnytimeTasks = TestUtil.getDoItAnytimeTasks(expectedList);
        TestTask[] expectedDoneTasks = TestUtil.getDoneTasks(expectedList);
        assertTrue(doItSoonPanel.isListMatching(expectedDoItSoonTasks));
        assertTrue(doItAnytimePanel.isListMatching(expectedDoItAnytimeTasks));
        assertTrue(completedTasksPanel.isListMatching(expectedDoneTasks));
    }
}
