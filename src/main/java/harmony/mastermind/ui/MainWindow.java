package harmony.mastermind.ui;

import java.util.Date;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.ocpsoft.prettytime.PrettyTime;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.core.Config;
import harmony.mastermind.commons.core.GuiSettings;
import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.commons.events.model.ExpectingConfirmationEvent;
import harmony.mastermind.commons.events.model.TaskManagerChangedEvent;
import harmony.mastermind.commons.events.ui.ExitAppRequestEvent;
import harmony.mastermind.commons.events.ui.IncorrectCommandAttemptedEvent;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.commands.ListCommand;
import harmony.mastermind.model.UserPrefs;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {
    
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    private static final double WIDTH_MULTIPLIER_INDEX = 0.07;
    private static final double WIDTH_MULTIPLIER_NAME = 0.285;
    private static final double WIDTH_MULTIPLIER_STARTDATE = 0.18;
    private static final double WIDTH_MULTIPLIER_ENDDATE = 0.18;
    private static final double WIDTH_MULTIPLIER_TAGS = 0.15;
    private static final double WIDTH_MULTIPLIER_RECUR = 0.12;
    
    private static final short INDEX_HOME = 0;
    private static final short INDEX_TASKS = 1;
    private static final short INDEX_EVENTS = 2;
    private static final short INDEX_DEADLINES = 3;
    private static final short INDEX_ARCHIVES = 4;
    
    private static final KeyCombination CTRL_ONE = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_TWO = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_THREE = new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_FOUR = new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_FIVE = new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.CONTROL_DOWN);
    
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

    private CommandResult mostRecentResult;
    private String currCommandText;
    private Stack<String> commandHistory = new Stack<String>();
    private int commandIndex = 0;
    private boolean isExpectingConfirmation = false;
    
    private AutoCompletionBinding<String> autoCompletionBinding;
    
    //List of words for autocomplete 
    String[] listOfWords = {"add", "delete", "edit", "clear", "help", "undo", "mark", "find", "exit"
            ,"do", "delete"};

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
    private TableColumn<ReadOnlyTask, Boolean> recurHome;

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
    private TableColumn<ReadOnlyTask, Boolean> recurTask;

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
    private TableColumn<ReadOnlyTask, Boolean> recurEvent;

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
    private TableColumn<ReadOnlyTask, Boolean> recurDeadline;

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
    @FXML
    private TableColumn<ReadOnlyTask, Boolean> recurArchive;

    @FXML
    private ListView<String> actionHistory;

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
    
    //@@A0138862W
    public void pushToActionHistory(Date dateExecuted, String title, String description){
        actionHistory.getItems().add(title+";"+description+";"+dateExecuted);
        actionHistory.scrollTo(actionHistory.getItems().size()-1);
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

        
        initAutoComplete();
        
        initActionHistory(actionHistory);
        actionHistory.setPlaceholder(new Label("No action history yet"));
        
        // default focus to cammand box
        Platform.runLater(()->commandField.requestFocus());
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
        initRecur(recurHome);
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
        initRecur(recurTask);       
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
        initRecur(recurEvent);
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
        initRecur(recurDeadline);    
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
        initRecur(recurArchive);    
    }
    

    private void initActionHistory(ListView<String> actionHistory){

        actionHistory.setCellFactory(listView -> {
            ListCell<String> actionCell = new ListCell<String>(){
              
                @Override
                protected void updateItem(String item, boolean isEmpty){
                    super.updateItem(item, isEmpty);
                    
                    if(!isEmpty){
                        
                        ActionHistoryItem actionHistoryItem = UiPartLoader.loadUiPart(new ActionHistoryItem());
                        
                        
                        String[] args = item.split(";");
                        
                        actionHistoryItem.setTitle(args[0].toUpperCase());
                        actionHistoryItem.setDescription(args[1]);
                        actionHistoryItem.setDate(args[2].toUpperCase());
                        
                        
                        if(args[0].toUpperCase().equals("INVALID COMMAND")){
                            actionHistoryItem.setTypeFail();
                        }else{
                            actionHistoryItem.setTypeSuccess();
                        }
                        
                        this.setGraphic(actionHistoryItem.getNode());
                        this.setPrefHeight(50);
                        this.setPrefWidth(250);
                        
                        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }else{
                        this.setGraphic(null);
                    }
                }
            };
            
            
            return actionCell;
        });
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
        
        nameColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, String>(){
            
            @Override
            public void updateItem(String item , boolean isEmpty){
                super.updateItem(item, isEmpty);
                if(!isEmpty()){
                    
                    TextFlow textFlow = new TextFlow();
                    
                    Text taskName = new Text(item);
                    taskName.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-fill: deepSkyBlue;");
                    
                    textFlow.getChildren().add(taskName);
                    
                    
                    this.setGraphic(textFlow);
                    this.setPrefHeight(50);
                    
                }else{
                    this.setGraphic(null);
                }
                
            }
        });
        
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
                                                 + task.getValue().parse(task.getValue().getStartDate()));
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        
        startDateColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, String>(){
            
            @Override
            public void updateItem(String item , boolean isEmpty){
                super.updateItem(item, isEmpty);
                if(!isEmpty()){
                    
                    TextFlow textFlow = new TextFlow();
                    
                    String[] dates = item.split("\n");
                    
                    if(dates.length>1){
                    
                        Text prettyDate = new Text(dates[0]);
                        prettyDate.setStyle("-fx-font-weight:bold; -fx-fill: white;");
                        
                        Text lineBreak = new Text("\n\n");
                        lineBreak.setStyle("-fx-font-size:2px;");
                        
                        Text uglyDate = new Text(dates[1]);
                        uglyDate.setStyle("fx-font-style: oblique; -fx-fill: deepSkyBlue; -fx-font-size: 10px;");
                        
                        textFlow.getChildren().add(prettyDate);
                        textFlow.getChildren().add(lineBreak);
                        textFlow.getChildren().add(uglyDate);
                        
                        
                        this.setGraphic(textFlow);
                        this.setPrefHeight(50);
                    }
                }else{
                    this.setGraphic(null);
                }
                
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
                                                 + task.getValue().parse(task.getValue().getEndDate()));
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        
        endDateColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, String>(){
            
            @Override
            public void updateItem(String item , boolean isEmpty){
                super.updateItem(item, isEmpty);
                if(!isEmpty()){
                    
                    TextFlow textFlow = new TextFlow();
                    
                    String[] dates = item.split("\n");
                    
                    if(dates.length>1){
                    
                        Text prettyDate = new Text(dates[0]);
                        prettyDate.setStyle("-fx-font-weight:bold; -fx-fill: white;");
                        
                        Text lineBreak = new Text("\n\n");
                        lineBreak.setStyle("-fx-font-size:2px;");
                        
                        Text uglyDate = new Text(dates[1]);
                        uglyDate.setStyle("fx-font-style: oblique; -fx-fill: deepSkyBlue; -fx-font-size: 10px;");
                        
                        textFlow.getChildren().add(prettyDate);
                        textFlow.getChildren().add(lineBreak);
                        textFlow.getChildren().add(uglyDate);
                        
                        
                        this.setGraphic(textFlow);
                        this.setPrefHeight(50);
                    }
                }else{
                    this.setGraphic(null);
                }
                
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
        
        tagsColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, String>(){
            
            @Override
            public void updateItem(String item , boolean isEmpty){
                super.updateItem(item, isEmpty);
                if(!isEmpty()){
                    this.setText(item.replace(',', ' '));
                    this.setStyle("-fx-font-weight:bold;");
                    this.setWrapText(true);
                }else{
                    this.setText("");
                }
            }
        });
        
    }
    
    
    /**
     * Initialize a checkbox to determine whether task is recurring
     */
    //@@author A0124797R
    private void initRecur(TableColumn<ReadOnlyTask, Boolean> recurColumn) {
        recurColumn.setGraphic(new ImageView("file:src/main/resources/images/recur.png"));
        recurColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_RECUR));
        recurColumn.setCellValueFactory(task -> new SimpleBooleanProperty(task.getValue().isRecur()));
        
        recurColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, Boolean>(){
            
            @Override
            public void updateItem(Boolean isRecur , boolean isEmpty){
                super.updateItem(isRecur, isEmpty);
                if(!isEmpty()){
                    CheckBox box = new CheckBox();
                    box.setSelected(isRecur);
                    box.setDisable(true);
                    box.setStyle("-fx-opacity: 1");
                    
                    this.setAlignment(Pos.CENTER);
                    this.setGraphic(box);
                }else{
                    this.setGraphic(null);;
                }
            }
        });
    }


    /**
     * Handles all command input keyed in by user
     */
    @FXML
    //@@author A0124797R
    private void handleCommandInputChanged() {
        // Take a copy of the command text
        currCommandText = commandField.getText();
        
        setStyleToIndicateCorrectCommand();
        
        String currentTab = getCurrentTab();
        /*
         * We assume the command is correct. If it is incorrect, the command box
         * will be changed accordingly in the event handling code {@link
         * #handleIncorrectCommandAttempted}
         */
        mostRecentResult = logic.execute(currCommandText, currentTab);
        consoleOutput.setText(mostRecentResult.feedbackToUser);
        
        this.pushToActionHistory(new Date(), mostRecentResult.title, mostRecentResult.feedbackToUser);

        //updates the tab when a list command is called
        updateTab(mostRecentResult);

        //adds current command into the stack
        updateCommandHistory(currCommandText);

        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    //@@author A0124797R
    /**
     * Handles any KeyPress in the commandField
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        KeyCode key = event.getCode();
        switch (key) {
            case UP:    restorePrevCommandText();
                        return;
            case DOWN:  restoreNextCommandText();
                        return;
        }
        
        if (CTRL_ONE.match(event)) {
            updateTab(ListCommand.MESSAGE_SUCCESS);
        }else if (CTRL_TWO.match(event)) {
            updateTab(ListCommand.MESSAGE_SUCCESS_TASKS);
        }else if (CTRL_THREE.match(event)) {
            updateTab(ListCommand.MESSAGE_SUCCESS_EVENTS);
        }else if (CTRL_FOUR.match(event)) {
            updateTab(ListCommand.MESSAGE_SUCCESS_DEADLINES);
        }else if (CTRL_FIVE.match(event)) {
            updateTab(ListCommand.MESSAGE_SUCCESS_ARCHIVES);
        }
    }
    
    @FXML
    //@@author A0143378Y
    private void initAutoComplete(){
        //Autocomplete function
        autoCompletionBinding = TextFields.bindAutoCompletion(commandField, listOfWords);
        autoCompletionBinding.setPrefWidth(500);
        autoCompletionBinding.setVisibleRowCount(5);
        autoCompletionBinding.setHideOnEscape(true);

    }

    @Subscribe
    //@@author A0139194X
    private void handleExpectingConfirmationEvent(ExpectingConfirmationEvent event) {
        isExpectingConfirmation = true;
        consoleOutput.setText("Type \"Yes\" to confirm clearing Mastermind." + "\n"
                               + "Type \"No\" to cancel.");
        while (isExpectingConfirmation) {
            String confirmation = commandField.getText();
            setStyleToIndicateCorrectCommand();

            if (confirmation.toLowerCase().trim().equals("yes")) {
                isExpectingConfirmation = false;
                break;
            } else if (confirmation.toLowerCase().trim().equals("no")) {
                isExpectingConfirmation = false;
                break;
            }
        }
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
        updateTab(tab);
    }
    
    //@@author A0124797R
    private void updateTab(String result) {
        switch (result) {
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
    
    //@@author A0124797R
    /**
     * Adds recent input into stack
     */
    private void updateCommandHistory(String command) {
        commandHistory.push(command);
        commandIndex = commandHistory.size();
    }

    //@@author A0124797R
    private String getPrevCommandHistory() {
        if (commandHistory.empty()) {
            return null;
        }else if (commandIndex == 0) {
            return null;
        }else {
            commandIndex--;
            return commandHistory.get(commandIndex);
        }
    }
    
    //@@author A0124797R
    private String getNextCommandHistory() {
        if (commandHistory.empty()) {
            return null;
        }else if (commandIndex >= commandHistory.size()) {
            return null;
        }else {
            commandIndex++;
            return commandHistory.get(commandIndex-1);
        }
    }
    
    public void disposeAutoCompleteBinding(){
        this.autoCompletionBinding.dispose();
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
        String prevCommand = getPrevCommandHistory();
        if (prevCommand!=null) {
            commandField.setText(prevCommand);
        }//else ignore
    }
    
    //@@author A0124797R
    private void restoreNextCommandText() {
        String nextCommand = getNextCommandHistory();
        if (nextCommand!=null) {
            commandField.setText(nextCommand);
        }else {
            commandField.setText("");
        }
    }
}
