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
import seedu.address.model.TaskMaster;
import seedu.address.model.task.TaskOcurrence;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;
import seedu.address.ui.MyAgenda;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A GUI Test class for TaskList.
 */
public abstract class TaskMasterGuiTest {

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
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    protected NavbarPanelHandle navbar;
    protected BrowserPanelHandle browser;
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
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            navbar = mainGui.getNavbar();
            browser = mainGui.getBrowser();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(() -> {
			try {
				return getInitialData();
			} catch (TimeslotOverlapException e) {
				// TODO Auto-generated catch block
				assert false: "not possible";
				return null;
			}
		}, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     * @throws TimeslotOverlapException 
     */
    protected TaskMaster getInitialData() throws TimeslotOverlapException {
        TaskMaster ab = TestUtil.generateEmptyTaskList();
        TypicalTestTasks.loadTaskListWithSampleData(ab);
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
     * Asserts the floatingTask shown in the card is same as the given floatingTask
     */
    public void assertMatching(TaskOcurrence task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the size of the floatingTask list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfPeople = taskListPanel.getNumberOfPeople();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     * @param expected
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
    
    //@@author A0147967J
    /**
     * Asserts the expected task components are reflected in the agenda.
     * @param expected
     */
    protected void assertIsAgendaMatching(ArrayList<TaskOcurrence> expectedShown){
		//Get the updated agenda
		MyAgenda toBeChecked = browser.getMyAgenda();
		//Checks the number of items in the agenda
		assertEquals(expectedShown.size(), toBeChecked.appointments().size());
		//Checks one-to-one match
		for(TaskOcurrence t: expectedShown){
			assertTrue(browser.isContained(TestUtil.getAppointment(t)));
		}
	}

}
