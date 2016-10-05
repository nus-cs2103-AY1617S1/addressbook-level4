package seedu.todoList.model;


import java.util.List;

import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.UniquetaskList;

/**
 * Unmodifiable view of an Todo book
 */
public interface ReadOnlyTodoList {

    UniqueTagList getUniqueTagList();

    UniquetaskList getUniquetaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> gettaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
