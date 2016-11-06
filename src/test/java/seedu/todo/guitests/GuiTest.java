package seedu.todo.guitests;

import static org.junit.Assert.*;
import static seedu.todo.testutil.AssertUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.BaseEvent;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.guitests.guihandles.AliasViewHandle;
import seedu.todo.guitests.guihandles.ConfigViewHandle;
import seedu.todo.guitests.guihandles.ConsoleHandle;
import seedu.todo.guitests.guihandles.HelpViewHandle;
import seedu.todo.guitests.guihandles.MainGuiHandle;
import seedu.todo.guitests.guihandles.TagListHandle;
import seedu.todo.guitests.guihandles.TaskListDateItemHandle;
import seedu.todo.guitests.guihandles.TaskListEventItemHandle;
import seedu.todo.guitests.guihandles.TaskListHandle;
import seedu.todo.guitests.guihandles.TaskListTaskItemHandle;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

// @@author A0139812A
public abstract class GuiTest {

    // The TestName Rule makes the current test name available inside test methods.
    @Rule
    public TestName name = new TestName();
    
    TestApp testApp;
    
    // Handles to GUI elements present at the start up are created in advance for easy access from child classes.
    protected MainGuiHandle mainGui;
    protected ConsoleHandle console;
    protected TaskListHandle taskList;
    protected TagListHandle tagList;
    protected AliasViewHandle aliasView;
    protected ConfigViewHandle configView;
    protected HelpViewHandle helpView;
    
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
            console = mainGui.getConsole();
            taskList = mainGui.getTaskList();
            tagList = mainGui.getTagList();
            aliasView = mainGui.getAliasView();
            configView = mainGui.getConfigView();
            helpView = mainGui.getHelpView();
            // TODO: create handles for other components
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
    protected TodoListDB getInitialData() {
        TodoListDB db = TodoListDB.getInstance();
        return db;
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

    public void raise(BaseEvent e) {
        //JUnit doesn't run its test cases on the UI thread. Platform.runLater is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }
    
    /* ========= COMMON TEST METHODS ============= */
    
    protected void assertInvalidCommand(String command) {
        assertEquals(command, console.getConsoleInputText());
        assertEquals("Invalid command!", console.getConsoleTextArea());
    }
    
    protected void assertValidCommand(String command) {
        assertEquals("", console.getConsoleInputText());
        assertNotEquals("Invalid command!", console.getConsoleTextArea());
    }
    
    /**
     * Utility method for testing if task has been successfully added to the GUI.
     * This runs a command and checks if TaskList contains TaskListTaskItem that matches
     * the task that was just added.
     * 
     * Assumption: No two events can have the same name in this test.
     */
    protected void assertTaskVisible(Task taskToAdd) {
        // Get the task date.
        LocalDateTime taskDateTime = taskToAdd.getCalendarDateTime();
        if (taskDateTime == null) {
            taskDateTime = DateUtil.NO_DATETIME_VALUE;
        }
        LocalDate taskDate = taskDateTime.toLocalDate();
        
        // Check TaskList if it contains a TaskListDateItem with the date.
        TaskListDateItemHandle dateItem = taskList.getTaskListDateItem(taskDate);
        assertSameDate(taskDate, dateItem);
        
        // Check TaskListDateItem if it contains the TaskListTaskItem with the same data.
        TaskListTaskItemHandle taskItem = dateItem.getTaskListTaskItem(taskToAdd);
        assertNotNull(taskItem);
    }

    /**
     * Utility method for testing if event has been successfully added to the GUI.
     * This runs a command and checks if TaskList contains TaskListEventItem that matches
     * the task that was just added.
     * 
     * Assumption: No two events can have the same name in this test.
     * 
     * TODO: Check event dates if they match.
     */
    protected void assertEventVisible(Event eventToAdd) {
        // Get the event date.
        LocalDateTime eventStartDateTime = eventToAdd.getStartDate();
        if (eventStartDateTime == null) {
            eventStartDateTime = DateUtil.NO_DATETIME_VALUE;
        }
        LocalDate eventStartDate = eventStartDateTime.toLocalDate();
        
        // Check TaskList if it contains a TaskListDateItem with the date of the event start date.
        TaskListDateItemHandle dateItem = taskList.getTaskListDateItem(eventStartDate);
        assertSameDate(eventStartDate, dateItem);
        
        // Check TaskListDateItem if it contains the TaskListEventItem with the same data.
        TaskListEventItemHandle eventItem = dateItem.getTaskListEventItem(eventToAdd);
        assertNotNull(eventItem);
    }
    
    /**
     * Utility method for testing if task does not appear in the GUI after a command.
     * Assumption: No two events can have the same name in this test.
     */
    protected void assertTaskNotVisible(Task taskToAdd) {
        // Get the task date.
        LocalDateTime taskDateTime = taskToAdd.getCalendarDateTime();
        if (taskDateTime == null) {
            taskDateTime = DateUtil.NO_DATETIME_VALUE;
        }
        LocalDate taskDate = taskDateTime.toLocalDate();
        
        // Check TaskList if it contains a TaskListDateItem with the date.
        TaskListDateItemHandle dateItem = taskList.getTaskListDateItem(taskDate);
        
        // It's fine if there's no task item, because it's not visible
        if (dateItem == null) {
            return;
        }
        
        // If there's a date item, then we make sure that it isn't a task in the date item with the same name.
        TaskListTaskItemHandle taskItem = dateItem.getTaskListTaskItem(taskToAdd);
        assertNull(taskItem);
    }

    /**
     * Utility method for testing if event does not appear in the GUI after a command.
     * Assumption: No two events can have the same name in this test.
     */
    protected void assertEventNotVisible(Event eventToAdd) {
        // Get the event date.
        LocalDateTime eventStartDateTime = eventToAdd.getStartDate();
        if (eventStartDateTime == null) {
            eventStartDateTime = DateUtil.NO_DATETIME_VALUE;
        }
        LocalDate eventStartDate = eventStartDateTime.toLocalDate();
        
        // Gets the date item that might contain the event
        TaskListDateItemHandle dateItem = taskList.getTaskListDateItem(eventStartDate);
        
        // It's fine if there's no date item, because it's not visible.
        if (dateItem == null) {
            return;
        }
        
        // If there's a date item, then we make sure that there isn't an event in the date item with the same name.
        TaskListEventItemHandle eventItem = dateItem.getTaskListEventItem(eventToAdd);
        assertNull(eventItem);
    }
}
