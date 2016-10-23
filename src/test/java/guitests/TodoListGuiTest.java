package guitests;

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

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * A GUI Test class for Uncle Jim's Discount To-do List.
 */
public abstract class TodoListGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule public TestName name = new TestName();
    protected List<ImmutableTask> initialTaskData;
    private TestApp testApp;

    /*
     * Handles to GUI elements present at the start up are created in advance
     * for easy access from child classes.
     */
    //TODO: Attach new Handles here!
    protected MainGuiHandle mainGui;
    protected TodoListViewHandle todoListView;
    protected CommandInputViewHandle commandInputView;

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
        //TODO: You might need a default set of tasks (instead of randomly generated ones)
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

    //TODO: All these below needs to be removed.
//    /**
//     * Asserts the person shown in the card is same as the given person
//     */
//    public void assertMatching(ReadOnlyPerson person, PersonCardHandle card) {
//        assertTrue(TestUtil.compareCardAndPerson(card, person));
//    }
//
//    /**
//     * Asserts the size of the person list is equal to the given number.
//     */
//    protected void assertListSize(int size) {
//        int numberOfPeople = personListPanel.getNumberOfPeople();
//        assertEquals(size, numberOfPeople);
//    }
//
//    /**
//     * Asserts the message shown in the Result Display area is same as the given string.
//     */
//    protected void assertResultMessage(String expected) {
//        assertEquals(expected, resultDisplay.getText());
//    }
}
