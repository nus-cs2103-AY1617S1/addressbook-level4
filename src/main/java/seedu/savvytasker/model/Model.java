package seedu.savvytasker.model;

import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.DuplicateSymbolKeywordException;
import seedu.savvytasker.model.alias.SymbolKeywordNotFoundException;
import seedu.savvytasker.model.task.FindType;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.InvalidDateException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlySavvyTasker newData);

    /** Returns Savvy Tasker */
    ReadOnlySavvyTasker getSavvyTasker();

    //@@author A0139915W
    /** Deletes the given Task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Modifies the given Task. */
    void modifyTask(ReadOnlyTask target, Task replacement) throws TaskNotFoundException, InvalidDateException;

    /** Adds the given Task. 
     * @throws {@link DuplicateTaskException} if a duplicate is found
     * */
    void addTask(Task task) throws DuplicateTaskException, InvalidDateException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<Task>} */
    UnmodifiableObservableList<Task> getFilteredTaskListTask();

    /** Updates the filter of the filtered task list to show all active tasks sorted by due date */
    void updateFilteredListToShowActiveSortedByDueDate();

    /** Updates the filter of the filtered task list to show all active tasks sorted by priority level */
    void updateFilteredListToShowActiveSortedByPriorityLevel();

    /** Updates the filter of the filtered task list to show all active tasks */
    void updateFilteredListToShowActive();

    /** Updates the filter of the filtered task list to show all archived tasks */
    void updateFilteredListToShowArchived();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(FindType findType, String[] keywords);
    //@@author

    /** Adds the given AliasSymbol */
    void addAliasSymbol(AliasSymbol symbol) throws DuplicateSymbolKeywordException;
    
    /** Removes an the given AliasSymbol. */
    void removeAliasSymbol(AliasSymbol symbol) throws SymbolKeywordNotFoundException;
}
