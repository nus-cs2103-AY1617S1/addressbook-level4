<<<<<<< HEAD:src/main/java/jym/manager/storage/XmlAdaptedTag.java
package jym.manager.storage;
=======
package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/storage/XmlAdaptedTag.java

import javax.xml.bind.annotation.XmlValue;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.commons.util.CollectionUtil;
import jym.manager.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTag {

    @XmlValue
    public String tagName;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTag() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Tag toModelType() throws IllegalValueException {
        return new Tag(tagName);
    }

}
