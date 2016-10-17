package seedu.whatnow.model;

import javafx.collections.transformation.FilteredList;
import seedu.whatnow.commons.core.ComponentManager;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.events.model.WhatNowChangedEvent;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the WhatNow data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WhatNow whatNow;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredSchedules;

    /**
     * Initializes a ModelManager with the given WhatNow
     * WhatNow and its variables should not be null
     */
    public ModelManager(WhatNow src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with WhatNow: " + src + " and user prefs " + userPrefs);

        whatNow = new WhatNow(src);
        filteredTasks = new FilteredList<>(whatNow.getTasks());
        filteredSchedules = new FilteredList<>(whatNow.getTasks());
    }

    public ModelManager() {
        this(new WhatNow(), new UserPrefs());
    }

    public ModelManager(ReadOnlyWhatNow initialData, UserPrefs userPrefs) {
        whatNow = new WhatNow(initialData);
        filteredTasks = new FilteredList<>(whatNow.getTasks());
        filteredSchedules = new FilteredList<>(whatNow.getTasks());
    }

    @Override
    public void resetData(ReadOnlyWhatNow newData) {
        whatNow.resetData(newData);
        indicateWhatNowChanged();
    }

    @Override
    public ReadOnlyWhatNow getWhatNow() {
        return whatNow;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateWhatNowChanged() {
        raise(new WhatNowChangedEvent(whatNow));
    }
    
    @Override
    public synchronized void changeTask(ReadOnlyTask target) throws DataConversionException, IOException, TaskNotFoundException {
        whatNow.changeTask(target);
        indicateWhatNowChanged();
    }
    
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        whatNow.removeTask(target);
        indicateWhatNowChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        whatNow.addTask(task);
        updateFilteredListToShowAll();
        indicateWhatNowChanged();
    }
    
    @Override
    public synchronized void updateTask(ReadOnlyTask old, Task toUpdate) throws TaskNotFoundException {
        whatNow.updateTask(old, toUpdate);
        indicateWhatNowChanged();
    }
    
    @Override
    public synchronized void markTask(ReadOnlyTask target) throws TaskNotFoundException {
        whatNow.markTask(target);
        indicateWhatNowChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        String[] status = {"incomplete"};
        Set<String> keyword = new HashSet<>(Arrays.asList(status));
        updateFilteredListToShowAllByStatus(keyword);
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList(Set<String> keyword) {
        updateFilteredTaskList(keyword);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    
    @Override
    public void updateFilteredListToShowAllByStatus(Set<String> keyword) {
        updateFilteredTaskList(new PredicateExpression(new TaskStatusQualifier(keyword)));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    //=========== Filtered Schedule List Accessors ===============================================================
    
    @Override 
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList() {
        String[] status = {"completed"}; //Test only, to be changed to schedule task
        Set<String> keyword = new HashSet<>(Arrays.asList(status));
        updateFilteredScheduleListToShowAllByStatus(keyword);
        return new UnmodifiableObservableList<>(filteredSchedules);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredScheduleList() {
        return new UnmodifiableObservableList<>(filteredSchedules);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList(Set<String> keyword) {
        updateFilteredScheduleList(keyword);
        return new UnmodifiableObservableList<>(filteredSchedules);
    }
    
    @Override
    public void updateFilteredScheduleListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    
    @Override
    public void updateFilteredScheduleListToShowAllByStatus(Set<String> keyword) {
        updateFilteredScheduleList(new PredicateExpression(new TaskStatusQualifier(keyword)));
    }

    @Override
    public void updateFilteredScheduleList(Set<String> keywords){
        updateFilteredScheduleList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredScheduleList(Expression expression) {
        filteredSchedules.setPredicate(expression::satisfies);
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
    
    private class TaskStatusQualifier implements Qualifier {
        private Set<String> status;
        
        TaskStatusQualifier(Set<String> status) {
            this.status = status;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return status.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getStatus(), keyword))
                    .findAny()
                    .isPresent();
        }
        
        @Override
        public String toString() {
            return "Status=" + String.join(", ", status);
        }
    }

}
