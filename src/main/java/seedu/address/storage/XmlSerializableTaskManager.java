package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.item.ReadOnlyFloatingTask;
import seedu.address.model.item.UniqueFloatingTaskList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Task Manager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedFloatingTask> floatingTasks;
    {
        floatingTasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskManager() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
        floatingTasks.addAll(src.getFloatingTaskList().stream().map(XmlAdaptedFloatingTask::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueFloatingTaskList getUniqueFloatingTaskList() {
        UniqueFloatingTaskList lists = new UniqueFloatingTaskList();
        for (XmlAdaptedFloatingTask p : floatingTasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyFloatingTask> getFloatingTaskList() {
        return floatingTasks.stream().map(p -> {
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
