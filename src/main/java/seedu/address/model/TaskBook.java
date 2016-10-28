package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList datedTasks;
    private final UniqueTaskList undatedTasks;
    private final UniqueTagList tags;

    {
        datedTasks = new UniqueTaskList();
        undatedTasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Persons and Tags are copied into this taskbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueDatedTaskList(), toBeCopied.getUniqueUndatedTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this taskbook
     */
    public TaskBook(UniqueTaskList persons, UniqueTaskList undatedTasks, UniqueTagList tags) {
        resetData(persons.getInternalList(), undatedTasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyAddressBook() {
        return new TaskBook();
    }

//// list overwrite operations

    
    public ObservableList<Task> getDatedTasks() {
    	sortDatedTaskLists();
        return datedTasks.getInternalList();
    }
    
    public ObservableList<Task> getUndatedTasks() {
    	sortUndatedTaskLists();
        return undatedTasks.getInternalList();
    }
    
    private void sortUndatedTaskLists(){
   	undatedTasks.sort(Task.Comparators.NAME);
    }

    private void sortDatedTaskLists(){
        datedTasks.sort(Task.Comparators.DATE);
    }
    
    public void setDatedTasks(List<Task> datedTasks) {
        this.datedTasks.getInternalList().setAll(datedTasks);
    }
    
    public void setUndatedTasks(List<Task> undatedTasks) {
        this.undatedTasks.getInternalList().setAll(undatedTasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, 
            Collection<? extends ReadOnlyTask> newUndatedTasks,
            Collection<Tag> newTags) {
        setDatedTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setUndatedTasks(newUndatedTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getDatedTaskList(), newData.getUndatedTaskList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the task book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        if (checkIfDated(p)){
            datedTasks.add(p);
        }
        else{
            undatedTasks.add(p);
        }
    }
    
    private boolean checkIfDated(ReadOnlyTask d){
        if(d.getDatetime().toString().equals("")){
            return false;
        }
        else{
            return true;
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
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(new UniqueTagList(correctTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {       
        if (datedTasks.contains(key)) {
            datedTasks.remove(key);
            return true;
        } 
        else if (undatedTasks.contains(key)){
            undatedTasks.remove(key);
            return true;
        }
        else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
   
    public boolean completeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (datedTasks.contains(key)) {
            datedTasks.complete(key);
            return true;
        } 
        else if (undatedTasks.contains(key)){
            undatedTasks.complete(key);
            return true;
        }
        else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean uncompleteTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (datedTasks.contains(key)) {
            datedTasks.uncomplete(key);
            return true;
        } 
        else if (undatedTasks.contains(key)){
            undatedTasks.uncomplete(key);
            return true;
        }
        else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean overdueTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        if(datedTasks.contains(target)){
            datedTasks.overdue(target);
            return true;
        }
        else{
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
        return datedTasks.getInternalList().size() + " datedTasks, " +
               undatedTasks.getInternalList().size() + " undatedTasks, "+
               tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    //this gets called when ModelManager.indicateTaskBookChanged() 
    @Override
    public List<ReadOnlyTask> getDatedTaskList() {
    	sortDatedTaskLists();
        return Collections.unmodifiableList(datedTasks.getInternalList());
    }
    
    //this also gets called when ModelManager.indicateTaskBookChanged() 
    @Override
    public List<ReadOnlyTask> getUndatedTaskList() {
    	sortUndatedTaskLists();
        return Collections.unmodifiableList(undatedTasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueDatedTaskList() {
    	sortDatedTaskLists();
        return this.datedTasks;
    }
    
    @Override
    public UniqueTaskList getUniqueUndatedTaskList() {
    	sortUndatedTaskLists();
        return this.undatedTasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.datedTasks.equals(((TaskBook) other).datedTasks)
                && this.undatedTasks.equals(((TaskBook) other).undatedTasks)
                && this.tags.equals(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(datedTasks, undatedTasks, tags);
    }

    //@@author A0139024M 
    public boolean postponed(Task target) throws UniqueTaskList.TaskNotFoundException {
        if(datedTasks.contains(target)){
            datedTasks.postponed(target);
            return true;
        }
        else{
            throw new UniqueTaskList.TaskNotFoundException();   
        }        
    }
    
    public boolean expireTask(Task target) throws UniqueTaskList.TaskNotFoundException {
        if(datedTasks.contains(target)){
            datedTasks.expire(target);
            return true;
        }
        else{
            throw new UniqueTaskList.TaskNotFoundException(); 
        }
        
    }

    public boolean floatingStatusReset(Task undatedTarget) throws UniqueTaskList.TaskNotFoundException {
        if(undatedTasks.contains(undatedTarget)){
            undatedTasks.postponed(undatedTarget);
            return true;
        }
        else{
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    //@@author 
}
