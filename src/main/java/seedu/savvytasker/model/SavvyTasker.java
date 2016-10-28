package seedu.savvytasker.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.AliasSymbolList;
import seedu.savvytasker.model.alias.DuplicateSymbolKeywordException;
import seedu.savvytasker.model.alias.SymbolKeywordNotFoundException;
import seedu.savvytasker.model.task.ReadOnlyTask;
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
     * @throws DuplicateTaskException if an equivalent task already exists.
     * @throws InvalidDateException if the end date is earlier than the start date.
     */
    public Task addTask(Task t) throws DuplicateTaskException, InvalidDateException {
        t.setId(tasks.getNextId());
        return tasks.add(t);
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
