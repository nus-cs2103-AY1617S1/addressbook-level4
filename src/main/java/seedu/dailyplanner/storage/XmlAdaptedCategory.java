package seedu.dailyplanner.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.commons.util.CollectionUtil;
import seedu.dailyplanner.model.category.Category;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedCategory {

    @XmlValue
    public String tagName;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedCategory() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCategory(Category source) {
        tagName = source.tagName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Category toModelType() throws IllegalValueException {
        return new Category(tagName);
    }

}
