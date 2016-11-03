package tars.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tars.commons.core.Config;
import tars.commons.core.GuiSettings;
import tars.logic.Logic;
import tars.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where
 * other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/tars_icon_32.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;
    public static final int OVERVIEW_PANEL_TAB_PANE_INDEX = 0;
    public static final int RSV_TASK_LIST_PANEL_TAB_PANE_INDEX = 1;
    public static final int HELP_PANEL_TAB_PANE_INDEX = 2;

    private Logic logic;
    private MainWindowEventsHandler mainWindowEventsHandler;

    // Independent Ui parts residing in this Ui container
    private Header header;
    private TaskListPanel taskListPanel;
    private RsvTaskListPanel rsvTaskListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private HelpPanel helpPanel;
    private ThisWeekPanel thisWeekPanel;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    @FXML
    private AnchorPane commandBoxPlaceholder;
    @FXML
    private AnchorPane headerPlaceholder;
    @FXML
    private AnchorPane taskListPanelPlaceholder;
    @FXML
    private AnchorPane rsvTaskListPanelPlaceholder;
    @FXML
    private AnchorPane resultDisplayPlaceholder;
    @FXML
    private AnchorPane statusbarPlaceholder;
    @FXML
    private AnchorPane thisWeekPanelPlaceholder;
    @FXML
    private AnchorPane helpPanelPlaceholder;

    @FXML
    private Label taskListLabel;

    @FXML
    private TabPane tabPane;
    @FXML
    private AnchorPane thisWeekTabAnchorPane;
    @FXML
    private AnchorPane rsvTabAnchorPane;
    @FXML
    private AnchorPane helpTabAnchorPane;

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public static MainWindow load(Stage primaryStage, Config config,
            UserPrefs prefs, Logic logic) {

        MainWindow mainWindow =
                UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config,
                prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, Config config,
            UserPrefs prefs, Logic logic) {


        // Set dependencies
        this.logic = logic;
        this.config = config;
        this.userPrefs = prefs;

        // Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(userPrefs);

        // Configure event handling
        this.mainWindowEventsHandler = new MainWindowEventsHandler(primaryStage,
                rootLayout, tabPane);

        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);


    }

    protected void fillInnerParts() {
        header = Header.load(primaryStage, headerPlaceholder);
        taskListPanel = TaskListPanel.load(primaryStage,
                getTaskListPlaceholder(), logic.getFilteredTaskList());
        rsvTaskListPanel = RsvTaskListPanel.load(primaryStage,
                getRsvTaskListPlaceholder(), logic.getFilteredRsvTaskList());
        resultDisplay =
                ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage,
                getStatusbarPlaceholder(), config.getTarsFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(),
                resultDisplay, logic);
        helpPanel = HelpPanel.load(primaryStage, getHelpPanelPlaceholder());
        thisWeekPanel = ThisWeekPanel.load(primaryStage,
                getThisWeekPanelPlaceholder(), logic.getTaskList());
    }

    /**
     * A method to reload the status bar footer
     * 
     * @@author A0124333U
     */
    public void reloadStatusBarFooter(String newTarsFilePath) {
        statusBarFooter = StatusBarFooter.load(primaryStage,
                getStatusbarPlaceholder(), newTarsFilePath);
    }

    // @@author

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    private AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }

    private AnchorPane getRsvTaskListPlaceholder() {
        return rsvTaskListPanelPlaceholder;
    }

    private AnchorPane getHelpPanelPlaceholder() {
        return helpPanelPlaceholder;
    }

    private AnchorPane getThisWeekPanelPlaceholder() {
        return thisWeekPanelPlaceholder;
    }
    
    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }
    
    public HelpPanel getHelpPanel() {
        return this.helpPanel;
    }

    public MainWindowEventsHandler getEventsHandler() {
        return this.mainWindowEventsHandler;
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
            primaryStage
                    .setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage
                    .setY(prefs.getGuiSettings().getWindowCoordinates().getY());
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
        return new GuiSettings(primaryStage.getWidth(),
                primaryStage.getHeight(), (int) primaryStage.getX(),
                (int) primaryStage.getY());
    }


    public void show() {
        primaryStage.show();
    }

}
