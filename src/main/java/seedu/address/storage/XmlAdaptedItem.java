package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedItem {

    @XmlElement(required = true)
    private String description;

    /*
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    */
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedItem() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedItem(ReadOnlyItem source) {
        description = source.getDescription().getFullDescription();
        /*
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        */
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Item toModelType() throws IllegalValueException {
        /*final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }*/
        final Description description = new Description(this.description);
        return new FloatingTask(description);
        //TODO: use better method to instantiate!!
    }
}
