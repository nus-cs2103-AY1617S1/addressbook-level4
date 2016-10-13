package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private String deadline = "00/00/0000 00:00:00";
    @XmlElement
    private boolean isCompleted = false;
    @XmlElement
    private String startTime = "00/00/0000 00:00:00";
    @XmlElement
    private String endTime = "00/00/0000 00:00:00";
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
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

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
        name = source.getName().getName();
        
        isCompleted = source.getComplete().isCompleted();
        
        Date tempDeadline = source.getDeadline().getDeadline();
        if (tempDeadline != null) {
        	deadline = sdf.format(new Date(tempDeadline.getTime()));
        }
        
        Period period = source.getPeriod();
        Date tempStartTime = period.getStartTime();
        if (tempStartTime != null) {
        	startTime = sdf.format(new Date(tempStartTime.getTime()));
        }
        Date tempEndTime = period.getEndTime();
        if (tempEndTime != null) {
        	endTime = sdf.format(new Date(period.getEndTime().getTime()));
        }
        
        Recurrence deadlineRecurrence = source.getDeadlineRecurrence(); 
        deadlinePattern = deadlineRecurrence.getPattern().name();
        deadlineFrequency = deadlineRecurrence.getFrequency();
        
        Recurrence periodRecurrence = source.getPeriodRecurrence();
        periodPattern = periodRecurrence.getPattern().name();
        periodFrequency = periodRecurrence.getFrequency();
        
        tagged = new ArrayList<>();
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
        
        final Complete complete = new Complete(this.isCompleted);
        final Deadline deadline = (this.deadline.equals("00/00/0000 00:00:00")) ? new Deadline() : 
            new Deadline(sdf.parse(this.deadline));
        final Period period = (this.startTime.equals("00/00/0000 00:00:00") || 
                this.endTime.equals("00/00/0000 00:00:00")) ? new Period() : 
        		    new Period(sdf.parse(this.startTime), sdf.parse(this.endTime));
        final Recurrence deadlineRecurrence = this.deadlinePattern.equals("NONE") ? 
                new Recurrence() : new Recurrence(Recurrence.Pattern.valueOf(this.deadlinePattern), 
                        this.deadlineFrequency);
        final Recurrence periodRecurrence = this.periodPattern.equals("NONE") ? 
                new Recurrence() : new Recurrence(Recurrence.Pattern.valueOf(this.periodPattern), 
                        this.periodFrequency);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, complete, deadline, period, deadlineRecurrence, periodRecurrence, tags);
    }
}
