package seedu.unburden.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */

//@@Gauri Joshi A0143095H
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String taskD;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String done;

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
        name = source.getName().fullName;
        taskD = source.getTaskDescription().fullTaskDescriptions;
        date = source.getDate().fullDate;
        startTime = source.getStartTime().fullTime;
        endTime = source.getEndTime().fullTime;
        done = source.getDoneString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
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
        final Name name = new Name(this.name);
        final TaskDescription taskD = new TaskDescription(this.taskD);
        final Date date = new Date(this.date);
        final Time startTime = new Time(this.startTime);
        final Time endTime = new Time(this.endTime);
        final String done = new String(this.done);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Task(name,taskD,date,startTime,endTime,tags);
    }
}
