package seedu.todo.storage;

import javax.xml.bind.annotation.XmlElement;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTag {

    @XmlElement(required = true)
    public String tagName;
    
    @XmlElement
    public String taskCount;

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
        tagName = source.getName();
        taskCount = source.getCount() + "";
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Tag toModelType() throws IllegalValueException {
        return new Tag(tagName, Integer.parseInt(taskCount));
    }

}
