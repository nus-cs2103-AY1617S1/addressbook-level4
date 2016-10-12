package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.*;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.RecurrenceType;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

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
        taskName = source.getTaskName();
        startDateTime = source.getStartDateTime();
        endDateTime = source.getEndDateTime();
        location = source.getLocation();
        priority = source.getPriority();
        recurringType = source.getRecurringType();
        numberOfRecurrence = source.getNumberOfRecurrence();
        category = source.getCategory();
        description = source.getDescription();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Task toModelType() throws IllegalValueException {
        final String taskName = this.taskName;
        final Date startDateTime = this.startDateTime;
        final Date endDateTime = this.endDateTime;
        final String location = this.location;
        final PriorityLevel priority = this.priority;
        final RecurrenceType recurringType = this.recurringType;
        final int numberOfRecurrence = this.numberOfRecurrence;
        final String category = this.category;
        final String description = this.description;
        return new Task(taskName, startDateTime, endDateTime, location, priority,
                recurringType, numberOfRecurrence, category, description);
    }
}
