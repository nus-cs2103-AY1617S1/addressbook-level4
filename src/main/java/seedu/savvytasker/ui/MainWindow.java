//@@author A0138431L

package seedu.savvytasker.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import seedu.savvytasker.commons.core.Config;
import seedu.savvytasker.commons.core.GuiSettings;
import seedu.savvytasker.commons.events.ui.ExitAppRequestEvent;
import seedu.savvytasker.logic.Logic;
import seedu.savvytasker.model.UserPrefs;
import seedu.savvytasker.model.task.ReadOnlyTask;

/**
 * The Main Window. Provides the basic application layout containing
 * a sorting and filtered list that display the result of the user command 
 * on the left and a week's view of the task
 * 
 * The week's view contains 4 lists, namely the floating list, overdue list, 
 * days of the week list and upcoming list
 *  
 * Floating list contains task without start and end dateTime
 * Overdue list contains task with end date before current date
 * Days of the week list contains task that falls on the respective day of the selected week
 * Upcoming list contains task with start date after the last day of selected week
 * 
 * @author A0138431L
 * 
 */
public class MainWindow extends UiPart {

	private static final String ICON = "/images/address_book_32.png";
	private static final String FXML = "MainWindow.fxml";
	public static final int MIN_HEIGHT = 700;
	public static final int MIN_WIDTH = 1150;

	private Logic logic;
	Date date = new Date();
	private static int DAYS_OF_WEEK = 7;	

	// Independent Ui parts residing in this Ui container
	//private BrowserPanel browserPanel;
	private TaskListPanel taskListPanel;
    private AliasSymbolListPanel aliasSymbolListPanel;
	private ResultDisplay resultDisplay;
	private StatusBarFooter statusBarFooter;
	private CommandBox commandBox;
	private Config config;
	private UserPrefs userPrefs;
	@FXML
	private FloatingPanel floatingPanel;
	@FXML
	private OverduePanel overduePanel;
	@FXML
	private DailyPanel dailyPanel;
	@FXML
	private UpcomingPanel upcomingPanel;

	// Handles to elements of this Ui container
	private VBox rootLayout;
	private Scene scene;

	private String addressBookName;

	@FXML
	private AnchorPane browserPlaceholder;

	@FXML
	private AnchorPane commandBoxPlaceholder;

	@FXML
	private MenuItem helpMenuItem;

    @FXML
    private AnchorPane taskListPanelPlaceholder;
    
    @FXML
    private AnchorPane aliasSymbolListPanelPlaceholder;

	@FXML
	private AnchorPane resultDisplayPlaceholder;

	@FXML
	private AnchorPane statusbarPlaceholder;

    @FXML
    private VBox listPanel;

	@FXML 
	private AnchorPane floatingPanelPlaceholder;

	@FXML 
	private AnchorPane overduePanelPlaceholder;

	@FXML 
	private AnchorPane day1PanelPlaceholder;
	@FXML 
	private AnchorPane day2PanelPlaceholder;
	@FXML 
	private AnchorPane day3PanelPlaceholder;
	@FXML 
	private AnchorPane day4PanelPlaceholder;
	@FXML 
	private AnchorPane day5PanelPlaceholder;
	@FXML 
	private AnchorPane day6PanelPlaceholder;
	@FXML 
	private AnchorPane day7PanelPlaceholder;

	@FXML 
	private AnchorPane upcomingPanelPlaceholder;

	public MainWindow() {
		super();
	}

	@Override
	public void setNode(Node node) {
		rootLayout = (VBox) node;
	}

	@Override
	public String getFxmlPath() {
		return FXML;
	}

	public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {

		MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
		mainWindow.configure(config.getAppTitle(), config.getSavvyTaskerName(), config, prefs, logic);
		return mainWindow;
	}

	private void configure(String appTitle, String addressBookName, Config config, UserPrefs prefs,
			Logic logic) {

		//Set dependencies
		this.logic = logic;
		this.addressBookName = addressBookName;
		this.config = config;
		this.userPrefs = prefs;

		//Configure the UI
		setTitle(appTitle);
		setIcon(ICON);
		setWindowMinSize();
		setWindowDefaultSize(prefs);
		scene = new Scene(rootLayout);
		primaryStage.setScene(scene);

		setAccelerators();
	}

	private void setAccelerators() {
		helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
	}

	void fillInnerParts() {
		//browserPanel = BrowserPanel.load(browserPlaceholder);
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPlaceholder(), logic.getFilteredTaskList());
        aliasSymbolListPanel = AliasSymbolListPanel.load(primaryStage, getAliasSymbolPlaceholder(), logic.getAliasSymbolList());
        setDefaultView();

		resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
		statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getSavvyTaskerFilePath());
		commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
		commandBox.getCommandTextField().requestFocus();
		floatingPanel = FloatingPanel.load(primaryStage, getFloatingPanelPlaceholder(), logic.getFilteredFloatingTasks());
		overduePanel = OverduePanel.load(primaryStage, getOverduePanelPlaceholder(), logic.getFilteredOverdueTasks());
		for(int i = 0; i < DAYS_OF_WEEK; i++) {
			Date onDate = new Date();
			onDate.setTime(date.getTime());
			onDate = addDay(i, onDate);
			dailyPanel = DailyPanel.load(primaryStage, getDailyPanelPlaceholder(i), logic.getFilteredDailyTasks(i, onDate), i, onDate);
		}
		upcomingPanel = UpcomingPanel.load(primaryStage, getUpcomingPanelPlaceholder(), logic.getFilteredUpcomingTasks(date));
	}
    
    /**
     * Removes all the children in the taskPanel VBox
     * Shows the default list, which is the list of tasks
     */
    private void setDefaultView() {
        getListPanel().getChildren().remove(getAliasSymbolPlaceholder());
        getListPanel().getChildren().remove(getTaskListPlaceholder());
        getListPanel().getChildren().add(getTaskListPlaceholder());
    }
    
    /**
     * Set to true to show the list of tasks. Set to false to show the list of alias
     * @param isShown
     */
    public void showTaskList(boolean isShown) {
        getListPanel().getChildren().remove(getAliasSymbolPlaceholder());
        getListPanel().getChildren().remove(getTaskListPlaceholder());
        if (isShown) {
            getListPanel().getChildren().add(getTaskListPlaceholder());
        } else {
            getListPanel().getChildren().add(getAliasSymbolPlaceholder());
        }
    }
    
    private VBox getListPanel() {
        return listPanel;
    }

	private AnchorPane getCommandBoxPlaceholder() {
		return commandBoxPlaceholder;
	}

	private AnchorPane getStatusbarPlaceholder() {
		return statusbarPlaceholder;
	}

	private AnchorPane getResultDisplayPlaceholder() {
		return resultDisplayPlaceholder;
	}

    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }
    
    public AnchorPane getAliasSymbolPlaceholder() {
        return aliasSymbolListPanelPlaceholder;
    }

	private AnchorPane getFloatingPanelPlaceholder() {
		return floatingPanelPlaceholder;
	}

	private AnchorPane getOverduePanelPlaceholder() {
		return overduePanelPlaceholder;
	}

	private AnchorPane getDailyPanelPlaceholder(int index) {

		switch(index) {

		case 0: 
			
			return day1PanelPlaceholder;

		case 1: 

			return day2PanelPlaceholder;

		case 2: 

			return day3PanelPlaceholder;

		case 3: 

			return day4PanelPlaceholder;

		case 4: 

			return day5PanelPlaceholder;

		case 5: 

			return day6PanelPlaceholder;

		case 6: 

			return day7PanelPlaceholder;

		default:

			return day1PanelPlaceholder;
		}

    }

    private AnchorPane getUpcomingPanelPlaceholder() {
        return upcomingPanelPlaceholder;
    }

    private Date addDay(int i, Date date) {
			
        //convert date object to calendar object and add 1 days
        Calendar calendarExpectedDate = Calendar.getInstance();
        calendarExpectedDate.setTime(date);
        
        calendarExpectedDate.add(Calendar.DATE, i);
			
        //convert calendar object back to date object
        date = calendarExpectedDate.getTime();
			
        return date;
    }
		
    public void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

	/**
	 * Sets the default size based on user preferences.
	 */
	protected void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
    * Returns the current size and the position of the main Window.
    */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = HelpWindow.load(primaryStage);
        helpWindow.show();
    }
		
	public void hideHelp() {
        HelpWindow helpWindow = HelpWindow.load(primaryStage);
        helpWindow.hide();
    }

	public void show() {
		primaryStage.show();
    }

    /**
    * Closes the application.
    */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
    
    public AliasSymbolListPanel getAliasSymbolListPanel() {
        return this.aliasSymbolListPanel;
    }

    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }


	public void loadPersonPage(ReadOnlyTask task) {
		//browserPanel.loadPersonPage(task);
	}

	public void releaseResources() {
        //browserPanel.freeResources();
    }
		
}