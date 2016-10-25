package seedu.flexitrack.model;

import javafx.collections.transformation.FilteredList;
import seedu.flexitrack.commons.core.LogsCenter;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.util.StringUtil;
import seedu.flexitrack.logic.commands.ListCommand;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.IllegalEditException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.flexitrack.commons.events.model.FlexiTrackChangedEvent;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the tasktracker data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FlexiTrack flexiTracker;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given FlexiTracker FlexiTracker and
     * its variables should not be null
     */
    public ModelManager(FlexiTrack src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with tasktracker: " + src + " and user prefs " + userPrefs);

        flexiTracker = new FlexiTrack(src);
        filteredTasks = new FilteredList<>(flexiTracker.getTasks());
    }

    public ModelManager() {
        this(new FlexiTrack(), new UserPrefs());
    }

    public ModelManager(ReadOnlyFlexiTrack initialData, UserPrefs userPrefs) {
        flexiTracker = new FlexiTrack(initialData);
        filteredTasks = new FilteredList<>(flexiTracker.getTasks());
    }

    @Override
    public void resetData(ReadOnlyFlexiTrack newData) {
        flexiTracker.resetData(newData);
        indicateFlexiTrackerChanged();
    }

    @Override
    public ReadOnlyFlexiTrack getFlexiTrack() {
        return flexiTracker;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateFlexiTrackerChanged() {
        raise(new FlexiTrackChangedEvent(flexiTracker));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        flexiTracker.removeTask(target);
        indicateFlexiTrackerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        flexiTracker.addTask(task);
        updateFilteredListToShowAll();
        indicateFlexiTrackerChanged();
    }

    @Override
    public void markTask(ReadOnlyTask targetIndex) throws IllegalValueException {
        flexiTracker.markTask(targetIndex);
        indicateFlexiTrackerChanged();
    }

    @Override
    public void unmarkTask(ReadOnlyTask targetIndex) throws IllegalValueException {
        flexiTracker.unmarkTask(targetIndex);
        indicateFlexiTrackerChanged();
    }

    @Override
    public String editTask(int taskToEdit, String[] args)
            throws TaskNotFoundException, IllegalEditException, IllegalValueException {
        String duration = flexiTracker.editTask(taskToEdit, args);
        indicateFlexiTrackerChanged();
        return duration;
    }

    // =========== Filtered Tasks List Accessors
    // ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredListToFitUserInput(String args){
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(args)));
    }
    
//    @Override
//    public void updateFilteredListToShowPastTasks(){
//        updateFilteredTaskList(new PredicateExpression(new DateQualifier(ListCommand.LIST_PAST_COMMAND)));
//    }
//    
//    @Override
//    public void updateFilteredListToShowMarkTasks(){
//        updateFilteredTaskList(new PredicateExpression(new DateQualifier(ListCommand.LIST_MARK_COMMAND)));
//    }
//    
//    @Override
//    public void updateFilteredListToShowUnmarkTasks(){
//        updateFilteredTaskList(new PredicateExpression(new DateQualifier(ListCommand.LIST_UNMARK_COMMAND)));
//    }
    
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // ==================================================

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
            if (nameKeyWords.toString().contains("f/")) {
                return nameKeyWords.stream()
                        .filter(keyword -> StringUtil.equalsIgnoreCase(task.getName().fullName, keyword)).findAny()
                        .isPresent();
            }

            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    //TODO: 
    private class DateQualifier implements Qualifier {
        private String keyWords;
        private String dateInfo; 

        DateQualifier(String keyWord) {
            this.keyWords = keyWord;
            this.dateInfo = keyWord.replace(ListCommand.LIST_FUTURE_COMMAND, "").replace(ListCommand.LIST_PAST_COMMAND, "")
                    .replace(ListCommand.LIST_UNMARK_COMMAND, "").replace(ListCommand.LIST_MARK_COMMAND, "")
                    .replace(ListCommand.LIST_LAST_WEEK_COMMAND, "").replace(ListCommand.LIST_LAST_MONTH_COMMAND, "")
                    .replace(ListCommand.LIST_NEXT_WEEK_COMMAND, "").replace(ListCommand.LIST_NEXT_MONTH_COMMAND, "").trim();
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean willBeShown=true; 

            if (keyWords.contains(ListCommand.LIST_FUTURE_COMMAND)) {
                if (task.getIsTask()){ 
                    willBeShown = DateTimeInfo.isInTheFuture(task.getDueDate());
                } else if (task.getIsEvent()){
                    willBeShown = DateTimeInfo.isInTheFuture(task.getEndTime());
                }else { 
                    willBeShown = !task.getIsDone();
                }
            } else if (keyWords.contains(ListCommand.LIST_PAST_COMMAND)){
                if (task.getIsTask()){ 
                    willBeShown = DateTimeInfo.isInThePast(task.getDueDate());
                } else {
                    willBeShown = DateTimeInfo.isInThePast(task.getEndTime());
                } 
            } else if (keyWords.contains(ListCommand.LIST_LAST_COMMAND) || keyWords.contains(ListCommand.LIST_NEXT_COMMAND)){
                willBeShown = DateTimeInfo.withInTheDuration(keyWords,task);
            } else if (!dateInfo.equals("")){
                willBeShown = DateTimeInfo.isOnTheDate(keyWords, task);
            }

            if (willBeShown==false){ 
                return false; 
            }
            if (keyWords.contains(ListCommand.LIST_UNMARK_COMMAND)){
                return !task.getIsDone();
            } else if (keyWords.contains(ListCommand.LIST_MARK_COMMAND)){
                return task.getIsDone();
            }
            
            return willBeShown;
            
        }
        
//        @Override
//        public String toString() {
//            return "name=" + String.join(", ", dateKeyWords);
//        }
    }
    
    

}
