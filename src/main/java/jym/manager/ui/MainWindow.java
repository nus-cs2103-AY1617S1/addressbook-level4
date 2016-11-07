package jym.manager.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jym.manager.commons.core.Config;
import jym.manager.commons.core.GuiSettings;
import jym.manager.commons.events.ui.ExitAppRequestEvent;
import jym.manager.logic.Logic;
import jym.manager.model.UserPrefs;
import jym.manager.model.task.ReadOnlyTask;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
<<<<<<< HEAD:src/main/java/jym/manager/ui/MainWindow.java
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;
    
    public static final String TAB_TASK_COMPLETE = "Completed";
    public static final String TAB_TASK_INCOMPLETE = "Incomplete";
=======
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/ui/MainWindow.java

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private TaskListPanel taskListPanel;
    private CompleteTaskListPanel completeTaskListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;

    private String taskManagerName;

    @FXML
    private AnchorPane browserPlaceholder;
    
    @FXML
    private AnchorPane completeTaskListPanelPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane taskListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;
<<<<<<< HEAD:src/main/java/jym/manager/ui/MainWindow.java
    
    public MainWindow() {
        super();
    }
=======
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/ui/MainWindow.java

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

        // Set dependencies
        this.logic = logic;
        this.taskManagerName = taskManagerName;
        this.config = config;
        this.userPrefs = prefs;

        // Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        setAccelerators();
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts() {
    //    browserPanel = BrowserPanel.load(browserPlaceholder);
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPlaceholder(), logic.getFilteredIncompleteTaskList());
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
<<<<<<< HEAD:src/main/java/jym/manager/ui/MainWindow.java
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getTaskManagerFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
        completeTaskListPanel = CompleteTaskListPanel.load(primaryStage,  getCompleteTaskListPlaceholder(), logic.getFilteredCompleteTaskList());
=======
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(),
                                               config.getAddressBookFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), logic);
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/ui/MainWindow.java
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

<<<<<<< HEAD:src/main/java/jym/manager/ui/MainWindow.java
    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }
    
    public AnchorPane getCompleteTaskListPlaceholder() {
        return completeTaskListPanelPlaceholder;
=======
    private AnchorPane getPersonListPlaceholder() {
        return personListPanelPlaceholder;
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/ui/MainWindow.java
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
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
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = HelpWindow.load(primaryStage);
        helpWindow.show();
    }

    void show() {
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
    
    public CompleteTaskListPanel getCompleteTaskListPanel() {
        return this.completeTaskListPanel;
    }

<<<<<<< HEAD:src/main/java/jym/manager/ui/MainWindow.java
    public void loadTaskPage(ReadOnlyTask task) {
        browserPanel.loadTaskPage(task);
    }

    public void releaseResources() {
      //  browserPanel.freeResources();
=======
    void loadPersonPage(ReadOnlyPerson person) {
        browserPanel.loadPersonPage(person);
    }

    void releaseResources() {
        browserPanel.freeResources();
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/ui/MainWindow.java
    }

}
