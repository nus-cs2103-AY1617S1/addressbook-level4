package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    
    @XmlElement
    private List<XmlAdaptedDeadline> deadlined = new ArrayList<>();
    
    @XmlElement(required = true)
    private String priority;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyTask source) {
        name = source.getName().fullName;
       deadlined = new ArrayList<>();
        for (Deadline deadline : source.getDeadlines()) {
            deadlined.add(new XmlAdaptedDeadline(deadline));
        }
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
    	final List<Deadline> taskDeadlines = new ArrayList<>();
        for (XmlAdaptedDeadline deadline : deadlined) {
            taskDeadlines.add(deadline.toModelType());
        }
    	final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final UniqueDeadlineList deadlines = new UniqueDeadlineList(taskDeadlines);
        final Priority priority = new Priority(this.priority);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, deadlines, priority, tags);
    }
}
