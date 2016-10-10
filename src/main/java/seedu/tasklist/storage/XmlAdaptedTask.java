package seedu.tasklist.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private int uniqueID;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().taskDetails;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        priority = source.getPriority().priorityLevel;
        uniqueID = source.getUniqueID();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final TaskDetails name = new TaskDetails(this.name);
        final StartTime startTime = new StartTime(this.phone);
        final EndTime endTime = new EndTime(this.email);
        final Priority priority = new Priority(this.priority);
        final int uniqueID = this.uniqueID;
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Task(name, startTime, endTime, priority, tags);
    }
}
