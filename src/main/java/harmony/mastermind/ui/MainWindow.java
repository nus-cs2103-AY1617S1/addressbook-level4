package harmony.mastermind.ui;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.Collections;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.ocpsoft.prettytime.PrettyTime;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.core.Config;
import harmony.mastermind.commons.core.GuiSettings;
import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.commons.events.model.ExpectingConfirmationEvent;
import harmony.mastermind.commons.events.model.TaskManagerChangedEvent;
import harmony.mastermind.commons.events.ui.IncorrectCommandAttemptedEvent;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.commands.ListCommand;
import harmony.mastermind.logic.commands.UpcomingCommand;
import harmony.mastermind.model.UserPrefs;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
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
import javafx.scene.layout.HBox;
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
    
    private static final double WIDTH_MULTIPLIER_INDEX = 0.03;
    private static final double WIDTH_MULTIPLIER_NAME = 0.285;
    private static final double WIDTH_MULTIPLIER_STARTDATE = 0.18;
    private static final double WIDTH_MULTIPLIER_ENDDATE = 0.18;
    private static final double WIDTH_MULTIPLIER_TAGS = 0.2;
    private static final double WIDTH_MULTIPLIER_RECUR = 0.1;
    
    private static final short INDEX_HOME = 0;
    private static final short INDEX_TASKS = 1;
    private static final short INDEX_EVENTS = 2;
    private static final short INDEX_DEADLINES = 3;
    private static final short INDEX_ARCHIVES = 4;
    
    private static final String[] NAME_TABS = {"Home", "Tasks", "Events", "Deadlines", "Archives"};
    
    
    private static final KeyCombination CTRL_ONE = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_TWO = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_THREE = new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_FOUR = new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_FIVE = new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.CONTROL_DOWN);
    
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
    private Stack<String> commandHistory = new Stack<String>();
    private int commandIndex = 0;
    
    private boolean isExpectingConfirmation = false;
    
    private AutoCompletionBinding<String> autoCompletionBinding;
    
    //List of words for autocomplete 
    Set listOfWords = new HashSet<>();
    String[] words = {"add", "delete", "edit", "clear", "help", "undo", "mark", "find", "exit"
            ,"do", "delete"};
    

    ObservableList<Tab> tabLst;

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
    private TableColumn<ReadOnlyTask, ReadOnlyTask> indexHome;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> taskNameHome;

    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> startDateHome;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> endDateHome;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> tagsHome;
    @FXML
    private TableColumn<ReadOnlyTask, Boolean> recurHome;

    @FXML
    private TableView<ReadOnlyTask> taskTableTask;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> indexTask;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> taskNameTask;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> startDateTask;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> endDateTask;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> tagsTask;
    @FXML
    private TableColumn<ReadOnlyTask, Boolean> recurTask;

    @FXML
    private TableView<ReadOnlyTask> taskTableEvent;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> indexEvent;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> taskNameEvent;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> startDateEvent;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> endDateEvent;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> tagsEvent;
    @FXML
    private TableColumn<ReadOnlyTask, Boolean> recurEvent;

    @FXML
    private TableView<ReadOnlyTask> taskTableDeadline;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> indexDeadline;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> taskNameDeadline;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> startDateDeadline;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> endDateDeadline;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> tagsDeadline;
    @FXML
    private TableColumn<ReadOnlyTask, Boolean> recurDeadline;

    @FXML
    private TableView<ReadOnlyTask> taskTableArchive;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> indexArchive;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> taskNameArchive;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> startDateArchive;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> endDateArchive;
    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> tagsArchive;
    @FXML
    private TableColumn<ReadOnlyTask, Boolean> recurArchive;

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

    //@@author A0139194X
    public Node getNode() {
        return rootLayout;
    }
    
    //@@author A0138862W
    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
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
    public void pushToActionHistory(String title, String description){
        ActionHistory aHistory = new ActionHistory(title, description);
        
        actionHistory.getItems().add(aHistory);
        actionHistory.scrollTo(actionHistory.getItems().size()-1);
    }
    // @@author

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
        return tabPane.getSelectionModel().getSelectedItem().getId();
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

    //@@author A0124797R
    /**
     * update the number of task in each tab in the tab title
     */
    private void updateTabTitle() {
        tabLst.get(INDEX_HOME).setText(NAME_TABS[INDEX_HOME] + "(" 
                + logic.getFilteredTaskList().size() + ")");
        tabLst.get(INDEX_TASKS).setText(NAME_TABS[INDEX_TASKS] + "("
                + logic.getFilteredFloatingTaskList().size() + ")");
        tabLst.get(INDEX_EVENTS).setText(NAME_TABS[INDEX_EVENTS] + "("
                + logic.getFilteredEventList().size() + ")");
        tabLst.get(INDEX_DEADLINES).setText(NAME_TABS[INDEX_DEADLINES] + "("
                + logic.getFilteredDeadlineList().size() + ")");
        tabLst.get(INDEX_ARCHIVES).setText(NAME_TABS[INDEX_ARCHIVES] + "("
                + logic.getFilteredArchiveList().size() + ")");
    }
    
    
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
    //@@author A0124797R
    private void initArchiveTab() {
        initIndex(indexArchive);
        initName(taskNameArchive);
        initStartDate(startDateArchive);
        initEndDate(endDateArchive);
        initTags(tagsArchive);  
        initRecur(recurArchive);
    }
    
    //@@author A0138862W
    private void initActionHistory(ListView<ActionHistory> actionHistory){

        actionHistory.setOnMouseClicked(value->{
            consoleOutput.setText(actionHistory.getSelectionModel().getSelectedItem().getDescription());
        });
        actionHistory.setCellFactory(listView -> {
            ListCell<ActionHistory> actionCell = new ListCell<ActionHistory>(){
              
                @Override
                protected void updateItem(ActionHistory item, boolean isEmpty){
                    super.updateItem(item, isEmpty);
                    
                    if(!isEmpty){
                        
                        ActionHistoryEntry actionHistoryEntry = UiPartLoader.loadUiPart(new ActionHistoryEntry());
                        
                        
                        actionHistoryEntry.setTitle(item.getTitle().toUpperCase());
                        actionHistoryEntry.setDescription(item.getDescription());
                        actionHistoryEntry.setDate(item.getDateActioned().toString().toUpperCase());
                        
                        
                        if(item.getTitle().toUpperCase().equals("INVALID COMMAND")){
                            actionHistoryEntry.setTypeFail();
                        }else{
                            actionHistoryEntry.setTypeSuccess();
                        }
                        
                        this.setGraphic(actionHistoryEntry.getNode());
                        
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
    private void initIndex(TableColumn<ReadOnlyTask, ReadOnlyTask> indexColumn) {
        indexColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_INDEX));
        
        indexColumn.setCellFactory(column -> new TableCell<ReadOnlyTask, ReadOnlyTask>() {
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
    private void initName(TableColumn<ReadOnlyTask, ReadOnlyTask> nameColumn) {
        nameColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_NAME));
        nameColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));
        
        nameColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, ReadOnlyTask>(){
            
            @Override
            public void updateItem(ReadOnlyTask readOnlyTask , boolean isEmpty){
                super.updateItem(readOnlyTask, isEmpty);
                
                if(!isEmpty()){
                    
                    VBox vBox = new VBox(3);                   
                    
                    Text taskName = generateStyledText(readOnlyTask, readOnlyTask.getName());
                    taskName.getStyleClass().add("task-name-column");
                    vBox.getChildren().add(taskName);
                    
                    HBox hBox = new HBox(5);
                    
                    Button status = new Button();
                    if(readOnlyTask.isHappening()){
                        status.setText("HAPPENING");
                        status.getStyleClass().add("tag-happening");
                        hBox.getChildren().add(status);
                    }else if(readOnlyTask.isDue()){
                        status.setText("DUE");
                        status.getStyleClass().add("tag-overdue");
                        hBox.getChildren().add(status);
                    }
                    
                    if(readOnlyTask.isEvent()){
                        Button eventDuration = new Button();
                        eventDuration.setText(readOnlyTask.getEventDuration().toDays()+" DAY(S) EVENT");
                        eventDuration.getStyleClass().add("tag-event-duration");
                        hBox.getChildren().add(eventDuration);
                    }else if(readOnlyTask.isDeadline() && !readOnlyTask.isDue()){
                        Button dueDuration = new Button();
                        dueDuration.setText("DUE IN "+readOnlyTask.getDueDuration().toDays()+" DAY(S)");
                        dueDuration.getStyleClass().add("tag-due-duration");
                        hBox.getChildren().add(dueDuration);
                    }
                    
                    vBox.getChildren().add(hBox);
                    
                    this.setGraphic(vBox);
                    this.setPrefHeight(50);
                    
                }else{
                    this.setGraphic(null);
                }
                
            }
        });
        
    }
    
    /*
     * Generate styled row base on the task status: due(red), happening(orange), normal(blue)
     * 
     */
    //@@author A0138862W
    private Text generateStyledText(ReadOnlyTask readOnlyTask, String text){
        Text taskName = new Text(text);
        
        if(readOnlyTask.isHappening()){
            taskName.getStyleClass().add("happening");
        }else if(readOnlyTask.isDue()){
            taskName.getStyleClass().add("overdue");
        }else{
            taskName.getStyleClass().add("normal");
        }
        return taskName;
    }
    
    
    /**
     * Initialize the start dates of the tasks
     */
    //@@author A0138862W
    private void initStartDate(TableColumn<ReadOnlyTask, ReadOnlyTask> startDateColumn) {
        startDateColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_STARTDATE));
        startDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));
        
        startDateColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, ReadOnlyTask>(){
            
            @Override
            public void updateItem(ReadOnlyTask readOnlyTask , boolean isEmpty){
                super.updateItem(readOnlyTask, isEmpty);
                if(!isEmpty() && readOnlyTask.getStartDate()!= null){
                    
                    TextFlow textFlow = new TextFlow();
                    
                    Text prettyDate = generateStyledText(readOnlyTask, prettyTime.format(readOnlyTask.getStartDate()));
                    prettyDate.getStyleClass().add("pretty-date");
                    
                    Text lineBreak = new Text("\n\n");
                    lineBreak.setStyle("-fx-font-size:2px;");
                    
                    Text uglyDate = generateStyledText(readOnlyTask, readOnlyTask.parse(readOnlyTask.getStartDate()));
                    uglyDate.getStyleClass().add("ugly-date");
                    
                    textFlow.getChildren().add(prettyDate);
                    textFlow.getChildren().add(lineBreak);
                    textFlow.getChildren().add(uglyDate);
                    
                    
                    this.setGraphic(textFlow);
                    this.setPrefHeight(50);
                    
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
    private void initEndDate(TableColumn<ReadOnlyTask, ReadOnlyTask> endDateColumn) {
        endDateColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_ENDDATE));
        endDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));
        
        endDateColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, ReadOnlyTask>(){
            
            @Override
            public void updateItem(ReadOnlyTask readOnlyTask , boolean isEmpty){
                super.updateItem(readOnlyTask, isEmpty);
                if(!isEmpty() && readOnlyTask.getEndDate() != null){
                    
                    TextFlow textFlow = new TextFlow();
                    
                    Text prettyDate = generateStyledText(readOnlyTask, prettyTime.format(readOnlyTask.getEndDate()));
                    prettyDate.getStyleClass().add("pretty-date");
                    
                    Text lineBreak = new Text("\n\n");
                    lineBreak.setStyle("-fx-font-size:2px;");
                    
                    Text uglyDate = generateStyledText(readOnlyTask,readOnlyTask.parse(readOnlyTask.getEndDate()));
                    uglyDate.getStyleClass().add("ugly-date");
                    
                    textFlow.getChildren().add(prettyDate);
                    textFlow.getChildren().add(lineBreak);
                    textFlow.getChildren().add(uglyDate);
                    
                    
                    this.setGraphic(textFlow);
                    this.setPrefHeight(50);
                    
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
    private void initTags(TableColumn<ReadOnlyTask, ReadOnlyTask> tagsColumn) {
        tagsColumn.prefWidthProperty().bind(taskTableHome.widthProperty().multiply(WIDTH_MULTIPLIER_TAGS));
        tagsColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));
        
        tagsColumn.setCellFactory( col -> new TableCell<ReadOnlyTask, ReadOnlyTask>(){
            
            @Override
            public void updateItem(ReadOnlyTask readOnlyTask , boolean isEmpty){
                super.updateItem(readOnlyTask, isEmpty);
                if(!isEmpty() && readOnlyTask.getTags()!=null){
                    
                    HBox tags = new HBox(5);
                    
                    for(Tag tag : readOnlyTask.getTags()){
                        Button tagBubble = new Button();
                        tagBubble.setText(tag.tagName);
                        tagBubble.getStyleClass().add("tag");
                        tags.getChildren().add(tagBubble);
                    }
                    
                    this.setGraphic(tags);
                }else{
                    this.setGraphic(null);
                }
            }
        });
        
    }
    
    
    /**
     * Initialize a checkbox to determine whether task is recurring
     */
    //@@author A0124797R
    private void initRecur(TableColumn<ReadOnlyTask, Boolean> recurColumn) {
        recurColumn.setGraphic(new ImageView("file:src/main/resources/images/recur_white.png"));
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
        
        this.pushToActionHistory(mostRecentResult.title, mostRecentResult.feedbackToUser);

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
            case ENTER: learnWord(commandField.getText());
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
        Collections.addAll(listOfWords, words);
        autoCompletionBinding = TextFields.bindAutoCompletion(commandField, listOfWords);
        autoCompletionBinding.setPrefWidth(500);
        autoCompletionBinding.setVisibleRowCount(5);
        autoCompletionBinding.setHideOnEscape(true);

    }
    
    //@@author A0143378Y
    //This function takes in whatever the user has "ENTER"-ed, and save in a dictionary of words
    //These words will be in the autocomplete list of words 
    private void learnWord(String text) { 
        listOfWords.add(text);
        
        if(autoCompletionBinding != null) { 
            autoCompletionBinding.dispose();
        }
        
        autoCompletionBinding = TextFields.bindAutoCompletion(commandField, listOfWords);
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
    

    //@@author A0124797R
    private void updateTab(CommandResult result) {
        String tab = result.toString();
        updateTab(tab);
    }
    
    @Subscribe
    //@@author A0124797R
    private void handleTaskManagerChanged(TaskManagerChangedEvent event) {
        updateTabTitle();
    }
    
    //@@author A0124797R
    //updates the tab if list/upcoming command is used
    /**
     * handle the switching of tabs
     */
    private void updateTab(String result) {
        switch (result) {
            case ListCommand.MESSAGE_SUCCESS:               tabPane.getSelectionModel().select(INDEX_HOME);
                        
                                                            break;
            case UpcomingCommand.MESSAGE_SUCCESS_UPCOMING:  tabPane.getSelectionModel().select(INDEX_HOME);
                                                            break;
            case ListCommand.MESSAGE_SUCCESS_TASKS:         tabPane.getSelectionModel().select(INDEX_TASKS);
                                                            break;
            case ListCommand.MESSAGE_SUCCESS_EVENTS:        tabPane.getSelectionModel().select(INDEX_EVENTS);
                                                            break;
            case ListCommand.MESSAGE_SUCCESS_DEADLINES:     tabPane.getSelectionModel().select(INDEX_DEADLINES);
                                                            break;
            case ListCommand.MESSAGE_SUCCESS_ARCHIVES:      tabPane.getSelectionModel().select(INDEX_ARCHIVES);
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
        }else if (commandIndex >= commandHistory.size()-1) {
            return null;
        }else {
            commandIndex++;
            return commandHistory.get(commandIndex);
        }
    }
    
    public void disposeAutoCompleteBinding(){
        this.autoCompletionBinding.dispose();
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
        String prevCommand = getPrevCommandHistory();
        if (prevCommand!=null) {
            //need to wrap in runLater due to concurrency threading in JavaFX
            Platform.runLater(new Runnable() {
                public void run() {
                    commandField.setText(prevCommand);
                    commandField.positionCaret(prevCommand.length());
                }
            });
        }//else ignore
    }
    
    //@@author A0124797R
    private void restoreNextCommandText() {
        String nextCommand = getNextCommandHistory();
        //need to wrap in runLater due to concurrency threading in JavaFX
        Platform.runLater(new Runnable() {
            public void run() {
                if (nextCommand!=null) {
                    commandField.setText(nextCommand);
                    commandField.positionCaret(nextCommand.length());
                }else {
                    commandField.setText("");
                }
            }
        });
    }
}
