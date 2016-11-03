package seedu.task.model;



import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Status;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
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
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        if (!task.getDeadline().toString().isEmpty()) {
            String strDatewithTime = task.getDeadline().toString().replace(" ", "T");
            LocalDateTime aLDT = LocalDateTime.parse(strDatewithTime);

            Date currentDate=new Date();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());

            if (aLDT.isBefore(localDateTime)) {

                task = new Task (task.getName(), task.getStartTime(), task.getEndTime(), task.getDeadline(), task.getTags(), new Status(task.getStatus().getDoneStatus(), true, task.getStatus().getFavoriteStatus()));

            }
            else{
                task = new Task (task.getName(), task.getStartTime(), task.getEndTime(), task.getDeadline(), task.getTags(), new Status(task.getStatus().getDoneStatus(), false, task.getStatus().getFavoriteStatus()));

            }

        }
        else {
            task = new Task (task.getName(), task.getStartTime(), task.getEndTime(), task.getDeadline(), task.getTags(), new Status(task.getStatus().getDoneStatus(), false, task.getStatus().getFavoriteStatus()));

        }
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        
       
    }
    
    //@@author A0147335E-reused
    @Override
    public synchronized void addTask(int index, Task task) throws UniqueTaskList.DuplicateTaskException {
        if (!task.getDeadline().toString().isEmpty()) {
            String strDatewithTime = task.getDeadline().toString().replace(" ", "T");
            LocalDateTime aLDT = LocalDateTime.parse(strDatewithTime);

            Date currentDate=new Date();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());

            if (aLDT.isBefore(localDateTime)) {

                task = new Task (task.getName(), task.getStartTime(), task.getEndTime(), task.getDeadline(), task.getTags(), new Status(task.getStatus().getDoneStatus(), true, task.getStatus().getFavoriteStatus()));

            }
            else{
                task = new Task (task.getName(), task.getStartTime(), task.getEndTime(), task.getDeadline(), task.getTags(), new Status(task.getStatus().getDoneStatus(), false, task.getStatus().getFavoriteStatus()));

            }

        }
        else {
            task = new Task (task.getName(), task.getStartTime(), task.getEndTime(), task.getDeadline(), task.getTags(), new Status(task.getStatus().getDoneStatus(), false, task.getStatus().getFavoriteStatus()));

        }
        taskManager.addTask(index, task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    //@@author
    
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    // @@author A0147944U
    /**
     * Select sorting method based on keyword
     * 
     * @param keyword keyword given by user to sort tasks by
     */
    public void sortFilteredTaskList(String keyword) {
        if (keyword.equals("Deadline")) {
            taskManager.sortByDeadline();
        } else if (keyword.equals("Start Time")) {
            taskManager.sortByStartTime();
        } else if (keyword.equals("End Time")) {
            taskManager.sortByEndTime();
        } else if (keyword.equals("Completed")) {
            taskManager.sortByDoneStatus();
        } else if (keyword.equals("Name")) {
            taskManager.sortByName();
        } else {
            taskManager.sortByDefaultRules();
        }
        indicateTaskManagerChanged();
    }
    // @@author
    
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
            
            String name = task.getAsText().toLowerCase();
            
            return nameKeyWords.stream()
                    .filter(keyword -> name.indexOf(keyword.toLowerCase())>=0)
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
