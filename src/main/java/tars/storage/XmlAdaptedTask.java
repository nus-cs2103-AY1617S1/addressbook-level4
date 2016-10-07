package tars.storage;

import tars.commons.exceptions.IllegalValueException;
import tars.model.task.*;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String priority;
    @XmlElement
    private DateTime dateTime;
    @XmlElement
    private boolean status;


    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().taskName;
        priority = source.getPriority().priorityLevel;
        dateTime = source.getDateTime();
        status = source.getStatus().status;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final DateTime dateTime = new DateTime(this.dateTime.startDateString, this.dateTime.endDateString);
        final Status status = new Status(this.status);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, dateTime, priority, status, tags);
    }
}
