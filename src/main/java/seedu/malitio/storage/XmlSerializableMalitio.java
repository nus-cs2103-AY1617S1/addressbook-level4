package seedu.malitio.storage;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.ReadOnlySchedule;
import seedu.malitio.model.task.ReadOnlyTask;
import seedu.malitio.model.task.UniqueScheduleList;
import seedu.malitio.model.task.UniqueTaskList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Malitio that is serializable to XML format
 */
@XmlRootElement(name = "malitio")
public class XmlSerializableMalitio implements ReadOnlyMalitio {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedSchedule> schedules;
    @XmlElement
    private List<Tag> tags;

    {
        tasks = new ArrayList<>();
        schedules = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableMalitio() {}

    /**
     * Conversion
     */
    public XmlSerializableMalitio(ReadOnlyMalitio src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        schedules.addAll(src.getScheduleList().stream().map(XmlAdaptedSchedule::new).collect(Collectors.toList()));
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
        return Collections.unmodifiableList(tags);
    }

    @Override
    public UniqueScheduleList getUniqueScheduleList() {
        UniqueScheduleList lists = new UniqueScheduleList();
        for (XmlAdaptedSchedule p : schedules) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlySchedule> getScheduleList() {
        return schedules.stream().map(p -> {
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
