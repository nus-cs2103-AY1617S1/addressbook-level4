package seedu.jimi.model;

import javafx.collections.transformation.FilteredList;
import seedu.jimi.commons.core.ComponentManager;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.ui.ShowTaskPanelSectionEvent;
import seedu.jimi.commons.util.StringUtil;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;
import seedu.jimi.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.jimi.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of Jimi's data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<ReadOnlyTask> filteredReadOnlyTasks;
    private final FilteredList<ReadOnlyTask> filteredDeadlineTasks;
    private final FilteredList<ReadOnlyTask> filteredEvents;

    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredReadOnlyTasks = new FilteredList<>(taskBook.getTasks());
        filteredDeadlineTasks = new FilteredList<>(taskBook.getDeadlineTasks());
        filteredEvents = new FilteredList<>(taskBook.getEvents());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        filteredReadOnlyTasks = new FilteredList<>(taskBook.getTasks());
        filteredDeadlineTasks = new FilteredList<>(taskBook.getDeadlineTasks());
        filteredEvents = new FilteredList<>(taskBook.getEvents());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /**
     * @return A modifiable version of the task list.
     */
    public FilteredList<ReadOnlyTask> getModifiableTaskList() {
        return filteredReadOnlyTasks;
    }
    
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(taskBook));
    }
    
    /** Raises and event to indicate user request to show task panel sections. */
    public void showTaskPanelSection(String sectionToShow) {
        raise(new ShowTaskPanelSectionEvent(sectionToShow));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(ReadOnlyTask task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    public void addDeadlineTask(ReadOnlyTask deadlineTask) throws DuplicateTaskException {
        taskBook.addDeadlineTask(deadlineTask);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void addEvent(ReadOnlyTask event) throws DuplicateTaskException {
        taskBook.addEvent(event);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    /**
     * 
     * @param newTask Task to be replaced with.
     * @param targetIndex Index of oldTask to be replaced by.
     */
    @Override
    public synchronized void editReadOnlyTask(int targetIndex, ReadOnlyTask newTask) {
        taskBook.editTask(targetIndex, newTask);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    /**
     * 
     * @param taskToComplete Task to set to complete/incomplete.
     * @param isComplete True, if task is to be set to completed.
     */
    @Override
    public synchronized void completeTask(ReadOnlyTask taskToComplete, boolean isComplete) {
        taskBook.completeTask(taskToComplete, isComplete);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    //=========== Filtered FloatingTask, DeadlineTask, Events List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredReadOnlyTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineTaskList() {
        return new UnmodifiableObservableList<>(filteredDeadlineTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }
    
    @Override
    public void updateFilteredListToShowAll() {
        filteredReadOnlyTasks.setPredicate(null);
        filteredDeadlineTasks.setPredicate(null);
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
        updateFilteredDeadlineTaskList(new PredicateExpression(new NameQualifier(keywords)));
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords)));
        
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredReadOnlyTasks.setPredicate(expression::satisfies);
    }

    private void updateFilteredDeadlineTaskList(Expression expression) {
        filteredDeadlineTasks.setPredicate(expression::satisfies);
    }

    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
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
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
}
