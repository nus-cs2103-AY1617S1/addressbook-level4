package seedu.flexitrack.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.Name;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    // @XmlElement(required = true)
    // private Boolean isEvent;
    // @XmlElement(required = true)
    // private Boolean isTask;
    @XmlElement
    private String dueDate;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().toString();
        dueDate = source.getDueDate().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             task
     */
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final DateTimeInfo dueDate = new DateTimeInfo(this.dueDate);
        final DateTimeInfo startTime = new DateTimeInfo(this.startTime);
        final DateTimeInfo endTime = new DateTimeInfo(this.endTime);
        return new Task(name, dueDate, startTime, endTime);
    }
}
