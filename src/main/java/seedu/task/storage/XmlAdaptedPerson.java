package seedu.task.storage;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

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
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyTask source) {
        name = source.getName().fullName;
        description = source.getDescription().value;
        status = source.getTaskStatus();
//        deadline = source.getDealine().value;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {

        final Name name = new Name(this.name);
        final Description description = new Description(this.description);
        final Boolean status = new Boolean(this.status);
        
        return new Task(name, description, status);
    }
}
