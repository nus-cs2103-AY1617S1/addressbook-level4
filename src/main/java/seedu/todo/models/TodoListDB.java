package seedu.todo.models;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.storage.JsonStorage;
import seedu.todo.storage.Storage;

public class TodoListDB {

    private static TodoListDB instance = null;
    private static Storage storage = new JsonStorage();
    
    private Set<Task> tasks = new LinkedHashSet<Task>();
    private Set<Event> events = new LinkedHashSet<Event>();
    
    protected TodoListDB() {
        // Prevent instantiation.
    }
    
    public List<Task> getAllTasks() {
        return new ArrayList<Task>(tasks);
    }
    
    public List<Event> getAllEvents() {
        return new ArrayList<Event>(events);
    }
    
    public Task createTask() {
        Task task = new Task();
        tasks.add(task);
        return task;
    }
    
    public boolean destroyTask(Task task) {
        tasks.remove(task);
        return save();
    }
    
    public Event createEvent() {
        Event event = new Event();
        events.add(event);
        return event;
    }
    
    public boolean destroyEvent(Event event) {
        events.remove(event);
        return save();
    }
    
    public static TodoListDB getInstance() {
        if (instance == null) {
            instance = new TodoListDB();
        }
        return instance;
    }
    
    public boolean save() {
        try {
            storage.save(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean load() {
        try {
            instance = storage.load();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public int undoSize() {
        return storage.undoSize();
    }
    
    public boolean undo() {
        try {
            instance = storage.undo();
            return true;
        } catch (CannotUndoException | IOException e) {
            return false;
        }
    }
    
    public int redoSize() {
        return storage.redoSize();
    }
    
    public boolean redo() {
        try {
            instance = storage.redo();
            return true;
        } catch (CannotRedoException | IOException e) {
            return false;
        }
    }
    
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
    
    public List<Event> getEventbyDate(LocalDateTime givenDate) {
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
