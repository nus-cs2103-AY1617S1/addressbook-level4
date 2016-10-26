package guitests.guihandles;


import guitests.GuiRobot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import java.util.List;

//@@author A0147967J
/**
 * Provides a handle for the panel containing the task list.
 */
public class NavbarPanelHandle extends GuiHandle {
	
	private final String NAVBAR_TASKS = " Tasks";
    private final String NAVBAR_DEADLINES = " Deadlines";
    private final String NAVBAR_INCOMING_DEADLINES = " Incoming Deadlines";
    private final String NAVBAR_FLOATING_TASKS = " Floating Tasks";
    private final String NAVBAR_COMPLETED = " Completed";
    
    private final ObservableList<String> navbarElement = FXCollections.observableArrayList(NAVBAR_TASKS, NAVBAR_DEADLINES, NAVBAR_FLOATING_TASKS
			  																					 ,NAVBAR_INCOMING_DEADLINES, NAVBAR_COMPLETED);

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPaneNav";

    private static final String TASK_LIST_VIEW_ID = "#navbarView";

    public NavbarPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    @SuppressWarnings("unchecked")
	public ListView<String> getListView() {
        return (ListView<String>) getNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Navigates the list view to display and select the task.
     */
    public void navigateTo(String nav) {
        int index = navbarElement.indexOf(nav);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(550);
        
    }
}
