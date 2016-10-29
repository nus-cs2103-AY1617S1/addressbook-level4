package seedu.savvytasker.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.AliasSymbolList;
import seedu.savvytasker.model.alias.DuplicateSymbolKeywordException;
import seedu.savvytasker.model.alias.SymbolKeywordNotFoundException;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.RecurrenceType;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.InvalidDateException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * Wraps all data at the savvy-tasker level
 * Duplicates are not allowed (by .equals comparison)
 */
public class SavvyTasker implements ReadOnlySavvyTasker {

    private final TaskList tasks;
    private final AliasSymbolList symbols;

    public SavvyTasker() {
        this.tasks = new TaskList();
        this.symbols = new AliasSymbolList();
    }

    public SavvyTasker(ReadOnlySavvyTasker toBeCopied) {
        this(toBeCopied.getTaskList(), toBeCopied.getAliasSymbolList());
    }

    public SavvyTasker(TaskList tasks, AliasSymbolList symbols) {
        this();
        resetData(tasks.getInternalList());
        this.symbols.reset(symbols);
    }

    public static ReadOnlySavvyTasker getEmptySavvyTasker() {
        return new SavvyTasker();
    }

//// task list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlySavvyTasker newData) {
        resetData(newData.getReadOnlyListOfTasks());
    }

