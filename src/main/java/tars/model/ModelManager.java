package tars.model;

import javafx.collections.transformation.FilteredList;
import tars.commons.core.ComponentManager;
import tars.commons.core.LogsCenter;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.flags.Flag;
import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.model.task.Task;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.model.task.DateTime.IllegalDateException;
import tars.model.task.ReadOnlyTask;
import tars.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.DateTimeException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of tars data. All changes to any model should
 * be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Tars tars;
    private final FilteredList<Task> filteredTasks;
    private final Stack<Command> undoableCmdHistStack;

    private static final String LIST_KEYWORD_DONE = "done";
    private static final String LIST_KEYWORD_UNDONE = "undone";

    /**
     * Initializes a ModelManager with the given Tars Tars and its variables
     * should not be null
     */
    public ModelManager(Tars src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with tars: " + src + " and user prefs " + userPrefs);

        tars = new Tars(src);
        filteredTasks = new FilteredList<>(tars.getTasks());
        undoableCmdHistStack = new Stack<>();
    }

    public ModelManager() {
        this(new Tars(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTars initialData, UserPrefs userPrefs) {
        tars = new Tars(initialData);
        filteredTasks = new FilteredList<>(tars.getTasks());
        undoableCmdHistStack = new Stack<>();
    }

    @Override
    public void resetData(ReadOnlyTars newData) {
        tars.resetData(newData);
        indicateTarsChanged();
    }

    @Override
    public ReadOnlyTars getTars() {
        return tars;
    }

    @Override
    public Stack<Command> getUndoableCmdHist() {
        return undoableCmdHistStack;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTarsChanged() {
        raise(new TarsChangedEvent(tars));
    }

    @Override
    /**
     * @@author A0121533W
     */
    public synchronized Task editTask(ReadOnlyTask toEdit, HashMap<Flag, String> argsToEdit)
            throws TaskNotFoundException, DateTimeException, IllegalDateException,
            DuplicateTagException, TagNotFoundException, IllegalValueException {
        Task editedTask = tars.editTask(toEdit, argsToEdit);
        indicateTarsChanged();
        return editedTask;
    }
    
    @Override
    public synchronized void unEditTask(Task toUndo, Task replacement)
            throws DuplicateTaskException {
        tars.replaceTask(toUndo, replacement);
        indicateTarsChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        tars.removeTask(target);
        indicateTarsChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        tars.addTask(task);
        updateFilteredListToShowAll();
        indicateTarsChanged();
    }

    @Override
    /**
     * @@author A0121533W
     */
    public synchronized void mark(ArrayList<ReadOnlyTask> toMarkList, String status)
            throws DuplicateTaskException {
        tars.mark(toMarkList, status);
        indicateTarsChanged();

    }

    // =========== Filtered Task List Accessors ===========

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        if (keywords.contains(LIST_KEYWORD_DONE) || keywords.contains(LIST_KEYWORD_UNDONE)) {
            updateFilteredTaskList(new PredicateExpression(new ListQualifier(keywords)));
        } else {
            updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
        }
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering ==========

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

        /**
         * @@author A0124333U
         * @param task
         * @return true if ALL keywords are found in the task name
         */
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().taskName, keyword))
                    .count() == nameKeyWords.size();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class ListQualifier implements Qualifier {
        private Set<String> listArguments;

        ListQualifier(Set<String> listArguments) {
            this.listArguments = listArguments;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return listArguments.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getStatus().toString(), keyword)).findAny()
                    .isPresent();
        }

    }

}
