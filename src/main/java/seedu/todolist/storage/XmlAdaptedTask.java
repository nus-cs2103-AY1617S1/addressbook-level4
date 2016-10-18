package seedu.todolist.storage;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.*;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private String startDate;
    
    @XmlElement
    private String startTime;
    
    @XmlElement
    private String endDate;
    
    @XmlElement
    private String endTime;
    
    @XmlElement
    private String location;
    
    @XmlElement
    private String remarks;

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
        
        startDate = null;
        if (source.getInterval().getStartDate() != null) {
            startDate = source.getInterval().getStartDate().toString();
        }
        
        startTime = null;
        if (source.getInterval().getStartTime() != null) {
            startTime = source.getInterval().getStartTime().toString();
        }
        
        endDate = source.getInterval().getEndDate().toString();
        
        endTime = null;
        if (source.getInterval().getEndTime() != null) {
            endTime = source.getInterval().getEndTime().toString();
        }
        
        location = source.getLocation().toString();
        remarks = source.getRemarks().toString();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final Interval interval = new Interval(this.startDate, this.startTime, this.endDate, this.endTime);
        final Location location = new Location(this.location);
        final Remarks remarks = new Remarks(this.remarks);
        return new Task(name, interval, location, remarks);
    }
}
