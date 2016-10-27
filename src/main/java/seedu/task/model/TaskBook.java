package seedu.task.model;

import javafx.collections.ObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Event;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueEventList;
import seedu.task.model.item.UniqueTaskList;
import seedu.task.model.item.UniqueTaskList.DuplicateTaskException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private final UniqueEventList events;

    {
        tasks = new UniqueTaskList();
        events = new UniqueEventList();
    }

    public TaskBook() {}

    /**
     * Tasks and Events are copied into this taskbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueEventList());
    }

    /**
     * Tasks and Events are copied into this taskbook
     */
    public TaskBook(UniqueTaskList tasks, UniqueEventList events) {
        resetData(tasks.getInternalList(), events.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyTaskBook() {
        return new TaskBook();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public ObservableList<Event> getEvents() {
        return events.getInternalList();
    }

    public void setEvents(List<Event> events) {
        this.events.getInternalList().setAll(events);
    }
    
    //@@author A0121608N
    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<? extends ReadOnlyEvent> newEvents) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setEvents(newEvents.stream().map(Event::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getTaskList(), newData.getEventList());
    }
    //@@author

//// event-level operations
    //@@author A0127570H
    /**
     * Adds an event to the task book.
     *
     * @throws UniqueEventList.DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(Event p) throws UniqueEventList.DuplicateEventException {
        events.add(p);
    }
    
    //@@author A0121608N
    /**
     * Removes an event in the task book.
     *
     * @throws UniqueTaskList.EventNotFoundException if specified event does not exist.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws UniqueEventList.EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new UniqueEventList.EventNotFoundException();
        }
    }
    //@@author A0127570H
    
    /**
     * Edits an event in the task book.
     *
     * @throws UniqueEventList.DuplicateEventException if an equivalent event already exists.
     */
    public void editEvent(Event editEvent, ReadOnlyEvent targetEvent) throws UniqueEventList.DuplicateEventException {
        events.edit(editEvent, targetEvent);
        
    }
    
//// task-level operations

    /**
     * Adds a task to the task book.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(p);;
    }

    //@@author A0121608N
    /**
     * Removes a task in the task book.
     *
     * @throws UniqueTaskList.TaskNotFoundException if specified task does not exist.
     */
    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    /**
     * Marks a task in the task book.
     */
    public void markTask(ReadOnlyTask key){
        tasks.mark(key);
	}
    //@@author A0127570H
    
    /**
     * Edits a task in the task book.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void editTask(Task editTask, ReadOnlyTask targetTask) throws UniqueTaskList.DuplicateTaskException {
        tasks.edit(editTask, targetTask);
    }
    //@@author 
    
//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }
    
    @Override
    public List<ReadOnlyEvent> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }

    @Override
    public UniqueEventList getUniqueEventList() {
        return this.events;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }
}
