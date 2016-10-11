package seedu.todo.models;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
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
    
    public Task createTask() {
        Task task = new Task();
        tasks.add(task);
        return task;
    }
    
    public boolean destroyTask(Task task) {
        return tasks.remove(task);
    }
    
    public Event createEvent() {
        Event event = new Event();
        events.add(event);
        return event;
    }
    
    public boolean destroyEvent(Event event) {
        return events.remove(event);
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
