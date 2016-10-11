package seedu.taskman.storage;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.event.Activity;
import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.event.UniqueActivityList;
import seedu.taskman.model.ReadOnlyTaskMan;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TaskMan that is serializable to XML format
 */
@XmlRootElement(name = "taskMan")
public class XmlSerializableTaskMan implements ReadOnlyTaskMan {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<Tag> tags;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskMan() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskMan(ReadOnlyTaskMan src) {
        //TODO: writing tasks and events
        //implemented XmlAdaptedTask(Activity activity) for now
        tasks.addAll(src.getActivityList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            //TODO: better error handling
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueActivityList getUniqueActivityList() {
        UniqueActivityList lists = new UniqueActivityList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(new Activity(p.toModelType()));
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<Activity> getActivityList() {
        return tasks.stream().map(p -> {
            try {
                return new Activity(p.toModelType());
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
