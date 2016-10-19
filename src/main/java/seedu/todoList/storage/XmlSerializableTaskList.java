package seedu.todoList.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.ReadOnlyTaskList;
import seedu.todoList.model.task.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TaskList that is serializable to XML format
 */
public interface XmlSerializableTaskList extends ReadOnlyTaskList {

    @Override
    public UniqueTaskList getUniqueTaskList();

    @Override
    public List<ReadOnlyTask> getTaskList();
}
