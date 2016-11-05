package seedu.address.ui;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

	//@@author A0142184L
    private static final String ICON = "/images/amethyst_task_manager.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 750;
    public static final int MIN_WIDTH = 800;
    //@@author 
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ListPanel taskListLeftPanel;
    //@@author A0142184L
    private ListPanel todayTaskListTabPanel;
    private ListPanel tomorrowTaskListTabPanel;
    private ListPanel in7DaysTaskListTabPanel;
    private ListPanel in30DaysTaskListTabPanel;
    private ListPanel somedayTaskListTabPanel;
    //@@author 
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String taskManagerName;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;
    
    @FXML
    private AnchorPane taskListLeftPanelPlaceholder;

    //@@author A0142184L
    @FXML
    private TabPane tabPane;
    
    @FXML 
	private AnchorPane todayTaskListTabPanelPlaceholder;
    
    @FXML
	private AnchorPane tomorrowTaskListTabPanelPlaceholder;
    
    @FXML
	private AnchorPane in7DaysTaskListTabPanelPlaceholder;
    
    @FXML
	private AnchorPane in30DaysTaskListTabPanelPlaceholder;
    
    @FXML
	private AnchorPane somedayTaskListTabPanelPlaceholder;

   //@@author 
    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;


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
        mainWindow.configure(config.getAppTitle(), config.getTaskManagerName(), config, prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, String taskManagerName, Config config, UserPrefs prefs,
                           Logic logic) {

        //Set dependencies
        this.logic = logic;
        this.taskManagerName = taskManagerName;
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
        //@@author A0142184L
        todayTaskListTabPanel = ListPanel.loadTaskList(primaryStage, getTodayTaskListTabPanelPlaceholder(), logic.getTodayTaskList());
        tomorrowTaskListTabPanel = ListPanel.loadTaskList(primaryStage, getTomorrowTaskListTabPanelPlaceholder(), logic.getTomorrowTaskList());
        in7DaysTaskListTabPanel = ListPanel.loadTaskList(primaryStage, getIn7DaysTaskListTabPanelPlaceholder(), logic.getIn7DaysTaskList());
        in30DaysTaskListTabPanel = ListPanel.loadTaskList(primaryStage, getIn30DaysTaskListTabPanelPlaceholder(), logic.getIn30DaysTaskList());   
        somedayTaskListTabPanel = ListPanel.loadTaskList(primaryStage, getSomedayTaskListTabPanelPlaceholder(), logic.getSomedayTaskList());
        taskListLeftPanel = ListPanel.loadTaskList(primaryStage, getTaskListLeftPlaceholder(), logic.getFilteredTaskList());
        //@@author
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getTaskManagerFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);    
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

    public AnchorPane getTaskListLeftPlaceholder() {
        return taskListLeftPanelPlaceholder;
    }
    
    //@@author A0142184L
	private AnchorPane getTodayTaskListTabPanelPlaceholder() {
		return todayTaskListTabPanelPlaceholder;
	}

	private AnchorPane getTomorrowTaskListTabPanelPlaceholder() {
		return tomorrowTaskListTabPanelPlaceholder;
	}
	
	private AnchorPane getIn7DaysTaskListTabPanelPlaceholder() {
		return in7DaysTaskListTabPanelPlaceholder;
	}

	private AnchorPane getIn30DaysTaskListTabPanelPlaceholder() {
		return in30DaysTaskListTabPanelPlaceholder;
	}
    
    private AnchorPane getSomedayTaskListTabPanelPlaceholder() {
    	return somedayTaskListTabPanelPlaceholder;
	}
    
    //@@author 
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
    
    //@@author A0142184L
    public TabPane getTabPane() {
    	return tabPane;
    }
    
    //@@author
    public ListPanel getTaskListPanel() {
        return this.taskListLeftPanel;
    }

}
