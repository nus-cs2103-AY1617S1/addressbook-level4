package seedu.inbx0.model.task;

import java.util.Objects;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.commons.util.CollectionUtil;
import seedu.inbx0.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {
    
    public static final String MESSAGE_TIME_CONSTRAINTS = "The event is not possible as "
                                                           + "the end of the event is earlier or same as the start of event.";
    private Name name;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private Importance level;
    private boolean isEvent;
    
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     * @throws IllegalValueException if it is an event and not valid
     */
    public Task(Name name, Date startDate, Time startTime, Date endDate, Time endTime, Importance level, UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, startDate, startTime, endDate, endTime, level, tags);
        
        if(startDate.getDate() == "" && startTime.getTime() == "") {
            this.isEvent = false;
        }
        else
            this.isEvent = true;
        
        if(isEvent == true) {
            if(!isValidEvent(startDate, startTime, endDate, endTime)) {
                throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
            }
        }
        
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.level = level;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        
    }
    
    
    /**
     * Returns true if a given Date and Time allows it to be a valid event.
     */
    public static boolean isValidEvent(Date startDate, Time startTime, Date endDate, Time endTime) {
        boolean isValid = false;
        if(endTime.getTime() == "")
            return isValid;
        
        int numStartTime = Integer.parseInt(startTime.getTime().replaceAll("\\D+",""));
        int numEndTime = Integer.parseInt(endTime.getTime().replaceAll("\\D+",""));
        
        if(endDate.getYear() > startDate.getYear())
           isValid = true; 
        else if ((endDate.getYear() == startDate.getYear()) && (endDate.getMonth() > startDate.getMonth()))
            isValid = true;
        else if (((endDate.getYear() == startDate.getYear()) && 
                 (endDate.getMonth() == startDate.getMonth())) &&
                 (endDate.getDay() > startDate.getDay()))
            isValid = true;
        else if (((endDate.getYear() == startDate.getYear()) && 
                (endDate.getMonth() == startDate.getMonth())) &&
                (endDate.getDay() == startDate.getDay()) &&
                numEndTime > numStartTime)
                isValid = true;
           
        
        return isValid;         
    }
    
    /**
     * Copy constructor.
     * @throws IllegalValueException if it is an event and not valid
     */
    
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getLevel(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public Date getStartDate() {
        return startDate;
    }
    
    @Override
    public Time getStartTime() {
        return startTime;
    }
    
    @Override
    public Date getEndDate() {
        return endDate;
    }
    
    @Override
    public Time getEndTime() {
        return endTime;
    }
    
    @Override
    public Importance getLevel() {
        return level;
    }
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setLevel(Importance level) {
        this.level = level;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, startTime, endDate, endTime, level, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
