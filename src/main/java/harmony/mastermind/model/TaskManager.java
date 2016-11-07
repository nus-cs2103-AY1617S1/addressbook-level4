package harmony.mastermind.model;

import javafx.collections.ObservableList;

import java.util.*;
import java.util.stream.Collectors;

import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.events.ui.HighlightLastActionedRowRequestEvent;
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

//@@author A0124797R
/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {
    private static final int INDEX_RECURRENCE_KEYWORD = 0;
    private static final int INDEX_RECURRENCE_AMOUNT = 1;

    private static final int ONE_DAY = 1;
    private static final int ONE_WEEK = 7;
    private static final int ONE_MONTH = 1;
    private static final int ONE_YEAR = 1;
    
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
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueFloatingTaskList(), 
                toBeCopied.getUniqueEventList(), toBeCopied.getUniqueDeadlineList(), 
                toBeCopied.getUniqueTagList(), toBeCopied.getUniqueArchiveList());
    }

    /**
     * Tasks and Tags are copied into this TaskManager
     */
    public TaskManager(UniqueTaskList tasks, UniqueTaskList floatingTasks, UniqueTaskList events, UniqueTaskList deadlines, UniqueTagList tags, ArchiveTaskList archiveTasks) {
        resetData(tasks.getInternalList(), floatingTasks.getInternalList(), events.getInternalList(),
                deadlines.getInternalList(), tags.getInternalList(), archiveTasks.getInternalList());
    }

    //@@author
    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

    //list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }
    
    //@@author A0124797R
    /** returns an {@code ObservableList} of floating tasks*/
    public ObservableList<Task> getFloatingTasks() {
        return floatingTasks.getInternalList();
    }
    
    /** returns an {@code ObservableList} of events*/
    public ObservableList<Task> getEvents() {
        return events.getInternalList();
    }
    
    /** returns an {@code ObservableList} of deadlines*/
    public ObservableList<Task> getDeadlines() {
        return deadlines.getInternalList();
    }

    /** returns an {@code ObservableList} of archives*/
    public ObservableList<Task> getArchives() {
        return archives.getInternalList();
    }
    
    //@@author generated
    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().sort(comparator);
        this.tasks.getInternalList().setAll(tasks);
    }

    //@@author A0124797R
    public void setFloatingTasks(List<Task> floatingTasks) {
        this.floatingTasks.getInternalList().setAll(floatingTasks);
    }

    public void setEvents(List<Task> events) {
        this.events.getInternalList().setAll(events);
    }

    public void setDeadlines(List<Task> deadlines) {
        this.deadlines.getInternalList().setAll(deadlines);
    }
    
    public void setArchiveTasks(Collection<Task> archiveTasks) {
        this.archives.getInternalList().setAll(archiveTasks);
    }

    //@@author generated
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

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList(), newData.getFloatingTaskList(), newData.getEventList(), 
                newData.getDeadlineList(), newData.getTagList(), newData.getArchiveList());
    }



