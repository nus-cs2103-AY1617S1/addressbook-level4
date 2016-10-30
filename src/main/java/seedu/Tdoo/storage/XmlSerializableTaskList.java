package seedu.Tdoo.storage;

import seedu.Tdoo.model.ReadOnlyTaskList;
import seedu.Tdoo.model.task.*;

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
