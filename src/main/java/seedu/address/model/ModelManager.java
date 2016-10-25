package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.ui.ChangeToListDoneViewEvent;
import seedu.address.commons.events.ui.ChangeToListUndoneViewEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.UniqueTaskList;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredUndoneTasks;
    private final FilteredList<Task> filteredDoneTasks;
    private Boolean isDoneList = false;
    
    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredUndoneTasks = new FilteredList<>(taskManager.getUndoneTasks());
        filteredDoneTasks = new FilteredList<>(taskManager.getDoneTasks());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredUndoneTasks = new FilteredList<>(taskManager.getUndoneTasks());
        filteredDoneTasks = new FilteredList<>(taskManager.getDoneTasks());
        
    }

    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetUndoneData(newData);
        indicateTaskManagerChanged();
    }
    
    @Override
    public void resetDoneData(ReadOnlyTaskManager newData) {
        taskManager.resetDoneData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask floatingTask) throws TaskNotFoundException {
        taskManager.removeFloatingTask(floatingTask);
        indicateTaskManagerChanged();
    }
    
    //@@author A0139498J
    @Override
    public synchronized void addTask(Task task) {
        taskManager.addTask(task);
        updateFilteredListsToShowAll();
        indicateTaskManagerChanged();
    }
    

    @Override
    public synchronized void addDoneTask(Task task) {
        taskManager.addDoneTask(task);
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void deleteDoneTask(ReadOnlyTask task) throws TaskNotFoundException {
        taskManager.removeDoneTask(task);
        indicateTaskManagerChanged();
    }
    
    //@@author 
    @Override
    public void addTasks(List<Task> tasks) {
        for (Task task: tasks){
            addTask(task);
        }
    }

    @Override
    public void deleteTasks(List<ReadOnlyTask> targets) {
        for (ReadOnlyTask target : targets){
            try {
                deleteTask(target);
            } catch (TaskNotFoundException e) {
                // TODO Auto-generated catch block
                // Do something here? Indicate to user ?
                e.printStackTrace();
            }
        }
        
    }
    
    @Override
    public void addDoneTasks(List<Task> tasks) {
        for (Task task: tasks) {
            addDoneTask(task);
        }
        
    }

    @Override
    public void deleteDoneTasks(List<ReadOnlyTask> targets) {
        for (ReadOnlyTask target : targets) {
            try {
                deleteDoneTask(target);
            } catch (TaskNotFoundException e) {
                // TODO Auto-generated catch block
                // Do something here? Indicate to user ?
                e.printStackTrace();
            }
        }
    }
    //@@author A0139498J
    @Override
    public Boolean isCurrentListDoneList() {
        return isDoneList;
    }

    @Override
    public void setCurrentListToBeDoneList() {
        EventsCenter.getInstance().post(new ChangeToListDoneViewEvent());
        isDoneList = true;
    }
  
    @Override
    public void setCurrentListToBeUndoneList() {
        EventsCenter.getInstance().post(new ChangeToListUndoneViewEvent());
        isDoneList = false;
    }
    
    //@@author
    public synchronized void editTask(ReadOnlyTask floatingTask, Name name, Date startDate,
            Date endDate, Priority priority, RecurrenceRate recurrenceRate) {
        taskManager.editFloatingTask(floatingTask, name, startDate, endDate, priority, recurrenceRate);
        updateFilteredListsToShowAll();
        indicateTaskManagerChanged();
    }

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredUndoneTaskList() {
        return new UnmodifiableObservableList<>(filteredUndoneTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDoneTaskList() {
        return new UnmodifiableObservableList<>(filteredDoneTasks);
    }

    public void TaskManager() {
        filteredUndoneTasks.setPredicate(null);
        filteredDoneTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredUndoneTaskList(Set<String> keywords){
        updateFilteredUndoneTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredUndoneTaskList(Expression expression) {
        filteredUndoneTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredDoneTaskList(Set<String> keywords){
        updateFilteredDoneTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredDoneTaskList(Expression expression) {
        filteredDoneTasks.setPredicate(expression::satisfies);
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
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().name, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    //@@author A0139498J
    @Override
    public void updateFilteredListsToShowAll() {
        filteredUndoneTasks.setPredicate(null);
        filteredDoneTasks.setPredicate(null);
    }
    
}
