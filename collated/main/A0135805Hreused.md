# A0135805Hreused
###### \java\seedu\todo\commons\events\ui\HighlightTaskEvent.java
``` java
/**
 * Request to highlight in the user interface a particular task
 * displayed in the {@link TodoListView}
 */
public class HighlightTaskEvent extends BaseEvent {

    private final ImmutableTask task;

    public HighlightTaskEvent(ImmutableTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ImmutableTask getTask() {
        return task;
    }
}
```
###### \java\seedu\todo\model\tag\Tag.java
``` java
    /* Override Methods */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            // if is tag
            || (other instanceof Tag && this.tagName.toLowerCase().equals(((Tag) other).tagName.toLowerCase())); 
    }

    @Override
    public int hashCode() {
        return tagName.toLowerCase().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    /* Getters */
    public String getTagName() {
        return tagName;
    }

    /* Public Helper Methods */
    /**
     * Converts tags to a collection of tag names
     */
    public static Set<String> getLowerCaseNames(Collection<Tag> tags) {
        return tags.stream()
            .map(Tag::getTagName)
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
    }
}
```
###### \java\seedu\todo\model\tag\UniqueTagCollection.java
``` java
    /* Other Override Methods */
    @Override
    public Iterator<Tag> iterator() {
        return uniqueTagsToTasksMap.keySet().iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagCollection // instanceof handles nulls
                && this.uniqueTagsToTasksMap.equals(((UniqueTagCollection) other).uniqueTagsToTasksMap));
    }

    @Override
    public int hashCode() {
        return uniqueTagsToTasksMap.hashCode();
    }
}
```
###### \java\seedu\todo\ui\MainWindow.java
``` java
/**
 * The Main Window. Provides the basic application layout containing placeholders
 * where other JavaFX view elements can be placed.
 */
public class MainWindow extends UiPart {

    /* Constants */
    private static final String ICON = "/images/app_icon.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 780;
    private static final int MIN_WIDTH = 680;

    /* Variables */
    private Logic logic;
    private Config config;
    private UserPrefs userPrefs;
    private Model model;

    /* Independent Ui parts residing in this Ui container */
    private CommandInputView commandInputView;
    private CommandPreviewView commandPreviewView;
    private CommandFeedbackView commandFeedbackView;
    private CommandErrorView commandErrorView;
    private TodoListView todoListView;
    private HelpView helpView;
    private GlobalTagView globalTagView;

    /* Layout objects for MainWindow: Handles elements of this Ui container */
    private VBox rootLayout;
    private Scene scene;

    @FXML private AnchorPane commandInputViewPlaceholder;
    @FXML private AnchorPane commandErrorViewPlaceholder;
    @FXML private AnchorPane commandPreviewViewPlaceholder;
    @FXML private AnchorPane commandFeedbackViewPlaceholder;
    @FXML private AnchorPane globalTagViewPlaceholder;
    @FXML private AnchorPane todoListViewPlaceholder;
    @FXML private AnchorPane helpViewPlaceholder;
    @FXML private AnchorPane filterBarViewPlaceholder;
    @FXML private AnchorPane searchStatusViewPlaceholder;
    @FXML private AnchorPane emptyListPlaceholder;

    /**
     * Loads an instance of the {@link MainWindow} together with the associated view elements.
     *
     * @param primaryStage For the MainWindow to be loaded into.
     * @param config App configuration class file for some properties to be loaded.
     * @param prefs User preference for some properties to be loaded.
     * @param logic The main logic engine for commands to be executed.
     * @return An instance of the {@link MainWindow} element.
     */
    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Model model) {
        MainWindow mainWindow = UiPartLoaderUtil.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config, prefs, logic, model);
        return mainWindow;
    }

    private void configure(String appTitle, Config config, UserPrefs prefs, Logic logic, Model model) {
        //Set dependencies
        this.logic = logic;
        this.config = config;
        this.userPrefs = prefs;
        this.model = model;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
    }

    void fillInnerParts() {
        //Initialise the view elements to each placeholders.
        helpView = HelpView.load(primaryStage, helpViewPlaceholder);
        commandPreviewView = CommandPreviewView.load(primaryStage, commandPreviewViewPlaceholder);
        commandFeedbackView = CommandFeedbackView.load(primaryStage, commandFeedbackViewPlaceholder);
        commandInputView = CommandInputView.load(primaryStage, commandInputViewPlaceholder);
        commandErrorView = CommandErrorView.load(primaryStage, commandErrorViewPlaceholder);
        globalTagView = GlobalTagView.load(primaryStage, globalTagViewPlaceholder);
        todoListView = TodoListView.load(primaryStage, todoListViewPlaceholder, model.getObservableList());

        EmptyListView.load(primaryStage, emptyListPlaceholder, model.getObservableList(), model.getViewFilter());
        FilterBarView.load(primaryStage, filterBarViewPlaceholder, model.getViewFilter());
        SearchStatusView.load(primaryStage, searchStatusViewPlaceholder, model.getSearchStatus());
        
        //Constructs a command communication link between the commandXViews and logic.
        CommandController.constructLink(logic,
                commandInputView, commandPreviewView, commandFeedbackView, commandErrorView);
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

    public void hide() {
        primaryStage.hide();
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

    /* Override Methods */
    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    /* Getters */
    public TodoListView getTodoListView() {
        return this.todoListView;
    }

    public HelpView getHelpView() {
        return this.helpView;
    }

    public CommandFeedbackView getCommandFeedbackView() {
        return commandFeedbackView;
    }

    public CommandPreviewView getCommandPreviewView() {
        return commandPreviewView;
    }
    
    public GlobalTagView getGlobalTagView() {
        return globalTagView;
    }
}
```
###### \java\seedu\todo\ui\Ui.java
``` java
/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

}
```
###### \java\seedu\todo\ui\UiManager.java
``` java
/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/app_icon.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;
    private Model model; 

    public UiManager(Logic logic, Config config, UserPrefs prefs, Model model) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
        this.model = model;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic, model);
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

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("style/DefaultStyle.css");
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
```
###### \java\seedu\todo\ui\view\CommandErrorView.java
``` java
    /**
     * Loads and initialise the feedback view element to the placeHolder
     * @param primaryStage of the application
     * @param placeHolder where the view element {@link #errorViewBox} should be placed
     * @return an instance of this class
     */
    public static CommandErrorView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandErrorView errorView = UiPartLoaderUtil
                .loadUiPart(primaryStage, placeHolder, new CommandErrorView());
        errorView.configureLayout();
        errorView.hideCommandErrorView();
        return errorView;
    }

```
###### \java\seedu\todo\ui\view\CommandFeedbackView.java
``` java
    /**
     * Loads and initialise the feedback view element to the placeholder.
     *
     * @param primaryStage The main stage of the application.
     * @param placeholder The place where the view element {@link #textContainer} should be placed.
     * @return An instance of this class.
     */
    public static CommandFeedbackView load(Stage primaryStage, AnchorPane placeholder) {
        CommandFeedbackView feedbackView = UiPartLoaderUtil
                .loadUiPart(primaryStage, placeholder, new CommandFeedbackView());
        feedbackView.configureLayout();
        return feedbackView;
    }

    /**
     * Configures the UI layout of {@link CommandFeedbackView}.
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(textContainer, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandFeedbackLabel, 0.0, 0.0, 0.0, 0.0);
    }

```
###### \java\seedu\todo\ui\view\CommandInputView.java
``` java
    /**
     * Loads and initialise the input view element to the placeHolder
     * @param primaryStage of the application
     * @param placeHolder where the view element {@link #commandInputPane} should be placed
     * @return an instance of this class
     */
    public static CommandInputView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandInputView commandInputView = UiPartLoaderUtil
                .loadUiPart(primaryStage, placeHolder, new CommandInputView());
        commandInputView.configureLayout();
        commandInputView.configureProperties();
        return commandInputView;
    }

    /**
     * Configure the UI layout of {@link CommandInputView}
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(commandInputPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

```
###### \java\seedu\todo\ui\view\TaskCardView.java
``` java
    /*Layout Declarations*/
    @FXML private VBox taskCard;
    @FXML private ImageView pinImage;
    @FXML private Label titleLabel;
    @FXML private Label typeLabel, moreInfoLabel;
    @FXML private Label descriptionLabel, dateLabel, locationLabel;
    @FXML private HBox descriptionBox, dateBox, locationBox;
    @FXML private FlowPane titleFlowPane;
    
    /* Variables */
    private ImmutableTask task;
    private int displayedIndex;
    private TimeUtil timeUtil = new TimeUtil();

    /* Default Constructor */
    private TaskCardView(){
    }

    /* Initialisation Methods */
    /**
     * Loads and initialise one cell of the task in the to-do list ListView.
     * @param task to be displayed on the cell
     * @param displayedIndex index to be displayed on the card itself to the user
     * @return an instance of this class
     */
    public static TaskCardView load(ImmutableTask task, int displayedIndex){
        TaskCardView taskListCard = new TaskCardView();
        taskListCard.task = task;
        taskListCard.displayedIndex = displayedIndex;
        taskCardMap.put(task, taskListCard);
        return UiPartLoaderUtil.loadUiPart(taskListCard);
    }

    /**
     * Initialise all the view elements in a task card.
     */
    @FXML
    public void initialize() {
        displayEverythingElse();
        displayTags();
        displayTimings();
        setStyle();
        setTimingAutoUpdate();
        initialiseCollapsibleView();
    }

```
###### \java\seedu\todo\ui\view\TodoListView.java
``` java
    /**
     * Default Constructor for {@link TodoListView}
     */
    public TodoListView() {
        super();
    }

    /**
     * Loads and initialise the {@link TodoListView} to the placeHolder.
     *
     * @param primaryStage of the application
     * @param placeHolder where the view element {@link #todoListView} should be placed
     * @param todoList the list of tasks to be displayed
     * @return an instance of this class
     */
    public static TodoListView load(Stage primaryStage, AnchorPane placeHolder,
            ObservableList<ImmutableTask> todoList) {
        
        TodoListView todoListView = UiPartLoaderUtil.loadUiPart(primaryStage, placeHolder, new TodoListView());
        todoListView.configure(todoList);
        return todoListView;
    }

```
###### \java\seedu\todo\ui\view\TodoListView.java
``` java
    /**
     * Models a Task Card as a single ListCell of the ListView
     */
    private class TodoListViewCell extends ListCell<ImmutableTask> {

        /* Override Methods */
        @Override
        protected void updateItem(ImmutableTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                TaskCardView taskCardView = TaskCardView.load(task, FxViewUtil.convertToUiIndex(getIndex()));
                setGraphic(taskCardView.getLayout());
                setTaskCardStyleProperties(taskCardView);
            }
        }

```
