# A0132157M reused
###### /java/guitests/AddCommandTest.java
``` java
        TestEvent[] currentEventList = ed.getTypicalEvent();
        TestEvent eventToAdd = TypicalTestEvent.e6;
        assertAddEventSuccess(eventToAdd, currentEventList);
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        
        //add one deadline
```
###### /java/guitests/AddCommandTest.java
``` java
        TestDeadline[] currentDeadlineList = dd.getTypicalDeadline();
        TestDeadline ddToAdd = TypicalTestDeadline.d6;
        assertAddDeadlineSuccess(ddToAdd, currentDeadlineList);
        currentDeadlineList = TestUtil.addDeadlinesToList(currentDeadlineList, ddToAdd);

        //add another task
```
###### /java/guitests/AddCommandTest.java
``` java
        taskToAdd = TypicalTestTask.a7;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add another event
```
###### /java/guitests/AddCommandTest.java
``` java
        eventToAdd = TypicalTestEvent.e7;
        assertAddEventSuccess(eventToAdd, currentEventList);
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        
        //add another deadline
```
###### /java/guitests/AddCommandTest.java
``` java
        ddToAdd = TypicalTestDeadline.d7;
        assertAddDeadlineSuccess(ddToAdd, currentDeadlineList);
        currentDeadlineList = TestUtil.addDeadlinesToList(currentDeadlineList, ddToAdd);

        //add duplicate task
```
###### /java/guitests/AddCommandTest.java
``` java
        commandBox.runCommand(TypicalTestTask.a6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add duplicate event
```
###### /java/guitests/AddCommandTest.java
``` java
        commandBox.runCommand(TypicalTestEvent.e6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(eventListPanel.isListMatching(currentEventList));
        
        //add duplicate deadline
```
###### /java/guitests/AddCommandTest.java
``` java
        commandBox.runCommand(TypicalTestDeadline.d6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(deadlineListPanel.isListMatching(currentEventList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTask.a1);
        //assertAddEventSuccess(ed.e1);

        //invalid command
        commandBox.runCommand("adds assignment 66");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        //LogsCenter.getLogger(AddCommandTest.class).info("task.length add command: " + taskToAdd.getName().name.toString());

        commandBox.runCommand(taskToAdd.getAddCommand());
        //LogsCenter.getLogger(AddCommandTest.class).info("XXX: " + taskToAdd.getName().name.toString());
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getName().name);

        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
```
###### /java/guitests/ClearCommandTest.java
``` java
    public void clear() throws IllegalValueException {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicaltasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.a6.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.a6));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("TodoList has been cleared!");
    }
}
```
###### /java/guitests/DeleteCommandTest.java
``` java
    public void delete() throws IllegalValueException {

        //delete the first in the list
        TestTask[] currentList = td.getTypicaltasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removetaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removetaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removetaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete todo " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
```
###### /java/guitests/FindCommandTest.java
``` java
    public void find_nonEmptyList() {
        assertFindResult("find priority 999"); //no results
        assertFindResult("find project", td.a2, td.a5); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find project 1",td.a2);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find assignment 99"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findassignment");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
```
###### /java/guitests/guihandles/DeadlineListPanelHandle.java
``` java
public class DeadlineListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#deadlineCard";

    private static final String task_LIST_VIEW_ID = "#deadlineListView";

    public DeadlineListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedtasks() {
        ListView<ReadOnlyTask> taskList = getListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getListView() {
        return (ListView<ReadOnlyTask>) getNode(task_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, tasks); //something wrong, always return false!!!
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()){
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().name.equals(tasks[i].getName().name)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndDeadline(getDeadlineCardHandle(startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public DeadlineCardHandle navigateToDeadline(String readOnlyTask) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView().getItems().stream().filter(p -> p.getName().name.equals(readOnlyTask)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task not found: " + readOnlyTask);
        }

        return navigateToDeadline(task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public DeadlineCardHandle navigateToDeadline(ReadOnlyTask Deadline) {
        int index = getDeadlineIndex(Deadline); //SOmething wrong. Always return 0

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        }); 
        guiRobot.sleep(100);
        return getDeadlineCardHandle(Deadline);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getDeadlineIndex(ReadOnlyTask targettask) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if(tasksInList.get(i).getName().equals(targettask.getName().name)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public ReadOnlyTask getDeadline(int index) {
        return getListView().getItems().get(index);
    }

    public DeadlineCardHandle getDeadlineCardHandle(int task) {
        return getDeadlineCardHandle(new Deadline(getListView().getItems().get(task)));
    }

    public DeadlineCardHandle getDeadlineCardHandle(ReadOnlyTask Deadline) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> DeadlineCardNode = nodes.stream()
                .filter(n -> new DeadlineCardHandle(guiRobot, primaryStage, n).isSameDeadline(Deadline))
                .findFirst();
        if (DeadlineCardNode.isPresent()) {
            return new DeadlineCardHandle(guiRobot, primaryStage, DeadlineCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfDeadlines() {
        return getListView().getItems().size();
    }
}
```
###### /java/guitests/guihandles/EventListPanelHandle.java
``` java
public class EventListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#eventCard";

    private static final String task_LIST_VIEW_ID = "#eventListView";

    public EventListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedtasks() {
        ListView<ReadOnlyTask> taskList = getListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getListView() {
        return (ListView<ReadOnlyTask>) getNode(task_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, tasks); //something wrong, always return false!!!
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()){
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().name.equals(tasks[i].getName().name)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndEvent(getEventCardHandle(startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public EventCardHandle navigateToevent(String readOnlyTask) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView().getItems().stream().filter(p -> p.getName().name.equals(readOnlyTask)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task not found: " + readOnlyTask);
        }

        return navigateToevent(task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public EventCardHandle navigateToevent(ReadOnlyTask event) {
        int index = geteventIndex(event); //SOmething wrong. Always return 0

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        }); 
        guiRobot.sleep(100);
        return getEventCardHandle(event);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int geteventIndex(ReadOnlyTask targettask) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if(tasksInList.get(i).getName().equals(targettask.getName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public ReadOnlyTask getEvent(int index) {
        return getListView().getItems().get(index);
    }

    public EventCardHandle getEventCardHandle(int task) {
        return getEventCardHandle(new Event(getListView().getItems().get(task)));
    }

    public EventCardHandle getEventCardHandle(ReadOnlyTask event) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> eventCardNode = nodes.stream()
                .filter(n -> new EventCardHandle(guiRobot, primaryStage, n).isSameEvent(event))
                .findFirst();
        if (eventCardNode.isPresent()) {
            return new EventCardHandle(guiRobot, primaryStage, eventCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfEvents() {
        return getListView().getItems().size();
    }
}
```
###### /java/guitests/guihandles/TaskCardHandle.java
``` java
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String ENDDATE_FIELD_ID = "#endDate";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DONE_FIELD_ID = "#isDone";


    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }
    public String getDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID);
    }
    public String getEndDate() {
        return getTextFromLabel(ENDDATE_FIELD_ID);
    }
    

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getDone() {
        return getTextFromLabel(DONE_FIELD_ID);
    }


    public boolean isSametask(ReadOnlyTask task){
        return getName().equals(task.getName().name);// && getDate().equals(task.getDate().date)
                //&& getPriority().equals(task.getPriority().priority);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getDate().equals(handle.getDate())
                    && getEndDate().equals(handle.getEndDate())
                    && getPriority().equals(handle.getPriority())
                    && getDone().equals(handle.getDone());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getDate() + " " + getEndDate() + " " + getPriority() + " " + getDone();
    }
}
```
###### /java/guitests/guihandles/TaskListPanelHandle.java
``` java
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#name";

    private static final String task_LIST_VIEW_ID = "#todoListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedtasks() {
        ListView<ReadOnlyTask> taskList = getListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getListView() {
        return (ListView<ReadOnlyTask>) getNode(task_LIST_VIEW_ID);
        
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(Todo... tasks) {
        return this.isListMatching(0, tasks); //something wrong, always return false!!!
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()){
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().name.equals(tasks[i].getName().name)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }

```
###### /java/guitests/ListGuiTest.java
``` java
public abstract class ListGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTask td = new TypicalTestTask();
    protected TypicalTestEvent ed = new TypicalTestEvent();
    protected TypicalTestDeadline dd = new TypicalTestDeadline();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle taskListPanel;
    protected EventListPanelHandle eventListPanel;
    protected DeadlineListPanelHandle deadlineListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
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
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            mainMenu = mainGui.getMainMenu();
            taskListPanel = mainGui.getTaskListPanel();
            eventListPanel = mainGui.getEventListPanel();
            deadlineListPanel = mainGui.getDeadlineListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
```
###### /java/guitests/ListGuiTest.java
``` java
    protected TaskList getInitialData() {
        TaskList ab = TestUtil.generateEmptyTodoList();
        //TaskList cd = TestUtil.generateEmptyEventList();
        //TaskList ef = TestUtil.generateEmptyDeadlineList();
        TypicalTestTask.loadTodoListWithSampleData(ab);
        //TypicalTestEvent.loadEventListWithSampleData(cd);
        //TypicalTestDeadline.loadDeadlineListWithSampleData(ef);
        return ab;
    }


    /**
     * Override this in child classes to set the data file location.
     * @return
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(Todo task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }
```
###### /java/guitests/ListGuiTest.java
``` java
    public void assertEventMatching(Event event, EventCardHandle card) {
        assertTrue(TestUtil.compareCardAndEvent(card, event));
    }
```
###### /java/guitests/ListGuiTest.java
``` java
    public void assertDeadlineMatching(Deadline event, DeadlineCardHandle card) {
        assertTrue(TestUtil.compareCardAndDeadline(card, event));
    }
    

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfTask = taskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTask);
    }
```
###### /java/guitests/ListGuiTest.java
``` java
    protected void assertEventListSize(int size) {
        int numberOfTask = eventListPanel.getNumberOfEvents();
        assertEquals(size, numberOfTask);
    }
```
###### /java/guitests/ListGuiTest.java
``` java
    protected void assertDeadlineListSize(int size) {
        int numberOfTask = deadlineListPanel.getNumberOfDeadlines();
        assertEquals(size, numberOfTask);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     * @param expected
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
}
```
###### /java/seedu/todoList/commons/util/XmlUtilTest.java
``` java
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();

        XmlSerializableTodoList dataToWrite = new XmlSerializableTodoList(new TaskList());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTodoList.class);
        assertEquals((new TaskList(dataToWrite)).toString(),(new TaskList(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons


        TodoListBuilder builder = new TodoListBuilder(new TaskList());
        dataToWrite = new XmlSerializableTodoList(builder.withTask(TestUtil.generateSampletaskData().get(0)).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTodoList.class);

        assertEquals((new TaskList(dataToWrite)).toString(),(new TaskList(dataFromFile)).toString());
    }
}
```
###### /java/seedu/todoList/logic/LogicManagerTest.java
``` java
    public void setup() {
        model = new ModelManager();
        String tempTodoListFile = saveFolder.getRoot().getPath() + "TempTodoList.xml";
        String tempEventListFile = saveFolder.getRoot().getPath() + "TempEventList.xml";
        String tempDeadlineListFile = saveFolder.getRoot().getPath() + "TempDeadlineList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTodoListFile, tempEventListFile, tempDeadlineListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTodoList = new TaskList(model.getTodoList()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'TodoList' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTodoList, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskList(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal TodoList data are same as those in the {@code expectedTodoList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTodoList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskList expectedTodoList,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTodoList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTodoList, model.getTodoList());
        assertEquals(expectedTodoList, latestSavedTodoList);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generatetask(1));
        model.addTask(helper.generatetask(2));
        model.addTask(helper.generatetask(3));

        assertCommandBehavior("clear todo", ClearCommand.TODO_MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
        assertCommandBehavior("clear event", ClearCommand.EVENT_MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
        assertCommandBehavior("clear deadline", ClearCommand.DEADLINE_MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, Todo", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 valid@email.butNoPrefix a/valid, Todo", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@email.butNoTodoPrefix valid, Todo", expectedMessage);
    }

    @Test
    public void execute_add_invalidtaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] p/12345 e/valid@e.mail a/valid, Todo", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/not_numbers e/valid@e.mail a/valid, Todo", StartDate.MESSAGE_DATE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/notAnEmail a/valid, Todo", Priority.MESSAGE_PRIORITY_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();

        Todo toBeAdded = helper.a111();
        TaskList expectedAB = new TaskList();

        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();

        Todo toBeAdded = helper.a111();
        TaskList expectedAB = new TaskList();

        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal TodoList

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }


    @Test
    public void execute_list_showsAlltasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskList expectedAB = helper.generateTodoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare TodoList state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generatetaskList(2);

        // set AB state to 2 tasks
        model.resetTodoListData(new TaskList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTodoList(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    /*@Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrecttask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threetasks = helper.generatetaskList(3);

        TaskList expectedAB = helper.generateTodoList(threetasks);
        helper.addToModel(model, threetasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTodoList().get(1), threetasks.get(1));
    }*/


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrecttask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threetasks = helper.generatetaskList(3);

        TaskList expectedAB = helper.generateTodoList(threetasks);
        expectedAB.removeTask(threetasks.get(1));
        helper.addToModel(model, threetasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threetasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generatetaskWithToDo("bla bla KEY bla");
        Task pTarget2 = helper.generatetaskWithToDo("bla KEY bla bceofeia");
        Task p1 = helper.generatetaskWithToDo("KE Y");
        Task p2 = helper.generatetaskWithToDo("KEYKEYKEY sduauo");

        List<Task> fourtasks = helper.generatetaskList(p1, pTarget1, p2, pTarget2);
        TaskList expectedAB = helper.generateTodoList(fourtasks);
        List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourtasks);

        assertCommandBehavior("find KEY",
                Command.getMessageFortaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generatetaskWithToDo("bla bla KEY bla");
        Task p2 = helper.generatetaskWithToDo("bla KEY bla bceofeia");
        Task p3 = helper.generatetaskWithToDo("key key");
        Task p4 = helper.generatetaskWithToDo("KEy sduauo");

        List<Task> fourtasks = helper.generatetaskList(p3, p1, p4, p2);
        TaskList expectedAB = helper.generateTodoList(fourtasks);
        List<Task> expectedList = fourtasks;
        helper.addToModel(model, fourtasks);

        assertCommandBehavior("find KEY",
                Command.getMessageFortaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generatetaskWithToDo("bla bla KEY bla");
        Task pTarget2 = helper.generatetaskWithToDo("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generatetaskWithToDo("key key");
        Task p1 = helper.generatetaskWithToDo("sduauo");

        List<Task> fourtasks = helper.generatetaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskList expectedAB = helper.generateTodoList(fourtasks);
        List<Task> expectedList = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourtasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageFortaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
```
###### /java/seedu/todoList/logic/LogicManagerTest.java
``` java
    class TestDataHelper{

        Todo a111() throws Exception {
            Name name = new Name("Assignment 111");
            StartDate date = new StartDate("01-11-2016");
            EndDate endDate = new EndDate("02-12-2016");
            Priority priority = new Priority("111");
            String isDone = "false";
            
            //EndTime endTime = new EndTime("1111");
            //Tag tag1 = new Tag("tag1");
            //Tag tag2 = new Tag("tag2");
            //UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Todo(name, date, endDate, priority, isDone);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique task object.
         *
         * @param seed used to generate the task data field values
         */
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    public void setup() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("cd"), getTempFilePath("ef"), getTempFilePath("prefs"));
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonUserPrefsStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
     */

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void TodoListReadSave() throws Exception {
        TaskList original = new TypicalTestTask().getTypicalTodoList();
        LogsCenter.getLogger(StorageManagerTest.class).info("XXXXXX: " + original.getTasks());
        storageManager.saveTodoList(original);
        ReadOnlyTaskList retrieved = storageManager.readTodoList().get();
        assertEquals(original, new TaskList(retrieved));
        //More extensive testing of TodoList saving/reading is done in XmlTodoListStorageTest
    }
    
    @Test
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    public void EventListReadSave() throws Exception {
        TaskList original = new TypicalTestEvent().getTypicalEventList();
        storageManager.saveEventList(original);
        ReadOnlyTaskList retrieved = storageManager.readEventList().get();
        assertEquals(original, new TaskList(retrieved));
    }
    
    @Test
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    public void DeadlineListReadSave() throws Exception {
        TaskList original = new TypicalTestDeadline().getTypicalDeadlineList();
        storageManager.saveDeadlineList(original);
        ReadOnlyTaskList retrieved = storageManager.readDeadlineList().get();
        assertEquals(original, new TaskList(retrieved));
    }

    @Test
    public void getTodoListFilePath(){
        assertNotNull(storageManager.getTodoListFilePath());
    }
    
    @Test
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    public void getEventListFilePath(){
        assertNotNull(storageManager.getEventListFilePath());
    }
    
    @Test
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    public void getDeadlineListFilePath(){
        assertNotNull(storageManager.getDeadlineListFilePath());
    }

    @Test
    public void handleTodoListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTodoListStorageExceptionThrowingStub("dummy"), null, null, new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTodoListChangedEvent(new TodoListChangedEvent(new TaskList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }
    
    @Test
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    public void handleEventListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(null, new XmlEventListStorageExceptionThrowingStub("dummy"), null, new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleEventListChangedEvent(new EventListChangedEvent(new TaskList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }
    
    @Test
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    public void handleDeadlineListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(null, null, new XmlDeadlineListStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleEventListChangedEvent(new EventListChangedEvent(new TaskList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }



    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTodoListStorageExceptionThrowingStub extends XmlTodoListStorage{

        public XmlTodoListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        //@Override
        public void saveTodoList(ReadOnlyTaskList TodoList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    class XmlEventListStorageExceptionThrowingStub extends XmlEventListStorage{

        public XmlEventListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        //@Override
        public void saveEventList(ReadOnlyTaskList EventList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
```
###### /java/seedu/todoList/storage/StorageManagerTest.java
``` java
    class XmlDeadlineListStorageExceptionThrowingStub extends XmlDeadlineListStorage{

        public XmlDeadlineListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        //@Override
        public void saveDeadlineList(ReadOnlyTaskList DeadlineList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
```
###### /java/seedu/todoList/storage/XmlTodoListStorageTest.java
``` java
    public void readAndSaveTodoList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTodoList.xml";
        TypicalTestTask td = new TypicalTestTask();
        TaskList original = td.getTypicalTodoList();
        XmlTodoListStorage xmlTodoListStorage = new XmlTodoListStorage(filePath);

        //Save in new file and read back
        xmlTodoListStorage.saveTaskList(original, filePath);
        ReadOnlyTaskList readBack = xmlTodoListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Todo(TypicalTestTask.a6));
        original.removeTask(new Todo(TypicalTestTask.a1));
        xmlTodoListStorage.saveTaskList(original, filePath);
        readBack = xmlTodoListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Save and read without specifying file path
        original.addTask(new Todo(TypicalTestTask.a1));
        xmlTodoListStorage.saveTaskList(original); //file path not specified
        readBack = xmlTodoListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new TaskList(readBack));

    }

    @Test
    public void saveTodoList_nullTodoList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTodoList(null, "SomeFile.xml");
    }


    private void saveTodoList(ReadOnlyTaskList TodoList, String filePath) throws IOException {
        new XmlTodoListStorage(filePath).saveTaskList(TodoList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTodoList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTodoList(new TaskList(), null);
    }


}
```
###### /java/seedu/todoList/testutil/TaskBuilder.java
``` java
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTodo(Todo todo) throws IllegalValueException {
        this.task.setTodo(new Todo(todo));
        return this;
    }
    
    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    public TaskBuilder withStartDate(String date) throws IllegalValueException {
        this.task.setStartDate(new StartDate(date));
        return this;
    }
    public TaskBuilder withEndDate(String date) throws IllegalValueException {
        this.task.setEndDate(new EndDate(date));
        return this;
    }
    public TaskBuilder withDone(String dd) throws IllegalValueException {
        this.task.setDone(dd);
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
```
###### /java/seedu/todoList/testutil/TestTask.java
``` java
public class TestTask extends Todo implements ReadOnlyTask {

    //private Name name;
    private Todo Todo;
    private static Name name;
    private static Priority priority;
    private static StartDate startDate;
    private static EndDate endDate;
    private static String done;


    public TestTask() {
        super(name, startDate, endDate, priority, done);
    }

    public void setTodo(Todo Todo) {
        this.Todo = Todo;
    }

    public void setName(Name name) {
        TestTask.name = name;
    }

    public void setPriority(Priority priority) {
        TestTask.priority = priority;
    }
    
    public void setStartDate(StartDate sdate) {
        TestTask.startDate = sdate;
    }
    public void setEndDate(EndDate edate) {
        TestTask.endDate = edate;
    }
    public void setDone(String done) {
        TestTask.done = done;
    }

    //@Override
    public Todo getTodo() {
        return Todo;
    }
    
    //@Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Name getName() {
        return name;
    }

    public StartDate getStartDate() {
        return startDate;
    }
    public EndDate getEndDate() {
        return endDate;
    }
    public String getDone() {
        return done;
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        //sb.append(this.getName().name + " ");
        sb.append("from/ " + this.getStartDate().date + " ");
        sb.append("to/ " + this.getEndDate().endDate + " ");
        sb.append("p/ " + this.getPriority().priority + " ");
        sb.append(this.getDone());
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
```
###### /java/seedu/todoList/testutil/TestUtil.java
``` java
    private static Event[] getSampleeventData() {
        try {
            return new Event[]{
                    new Event(new Name("EE 11"), new StartDate("01-11-2016"), new EndDate("02-11-2016"), new StartTime("1000"), new EndTime("1030"), "false"),
                    new Event(new Name("Essignment 12"), new StartDate("02-11-2016"), new EndDate("02-11-2016"),  new StartTime("1000"), new EndTime("1030"), "false"),
                    new Event(new Name("Essignment 13"), new StartDate("03-11-2016"), new EndDate("02-11-2016"),  new StartTime("1100"), new EndTime("1130"), "false"),
                    new Event(new Name("Essignment 14"), new StartDate("04-11-2016"), new EndDate("02-11-2016"),  new StartTime("1200"), new EndTime("1230"), "false"),
                    new Event(new Name("Essignment 15"), new StartDate("05-11-2016"), new EndDate("02-11-2016"),  new StartTime("1300"), new EndTime("1330"), "false"),
                    new Event(new Name("Essignment 16"), new StartDate("06-11-2016"), new EndDate("02-11-2016"),  new StartTime("1400"), new EndTime("1430"), "false"),
                    new Event(new Name("Essignment 17"), new StartDate("07-11-2016"), new EndDate("02-11-2016"),  new StartTime("1500"), new EndTime("1530"), "false"),
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }
```
###### /java/seedu/todoList/testutil/TestUtil.java
``` java
    private static Deadline[] getSampledeadlineData() {
        try {
            return new Deadline[]{
                    new Deadline(new Name("DD 11"), new StartDate("01-11-2016"), new EndTime("1100"), "false"),
                    new Deadline(new Name("Dssignment 12"), new StartDate("02-11-2016"), new EndTime("1200"), "false"),
                    new Deadline(new Name("Dssignment 13"), new StartDate("03-11-2016"), new EndTime("1300"), "false"),
                    new Deadline(new Name("Dssignment 14"), new StartDate("04-11-2016"), new EndTime("1400"), "false"),
                    new Deadline(new Name("Dssignment 15"), new StartDate("05-11-2016"), new EndTime("1500"), "false"),
                    new Deadline(new Name("Dssignment 16"), new StartDate("06-11-2016"), new EndTime("1600"), "false"),
                    new Deadline(new Name("Dssignment 17"), new StartDate("07-11-2016"), new EndTime("1700"), "false"),
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }

    public static List<Task> generateSampletaskData() {
        return Arrays.asList(sampletaskData);
    }
```
###### /java/seedu/todoList/testutil/TestUtil.java
``` java
    public static List<Event> generateSampleeventData() {
        return Arrays.asList(sampleeventData);
    }
```
###### /java/seedu/todoList/testutil/TestUtil.java
``` java
    public static List<Deadline> generateSampledeadlineData() {
        return Arrays.asList(sampledeadlineData);
    }

    /**
     * Appends the file name to the sandbox folder path.
     * Creates the sandbox folder if it doesn't exist.
     * @param fileName
     * @return
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    public static void createDataFileWithSampleData(String filePath) {
        createDataFileWithData(generateSampleStorageTodoList(), filePath);
    }

    public static <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... s) {
        createDataFileWithSampleData(TestApp.SAVE_LOCATION_FOR_TESTING);
    }


    public static TaskList generateEmptyTodoList() {
        return new TaskList(new UniqueTaskList());
    }
```
###### /java/seedu/todoList/testutil/TestUtil.java
``` java
    public static TaskList generateEmptyEventList() {
        return new TaskList(new UniqueTaskList());
    }
```
###### /java/seedu/todoList/testutil/TestUtil.java
``` java
    public static TaskList generateEmptyDeadlineList() {
        return new TaskList(new UniqueTaskList());
    }

    public static XmlSerializableTodoList generateSampleStorageTodoList() {
        return new XmlSerializableTodoList(generateEmptyTodoList());
    }   
```
###### /java/seedu/todoList/testutil/TestUtil.java
``` java
    public static XmlSerializableEventList generateSampleStorageEventList() {
        return new XmlSerializableEventList(generateEmptyEventList());
    }
```
###### /java/seedu/todoList/testutil/TestUtil.java
``` java
    public static XmlSerializableDeadlineList generateSampleStorageDeadlineList() {
        return new XmlSerializableDeadlineList(generateEmptyDeadlineList());
    }

    /**
     * Tweaks the {@code keyCodeCombination} to resolve the {@code KeyCode.SHORTCUT} to their
     * respective platform-specific keycodes
     */
    public static KeyCode[] scrub(KeyCodeCombination keyCodeCombination) {
        List<KeyCode> keys = new ArrayList<>();
        if (keyCodeCombination.getAlt() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.ALT);
        }
        if (keyCodeCombination.getShift() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.SHIFT);
        }
        if (keyCodeCombination.getMeta() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.META);
        }
        if (keyCodeCombination.getControl() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.CONTROL);
        }
        keys.add(keyCodeCombination.getCode());
        return keys.toArray(new KeyCode[]{});
    }

    public static boolean isHeadlessEnvironment() {
        String headlessProperty = System.getProperty("testfx.headless");
        return headlessProperty != null && headlessProperty.equals("true");
    }

    public static void captureScreenShot(String fileName) {
        File file = GuiTest.captureScreenshot();
        try {
            Files.copy(file, new File(fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String descOnFail(Object... comparedObjects) {
        return "Comparison failed \n"
                + Arrays.asList(comparedObjects).stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public static void setFinalStatic(Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException{
        field.setAccessible(true);
        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        // ~Modifier.FINAL is used to remove the final modifier from field so that its value is no longer
        // final and can be changed
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static void initRuntime() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.hideStage();
    }

    public static void tearDownRuntime() throws Exception {
        FxToolkit.cleanupStages();
    }

    /**
     * Gets private method of a class
     * Invoke the method using method.invoke(objectInstance, params...)
     *
     * Caveat: only find method declared in the current Class, not inherited from supertypes
     */
    public static Method getPrivateMethod(Class objectClass, String methodName) throws NoSuchMethodException {
        Method method = objectClass.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method;
    }

    public static void renameFile(File file, String newFileName) {
        try {
            Files.copy(file, new File(newFileName));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Gets mid point of a node relative to the screen.
     * @param node
     * @return
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x,y);
    }

    /**
     * Gets mid point of a node relative to its scene.
     * @param node
     * @return
     */
    public static Point2D getSceneMidPoint(Node node) {
        double x = getScenePos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScenePos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x,y);
    }

    /**
     * Gets the bound of the node relative to the parent scene.
     * @param node
     * @return
     */
    public static Bounds getScenePos(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    public static Bounds getScreenPos(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

    public static double getSceneMaxX(Scene scene) {
        return scene.getX() + scene.getWidth();
    }

    public static double getSceneMaxY(Scene scene) {
        return scene.getX() + scene.getHeight();
    }

    public static Object getLastElement(List<?> list) {
        return list.get(list.size() - 1);
    }

    /**
     * Removes a subset from the list of tasks.
     * @param tasks The list of tasks
     * @param tasksToRemove The subset of tasks.
     * @return The modified tasks after removal of the subset from tasks.
     */
    public static TestTask[] removetasksFromList(final TestTask[] tasks, TestTask... tasksToRemove) {
        List<TestTask> listOftasks = asList(tasks);
        listOftasks.removeAll(asList(tasksToRemove));
        return listOftasks.toArray(new TestTask[listOftasks.size()]);
    }
    
    public static TestEvent[] removeEventsFromList(final TestEvent[] events, TestEvent... eventsToRemove) {
        List<TestEvent> listOfEvents = asList(events);
        listOfEvents.removeAll(asList(eventsToRemove));
        return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
    }
    
    public static TestDeadline[] removeDeadlinesFromList(final TestDeadline[] events, TestDeadline... deadlineToRemove) {
        List<TestDeadline> listOfdd = asList(events);
        listOfdd.removeAll(asList(deadlineToRemove));
        return listOfdd.toArray(new TestDeadline[listOfdd.size()]);
    }


    /**
     * Returns a copy of the list with the task at specified index removed.
     * @param list original list to copy from
     * @param targetIndexInOneIndexedFormat e.g. if the first element to be removed, 1 should be given as index.
     */
    public static TestTask[] removetaskFromList(final TestTask[] list, int targetIndexInOneIndexedFormat) {
        return removetasksFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }
    public static TestEvent[] removeEventFromList(final TestEvent[] list, int targetIndexInOneIndexedFormat) {
        return removeEventsFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }
    public static TestDeadline[] removeDeadlineFromList(final TestDeadline[] list, int targetIndexInOneIndexedFormat) {
        return removeDeadlinesFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }

    /**
     * Replaces tasks[i] with a task.
     * @param tasks The array of tasks.
     * @param task The replacement task
     * @param index The index of the task to be replaced.
     * @return
     */
    public static TestTask[] replacetaskFromList(TestTask[] tasks, TestTask task, int index) {
        tasks[index] = task;
        return tasks;
    }
    
    public static TestEvent[] replaceEventFromList(TestEvent[] events, TestEvent event, int index) {
        events[index] = event;
        return events;
    }
    public static TestDeadline[] replaceDeadlineFromList(TestDeadline[] dds, TestDeadline dd, int index) {
        dds[index] = dd;
        return dds;
    }

    /**
     * Appends tasks to the array of tasks.
     * @param tasks A array of tasks.
     * @param tasksToAdd The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static TestTask[] addTasksToList(final TestTask[] tasks, TestTask... tasksToAdd) {
        List<TestTask> listOftasks = asList(tasks);
        listOftasks.addAll(asList(tasksToAdd));
        return listOftasks.toArray(new TestTask[listOftasks.size()]);
    }
    
    public static TestEvent[] addEventsToList(final TestEvent[] events, TestEvent... eventsToAdd) {
        List<TestEvent> listOfEvents = asList(events);
        listOfEvents.addAll(asList(eventsToAdd));
        return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
    }
    
    public static TestDeadline[] addDeadlinesToList(final TestDeadline[] events, TestDeadline... eventsToAdd) {
        List<TestDeadline> listOfEvents = asList(events);
        listOfEvents.addAll(asList(eventsToAdd));
        return listOfEvents.toArray(new TestDeadline[listOfEvents.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for(T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndTask(TaskCardHandle card, ReadOnlyTask task) {
        return card.isSametask(task); //something wrong. Always return false
    }
    
    public static boolean compareCardAndEvent(EventCardHandle card, ReadOnlyTask event) {
        return card.isSameEvent(event); //something wrong. Always return false
    }
    
    public static boolean compareCardAndDeadline(DeadlineCardHandle card, ReadOnlyTask tasks) {
        return card.isSameDeadline(tasks); //something wrong. Always return false
    }

}
```
###### /java/seedu/todoList/testutil/TypicalTestDeadline.java
``` java
public class TypicalTestDeadline {

   
    public static TestDeadline  d1, d2, d3, d4, d5, d6, d7, d8;

    public TypicalTestDeadline() {
        try {
            d1 = new DeadlineBuilder().withName("d 1").withDate("30-10-2017").withEndTime("1000").withDone("ND").build();
            d2 = new DeadlineBuilder().withName("dd 1").withDate("26-10-2017").withEndTime("1200").build();
            d3 = new DeadlineBuilder().withName("deambuilding 3").withDate("27-10-2017").withEndTime("1300").build();
            d4 = new DeadlineBuilder().withName("dssignment 4").withDate("27-10-2017").withEndTime("1400").build();
            d5 = new DeadlineBuilder().withName("droject 5").withDate("28-10-2017").withEndTime("1500").build();
            //Manually added
            d6 = new DeadlineBuilder().withName("dssignment 6").withDate("28-10-2017").withEndTime("1600").build();
            d7 = new DeadlineBuilder().withName("domework 7").withDate("29-10-2017").withEndTime("1700").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadDeadlineListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new Deadline(d1));
            ab.addTask(new Deadline(d2));
            ab.addTask(new Deadline(d3));
            ab.addTask(new Deadline(d4));
            ab.addTask(new Deadline(d5));
            ab.addTask(new Deadline(d6));
            ab.addTask(new Deadline(d7));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        }
    }

    public TestDeadline[] getTypicalDeadline() {
        return new TestDeadline[]{d1, d2, d3, d4, d5, d6, d7};
    }

    public TaskList getTypicalDeadlineList(){
        TaskList ab = new TaskList();
        loadDeadlineListWithSampleData(ab);
        return ab;
    }

}
```
###### /java/seedu/todoList/testutil/TypicalTestEvent.java
``` java
public class TypicalTestEvent {

   
    public static TestEvent  e1, e2, e3, e4, e5, e6, e7, e8;

    public TypicalTestEvent() {
        try {
            e1 = new EventBuilder().withName("e 1").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e2 = new EventBuilder().withName("e 2").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e3 = new EventBuilder().withName("Eeambuilding 3").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e4 = new EventBuilder().withName("Essignment 4").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e5 = new EventBuilder().withName("Eroject 5").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            //Manually added
            e6 = new EventBuilder().withName("Essignment 6").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e7 = new EventBuilder().withName("Eomework 7").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadEventListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new Event(e1));
            ab.addTask(new Event(e2));
            ab.addTask(new Event(e3));
            ab.addTask(new Event(e4));
            ab.addTask(new Event(e5));
            ab.addTask(new Event(e6));
            ab.addTask(new Event(e7));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        }
    }

    public TestEvent[] getTypicalEvent() {
        return new TestEvent[]{e1, e2, e3, e4, e5, e6, e7};
    }

    public TaskList getTypicalEventList(){
        TaskList ab = new TaskList();
        loadEventListWithSampleData(ab);
        return ab;
    }
}
```
###### /java/seedu/todoList/testutil/TypicalTestTask.java
``` java
public class TypicalTestTask {

   
    public static TestTask  a1, a2, a3, a4, a5, a6, a7;

    public TypicalTestTask() {
        /*try {
            //a1 = new TaskBuilder().withName("assignment 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("false").build();
            //a2 = new TaskBuilder().withName("project 1").withStartDate("26-10-2017").withEndDate("27-10-2017").withPriority("2").withDone("false").build();
            //a3 = new TaskBuilder().withName("teambuilding 3").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("3").withDone("false").build();
            //a4 = new TaskBuilder().withName("assignment 4").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("2").withDone("false").build();
            //a5 = new TaskBuilder().withName("project 5").withStartDate("28-10-2017").withEndDate("29-10-2017").withPriority("3").withDone("false").build();
            //Manually added
            //a6 = new TaskBuilder().withName("assignment 6").withStartDate("28-10-2017").withEndDate("29-10-2017").withPriority("2").withDone("false").build();
            //a7 = new TaskBuilder().withName("homework 7").withStartDate("29-10-2017").withEndDate("30-10-2017").withPriority("1").withDone("false").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }*/
    }

    public static void loadTodoListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new TaskBuilder().withName("PROJECT 5").withStartDate("28-10-2016").withEndDate("29-10-2016").withPriority("3").withDone("false").build());
            ab.addTask(new Todo(new Name("todo 2"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 3"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 7"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 4"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 5"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 6"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public TestTask[] getTypicaltasks() throws IllegalValueException {
        return new TestTask[]{new TaskBuilder().withName("assignment 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("false").build(), 
                new TaskBuilder().withName("project 1").withStartDate("26-11-2017").withEndDate("27-11-2017").withPriority("2").withDone("false").build(), 
                new TaskBuilder().withName("teambuilding 3").withStartDate("27-12-2017").withEndDate("28-12-2017").withPriority("3").withDone("false").build(), 
                new TaskBuilder().withName("assignment 4").withStartDate("29-12-2017").withEndDate("30-12-2017").withPriority("2").withDone("false").build()};
    }

    public TaskList getTypicalTodoList(){
        TaskList ab = new TaskList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
```
