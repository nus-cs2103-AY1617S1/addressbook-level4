package seedu.agendum.storage;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.*;

import javax.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String isCompleted;
    @XmlElement(required = true)
    private String lastUpdatedTime;
    @XmlElement(required = false)
    private String startDateTime;
    @XmlElement(required = false)
    private String endDateTime;

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
        name = source.getName().fullName;
        isCompleted = Boolean.toString(source.isCompleted());
        lastUpdatedTime = source.getLastUpdatedTime().format(formatter);

        if (source.getStartDateTime().isPresent()) {
            startDateTime = source.getStartDateTime().get().format(formatter);
        }

        if (source.getEndDateTime().isPresent()) {
            endDateTime = source.getEndDateTime().get().format(formatter);
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final boolean markedAsCompleted = Boolean.valueOf(isCompleted);

        Task newTask = new Task(name);
        newTask.setLastUpdatedTime(LocalDateTime.parse(this.lastUpdatedTime, formatter));

        if (markedAsCompleted) {
            newTask.markAsCompleted();
        }

        if (startDateTime != null) {
            newTask.setStartDateTime(Optional.ofNullable(LocalDateTime.parse(this.startDateTime, formatter)));
        }

        if (endDateTime != null) {
            newTask.setEndDateTime(Optional.ofNullable(LocalDateTime.parse(this.endDateTime, formatter)));
        }

        return newTask;
    }
}
