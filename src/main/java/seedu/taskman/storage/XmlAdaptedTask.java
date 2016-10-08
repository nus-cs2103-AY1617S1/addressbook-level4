package seedu.taskman.storage;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.event.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = false)
    private String deadline;
    @XmlElement(required = false)
    private String status;
    @XmlElement(required = false)
    private String schedule;
    @XmlElement(required = false)
    private String frequency;

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
        title = source.getTitle().title;
        deadline = (source.getDeadline()) != null
                ? source.getDeadline().toString()
                : null;
        status = (source.getStatus()) != null
                ? source.getStatus().toString()
                : null;
        schedule = (source.getSchedule()) != null
                ? source.getSchedule().toString()
                : null;
        frequency = (source.getFrequency()) != null
                ? source.getFrequency().toString()
                : null;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this JAXB-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        // Todo: fix these... expect null strings to come in this.deadline & other fields
        final Title title = new Title(this.title);
        final Deadline deadline = new Deadline(this.deadline);
        final Status status = new Status(this.status);
        final Frequency frequency = (this.frequency) != null
                ? new Frequency(this.frequency)
                : null;
        final Schedule schedule = (this.schedule) != null
                ? new Schedule(this.schedule)
                : null;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(title, deadline, status, frequency, schedule, tags);
    }
}
