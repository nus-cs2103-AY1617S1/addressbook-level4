package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.FloatingTask;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyFloatingTask;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

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
    public XmlAdaptedFloatingTask(ReadOnlyFloatingTask source) {
        name = source.getName().name;
        priorityValue = source.getPriorityValue().priorityValue;        
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public FloatingTask toModelType() throws IllegalValueException {
        final String name = new String(this.name);
        final String priorityValue = new String(this.priorityValue);
        return new FloatingTask(new Name(name), new Priority(priorityValue));
    }
}
