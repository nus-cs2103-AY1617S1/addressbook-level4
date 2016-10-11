package seedu.address.model;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(ReadOnlyTaskBook taskBook) {
        super();
        assert taskBook != null;

        logger.fine("Initializing with task book: " + taskBook);

        this.taskBook = new TaskBook(taskBook);
        this.filteredTasks = new FilteredList<>(this.taskBook.getTasks());
    }

    public ModelManager() {
        this(new TaskBook());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    @Override
    public synchronized void deleteTask(Task target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(Task person) {
        taskBook.addTask(person);
        setFilter(null);
        indicateTaskBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<Task> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void setFilter(Predicate<Task> predicate) {
        filteredTasks.setPredicate(predicate);
    }

}
