# generated
###### \src\main\java\seedu\task\logic\commands\CommandResult.java
``` java
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }

}
```
###### \src\main\java\seedu\task\logic\commands\ExitCommand.java
``` java
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Task Manager as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute(boolean isUndo) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
```
###### \src\main\java\seedu\task\logic\commands\HelpCommand.java
``` java
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand() {}

    @Override
    public CommandResult execute(boolean isUndo) {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }
}
```
###### \src\main\java\seedu\task\logic\commands\IncorrectCommand.java
``` java
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(feedbackToUser);
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }

}

```
###### \src\main\java\seedu\task\logic\commands\ListCommand.java
``` java
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListCommand() {}

    @Override
    public CommandResult execute(boolean isUndo) {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }
}
```
###### \src\main\java\seedu\task\logic\commands\SelectCommand.java
``` java
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(boolean isUndo) {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
```
###### \src\main\java\seedu\task\model\ReadOnlyTaskManager.java
``` java
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
```
###### \src\main\java\seedu\task\model\tag\Tag.java
``` java
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public String tagName;

    public Tag() {
    }

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidTagName(name)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = name;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '#' + tagName;
    }

}
```
###### \src\main\java\seedu\task\model\tag\UniqueTagList.java
``` java
 */
public class UniqueTagList implements Iterable<Tag> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {

        private static final long serialVersionUID = 6350618164265958021L;

        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueTagList() {}

    /**
     * Varargs/array constructor, enforces no nulls or duplicates.
     */
    public UniqueTagList(Tag... tags) throws DuplicateTagException {
        assert !CollectionUtil.isAnyNull((Object[]) tags);
        final List<Tag> initialTags = Arrays.asList(tags);
        if (!CollectionUtil.elementsAreUnique(initialTags)) {
            throw new DuplicateTagException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * java collections constructor, enforces no null or duplicate elements.
     */
    public UniqueTagList(Collection<Tag> tags) throws DuplicateTagException {
        CollectionUtil.assertNoNullElements(tags);
        if (!CollectionUtil.elementsAreUnique(tags)) {
            throw new DuplicateTagException();
        }
        internalList.addAll(tags);
    }

    /**
     * java set constructor, enforces no nulls.
     */
    public UniqueTagList(Set<Tag> tags) {
        CollectionUtil.assertNoNullElements(tags);
        internalList.addAll(tags);
    }

    /**
     * Copy constructor, insulates from changes in source.
     */
    public UniqueTagList(UniqueTagList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * All tags in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<Tag> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        this.internalList.clear();
        this.internalList.addAll(replacement.internalList);
    }

    /**
     * Adds every tag from the argument list that does not yet exist in this list.
     */
    public void mergeFrom(UniqueTagList tags) {
        final Set<Tag> alreadyInside = this.toSet();
        for (Tag tag : tags) {
            if (!alreadyInside.contains(tag)) {
                internalList.add(tag);
            }
        }
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Tag toAdd) throws DuplicateTagException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Tag> getInternalList() {
        return internalList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTagList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \src\main\java\seedu\task\model\UserPrefs.java
``` java
 */
public class UserPrefs {

    public GuiSettings guiSettings;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setGuiSettings(500, 500, 0, 0);
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof UserPrefs)){ //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs)other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString(){
        return guiSettings.toString();
    }

}
```
###### \src\main\java\seedu\task\ui\CommandBox.java
``` java
public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;

    private Logic logic;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultDisplay resultDisplay, Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic);
        commandBox.addToPlaceholder();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }


    @FXML
    private void handleCommandInputChanged() {
        //Take a copy of the command text
        previousCommandTest = commandTextField.getText();

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }


    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event,"Invalid command: " + previousCommandTest));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
    }

    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(previousCommandTest);
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }

}
```
###### \src\main\java\seedu\task\ui\HelpWindow.java
``` java
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String USERGUIDE_URL =
            "https://github.com/CS2103AUG2016-T17-C3/Task-Manager/blob/master/docs/UserGuide.md";

    private AnchorPane mainPane;

    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(dialogStage, ICON);

        WebView browser = new WebView();
        browser.getEngine().load(USERGUIDE_URL);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(browser);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
