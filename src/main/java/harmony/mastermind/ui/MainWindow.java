package harmony.mastermind.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.ocpsoft.prettytime.PrettyTime;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.core.Config;
import harmony.mastermind.commons.core.GuiSettings;
import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.commons.events.model.TaskManagerChangedEvent;
import harmony.mastermind.commons.events.ui.HighlightLastActionedRowRequestEvent;
import harmony.mastermind.commons.events.ui.IncorrectCommandAttemptedEvent;
import harmony.mastermind.commons.events.ui.NewResultAvailableEvent;
import harmony.mastermind.commons.events.ui.TabChangedEvent;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.commands.IncorrectCommand;
import harmony.mastermind.logic.commands.ListCommand;
import harmony.mastermind.logic.commands.UpcomingCommand;
import harmony.mastermind.model.UserPrefs;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.TaskListComparator;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";

    private static final short INDEX_HOME = 0;
    private static final short INDEX_TASKS = 1;
    private static final short INDEX_EVENTS = 2;
    private static final short INDEX_DEADLINES = 3;
    private static final short INDEX_ARCHIVES = 4;

    private static final String[] NAME_TABS = { "Home", "Tasks", "Events", "Deadlines", "Archives" };

    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 460;

    // @@author A0138862W
    // init only one parser for all parsing, save memory and computation time
    private static final PrettyTime prettyTime = new PrettyTime();
    // @@author

    private Logic logic;

    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String taskManagerName;

    private final Logger logger = LogsCenter.getLogger(MainWindow.class);

    private CommandResult mostRecentResult;
    private String currCommandText;

    ObservableList<Tab> tabLst;

    // UI elements
    /*
     * @FXML private TextField commandField;
     */

    @FXML
    private AnchorPane commandBoxPlaceholder;
    private CommandBox commandBox;

    @FXML
    private AnchorPane actionHistoryPanePlaceholder;
    private ActionHistoryPane actionHistoryPane;

    @FXML
    private AnchorPane homeTableViewPlaceholder;
    private HomeTableView homeTableView;

    @FXML
    private AnchorPane tasksTableViewPlaceholder;
    private TasksTableView tasksTableView;

    @FXML
    private AnchorPane eventsTableViewPlaceholder;
    private EventsTableView eventsTableView;

    @FXML
    private AnchorPane deadlinesTableViewPlaceholder;
    private DeadlinesTableView deadlinesTableView;

    @FXML
    private AnchorPane archivesTableViewPlaceholder;
    private ArchivesTableView archivesTableView;

    @FXML
    private TextArea consoleOutput;

    @FXML
    private TabPane tabPane;

    @FXML
    private ListView<ActionHistory> actionHistory;

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

    // @@author A0139194X
    public Node getNode() {
        return rootLayout;
    }

    // @@author A0138862W
    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.fillInnerParts(logic);
        mainWindow.configure(config.getAppTitle(), config.getTaskManagerName(), config, prefs, logic);
        return mainWindow;
    }
    // @@author

    private void configure(String appTitle, String taskManagerName, Config config, UserPrefs prefs, Logic logic) {

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
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        tabLst = tabPane.getTabs();
        updateTabTitle();

        configureComponents(logic);

        registerAsAnEventHandler(this);
    }

    //@@author A0138862W
    /**
     * Configure Tables and Tabs
     * @param logic Logic Manager instance
     */
    private void configureComponents(Logic logic) {
        
        // Configure sorting algorithm for tables
        SortedList<ReadOnlyTask> sortedTasks = logic.getFilteredTaskList().sorted();
        Comparator<ReadOnlyTask> comparator = new TaskListComparator();
        sortedTasks.setComparator(comparator);
        sortedTasks.comparatorProperty().bind(homeTableView.getTableView().comparatorProperty());

        // define placeholder label for empty table
        Label placeholder = new Label("What's on your mind?\nTry adding a new task by executing \"add\" command!");
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setTextAlignment(TextAlignment.CENTER);

        homeTableView.getTableView().setPlaceholder(placeholder);
        homeTableView.getTableView().setItems(sortedTasks);

        tasksTableView.getTableView().setPlaceholder(placeholder);
        tasksTableView.getTableView().setItems(logic.getFilteredFloatingTaskList());

        eventsTableView.getTableView().setPlaceholder(placeholder);
        eventsTableView.getTableView().setItems(logic.getFilteredEventList());

        deadlinesTableView.getTableView().setPlaceholder(placeholder);
        deadlinesTableView.getTableView().setItems(logic.getFilteredDeadlineList());

        archivesTableView.getTableView().setPlaceholder(placeholder);
        archivesTableView.getTableView().setItems(logic.getFilteredArchiveList());
        
        tabPane.getSelectionModel().selectedItemProperty().addListener((tabList, fromTab, toTab)->{
            this.raise(new TabChangedEvent(fromTab.getId(), toTab.getId()));
        });
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

    //@@author A0138862W
    private void fillInnerParts(Logic logic) {
        commandBox = CommandBox.load(primaryStage, commandBoxPlaceholder, logic);
        actionHistoryPane = ActionHistoryPane.load(primaryStage, actionHistoryPanePlaceholder, logic);
        homeTableView = HomeTableView.load(primaryStage, homeTableViewPlaceholder, logic);
        
        tasksTableView = TasksTableView.load(primaryStage, tasksTableViewPlaceholder, logic);
        
        eventsTableView = EventsTableView.load(primaryStage, eventsTableViewPlaceholder, logic);
        
        deadlinesTableView = DeadlinesTableView.load(primaryStage, deadlinesTableViewPlaceholder, logic);
        
        archivesTableView = ArchivesTableView.load(primaryStage, archivesTableViewPlaceholder, logic);
    }
    //@@author

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(), (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    public void show() {
        primaryStage.show();
    }

    // @@author A0124797R
    /**
     * update the number of task in each tab in the tab title
     */
    protected void updateTabTitle() {
        tabLst.get(INDEX_HOME).setText(NAME_TABS[INDEX_HOME]
                                       + "("
                                       + logic.getFilteredTaskList().size()
                                       + ")");
        tabLst.get(INDEX_TASKS).setText(NAME_TABS[INDEX_TASKS]
                                        + "("
                                        + logic.getFilteredFloatingTaskList().size()
                                        + ")");
        tabLst.get(INDEX_EVENTS).setText(NAME_TABS[INDEX_EVENTS]
                                         + "("
                                         + logic.getFilteredEventList().size()
                                         + ")");
        tabLst.get(INDEX_DEADLINES).setText(NAME_TABS[INDEX_DEADLINES]
                                            + "("
                                            + logic.getFilteredDeadlineList().size()
                                            + ")");
        tabLst.get(INDEX_ARCHIVES).setText(NAME_TABS[INDEX_ARCHIVES]
                                           + "("
                                           + logic.getFilteredArchiveList().size()
                                           + ")");
    }

    // @@author A0124797R
    /**
     * handle the switching of tabs when list/upcoming is used
     */
    private void updateTab(String result) {
        switch (result) {
            case ListCommand.MESSAGE_SUCCESS:
                tabPane.getSelectionModel().select(INDEX_HOME);
                break;
            case UpcomingCommand.MESSAGE_SUCCESS_UPCOMING:
            case UpcomingCommand.MESSAGE_SUCCESS_UPCOMING_DEADLINE:
            case UpcomingCommand.MESSAGE_SUCCESS_UPCOMING_EVENT:
                tabPane.getSelectionModel().select(INDEX_HOME);
                break;
            case ListCommand.MESSAGE_SUCCESS_TASKS:
                tabPane.getSelectionModel().select(INDEX_TASKS);
                break;
            case ListCommand.MESSAGE_SUCCESS_EVENTS:
                tabPane.getSelectionModel().select(INDEX_EVENTS);
                break;
            case ListCommand.MESSAGE_SUCCESS_DEADLINES:
                tabPane.getSelectionModel().select(INDEX_DEADLINES);
                break;
            case ListCommand.MESSAGE_SUCCESS_ARCHIVES:
                tabPane.getSelectionModel().select(INDEX_ARCHIVES);
                break;
        }
    }
    
    //@@author A0138862W
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event){
        // updates the tab when a list command is called
        this.updateTab(event.message);
        logger.info("Update tab.");
    }
    
    // @@author A0124797R
    @Subscribe
    private void handleTaskManagerChanged(TaskManagerChangedEvent event) {
        updateTabTitle();
        logger.info("Update tab title.");
    }
   
}
