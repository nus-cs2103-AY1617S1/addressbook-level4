package seedu.jimi.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * JAXB-friendly version of the FloatingTask.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String isCompleted;
    
    @XmlElement(required = true)
    private String taskDeadline;
    @XmlElement(required = true)
    private String eventStartDatetime;
    @XmlElement(required = true)
    private String eventEndDatetime;
    
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given FloatingTask into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        isCompleted = Boolean.toString(source.isCompleted());
        
        if (source instanceof DeadlineTask) {
            taskDeadline = ((DeadlineTask) source).getDeadline().toString();
        } else if (source instanceof Event) {
            eventStartDatetime = ((Event) source).getStart().toString();
            eventEndDatetime = ((Event) source).getEnd().toString();
        } else {
            /* Set all null since floating task */
            taskDeadline = eventStartDatetime = eventEndDatetime = null;
        }
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's FloatingTask object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public ReadOnlyTask toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        
        final Name name = new Name(this.name);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final boolean isCompletedBoolean = Boolean.parseBoolean(isCompleted);
        ReadOnlyTask toConvert;
        
        if (taskDeadline == null && eventStartDatetime == null) { // floating
            toConvert = new FloatingTask(name, tags);
            ((FloatingTask) toConvert).setCompleted(isCompletedBoolean);
        } else if (taskDeadline != null) { // deadline task
            toConvert = new DeadlineTask(name, new DateTime(taskDeadline), tags);
            ((DeadlineTask) toConvert).setCompleted(isCompletedBoolean);
        } else {
            toConvert = new Event(
                    name, 
                    new DateTime(eventStartDatetime), 
                    new DateTime(eventEndDatetime), 
                    tags);
            ((Event) toConvert).setCompleted(isCompletedBoolean);
        }
        
        return toConvert;
    }
}
