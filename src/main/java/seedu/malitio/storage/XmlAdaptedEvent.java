package seedu.malitio.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlyEvent;

public class XmlAdaptedEvent {
    
    @XmlElement(required = true)
    private String name;


    @XmlElement(required = true)
    private String start;
    
    @XmlElement(required = true)
    private String end;
    
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Schedule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        name = source.getName().fullName;
        start = source.getStart().toString();
        end = source.getEnd().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final DateTime start = new DateTime(this.start);
        final DateTime end = new DateTime(this.end);
        final UniqueTagList tags = new UniqueTagList(taskTags);
            return new Event(name, start, end, tags);
    }
}
