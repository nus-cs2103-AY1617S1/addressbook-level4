package seedu.savvytasker.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.commons.events.model.AliasSymbolChangedEvent;
import seedu.savvytasker.commons.events.model.SavvyTaskerChangedEvent;
import seedu.savvytasker.commons.util.StringUtil;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.DuplicateSymbolKeywordException;
import seedu.savvytasker.model.alias.SymbolKeywordNotFoundException;
import seedu.savvytasker.model.task.FindType;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the savvy tasker data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SavvyTasker savvyTasker;
    private final FilteredList<Task> filteredTasks;
    private final SortedList<Task> sortedAndFilteredTasks;

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
        updateFilteredListToShowActive(); // shows only active tasks on start
    }

    public ModelManager() {
        this(new SavvyTasker());
    }

    public ModelManager(ReadOnlySavvyTasker initialData) {
        savvyTasker = new SavvyTasker(initialData);
        filteredTasks = new FilteredList<>(savvyTasker.getTasks());
        sortedAndFilteredTasks = new SortedList<>(filteredTasks, new TaskSortedByDefault());
        updateFilteredListToShowActive(); // shows only active tasks on start
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

    private void indicateAliasSymbolAdded(AliasSymbol symbol) {
        raise(new AliasSymbolChangedEvent(symbol, AliasSymbolChangedEvent.Action.Added));
    }
    
    private void indicateAliasSymbolRemoved(AliasSymbol symbol) {
        raise(new AliasSymbolChangedEvent(symbol, AliasSymbolChangedEvent.Action.Removed));
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
    public synchronized void addTask(Task t) throws DuplicateTaskException {
        t.setId(savvyTasker.getNextTaskId());
        savvyTasker.addTask(t);
        updateFilteredListToShowActive();
        indicateSavvyTaskerChanged();
    }

    @Override
    public synchronized void addAliasSymbol(AliasSymbol symbol) throws DuplicateSymbolKeywordException {
        savvyTasker.addAliasSymbol(symbol);
        indicateSavvyTaskerChanged();
        indicateAliasSymbolAdded(symbol);
    }

    @Override
    public synchronized void removeAliasSymbol(AliasSymbol symbol) throws SymbolKeywordNotFoundException {
        savvyTasker.removeAliasSymbol(symbol);
        indicateSavvyTaskerChanged();
        indicateAliasSymbolRemoved(symbol);
    }

    //=========== Filtered/Sorted Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<ReadOnlyTask>(sortedAndFilteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<Task> getFilteredTaskListTask() {
        return new UnmodifiableObservableList<Task>(sortedAndFilteredTasks);
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
    public void updateFilteredTaskList(FindType findType, String[] keywords) {
        assert findType != null;
        Qualifier qualifier = null;
        switch (findType)
        {
        case Partial:
            qualifier = new TaskNamePartialMatchQualifier(keywords);
            break;
        case Full:
            qualifier = new TaskNameFullMatchQualifier(keywords);
            break;
        case Exact:
            qualifier = new TaskNameExactMatchQualifier(keywords);
            break;
        default:
            assert false; // should never get here.
        }
        updateFilteredTaskList(new PredicateExpression(qualifier));
    }
    
    private void updateFilteredTaskList(Expression expression) {
        updateFilteredTaskList(expression, new TaskSortedByDefault());
    }

    private void updateFilteredTaskList(Expression expression, Comparator<Task> comparator) {
        filteredTasks.setPredicate(expression::satisfies);
        sortedAndFilteredTasks.setComparator(comparator);
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
        
        /**
         * Helper method to build Set<String> from String[]
         * @param keywords list of keywords
         */
        default Set<String> createSet(String[] keywords) {
            HashSet<String> _keywords = new HashSet<String>();
            for (String keyword : keywords) {
                _keywords.add(keyword);
            }
            return _keywords;
        }
    }

    /**
     * Qualifier matching a partial word from the set of keywords
     * @author A0139915W
     */
    private class TaskNamePartialMatchQualifier implements Qualifier {
        private Set<String> keyWordsToMatch;

        TaskNamePartialMatchQualifier(String[] keyWordsToMatch) {
            this.keyWordsToMatch = createSet(keyWordsToMatch);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keyWordsToMatch.stream()
                    .filter(keyword -> StringUtil.containsPartialIgnoreCase(task.getTaskName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "taskName(FullMatch)=" + String.join(", ", keyWordsToMatch);
        }
    }

    /**
     * Qualifier matching a full word from the set of keywords
     * @author A0139915W
     */
    private class TaskNameFullMatchQualifier implements Qualifier {
        private Set<String> keyWordsToMatch;

        TaskNameFullMatchQualifier(String[] keyWordsToMatch) {
            this.keyWordsToMatch = createSet(keyWordsToMatch);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keyWordsToMatch.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTaskName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "taskName(FullMatch)=" + String.join(", ", keyWordsToMatch);
        }
    }

    /**
     * Qualifier matching a exactly from the set of keywords
     * @author A0139915W
     */
    private class TaskNameExactMatchQualifier implements Qualifier {
        private Set<String> keyWordsToMatch;

        TaskNameExactMatchQualifier(String[] keyWordsToMatch) {
            this.keyWordsToMatch = new HashSet<String>();
            this.keyWordsToMatch.add(buildSingleString(keyWordsToMatch));
        }
        
        /**
         * Builds a single string to be matched exactly against the task name.
         * @param keyWordsToMatch list of keywords to match.
         * @return A single string built from the list of keywords.
         */
        private String buildSingleString(String[] keyWordsToMatch) {
            StringBuilder sb = new StringBuilder();
            List<String> keywords = Arrays.asList(keyWordsToMatch);
            Iterator<String> itr = keywords.iterator();
            while (itr.hasNext()) {
                sb.append(itr.next());
                if (itr.hasNext()) {
                    sb.append(" ");
                }
            }
            return sb.toString();
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keyWordsToMatch.stream()
                    .filter(keyword -> StringUtil.containsExactIgnoreCase(task.getTaskName(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "taskName(FullMatch)=" + String.join(", ", keyWordsToMatch);
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
                // Priority Level can be nulls
                // Check for existence of priorityLevel before comparing
                if (task1.getPriority() == null &&
                    task2.getPriority() == null) {
                    return 0;
                } else if (task1.getPriority() == null) {
                    return 1;
                } else if (task2.getPriority() == null) {
                    return -1;
                } else {
                    return task2.getPriority().compareTo(task1.getPriority());
                }
            }
        }
        
    }

}
