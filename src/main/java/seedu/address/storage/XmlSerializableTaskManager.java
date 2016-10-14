package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.UniqueTaskList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Task Manager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedTask> undoneTasks;
    {
        undoneTasks = new ArrayList<>();
    }
    
    @XmlElement
    private List<XmlAdaptedTask> doneTasks;
    {
        doneTasks = new ArrayList<>();
    }


    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskManager() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
        undoneTasks.addAll(src.getUndoneTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        doneTasks.addAll(src.getDoneTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueTaskList getUniqueUndoneTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : undoneTasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException | ParseException e) {
                System.out.println("Parse exception");
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getUndoneTaskList() {
        return undoneTasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException | ParseException e) {
                e.printStackTrace();
                System.out.println("Parse exception");

                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public UniqueTaskList getUniqueDoneTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : doneTasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException | ParseException e) {
                System.out.println("Parse exception");
                //TODO: better error handling
            }
        }
        return lists;
    }


    @Override
    public List<ReadOnlyTask> getDoneTaskList() {
        return doneTasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException | ParseException e) {
                e.printStackTrace();
                System.out.println("Parse exception");

                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
