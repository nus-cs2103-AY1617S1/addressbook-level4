package seedu.address.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.undo.UndoList;
import seedu.address.model.undo.UndoTask;

/**
 * Represents the in-memory model of the task book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredDatedTasks;
    private final FilteredList<Task> filteredUndatedTasks;
    private UndoList undoableTasks;

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
        filteredDatedTasks = new FilteredList<>(taskBook.getDatedTasks());
        filteredUndatedTasks = new FilteredList<>(taskBook.getUndatedTasks());
        undoableTasks = new UndoList();
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        filteredDatedTasks = new FilteredList<>(taskBook.getDatedTasks());
        filteredUndatedTasks = new FilteredList<>(taskBook.getUndatedTasks());
        undoableTasks = new UndoList();
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        undoableTasks = new UndoList(); 
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
    public synchronized void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(Task target) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(target);
        updateFilteredListToShowAll();
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void completeTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        taskBook.completeTask(target);
        updateFilteredListToShowAll();
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized UndoTask undoTask() {
        return undoableTasks.removeLast();
    }

    @Override
    public synchronized void overdueTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.overdueTask(target);
        updateFilteredListToShowAll();
        indicateTaskBookChanged();
    }

    @Override
    public void addUndo(String command, ReadOnlyTask toUndo) {
        undoableTasks.addToList(command, toUndo, null);
    }
    
    @Override
    public void addUndo(String command, ReadOnlyTask initData, ReadOnlyTask finalData) {
        undoableTasks.addToList(command, initData, finalData);
    }

    //=========== Filtered Task List Accessors =============================================================== 

	@Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDatedTaskList() {
        return new UnmodifiableObservableList<>(filteredDatedTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredUndatedTaskList() {
        return new UnmodifiableObservableList<>(filteredUndatedTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        updateFilteredTaskList("NONE", "OVERDUE");
        //filteredDatedTasks.setPredicate(null);
        //filteredUndatedTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new TaskQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskList(String... keyword){
        ArrayList<String> listOfKeywords = new ArrayList<>();
        for (String word : keyword){
            listOfKeywords.add(word);
        }
        updateFilteredTaskList(new PredicateExpression(new StatusQualifier(listOfKeywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredDatedTasks.setPredicate(expression::satisfies);
        filteredUndatedTasks.setPredicate(expression::satisfies);
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

    private class TaskQualifier implements Qualifier {
        private Set<String> taskKeyWords;

        TaskQualifier(Set<String> taskKeyWords) {
            this.taskKeyWords = taskKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent()
                    || taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDatetime().toString(), keyword))
                    .findAny()
                    .isPresent()
                    || taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDescription().value, keyword))
                    .findAny()
                    .isPresent()
                    || taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTags().toString(), keyword))
                    .findAny()
                    .isPresent());
        }

        @Override
        public String toString() {
            return "task=" + String.join(", ", taskKeyWords);
        }
    }

    private class StatusQualifier implements Qualifier {
        private ArrayList<Status> statusList;

        StatusQualifier(ArrayList<String> stateKeyWords) {
            this.statusList = new ArrayList<Status>();
            for (String word : stateKeyWords){
                statusList.add(new Status(word));
            }
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            for (Status key : statusList) {
                if (task.getStatus().equals(key)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "status=" + statusList.toString();
        }
    }

}
