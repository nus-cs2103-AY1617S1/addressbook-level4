package seedu.todo.guitests;

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
import seedu.todo.guitests.guihandles.MainGuiHandle;
import seedu.todo.models.TodoListDB;

public abstract class GuiTest {

    // The TestName Rule makes the current test name available inside test methods.
    @Rule
    public TestName name = new TestName();
    
    TestApp testApp;
    
    // Handles to GUI elements present at the start up are created in advance for easy access from child classes.
    protected MainGuiHandle mainGui;
    
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
}
