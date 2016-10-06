package seedu.taskman.storage;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.task.*;

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
    private String deadline;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String recurrence;
    @XmlElement(required = true)
    private String schedule;

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
    public XmlAdaptedTask(EventInterface source) {
        title = source.getTitle().title;
        deadline = ((Task) source).getDeadline().toString();
        status = ((Task) source).getStatus().toString();
        recurrence = source.getRecurrence().toString();
        schedule = source.getSchedule().toString();
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
        final Deadline deadline = new Deadline(this.deadline);
        final Status status = new Status(this.status);
        final Recurrence recurrence = new Recurrence(this.recurrence);
        final Schedule schedule = new Schedule(this.schedule);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(title, deadline, status, recurrence, schedule, tags);
    }
}
