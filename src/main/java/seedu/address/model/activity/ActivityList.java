package seedu.address.model.activity;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;

public class ActivityList implements Iterable<Activity> {
	
	private final ObservableList<Activity> internalList = FXCollections.observableArrayList();
	
	/* Construct an empty ActivityList */
	public ActivityList() {}

    /**
     * Adds a activity to the list.
     */
    public void add(Activity toAdd){
        assert toAdd != null;
        internalList.add(toAdd);
    }
	
    public ObservableList<Activity> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals(
                ((ActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
