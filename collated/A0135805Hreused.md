# A0135805Hreused
###### \src\main\java\seedu\todo\model\tag\Tag.java
``` java
    /* Override Methods */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag && this.tagName.equals(((Tag) other).tagName)) // if is tag
                || (other instanceof String && this.tagName.equals(other)); // if is string
                //Enables string comparison for hashing.
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
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
}
```
###### \src\main\java\seedu\todo\ui\view\CommandErrorView.java
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
###### \src\main\java\seedu\todo\ui\view\CommandFeedbackView.java
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
     * Configure the UI layout of {@link CommandFeedbackView}.
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(textContainer, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandFeedbackLabel, 0.0, 0.0, 0.0, 0.0);
    }

```
###### \src\main\java\seedu\todo\ui\view\CommandInputView.java
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
###### \src\main\java\seedu\todo\ui\view\TaskCardView.java
``` java
    /*Layout Declarations*/
    @FXML
    private VBox taskCard;
    @FXML
    private ImageView pinImage;
    @FXML
    private Label titleLabel;
    @FXML
    private Label typeLabel, moreInfoLabel;
    @FXML
    private Label descriptionLabel, dateLabel, locationLabel;
    @FXML
    private HBox descriptionBox, dateBox, locationBox;
    @FXML
    private FlowPane tagsBox;
    
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
###### \src\main\java\seedu\todo\ui\view\TodoListView.java
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
     * @return an instance of this class
     */
    public static TodoListView load(Stage primaryStage, AnchorPane placeHolder,
                                    ObservableList<ImmutableTask> todoList) {
        
        TodoListView todoListView = UiPartLoaderUtil
                .loadUiPart(primaryStage, placeHolder, new TodoListView());
        todoListView.configure(todoList);
        return todoListView;
    }

    /**
     * Configures the {@link TodoListView}
     *
     * @param todoList A list of {@link ImmutableTask} to be displayed on this {@link #todoListView}.
     */
    private void configure(ObservableList<ImmutableTask> todoList) {
        setConnections(todoList);
    }

    /**
     * Links the list of {@link ImmutableTask} to the todoListView.
     *
     * @param todoList A list of {@link ImmutableTask} to be displayed on this {@link #todoListView}.
     */
    private void setConnections(ObservableList<ImmutableTask> todoList) {
        todoListView.setItems(todoList);
        todoListView.setCellFactory(param -> new TodoListViewCell());
    }

```
###### \src\main\java\seedu\todo\ui\view\TodoListView.java
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
###### \src\test\java\guitests\guihandles\CommandInputViewHandle.java
``` java
    /**
     * Enters the given command in the Command Box and presses enter.
     * @param command Command text to be executed.
     */
    public void runCommand(String command) {
        enterCommand(command);
        pressEnter();
        guiRobot.sleep(GUI_SLEEP_DURATION); //Give time for the command to take effect
    }

    /* Text View Helper Methods */
    /**
     * Gets the text stored in a text area given the id to the text area
     *
     * @param textFieldId ID of the text area.
     * @return Returns the text that is contained in the text area.
     */
    private String getTextAreaText(String textFieldId) {
        return ((TextArea) getNode(textFieldId)).getText();
    }

    /**
     * Keys in the given {@code newText} to the specified text area given its ID.
     *
     * @param textFieldId ID for the text area.
     * @param newText Text to be keyed in to the text area.
     */
    private void setTextAreaText(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        TextArea textArea = (TextArea)guiRobot.lookup(textFieldId).tryQuery().get();
        Platform.runLater(() -> textArea.setText(newText));
        guiRobot.sleep(GUI_SLEEP_DURATION); // so that the texts stays visible on the GUI for a short period
    }
}
```
###### \src\test\java\guitests\guihandles\TaskCardViewHandle.java
``` java
    /**
     * Search and returns exactly one matching node.
     *
     * @param fieldId Field ID to search inside the parent node.
     * @return Returns one appropriate node that matches the {@code fieldId}.
     * @throws NullPointerException when no node with {@code fieldId} can be found, intentionally breaking
     *         the tests.
     */
    @Override
    protected Node getNode(String fieldId) throws NullPointerException {
        Optional<Node> node = guiRobot.from(rootNode).lookup(fieldId).tryQuery();
        if (node.isPresent()) {
            return node.get();
        } else {
            throw new NullPointerException("Node " + fieldId + " is not found.");
        }
    }

```
###### \src\test\java\guitests\guihandles\TaskCardViewHandle.java
``` java
    /* Override Methods */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardViewHandle) {
            TaskCardViewHandle handle = (TaskCardViewHandle) obj;

            boolean hasEqualTitle = this.getDisplayedTitle()
                    .equals(handle.getDisplayedTitle());
            boolean hasEqualDescription = this.getDisplayedDescription()
                    .equals(handle.getDisplayedDescription());
            boolean hasEqualDateText = this.getDisplayedDateText()
                    .equals(handle.getDisplayedDateText());
            boolean hasEqualLocation = this.getDisplayedLocation()
                    .equals(handle.getDisplayedLocation());
            boolean hasEqualType = this.getDisplayedTypeLabel()
                    .equals(handle.getDisplayedTypeLabel());
            boolean hasEqualMoreInfoVisibility = this.getMoreInfoLabelVisibility()
                    == handle.getMoreInfoLabelVisibility();
            boolean hasEqualDescriptionBoxVisibility = this.getDescriptionBoxVisibility()
                    == handle.getDescriptionBoxVisibility();
            boolean hasEqualDateBoxVisibility = this.getDateBoxVisibility()
                    == handle.getDateBoxVisibility();
            boolean hasEqualLocationBoxVisibility = this.getLocationBoxVisibility()
                    == handle.getLocationBoxVisibility();
            boolean hasEqualPinImageVisibility = this.getPinImageVisibility()
                    == handle.getPinImageVisibility();
            boolean hasEqualSelectedStyleApplied = this.isSelectedStyleApplied()
                    == handle.isSelectedStyleApplied();
            boolean hasEqualCompletedStyleApplied = this.isCompletedStyleApplied()
                    == handle.isCompletedStyleApplied();
            boolean hasEqualOverdueStyleApplied = this.isOverdueStyleApplied()
                    == handle.isOverdueStyleApplied();

            return hasEqualTitle && hasEqualDescription && hasEqualDateText
                    && hasEqualLocation && hasEqualType && hasEqualMoreInfoVisibility
                    && hasEqualDescriptionBoxVisibility && hasEqualDateBoxVisibility
                    && hasEqualLocationBoxVisibility && hasEqualPinImageVisibility
                    && hasEqualSelectedStyleApplied && hasEqualCompletedStyleApplied
                    && hasEqualOverdueStyleApplied;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDisplayedTitle() + " " + getDisplayedDescription();
    }
}
```
###### \src\test\java\guitests\guihandles\TodoListViewHandle.java
``` java
    /**
     * Constructs a handle for {@link TodoListView}.
     *
     * @param guiRobot The GUI test robot.
     * @param primaryStage The main stage that is executed from the application's UI.
     */
    public TodoListViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /* View Element Helper Methods */
    /**
     * Gets an instance of {@link ListView} of {@link TodoListView}
     */
    public ListView<ImmutableTask> getTodoListView() {
        return (ListView<ImmutableTask>) getNode(TODO_LIST_VIEW_ID);
    }

```
