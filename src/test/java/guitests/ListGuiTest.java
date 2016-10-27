package guitests;

import guitests.guihandles.*;
import javafx.stage.Stage;
import seedu.todoList.TestApp;
import seedu.todoList.commons.core.EventsCenter;
import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.Deadline;
import seedu.todoList.model.task.Event;
import seedu.todoList.model.task.Todo;
import seedu.todoList.testutil.TestUtil;
import seedu.todoList.testutil.TypicalTestDeadline;
import seedu.todoList.testutil.TypicalTestEvent;
import seedu.todoList.testutil.TypicalTestTask;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A GUI Test class for List.
 */
//@@author A0132157M reused
public abstract class ListGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTask td = new TypicalTestTask();
    protected TypicalTestEvent ed = new TypicalTestEvent();
    protected TypicalTestDeadline dd = new TypicalTestDeadline();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle taskListPanel;
    protected EventListPanelHandle eventListPanel;
    protected DeadlineListPanelHandle deadlineListPanel;
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
            eventListPanel = mainGui.getEventListPanel();
            deadlineListPanel = mainGui.getDeadlineListPanel();
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
  //@@author A0132157M reused
    protected TaskList getInitialData() {
        TaskList ab = TestUtil.generateEmptyTodoList();
        //TaskList cd = TestUtil.generateEmptyEventList();
        //TaskList ef = TestUtil.generateEmptyDeadlineList();
        TypicalTestTask.loadTodoListWithSampleData(ab);
        //TypicalTestEvent.loadEventListWithSampleData(cd);
        //TypicalTestDeadline.loadDeadlineListWithSampleData(ef);
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

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(Todo task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }
    //@@author A0132157M reused
    public void assertEventMatching(Event event, EventCardHandle card) {
        assertTrue(TestUtil.compareCardAndEvent(card, event));
    }
    //@@author A0132157M reused
    public void assertDeadlineMatching(Deadline event, DeadlineCardHandle card) {
        assertTrue(TestUtil.compareCardAndDeadline(card, event));
    }
    

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfTask = taskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTask);
    }
    //@@author A0132157M reused
    protected void assertEventListSize(int size) {
        int numberOfTask = eventListPanel.getNumberOfEvents();
        assertEquals(size, numberOfTask);
    }
    //@@author A0132157M reused
    protected void assertDeadlineListSize(int size) {
        int numberOfTask = deadlineListPanel.getNumberOfDeadlines();
        assertEquals(size, numberOfTask);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     * @param expected
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
}
