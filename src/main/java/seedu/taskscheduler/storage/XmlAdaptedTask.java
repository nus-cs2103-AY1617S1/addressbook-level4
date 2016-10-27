package seedu.taskscheduler.storage;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.tag.UniqueTagList;
import seedu.taskscheduler.model.task.Location;
import seedu.taskscheduler.model.task.Name;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.ReadOnlyTask.TaskType;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.TaskDateTime;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String type;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        startDateTime = source.getStartDate().toString();
        endDateTime = source.getEndDate().toString();
        address = source.getLocation().value;
        type = source.getType().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>(); 
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final TaskDateTime startDateTime = new TaskDateTime(this.startDateTime);
        final TaskDateTime endDateTime = new TaskDateTime(this.endDateTime);
        final Location address = new Location(this.address);
        final TaskType type = TaskType.valueOf(this.type);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, startDateTime, endDateTime, address, type, tags);
    }
}
