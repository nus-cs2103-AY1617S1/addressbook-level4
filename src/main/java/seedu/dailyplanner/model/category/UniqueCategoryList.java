package seedu.dailyplanner.model.category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.dailyplanner.commons.exceptions.DuplicateDataException;
import seedu.dailyplanner.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Category#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueCategoryList implements Iterable<Category> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateCategoryException extends DuplicateDataException {
        protected DuplicateCategoryException() {
            super("Operation would result in duplicate tags");
        }
    }

    private final ObservableList<Category> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueCategoryList() {}

    /**
     * Varargs/array constructor, enforces no nulls or duplicates.
     */
    public UniqueCategoryList(Category... tags) throws DuplicateCategoryException {
        assert !CollectionUtil.isAnyNull((Object[]) tags);
        final List<Category> initialTags = Arrays.asList(tags);
        if (!CollectionUtil.elementsAreUnique(initialTags)) {
            throw new DuplicateCategoryException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * java collections constructor, enforces no null or duplicate elements.
     */
    public UniqueCategoryList(Collection<Category> categories) throws DuplicateCategoryException {
        CollectionUtil.assertNoNullElements(categories);
        if (!CollectionUtil.elementsAreUnique(categories)) {
            throw new DuplicateCategoryException();
        }
        internalList.addAll(categories);
    }

    /**
     * java set constructor, enforces no nulls.
     */
    public UniqueCategoryList(Set<Category> categories) {
        CollectionUtil.assertNoNullElements(categories);
        internalList.addAll(categories);
    }

    /**
     * Copy constructor, insulates from changes in source.
     */
    public UniqueCategoryList(UniqueCategoryList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * All tags in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<Category> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(UniqueCategoryList replacement) {
        this.internalList.clear();
        this.internalList.addAll(replacement.internalList);
    }

    /**
     * Adds every tag from the argument list that does not yet exist in this list.
     */
    public void mergeFrom(UniqueCategoryList tags) {
        final Set<Category> alreadyInside = this.toSet();
        for (Category category : tags) {
            if (!alreadyInside.contains(category)) {
                internalList.add(category);
            }
        }
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Category toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateCategoryException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Category toAdd) throws DuplicateCategoryException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateCategoryException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Category> getInternalList() {
        return internalList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCategoryList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueCategoryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
