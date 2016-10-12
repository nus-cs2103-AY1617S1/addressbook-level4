package seedu.inbx0.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String level;

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
        name = source.getName().fullName;
        startDate = source.getStartDate().value;
        startTime = source.getStartTime().value;
        endDate = source.getEndDate().value;
        endTime = source.getEndTime().value;
        level = source.getLevel().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted Task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> TaskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            TaskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Date startDate = new Date(this.startDate);
        final Time startTime = new Time(this.startTime);
        final Date endDate = new Date(this.endDate);
        final Time endTime = new Time(this.endTime);
        final Importance level = new Importance(this.level);
        final UniqueTagList tags = new UniqueTagList(TaskTags);
        return new Task(name, startDate, startTime, endDate, endTime, level, tags);
    }
}
