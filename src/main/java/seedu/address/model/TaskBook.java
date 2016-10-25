package seedu.address.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * @@author A0138993L
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList events;
    private UniqueTaskList deadlines;
    private UniqueTaskList todo;
    //private UniqueTaskList completed;
    private final UniqueTagList tags;

    {
        events = new UniqueTaskList();
        deadlines = new UniqueTaskList();
        todo = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Tasks and Tags are copied into this addressbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueEventList(), toBeCopied.getUniqueDeadlineList(), toBeCopied.getUniqueTodoList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this addressbook
     */
    public TaskBook(UniqueTaskList events, UniqueTaskList deadlines, UniqueTaskList todo, UniqueTagList tags) {
        resetData(events.getInternalList(), deadlines.getInternalList(), todo.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyAddressBook() {
        return new TaskBook();
    }

//// list overwrite operations

    public ObservableList<Task> getEvents() {
        return events.getInternalList();
    }

    public ObservableList<Task> getDeadlines() {
        return deadlines.getInternalList();
    }

    public ObservableList<Task> getTodo() {
        return todo.getInternalList();
    }
    
    //public ObservableList<Task> getCompleted() {
    //    return completed.getInternalList();
    //}
    
    public void setEvents(List<Task> events) {
        this.events.getInternalList().setAll(events);
    }

    public void setDeadlines(List<Task> deadlines) {
        this.deadlines.getInternalList().setAll(deadlines);
    }

    public void setTodo(List<Task> todo) {
        this.todo.getInternalList().setAll(todo);
    }
    
    //public void setCompleted(List<Task> completed) {
    //    this.completed.getInternalList().setAll(completed);
    //}

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newEvents, Collection<? extends ReadOnlyTask> newDeadlines,
            Collection<? extends ReadOnlyTask> newTodo, Collection<Tag> newTags) {
        setEvents(newEvents.stream().map(Task::new).collect(Collectors.toList()));
        setDeadlines(newDeadlines.stream().map(Task::new).collect(Collectors.toList()));
        setTodo(newTodo.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
}

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getEventList(), newData.getDeadlineList(), newData.getTodoList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(t);
        if (t.getTaskCategory() == 1)
        	events.add(t);
        else if (t.getTaskCategory() == 2)
        	deadlines.add(t);
        else
        	todo.add(t);
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
        int taskCategory = key.getTaskCategory();
        if(taskCategory == 1){
            if (events.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }
        }
        else if (taskCategory == 2){
            if (deadlines.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }
        }
        else{
            if (todo.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }
        }
    }
    
    public boolean completeTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        int category = target.getTaskCategory();
        if(category == 1){
            if (events.completed(target)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else if(category == 2){
            if (deadlines.completed(target)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else{
            if (todo.completed(target)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
    }

    public void overdueTask() {
    	for (Task task: events) {
    		events.markOverdue(task);
        	System.out.println("events:" + task.getOverdue());
    	}
    	for (Task task: deadlines) {
        	deadlines.markOverdue(task);
        	System.out.println("deadlines:" + task.getOverdue());	
    	}
    }

/*    public boolean removeDeadline(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
=======
    public boolean removeDeadline(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
>>>>>>> code_cleanup
        if (deadlines.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean removeTodo(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (todo.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }*/
    
    public boolean changeTask(ReadOnlyTask target, String args, char category) throws TaskNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if(category == 'E'){
            if (events.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else if(category == 'D'){
            if (deadlines.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
        else{
            if (todo.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.TaskNotFoundException();
            }        
        }
    }

    public boolean changeDeadline(ReadOnlyTask target, String args) throws TaskNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if (deadlines.edit(target, args)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }        
    }
    
    public boolean changeTodo(ReadOnlyTask target, String args) throws TaskNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if (todo.edit(target, args)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }        
    }
//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return events.getInternalList().size() + " events, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    public String toStringDeadlines() {
        return deadlines.getInternalList().size() + " deadlines, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    public String toStringTodo() {
        return todo.getInternalList().size() + " todo, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }

    public List<ReadOnlyTask> getDeadlineList() {
        return Collections.unmodifiableList(deadlines.getInternalList());
    }

    public List<ReadOnlyTask> getTodoList() {
        return Collections.unmodifiableList(todo.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueEventList() {
        return this.events;
    }

    public UniqueTaskList getUniqueDeadlineList() {
        return this.deadlines;
    }

    public UniqueTaskList getUniqueTodoList() {
        return this.todo;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.events.equals(((TaskBook) other).events)
                && this.tags.equals(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(events, tags);
    }

}
