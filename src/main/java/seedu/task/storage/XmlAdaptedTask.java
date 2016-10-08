package seedu.task.storage;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

//    @XmlElement(required = true)
//    private String name;
//    @XmlElement(required = true)
//    private String phone;
//    @XmlElement(required = true)
//    private String email;
//    @XmlElement(required = true)
//    private String address;
	
	@XmlElement(required = true)
	private String name;
	
	@XmlElement
	private String description;
	
	@XmlElement
	private Boolean status;
//	
//	@XmlElement
//	private String deadline;

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
        description = source.getDescription().value;
        status = source.getTaskStatus();
//        deadline = source.getDealine().value;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {

        final Name name = new Name(this.name);
        final Description description = new Description(this.description);
        final Boolean status = new Boolean(this.status);
        
        return new Task(name, description, status);
    }
}
