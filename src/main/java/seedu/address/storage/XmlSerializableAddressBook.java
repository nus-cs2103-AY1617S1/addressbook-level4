package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.ReadOnlyTaskScheduler;

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
public class XmlSerializableAddressBook implements ReadOnlyTaskScheduler {

    @XmlElement
    private List<XmlAdaptedTask> persons;
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
    public XmlSerializableAddressBook(ReadOnlyTaskScheduler src) {
        persons.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : persons) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
