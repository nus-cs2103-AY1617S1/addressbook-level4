package seedu.savvytasker.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.commons.events.model.SavvyTaskerChangedEvent;
import seedu.savvytasker.commons.util.StringUtil;
import seedu.savvytasker.model.person.ReadOnlyTask;
import seedu.savvytasker.model.person.Task;
import seedu.savvytasker.model.person.TaskList.TaskNotFoundException;

import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the savvy tasker data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SavvyTasker savvyTasker;
    private final FilteredList<Task> filteredTasks;
    private SortedList<Task> sortedAndFilteredTasks;

    /**
     * Initializes a ModelManager with the given SavvyTasker
     * and its variables should not be null
     */
    public ModelManager(SavvyTasker src) {
        super();
        assert src != null;

        logger.fine("Initializing with savvy tasker: " + src);

        savvyTasker = new SavvyTasker(src);
        filteredTasks = new FilteredList<>(savvyTasker.getTasks());
        sortedAndFilteredTasks = new SortedList<>(filteredTasks, new TaskSortedByDefault());
        
    }

    public ModelManager() {
        this(new SavvyTasker());
    }

    public ModelManager(ReadOnlySavvyTasker initialData) {
        savvyTasker = new SavvyTasker(initialData);
        filteredTasks = new FilteredList<>(savvyTasker.getTasks());
        sortedAndFilteredTasks = new SortedList<>(filteredTasks, new TaskSortedByDefault());
    }

    @Override
    public void resetData(ReadOnlySavvyTasker newData) {
        savvyTasker.resetData(newData);
        indicateSavvyTaskerChanged();
    }

    @Override
    public ReadOnlySavvyTasker getSavvyTasker() {
        return savvyTasker;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateSavvyTaskerChanged() {
        raise(new SavvyTaskerChangedEvent(savvyTasker));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        savvyTasker.removeTask(target);
        indicateSavvyTaskerChanged();
    }

    @Override
    public void modifyTask(ReadOnlyTask target, Task replacement) throws TaskNotFoundException {
        savvyTasker.replaceTask(target, replacement);
        indicateSavvyTaskerChanged();
    }

    @Override
    public synchronized void addTask(Task t) {
        savvyTasker.addTask(t);
        updateFilteredListToShowActive();
        indicateSavvyTaskerChanged();
    }

    //=========== Filtered/Sorted Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<ReadOnlyTask>(sortedAndFilteredTasks);
    }

    @Override
    public void updateFilteredListToShowActiveSortedByDueDate() {
        updateFilteredListToShowActive(new TaskSortedByDueDate());
    }

    @Override
    public void updateFilteredListToShowActiveSortedByPriorityLevel() {
        updateFilteredListToShowActive(new TaskSortedByPriorityLevel());
    }

    @Override
    public void updateFilteredListToShowActive() {
        updateFilteredTaskList(new PredicateExpression(new TaskIsActiveQualifier()));
    }
    
    private void updateFilteredListToShowActive(Comparator<Task> comparator) {
        updateFilteredTaskList(
                new PredicateExpression(new TaskIsActiveQualifier()),
                comparator);
    }
    
    @Override
    public void updateFilteredListToShowArchived() {
        updateFilteredTaskList(new PredicateExpression(new TaskIsArchivedQualifier()));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TaskNameExactMatchQualifier(keywords)));
    }
    
    private void updateFilteredTaskList(Expression expression) {
        updateFilteredTaskList(expression, new TaskSortedByDefault());
    }

    private void updateFilteredTaskList(Expression expression, Comparator<Task> comparator) {
        filteredTasks.setPredicate(expression::satisfies);
        sortedAndFilteredTasks = new SortedList<>(filteredTasks, comparator);
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

    private class TaskNameExactMatchQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        TaskNameExactMatchQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTaskName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    /**
     * Qualifier for checking if {@link Task} is active. Tasks that are not archived are active.
     * @author A0139915W
     *
     */
    private class TaskIsActiveQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isArchived() == false;
        }

        @Override
        public String toString() {
            return "isArchived=false";
        }
    }
    
    /**
     * Qualifier for checking if {@link Task} is archived
     * @author A0139915W
     *
     */
    private class TaskIsArchivedQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isArchived() == true;
        }

        @Override
        public String toString() {
            return "isArchived=true";
        }
    }
    
    //========== Inner classes/interfaces used for sorting ==================================================
    
    /**
     * Compares {@link Task} by their default field, id
     * @author A0139915W
     *
     */
    private class TaskSortedByDefault implements Comparator<Task> {
        
        @Override
        public int compare(Task task1, Task task2) {
            if (task1 == null && task2 == null) return 0;
            else if (task1 == null) return 1;
            else if (task2 == null) return -1;
            else return task1.getId() - task2.getId();
        }
        
    }
    
    /**
     * Compares {@link Task} by their DueDate
     * @author A0139915W
     *
     */
    private class TaskSortedByDueDate implements Comparator<Task> {

        @Override
        public int compare(Task task1, Task task2) {
            if (task1 == null && task2 == null) return 0;
            else if (task1 == null) return 1;
            else if (task2 == null) return -1;
            else {
                // End dates can be nulls (floating tasks)
                // Check for existence of endDateTime before comparing
                if (task1.getEndDateTime() == null &&
                    task2.getEndDateTime() == null) {
                    return 0;
                } else if (task1.getEndDateTime() == null) {
                    return 1;
                } else if (task2.getEndDateTime() == null) {
                    return -1;
                } else {
                    return task1.getEndDateTime().compareTo(task2.getEndDateTime());
                }
            }
        }
        
    }
    
    /**
     * Compares {@link Task} by their PriorityLevel
     * @author A0139915W
     *
     */
    private class TaskSortedByPriorityLevel implements Comparator<Task> {

        @Override
        public int compare(Task task1, Task task2) {
            if (task1 == null && task2 == null) return 0;
            else if (task1 == null) return 1;
            else if (task2 == null) return -1;
            else {
                int result =task1.getPriority().compareTo(task2.getPriority());
                return result;
            }
        }
        
    }

}
