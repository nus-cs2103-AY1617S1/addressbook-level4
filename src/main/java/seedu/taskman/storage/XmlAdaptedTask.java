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
    @XmlElement(required = true)
    private String status;

    @XmlElement(required = false)
    private Long deadline;
    @XmlElement(required = false)
    private Long frequency;

    @XmlElement(required = false)
    private Long scheduleStart;
    @XmlElement(required = false)
    private Long scheduleEnd;

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
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public XmlAdaptedTask(Activity source) {
        title = source.getTitle().title;

        if (source.getStatus().isPresent()) {
            status = source.getStatus().get().toString();
        }

        if (source.getDeadline().isPresent()) {
            deadline = source.getDeadline().get().epochSecond;
        }

        if (source.getSchedule().isPresent()) {
            Schedule schedule = source.getSchedule().get();
            scheduleStart = schedule.startEpochSecond;
            scheduleEnd = schedule.endEpochSecond;
        }

        if (source.getFrequency().isPresent()) {
            frequency = source.getFrequency().get().seconds;
        }

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
        final Title title = new Title(this.title);
        final Status status = new Status(this.status);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final Deadline deadline = this.deadline != null
                ? new Deadline(this.deadline)
                : null;
        final Frequency frequency = this.frequency != null
                ? new Frequency(this.frequency)
                : null;
        final Schedule schedule = this.scheduleStart != null && this.scheduleEnd != null
                ? new Schedule(this.scheduleStart, this.scheduleEnd)
                : null;

        Task task = new Task(title, tags, deadline, schedule, frequency);
        task.setStatus(status);
        return task;
    }
}
