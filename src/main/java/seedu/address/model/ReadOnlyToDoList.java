package seedu.address.model;


import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.UniqueToDoList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyToDoList {

    UniqueTagList getUniqueTagList();

    UniqueToDoList getUniqueToDoList();

    /**
     * Returns an unmodifiable view of ToDos list
     */
    List<ReadOnlyToDo> getToDoList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
