//@@author A0139916U
package seedu.savvytasker.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.savvytasker.commons.exceptions.IllegalValueException;
import seedu.savvytasker.model.alias.AliasSymbol;

public class XmlAdaptedAliasSymbol {

    @XmlElement(required = true)
    private String keyword;
    @XmlElement(required = true)
    private String representation;
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedAliasSymbol() {}
    

    /**
     * Converts a given AliasSymbol into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAliasSymbol
     */
    public XmlAdaptedAliasSymbol(AliasSymbol source) {
        keyword = source.getKeyword();
        representation = source.getRepresentation();
    }

    /**
     * Converts this jaxb-friendly adapted alias symbol object into the model's AliasSymbol object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted AliasSymbol
     */
    public AliasSymbol toModelType() throws IllegalValueException {
        return new AliasSymbol(keyword, representation);
    }
}
