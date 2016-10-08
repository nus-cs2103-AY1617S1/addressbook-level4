package seedu.menion.storage;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.*;
import seedu.menion.model.tag.Tag;
import seedu.menion.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String reminder;
    @XmlElement(required = true)
    private String priority;

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
    public XmlAdaptedTask(ReadOnlyActivity source) {
        name = source.getName().fullName;
        deadline = source.getDeadline().value;
        reminder = source.getReminder().value;
        priority = source.getPriority().value;
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
    public Activity toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Deadline deadline = new Deadline(this.deadline);
        final Reminder reminder = new Reminder(this.reminder);
        final Priority priority = new Priority(this.priority);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Activity(name, deadline, reminder, priority, tags);
    }
}
