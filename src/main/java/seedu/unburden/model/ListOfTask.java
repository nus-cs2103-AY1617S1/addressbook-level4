package seedu.unburden.model;

import javafx.collections.ObservableList;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;
import seedu.unburden.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ListOfTask implements ReadOnlyListOfTask {

    private final UniqueTaskList persons;
    private final UniqueTagList tags;

    {
        persons = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public ListOfTask() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public ListOfTask(ReadOnlyListOfTask toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public ListOfTask(UniqueTaskList persons, UniqueTagList tags) {
        resetData(persons.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyListOfTask getEmptyAddressBook() {
        return new ListOfTask();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return persons.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.persons.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newPersons, Collection<Tag> newTags) {
        setTasks(newPersons.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyListOfTask newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicatePersonException {
        syncTagsWithMasterList(p);
        persons.add(p);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList personTags = task.getTags();
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
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removePerson(ReadOnlyTask key) throws UniqueTaskList.PersonNotFoundException {
        if (persons.remove(key)) {
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

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(persons.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.persons;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListOfTask // instanceof handles nulls
                && this.persons.equals(((ListOfTask) other).persons)
                && this.tags.equals(((ListOfTask) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
