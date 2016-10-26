package seedu.todo.model;

import javafx.collections.ObservableList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.tag.UniqueTagList.DuplicateTagException;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class DoDoBird implements ReadOnlyToDoList {
    
    private final Stack<UniqueTaskList> tasksHistory;
    private final Stack<UniqueTagList> tagsHistory;

    public DoDoBird() {
        this(new UniqueTaskList(), new UniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this ToDoList
     */
    public DoDoBird(ReadOnlyToDoList toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this ToDoList
     */
    public DoDoBird(UniqueTaskList tasks, UniqueTagList tags) {
        tasksHistory = new Stack<>();
        tagsHistory = new Stack<>();
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyToDoList getEmptyToDoList() {
        return new DoDoBird();
    }

    /*****************************
     * LIST OVERWRITE OPERATIONS *
     *****************************/
   
    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasksHistory.peek().getInternalList());
    }
    
    //@@author A0142421X
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tagsHistory.peek().getInternalList());
    }
    
    //@@author A0093896H
    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasksHistory.peek();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tagsHistory.peek();
    }
    //@@author

    public ObservableList<Task> getTasks() {
        return tasksHistory.peek().getInternalList();
    }
    
    public ObservableList<Tag> getTags() {
        return tagsHistory.peek().getInternalList();
    }
    
    public void resetData(ReadOnlyToDoList newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }
    
    //@@author A0093896H
    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
        
        for (Task t : this.getTasks()) {
            if (t.isRecurring() 
                    && (t.getOnDate().getDate().isBefore(LocalDate.now()) 
                    || t.getByDate().getDate().isBefore(LocalDate.now()))) {
                t.getRecurrence().updateTaskDate(t);
            }
        }
    }

    public void setTasks(List<Task> tasks) {
        if (this.tasksHistory.isEmpty()) {
            UniqueTaskList topList = this.copyTaskList(tasks);
            this.tasksHistory.push(topList);
        } else {
            this.updateTaskHistoryStack();
            this.getTasks().setAll(tasks);
        }
    }
    //@@author

    public void setTags(Collection<Tag> tags) {
        if (this.tagsHistory.isEmpty()) {
            UniqueTagList topList = this.copyTagList(tags);
            this.tagsHistory.push(topList);
        } else {
            this.updateTaskHistoryStack();
            this.getTags().setAll(tags);
        }
    }

    /*************************
     * TASK-LEVEL OPERATIONS *
     *************************/
    
    //@@author A0093896H
    public int getTaskIndex(ReadOnlyTask target) {
        return this.getTasks().indexOf(target);
    }
    
    public Task getTask(int index) {
        assert index >= 0;    
        return this.getTasks().get(index);
    }
    
    /**
     * Adds a task to the to do list.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        updateTaskHistoryStack();
        updateTagHistoryStack();
        this.getUniqueTaskList().add(p);
        updateTagTopList();
    }
    
    public void deleteTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        updateTaskHistoryStack();
        updateTagHistoryStack();
        this.getUniqueTaskList().remove(key);
        updateTagTopList();
    }
    
    /**
     * Updates a task to the to do list.
     * 
     * @param index the index of the task to update
     * @param newTask copy the fields in new task into task to update
     * @throws UniqueTaskList.TaskNotFoundException if the task to update is not found.
     */
    public void updateTask(ReadOnlyTask oldTask, ReadOnlyTask newTask) throws TaskNotFoundException {
        updateTaskHistoryStack();
        updateTagHistoryStack();
        
        int index = this.getTaskIndex(oldTask);
        
        this.getTasks().get(index).setName(newTask.getName());
        this.getTasks().get(index).setDetail(newTask.getDetail());
        this.getTasks().get(index).setOnDate(newTask.getOnDate());
        this.getTasks().get(index).setByDate(newTask.getByDate());
        this.getTasks().get(index).setPriority(newTask.getPriority());
        this.getTasks().get(index).setRecurrence(newTask.getRecurrence());
    }
    
    public void addTaskTags(ReadOnlyTask oldTask, UniqueTagList newTagList) throws TaskNotFoundException {
        updateTaskHistoryStack();
        updateTagHistoryStack();
        
        int index = this.getTaskIndex(oldTask);
        Task toTag = this.getTasks().get(index); 
        for (Tag t : newTagList.getInternalList()) {
            try {
                toTag.addTag(t);
            } catch (DuplicateTagException e) {}
        }
        updateTagTopList();
    }
    
    public void deleteTaskTags(ReadOnlyTask oldTask, UniqueTagList tagList) throws TaskNotFoundException {
        updateTaskHistoryStack();
        updateTagHistoryStack();
        
        int index = this.getTaskIndex(oldTask);
        Task toUntag = this.getTasks().get(index);
        
        for (Tag tag : tagList.getInternalList()) {
            try {
                toUntag.removeTag(tag);
            } catch (UniqueTagList.TagNotFoundException e) {}
        }
        updateTagTopList();
    }
        
    /**
     * Pop the top most UniqueTaskList and UniqueTagList
     * Does not pop if there is only one state in history 
     * TODO : Does not handle tags as of yet
     */
    public boolean undo() {
        if (this.tasksHistory.size() > 1) {
            UniqueTaskList topTaskList = this.tasksHistory.pop();
            UniqueTaskList oldTaskList = this.tasksHistory.pop();
            topTaskList.getInternalList().setAll(oldTaskList.getInternalList());
            this.tasksHistory.push(topTaskList);
            
            UniqueTagList topTagList = this.tagsHistory.pop();
            UniqueTagList oldTagList = this.tagsHistory.pop();
            topTagList.getInternalList().setAll(oldTagList.getInternalList());
            this.tagsHistory.push(topTagList);
            
            return true;
        }
        return false;
    }
    
    
    /*************************
     *  TAG-LEVEL OPERATIONS *
     *************************/
    
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        updateTagHistoryStack();
        UniqueTagList topList = this.getUniqueTagList();
        topList.add(t);
    }
    
    
    private void updateTagTopList() {
        UniqueTagList topList = this.getUniqueTagList();
        topList.getInternalList().clear();
        
        for (Task task : this.getTasks()) {
            for (Tag tag : task.getTags().getInternalList()) {
                try {
                    topList.add(tag);
                } catch (DuplicateTagException e) {}
            }
        }
    }
    
    /******************
     *  UTIL METHODS  *
     ******************/
    
    @Override
    public String toString() {
        return tasksHistory.peek().getInternalList().size() + " tasks, " 
                + tagsHistory.peek().getInternalList().size() +  " tags";
        // TODO: refine later
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoDoBird // instanceof handles nulls
                && this.tasksHistory.peek().equals(((DoDoBird) other).tasksHistory.peek())
                && this.tagsHistory.peek().equals(((DoDoBird) other).tagsHistory.peek()));
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasksHistory.peek(), tagsHistory.peek());
    }
    
    /**
     * Updates tasks history
     * Maintains the reference of the top UniqueTaskList
     * Call this method for add, delete, update
     */
    private void updateTaskHistoryStack() {
        UniqueTaskList topList = this.tasksHistory.pop();
        UniqueTaskList oldList = this.copyTaskList(topList.getInternalList());
        this.tasksHistory.push(oldList);
        this.tasksHistory.push(topList);
    }
    
    /**
     * Updates tags history
     * Maintains the reference of the top UniqueTagList
     * Call this method for add, delete, update
     */
    private void updateTagHistoryStack() {
        UniqueTagList topList = this.tagsHistory.pop();
        UniqueTagList oldList = this.copyTagList(topList.getInternalList());
        this.tagsHistory.push(oldList);
        this.tagsHistory.push(topList);
        
    }
    
    private UniqueTaskList copyTaskList(Collection<Task> old) {
        UniqueTaskList newList = new UniqueTaskList();

        for (Task t : old) {
            try {
                newList.add(new Task(t));
            } catch (UniqueTaskList.DuplicateTaskException e) {}
        }
        return newList;
    }
    
 
    private UniqueTagList copyTagList(Collection<Tag> old) {
        UniqueTagList newList = new UniqueTagList();
        
        for (Tag t : old) {
            try {
                newList.add(t);
            } catch (UniqueTagList.DuplicateTagException e) {}
        }
        return newList;
    }
    
}
