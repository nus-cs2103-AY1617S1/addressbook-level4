package seedu.manager.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.activity.Activity;
import seedu.manager.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of an Activity.
 */
public class XmlAdaptedActivity {

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedActivity() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedActivity
     */
    public XmlAdaptedActivity(Activity source) {
        name = source.name;
        // TODO: implement other required fields if necessary
//        phone = source.getPhone().value;
//        email = source.getEmail().value;
//        address = source.getAddress().value;
//        tagged = new ArrayList<>();
//        for (Tag tag : source.getTags()) {
//            tagged.add(new XmlAdaptedTag(tag));
//        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Activity toModelType() throws IllegalValueException {
        final List<Tag> activityTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            activityTags.add(tag.toModelType());
        }
        // TODO: implement for other fields if necessary
//        final Name name = new Name(this.name);
//        final Phone phone = new Phone(this.phone);
//        final Email email = new Email(this.email);
//        final Address address = new Address(this.address);
//        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Activity(this.name);
    }
}
