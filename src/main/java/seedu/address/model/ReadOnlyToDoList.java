package seedu.address.model;

import seedu.address.model.todo.ReadOnlyToDo;
import java.util.List;

/**
 * Unmodifiable view of a to-do list
 */
public interface ReadOnlyToDoList {
    List<ReadOnlyToDo> getToDoList();
}
