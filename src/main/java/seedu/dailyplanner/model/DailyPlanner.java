package seedu.dailyplanner.model;

import javafx.collections.ObservableList;
import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.category.UniqueCategoryList;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList;
import seedu.dailyplanner.model.task.UniqueTaskList.PersonNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
 * .equals comparison)
 */
public class DailyPlanner implements ReadOnlyDailyPlanner {

    private final UniqueTaskList persons;
    private final UniqueCategoryList tags;

    {
        persons = new UniqueTaskList();
        tags = new UniqueCategoryList();
    }

    public DailyPlanner() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public DailyPlanner(ReadOnlyDailyPlanner toBeCopied) {
        this(toBeCopied.getUniquePersonList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public DailyPlanner(UniqueTaskList persons, UniqueCategoryList tags) {
        resetData(persons.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyDailyPlanner getEmptyAddressBook() {
        return new DailyPlanner();
    }

//// list overwrite operations

    public ObservableList<Task> getPersons() {
        return persons.getInternalList();
    }
    
    public ObservableList<Task> getPinnedTasks() {
   	return persons.getInternalPinnedList();
       }

    public void setPersons(List<Task> persons) {
        this.persons.getInternalList().setAll(persons);
    }

    public void setTags(Collection<Category> categories) {
        this.tags.getInternalList().setAll(categories);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newPersons, Collection<Category> newTags) {
        setPersons(newPersons.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyDailyPlanner newData) {
        resetData(newData.getPersonList(), newData.getTagList());
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
        persons.add(p);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task person) {
        final UniqueCategoryList personTags = person.getTags();
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        final Map<Category, Category> masterTagObjects = new HashMap<>();
        for (Category category : tags) {
            masterTagObjects.put(category, category);
        }

        // Rebuild the list of person tags using references from the master list
        final Set<Category> commonTagReferences = new HashSet<>();
        for (Category category : personTags) {
            commonTagReferences.add(masterTagObjects.get(category));
        }
        person.setTags(new UniqueCategoryList(commonTagReferences));
    }

    public boolean removePerson(ReadOnlyTask key) throws UniqueTaskList.PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.PersonNotFoundException();
        }
    }
    
    public void markTaskAsComplete(ReadOnlyTask key) throws UniqueTaskList.PersonNotFoundException {
        persons.complete(key);
    }
    

    public void pinTask(ReadOnlyTask taskToPin) throws PersonNotFoundException {
        persons.pin(taskToPin);
    }
    
    public void unpinTask(int targetIndex) {
    	persons.unpin(targetIndex);
	}
    
    public void uncompleteTask(int targetIndex) {
        persons.uncomplete(targetIndex);
        
    }
    
	public void resetPinBoard() {
		persons.resetPinBoard();
	}
    
    public int indexOf(Task task) {
        return persons.getIndexOf(task);
    }
    
    public void updatePinBoard() {
        persons.updatePinBoard();
    }
//// tag-level operations

    public void addTag(Category t) throws UniqueCategoryList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return persons.getInternalList().size() + " persons, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getPersonList() {
        return Collections.unmodifiableList(persons.getInternalList());
    }

    @Override
    public List<Category> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniquePersonList() {
        return this.persons;
    }

    @Override
    public UniqueCategoryList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DailyPlanner // instanceof handles nulls
                && this.persons.equals(((DailyPlanner) other).persons)
                && this.tags.equals(((DailyPlanner) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }


}