package seedu.todo.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.ReadOnlyToDoList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable ToDoList that is serializable to XML format
 */
@XmlRootElement(name = "ToDoList")
public class XmlSerializableToDoList implements ReadOnlyToDoList {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableToDoList() {}
    
    //@@author A0093896H
    /**
     * Conversion
     */
    public XmlSerializableToDoList(ReadOnlyToDoList src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }
    //@@author

    @Override
    public UniqueTagList getUniqueTagList() {
        UniqueTagList list = new UniqueTagList();
        for (XmlAdaptedTag t : tags) {
            try {
                list.add(t.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList list = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                list.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return list;
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
    
    //@@author A0093896H
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