//// task-level operations

    /**
     * Adds a task to the task manager and synchronize with all the tabs.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(t);
        tasks.add(t);
        syncAddTask(t);
        this.getUniqueTaskList().getInternalList().sort(comparator);

    }

    //@@author A0124797R
    /**
     * Adds the next recurring task to the task manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addNextTask(Task t) throws UniqueTaskList.DuplicateTaskException, NotRecurringTaskException {
        syncTagsWithMasterList(t);
        Task newT = getNextTask(t);
        tasks.add(newT);
        syncAddTask(newT);
        EventsCenter.getInstance().post(new HighlightLastActionedRowRequestEvent(newT));
    }
    
    
    //@@author A0124797R
    /**
     * returns a Task with the next recurring date given a recurring Task
     * @throws NotRecurringTaskException
     */
    public Task getNextTask(Task t) throws NotRecurringTaskException {
        if (t.isFloating() || t.getRecur() == null){
            throw new NotRecurringTaskException();
        }
        
        Task newT = null;
        String[] recurVal = t.getRecur().split(" ");
        String nextRecur = getNextRecur(t.getRecur());
        Date nextEndDate = getNextDate(t.getEndDate(),recurVal[INDEX_RECURRENCE_KEYWORD]);
        
        if (t.isDeadline()) {
            newT = new Task(t.getName(), nextEndDate, t.getTags(), nextRecur, new Date());
        }else if (t.isEvent()) {
            Date nextStartDate = getNextDate(t.getStartDate(), recurVal[INDEX_RECURRENCE_KEYWORD]);
            newT = new Task(t.getName(), nextStartDate, nextEndDate, t.getTags(), nextRecur, new Date());
        }
        
        return newT;
        
    }

    //@@author A0124797R
    /**
    * returns the next date based on the type of recurring task
    */
    private Date getNextDate(Date d, String recur) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int date;
        switch (recur) {
            case "daily" :   
                date = c.get(Calendar.DATE);
                c.set(Calendar.DATE, date + ONE_DAY);
                break;
            case "weekly" :  
                date = c.get(Calendar.DATE);
                c.set(Calendar.DATE, date + ONE_WEEK);
                break;
            case "monthly" : 
                date = c.get(Calendar.MONTH);
                c.set(Calendar.MONTH, date + ONE_MONTH);
                break;
            case "yearly" :  
                date = c.get(Calendar.YEAR);
                c.set(Calendar.YEAR, date + ONE_YEAR);
                break;
            default :
                assert false;
        }
        
        return c.getTime();
    }

    //@@author A0124797R
    /**
    * returns the next date based on the type of recurring task
    */
    private String getNextRecur(String recur) {
        String[] recurArr = recur.split(" ");
        if (recurArr.length==1) {
            return recur;
        }else {
            int counter = Integer.parseInt(recurArr[INDEX_RECURRENCE_AMOUNT]);
            
            if (counter>2) {
                return recurArr[0] + " " + Integer.toString(counter-1);
            } else {
                return null;
            }
        }
    }

    //@@author
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
    
    //@@author A0124797R
    /**
     * Removes an archived task as indicated
     */
    public boolean removeArchive(ReadOnlyTask key) throws ArchiveTaskList.TaskNotFoundException {
        if (archives.remove(key)) {
            return true;
        } else {
            throw new ArchiveTaskList.TaskNotFoundException();
        }
    }

    //@@author A0124797R
    /**
     * marks task as completed by
     * removing the task from tasks and adds into archivedtasks
     */
    public boolean markTask(Task key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            archives.add(key.mark());
            this.getUniqueTaskList().getInternalList().sort(comparator);
            syncRemoveTask(key);
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    //@@author A0124797R
    /**
     * marks task as not completed by
     * removing the task from archivedTasks and adds into tasks
     */
    public boolean unmarkTask(Task key) throws DuplicateTaskException, ArchiveTaskList.TaskNotFoundException {
        if (archives.remove(key)) {
            tasks.add(key.unmark());
            this.getUniqueTaskList().getInternalList().sort(comparator);
            syncAddTask(key.unmark());
            return true;
        } else {
            throw new ArchiveTaskList.TaskNotFoundException();
        }
    }

    //@@author A0124797R
    /**
     * Synchronize adding of tasks across the tabs
     */
    private void syncAddTask(Task task) throws DuplicateTaskException{   
        if (task.isFloating()) {
            floatingTasks.add(task);
        } else if (task.isDeadline()) {
            deadlines.add(task);
        } else if (task.isEvent()) {
            events.add(task);
        }
    }

    //@@author A0124797R
    /**
     * Synchronize removing of tasks across the tabs
     */
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

    //@@author
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
    
    @Override
    public List<ReadOnlyTask> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }
    
    @Override
    public List<ReadOnlyTask> getDeadlineList() {
        return Collections.unmodifiableList(deadlines.getInternalList());
    }
    
    @Override
    public List<ReadOnlyTask> getArchiveList() {
        return Collections.unmodifiableList(archives.getInternalList());
    }

    //@@author
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    //@@author A0124797R
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

    @Override
    public ArchiveTaskList getUniqueArchiveList() {
        return this.archives;
    }
    
    //@@author
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

    //@@author
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }

    public void searchTask(String keyword, Memory memory) {
        ParserSearch.run(keyword, memory);
        
    }

}
