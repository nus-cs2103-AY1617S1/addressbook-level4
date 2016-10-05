package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.item.ReadOnlyFloatingTask;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniqueFloatingTaskList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook implements ReadOnlyAddressBook {

    @XmlElement
    private List<XmlAdaptedFloatingTask> floatingTasks;
    {
        floatingTasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableAddressBook() {}

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        floatingTasks.addAll(src.getFloatingTaskList().stream().map(XmlAdaptedFloatingTask::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueFloatingTaskList getUniqueFloatingTaskList() {
        UniqueFloatingTaskList lists = new UniqueFloatingTaskList();
        for (XmlAdaptedFloatingTask p : floatingTasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyFloatingTask> getFloatingTaskList() {
        return floatingTasks.stream().map(p -> {
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
