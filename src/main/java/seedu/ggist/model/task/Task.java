package seedu.ggist.model.task;


import java.util.Date;
import java.util.Objects;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.parser.DateTimeParser;


/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask{

    protected TaskName taskName;
    protected TaskDate startDate;
    protected TaskTime startTime;
    protected TaskDate endDate;
    protected TaskTime endTime;
    protected Priority priority;
    protected boolean done;
    
    protected Date start;
    protected Date end;

    /**
     * Every field must be present and not null.
     * @throws IllegalValueException 
     * 
    */     
    
    public Task(TaskName taskName, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime, Priority priority) throws IllegalValueException {
        this.taskName = taskName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.priority = priority;
        this.done = false;
        if (startDate.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || startTime.value.equals(Messages.MESSAGE_NO_START_TIME_SET)) {
            start = constructDateTime(endDate, endTime);
        } else {
            start = constructDateTime(startDate, startTime);
        }
        end = constructDateTime(endDate, endTime);
        
        Date currentDate  = new Date();
        if (end.before(currentDate)) {
            throw new IllegalValueException("You have enetered a past date or time!");
        }
        
    }
    

    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getPriority());
    }
    public void setDone() {
        done = true;
    }
    
    public void setUnDone() {
        done = false;
    }
      
    public Date constructDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        if ((date.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || date.value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) && 
            (time.value.equals(Messages.MESSAGE_NO_START_TIME_SET) || time.value.equals(Messages.MESSAGE_NO_END_TIME_SET))) {
            Date date4 = new DateTimeParser("1st January 2050 11:59pm").getDateTime();
            return date4;
        } else if ((date.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || date.value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED))){
            Date date1 = new DateTimeParser("1st January 2050 " + time.value).getDateTime();
            return date1;
        } else if ((time.value.equals(Messages.MESSAGE_NO_START_TIME_SET) || time.value.equals(Messages.MESSAGE_NO_END_TIME_SET))) {
            Date date2 = new DateTimeParser("11:59 pm " + date.value).getDateTime();
            return date2;
        } else {
            Date date3 = new DateTimeParser(time.value + " " + date.value).getDateTime();
            return date3;
        }
    }

    
    @Override
    public boolean getDone() {
        return done;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instance of handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
 
    @Override
    public TaskName getTaskName() {
        return taskName;
    }

    @Override
    public TaskDate getStartDate() {
        return startDate;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }
    
    @Override
    public TaskDate getEndDate() {
        return endDate;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }
    
    @Override
    public Priority getPriority() {
        return priority;
    }
    
    @Override
    public Date getStartDateTime() {
        return start;
    }
    
    @Override
    public Date getEndDateTime() {
        return end;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName,startDate, startTime, endDate, endTime, priority);
    }


}
