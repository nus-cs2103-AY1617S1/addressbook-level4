package seedu.taskscheduler.storage;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.commons.util.DateFormatter;
import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.tag.UniqueTagList;
import seedu.taskscheduler.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        phone = source.getStartDate().toString();
        email = source.getEndDate().toString();
        address = source.getLocation().value;
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
        final List<Tag> personTags = new ArrayList<>(); 
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final TaskDateTime phone = new TaskDateTime(this.phone);
        final TaskDateTime email = new TaskDateTime(this.email);
        final Location address = new Location(this.address);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Task(name, phone, email, address, tags);
    }
}
