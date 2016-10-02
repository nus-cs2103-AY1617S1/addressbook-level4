package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.UniqueToDoList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ToDoList implements ReadOnlyToDoList {

    private final UniqueToDoList ToDos;
    private final UniqueTagList tags;

    {
        ToDos = new UniqueToDoList();
        tags = new UniqueTagList();
    }

    public ToDoList() {}

    /**
     * ToDos and Tags are copied into this ToDoList
     */
    public ToDoList(ReadOnlyToDoList toBeCopied) {
        this(toBeCopied.getUniqueToDoList(), toBeCopied.getUniqueTagList());
    }

    /**
     * ToDos and Tags are copied into this ToDoList
     */
    public ToDoList(UniqueToDoList ToDos, UniqueTagList tags) {
        resetData(ToDos.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyToDoList getEmptyToDoList() {
        return new ToDoList();
    }

//// list overwrite operations

    public ObservableList<ToDo> getToDos() {
        return ToDos.getInternalList();
    }

    public void setToDos(List<ToDo> ToDos) {
        this.ToDos.getInternalList().setAll(ToDos);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyToDo> newToDos, Collection<Tag> newTags) {
        setToDos(newToDos.stream().map(ToDo::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyToDoList newData) {
        resetData(newData.getToDoList(), newData.getTagList());
    }

//// ToDo-level operations

    /**
     * Adds a ToDo to the address book.
     * Also checks the new ToDo's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the ToDo to point to those in {@link #tags}.
     *
     * @throws UniqueToDoList.DuplicateToDoException if an equivalent ToDo already exists.
     */
    public void addToDo(ToDo p) throws UniqueToDoList.DuplicateToDoException {
        syncTagsWithMasterList(p);
        ToDos.add(p);
    }

    /**
     * Ensures that every tag in this ToDo:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(ToDo ToDo) {
        final UniqueTagList ToDoTags = ToDo.getTags();
        tags.mergeFrom(ToDoTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of ToDo tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : ToDoTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        ToDo.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeToDo(ReadOnlyToDo key) throws UniqueToDoList.ToDoNotFoundException {
        if (ToDos.remove(key)) {
            return true;
        } else {
            throw new UniqueToDoList.ToDoNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return ToDos.getInternalList().size() + " ToDos, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyToDo> getToDoList() {
        return Collections.unmodifiableList(ToDos.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueToDoList getUniqueToDoList() {
        return this.ToDos;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToDoList // instanceof handles nulls
                && this.ToDos.equals(((ToDoList) other).ToDos)
                && this.tags.equals(((ToDoList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ToDos, tags);
    }
}
