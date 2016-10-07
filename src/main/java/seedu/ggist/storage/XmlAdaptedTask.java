package seedu.ggist.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.tag.Tag;
import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of GGist.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
 
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
        taskName = source.getTaskName().taskName;
        date = source.getDate().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final TaskName taskName = new TaskName(this.taskName);
        final Date date = new Date(this.date);
        final Time startTime = new Time(this.startTime);
        final Time endTime = new Time(this.endTime);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(taskName, date, startTime, endTime, tags);
    }
}
