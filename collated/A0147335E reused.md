# A0147335E reused
###### \src\main\java\seedu\task\logic\commands\AddCommand.java
``` java
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TASK_NAME, from <time> to <time> by <time> #TAG...\n"
            + "Example: " + COMMAND_WORD
            + " do homework from 12.00pm to 01.00pm by 03.00pm #homework";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime, String deadline, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Name(name), new StartTime(startTime), new EndTime(endTime), new Deadline(deadline), new UniqueTagList(tagSet), new Status(false, false, true));
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        try {
            model.addTask(toAdd);
            if(isUndo == false){
                history.getUndoList().add(new RollBackCommand(COMMAND_WORD, toAdd, null));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

    public CommandResult execute(int index) {
        assert model != null;
        try {
            model.addTask(index, toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
```
###### \src\main\java\seedu\task\logic\commands\ClearCommand.java
``` java
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";

    public ClearCommand() {}

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        for(int i = 0; i < lastShownList.size(); i++) {
            ReadOnlyTask taskToDelete = lastShownList.get(i);
            if(isUndo == false) {
                Task task = new Task(taskToDelete.getName(), taskToDelete.getStartTime(), taskToDelete.getEndTime(), taskToDelete.getDeadline(), taskToDelete.getTags(), taskToDelete.getStatus());
                history.getUndoList().add(new RollBackCommand(COMMAND_WORD , task, null));
            }
        }
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(int index) {
        return null;
    }
}
```
###### \src\main\java\seedu\task\logic\commands\Command.java
``` java
 */
public abstract class Command {
    protected Model model;
    protected HistoryManager history;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute(boolean isUndo);
    public abstract CommandResult execute(int index);

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    public void setHistory(HistoryManager history) {
        this.history = history;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
}
```
###### \src\main\java\seedu\task\logic\commands\DeleteCommand.java
``` java
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(boolean isUndo) {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        if (isUndo == false) {
            Task task = new Task(taskToDelete.getName(), taskToDelete.getStartTime(), taskToDelete.getEndTime(), taskToDelete.getDeadline(), taskToDelete.getTags(), taskToDelete.getStatus());
            history.getUndoList().add(new RollBackCommand(COMMAND_WORD, task, null));
        }
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }


    @Override
    public CommandResult execute(int index) {
        return null;
    }
}
```
###### \src\main\java\seedu\task\logic\Logic.java
``` java
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the undo list of commands */
    ArrayList<RollBackCommand> getUndoList();

    /** Returns the list of previous commands */
    ArrayList<String> getPreviousCommandList();
}
```
###### \src\main\java\seedu\task\logic\LogicManager.java
``` java
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandParser parser;
    private final HistoryManager historyManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new CommandParser();
        this.historyManager = new HistoryManager();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);

        command.setData(model);
        command.setHistory(historyManager);
```
###### \src\main\java\seedu\task\logic\LogicManager.java
``` java
        logger.info("SUCCESS");

        if (!commandText.equals("undo")) {
            historyManager.getPreviousCommandList().add(commandText);
            return command.execute(false);
        }
        else {
            return command.execute(true);
        }
    }


    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ArrayList<RollBackCommand> getUndoList() {
        return historyManager.getUndoList();
    }

    @Override
    public ArrayList<String> getPreviousCommandList() {
        return historyManager.getPreviousCommandList();
    }
}
```
###### \src\main\java\seedu\task\model\Model.java
``` java
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given task on a specific index */
    void addTask(int index, Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
```
###### \src\main\java\seedu\task\model\ModelManager.java
``` java
    @Override
    public synchronized void addTask(int index, Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(index, task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String name = task.getName().fullName.toLowerCase();
            return nameKeyWords.stream()
                    .filter(keyword -> name.indexOf(keyword.toLowerCase())>=0)
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
```
###### \src\main\java\seedu\task\model\task\UniqueTaskList.java
``` java
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {

        private static final long serialVersionUID = -6438066197743930586L;

        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {

        private static final long serialVersionUID = -4389890624748332382L;}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    
```
###### \src\main\java\seedu\task\model\TaskManager.java
``` java
    public void addTask(int index, Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(index, p);
    }
    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equals(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
```
###### \src\main\java\seedu\task\ui\TaskCard.java
``` java
public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private Label tags;

    private static ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        TaskCard.task = task;
        card.displayedIndex = displayedIndex;

        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        if(!task.getDeadline().value.equals("now") && !task.getDeadline().value.equals(" from now")){
            startTimeLabel.setText(" from " + task.getStartTime().value);
        }else{
            startTimeLabel.setText("");
        }
        if(!task.getEndTime().value.equals("no endtime") && !task.getEndTime().value.equals(" to no endtime")){
            endTimeLabel.setText(" to " + task.getEndTime().value);
        }else{
            endTimeLabel.setText("");
        }
        if(!task.getDeadline().value.equals("no deadline") && !task.getDeadline().value.equals(" to no deadline")){
            deadlineLabel.setText(" ends " + task.getDeadline().value);
        }else{
            deadlineLabel.setText("");
        }
        tags.setText(task.tagsString());
    }

```
