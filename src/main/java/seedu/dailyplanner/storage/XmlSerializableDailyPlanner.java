package seedu.dailyplanner.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.ReadOnlyDailyPlanner;
import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.category.UniqueCategoryList;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableDailyPlanner implements ReadOnlyDailyPlanner {

    @XmlElement
    private List<XmlAdaptedTask> persons;
    @XmlElement
    private List<Category> categories;

    {
        persons = new ArrayList<>();
        categories = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableDailyPlanner() {}

    /**
     * Conversion
     */
    public XmlSerializableDailyPlanner(ReadOnlyDailyPlanner src) {
        persons.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        categories = src.getCategoryList();
    }

    @Override
    public UniqueCategoryList getUniqueCatList() {
        try {
            return new UniqueCategoryList(categories);
        } catch (UniqueCategoryList.DuplicateCategoryException e) {
            //TODO: better error handling
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
                //TODO: better error handling
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
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Category> getCategoryList() {
        return Collections.unmodifiableList(categories);
    }

}
