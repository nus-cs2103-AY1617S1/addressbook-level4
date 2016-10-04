package tars.model;

import javafx.collections.ObservableList;
import tars.model.task.Person;
import tars.model.task.ReadOnlyPerson;
import tars.model.task.UniquePersonList;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Tars implements ReadOnlyTars {

    private final UniquePersonList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniquePersonList();
        tags = new UniqueTagList();
    }

    public Tars() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public Tars(ReadOnlyTars toBeCopied) {
        this(toBeCopied.getUniquePersonList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public Tars(UniquePersonList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTars getEmptyTars() {
        return new Tars();
    }

//// list overwrite operations

    public ObservableList<Person> getPersons() {
        return tasks.getInternalList();
    }

    public void setPersons(List<Person> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyPerson> newPersons, Collection<Tag> newTags) {
        setPersons(newPersons.stream().map(Person::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTars newData) {
        resetData(newData.getPersonList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to tars.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniquePersonList.DuplicatePersonException if an equivalent task already exists.
     */
    public void addPerson(Person p) throws UniquePersonList.DuplicatePersonException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Person task) {
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

    public boolean removePerson(ReadOnlyPerson key) throws UniquePersonList.PersonNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniquePersonList.PersonNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyPerson> getPersonList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniquePersonList getUniquePersonList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tars // instanceof handles nulls
                && this.tasks.equals(((Tars) other).tasks)
                && this.tags.equals(((Tars) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
