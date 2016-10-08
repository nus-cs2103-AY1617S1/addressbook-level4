package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.ReadOnlyActivityManager;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable ActivityManager that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableActivityManager implements ReadOnlyActivityManager {

    @XmlElement
    private List<XmlAdaptedActivity> activities;
    @XmlElement
    private List<Tag> tags;

    {
        activities = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableActivityManager() {}

    /**
     * Conversion
     */
    public XmlSerializableActivityManager(ReadOnlyActivityManager src) {
        activities.addAll(src.getListActivity().stream().map(XmlAdaptedActivity::new).collect(Collectors.toList()));
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
    public ActivityList getActivityList() {
        ActivityList lists = new ActivityList();
        for (XmlAdaptedActivity p : activities) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<Activity> getListActivity() {
        return activities.stream().map(p -> {
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
