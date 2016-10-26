package seedu.todoList.storage;

import seedu.todoList.model.ReadOnlyTaskList;
import seedu.todoList.model.task.*;

import java.util.List;

/**
 * An Immutable TaskList that is serializable to XML format
 */
public interface XmlSerializableTaskList extends ReadOnlyTaskList {

    @Override
    public UniqueTaskList getUniqueTaskList();

    @Override
    public List<ReadOnlyTask> getTaskList();
}