```
###### \src\main\java\seedu\task\ui\MainWindow.java
``` java
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/task_manager_32.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    private Logic logic;

    // Independent Ui parts residing in this Ui container

    private TaskListPanel taskListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String taskManagerName;

    @FXML
    private AnchorPane browserPlaceholder;

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
        mainWindow.configure(config.getAppTitle(), config.getTaskManagerName(), config, prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, String taskManagerName, Config config, UserPrefs prefs,
                           Logic logic) {

        //Set dependencies
        this.logic = logic;
        this.taskManagerName = taskManagerName;
        this.config = config;
        this.userPrefs = prefs;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        setAccelerators();
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts() {

        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPlaceholder(), logic.getFilteredTaskList());
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getTaskManagerFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
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

    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
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
```
###### \src\main\java\seedu\task\ui\ResultDisplay.java
``` java
 */
public class ResultDisplay extends UiPart {
    public static final String RESULT_DISPLAY_ID = "resultDisplay";
    private static final String STATUS_BAR_STYLE_SHEET = "result-display";
    private TextArea resultDisplayArea;
    private final StringProperty displayed = new SimpleStringProperty("");

    private static final String FXML = "ResultDisplay.fxml";

    private AnchorPane placeHolder;

    private AnchorPane mainPane;

    public static ResultDisplay load(Stage primaryStage, AnchorPane placeHolder) {
        ResultDisplay statusBar = UiPartLoader.loadUiPart(primaryStage, placeHolder, new ResultDisplay());
        statusBar.configure();
        return statusBar;
    }

    public void configure() {
        resultDisplayArea = new TextArea();
        resultDisplayArea.setEditable(false);
        resultDisplayArea.setId(RESULT_DISPLAY_ID);
        resultDisplayArea.getStyleClass().removeAll();
        resultDisplayArea.getStyleClass().add(STATUS_BAR_STYLE_SHEET);
        resultDisplayArea.setStyle("-fx-font: 20 helvetica;");
        resultDisplayArea.setText("");
        resultDisplayArea.textProperty().bind(displayed);
        FxViewUtil.applyAnchorBoundaryParameters(resultDisplayArea, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(resultDisplayArea);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public void postMessage(String message) {
        displayed.setValue(message);
    }

}
```
###### \src\main\java\seedu\task\ui\StatusBarFooter.java
``` java
 */
public class StatusBarFooter extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);
    private StatusBar syncStatus;
    private StatusBar saveLocationStatus;

    private GridPane mainPane;

    @FXML
    private AnchorPane saveLocStatusBarPane;

    @FXML
    private AnchorPane syncStatusBarPane;

    private AnchorPane placeHolder;

    private static final String FXML = "StatusBarFooter.fxml";

    public static StatusBarFooter load(Stage stage, AnchorPane placeHolder, String saveLocation) {
        StatusBarFooter statusBarFooter = UiPartLoader.loadUiPart(stage, placeHolder, new StatusBarFooter());
        statusBarFooter.configure(saveLocation);
        return statusBarFooter;
    }

    public void configure(String saveLocation) {
        addMainPane();
        addSyncStatus();
        setSyncStatus("Not updated yet in this session");
        addSaveLocation();
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }

    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

    private void addSaveLocation() {
        this.saveLocationStatus = new StatusBar();
        FxViewUtil.applyAnchorBoundaryParameters(saveLocationStatus, 0.0, 0.0, 0.0, 0.0);
        saveLocStatusBarPane.getChildren().add(saveLocationStatus);
    }

    private void setSyncStatus(String status) {
        this.syncStatus.setText(status);
    }

    private void addSyncStatus() {
        this.syncStatus = new StatusBar();
        FxViewUtil.applyAnchorBoundaryParameters(syncStatus, 0.0, 0.0, 0.0, 0.0);
        syncStatusBarPane.getChildren().add(syncStatus);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        String lastUpdated = (new Date()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }
}
```
###### \src\main\java\seedu\task\ui\TaskListPanel.java
``` java
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> taskListView;

    public TaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
        
        
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
       
        placeHolderPane.getChildren().add(panel);
        
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));

            }
        });
        
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
                
                
                
            }
        }
    }

}
```
###### \src\main\java\seedu\task\ui\Ui.java
``` java
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

}
```
###### \src\main\java\seedu\task\ui\UiManager.java
``` java
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/task_manager_32.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        
    }

}
```
###### \src\main\java\seedu\task\ui\UiPart.java
``` java
 */
