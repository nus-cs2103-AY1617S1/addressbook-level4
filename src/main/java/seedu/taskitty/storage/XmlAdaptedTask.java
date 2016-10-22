package seedu.taskitty.storage;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;
    @XmlElement
    private boolean isDone;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        TaskDate sourceStartDate = source.getPeriod().getStartDate();
        TaskDate sourceEndDate = source.getPeriod().getEndDate();
        TaskTime sourceStartTime = source.getPeriod().getStartTime();
        TaskTime sourceEndTime = source.getPeriod().getEndTime();
        
        if (sourceStartDate != null) {
            startDate = sourceStartDate.toString();
        }
        if (sourceEndDate != null) {
            endDate = sourceEndDate.toString();
        }
        if (sourceStartTime != null) {
            startTime = sourceStartTime.toString();
        }
        if (sourceEndTime != null) {
            endTime = sourceEndTime.toString();
        }
        isDone = source.getIsDone();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }
    
    public void markAsDone() {
    	if (!isDone) {
    		isDone = true;
    	}
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        
        TaskDate startDate = null;
        if (this.startDate != null) {
            startDate = new TaskDate(this.startDate);
        }
        
        TaskDate endDate = null;
        if (this.endDate != null) {
            endDate = new TaskDate(this.endDate);
        }
        
        TaskTime startTime = null;
        if (this.startTime != null) {
            startTime = new TaskTime(this.startTime);
        }
        
        TaskTime endTime = null;
        if (this.endTime != null) {
            endTime = new TaskTime(this.endTime);
        }
        final UniqueTagList tags = new UniqueTagList(taskTags);
        
        Task task = new Task(name, new TaskPeriod(startDate, startTime, endDate, endTime), tags);
        
       if (isDone) {
        	task.markAsDone();
       }
        
        return task;
    }
}
