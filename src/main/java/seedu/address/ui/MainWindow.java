package seedu.address.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.TaskConfig;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.FilterLabelChangeEvent.COMMANDTYPE;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String INACTIVE_CSS = "-fx-background-color: derive(#1d1d1d, 20%);";
    private static final String ACTIVE_CSS = "-fx-background-color: #9999; -fx-border-radius:  10 10 10 10; "
            + "-fx-background-radius:  10 10 10 10;";
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow_Task.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel taskListPanel;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private TaskConfig config;
    private UserPrefs userPrefs;
    private HelpPanel helpListPanel;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String addressBookName;
    
    private final KeyCombination controlPlusUp = new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN);
    private final KeyCombination controlPlusDown = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN);


    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private AnchorPane taskListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;
    
    @FXML
    private AnchorPane helpListPanelPlaceholder;
    
    @FXML
    private Label notComplete;
    
    @FXML
    private Label complete;
    
    @FXML
    private Label find;

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

    public static MainWindow load(Stage primaryStage, TaskConfig config, UserPrefs prefs, Logic logic) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getTaskManagerName(), config, prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, String addressBookName, TaskConfig config, UserPrefs prefs,
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
        setSceneHandlers(scene);
        primaryStage.setScene(scene);
        


    }

    //@@author A0138978E
    private void setSceneHandlers(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (controlPlusDown.match(event)) {
					taskListPanel.scrollDown();
				} else if (controlPlusUp.match(event)) {
					taskListPanel.scrollUp();
				}
			}
        	
        });
		
	}
    
    //@@author
    void fillInnerParts() {
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPlaceholder(), logic.getFilteredTaskList());
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
        helpListPanel = HelpPanel.load(primaryStage,getHelpListPlaceholder(), logic.getHelpList() );
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }
    public AnchorPane getHelpListPlaceholder() {
        return helpListPanelPlaceholder;
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
  //@@author A0139708W
    void hideHelp() {
        helpListPanelPlaceholder.getParent().toBack();
        helpListPanelPlaceholder.getParent().setOpacity(0);
    }
    
    void showHelp() {
        helpListPanelPlaceholder.getParent().toFront();
        helpListPanelPlaceholder.getParent().setOpacity(100);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }
    
    public void handleFilterLabelChange(COMMANDTYPE commandType) {
        if (commandType == COMMANDTYPE.List) {
            setListLabelActive();

        } else if (commandType == COMMANDTYPE.Find) {
            setFindLabelActive();
            
        } else if (commandType == COMMANDTYPE.ListComplete) {
            setCompleteLabelActive();
        }
    }

    private void setCompleteLabelActive() {
        complete.setStyle(ACTIVE_CSS);
        find.setStyle(INACTIVE_CSS);
        notComplete.setStyle(INACTIVE_CSS);
        
    }

    private void setFindLabelActive() {
        find.setStyle(ACTIVE_CSS);
        notComplete.setStyle(INACTIVE_CSS);
        complete.setStyle(INACTIVE_CSS);
        
        
    }

    private void setListLabelActive() {
        notComplete.setStyle(ACTIVE_CSS);
        find.setStyle(INACTIVE_CSS);
        complete.setStyle(INACTIVE_CSS);
        
    }

    @FXML
    public void handleHelp() {
        showHelp();
    }
    
    @FXML
    public void handleAliasList() {
        AliasWindow aliasWindow = AliasWindow.load(primaryStage, logic);
        aliasWindow.show();
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

    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }


}
