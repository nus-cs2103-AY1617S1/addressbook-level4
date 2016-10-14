package seedu.inbx0.model;

import javafx.collections.transformation.FilteredList;
import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.events.model.TaskListChangedEvent;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.commons.util.StringUtil;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.UniqueTaskList;
import seedu.inbx0.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.inbx0.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given TaskList
     * TaskList and its variables should not be null
     */
    public ModelManager(TaskList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskList = new TaskList(src);
        filteredTasks = new FilteredList<>(taskList.getTasks());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskList initialData, UserPrefs userPrefs) {
        taskList = new TaskList(initialData);
        filteredTasks = new FilteredList<>(taskList.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void editTask(ReadOnlyTask target, Task task) throws TaskNotFoundException, DuplicateTaskException {
        taskList.editTask(target, task);
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

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
    public void updateFilteredTaskList(int type, Set<String> keywords){
    	switch(type) {
    		case 0: updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    				break;
    		case 1: updateFilteredTaskList(new PredicateExpression(new StartDateQualifier(keywords)));
					break;
    		case 2: updateFilteredTaskList(new PredicateExpression(new EndDateQualifier(keywords)));
    				break;
    		case 3: updateFilteredTaskList(new PredicateExpression(new LevelQualifier(keywords)));
    				break;
    		case 4: updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywords)));
    				break;
    		default: ;
    	}
    }
    
    @Override
    public void updateFilteredTaskList(String date, String preposition){
        System.out.println(preposition);
        if(preposition == "")
            updateFilteredTaskList(new PredicateExpression(new StartOnAndEndOnDateQualifier(date)));
        else
            updateFilteredTaskList(new PredicateExpression(new EndUntilDateQualifier(date)));
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

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getAsText(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    private class StartOnAndEndOnDateQualifier implements Qualifier {
        private String date;
        
        StartOnAndEndOnDateQualifier(String date) {
            this.date = date;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return (date.equals(task.getStartDate().value) | date.equals(task.getEndDate().value));
        }
        
        @Override
        public String toString() {
            return "date= " + date;
        }
    }
    
    private class EndUntilDateQualifier implements Qualifier {
        private String date;
        
        EndUntilDateQualifier(String date) {
            this.date = date;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            Date today = null;
            boolean isBeforeOrOnDueButAfterOrOnCurrent = false;
            try {
                today = new Date("today");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            
            int dueByNumberDate = Integer.parseInt(date.replaceAll("\\D+",""));
            int dueByDay = dueByNumberDate / 1000000;
            int dueByMonth = (dueByNumberDate / 10000) % 100;
            int dueByYear = dueByNumberDate % 10000;
         
            if(dueByYear > task.getEndDate().getYear() && task.getEndDate().getYear() > today.getYear()) 
                isBeforeOrOnDueButAfterOrOnCurrent = true;
            
            
            if(dueByYear == task.getEndDate().getYear() && task.getEndDate().getYear() == today.getYear() &&
               dueByMonth > task.getEndDate().getMonth() && task.getEndDate().getMonth() > today.getMonth())
                isBeforeOrOnDueButAfterOrOnCurrent = true;
            
            if(dueByYear == task.getEndDate().getYear() && task.getEndDate().getYear() == today.getYear() && 
               dueByMonth == task.getEndDate().getMonth() && task.getEndDate().getMonth() == today.getMonth() &&
               dueByDay >= task.getEndDate().getDay() && task.getEndDate().getDay() >= today.getDay())
                isBeforeOrOnDueButAfterOrOnCurrent = true;
            
            return isBeforeOrOnDueButAfterOrOnCurrent;
                   
        }
        
        @Override
        public String toString() {
            return "date= " + date;
        }
    }

    private class StartDateQualifier implements Qualifier {
        private Set<String> startDateKeyWords;

        StartDateQualifier(Set<String> startDateKeyWords) {
            this.startDateKeyWords = startDateKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return startDateKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getStartDate().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "startDate=" + String.join(", ", startDateKeyWords);
        }
    }

    private class EndDateQualifier implements Qualifier {
        private Set<String> endDateKeyWords;

        EndDateQualifier(Set<String> endDateKeyWords) {
            this.endDateKeyWords = endDateKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return endDateKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getEndDate().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "endDate=" + String.join(", ", endDateKeyWords);
        }
    }
    
    private class LevelQualifier implements Qualifier {
        private Set<String> levelKeyWords;

        LevelQualifier(Set<String> levelKeyWords) {
            this.levelKeyWords = levelKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return levelKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getLevel().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "level=" + String.join(", ", levelKeyWords);
        }
    }

    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;

        TagQualifier(Set<String> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return tagKeyWords.stream()
                    .filter(keyword -> task.tagsString().contains(keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeyWords);
        }
    }
}
