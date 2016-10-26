package seedu.malitio.storage;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueEventList;
import seedu.malitio.model.task.UniqueFloatingTaskList;

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
    private List<XmlAdaptedFloatingTask> floatingTasks;
    @XmlElement
    private List<XmlAdaptedDeadline> deadlines;
    @XmlElement
    private List<XmlAdaptedEvent> events;
    @XmlElement
    private List<Tag> tags;

    {
        floatingTasks = new ArrayList<>();
        deadlines = new ArrayList<>();
        events = new ArrayList<>();
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
        floatingTasks.addAll(src.getFloatingTaskList().stream().map(XmlAdaptedFloatingTask::new).collect(Collectors.toList()));
        deadlines.addAll(src.getDeadlineList().stream().map(XmlAdaptedDeadline::new).collect(Collectors.toList()));
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
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
    public UniqueDeadlineList getUniqueDeadlineList() {
        UniqueDeadlineList lists = new UniqueDeadlineList();
        for (XmlAdaptedDeadline p : deadlines) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }
    
    @Override
    public UniqueEventList getUniqueEventList() {
        UniqueEventList lists = new UniqueEventList();
        for (XmlAdaptedEvent p : events) {
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

    @Override
    public List<ReadOnlyDeadline> getDeadlineList() {
        return deadlines.stream().map(p -> {
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
    public List<ReadOnlyEvent> getEventList() {
        return events.stream().map(p -> {
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
