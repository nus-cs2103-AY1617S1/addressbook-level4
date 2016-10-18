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
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    
    @XmlElement
    private String status;
    
    @XmlElement
    private String taskType;
    
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
        if(source.getTaskDate() != null) {
        	taskDate = source.getTaskDate().getDate();
        }
        status = source.getStatus();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        taskType = source.getTaskType();
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
        
        return (this.taskDate == null) ? new Task(name, tags, this.status) : new Task(name, new TaskDate(this.taskDate), tags,this.status);
    }
}
