package seedu.forgetmenot.storage;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private Boolean done;
    @XmlElement(required = true)
    private String start;
    @XmlElement(required = true)
    private String end;
    @XmlElement(required = true)
    private String recurrence;

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
        done = source.getDone().value;
        start = source.getStartTime().toString();
        end = source.getEndTime().toString();
        recurrence = source.getRecurrence().days;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final Done done = new Done(this.done);
        final Time start = new Time(this.start);
        final Time end = new Time(this.end);
        final Recurrence recurrence = new Recurrence(this.recurrence);
        return new Task(name, done, start, end, recurrence);
    }
}
