package seedu.taskscheduler.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.taskscheduler.commons.core.Config;
import seedu.taskscheduler.commons.core.GuiSettings;
import seedu.taskscheduler.commons.events.ui.ExitAppRequestEvent;
import seedu.taskscheduler.logic.Logic;
import seedu.taskscheduler.model.UserPrefs;
import seedu.taskscheduler.model.task.ReadOnlyTask;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/icon.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TagListPanel tagListPanel;
    private TaskListPanel taskListPanel;
    private PriorityListPanel priorityListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;
    private StringProperty completedCounter;
    private StringProperty pendingCounter;
    private StringProperty overdueCounter;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem tagsPaneMenuItem;
    
    @FXML
    private MenuItem priorityPaneMenuItem;
    
    @FXML
    private MenuItem taskPaneMenuItem;
    
    @FXML
    private MenuItem commandBoxPaneMenuItem;

    @FXML
    private Label lblCompleted;
    
    @FXML
    private Label lblPending;
    
    @FXML
    private Label lblOverdue;
    
    @FXML
    private AnchorPane priorityListPanelPlaceholder;
    
    @FXML
    private AnchorPane tagListPanelPlaceholder;
    
    @FXML
    private AnchorPane taskListPanelPlaceholder;
    
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
        mainWindow.configure(config.getAppTitle(), config.getTaskSchedulerName(), config, prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, String taskSchedulerName, Config config, UserPrefs prefs,
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
    }

    //@@author A0148145E
    public void fillInnerParts() {
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPlaceholder(), logic.getFilteredTaskList());
        tagListPanel = TagListPanel.load(primaryStage, getTagListPlaceholder(), logic.getUnmodifiableTagList());
        priorityListPanel = PriorityListPanel.load(primaryStage, getPriorityListPlaceholder(), 
                logic.getFilteredTaskList());
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getTaskSchedulerFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
    }
    
    public void fillLabels() {
        completedCounter = new SimpleStringProperty();
        pendingCounter = new SimpleStringProperty();
        overdueCounter = new SimpleStringProperty();
        lblCompleted.textProperty().bind(completedCounter);
        lblPending.textProperty().bind(pendingCounter);
        lblOverdue.textProperty().bind(overdueCounter);
    }
    //@@author 

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    public AnchorPane getPriorityListPlaceholder() {
        return priorityListPanelPlaceholder;
    }
    
    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }
    
    public AnchorPane getTagListPlaceholder() {
        return tagListPanelPlaceholder;
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
    
    @FXML
    public void handleFocusTagPane() {
        tagListPanel.requestFocus();
    }

    @FXML
    public void handleFocusPriorityPane() {
        priorityListPanel.requestFocus();
    }
    
    @FXML
    public void handleFocusTaskPane() {
        taskListPanel.requestFocus();
    }
    
    @FXML
    public void handleFocusCommandBox() {
        commandBox.requestFocus();
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

    public void releaseResources() {
    }
    
    //@@author A0148145E
    public void updateLabels(ObservableList<ReadOnlyTask> list){
        long pendingCount = list.stream().filter(b -> !b.isCompleted()).count();
        long completedCount = list.size() - pendingCount;
        long overdueCount = list.stream().filter(b -> b.isOverdue()).count();
        pendingCounter.set(String.valueOf(pendingCount));
        completedCounter.set(String.valueOf(completedCount));
        overdueCounter.set(String.valueOf(overdueCount));
    }

}
