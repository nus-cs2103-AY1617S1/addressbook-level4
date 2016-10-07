package seedu.oneline.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String starttime;
    @XmlElement(required = true)
    private String endtime;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String recurrence;

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
        name = source.getName().name;
        starttime = source.getStartTime().toString();
        endtime = source.getEndTime().toString();
        deadline = source.getDeadline().toString();
        recurrence = source.getRecurrence().toString();
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
        final TaskName name = new TaskName(this.name);
        final TaskTime startTime = new TaskTime(this.starttime);
        final TaskTime endTime = new TaskTime(this.endtime);
        final TaskTime deadline = new TaskTime(this.deadline);
        final TaskRecurrence recurrence = new TaskRecurrence(this.recurrence);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, startTime, endTime, deadline, recurrence, tags);
    }
}
