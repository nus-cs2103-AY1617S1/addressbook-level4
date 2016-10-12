package seedu.address.storage.alias;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Alias;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedAlias {

    @XmlElement(required = true)
    private String shortcut;
    @XmlElement(required = true)
    private String sentence;
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedAlias() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedAlias(Alias source) {
    	shortcut = source.getShortcut().toString();
    	sentence = source.getSentence().toString();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Alias toModelType() throws IllegalValueException {
        return new Alias(shortcut, sentence);
    }
}
