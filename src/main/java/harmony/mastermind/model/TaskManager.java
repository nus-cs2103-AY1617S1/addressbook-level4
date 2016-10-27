package harmony.mastermind.model;

import javafx.collections.ObservableList;

import java.util.*;
import java.util.stream.Collectors;

import harmony.mastermind.commons.exceptions.NotRecurringTaskException;
import harmony.mastermind.logic.parser.ParserSearch;
import harmony.mastermind.memory.Memory;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.TaskListComparator;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueTaskList floatingTasks;
    private final UniqueTaskList events;
    private final UniqueTaskList deadlines;
    private final ArchiveTaskList archives;
    private final UniqueTagList tags;
    private final TaskListComparator comparator;

    {
        tasks = new UniqueTaskList();
        floatingTasks = new UniqueTaskList();
        events = new UniqueTaskList();
        deadlines = new UniqueTaskList();
        archives = new ArchiveTaskList();
        tags = new UniqueTagList();
        comparator = new TaskListComparator();
    }

    public TaskManager() {}

    /**
     * Tasks and Tags are copied into this TaskManager
     */
    //@@author A0124797R
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueFloatingTaskList(), 
                toBeCopied.getUniqueEventList(), toBeCopied.getUniqueDeadlineList(), 
                toBeCopied.getUniqueTagList(), toBeCopied.getUniqueArchiveList());
    }

    /**
     * Tasks and Tags are copied into this TaskManager
     */
    //@@author A0124797R
    public TaskManager(UniqueTaskList tasks, UniqueTaskList floatingTasks, UniqueTaskList events, UniqueTaskList deadlines, UniqueTagList tags, ArchiveTaskList archiveTasks) {
        resetData(tasks.getInternalList(), floatingTasks.getInternalList(), events.getInternalList(),
                deadlines.getInternalList(), tags.getInternalList(), archiveTasks.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }
    
    //@@author A0124797R
    public ObservableList<Task> getFloatingTasks() {
        return floatingTasks.getInternalList();
    }
    
    //@@author A0124797R
    public ObservableList<Task> getEvents() {
        return events.getInternalList();
    }
    
    //@@author A0124797R
    public ObservableList<Task> getDeadlines() {
        return deadlines.getInternalList();
    }
    
    //@@author A0124797R
    public ObservableList<Task> getArchives() {
        return archives.getInternalList();
    }
    
    public void setTasks(List<Task> tasks) {
        this.getUniqueTaskList().getInternalList().sort(new TaskListComparator());
        this.tasks.getInternalList().setAll(tasks);
    }

    //@@author A0124797R
    public void setFloatingTasks(List<Task> floatingTasks) {
        this.floatingTasks.getInternalList().setAll(floatingTasks);
    }

    //@@author A0124797R
    public void setEvents(List<Task> events) {
        this.events.getInternalList().setAll(events);
    }

    //@@author A0124797R
    public void setDeadlines(List<Task> deadlines) {
        this.deadlines.getInternalList().setAll(deadlines);
    }
    
    //@@author A0124797R
    public void setArchiveTasks(Collection<Task> archiveTasks) {
        this.archives.getInternalList().setAll(archiveTasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    //@@author A0124797R
    public void resetData(Collection<? extends ReadOnlyTask> newTasks, 
            Collection<? extends ReadOnlyTask> newFloatingTasks, 
            Collection<? extends ReadOnlyTask> newEvents,
            Collection<? extends ReadOnlyTask> newDeadlines, 
            Collection<Tag> newTags,
            Collection<? extends ReadOnlyTask> newArchiveTasks) {
        
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setFloatingTasks(newFloatingTasks.stream().map(Task::new).collect(Collectors.toList()));
        setEvents(newEvents.stream().map(Task::new).collect(Collectors.toList()));
        setDeadlines(newDeadlines.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
        setArchiveTasks(newArchiveTasks.stream().map(Task::new).map(Task::mark).collect(Collectors.toList()));
    }

    //@@author A0124797R
    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList(), newData.getFloatingTaskList(), newData.getEventList(), 
                newData.getDeadlineList(), newData.getTagList(), newData.getArchiveList());
    }



//// task-level operations

    /**
     * Adds a task to the task manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(t);
        tasks.add(t);
        this.getUniqueTaskList().getInternalList().sort(comparator);
        syncAddTask(t);

    }
    
    /**
     * Adds the next recurring task to the task manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    //@@author A0124797R
    public void addNextTask(Task t) throws UniqueTaskList.DuplicateTaskException, NotRecurringTaskException {
        syncTagsWithMasterList(t);
        Task newT = getNextTask(t);
        tasks.add(newT);
        syncAddTask(newT);
    }
    
    
    //@@author A0124797R
    /**
     * returns a Task with the next recurring date given a recurring Task
     */
    public Task getNextTask(Task t) throws NotRecurringTaskException {
        if (t.isFloating() || t.getRecur() == null){
            throw new NotRecurringTaskException();
        }
        
        Task newT = null;
        String[] recurVal = t.getRecur().split(" ");
        String nextRecur = getNextRecur(t.getRecur());
        Date nextEndDate = getNextDate(t.getEndDate(),recurVal[0]);
        
        if (t.isDeadline()) {
            newT = new Task(t.getName(), nextEndDate, t.getTags(), nextRecur);
        }else if (t.isEvent()) {
            Date nextStartDate = getNextDate(t.getStartDate(), recurVal[0]);
            newT = new Task(t.getName(), nextStartDate, nextEndDate, t.getTags(), nextRecur, null);
        }
        
        return newT;
        
    }
    
    /**
    * returns the next date based on the type of recurring task
    */
    //@@author A0124797R
    private Date getNextDate(Date d, String recur) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int date;
        switch (recur) {
            case "daily":   date = c.get(Calendar.DATE);
                            c.set(Calendar.DATE, date + 1);
                            break;
            case "weekly":  date = c.get(Calendar.DATE);
                            c.set(Calendar.DATE, date + 7);
                            break;
            case "biweekly":  date = c.get(Calendar.DATE);
                            c.set(Calendar.DATE, date + 14);
                            break;
            case "monthly": date = c.get(Calendar.MONTH);
                            c.set(Calendar.MONTH, date + 1);
                            break;
            case "yearly":  date = c.get(Calendar.YEAR);
                            c.set(Calendar.YEAR, date + 1);
                            break;
        }
        
        return c.getTime();
    }
    
    /**
    * returns the next date based on the type of recurring task
    */
    //@@author A0124797R
    private String getNextRecur(String recur) {
        String[] recurArr = recur.split(" ");
        if (recurArr.length==1) {
            return recur;
        }else {
            int counter = Integer.parseInt(recurArr[1]);
            
            if (counter>2) {
                return recurArr[0] + " " + Integer.toString(counter-1);
            } else {
                return null;
            }
        }
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            this.getUniqueTaskList().getInternalList().sort(comparator);
            syncRemoveTask(key);
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean removeArchive(ReadOnlyTask key) throws ArchiveTaskList.TaskNotFoundException {
        if (archives.remove(key)) {
            return true;
        } else {
            throw new ArchiveTaskList.TaskNotFoundException();
        }
    }
    
    /**
     * marks task as completed by
     * removing the task from tasks and adds into archivedtasks
     * throws TaskNotFoundException
     * throws DuplicateTaskException 
     */
    //@@author A0124797R
    public boolean markTask(Task key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            archives.add(key.mark());
            syncRemoveTask(key);
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    /**
     * marks task as not completed by
     * removing the task from archivedTasks and adds into tasks
     * throws TaskNotFoundException, DuplicateTaskException 
     */
    //@@author A0124797R
    public boolean unmarkTask(Task key) throws DuplicateTaskException, ArchiveTaskList.TaskNotFoundException {
        if (archives.remove(key)) {
            tasks.add(key.unmark());
            syncAddTask(key.unmark());
            return true;
        } else {
            throw new ArchiveTaskList.TaskNotFoundException();
        }
    }
    
    /**
     * Synchronize adding of tasks
     */
    //@@author A0124797R
    private void syncAddTask(Task task) throws DuplicateTaskException{   
        if (task.isFloating()) {
            floatingTasks.add(task);
        } else if (task.isDeadline()) {
            deadlines.add(task);
        } else if (task.isEvent()) {
            events.add(task);
        }
    }
    
    /**
     * Synchronize removing of tasks
     */
    //@@author A0124797R
    private void syncRemoveTask(ReadOnlyTask task) throws TaskNotFoundException{
        if (task.isFloating()) {
            floatingTasks.remove(task);
        } else if (task.isDeadline()) {
            deadlines.remove(task);
        } else if (task.isEvent()) {
            events.remove(task);
        }
    }


//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags,"
                + archives.getInternalList().size();
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }
    
    //@@author A0124797R
    @Override
    public List<ReadOnlyTask> getFloatingTaskList() {
        return Collections.unmodifiableList(floatingTasks.getInternalList());
    }
    
    //@@author A0124797R
    @Override
    public List<ReadOnlyTask> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }
    
    //@@author A0124797R
    @Override
    public List<ReadOnlyTask> getDeadlineList() {
        return Collections.unmodifiableList(deadlines.getInternalList());
    }
    
    //@@author A0124797R
    @Override
    public List<ReadOnlyTask> getArchiveList() {
        return Collections.unmodifiableList(archives.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTaskList getUniqueFloatingTaskList() {
        return this.floatingTasks;
    }

    @Override
    public UniqueTaskList getUniqueEventList() {
        return this.events;
    }

    @Override
    public UniqueTaskList getUniqueDeadlineList() {
        return this.deadlines;
    }

    //@@author A0124797R
    @Override
    public ArchiveTaskList getUniqueArchiveList() {
        return this.archives;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    //@@author A0124797R
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.floatingTasks.equals(((TaskManager) other).floatingTasks)
                && this.events.equals(((TaskManager) other).events)
                && this.deadlines.equals(((TaskManager) other).deadlines)
                && this.tags.equals(((TaskManager) other).tags)
                && this.archives.equals(((TaskManager) other).archives));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }

    public void searchTask(String keyword, Memory memory) {
        ParserSearch.run(keyword, memory);
        
    }

}
