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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

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
    private TableView<ReadOnlyTask> taskTable;

    @FXML
    private TableColumn<ReadOnlyTask, String> indexColumn;

    @FXML
    private TableColumn<ReadOnlyTask, String> taskNameColumn;

    @FXML
    private TableColumn<ReadOnlyTask, String> startDateColumn;

    @FXML
    private TableColumn<ReadOnlyTask, String> endDateColumn;

    @FXML
    private TableColumn<ReadOnlyTask, String> tagsColumn;

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

        taskTable.setItems(logic.getFilteredTaskList());

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

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(), (int) primaryStage.getX(),
                (int) primaryStage.getY());
    }

    public void show() {
        primaryStage.show();
    }

    // ==================================

    @FXML
    //@@author A0138862W
    private void initialize() {
        indexColumn.prefWidthProperty().bind(taskTable.widthProperty().multiply(0.03));
        taskNameColumn.prefWidthProperty().bind(taskTable.widthProperty().multiply(0.40));
        startDateColumn.prefWidthProperty().bind(taskTable.widthProperty().multiply(0.20));
        endDateColumn.prefWidthProperty().bind(taskTable.widthProperty().multiply(0.20));
        tagsColumn.prefWidthProperty().bind(taskTable.widthProperty().multiply(0.17));

        taskNameColumn.setCellValueFactory(task -> new ReadOnlyStringWrapper(task.getValue().getName()));
        startDateColumn.setCellValueFactory(task -> {
            if (task.getValue().isEvent()) {
                return new ReadOnlyStringWrapper(new PrettyTime().format(task.getValue().getStartDate())
                                                 + "\n"
                                                 + task.getValue().getStartDate().toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        endDateColumn.setCellValueFactory(task -> {
            if (!task.getValue().isFloating()) {
                return new ReadOnlyStringWrapper(new PrettyTime().format(task.getValue().getEndDate())
                                                 + "\n"
                                                 + task.getValue().getEndDate().toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        tagsColumn.setCellValueFactory(task -> new ReadOnlyStringWrapper(task.getValue().getTags().toString()));

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

    @FXML
    //@@author A0124797R
    private void handleCommandInputChanged() {
        // Take a copy of the command text
        currCommandText = commandField.getText();

        setStyleToIndicateCorrectCommand();
        
        if (!currCommandText.equals(PreviousCommand.COMMAND_WORD)) {
            /*
             * We assume the command is correct. If it is incorrect, the command box
             * will be changed accordingly in the event handling code {@link
             * #handleIncorrectCommandAttempted}
             */
            mostRecentResult = logic.execute(currCommandText);
            consoleOutput.setText(mostRecentResult.feedbackToUser);

            System.out.println();
            
            if (!mostRecentResult.feedbackToUser.equals(ListCommand.MESSAGE_SUCCESS_ARCHIVED)) {
                taskTable.setItems(logic.getFilteredTaskList());
                
            }else {
                taskTable.setItems(logic.getFilteredArchiveList());
            }
            

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
