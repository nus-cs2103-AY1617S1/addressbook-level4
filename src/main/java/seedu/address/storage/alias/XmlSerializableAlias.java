package seedu.address.storage.alias;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Alias;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Alias that is serializable to XML format
 */
//@@author A0143107U
@XmlRootElement(name = "alias")
public class XmlSerializableAlias extends UniqueItemCollection<Alias>{ 

    @XmlElement
    private List<XmlAdaptedAlias> alias;
    {
        alias = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableAlias() {}

    /**
     * Conversion
     */
    public XmlSerializableAlias(UniqueItemCollection<Alias> src) {
        alias.addAll(src.getInternalList().stream().map(XmlAdaptedAlias::new).collect(Collectors.toList()));
    }
    
    /*
     * This method is called after all the properties (except IDREF) are unmarshalled for this object, 
     *  but before this object is set to the parent object. This allows us to set the correct internal
     *  when loading from XML storage.
     */
    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        for (XmlAdaptedAlias a : alias) {
            try {
                this.add(a.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
    };



}
