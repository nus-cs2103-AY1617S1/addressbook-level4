package seedu.jimi.model;

import java.util.Set;
import java.util.logging.Logger;

import seedu.jimi.commons.core.ComponentManager;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.events.model.TaskBookChangedEvent;
import seedu.jimi.commons.events.ui.ShowTaskPanelSectionEvent;
import seedu.jimi.model.FilteredListManager.ListId;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;
import seedu.jimi.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * @@author A0148040R
 * Represents the in-memory model of Jimi's data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    
    private final TaskBook taskBook;
    private final FilteredListManager filteredListManager;
    private final UserPrefs userPrefs;
    
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
        this.userPrefs = userPrefs;
        this.filteredListManager = new FilteredListManager(taskBook);
    }
    
    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }
    
    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        this.userPrefs = userPrefs;
        this.filteredListManager = new FilteredListManager(taskBook);
    }
    
    public ModelManager(Model other) {
        this(new TaskBook(other.getTaskBook()), new UserPrefs(other.getUserPrefs()));
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
        updateAllFilteredListsToNormalListing();
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
        updateAllFilteredListsToNormalListing();
        indicateTaskBookChanged();
    }
    
    public Model clone() {
        return new ModelManager(new TaskBook(taskBook), new UserPrefs(userPrefs));
    }
    
    /*
     * ==================================================================
     *                  Updating Filtered Lists 
     * ==================================================================
     */
    
    @Override
    public void updateAllFilteredListsToNormalListing() {
        this.filteredListManager.updateFilteredListsToNormalListing();
    }
    
    @Override
    public void updateFilteredAgendaTaskList(Set<String> keywords) {
        this.filteredListManager.updateFilteredList(ListId.TASKS_AGENDA, keywords);
    }

    @Override
    public void updateFilteredAgendaEventList(Set<String> keywords) {
        this.filteredListManager.updateFilteredList(ListId.EVENTS_AGENDA, keywords);
    }
    
    @Override
    public void updateFilteredAgendaTaskList(ListId other) {
        this.filteredListManager.updateFilteredList(ListId.TASKS_AGENDA, other);
    }

    @Override
    public void updateFilteredAgendaEventList(ListId other) {
        this.filteredListManager.updateFilteredList(ListId.EVENTS_AGENDA, other);
    }
    
    @Override
    public void updateFilteredAgendaTaskList(DateTime toDate, DateTime fromDate) {
        this.filteredListManager.updateFilteredList(ListId.TASKS_AGENDA, toDate, fromDate);
    }

    @Override
    public void updateFilteredAgendaEventList(DateTime toDate, DateTime fromDate) {
        this.filteredListManager.updateFilteredList(ListId.EVENTS_AGENDA, toDate, fromDate);
    }
    
    @Override
    public void updateFilteredAgendaTaskList(Set<String> keywords, DateTime toDate, DateTime fromDate) {
        this.filteredListManager.updateFilteredList(ListId.TASKS_AGENDA, keywords, toDate, fromDate);
    }

    @Override
    public void updateFilteredAgendaEventList(Set<String> keywords, DateTime toDate, DateTime fromDate) {
        this.filteredListManager.updateFilteredList(ListId.EVENTS_AGENDA, keywords, toDate, fromDate);
    }
    
    /*
     * ==================================================================
     *                  Accessing Filtered Lists 
     * ==================================================================
     */
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList() {
        return this.filteredListManager.getFilteredList(ListId.FLOATING_TASKS);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredIncompleteTaskList() {
        return this.filteredListManager.getFilteredList(ListId.INCOMPLETE);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList() {
        return this.filteredListManager.getFilteredList(ListId.COMPLETED);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay1TaskList() {
        return this.filteredListManager.getFilteredList(ListId.DAY_AHEAD_0);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay2TaskList() {
        return this.filteredListManager.getFilteredList(ListId.DAY_AHEAD_1);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay3TaskList() {
        return this.filteredListManager.getFilteredList(ListId.DAY_AHEAD_2);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay4TaskList() {
        return this.filteredListManager.getFilteredList(ListId.DAY_AHEAD_3);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay5TaskList() {
        return this.filteredListManager.getFilteredList(ListId.DAY_AHEAD_4);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay6TaskList() {
        return this.filteredListManager.getFilteredList(ListId.DAY_AHEAD_5);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDay7TaskList() {
        return this.filteredListManager.getFilteredList(ListId.DAY_AHEAD_6);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredAgendaTaskList() {
        return this.filteredListManager.getFilteredList(ListId.TASKS_AGENDA);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredAgendaEventList() {
        return this.filteredListManager.getFilteredList(ListId.EVENTS_AGENDA);
    }

    @Override
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }
}
