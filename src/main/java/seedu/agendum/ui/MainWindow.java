package seedu.agendum.ui;

import com.sun.javafx.stage.StageHelper;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.agendum.commons.core.Config;
import seedu.agendum.commons.core.GuiSettings;
import seedu.agendum.commons.events.ui.ExitAppRequestEvent;
import seedu.agendum.logic.Logic;
import seedu.agendum.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/agendum_icon.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    private Logic logic;
    
    // Independent Ui parts residing in this Ui container
    private AllTasksPanel allTasksPanel;
    private CompletedTasksPanel completedTasksPanel;
    private OtherTasksPanel otherTasksPanel;
    private ResultPopUp resultPopUp;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;
    private Stage helpWindowStage = null;

    @FXML
    private AnchorPane browserPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane allTasksPlaceHolder;
    
    @FXML
    private AnchorPane completedTasksPlaceHolder;
    
    @FXML
    private AnchorPane otherTasksPlaceHolder;
    
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
        mainWindow.configure(config.getAppTitle(), config.getToDoListName(), config, prefs, logic);
        return mainWindow;
    }

    //@@author A0148031R
    private void configure(String appTitle, String toDoListName, Config config, UserPrefs prefs,
                           Logic logic) {

        //Set dependencies
        this.logic = logic;
        this.config = config;
        this.userPrefs = prefs;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        
        primaryStage.setScene(scene);
        
        primaryStage.setOnCloseRequest(e -> Platform.exit());

        setAccelerators();
        configureHelpWindowToggle();
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F5"));
    }
    
    private void configureHelpWindowToggle() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            KeyCombination toggleHelpWindow = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
            @Override
            public void handle(KeyEvent evt) {
                if(toggleHelpWindow.match(evt) && helpWindowStage != null) {
                    if(helpWindowStage.isFocused()) {
                        primaryStage.requestFocus();
                    } else {
                        helpWindowStage.requestFocus();
                    }
                }
            }
        });
    }

  //@@author A0148031R
    void fillInnerParts() {
        allTasksPanel = AllTasksPanel.load(primaryStage, getAllTasksPlaceHolder(), logic.getFilteredTaskList());
        completedTasksPanel = CompletedTasksPanel.load(primaryStage, getCompletedTasksPlaceHolder(), logic.getFilteredTaskList());
        otherTasksPanel = OtherTasksPanel.load(primaryStage, getOtherTasksPlaceHolder(), logic.getFilteredTaskList());
        resultPopUp = ResultPopUp.load(primaryStage);
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getToDoListFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultPopUp, logic);
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }
    
    public AnchorPane getAllTasksPlaceHolder() {
        return allTasksPlaceHolder;
    }
    
    public AnchorPane getCompletedTasksPlaceHolder() {
        return completedTasksPlaceHolder;
    }
    
    public AnchorPane getOtherTasksPlaceHolder() {
        return otherTasksPlaceHolder;
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
        if(helpWindow != null) {
            this.helpWindowStage = helpWindow.getStage();
            helpWindow.show();
        }
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

    public AllTasksPanel getAllTasksPanel() {
        return this.allTasksPanel;
    }
    
    public CompletedTasksPanel getCompletedTasksPanel() {
        return this.completedTasksPanel;
    }
}
