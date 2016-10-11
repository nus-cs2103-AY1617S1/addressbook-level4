package seedu.taskitty.storage;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String date;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;

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
        name = source.getName().fullName;
        TaskDate sourceDate = source.getDate();
        TaskTime sourceStartTime = source.getStartTime();
        TaskTime sourceEndTime = source.getEndTime();
        if (sourceDate != null) {
            date = sourceDate.toString();
        }
        if (sourceStartTime != null) {
            startTime = sourceStartTime.toString();
        }
        if (sourceEndTime != null) {
            endTime = sourceEndTime.toString();
        }
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
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        
        TaskDate date = null;
        if (this.date != null) {
            date = new TaskDate(this.date);
        }
        
        TaskTime startTime = null;
        if (this.startTime != null) {
            startTime = new TaskTime(this.startTime);
        }
        
        TaskTime endTime = null;
        if (this.endTime != null) {
            endTime = new TaskTime(this.endTime);
        }
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, date, startTime, endTime, tags);
    }
}
