package jym.manager.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.model.ReadOnlyTaskManager;
import jym.manager.model.tag.Tag;
import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.model.task.UniqueTaskList;

/**
 * An Immutable TaskManager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
<<<<<<< HEAD:src/main/java/jym/manager/storage/XmlSerializableTaskManager.java
    private List<Tag> tags;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }
=======
    private List<XmlAdaptedTag> tags;
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/storage/XmlSerializableAddressBook.java

    /**
     * Empty constructor required for marshalling
     */
<<<<<<< HEAD:src/main/java/jym/manager/storage/XmlSerializableTaskManager.java
    public XmlSerializableTaskManager() {}
=======
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/storage/XmlSerializableAddressBook.java

    /**
     * Conversion
     */
<<<<<<< HEAD:src/main/java/jym/manager/storage/XmlSerializableTaskManager.java
    public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
=======
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/storage/XmlSerializableAddressBook.java
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        UniqueTagList lists = new UniqueTagList();
        for (XmlAdaptedTag t : tags) {
            try {
                lists.add(t.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
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

    @Override
    public List<Tag> getTagList() {
        return tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
