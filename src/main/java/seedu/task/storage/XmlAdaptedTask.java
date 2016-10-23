package seedu.task.storage;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {
	
	@XmlElement(required = true)
	private String name;
	
	@XmlElement
	private String description;
	
	@XmlElement
	private Boolean status;
	
	@XmlElement
	private String deadline;

//    @XmlElement
//    private List<XmlAdaptedTag> tagged = new ArrayList<>();

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
        name = source.getTask().fullName;
        description = source.getDescriptionValue();
        status = source.getTaskStatus();
        deadline = source.getDeadlineValue();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     * 	- if a description or deadline is a string, make it null.
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {

        final Name name = new Name(this.name);
        final Description description = this.description.isEmpty()? null : new Description(this.description);
        final Deadline deadline = this.deadline.isEmpty()? null : new Deadline(this.deadline);
        final Boolean status = new Boolean(this.status);
        
        return new Task(name, description, deadline, status);
    }
}
