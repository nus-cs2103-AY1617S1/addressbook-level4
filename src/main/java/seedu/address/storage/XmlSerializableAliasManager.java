package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.AliasManager;
import seedu.address.model.ReadOnlyAliasManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.alias.ReadOnlyAlias;
import seedu.address.model.alias.UniqueAliasList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

//@XmlRootElement(name = "alias")
//public class XmlSerializableAliasManager implements ReadOnlyAliasManager {
//
//    @XmlElement
//    private HashMap<String, String> aliasMap;
//    
//    {
//        aliasMap = new HashMap<String, String>();
//    }
//
//    /**
//     * Empty constructor required for marshalling
//     */
//    public XmlSerializableAliasManager() {}
//
//    /**
//     * Conversion
//     */
//    public XmlSerializableAliasManager(ReadOnlyAliasManager alias) {
//        aliasMap.putAll(alias.getAlias());
//    }
//
//	@Override
//	public HashMap<String, String> getAlias() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getValueOf(String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//    @Override
//    public UniqueTaskList getUniqueTaskList() {
//        UniqueTaskList lists = new UniqueTaskList();
//        for (XmlAdaptedTask p : tasks) {
//            try {
//                lists.add(p.toModelType());
//            } catch (IllegalValueException e) {
//                //TODO: better error handling
//            }
//        }
//        return lists;
//    }

//    @Override
//    public List<ReadOnlyTask> getTaskList() {
//        return tasks.stream().map(p -> {
//            try {
//                return p.toModelType();
//            } catch (IllegalValueException e) {
//                e.printStackTrace();
//                //TODO: better error handling
//                return null;
//            }
//        }).collect(Collectors.toCollection(ArrayList::new));
//    }
}
