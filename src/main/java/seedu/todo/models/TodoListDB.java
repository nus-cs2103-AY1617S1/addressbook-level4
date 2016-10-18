package seedu.todo.models;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.storage.JsonStorage;
import seedu.todo.storage.Storage;

/**
 * This class holds the entire persistent database for the TodoList app.
 * <ul>
 * <li>This is a singleton class. For obvious reasons, the TodoList app should
 * not be working with multiple DB instances simultaneously.</li>
 * <li>Object to object dynamic references should not be expected to survive
 * serialization.</li>
 * </ul>
 * 
 * @author louietyj
 *
 */
public class TodoListDB {

    private static TodoListDB instance = null;
    private static Storage storage = new JsonStorage();
    
    private Set<Task> tasks = new LinkedHashSet<Task>();
    private Set<Event> events = new LinkedHashSet<Event>();
    
    protected TodoListDB() {
        // Prevent instantiation.
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
     * Destroys all Task in the DB and persists the commit.
     * 
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyAllTask() {
        tasks = new LinkedHashSet<Task>();
        return save();
    }
    
    /**
     * Destroys all Task in the DB by date
     * 
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyAllTaskByDate(LocalDateTime givenDate) {
        List<Task> selectedTasks = getTaskByDate(givenDate);
        tasks.removeAll(selectedTasks);
        return save();
    }
    
    /**
     * Destroys all Task in the DB by a range of date
     * 
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyAllTaskByRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Task> selectedTasks = getTaskByRange(dateFrom, dateTo);
        tasks.removeAll(selectedTasks);
        return save();
    }
    
    /**
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
     * Destroys all Event in the DB and persists the commit.
     * 
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyAllEvent() {
        events = new LinkedHashSet<Event>();
        return save();
    }
    
    /**
     * Destroys all Event in the DB by date
     * 
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyAllEventByDate(LocalDateTime givenDate) {
        List<Event> selectedEvents = getEventByDate(givenDate);
        events.removeAll(selectedEvents);
        return save();
    }
    
    /**
     * Destroys all Event in the DB by a range of date
     * 
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyAllEventByRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Event> selectedEvents = getEventByRange(dateFrom, dateTo);
        events.removeAll(selectedEvents);
        return save();
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
    
    /**
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
     * Get a list of Task in the DB filtered by a given date.
     * 
     * @return list of tasks
     */
    public List<Task> getTaskByDate(LocalDateTime givenDate) {
        ArrayList<Task> taskByDate = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (currTaskDueDate.equals(givenDate)) {
                taskByDate.add(currTask);
            }
        }
        return taskByDate;
    }
    
    /**
     * Get a list of Task in the DB filtered by status and one date.
     * 
     * @return list of tasks
     */
    public List<Task> getTaskByDate(LocalDateTime givenDate, boolean isCompleted, boolean listAllStatus) {
        ArrayList<Task> taskByDate = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (listAllStatus) {
                if (currTaskDueDate.equals(givenDate)) {
                    taskByDate.add(currTask);
                }
            } else {
                if (currTaskDueDate.equals(givenDate) && currTask.isCompleted() == isCompleted) {
                    taskByDate.add(currTask);
                }
            }
        }
        return taskByDate;
    }
    
    /**
     * Get a list of Task in the DB filtered by range of date.
     * 
     * @return list of tasks
     */
    public List<Task> getTaskByRange (LocalDateTime fromDate , LocalDateTime toDate) {
        ArrayList<Task> taskByRange = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        if (fromDate == null) {
            fromDate = LocalDateTime.MIN;
        }
        
        if (toDate == null) {
            toDate = LocalDateTime.MAX;
        }
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (currTaskDueDate.compareTo(fromDate) >= 0 && currTaskDueDate.compareTo(toDate) <= 0) {
                taskByRange.add(currTask);
            }
            
        }
        return taskByRange;
    }
    
    /**
     * Get a list of Task in the DB filtered by status and range of date.
     * 
     * @return list of tasks
     */
    public List<Task> getTaskByRange (LocalDateTime fromDate , LocalDateTime toDate, boolean isCompleted, boolean listAllStatus) {
        ArrayList<Task> taskByRange = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        if (fromDate == null) {
            fromDate = LocalDateTime.MIN;
        }
        
        if (toDate == null) {
            toDate = LocalDateTime.MAX;
        }
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (listAllStatus) {
                if (currTaskDueDate.compareTo(fromDate) >= 0 && currTaskDueDate.compareTo(toDate) <= 0) {
                    taskByRange.add(currTask);
                }
            } else {
                if (currTaskDueDate.compareTo(fromDate) >= 0 && currTaskDueDate.compareTo(toDate) <= 0 && 
                        currTask.isCompleted() == isCompleted) {
                    taskByRange.add(currTask);
                }
            }
        }
        return taskByRange;
    }
    
    /**
     * Get a list of Event in the DB filtered by status and one date.
     * 
     * @return list of events
     */
    public List<Event> getEventByDate(LocalDateTime givenDate) {
        ArrayList<Event> eventByDate = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (DateUtil.floorDate(currEvent.getCalendarDT()).equals(givenDate)) {
                eventByDate.add(currEvent);
            }
        }
        return eventByDate;
    }
    
    /**
     * Get a list of Event in the DB filtered by status and range of date.
     * 
     * @return list of events
     */
    public List<Event> getEventByRange (LocalDateTime fromDate , LocalDateTime toDate) {
        ArrayList<Event> eventByRange = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        
        //if either date are null, set it to min or max
        if (fromDate == null) {
            fromDate = LocalDateTime.MIN;
        }
        
        if (toDate == null) {
            toDate = LocalDateTime.MAX;
        }
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (DateUtil.floorDate(currEvent.getStartDate()).compareTo(fromDate) >= 0 && 
                    DateUtil.floorDate(currEvent.getStartDate()).compareTo(toDate) <= 0) {
                eventByRange.add(currEvent);
            }
        }
        return eventByRange;
    }
}
