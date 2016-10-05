package seedu.todoList.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.ReadOnlyTodoList;
import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.UniquetaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TodoList that is serializable to XML format
 */
@XmlRootElement(name = "Todobook")
public class XmlSerializableTodoList implements ReadOnlyTodoList {

    @XmlElement
    private List<XmlAdaptedtask> tasks;
    @XmlElement
    private List<Tag> tags;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTodoList() {}

    /**
     * Conversion
     */
    public XmlSerializableTodoList(ReadOnlyTodoList src) {
        tasks.addAll(src.gettaskList().stream().map(XmlAdaptedtask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            //TODO: better error handling
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniquetaskList getUniquetaskList() {
        UniquetaskList lists = new UniquetaskList();
        for (XmlAdaptedtask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> gettaskList() {
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
        return Collections.unmodifiableList(tags);
    }

}
