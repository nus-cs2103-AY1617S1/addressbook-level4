package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Item.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String type;
    @XmlElement(required = false)
    private String startDate;
    @XmlElement(required = false)
    private String startTime;
    @XmlElement(required = false)
    private String endDate;
    @XmlElement(required = false)
    private String endTime;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Item into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyToDo source) {
        name = source.getType().value;
        type = source.getName().value;
        startDate = "";
        startTime = "";
        endDate = "";
        endTime = "";
//        startDate = source.getStartDate().value;
//        startTime = source.getStartTime().value;
//        endDate = source.getEndDate().value;
//        endTime = source.getEndTime().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Item toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Type type = new Type(this.type);
        final Name name = new Name(this.name);
        TodoDate startDate = null;
        TodoTime startTime = null;
        TodoDate endDate = null;
        TodoTime endTime = null;
        if (type.equals(Type.EVENT_WORD)) {
            startDate = new TodoDate(this.startDate);
            startTime = new TodoTime(this.startTime);
        }
        if (type.equals(Type.DEADLINE_WORD) || type.equals(Type.EVENT_WORD)) {
            endDate = new TodoDate(this.endDate);
            endTime = new TodoTime(this.endTime);
        }
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Item(type, name, startDate, startTime, endDate, endTime, tags);
    }
}
