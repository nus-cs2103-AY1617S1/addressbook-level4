package seedu.agendum.storage;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.*;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String isCompleted;

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
        if (markedAsCompleted) {
            newTask.markAsCompleted();
        }
        return newTask;
    }
}
