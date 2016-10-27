package harmony.mastermind.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.ReadOnlyTaskManager;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.TaskListComparator;
import harmony.mastermind.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TaskManager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedTask> floatingTasks;
    @XmlElement
    private List<XmlAdaptedEvent> events;
    @XmlElement
    private List<XmlAdaptedDeadline> deadlines;
    @XmlElement
    private List<XmlAdaptedArchive> archives;
    @XmlElement
    private List<Tag> tags;
    
    private TaskListComparator comparator;
    

    {
        floatingTasks = new ArrayList<>();
        events = new ArrayList<>();
        deadlines= new ArrayList<>();
        archives = new ArrayList<>();
        tags = new ArrayList<>();
        comparator = new TaskListComparator();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskManager() {}

    /**
     * Conversion
     * 
     */
    //@@author A0124797R
    public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
        floatingTasks.addAll(src.getFloatingTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
        deadlines.addAll(src.getDeadlineList().stream().map(XmlAdaptedDeadline::new).collect(Collectors.toList()));
        tags = src.getTagList();
        archives.addAll(src.getArchiveList().stream()
                .map(XmlAdaptedArchive::new).collect(Collectors.toList()));
    }

    //@@author A0124797R
    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask xmlt : floatingTasks) {
            try {
                lists.add(xmlt.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        for (XmlAdaptedEvent xmle : events) {
            try {
                lists.add(xmle.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        for (XmlAdaptedDeadline xmld : deadlines) {
            try {
                lists.add(xmld.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        
        lists.getInternalList().sort(comparator);
        return lists;
    }

    //@@author A0124797R
    @Override
    public UniqueTaskList getUniqueFloatingTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        
        for (XmlAdaptedTask xmlt : floatingTasks) {
            try {
                lists.add(xmlt.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    //@@author A0124797R
    @Override
    public UniqueTaskList getUniqueEventList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedEvent xmle : events) {
            try {
                lists.add(xmle.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    //@@author A0124797R
    @Override
    public UniqueTaskList getUniqueDeadlineList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedDeadline xmld : deadlines) {
            try {
                lists.add(xmld.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }
    
    //@@author A0124797R
    @Override
    public ArchiveTaskList getUniqueArchiveList() {
        ArchiveTaskList lists = new ArchiveTaskList();
        for (XmlAdaptedArchive p : archives) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
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

    //@@author A0124797R 
    @Override
    public List<ReadOnlyTask> getTaskList() {
        List<ReadOnlyTask> tasks = getFloatingTaskList();
        List<ReadOnlyTask> event = getEventList();
        List<ReadOnlyTask> deadline = getDeadlineList();

        tasks.addAll(event);
        tasks.addAll(deadline);
        
        return tasks;
    }
    
    //@@author A0124797R 
    @Override
    public List<ReadOnlyTask> getFloatingTaskList() {
        return floatingTasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
    
    //@@author A0124797R 
    @Override
    public List<ReadOnlyTask> getEventList() {
        return events.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
    
    //@@author A0124797R 
    @Override
    public List<ReadOnlyTask> getDeadlineList() {
        return deadlines.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
    
    //@@author A0124797R 
    @Override
    public List<ReadOnlyTask> getArchiveList() {
        return archives.stream().map(p -> {
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