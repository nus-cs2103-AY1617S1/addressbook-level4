package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    
    @XmlElement
    private long deadline = -1;
    @XmlElement
    private boolean isCompleted = false;
    @XmlElement
    private long startTime = -1;
    @XmlElement
    private long endTime = -1;
    @XmlElement
    private String deadlinePattern = "NONE";
    @XmlElement
    private int deadlineFrequency = 0;
    @XmlElement
    private String periodPattern = "NONE";
    @XmlElement
    private int periodFrequency = 0;

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
        name = source.getName().name;
        
        isCompleted = source.getComplete().isCompleted;
        
        Date tempDeadline = source.getDeadline().deadline;
        if (tempDeadline != null) {
        	deadline = tempDeadline.getTime();
        }
        
        Period period = source.getPeriod();
        Date tempStartTime = period.startTime;
        if (tempStartTime != null) {
        	startTime = tempStartTime.getTime();
        }
        Date tempEndTime = period.endTime;
        if (tempEndTime != null) {
        	endTime = period.endTime.getTime();
        }
        
        Recurrence deadlineRecurrence = source.getDeadlineRecurrence(); 
        deadlinePattern = deadlineRecurrence.pattern.name();
        deadlineFrequency = deadlineRecurrence.frequency;
        
        Recurrence periodRecurrence = source.getPeriodRecurrence();
        periodPattern = periodRecurrence.pattern.name();
        periodFrequency = periodRecurrence.frequency;
        
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        
        final Complete complete = new Complete(this.isCompleted);
        final Deadline deadline = (this.deadline == -1) ? new Deadline() : 
        												  new Deadline(new Date(this.deadline));
        final Period period = (this.startTime == -1 ||
        					   this.endTime == -1) ? new Period() : 
        						   					 new Period(new Date(this.startTime),
        						   							 	new Date(this.endTime));
        final Recurrence deadlineRecurrence = this.deadlinePattern.equals("NONE") ? 
        		new Recurrence() : new Recurrence(Recurrence.Pattern.valueOf(this.deadlinePattern), this.deadlineFrequency);
        final Recurrence periodRecurrence = this.periodPattern.equals("NONE") ? 
        		new Recurrence() : new Recurrence(Recurrence.Pattern.valueOf(this.periodPattern), this.periodFrequency);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, complete, deadline, period, deadlineRecurrence, periodRecurrence, tags);
    }
}
