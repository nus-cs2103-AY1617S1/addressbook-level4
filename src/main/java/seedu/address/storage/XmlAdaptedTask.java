package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Detail;
import seedu.address.model.task.Done;
import seedu.address.model.task.DueByDate;
import seedu.address.model.task.DueByTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String detail;
    @XmlElement(required = true)
    private String done;
    @XmlElement(required = true)
    private String dueByDate;
    @XmlElement(required = true)
    private String dueByTime;
    @XmlElement(required = true)
    private String priority;

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
        detail = source.getDetail().details;
        done = source.checkDone().value;
        dueByDate = source.getDueByDate().value;
        dueByTime = source.getDueByTime().value;
        priority = source.getPriority().value;
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
        
        final Detail detail = new Detail(this.detail);
        final Done done = new Done(this.done);
        final DueByDate dbd = new DueByDate(this.dueByDate);
        final DueByTime dbt = new DueByTime(this.dueByTime);
        final Priority priority = new Priority(this.priority);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Task(detail, done, dbd, dbt, priority, tags);
    }
}
