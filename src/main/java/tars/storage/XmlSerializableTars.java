package tars.storage;

import tars.commons.exceptions.IllegalValueException;
import tars.model.ReadOnlyTars;
import tars.model.task.ReadOnlyTask;
import tars.model.task.UniqueTaskList;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Tars that is serializable to XML format
 */
@XmlRootElement(name = "tars")
public class XmlSerializableTars implements ReadOnlyTars {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<Tag> tags;
    @XmlElement
    private List<XmlAdaptedRsvTask> rsvTasks;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
        rsvTasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTars() {}

    /**
     * Conversion
     */
    public XmlSerializableTars(ReadOnlyTars src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        rsvTasks.addAll(src.getRsvTaskList().stream().map(XmlAdaptedRsvTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {

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
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

    @Override
    public UniqueRsvTaskList getUniqueRsvTaskList() {
        UniqueRsvTaskList lists = new UniqueRsvTaskList();
        for (XmlAdaptedRsvTask rt : rsvTasks) {
            try {
                lists.add(rt.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<RsvTask> getRsvTaskList() {
        return rsvTasks.stream().map(rt -> {
            try {
                return rt.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}