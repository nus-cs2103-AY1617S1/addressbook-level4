package seedu.oneline.model;

import javafx.collections.transformation.FilteredList;
import seedu.oneline.commons.core.ComponentManager;
import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.events.model.TaskBookChangedEvent;
import seedu.oneline.commons.exceptions.StateNonExistentException;
import seedu.oneline.commons.util.StringUtil;
import seedu.oneline.logic.commands.Command;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Date;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;


/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredTasks;

    private final Stack<ModelState> prevState = new Stack<ModelState>();
    private final Stack<ModelState> nextState = new Stack<ModelState>();
    
    
    /**
     * Initializes a ModelManager with the given Task book
     * Task book and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
        filteredTasks.setPredicate(getNotDonePredicate());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
        filteredTasks.setPredicate(getNotDonePredicate());
    }

    @Override
    public CommandResult executeCommand(Command command) {
        command.setData(this);
        if (command.canUndo()) {
            saveState();
        }
        return command.execute();
    }
    
    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAllNotDone();
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void replaceTask(ReadOnlyTask oldTask, Task newTask) throws TaskNotFoundException, DuplicateTaskException {
        taskBook.getUniqueTaskList().replaceTask(oldTask, newTask);
        updateFilteredListToShowAllNotDone();
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void doneTask(int index) throws TaskNotFoundException {
        Task done = filteredTasks.get(index);
        assert done != null;
        done.setCompleted(true);
        updateFilteredListToShowAllNotDone();
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void undoneTask(int index) throws TaskNotFoundException {
        Task undone = filteredTasks.get(index);
        assert undone != null;
        undone.setCompleted(false);
        updateFilteredListToShowAllDone();
        indicateTaskBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================
    //@@author: A0138848M
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getSortedFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks.sorted());
    }
    
    //@@author:
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    
    @Override
    public void updateFilteredListToShowAllNotDone() {
        filteredTasks.setPredicate(null);
        filteredTasks.setPredicate(getNotDonePredicate());
    }
    
    @Override
    public void updateFilteredListToShowAllDone() {
        filteredTasks.setPredicate(null);
        filteredTasks.setPredicate(getDonePredicate());
    }
    
    //@@author: A0138848M
    @Override
    public void updateFilteredListToShowToday() {
        Date now = new Date();
        updateFilteredListToShowAllNotDone();
        Predicate<Task> eventSameDay = t -> t.isEvent() 
                && DateUtils.isSameDay(t.getEndTime().getDate(), now);
        Predicate<Task> deadlineSameDay = t -> t.hasDeadline() 
                && DateUtils.isSameDay(t.getDeadline().getDate(), now);
        filteredTasks.setPredicate(eventSameDay.or(deadlineSameDay));
    }
    
    @Override
    public void updateFilteredListToShowWeek() {
    }
    
    @Override
    public void updateFilteredListToShowFloat() {
    }
    
    //@@author
    private Predicate<Task> getNotDonePredicate() {
        return task -> !task.isCompleted();
    }
    
    private Predicate<Task> getDonePredicate() {
        return task -> task.isCompleted();
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
        boolean satisfies(ReadOnlyTask person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
  //@@author
  //========== Inner functions and classes used for undo/redo ==================================================
    
    public void undo() throws StateNonExistentException {
        if (prevState.size() == 0) {
            throw new StateNonExistentException();
        }
        nextState.push(new ModelState(this));
        loadState(prevState.pop());
    }
    
    public void redo() throws StateNonExistentException {
        if (nextState.size() == 0) {
            throw new StateNonExistentException();
        }
        prevState.push(new ModelState(this));
        loadState(nextState.pop());
    }
    
    private void saveState() {
        prevState.push(new ModelState(this));
        nextState.clear();
    }
    
    private void loadState(ModelState state) {
        resetData(state.data);
        filteredTasks.setPredicate(state.filterPredicate);
    }
    
    private static class ModelState {
        
        final ReadOnlyTaskBook data;
        final Predicate<? super Task> filterPredicate;
        
        public ModelState(ModelManager manager) {
            data = new TaskBook(manager.getTaskBook());
            filterPredicate = manager.filteredTasks.getPredicate();
        }
        
    }

}
