package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.ReadOnlyAlias;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Alias.
 */
public class XmlAdaptedAlias {

    @XmlElement(required = true)
    private String alias;
    @XmlElement(required = true)
    private String originalPhrase;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedAlias() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedAlias(ReadOnlyAlias source) {
        alias = source.getAlias();
        originalPhrase = source.getOriginalPhrase();
    }

    /**
     * Converts this jaxb-friendly adapted alias object into the model's Alias object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted alias
     */
    public Alias toModelType() throws IllegalValueException {
        final String alias = new String(this.alias);
        final String originalPhrase = new String(this.originalPhrase);
        
        return new Alias(alias, originalPhrase);
    }
}
