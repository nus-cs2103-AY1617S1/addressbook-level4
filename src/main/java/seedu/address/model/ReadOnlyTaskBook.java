package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskBook {

    /**
     * Returns an unmodifiable view of tasks list
     */
    ObservableList<Task> getTasks();

}
