package seedu.jimi.model;

import java.util.Set;
import java.util.logging.Logger;

import seedu.jimi.commons.core.ComponentManager;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.events.model.TaskBookChangedEvent;
import seedu.jimi.commons.events.ui.ShowTaskPanelSectionEvent;
import seedu.jimi.model.FilteredListManager.ListId;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;
import seedu.jimi.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of Jimi's data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    
    private final TaskBook taskBook;
    private final FilteredListManager filteredListManager;
    
    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;
        
        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);
        
        taskBook = new TaskBook(src);
        
        this.filteredListManager = new FilteredListManager(taskBook);
    }
    
    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }
    
    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        
        this.filteredListManager = new FilteredListManager(taskBook);
    }
    
    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }
    
    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }
    
    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }
    
    /** Raises and event to indicate user request to show task panel sections. */
    public void showTaskPanelSection(String sectionToShow) {
        raise(new ShowTaskPanelSectionEvent(sectionToShow));
    }
    
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void addTask(ReadOnlyTask task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateAllFilteredListsShowDefault();
        indicateTaskBookChanged();
    }
    
    /**
     * Replaces a task in task book.
     * 
     * @param newTask Task to be replaced with.
     * @param targetIndex Index of oldTask to be replaced by.
     */
    @Override
    public void replaceTask(ReadOnlyTask oldTask, ReadOnlyTask newTask) {
        taskBook.replaceTask(oldTask, newTask);
        indicateTaskBookChanged();
    }
    
    /**
     * Completes a task in task book.
     * 
     * @param taskToComplete Task to set to complete/incomplete.
     * @param isComplete True, if task is to be set to completed.
     * @throws TaskNotFoundException 
     */
    @Override
    public synchronized void completeTask(ReadOnlyTask taskToComplete, boolean isComplete)
            throws TaskNotFoundException {
        taskBook.completeTask(taskToComplete, isComplete);
        indicateTaskBookChanged();
    }
    
    /*
     * ==================================================================
     *                  Updating Filtered Lists 
     * ==================================================================
     */
    
    @Override
    public void updateAllFilteredListsShowDefault() {
        this.filteredListManager.updateFilteredListToDefault();
    }
    
    @Override
    public void updateFilteredAgendaTaskList(Set<String> keywords) {
        this.filteredListManager.updateRequiredFilteredTaskList(ListId.TASKS_AGENDA, keywords);
    }

    @Override
    public void updateFilteredAgendaEventList(Set<String> keywords) {
        this.filteredListManager.updateRequiredFilteredTaskList(ListId.EVENTS_AGENDA, keywords);
    }
    
    /*
     * ==================================================================
     *                  Accessing Filtered Lists 
     * ==================================================================
     */
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.FLOATING_TASKS);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredIncompleteTaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.INCOMPLETE);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.COMPLETED);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay1TaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.DAY_AHEAD_0);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay2TaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.DAY_AHEAD_1);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay3TaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.DAY_AHEAD_2);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay4TaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.DAY_AHEAD_3);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay5TaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.DAY_AHEAD_4);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay6TaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.DAY_AHEAD_5);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay7TaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.DAY_AHEAD_6);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredAgendaTaskList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.TASKS_AGENDA);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredAgendaEventList() {
        return this.filteredListManager.getRequiredFilteredTaskList(ListId.EVENTS_AGENDA);
    }
}
