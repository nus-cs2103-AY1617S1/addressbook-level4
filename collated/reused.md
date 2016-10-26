# reused
###### \build\resources\main\style\DefaultStyle.css
``` css
/**Modern UI***/
/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: white;
    -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}
```
###### \src\main\java\seedu\todo\ui\MainWindow.java
``` java
/**
 * The Main Window. Provides the basic application layout containing placeholders
 * where other JavaFX view elements can be placed.
 */
public class MainWindow extends UiPart {

    /* Constants */
    private static final String ICON = "/images/app_icon.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 400;
    private static final int MIN_WIDTH = 580;

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

    /* Layout objects for MainWindow: Handles elements of this Ui container */
    private VBox rootLayout;
    private Scene scene;

    @FXML private AnchorPane commandInputViewPlaceholder;
    @FXML private AnchorPane commandErrorViewPlaceholder;
    @FXML private AnchorPane commandPreviewViewPlaceholder;
    @FXML private AnchorPane commandFeedbackViewPlaceholder;
    @FXML private AnchorPane todoListViewPlaceholder;
    @FXML private AnchorPane helpViewPlaceholder;
    @FXML private AnchorPane filterBarViewPlaceholder;
    @FXML private AnchorPane searchStatusViewPlaceholder;

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
        todoListView = TodoListView.load(primaryStage, todoListViewPlaceholder, model.getObservableList());
        helpView = HelpView.load(primaryStage, helpViewPlaceholder);
        commandPreviewView = CommandPreviewView.load(primaryStage, commandPreviewViewPlaceholder);
        commandFeedbackView = CommandFeedbackView.load(primaryStage, commandFeedbackViewPlaceholder);
        commandInputView = CommandInputView.load(primaryStage, commandInputViewPlaceholder);
        commandErrorView = CommandErrorView.load(primaryStage, commandErrorViewPlaceholder);

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
}
```
###### \src\main\java\seedu\todo\ui\Ui.java
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
###### \src\main\java\seedu\todo\ui\UiManager.java
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
###### \src\main\java\seedu\todo\ui\UiManager.java
``` java
    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

}
```
###### \src\main\java\seedu\todo\ui\UiPart.java
``` java
/**
 * Base class for UI parts.
 * A 'UI part' represents a distinct part of the UI. e.g. Windows, dialogs, panels, status bars, etc.
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
###### \src\main\java\seedu\todo\ui\util\FxViewUtil.java
``` java
    public static void applyAnchorBoundaryParameters(Node node, double left, double right, double top, double bottom) {
        AnchorPane.setBottomAnchor(node, bottom);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setTopAnchor(node, top);
    }

```
###### \src\main\java\seedu\todo\ui\util\UiPartLoaderUtil.java
``` java
/**
 * A utility class to load UiParts from FXML files.
 */
public class UiPartLoaderUtil {
    private final static String FXML_FILE_FOLDER = "/view/";

    public static <T extends UiPart> T loadUiPart(Stage primaryStage, T controllerSeed) {
        return loadUiPart(primaryStage, null, controllerSeed);
    }

    /**
     * Returns the ui class for a specific UI Part.
     * If a placeholder is supplied, the sampleUiPart will be attached to the placeholder automatically.
     *
     * @param primaryStage The primary stage for the view.
     * @param placeholder The placeholder where the loaded Ui Part is added.
     * @param sampleUiPart The sample of the expected UiPart class.
     * @param <T> The type of the UiPart
     */
    public static <T extends UiPart> T loadUiPart(Stage primaryStage, AnchorPane placeholder, T sampleUiPart) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_FILE_FOLDER + sampleUiPart.getFxmlPath()));
        Node mainNode = loadLoader(loader, sampleUiPart.getFxmlPath());
        UiPart controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setPlaceholder(placeholder);
        controller.setNode(mainNode);
        attachToPlaceholder(placeholder, mainNode);
        return (T) controller;
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

```
###### \src\main\resources\style\DefaultStyle.css
``` css
/**Modern UI***/
/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: white;
    -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}
