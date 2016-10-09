package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedFloatingTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priorityValue;


    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedFloatingTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedFloatingTask(ReadOnlyTask source) {
        name = source.getName().name;
        priorityValue = source.getPriorityValue().toString();        
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final String name = new String(this.name);
        Priority priority = Priority.medium;

        if (this.priorityValue.equals("high")) {
            priority = Priority.high;
        } else if (this.priorityValue.equals("medium")) {
            priority = Priority.medium;
        } else if (this.priorityValue.equals("low")) {
            priority = Priority.low;
        }
        
        return new Task(new Name(name), priority);
    }
}
