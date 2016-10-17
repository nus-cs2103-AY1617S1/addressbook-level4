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
import seedu.address.model.task.UniqueTaskList.PersonNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList persons;
    private UniqueTaskList deadlines;
    private UniqueTaskList todo;
    private final UniqueTagList tags;

    {
        persons = new UniqueTaskList();
        deadlines = new UniqueTaskList();
        todo = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniquePersonList(), toBeCopied.getUniqueDeadlineList(), toBeCopied.getUniqueTodoList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskBook(UniqueTaskList persons, UniqueTaskList deadlines, UniqueTaskList todo, UniqueTagList tags) {
        resetData(persons.getInternalList(), deadlines.getInternalList(), todo.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyAddressBook() {
        return new TaskBook();
    }

//// list overwrite operations

    public ObservableList<Task> getPersons() {
        return persons.getInternalList();
    }

    public ObservableList<Task> getDeadlines() {
        return deadlines.getInternalList();
    }

    public ObservableList<Task> getTodo() {
        return todo.getInternalList();
    }
    
    public void setPersons(List<Task> persons) {
        this.persons.getInternalList().setAll(persons);
    }

    public void setDeadlines(List<Task> deadlines) {
        this.deadlines.getInternalList().setAll(deadlines);
    }

    public void setTodo(List<Task> todo) {
        this.todo.getInternalList().setAll(todo);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newPersons, Collection<? extends ReadOnlyTask> newDeadlines,
    					  Collection<? extends ReadOnlyTask> newTodo, Collection<Tag> newTags) {
        setPersons(newPersons.stream().map(Task::new).collect(Collectors.toList()));
        setPersons(newDeadlines.stream().map(Task::new).collect(Collectors.toList()));
        setPersons(newTodo.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getPersonList(), newData.getDeadlineList(), newData.getTodoList(), newData.getTagList());
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Task p) throws UniqueTaskList.DuplicatePersonException {
        syncTagsWithMasterList(p);
        if (p.getTaskCategory() == 1)
        	persons.add(p);
        else if (p.getTaskCategory() == 2)
        	deadlines.add(p);
        else
        	todo.add(p);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task person) {
        final UniqueTagList personTags = person.getTags();
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of person tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : personTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        person.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removePerson(ReadOnlyTask key) throws UniqueTaskList.PersonNotFoundException {
        int taskCategory = key.getTaskCategory();
        if(taskCategory == 1){
            if (persons.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.PersonNotFoundException();
            }
        }
        else if (taskCategory == 2){
            if (deadlines.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.PersonNotFoundException();
            }
        }
        else{
            if (todo.remove(key)) {
                return true;
            } else {
                throw new UniqueTaskList.PersonNotFoundException();
            }
        }
    }

/*    public boolean removeDeadline(ReadOnlyTask key) throws UniqueTaskList.PersonNotFoundException {
        if (deadlines.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.PersonNotFoundException();
        }
    }
    
    public boolean removeTodo(ReadOnlyTask key) throws UniqueTaskList.PersonNotFoundException {
        if (todo.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.PersonNotFoundException();
        }
    }*/
    
    public boolean changePerson(ReadOnlyTask target, String args, char category) throws PersonNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if(category == 'E'){
            if (persons.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.PersonNotFoundException();
            }        
        }
        else if(category == 'D'){
            if (deadlines.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.PersonNotFoundException();
            }        
        }
        else{
            if (todo.edit(target, args)) {
                return true;
            } else {
                throw new UniqueTaskList.PersonNotFoundException();
            }        
        }
    }

    public boolean changeDeadline(ReadOnlyTask target, String args) throws PersonNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if (deadlines.edit(target, args)) {
            return true;
        } else {
            throw new UniqueTaskList.PersonNotFoundException();
        }        
    }
    
    public boolean changeTodo(ReadOnlyTask target, String args) throws PersonNotFoundException, IllegalValueException {
        // TODO Auto-generated method stub
        //System.out.println("dummy");
        if (todo.edit(target, args)) {
            return true;
        } else {
            throw new UniqueTaskList.PersonNotFoundException();
        }        
    }
//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return persons.getInternalList().size() + " persons, " + tags.getInternalList().size() +  " tags";
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
    public List<ReadOnlyTask> getPersonList() {
        return Collections.unmodifiableList(persons.getInternalList());
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
    public UniqueTaskList getUniquePersonList() {
        return this.persons;
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
                && this.persons.equals(((TaskBook) other).persons)
                && this.tags.equals(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

}
