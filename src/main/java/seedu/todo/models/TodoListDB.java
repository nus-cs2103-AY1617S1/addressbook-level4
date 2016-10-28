package seedu.todo.models;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
 * @@author A0093907W
 *
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
     * Destroys all Task in the DB and persists the commit.
     * 
     * @return true if the save was successful, false otherwise
     * @@author Tiong YaoCong A0139922Y
     */
    public void destroyAllTask() {
        tasks = new LinkedHashSet<Task>();
    }
    
    /**
     * Destroys all Task in the DB by date
     * 
     * @return true if the save was successful, false otherwise
     * @@author Tiong YaoCong A0139922Y
     */
    public void destroyAllTaskByDate(LocalDateTime givenDate) {
        List<Task> selectedTasks = getTaskByDate(givenDate);
        tasks.removeAll(selectedTasks);
    }
    
    /**
     * Destroys all Task in the DB by a range of date
     * 
     * @@author Tiong YaoCong A0139922Y
     */
    public void destroyAllTaskByRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Task> selectedTasks = getTaskByRange(dateFrom, dateTo);
        tasks.removeAll(selectedTasks);
    }
    
    /**
     * Create a new Event in the DB and return it.<br>
     * <i>The new record is not persisted until <code>save</code> is explicitly
     * called.</i>
     * 
     * @return event
     * 
     * @@author A0093907W
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
     * @@author Tiong YaoCong A0139922Y
     */
    public void destroyAllEvent() {
        events = new LinkedHashSet<Event>();
    }
    
    /**
     * Destroys all Event in the DB by date
     * 
     * @@author Tiong YaoCong A0139922Y
     */
    public void destroyAllEventByDate(LocalDateTime givenDate) {
        List<Event> selectedEvents = getEventByDate(givenDate);
        events.removeAll(selectedEvents);
    }
    
    /**
     * Destroys all Event in the DB by a range of date
     * 
     * 
     * @return true if the save was successful, false otherwise
     * @@author Tiong YaoCong A0139922Y
     */
    public void destroyAllEventByRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Event> selectedEvents = getEventByRange(dateFrom, dateTo);
        events.removeAll(selectedEvents);
    }
    
    
    
    /**
     * Gets the singleton instance of the TodoListDB.
     * 
     * @return TodoListDB
     * 
     * @@author A0093907W
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

    /**
     * Filter a list of tasks with a provided item name list
     * 
     * @param tasks 
     *            a list of tasks after searching condition
     * @param itemNameList
     *            a list of keyword to search based on task name
     * @return tasks
     * @@author Tiong YaoCong A0139922Y
     */
    public List<Task> getTaskByName(List<Task> tasks, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Task> taskByName = new ArrayList<Task>();
        Iterator<Task> tagIterator = tasks.iterator();
        Iterator<String> taskNameIterator = itemNameList.iterator();
        Iterator<String> tagNameIterator = tagNameList.iterator();
        boolean isFound = false;
        while (tagIterator.hasNext()) {
            Task currTask = tagIterator.next();
            String currTaskName = currTask.getName().toLowerCase();
            ArrayList<String> currTaskTagList = currTask.getTagList();
            String[] currTaskStartingNameBetweenSpace = currTaskName.split(" ");
            while(taskNameIterator.hasNext() || tagNameIterator.hasNext()) {
                String currentMatchingNameString = "";
                String currentMatchingTagNameString = "";
                
                try {
                    currentMatchingNameString = taskNameIterator.next().toLowerCase();
                } catch (NoSuchElementException e) {
                    currentMatchingNameString = null;
                }
                
                try {
                    currentMatchingTagNameString = tagNameIterator.next().toLowerCase();
                } catch  (NoSuchElementException e) {
                    currentMatchingTagNameString = null;
                }
                
                if (currentMatchingNameString != null && currentMatchingTagNameString != null) {
                    if (currTaskName.contains(currentMatchingNameString) || currTaskTagList.contains(currentMatchingTagNameString)){
                        taskByName.add(currTask);
                    }
                } else if (currentMatchingNameString != null) {
                    if (currTaskName.contains(currentMatchingNameString)) {
                        taskByName.add(currTask);
                    }
                } else {
                    if (currTaskTagList.contains(currentMatchingTagNameString)) {
                        taskByName.add(currTask);
                    }
                }
            }
            tagNameIterator = tagNameList.iterator();
            taskNameIterator = itemNameList.iterator();
        }
        return taskByName;
    }

   /**
     * Get a list of Task in the DB filtered by status , name and one date.
     * 
     * @param givenDate
     *               LocalDateTime parsed by Natty
     * @param isCompleted
     *               true if searching for completed task, else false for incomplete tasks  
     * @param listAllStatus
     *               true if searching for both completed and incomplete tasks, false if either one is been specify
     * @param itemNameList
     *               list of String to be used to search against task name                                        
     * @return list of tasks
     * @@author Tiong YaoCong A0139922Y
     */
    public List<Task> getTaskByDateWithStatusAndName(LocalDateTime givenDate, boolean isCompleted, 
            boolean listAllStatus, HashSet<String> itemNameList, HashSet<String> tagNameList) {
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
        
        if (itemNameList.size() == 0) {
            return taskByDate;
        } else {
            return getTaskByName(taskByDate, itemNameList, tagNameList);
        }
    }

    /**
     * Get a list of Task in the DB filtered by a given date.
     * 
     * @param givenDate 
     *                LocalDateTime format parsed by Natty
     *                
     * @return list of tasks
     * @@author Tiong YaoCong A0139922Y
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
     * @param givenDate 
     *                  LocalDateTime parsed by Natty
     * @param isCompleted
     *               true if searching for completed task, else false for incomplete tasks  
     * @param listAllStatus
     *               true if searching for both completed and incomplete tasks, false if either one is been specify
     * @return list of tasks
     * @@author Tiong YaoCong A0139922Y
     */
    public List<Task> getTaskByDateWithStatus(LocalDateTime givenDate, boolean isCompleted, boolean listAllStatus) {
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
     * Get a list of Task in the DB filtered by status, name and range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date               
     * @param isCompleted
     *               true if searching for completed task, else false for incomplete tasks  
     * @param listAllStatus
     *               true if searching for both completed and incomplete tasks, false if either one is been specify
     *               
     * @return list of tasks
     * @@author Tiong YaoCong A0139922Y
     */
    public List<Task> getTaskByRangeWithName (LocalDateTime fromDate , LocalDateTime toDate, boolean isCompleted, 
            boolean listAllStatus, HashSet<String> itemNameList, HashSet<String> tagNameList) {
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
        
        if (itemNameList.size() == 0 && tagNameList.size() == 0) {
            return taskByRange;
        } else {
            return getTaskByName(taskByRange, itemNameList, tagNameList);
        }
    }
    
    /**
     * Get a list of Task in the DB filtered by range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date   
     * @return list of tasks
     * @@author Tiong YaoCong A0139922Y
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
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date               
     * @param isCompleted
     *               true if searching for completed task, else false for incomplete tasks  
     * @param listAllStatus
     *               true if status of task is not been specify else false if complete or incomplete is been specify
     *                             
     * @return list of tasks
     * @@author Tiong YaoCong A0139922Y
     */
    public List<Task> getTaskByRangeWithStatus (LocalDateTime fromDate , LocalDateTime toDate, 
            boolean isCompleted, boolean listAllStatus) {
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
     * Get a list of Event in the DB filtered by status, name and one date.
     * 
     * @param givenDate
     *                LocalDateTime parsed by Natty to be used to search event that start from this date
     * @return list of events
     * @@author Tiong YaoCong A0139922Y
     */
    public List<Event> getEventbyDateWithName(LocalDateTime givenDate, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Event> eventByDate = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (DateUtil.floorDate(currEvent.getCalendarDT()).equals(givenDate)) {
                eventByDate.add(currEvent);
            }
        }

        if (itemNameList.size() == 0) {
            return eventByDate;
        } else {
            return getEventByName(eventByDate, itemNameList, tagNameList);
        }
    }
    
    /**
     * Get a list of Event in the DB filtered by status and one date.
     * 
     * @param givenDate
     *                LocalDateTime parsed by Natty to be used to search event that start from this date
     * @param itemNameList
     *                list of name keyword entered by user , to be used for match event name
     * @return list of events
     * @@author Tiong YaoCong A0139922Y
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
     * Get a list of Event in the DB filtered by status, name and range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date               
     * @param itemNameList
     *                list of name keyword entered by user , to be used for match event name
     * @return list of events
     * @@author Tiong YaoCong A0139922Y
     */
    public List<Event> getEventByRangeWithName (LocalDateTime fromDate , LocalDateTime toDate, HashSet<String> itemNameList, HashSet<String> tagNameList) {
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
        
        if (itemNameList.size() == 0) {
            return eventByRange;
        } else {
            return getEventByName(eventByRange, itemNameList, tagNameList);
        }
    }
    
    /**
     * Get a list of Event in the DB filtered by status and range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date               
     *                           
     * @return list of events
     * @@author Tiong YaoCong A0139922Y
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

    /**
     * Filter a list of event with a given name list
     * 
     * @param events
     *                list of events to be used for filtering            
     * @param itemNameList
     *                list of name keyword entered by user , to be used for match event name
     * @return list of events
     * @@author Tiong YaoCong A0139922Y
     */    
    public List<Event> getEventByName(List<Event> events, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Event> eventByName = new ArrayList<Event>();
        Iterator<Event> eventIterator = events.iterator();
        Iterator<String> eventNameIterator = itemNameList.iterator();
        Iterator<String> tagNameIterator = tagNameList.iterator();
        while (eventIterator.hasNext()) {
            Event currEvent = eventIterator.next();
            String currEventName = currEvent.getName().toLowerCase();
            ArrayList<String> currEventTagList = currEvent.getTagList();
            while(eventNameIterator.hasNext() || tagNameIterator.hasNext()) {
                String currentMatchingNameString = "";
                String currentMatchingTagNameString = "";
                
                try {
                    currentMatchingNameString = eventNameIterator.next().toLowerCase();
                } catch (NoSuchElementException e) {
                    currentMatchingNameString = null;
                }
                
                try {
                    currentMatchingTagNameString = tagNameIterator.next().toLowerCase();
                } catch  (NoSuchElementException e) {
                    currentMatchingTagNameString = null;
                }
                
                if (currentMatchingNameString != null && currentMatchingTagNameString != null) {
                    if (currEventName.contains(currentMatchingNameString) || currEventTagList.contains(currentMatchingTagNameString)){
                        eventByName.add(currEvent);
                    }
                } else if (currentMatchingNameString != null) {
                    if (currEventName.contains(currentMatchingNameString)) {
                        eventByName.add(currEvent);
                    }
                } else {
                    if (currEventTagList.contains(currentMatchingTagNameString)) {
                        eventByName.add(currEvent);
                    }
                }
            }
            tagNameIterator = tagNameList.iterator();
            eventNameIterator = itemNameList.iterator();
        }
        return eventByName;
    }
}
