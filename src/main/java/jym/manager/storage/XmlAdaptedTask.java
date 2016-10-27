package jym.manager.storage;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.model.tag.Tag;
import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.*;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = false)
    private String deadline;
    @XmlElement(required = false)
    private String endTime;
    @XmlElement(required = false)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

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
        description = source.getDescription().toString();
        deadline = source.getDate().toString();
        endTime = (source.getEndTime() != null)? source.getEndTime().toString() : null;
        address = source.getLocation().toString();
//        tagged = new ArrayList<>();
//        for (Tag tag : source.getTags()) {
//            tagged.add(new XmlAdaptedTag(tag));
//        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Description desc = new Description(this.description);
        final Deadline dline = new Deadline(this.deadline);
        final Location addr = new Location(this.address);
        final UniqueTagList tags = new UniqueTagList(personTags);
        if(endTime != null){
        	final Deadline etime = new Deadline(this.endTime);
        	List<Deadline> l = new ArrayList();
        	l.add(dline);
        	l.add(etime);
        	return new Task(desc, l, addr, tags);
        }
        return new Task(desc, dline, addr, tags);
    }
}
