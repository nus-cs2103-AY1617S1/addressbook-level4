package seedu.gtd.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.model.task.*;
import seedu.gtd.model.tag.Tag;
import seedu.gtd.model.tag.UniqueTagList;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String dueDate;
    @XmlElement(required = true)
    private String address;
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
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        dueDate = source.getDueDate().value;
        address = source.getAddress().value;
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
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final DueDate dueDate = new DueDate(this.dueDate);
        final Address address = new Address(this.address);
        final Priority priority = new Priority(this.priority);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, dueDate, address, priority, tags);
    }
}
