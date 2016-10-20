package seedu.address.model.deadline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of deadlines that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Deadline#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueDeadlineList implements Iterable<Deadline> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateDeadlineException extends DuplicateDataException {
        protected DuplicateDeadlineException() {
            super("Operation would result in duplicate ");
        }
    }

    private final ObservableList<Deadline> internalList = FXCollections.observableArrayList();
	public Deadline value;

    /**
     * Constructs empty DeadlineList.
     */
    public UniqueDeadlineList() {}

    /**
     * Varargs/array constructor, enforces no nulls or duplicates.
     */
    public UniqueDeadlineList(Deadline... deadlines) throws DuplicateDeadlineException {
        assert !CollectionUtil.isAnyNull((Object[]) deadlines );
        final List<Deadline> initialDeadlines = Arrays.asList();
        if (!CollectionUtil.elementsAreUnique(initialDeadlines)) {
            throw new DuplicateDeadlineException();
        }
        internalList.addAll(initialDeadlines);
    }

    /**
     * java collections constructor, enforces no null or duplicate elements.
     */
    public UniqueDeadlineList(Collection<Deadline> deadlines) throws DuplicateDeadlineException {
        CollectionUtil.assertNoNullElements(deadlines);
        if (!CollectionUtil.elementsAreUnique(deadlines)) {
            throw new DuplicateDeadlineException();
        }
        internalList.addAll();
    }

    /**
     * java set constructor, enforces no nulls.
     */
    public UniqueDeadlineList(Set<Deadline> deadlines) {
    	if(!deadlines.isEmpty()){
    		setDeadline(deadlines.iterator().next());
    	}
        CollectionUtil.assertNoNullElements(deadlines);
        internalList.addAll(deadlines);
    }

    /**
     * Copy constructor, insulates from changes in source.
     */
    public UniqueDeadlineList(UniqueDeadlineList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * All  in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<Deadline> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the  in this list with those in the argument Deadline list.
     */
    public void setDeadlines(UniqueDeadlineList replacement) {
        this.internalList.clear();
        this.internalList.addAll(replacement.internalList);
    }

    /**
     * Adds every Deadline from the argument list that does not yet exist in this list.
     */
    public void mergeFrom(UniqueDeadlineList deadlines) {
        final Set<Deadline> alreadyInside = this.toSet();
        for (Deadline deadline : deadlines) {
            if (!alreadyInside.contains(deadline)) {
                internalList.add(deadline);
            }
        }
    }

    /**
     * Returns true if the list contains an equivalent Deadline as the given argument.
     */
    public boolean contains(Deadline toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Deadline to the list.
     *
     * @throws DuplicateDeadlineException if the Deadline to add is a duplicate of an existing Deadline in the list.
     */
    public void add(Deadline toAdd) throws DuplicateDeadlineException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateDeadlineException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Deadline> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Deadline> getInternalList() {
        return internalList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDeadlineList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueDeadlineList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
    
    private void setDeadline(Deadline deadline){
    	this.value = deadline;
    }
    
    public Deadline getDeadline(){
    	return this.value;
    }
}
