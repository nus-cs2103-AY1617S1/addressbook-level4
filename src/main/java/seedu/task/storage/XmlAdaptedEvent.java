package seedu.task.storage;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {
	
	@XmlElement(required = true)
	private String name;
	
	@XmlElement
	private String description;
	
	@XmlElement
	private String duration;


    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        name = source.getEvent().fullName;
        description = source.getDescription().value;
        duration = source.getDuration().toString();
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {

        final Name name = new Name(this.name);
        final Description description = new Description(this.description);
        final EventDuration eventDuration = new EventDuration(this.duration);
        
        return new Event(name, description, eventDuration);
    }
}
