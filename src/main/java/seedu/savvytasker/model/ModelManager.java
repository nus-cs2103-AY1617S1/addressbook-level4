package seedu.savvytasker.model;

import javafx.collections.transformation.FilteredList;
import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.commons.events.model.SavvyTaskerChangedEvent;
import seedu.savvytasker.commons.util.StringUtil;
import seedu.savvytasker.model.person.ReadOnlyTask;
import seedu.savvytasker.model.person.Task;
import seedu.savvytasker.model.person.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.person.TaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SavvyTasker savvyTasker;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given SavvyTasker
     * AddressBook and its variables should not be null
     */
    public ModelManager(SavvyTasker src) {
        super();
        assert src != null;

        logger.fine("Initializing with address book: " + src);

        savvyTasker = new SavvyTasker(src);
        filteredTasks = new FilteredList<>(savvyTasker.getTasks());
    }

    public ModelManager() {
        this(new SavvyTasker());
    }

    public ModelManager(ReadOnlySavvyTasker initialData) {
        savvyTasker = new SavvyTasker(initialData);
        filteredTasks = new FilteredList<>(savvyTasker.getTasks());
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
    public synchronized void addTask(Task t) throws DuplicateTaskException {
        savvyTasker.addTask(t);
        updateFilteredListToShowAll();
        indicateSavvyTaskerChanged();
    }

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<ReadOnlyTask>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new TaskNameExactMatchQualifier(keywords)));
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

}
