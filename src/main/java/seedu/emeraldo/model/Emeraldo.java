package seedu.emeraldo.model;

import javafx.collections.ObservableList;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.tag.UniqueTagList;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.model.task.Task;
import seedu.emeraldo.model.task.UniquePersonList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Emeraldo implements ReadOnlyEmeraldo {

    private final UniquePersonList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniquePersonList();
        tags = new UniqueTagList();
    }

    public Emeraldo() {}

    /**
     * Persons and Tags are copied into this task manager
     */
    public Emeraldo(ReadOnlyEmeraldo toBeCopied) {
        this(toBeCopied.getUniquePersonList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this task manager
     */
    public Emeraldo(UniquePersonList persons, UniqueTagList tags) {
        resetData(persons.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyEmeraldo getEmptyEmeraldo() {
        return new Emeraldo();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setPersons(List<Task> persons) {
        this.tasks.getInternalList().setAll(persons);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newPersons, Collection<Tag> newTags) {
        setPersons(newPersons.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyEmeraldo newData) {
        resetData(newData.getPersonList(), newData.getTagList());
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniquePersonList.DuplicateTaskException if an equivalent person already exists.
     */
    public void addPerson(Task p) throws UniquePersonList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
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

    public boolean removePerson(ReadOnlyTask key) throws UniquePersonList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniquePersonList.TaskNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " persons, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getPersonList() {
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
                || (other instanceof Emeraldo // instanceof handles nulls
                && this.tasks.equals(((Emeraldo) other).tasks)
                && this.tags.equals(((Emeraldo) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
