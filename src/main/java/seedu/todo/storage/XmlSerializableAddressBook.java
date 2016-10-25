package seedu.todo.storage;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.ReadOnlyAddressBook;
import seedu.todo.model.person.ReadOnlyPerson;
import seedu.todo.model.person.UniquePersonList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagCollection;

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
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<Tag> tags;

    {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableAddressBook() {}

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagCollection getUniqueTagList() {
//        try {
//            return new UniqueTagCollection(tags);
//        } catch (UniqueTagCollection.DuplicateTagException e) {
//            //TODO: better error handling
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    @Override
    public UniquePersonList getUniquePersonList() {
        UniquePersonList lists = new UniquePersonList();
        for (XmlAdaptedPerson p : persons) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyPerson> getPersonList() {
        return persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
