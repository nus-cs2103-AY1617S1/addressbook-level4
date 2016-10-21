package seedu.savvytasker.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.savvytasker.commons.exceptions.IllegalValueException;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.TaskList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable SavvyTasker that is serializable to XML format
 */
@XmlRootElement(name = "savvytasker")
public class XmlSerializableSavvyTasker implements ReadOnlySavvyTasker {

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableSavvyTasker() {}

    /**
     * Conversion
     */
    public XmlSerializableSavvyTasker(ReadOnlySavvyTasker src) {
        tasks.addAll(src.getReadOnlyListOfTasks().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    @Override
    public TaskList getTaskList() {
        TaskList lists = new TaskList();
        for (XmlAdaptedTask t : tasks) {
            try {
                lists.add(t.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getReadOnlyListOfTasks() {
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
