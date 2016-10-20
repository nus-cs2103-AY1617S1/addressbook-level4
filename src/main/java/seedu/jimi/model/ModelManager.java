package seedu.jimi.model;

import javafx.collections.transformation.FilteredList;
import seedu.jimi.commons.core.ComponentManager;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.ui.ShowTaskPanelSectionEvent;
import seedu.jimi.commons.util.StringUtil;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;
import seedu.jimi.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.jimi.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of Jimi's data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final ModelFilteredMap filteredTasksMap;
    
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
        
        this.filteredTasksMap = new ModelFilteredMap(taskBook);
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        
        this.filteredTasksMap = new ModelFilteredMap(taskBook);
    }
    
    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }
    
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(taskBook));
    }
    
    /** Raises and event to indicate user request to show task panel sections. */
    public void showTaskPanelSection(String sectionToShow) {
        raise(new ShowTaskPanelSectionEvent(sectionToShow));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(ReadOnlyTask task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    /**
     * 
     * @param newTask Task to be replaced with.
     * @param targetIndex Index of oldTask to be replaced by.
     */
    @Override
    public synchronized void editReadOnlyTask(int targetIndex, ReadOnlyTask newTask) {
        taskBook.editTask(targetIndex, newTask);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    /**
     * 
     * @param taskToComplete Task to set to complete/incomplete.
     * @param isComplete True, if task is to be set to completed.
     */
    @Override
    public synchronized void completeTask(ReadOnlyTask taskToComplete, boolean isComplete) {
        taskBook.completeTask(taskToComplete, isComplete);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    //=========== Filtered list accessors ===============================================================

    @Override
    public void updateFilteredListToShowAll() {
        this.filteredTasksMap.updateFilteredListToShowAll();
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return this.filteredTasksMap.getFilteredTaskList();
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        this.filteredTasksMap.updateFilteredTaskList(keywords);
    }
}