//// symbol/task-level operations

    //@@author A0139915W

    /**
     * Adds a task to savvy tasker.
     * @throws {@link InvalidDateException} if the end date is earlier than the start date
     * @return Returns the task added if the operation succeeds, an exception is thrown otherwise.
     */
    public Task addTask(Task t) throws InvalidDateException {
        // guarantees unique ID
        t.setId(tasks.getNextId());
        try {
            return tasks.add(t);
        } catch (DuplicateTaskException e) {
            // should never get here.
            return null;
        }
    }

    /**
     * Adds a group of recurring tasks to savvy tasker.
     * @throws {@link InvalidDateException} if the end date is earlier than the start date
     * @return Returns the list of recurring tasks if the operation succeeds, an exception is thrown otherwise
     */
    public LinkedList<Task> addRecurringTasks(Task recurringTask) throws InvalidDateException {
        LinkedList<Task> tasksToAdd = 
                createRecurringTasks(recurringTask, recurringTask.getRecurringType(), 
                        recurringTask.getNumberOfRecurrence());
        Iterator<Task> itr = tasksToAdd.iterator();
        
        while(itr.hasNext()) {
            // this will be an atomic operation
            // guaranteed no duplicates
            // if the start/end dates are invalid,
            // the first task to be added will fail immediately, 
            // subsequent tasks will not be added
            try {
                tasks.add(itr.next());
            } catch (DuplicateTaskException e) {
                // should never get here.
                return null;
            } 
        }
        return tasksToAdd;
    }
    
    /**
     * Creates a list of recurring tasks to be added into savvy tasker.
     * @param recurringTask the task that recurs
     * @param recurringType the type of recurrence
     * @param numberOfRecurrences the number of recurrences
     * @return A list of tasks that represents a recurring task.
     */
    private LinkedList<Task> createRecurringTasks(Task recurringTask, RecurrenceType recurringType, int numberOfRecurrences) {
        assert recurringTask.getRecurringType() != null;
        assert recurringTask.getNumberOfRecurrence() > 0;
        
        LinkedList<Task> listOfTasks = new LinkedList<Task>();
        recurringTask.setGroupId(tasks.getNextGroupId());
        
        for (int i = 0; i < numberOfRecurrences; ++i) {
            Task t = recurringTask.clone();
            // guarantees uniqueness
            t.setId(tasks.getNextId());
            listOfTasks.add(setDatesForRecurringType(t, recurringType, i));
        }
        
        return listOfTasks;
    }
    
    /**
     * Helper function for createRecurringTasks(). Sets the respective start/end datetime for the
     * i-th recurring task to be added
     * @param t The first recurring task
     * @param recurringType the type of recurrence
     * @param index the index of the loop
     * @return A task with its respective datetime set.
     */
    private Task setDatesForRecurringType(Task t, RecurrenceType recurringType, int index) {
        Date startDate = t.getStartDateTime();
        Date endDate = t.getEndDateTime();
        Calendar calendar = Calendar.getInstance();
        switch (recurringType) {
        case Daily: // add one day to the i-th task
            if (startDate != null) {
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE, 1 * index);
                startDate = calendar.getTime();
            }
            if (endDate != null) {
                calendar.setTime(endDate);
                calendar.add(Calendar.DATE, 1 * index);
                endDate = calendar.getTime();
            }
            break;
        case Weekly:  // add 7 days to the i-th task
            if (startDate != null) {
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE, 7 * index);
                startDate = calendar.getTime();
            }
            if (endDate != null) {
                calendar.setTime(endDate);
                calendar.add(Calendar.DATE, 7 * index);
                endDate = calendar.getTime();
            }
            break;
        case Monthly: // add 1 month to the i-th task
            if (startDate != null) {
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH, 1 * index);
                startDate = calendar.getTime();
            }
            if (endDate != null) {
                calendar.setTime(endDate);
                calendar.add(Calendar.MONTH, 1 * index);
                endDate = calendar.getTime();
            }
            break;
        case Yearly: // add 1 year to the i-th task
            if (startDate != null) {
                calendar.setTime(startDate);
                calendar.add(Calendar.YEAR, 1 * index);
                startDate = calendar.getTime();
            }
            if (endDate != null) {
                calendar.setTime(endDate);
                calendar.add(Calendar.YEAR, 1 * index);
                endDate = calendar.getTime();
            }
            break;
        case None:
        default:
            assert false; // should not come here
        }
        t.setStartDateTime(startDate);
        t.setEndDateTime(endDate);
        return t;
    }
    
    /**
     * Removes a task from savvy tasker.
     * @param key the task to be removed
     * @throws {@link TaskNotFoundException} if the task does not exist
     * @return Returns a Task if the remove operation is successful, an exception is thrown otherwise.
     */
    public Task removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        return tasks.remove(key);
    }
    
    /**
     * Replaces a task from savvy tasker.
     * @param key the task to be replaced
     * @throws {@link TaskNotFoundException} if the task does not exist
     * @throws {@link InvalidDateException} if the end date is earlier than the start date
     * @return true if the task is removed successfully
     */
    public Task replaceTask(ReadOnlyTask key, Task replacement) throws TaskNotFoundException, InvalidDateException {
        return tasks.replace(key, replacement);
    }
    //@@author
    //@@author A0139916U
    
    /**
     * Adds an alias symbol to savvy tasker.
     * @param symbol the symbol to add
     * @throws DuplicateSymbolKeywordException if another symbol with the same keyword already exists
     */
    public void addAliasSymbol(AliasSymbol symbol) throws DuplicateSymbolKeywordException {
        symbols.addAliasSymbol(symbol);
    }
    
    /**
     * Removes an alias symbol from savvy tasker.
     * @param symbol the symbol to remove
     * @throws SymbolKeywordNotFoundException  if there is no such symbol
     */
    public void removeAliasSymbol(AliasSymbol symbol) throws SymbolKeywordNotFoundException {
        symbols.removeAliasSymbol(symbol);
    }
    //@@author
    
//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + symbols.size() + " symbols";
        // TODO: refine later
    }

    //@@author A0139915W
    @Override
    public List<ReadOnlyTask> getReadOnlyListOfTasks() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public TaskList getTaskList() {
        TaskList defensiveCopy = new TaskList();
        defensiveCopy.getInternalList().addAll(tasks.getInternalList());
        return defensiveCopy;
    }
    //@@author

    //@@author A0139916U
    @Override
    public List<AliasSymbol> getReadOnlyListOfAliasSymbols() {
        return symbols.asReadonly();
    }

    @Override
    public AliasSymbolList getAliasSymbolList() {
        return new AliasSymbolList(symbols);
    }
    //@@author

    //@@author A0139915W
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SavvyTasker // instanceof handles nulls
                && this.tasks.equals(((SavvyTasker) other).tasks)
                && this.symbols.equals(((SavvyTasker) other).symbols));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, symbols);
    }
    //@@author
}
