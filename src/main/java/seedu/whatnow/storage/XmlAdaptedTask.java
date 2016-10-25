package seedu.whatnow.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    
    @XmlElement(required = true)
    private String taskDate;
    
    @XmlElement
    private String startDate;
    
    @XmlElement
    private String endDate;
    
    @XmlElement
    private String taskTime;
    
    @XmlElement
    private String startTime;
    
    @XmlElement
    private String endTime;
    
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    
    @XmlElement
    private String status;
    
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
        tagged = new ArrayList<>();
        if(source.getDate() != null)
        	taskDate = source.getDate();
        if (source.getStartDate() != null)
            startDate = source.getStartDate();
        if (source.getEndDate() != null)
            endDate = source.getEndDate();
        if (source.getTime() != null)
            taskTime = source.getTime();
        if (source.getStartTime() != null)
            startTime = source.getStartTime();
        if (source.getEndTime() != null)
            endTime = source.getEndTime();
        status = source.getStatus();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     * @throws ParseException 
     */
    public Task toModelType() throws IllegalValueException, ParseException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, taskDate, startDate, endDate, taskTime, startTime, endTime, tags, status, null);
    }
}
