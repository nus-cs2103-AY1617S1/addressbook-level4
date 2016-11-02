package seedu.taskmanager.model.tag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmanager.commons.exceptions.DuplicateDataException;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.CollectionUtil;
import seedu.taskmanager.model.tag.UniqueTagList.DuplicateTagException;

import java.util.*;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTagList implements Iterable<Tag> {
    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();
    
    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }
    
    //@@author A0140060A
    /**
     * Signals that an operation tried to access a nonexistent tag.
     */
    public static class TagNotFoundException extends IllegalValueException {
        public static final String MESSAGE_TAG_NOT_FOUND = "Tag %1$s does not exist! Tags must exist in order to be deletable";
        protected TagNotFoundException(Tag tag) {
            super(String.format(MESSAGE_TAG_NOT_FOUND, tag));
        }
    }
    //@@author
    
    /**
     * Constructs empty TagList.
     */
    public UniqueTagList() {}

    /**
     * Varargs/array constructor, enforces no nulls or duplicates.
     */
    public UniqueTagList(Tag... tags) throws DuplicateTagException {
        assert !CollectionUtil.isAnyNull((Object[]) tags);
        final List<Tag> initialTags = Arrays.asList(tags);
        if (!CollectionUtil.elementsAreUnique(initialTags)) {
            throw new DuplicateTagException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * java collections constructor, enforces no null or duplicate elements.
     */
    public UniqueTagList(Collection<Tag> tags) throws DuplicateTagException {
        CollectionUtil.assertNoNullElements(tags);
        if (!CollectionUtil.elementsAreUnique(tags)) {
            throw new DuplicateTagException();
        }
        internalList.addAll(tags);
    }

    /**
     * java set constructor, enforces no nulls.
     */
    public UniqueTagList(Set<Tag> tags) {
        CollectionUtil.assertNoNullElements(tags);
        internalList.addAll(tags);
    }

    /**
     * Copy constructor, insulates from changes in source.
     */
    public UniqueTagList(UniqueTagList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * All tags in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<Tag> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        this.internalList.clear();
        this.internalList.addAll(replacement.internalList);
    }

    /**
     * Adds every tag from the argument list that does not yet exist in this list.
     */
    public void mergeFrom(UniqueTagList tags) {
        final Set<Tag> alreadyInside = this.toSet();
        for (Tag tag : tags) {
            if (!alreadyInside.contains(tag)) {
                internalList.add(tag);
            }
        }
    }
    
    //@@author A0140060A
    /**
     * Removes a Tag from the list.
     *
     * @throws TagNotFoundException if the Tag to remove does not exist in the list.
     */
    public void remove(Tag toRemove) throws TagNotFoundException {
        assert toRemove != null;
        if (contains(toRemove)) {
            this.internalList.remove(toRemove);
        } else {
            throw new TagNotFoundException(toRemove);
        }
    }
    
    /**
     * removes every tag in the argument list from this list.
     * @param tagsToRemove 
     * @throws TagNotFoundException if a Tag to remove does not exist in the list.
     */
    public void remove(UniqueTagList tagsToRemove) throws TagNotFoundException {
        for (Tag tag : tagsToRemove) {
            remove(tag);
        }
        /*
        ObservableList<Tag> internalListCopy = FXCollections.observableArrayList(internalList);
        this.internalList.clear();
        
        for (Tag tag : internalListCopy) {
            if (!tagsToRemove.contains(tag)) {
                internalList.add(tag);
            }
        }*/
    }
    //@@author
    
    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Tag toAdd) throws DuplicateTagException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Tag> getInternalList() {
        return internalList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTagList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
