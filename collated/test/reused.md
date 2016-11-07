# reused
###### \java\guitests\guihandles\GuiHandle.java
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
        return node.isPresent() ? node.get() : null;
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
###### \java\guitests\guihandles\MainGuiHandle.java
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

    public CommandErrorViewHandle getCommandErrorView() {
        return new CommandErrorViewHandle(guiRobot, primaryStage);
    }

    public HelpViewHandle getHelpView() {
        return new HelpViewHandle(guiRobot, primaryStage);
    }

    public GlobalTagViewHandle getGlobalTagView() {
        return new GlobalTagViewHandle(guiRobot, primaryStage);
    }

    public FilterBarViewHandle getFilterBarView() {
        return new FilterBarViewHandle(guiRobot, primaryStage);
    }

    public SearchStatusViewHandle getSearchStatusView() {
        return new SearchStatusViewHandle(guiRobot, primaryStage);
    }
}
```
###### \java\guitests\GuiRobot.java
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
###### \java\guitests\TodoListGuiTest.java
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
    protected MainGuiHandle mainGui;
    protected TodoListViewHandle todoListView;
    protected CommandInputViewHandle commandInputView;
    protected CommandPreviewViewHandle commandPreviewView;
    protected CommandFeedbackViewHandle commandFeedbackView;
    protected CommandErrorViewHandle commandErrorView;
    protected HelpViewHandle helpView;
    protected GlobalTagViewHandle globalTagView;
    protected FilterBarViewHandle filterBarView;
    protected SearchStatusViewHandle searchStatusView;

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
            commandErrorView = mainGui.getCommandErrorView();
            helpView = mainGui.getHelpView();
            globalTagView = mainGui.getGlobalTagView();
            filterBarView = mainGui.getFilterBarView();
            searchStatusView = mainGui.getSearchStatusView();
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

```
###### \java\guitests\TodoListGuiTest.java
``` java
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
     * Copies the list of ImmutableTask stored inside the {@link TodoListView}
     * into {@link #previousTasksFromView}, for history taking.
     */
    protected void updatePreviousTaskListFromView() {
        previousTasksFromView = new ArrayList<>(todoListView.getImmutableTaskList());
    }

```
