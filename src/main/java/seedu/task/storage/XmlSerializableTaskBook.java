package seedu.task.storage;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList;

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
public class XmlSerializableAddressBook implements ReadOnlyTaskBook {

    @XmlElement
    private List<XmlAdaptedPerson> tasks;
//    @XmlElement
//    private List<Tag> tags;

    {
        tasks = new ArrayList<>();
//        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableAddressBook() {}

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyTaskBook src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
//        tags = src.getTagList();
    }

//    @Override
//    public UniqueTagList getUniqueTaskList() {
//        try {
//            return new UniqueTagList(tags);
//        } catch (UniqueTagList.DuplicateTagException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedPerson t : tasks) {
            try {
                lists.add(t.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

//    @Override
//    public List<Tag> getTagList() {
//        return Collections.unmodifiableList(tags);
//    }

}
