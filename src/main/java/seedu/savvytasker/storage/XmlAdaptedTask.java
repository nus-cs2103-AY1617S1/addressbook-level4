package seedu.savvytasker.storage;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import seedu.savvytasker.commons.exceptions.IllegalValueException;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.RecurrenceType;
import seedu.savvytasker.model.task.Task;

//@@author A0139915W
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private int id;
    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = false)
    private Date startDateTime;
    @XmlElement(required = false)
    private Date endDateTime;
    @XmlElement(required = false)
    private String location;
    @XmlElement(required = false)
    private PriorityLevel priority;
    @XmlElement(required = false)
    private RecurrenceType recurringType;
    @XmlElement(required = false)
    private int numberOfRecurrence;
    @XmlElement(required = false)
    private String category;
    @XmlElement(required = false)
    private String description;
    @XmlElement(required = true)
    private boolean isArchived;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        id = source.getId();
        taskName = source.getTaskName();
        startDateTime = source.getStartDateTime();
        endDateTime = source.getEndDateTime();
        location = source.getLocation();
        priority = source.getPriority();
        recurringType = source.getRecurringType();
        numberOfRecurrence = source.getNumberOfRecurrence();
        category = source.getCategory();
        description = source.getDescription();
        isArchived = source.isArchived();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Task toModelType() throws IllegalValueException {
        final int id = this.id;
        final String taskName = this.taskName;
        final Date startDateTime = this.startDateTime;
        final Date endDateTime = this.endDateTime;
        final String location = this.location;
        final PriorityLevel priority = this.priority;
        final RecurrenceType recurringType = this.recurringType;
        final int numberOfRecurrence = this.numberOfRecurrence;
        final String category = this.category;
        final String description = this.description;
        final boolean isArchived = this.isArchived;
        return new Task(id, taskName, startDateTime, endDateTime, location, priority,
                recurringType, numberOfRecurrence, category, description, isArchived);
    }
}
//@@author
