package seedu.oneline.storage;

import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.commons.util.CollectionUtil;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTagColor {

    @XmlElement(required = true)
    public String tagName;
    @XmlElement(required = true)
    public String tagColor;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTagColor() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTagColor(Tag source, TagColor color) {
        tagName = source.serialize();
        tagColor = color.serialize();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Entry<Tag, TagColor> toModelType() throws IllegalValueException {
        return new SimpleEntry<Tag, TagColor>(
                        toTag(),
                        toTagColor()
                    );
    }
    
    public Tag toTag() throws IllegalValueException {
        return Tag.deserialize(tagName);
    }
    
    public TagColor toTagColor() throws IllegalValueException {
        return TagColor.deserialize(tagColor);
    }

}
