package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyTaskBook {

    private final UniqueTaskList persons;
    private final UniqueTaskList undatedList;
    private final UniqueTagList tags;

    {
        persons = new UniqueTaskList();
        undatedList = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public AddressBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueDatedTaskList(), toBeCopied.getUniqueUndatedTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public AddressBook(UniqueTaskList persons, UniqueTaskList undatedTasks, UniqueTagList tags) {
        resetData(persons.getInternalList(), undatedTasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyAddressBook() {
        return new AddressBook();
    }

//// list overwrite operations

    public ObservableList<Task> getPersons() {
    	sortPersonFilteredLists();
        return persons.getInternalList();
    }
    
    public ObservableList<Task> getUndatedTasks() {
    	sortUndatedFilteredLists();
        return undatedList.getInternalList();
    }
    
    private void sortUndatedFilteredLists(){
   	undatedList.sort(Task.Comparators.NAME);
    }

    private void sortPersonFilteredLists(){
        persons.sort(Task.Comparators.DATE);
    }
    
    public void setPersons(List<Task> persons) {
        this.persons.getInternalList().setAll(persons);
    }
    
    public void setUndatedTasks(List<Task> undatedTasks) {
        this.undatedList.getInternalList().setAll(undatedTasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newPersons, 
            Collection<? extends ReadOnlyTask> newUndatedTasks,
            Collection<Tag> newTags) {
        setPersons(newPersons.stream().map(Task::new).collect(Collectors.toList()));
        setUndatedTasks(newUndatedTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getDatedTaskList(), newData.getUndatedTaskList(), newData.getTagList());
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent person already exists.
     */
    public void addPerson(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        if (checkIfDated(p)){
            persons.add(p);
        }
        else{
            undatedList.add(p);
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

    public boolean removePerson(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {       
        if (persons.contains(key)) {
            persons.remove(key);
            return true;
        } 
        else if (undatedList.contains(key)){
            undatedList.remove(key);
            return true;
        }
        else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
   
    public boolean completeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (persons.contains(key)) {
            persons.complete(key);
            return true;
        } 
        else if (undatedList.contains(key)){
            undatedList.complete(key);
            return true;
        }
        else {
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
        return persons.getInternalList().size() + " persons, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    //this gets called when ModelManager.indicateAddressBookChanged() 
    @Override
    public List<ReadOnlyTask> getDatedTaskList() {
    	sortPersonFilteredLists();
        return Collections.unmodifiableList(persons.getInternalList());
    }
    
    //this also gets called when ModelManager.indicateAddressBookChanged() 
    @Override
    public List<ReadOnlyTask> getUndatedTaskList() {
    	sortUndatedFilteredLists();
        return Collections.unmodifiableList(undatedList.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueDatedTaskList() {
    	sortPersonFilteredLists();
        return this.persons;
    }
    
    @Override
    public UniqueTaskList getUniqueUndatedTaskList() {
    	sortUndatedFilteredLists();
        return this.undatedList;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.undatedList.equals(((AddressBook) other).undatedList)
                && this.tags.equals(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, undatedList, tags);
    }

}
