package seedu.todolist.storage;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.*;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private String locationParameter;
    
    @XmlElement
    private String remarksParameter;

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
        locationParameter = source.getLocationParameter().toString();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final LocationParameter location = new LocationParameter(this.locationParameter);
        final RemarksParameter remarks = new RemarksParameter(this.remarksParameter);
        return new Task(name, location, remarks);
    }
}
