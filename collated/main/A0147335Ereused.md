# A0147335Ereused
###### \java\seedu\task\logic\commands\AddCommand.java
``` java
    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        try {
            model.addTask(toAdd);
            if (!isUndo) {
                getUndoList().add(new RollBackCommand(COMMAND_WORD, toAdd, null));
            }
```
###### \java\seedu\task\logic\commands\AddCommand.java
``` java
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

	private ArrayList<RollBackCommand> getUndoList() {
		return history.getUndoList();
	}
	
	// insert a task at a specific index
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
###### \java\seedu\task\logic\commands\ClearCommand.java
``` java
	@Override
	public CommandResult execute(boolean isUndo) {
		assert model != null;
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		for (int i = 0; i < lastShownList.size(); i++) {
			ReadOnlyTask taskToDelete = lastShownList.get(i);
			if (!isUndo) {
				getUndoList().add(new RollBackCommand(COMMAND_WORD, (Task) taskToDelete, null));
			}
		}
		model.resetData(TaskManager.getEmptyTaskManager());
		return new CommandResult(MESSAGE_SUCCESS);
	}

```
###### \java\seedu\task\logic\commands\ClearCommand.java
``` java
	private ArrayList<RollBackCommand> getUndoList() {
		return history.getUndoList();
	}

}
```
###### \java\seedu\task\logic\commands\Command.java
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

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

```
###### \java\seedu\task\logic\commands\DeleteCommand.java
``` java
    @Override
    public CommandResult execute(boolean isUndo) {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        if (!isUndo) {
            getUndoList().add(new RollBackCommand(COMMAND_WORD, (Task) taskToDelete, null));
        }
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

```
###### \java\seedu\task\logic\commands\DeleteCommand.java
``` java
	private ArrayList<RollBackCommand> getUndoList() {
		return history.getUndoList();
	}

}
```
###### \java\seedu\task\logic\LogicManager.java
``` java
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new CommandParser();
        this.historyManager = new HistoryManager();
    }
    
```
###### \java\seedu\task\logic\LogicManager.java
``` java
    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.setHistory(historyManager);
        
        if (command instanceof IncorrectCommand) {
            return command.execute(false);
        }
        
        logger.info("SUCCESS");

        if (!isUndo(commandText)) {
            getPreviousCommandList().add(commandText);
            return command.execute(false);
        } else {
            return command.execute(true);
        }
    }

```
###### \java\seedu\task\model\Model.java
``` java
    /** Adds the given task on a specific index */
    void addTask(int index, Task task) throws UniqueTaskList.DuplicateTaskException;
    
    
```
###### \java\seedu\task\model\ModelManager.java
``` java
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        Task newTask = task;
        if (!isDeadlineExist(task)) {
            String strDatewithTime = newTask.getDeadline().toString().replace(" ", "T");
            LocalDateTime taskDateTime = LocalDateTime.parse(strDatewithTime);

            Date currentDate = new Date();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());

            if (taskDateTime.isBefore(localDateTime)) {
                newTask = new Task(newTask.getName(), newTask.getStartTime(), newTask.getEndTime(), newTask.getDeadline(),
                        newTask.getTags(),
                        new Status(newTask.getStatus().getDoneStatus(), true, newTask.getStatus().getFavoriteStatus()),
                        newTask.getRecurring());
            } else {
                newTask = new Task(newTask.getName(), newTask.getStartTime(), newTask.getEndTime(), newTask.getDeadline(),
                        newTask.getTags(),
                        new Status(newTask.getStatus().getDoneStatus(), false, newTask.getStatus().getFavoriteStatus()),
                        newTask.getRecurring());
            }
        } else {
            newTask = new Task(newTask.getName(), newTask.getStartTime(), newTask.getEndTime(), newTask.getDeadline(), newTask.getTags(),
                    new Status(newTask.getStatus().getDoneStatus(), false, newTask.getStatus().getFavoriteStatus()),
                    newTask.getRecurring());
        }
        taskManager.addTask(newTask);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(int index, Task task) throws UniqueTaskList.DuplicateTaskException {
        Task newTask = task;
        if (!isDeadlineExist(newTask)) {
            String strDatewithTime = newTask.getDeadline().toString().replace(" ", "T");
            LocalDateTime newTaskDateTime = LocalDateTime.parse(strDatewithTime);

            Date currentDate = new Date();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());

            if (newTaskDateTime.isBefore(localDateTime)) {
                newTask = new Task(newTask.getName(), newTask.getStartTime(), newTask.getEndTime(), newTask.getDeadline(),
                        newTask.getTags(),
                        new Status(newTask.getStatus().getDoneStatus(), true, newTask.getStatus().getFavoriteStatus()),
                        newTask.getRecurring());
            } else {
                newTask = new Task(newTask.getName(), newTask.getStartTime(), newTask.getEndTime(), newTask.getDeadline(),
                        newTask.getTags(),
                        new Status(newTask.getStatus().getDoneStatus(), false, newTask.getStatus().getFavoriteStatus()),
                        newTask.getRecurring());
            }

        } else {
            newTask = new Task(newTask.getName(), newTask.getStartTime(), newTask.getEndTime(), newTask.getDeadline(), newTask.getTags(),
                    new Status(newTask.getStatus().getDoneStatus(), false, newTask.getStatus().getFavoriteStatus()),
                    newTask.getRecurring());
        }

        taskManager.addTask(index, newTask);
        indicateTaskManagerChanged();
    }

    private boolean isDeadlineExist(Task task) {
        return task.getDeadline().toString().isEmpty();
    }
```
###### \java\seedu\task\model\task\UniqueTaskList.java
``` java
/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {

        private static final long serialVersionUID = -6438066197743930586L;

        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would
     * fail because there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {

        private static final long serialVersionUID = -4389890624748332382L;
    }

    /**
     * Returns true if the list contains an equivalent task as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

```
###### \java\seedu\task\model\TaskManager.java
``` java
    public void addTask(int index, Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(index, p);
    }

```
