package seedu.ggist.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.ggist.commons.core.ComponentManager;
import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.commons.events.model.TaskManagerChangedEvent;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.StringUtil;
import seedu.ggist.logic.commands.Command;
import seedu.ggist.logic.commands.CommandResult;
import seedu.ggist.logic.commands.EditCommand;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.TaskDate;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList;
import seedu.ggist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private FilteredList<Task> filteredTasks;
    private SortedList<Task> sortedTasks;
    private String today;

    public String lastListing;

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
        today = LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, dd MMM YY"));
        updateFilteredListToShowDate(today);
    }
    
    public void setLastListing(String listing) {
        lastListing = listing;
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
        updateListing();
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void doneTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.doneTask(target);
        updateListing();
        indicateTaskManagerChanged();
    }

    public synchronized void editTask(ReadOnlyTask target, String field, String value) throws TaskNotFoundException, IllegalValueException {
 
        taskManager.editTask(target, field, value);
        updateListing();
    	indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        updateListing();
        indicateTaskManagerChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    /**
     * Updates filtered list to show based on last shown listing choice
     */
    private void updateListing() {
        if (lastListing == null) {
            updateFilteredListToShowDate(today);
        } else if (lastListing.equals("")) {
            updateFilteredListToShowAllUndone();
        } else if (lastListing.equals("done")) {
            updateFilteredListToShowAllDone();
        } else if (TaskDate.isValidDateFormat(lastListing)){
            updateFilteredListToShowDate(lastListing);
        } else if (lastListing.equals("all")){
            updateFilteredListToShowAll();
        }
    }
     
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getSortedTaskList() {
        sortFilteredList();
        return new UnmodifiableObservableList<>(sortedTasks);
    }
    
    public void sortFilteredList() {
        Comparator<ReadOnlyTask> compareDateTime = new Comparator<ReadOnlyTask>(){
            public int compare (ReadOnlyTask t1, ReadOnlyTask t2){
                return t1.getStartDateTime().before(t2.getStartDateTime()) ? -1 : 
                       (t1.getEndDateTime().before(t2.getEndDateTime()) ? -1 : 1);
            }
        };
        sortedTasks = new SortedList<Task>(filteredTasks, compareDateTime);
    }

    @Override
    public void updateFilteredListToShowAll() {
        updateFilteredListToShowAll(new PredicateExpression(new AllQualifier()));
//      sortFilteredList();
    }
    public void updateFilteredListToShowAll(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowAllDone() {
        updateFilteredListToShowAllDone(new PredicateExpression(new DoneQualifier()));
//        sortFilteredList();
    }
    
    private void updateFilteredListToShowAllDone(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowAllUndone() {
        updateFilteredListToShowAllUndone(new PredicateExpression(new NotDoneQualifier()));
//        sortFilteredList();
    }
    
    private void updateFilteredListToShowAllUndone(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowDate(String keywords){
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(keywords)));
 //       sortFilteredList();
    }

  /*  private void updateFilteredListToShowDate(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
*/
    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
//        sortFilteredList();
    }

    public void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowChanges() {
        System.out.println(filteredTasks.getPredicate());
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
    
    private class AllQualifier implements Qualifier {
        AllQualifier() {}
        
        public boolean run(ReadOnlyTask task) {
            return (task != null);
        }
    }
    
    private class NotDoneQualifier implements Qualifier {
        
        NotDoneQualifier() {}
        
        public boolean run(ReadOnlyTask task) {
            return (!task.getDone());
        }
    }
    
    private class DoneQualifier implements Qualifier {
        
        DoneQualifier() {}
        
        public boolean run(ReadOnlyTask task) {
            return task.getDone();
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> taskNameKeyWords;

        NameQualifier(Set<String> taskNameKeyWords) {
            this.taskNameKeyWords = taskNameKeyWords;
        }

        @Override

        public boolean run(ReadOnlyTask task) {
            return taskNameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", taskNameKeyWords);
        }
    }
    
    private class DateQualifier implements Qualifier {
        private String taskDateKeyWords;

        DateQualifier(String taskDateKeyWords) {
            this.taskDateKeyWords = taskDateKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (taskDateKeyWords.equalsIgnoreCase(task.getStartDate().toString()) || 
                   taskDateKeyWords.equalsIgnoreCase(task.getEndDate().toString())) && 
                   !task.getDone() ||
                   (task.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) && 
                   task.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED) && 
                   !task.getDone());          
                   
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", taskDateKeyWords);
        }
    }
}