```
###### \src\test\java\guitests\guihandles\GuiHandle.java
``` java
/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
    protected static final int GUI_SLEEP_DURATION = 200;
    protected static final int GUI_ENTER_SLEEP_DURATION = 200;

    protected final GuiRobot guiRobot;
    protected final Stage primaryStage;
    protected final String stageTitle;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public GuiHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        this.guiRobot = guiRobot;
        this.primaryStage = primaryStage;
        this.stageTitle = stageTitle;
        focusOnSelf();
    }

    public void focusOnWindow(String stageTitle) {
        logger.info("Focusing " + stageTitle);
        java.util.Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            logger.warning("Can't find stage " + stageTitle + ", Therefore, aborting focusing");
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> window.get().requestFocus());
        logger.info("Finishing focus " + stageTitle);
    }

    protected Node getNode(String query) {
        com.google.common.base.Optional<Node> node = guiRobot.lookup(query).tryQuery();
        return (node.isPresent()) ? node.get()
                                  : null;
    }

    public void pressSpace() {
        guiRobot.type(KeyCode.SPACE).sleep(GUI_ENTER_SLEEP_DURATION);
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER).sleep(GUI_ENTER_SLEEP_DURATION);
    }

    public void focusOnSelf() {
        if (stageTitle != null) {
            focusOnWindow(stageTitle);
        }
    }

    public void focusOnMainApp() {
        this.focusOnWindow(TestApp.APP_TITLE);
    }

    public void closeWindow() {
        java.util.Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> ((Stage)window.get()).close());
        focusOnMainApp();
    }
}
```
###### \src\test\java\guitests\guihandles\MainGuiHandle.java
``` java
/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    //TODO: Where should the TestApp.APP_TITLE be stored?
    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TodoListViewHandle getTodoListView() {
        return new TodoListViewHandle(guiRobot, primaryStage);
    }

    public CommandInputViewHandle getCommandInputView() {
        return new CommandInputViewHandle(guiRobot, primaryStage);
    }

    public CommandFeedbackViewHandle getCommandFeedbackView() {
        return new CommandFeedbackViewHandle(guiRobot, primaryStage);
    }

    public CommandPreviewViewHandle getCommandPreviewView() {
        return new CommandPreviewViewHandle(guiRobot, primaryStage);
    }

    @Deprecated
    public PersonListPanelHandle getPersonListPanel() {
        return new PersonListPanelHandle(guiRobot, primaryStage);
    }

    @Deprecated
    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }

}
```
###### \src\test\java\guitests\GuiRobot.java
``` java
/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    public GuiRobot push(KeyCodeCombination keyCodeCombination){
        return (GuiRobot) super.push(TestUtil.scrub(keyCodeCombination));
    }

}
```
###### \src\test\java\guitests\TodoListGuiTest.java
``` java
/**
 * A GUI Test class for Uncle Jim's Discount To-do List.
 */
public abstract class TodoListGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule public TestName name = new TestName();

    //Stores the list of immutable task first started with the application.
    protected List<ImmutableTask> initialTaskData;

    //Stores a copy of immutable task lists that are currently on display.
    protected List<ImmutableTask> previousTasksFromView;

    private TestApp testApp;

    /*
     * Handles to GUI elements present at the start up are created in advance
     * for easy access from child classes.
     */
    //TODO: Attach new Handles here!
    protected MainGuiHandle mainGui;
    protected TodoListViewHandle todoListView;
    protected CommandInputViewHandle commandInputView;
    protected CommandPreviewViewHandle commandPreviewView;
    protected CommandFeedbackViewHandle commandFeedbackView;

    private Stage stage;

    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        //TODO: Set up new handles them!
        FxToolkit.setupStage((stage) -> {
            this.stage = stage;
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            todoListView = mainGui.getTodoListView();
            commandInputView = mainGui.getCommandInputView();
            commandFeedbackView = mainGui.getCommandFeedbackView();
            commandPreviewView = mainGui.getCommandPreviewView();
        });
        // EventsCenter.clearSubscribers();
        /*
         * A new instance of the to-do list data structure ImmutableTodoList
         * that will be injected into the TestApp, which inherits from MainApp
         */
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TodoList getInitialData() {
        TodoList todoList = TestUtil.generateEmptyTodoList(getDataFileLocation());
        initialTaskData = TaskFactory.list();
        TestUtil.loadTodoListWithData(todoList, initialTaskData);
        return todoList;
    }

    /**
     * Override {@link #getInitialData()} and use this method to set a variable initial
     * task size between {@code lowerBound} and {@code upperBound} inclusive.
     */
    protected TodoList getInitialDataHelper(int lowerBound, int upperBound) {
        TodoList todoList = TestUtil.generateEmptyTodoList(getDataFileLocation());
        initialTaskData = TaskFactory.list(lowerBound, upperBound);
        TestUtil.loadTodoListWithData(todoList, initialTaskData);
        return todoList;
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Enters the command via the {@link CommandInputView} but does not execute.
     */
    protected void enterCommand(String commandText) {
        commandInputView.enterCommand(commandText);
        mainGui.pressSpace();
    }

    /**
     * Executes the command via the {@link CommandInputView}
     */
    protected void runCommand(String commandText) {
        commandInputView.runCommand(commandText);
    }

    /**
     * Asserts the message shown in the {@link CommandFeedbackView}
     * is same as the given {@code expected} string.
     */
    protected void assertFeedbackMessage(String expected) {
        assertEquals(expected, commandFeedbackView.getText());
    }

    /**
     * Copies the list of ImmutableTask stored inside the {@link TodoListView}
     * into {@link #previousTasksFromView}, for history taking.
     */
    protected void updatePreviousTaskListFromView() {
        previousTasksFromView = new ArrayList<>(todoListView.getImmutableTaskList());
    }
}
```
