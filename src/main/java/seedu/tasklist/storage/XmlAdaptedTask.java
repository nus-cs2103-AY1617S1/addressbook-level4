package seedu.tasklist.storage;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.task.*;
import seedu.tasklsit.model.tag.Tag;
import seedu.tasklsit.model.tag.UniqueTagList;

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
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getTitle().fullTitle;
        phone = source.getGroup().startDate;
        email = source.getDescription().description;
        address = source.getAddress().date;
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
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Title title = new Title(this.name);
        final StartDate startDate = new StartDate(this.phone);
        final Description description = new Description(this.email);
        final DueDate dueDate = new DueDate(this.address);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Task(title, startDate, description, dueDate, tags);
    }
}
