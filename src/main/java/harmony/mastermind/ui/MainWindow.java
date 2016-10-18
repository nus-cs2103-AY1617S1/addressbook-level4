package harmony.mastermind.ui;

import java.util.logging.Logger;

import org.ocpsoft.prettytime.PrettyTime;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.core.Config;
import harmony.mastermind.commons.core.GuiSettings;
import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.commons.events.model.TaskManagerChangedEvent;
import harmony.mastermind.commons.events.ui.ExitAppRequestEvent;
import harmony.mastermind.commons.events.ui.IncorrectCommandAttemptedEvent;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.commands.ListCommand;
import harmony.mastermind.logic.commands.PreviousCommand;
import harmony.mastermind.model.UserPrefs;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {
    
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    private static final double WIDTH_MULTIPLIER_INDEX = 0.045;
    private static final double WIDTH_MULTIPLIER_NAME = 0.355;
    private static final double WIDTH_MULTIPLIER_STARTDATE = 0.2;
    private static final double WIDTH_MULTIPLIER_ENDDATE = 0.2;
    private static final double WIDTH_MULTIPLIER_TAGS = 0.2;
    
    private static final short INDEX_HOME = 0;
    private static final short INDEX_TASKS = 1;
    private static final short INDEX_EVENTS = 2;
    private static final short INDEX_DEADLINES = 3;
    private static final short INDEX_ARCHIVES = 4;
    
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 460;
    
    private Logic logic;

    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String taskManagerName;

    private final Logger logger = LogsCenter.getLogger(MainWindow.class);

    String currCommandText;
    
    String prevCommandText;

    private CommandResult mostRecentResult;

    // UI elements
    @FXML
    private TextField commandField;

    @FXML
    private TextArea consoleOutput;
    
    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<ReadOnlyTask> taskTableHome;

    @FXML
    private TableColumn<ReadOnlyTask, String> indexHome;

    @FXML
    private TableColumn<ReadOnlyTask, String> taskNameHome;

    @FXML
    private TableColumn<ReadOnlyTask, String> startDateHome;

    @FXML
    private TableColumn<ReadOnlyTask, String> endDateHome;

    @FXML
    private TableColumn<ReadOnlyTask, String> tagsHome;

    @FXML
    private TableView<ReadOnlyTask> taskTableTask;

    @FXML
    private TableColumn<ReadOnlyTask, String> indexTask;

    @FXML
    private TableColumn<ReadOnlyTask, String> taskNameTask;

    @FXML
    private TableColumn<ReadOnlyTask, String> startDateTask;

    @FXML
    private TableColumn<ReadOnlyTask, String> endDateTask;

    @FXML
    private TableColumn<ReadOnlyTask, String> tagsTask;
    
    @FXML
    private TableView<ReadOnlyTask> taskTableEvent;

    @FXML
    private TableColumn<ReadOnlyTask, String> indexEvent;

    @FXML
    private TableColumn<ReadOnlyTask, String> taskNameEvent;

    @FXML
    private TableColumn<ReadOnlyTask, String> startDateEvent;

    @FXML
    private TableColumn<ReadOnlyTask, String> endDateEvent;

    @FXML
    private TableColumn<ReadOnlyTask, String> tagsEvent;
    
    @FXML
    private TableView<ReadOnlyTask> taskTableDeadline;

    @FXML
    private TableColumn<ReadOnlyTask, String> indexDeadline;

    @FXML
    private TableColumn<ReadOnlyTask, String> taskNameDeadline;

    @FXML
    private TableColumn<ReadOnlyTask, String> startDateDeadline;

    @FXML
    private TableColumn<ReadOnlyTask, String> endDateDeadline;

    @FXML
    private TableColumn<ReadOnlyTask, String> tagsDeadline;
    
    @FXML
    private TableView<ReadOnlyTask> taskTableArchive;

    @FXML
    private TableColumn<ReadOnlyTask, String> indexArchive;

    @FXML
    private TableColumn<ReadOnlyTask, String> taskNameArchive;

    @FXML
    private TableColumn<ReadOnlyTask, String> startDateArchive;

    @FXML
    private TableColumn<ReadOnlyTask, String> endDateArchive;

    @FXML
    private TableColumn<ReadOnlyTask, String> tagsArchive;

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

    //@@author A0138862W
    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getTaskManagerName(), config, prefs, logic);
        return mainWindow;
    }

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
        
        taskTableHome.setItems(logic.getFilteredTaskList());
        taskTableTask.setItems(logic.getFilteredFloatingTaskList());
        taskTableEvent.setItems(logic.getFilteredEventList());
        taskTableDeadline.setItems(logic.getFilteredDeadlineList());
        taskTableArchive.setItems(logic.getFilteredArchiveList());

        registerAsAnEventHandler(this);
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
    
    public String getCurrentTab() {
        return tabPane.getSelectionModel().getSelectedItem().getText();
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(), (int) primaryStage.getX(),
                (int) primaryStage.getY());
    }

    /*
     * TODO: WILL NOT WORK BECAUSE UI REVAMP REMOVED Browser panel.
     * 
     * @FXML public void handleHelp() { browserPanel.loadHelpPage(); }
     */

    public void show() {
        primaryStage.show();
    }

    // ==================================

    @FXML
    //@@author A0124797R
    private void initialize() {
        initHomeTab();
        initTaskTab();
        initEventTab();
        initDeadlineTab();
        initArchiveTab();

    }

    /**
     * Initialise the tasks in the Home tab 
     */
    @FXML
    //@@author A0124797R
    private void initHomeTab() {
        initIndex(indexHome);
        initName(taskNameHome);
        initStartDate(startDateHome);
        initEndDate(endDateHome);
        initTags(tagsHome);
    }

    /**
     * Initialise the tasks in the Task tab 
     */
    @FXML
    //@@author A0124797R
    private void initTaskTab() {
        initIndex(indexTask);
        initName(taskNameTask);
        initStartDate(startDateTask);
        initEndDate(endDateTask);
        initTags(tagsTask);        
    }
    /**
     * Initialise the task in the Event tab 
     */
    @FXML
    //@@author A0124797R
    private void initEventTab() {
        initIndex(indexEvent);
        initName(taskNameEvent);
        initStartDate(startDateEvent);
        initEndDate(endDateEvent);
        initTags(tagsEvent);
    }
    /**
     * Initialise the task in the Deadline tab 
     */
    @FXML
    //@@author A0124797R
    private void initDeadlineTab() {
        initIndex(indexDeadline);
        initName(taskNameDeadline);
        initStartDate(startDateDeadline);
        initEndDate(endDateDeadline);
        initTags(tagsDeadline);        
    }
    /**
     * Initialise the task in the archive tab 
     */
    @FXML
    //@@author A0124797R
    private void initArchiveTab() {
        initIndex(indexArchive);
        initName(taskNameArchive);
        initStartDate(startDateArchive);
        initEndDate(endDateArchive);
        initTags(tagsArchive);      
    }
    

    
    /**
     * Initializes the indexing of tasks
     */
    //@@author A0138862W
    private void initIndex(TableColumn<ReadOnlyTask, String> indexColumn) {
        indexColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_INDEX));
        indexColumn.setCellFactory(column -> new TableCell<ReadOnlyTask, String>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);

                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1));
                }
            }
        });
    }

    /**
     * Initialize the Names of the tasks
     */
    //@@author A0138862W
    private void initName(TableColumn<ReadOnlyTask, String> nameColumn) {
        nameColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_NAME));
        nameColumn.setCellValueFactory(task -> new ReadOnlyStringWrapper(task.getValue().getName()));
    }
    
    /**
     * Initialize the start dates of the tasks
     */
    //@@author A0138862W
    private void initStartDate(TableColumn<ReadOnlyTask, String> startDateColumn) {
        startDateColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_STARTDATE));
        startDateColumn.setCellValueFactory(task -> {
            if (task.getValue().isEvent()) {
                return new ReadOnlyStringWrapper(new PrettyTime().format(task.getValue().getStartDate())
                                                 + "\n"
                                                 + task.getValue().getStartDate().toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
    }
    
    /**
     * Initialize the end dates of the tasks
     */
    //@@author A0138862W
    private void initEndDate(TableColumn<ReadOnlyTask, String> endDateColumn) {
        endDateColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_ENDDATE));
        endDateColumn.setCellValueFactory(task -> {
            if (!task.getValue().isFloating()) {
                return new ReadOnlyStringWrapper(new PrettyTime().format(task.getValue().getEndDate())
                                                 + "\n"
                                                 + task.getValue().getEndDate().toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
    }
    
    /**
     * Initialize the tags of the tasks
     */
    //@@author A0138862W
    private void initTags(TableColumn<ReadOnlyTask, String> tagsColumn) {
        tagsColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_TAGS));
        tagsColumn.setCellValueFactory(task -> new ReadOnlyStringWrapper(task.getValue().getTags().toString()));
    }


    @FXML
    //@@author A0124797R
    private void handleCommandInputChanged() {
        // Take a copy of the command text
        currCommandText = commandField.getText();
        String currentTab = getCurrentTab();

        setStyleToIndicateCorrectCommand();

        /*
         * We assume the command is correct. If it is incorrect, the command box
         * will be changed accordingly in the event handling code {@link
         * #handleIncorrectCommandAttempted}
         */
        mostRecentResult = logic.execute(currCommandText, currentTab);
        consoleOutput.setText(mostRecentResult.feedbackToUser);
        
        if (!currCommandText.equals(PreviousCommand.COMMAND_WORD)) {
            updateTab(mostRecentResult);

            prevCommandText = currCommandText;
        }else {
            restorePrevCommandText();
            
            return;
        }

        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    @Subscribe
    //@@author A0124797R
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Invalid command: " + currCommandText));
        restoreCommandText();
    }

    //@@author A0124797R
    private void updateTab(CommandResult result) {
        String tab = result.toString();
        switch (tab) {
            case ListCommand.MESSAGE_SUCCESS:           tabPane.getSelectionModel().select(INDEX_HOME);
                                                        break;
            case ListCommand.MESSAGE_SUCCESS_TASKS:     tabPane.getSelectionModel().select(INDEX_TASKS);
                                                        break;
            case ListCommand.MESSAGE_SUCCESS_EVENTS:    tabPane.getSelectionModel().select(INDEX_EVENTS);
                                                        break;
            case ListCommand.MESSAGE_SUCCESS_DEADLINES: tabPane.getSelectionModel().select(INDEX_DEADLINES);
                                                        break;
            case ListCommand.MESSAGE_SUCCESS_ARCHIVES:  tabPane.getSelectionModel().select(INDEX_ARCHIVES);
                                                        break;
        }
    }
    
    /**
     * Sets the command box style to indicate a correct command.
     */
    //@@author A0124797R
    private void setStyleToIndicateCorrectCommand() {
        commandField.setText("");
    }

    //@@author A0124797R
    private void restoreCommandText() {
        commandField.setText(currCommandText);
    }
    
    //@@author A0124797R
    private void restorePrevCommandText() {
        commandField.setText(prevCommandText);
    }
}
