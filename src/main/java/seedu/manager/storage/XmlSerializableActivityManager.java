package seedu.manager.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.ReadOnlyActivityManager;
import seedu.manager.model.activity.Activity;
import seedu.manager.model.activity.ActivityList;
import seedu.manager.model.tag.Tag;
import seedu.manager.model.tag.UniqueTagList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable ActivityManager that is serializable to XML format
 */
@XmlRootElement(name = "activitymanager")
public class XmlSerializableActivityManager implements ReadOnlyActivityManager {

    @XmlElement(name = "activity")
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
