package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.ReadOnlyAliasManager;
import seedu.address.model.alias.ReadOnlyAlias;
import seedu.address.model.alias.UniqueAliasList;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@@author A0143756Y-reused
/**
 * An immutable AliasManager that is serializable to XML format
 */

@XmlRootElement(name = "aliasmanager")
public class XmlSerializableAliasManager implements ReadOnlyAliasManager {

    @XmlElement
    private List<XmlAdaptedAlias> aliases;

    {
        aliases = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableAliasManager() {}

    /**
     * Conversion
     */
    public XmlSerializableAliasManager(ReadOnlyAliasManager src) {
        aliases.addAll(src.getAliasList().stream().map(XmlAdaptedAlias::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueAliasList getUniqueAliasList() {
        UniqueAliasList lists = new UniqueAliasList();
        for (XmlAdaptedAlias p : aliases) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyAlias> getAliasList() {
        return aliases.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