public abstract class UiPart {

    /**
     * The primary stage for the UI Part.
     */
    Stage primaryStage;

    public UiPart(){

    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event){
        EventsCenter.getInstance().post(event);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Override this method to receive the main Node generated while loading the view from the .fxml file.
     * @param node
     */
    public abstract void setNode(Node node);

    /**
     * Override this method to return the name of the fxml file. e.g. {@code "MainWindow.fxml"}
     * @return
     */
    public abstract String getFxmlPath();

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    /**
     * Creates a modal dialog.
     * @param title Title of the dialog.
     * @param parentStage The owner stage of the dialog.
     * @param scene The scene that will contain the dialog.
     * @return the created dialog, not yet made visible.
     */
    protected Stage createDialogStage(String title, Stage parentStage, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    /**
     * Sets the given image as the icon for the primary stage of this UI Part.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    protected void setIcon(String iconSource) {
        primaryStage.getIcons().add(AppUtil.getImage(iconSource));
    }

    /**
     * Sets the given image as the icon for the given stage.
     * @param stage
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    protected void setIcon(Stage stage, String iconSource) {
        stage.getIcons().add(AppUtil.getImage(iconSource));
    }

    /**
     * Sets the placeholder for UI parts that reside inside another UI part.
     * @param placeholder
     */
    public void setPlaceholder(AnchorPane placeholder) {
        //Do nothing by default.
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
```
###### \src\main\java\seedu\task\ui\UiPartLoader.java
``` java
 */
public class UiPartLoader {
    private final static String FXML_FILE_FOLDER = "/view/";

    public static <T extends UiPart> T loadUiPart(Stage primaryStage, T controllerSeed) {
        return loadUiPart(primaryStage, null, controllerSeed);
    }

    /**
     * Returns the ui class for a specific UI Part.
     *
     * @param primaryStage The primary stage for the view.
     * @param placeholder The placeholder where the loaded Ui Part is added.
     * @param sampleUiPart The sample of the expected UiPart class.
     * @param <T> The type of the UiPart
     */
    @SuppressWarnings("unchecked")
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, AnchorPane placeholder, T sampleUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + sampleUiPart.getFxmlPath()));
        Node mainNode = loadLoader(loader, sampleUiPart.getFxmlPath());
        UiPart controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setPlaceholder(placeholder);
        controller.setNode(mainNode);
        return (T)controller;
    }

    /**
     * Returns the ui class for a specific UI Part.
     *
     * @param seedUiPart The UiPart object to be used as the ui.
     * @param <T> The type of the UiPart
     */

    public static <T extends UiPart> T loadUiPart(T seedUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + seedUiPart.getFxmlPath()));
        loader.setController(seedUiPart);
        loadLoader(loader, seedUiPart.getFxmlPath());
        return seedUiPart;
    }


    private static Node loadLoader(FXMLLoader loader, String fxmlFileName) {
        try {
            return loader.load();
        } catch (Exception e) {
            String errorMessage = "FXML Load Error for " + fxmlFileName;
            throw new RuntimeException(errorMessage, e);
        }
    }

}
```
