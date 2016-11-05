package seedu.todo.models;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.storage.JsonStorage;
import seedu.todo.storage.Storage;

/**
 * @@author A0093907W
 * 
 * This class holds the entire persistent database for the TodoList app.
 * <ul>
 * <li>This is a singleton class. For obvious reasons, the TodoList app should
 * not be working with multiple DB instances simultaneously.</li>
 * <li>Object to object dynamic references should not be expected to survive
 * serialization.</li>
 * </ul>
 */
public class TodoListDB {

    private static TodoListDB instance = null;
    private static Storage storage = new JsonStorage();
    
    private Set<Task> tasks = new LinkedHashSet<Task>();
    private Set<Event> events = new LinkedHashSet<Event>();
    private Map<String, String> aliases = new HashMap<String, String>();
    private Set<String> tagList = new LinkedHashSet<String>();
    
    protected TodoListDB() {
        // Prevent instantiation.
    }
    
    /**
     * Gets the singleton instance of the TodoListDB.
     * 
     * @return TodoListDB
     */
    public static TodoListDB getInstance() {
        if (instance == null) {
            instance = new TodoListDB();
        }
        return instance;
    }
    
    public void setStorage(Storage storageToSet) {
        storage = storageToSet;
    }
    
    /**
     * Update the overall Tags that exist in the DB.
     * 
     */
    public void updateTagList(String tagName) {
        tagList.add(tagName);
    }
    
    /**
     * Get a list of Tags in the DB.
     * 
     * @return tagList
     */
    public List<String> getTagList() {
        return new ArrayList<String>(tagList);
    }
    
    /**
     * Count tags which are already inserted into the db
     * 
     * @return Number of tags
     */
    public int countTagList() {
        return tagList.size();
    }
    
    public Map<String, String> getAliases() {
        return aliases;
    }
    
    /**
     * Get a list of Tasks in the DB.
     * 
     * @return tasks
     */
    public List<Task> getAllTasks() {
        return new ArrayList<Task>(tasks);
    }

    
    /**
     * Count tasks which are not marked as complete, where {@code isComplete} is false.
     * 
     * @return Number of incomplete tasks
     */
    public int countIncompleteTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Count tasks which are overdue, where {@code dueDate} is before the time now.
     * 
     * @return Number of overdue tasks
     */
    public int countOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();
        int count = 0;
        for (Task task : tasks) {
            LocalDateTime dueDate = task.getDueDate();
            if (!task.isCompleted() && dueDate != null && dueDate.compareTo(now) < 0) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Get a list of Events in the DB.
     * 
     * @return events
     */
    public List<Event> getAllEvents() {
        return new ArrayList<Event>(events);
    }

    /**
     * Count events which are in the future, where {@code startDate} is after the time now.
     * 
     * @return Number of future events
     */
    public int countFutureEvents() {
        LocalDateTime now = LocalDateTime.now();
        int count = 0;
        for (Event event : events) {
            LocalDateTime startDate = event.getStartDate();
            if (startDate != null && startDate.compareTo(now) >= 0) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Create a new Task in the DB and return it.<br>
     * <i>The new record is not persisted until <code>save</code> is explicitly
     * called.</i>
     * 
     * @return task
     */
    public Task createTask() {
        Task task = new Task();
        tasks.add(task);
        return task;
    }
    
    /**
     * Destroys a Task in the DB and persists the commit.
     * 
     * @param task
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyTask(Task task) {
        tasks.remove(task);
        return save();
    }
    
    /**
     * Destroys all `tasks` from the DB.
     * 
     * @param tasks Tasks to remove
     */
    public void destroyTasks(List<Task> clearTasks) {
        tasks.removeAll(clearTasks);
    }
    
    /**
     * @@author A0093907W
     * 
     * Create a new Event in the DB and return it.<br>
     * <i>The new record is not persisted until <code>save</code> is explicitly
     * called.</i>
     * 
     * @return event
     */
    public Event createEvent() {
        Event event = new Event();
        events.add(event);
        return event;
    }
    
    /**
     * Destroys an Event in the DB and persists the commit.
     * 
     * @param event
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyEvent(Event event) {
        events.remove(event);
        return save();
    }
    
    /**
     * Destroys all `events` from the DB.
     * 
     * @param tasks Tasks to remove
     */
    public void destroyEvents(List<Event> clearEvents) {
        events.removeAll(clearEvents);
    }
    
    /**
     * @@author A0093907W
     * 
     * Explicitly persists the database to disk.
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean save() {
        try {
            storage.save(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Explicitly reloads the database from disk.
     * 
     * @return true if the load was successful, false otherwise
     */
    public boolean load() {
        try {
            instance = storage.load();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public void move(String newPath) throws IOException {
        storage.move(newPath);
    }
    
    /**
     * Returns the maximum possible number of undos.
     * 
     * @return undoSize
     */
    public int undoSize() {
        return storage.undoSize();
    }
    
    /**
     * Rolls back the DB by one commit.
     * 
     * @return true if the rollback was successful, false otherwise
     */
    public boolean undo() {
        try {
            instance = storage.undo();
            return true;
        } catch (CannotUndoException | IOException e) {
            return false;
        }
    }
    
    /**
     * Returns the maximum possible number of redos.
     * 
     * @return redoSize
     */
    public int redoSize() {
        return storage.redoSize();
    }
    
    /**
     * Rolls forward the DB by one undo commit.
     * 
     * @return true if the redo was successful, false otherwise
     */
    public boolean redo() {
        try {
            instance = storage.redo();
            return true;
        } catch (CannotRedoException | IOException e) {
            return false;
        }
    }
    
    /**
     * Get a list of events that are not over based on today date from the DB.
     * 
     * @return events
     * @@author Tiong YaoCong A0139922Y
     */   
    public List<Event> getAllCurrentEvents() {
        ArrayList<Event> currentEvents = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (!currEvent.isOver()) {
                currentEvents.add(currEvent);
            }
        }
        return currentEvents;
    }
    
    /**
     * Get a list of Incomplete Tasks in the DB.
     * 
     * @return tasks
     * @@author Tiong YaoCong A0139922Y
     */
    public List<Task> getIncompleteTasksAndTaskFromTodayDate() {
        ArrayList<Task> incompleteTasks = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        LocalDateTime todayDate = DateUtil.floorDate(LocalDateTime.now());
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            if (!currTask.isCompleted()) { //if incompleted
                incompleteTasks.add(currTask);
            } else {
                if (currTask.getDueDate() != null && DateUtil.floorDate(currTask.getDueDate()).compareTo(todayDate) >= 0) {
                    incompleteTasks.add(currTask);
                }
            }
        }
        return incompleteTasks;
    }

    
}
