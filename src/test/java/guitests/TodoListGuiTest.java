package guitests;

import guitests.guihandles.CommandFeedbackViewHandle;
import guitests.guihandles.CommandInputViewHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.TodoListViewHandle;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;
import seedu.todo.TestApp;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.model.TodoList;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.TaskFactory;
import seedu.todo.testutil.TestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

/**
 * A GUI Test class for Uncle Jim's Discount To-do List.
 */
public abstract class TodoListGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule public TestName name = new TestName();

    //Stores the list of immutable task first started with the application.
    protected List<ImmutableTask> initialTaskData;

    //Stores a copy of immutable task lists that are currently on display.
    protected List<ImmutableTask> previousTasksFromView;

    private TestApp testApp;

    /*
     * Handles to GUI elements present at the start up are created in advance
     * for easy access from child classes.
     */
    //TODO: Attach new Handles here!
    protected MainGuiHandle mainGui;
    protected TodoListViewHandle todoListView;
    protected CommandInputViewHandle commandInputView;
    protected CommandFeedbackViewHandle commandFeedbackView;

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
        //TODO: Set up new handles them!
        FxToolkit.setupStage((stage) -> {
            this.stage = stage;
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            todoListView = mainGui.getTodoListView();
            commandInputView = mainGui.getCommandInputView();
            commandFeedbackView = mainGui.getCommandFeedbackView();
        });
        EventsCenter.clearSubscribers();
        /*
         * A new instance of the to-do list data structure ImmutableTodoList
         * that will be injected into the TestApp, which inherits from MainApp
         */
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TodoList getInitialData() {
        TodoList todoList = TestUtil.generateEmptyTodoList(getDataFileLocation());
        initialTaskData = TaskFactory.list();
        TestUtil.loadTodoListWithData(todoList, initialTaskData);
        return todoList;
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
     * Executes the command via the {@link seedu.todo.ui.view.CommandInputView}
     */
    protected void runCommand(String commandText) {
        commandInputView.runCommand(commandText);
    }

    /**
     * Asserts the message shown in the {@link seedu.todo.ui.view.CommandFeedbackView}
     * is same as the given {@code expected} string.
     */
    protected void assertFeedbackMessage(String expected) {
        assertEquals(expected, commandFeedbackView.getText());
    }

    /**
     * Copies the list of ImmutableTask stored inside the {@link seedu.todo.ui.view.TodoListView}
     * into {@link #previousTasksFromView}, for history taking.
     */
    protected void updatePreviousTaskListFromView() {
        previousTasksFromView = new ArrayList<>(todoListView.getImmutableTaskList());
    }
}
