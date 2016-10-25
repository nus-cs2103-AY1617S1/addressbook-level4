//@@author A0146752B
package seedu.menion.storage;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable ActivityManager that is serializable to XML format
 */
@XmlRootElement(name = "activitymanager")
public class XmlSerializableActivityManager implements ReadOnlyActivityManager {

    @XmlElement
    private List<XmlAdaptedTask> tasks;


    
    @XmlElement
    private List<XmlAdaptedFloatingTask> floatingTasks;
    @XmlElement
    private List<XmlAdaptedEvent> events;
    

    {
        tasks = new ArrayList<>();
        floatingTasks = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableActivityManager() {}

    /**
     * Conversion
     */
    public XmlSerializableActivityManager(ReadOnlyActivityManager src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        floatingTasks.addAll(src.getFloatingTaskList().stream().map(XmlAdaptedFloatingTask::new).collect(Collectors.toList()));
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }


    @Override
    public UniqueActivityList getUniqueTaskList() {
        UniqueActivityList lists = new UniqueActivityList();
        for (XmlAdaptedTask t : tasks) {
            try {
                lists.add(t.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyActivity> getTaskList() {
        return tasks.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
    
    @Override
    public UniqueActivityList getUniqueFloatingTaskList() {
        UniqueActivityList lists = new UniqueActivityList();
        for (XmlAdaptedFloatingTask t : floatingTasks) {
            try {
                lists.add(t.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyActivity> getFloatingTaskList() {
        return floatingTasks.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
    
    @Override
    public UniqueActivityList getUniqueEventList() {
        UniqueActivityList lists = new UniqueActivityList();
        for (XmlAdaptedEvent t : events) {
            try {
                lists.add(t.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyActivity> getEventList() {
        return events.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }


}
