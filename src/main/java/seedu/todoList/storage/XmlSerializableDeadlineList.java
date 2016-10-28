package seedu.todoList.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.ReadOnlyTaskList;
import seedu.todoList.model.task.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@@author A0144061U-reused
/**
 * An Immutable TaskList that is serializable to XML format
 */
@XmlRootElement(name = "DeadlineList")
public class XmlSerializableDeadlineList implements XmlSerializableTaskList {

    @XmlElement
    private List<XmlAdaptedDeadline> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableDeadlineList() {}

    /**
     * Conversion
     */
    public XmlSerializableDeadlineList(ReadOnlyTaskList src) {
    	tasks.addAll(src.getTaskList().stream().map(XmlAdaptedDeadline::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedDeadline p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
