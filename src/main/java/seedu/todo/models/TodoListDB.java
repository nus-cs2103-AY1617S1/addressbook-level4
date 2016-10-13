package seedu.todo.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.todo.commons.util.FileUtil;
import seedu.todo.commons.util.JsonUtil;

public class TodoListDB {

    private static TodoListDB instance = null;
    
    private Set<Task> tasks = new HashSet<Task>();
    private Set<Event> events = new HashSet<Event>();
    
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
    
    private File getStorageFile() {
        return new File("database.json");
    }
    
    public boolean save() {
        try {
            FileUtil.writeToFile(getStorageFile(), JsonUtil.toJsonString(this));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean load() {
        try {
            instance = JsonUtil.fromJsonString(FileUtil.readFromFile(getStorageFile()), TodoListDB.class);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
