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
    
    /**
     * Returns the next available id for use to uniquely identify a task.
     * @author A0139915W
     * @return The next available id.
     */
    public int getNextTaskId() {
        return tasks.getNextId();
    }

    /**
     * Adds a task to savvy tasker.
     * @throws TaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws DuplicateTaskException {
        tasks.add(t);
    }
    
    /**
     * Removes a task from savvy tasker.
     * @param key the task to be removed
     * @return true if the task is removed successfully
     * @throws TaskNotFoundException if the task to be removed does not exist
     */
    public boolean removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskList.TaskNotFoundException();
        }
    }
    
    /**
     * Replaces a task from savvy tasker.
     * @param key the task to be replaced
     * @return true if the task is removed successfully
     * @throws TaskNotFoundException if the task to be removed does not exist
     */
    public boolean replaceTask(ReadOnlyTask key, Task replacement) throws TaskNotFoundException {
        if (tasks.contains(key)) {
            return tasks.replace(key, replacement);
        } else {
            throw new TaskList.TaskNotFoundException();
        }
    }
    
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
    

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + symbols.size() + " symbols";
        // TODO: refine later
    }

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

    @Override
    public List<AliasSymbol> getReadOnlyListOfAliasSymbols() {
        return symbols.asReadonly();
    }

    @Override
    public AliasSymbolList getAliasSymbolList() {
        return new AliasSymbolList(symbols);
    }

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
}
