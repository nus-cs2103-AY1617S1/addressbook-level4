package seedu.ggist.model.task;


import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.parser.DateTimeParser;

//@@author A0138411N    
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
    protected boolean isDone;
    protected boolean isOverdue;
    protected Date start;
    protected Date end;
    
    public static enum TaskType {
        FLOATING("task"), DEADLINE("deadline"), EVENT("event"); 
        
        private final String taskType;
        TaskType(String taskType) {
            this.taskType = taskType;
        }
        
        @Override
        public String toString() {
            return this.taskType;
        }
    }

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
        
        if (startDate.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) && startTime.value.equals(Messages.MESSAGE_NO_START_TIME_SET)) {
            constructStartDateTime(endDate, endTime);
        } else {
            constructStartDateTime(startDate, startTime);
        }
        constructEndDateTime(endDate, endTime);
        checkTimeOverdue();
        checkTimeClash(start,end);
    }
    

    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getPriority());
    }
          
    /**
     * Creates a Date object from the start date and start time
     * @throws IllegalValueException
     */
    public void constructStartDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        start = formatMissingDateTime(date, time);
    }
    
    /**
     * Creates a Date object from the end date and end time
     * @throws IllegalValueException
     */
    public void constructEndDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        end = formatMissingDateTime(date, time);
    }
    
    /**
     * Sets missing date and time to a large value
     * @param TaskDate
     * @param TaskTime
     * @return Date
     * @throws IllegalValueException 
     */
    public static Date formatMissingDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        if ((date.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || date.value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) 
             &&(time.value.equals(Messages.MESSAGE_NO_START_TIME_SET) || time.value.equals(Messages.MESSAGE_NO_END_TIME_SET))) {
            return new DateTimeParser("1st January 2050 11:59pm").getDateTime();
        } else if (date.value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)){
            return new DateTimeParser("1st January 2050 " + time.value).getDateTime();
        } else if (date.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)) {
            return new DateTimeParser("1st January 1990 " + time.value).getDateTime();
        } else if ((time.value.equals(Messages.MESSAGE_NO_START_TIME_SET) || time.value.equals(Messages.MESSAGE_NO_END_TIME_SET))) {
            return new DateTimeParser("11:59 pm " + date.value).getDateTime();
        } else {
            return new DateTimeParser(time.value + " " + date.value).getDateTime();
        }        
    }  
    
    /**
     * check if end time is before the current time. set task overdue if true
     * @throws IllegalValueException
     */
    public void checkTimeOverdue() throws IllegalValueException {

        Date currentDate  = new Date();
        if (end.before(currentDate) && isDone == false) {
            isOverdue = true;
        } else if (!end.before(currentDate)) {
            setNotOverdue();
        }
    }
    
    /**
     * check if end time is before start time
     * @throws IllegalValueException
     */
    public static void checkTimeClash(Date start, Date end) throws IllegalValueException {
        if(end.before(start)) {
            throw new IllegalValueException(Messages.MESSAGE_END_EARLIER_THAN_START);
        }
    }
    
    /**
     * compares task based on start date and time
     * @return dateTimeComparator
     */
    public static Comparator getTaskComparator(){
        return new Comparator<Task>(){
            public int compare (Task t1, Task t2){
                    
                if (t1.getStartDateTime().equals(t2.getStartDateTime())
                    && (t1.getEndDateTime().equals(t2.getEndDateTime()))) {
                    return t1.getTaskName().taskName.compareTo(t2.getTaskName().taskName);
                } 
                
                if (t1.getStartDateTime().before(t2.getStartDateTime())) {
                    return -1;
                } else if (t1.getStartDateTime().after(t2.getStartDateTime())) {
                    return 1;
                } 
                
                if (t1.getEndDateTime().before(t2.getEndDateTime())) {
                    return -1;
                } else if (t1.getEndDateTime().after(t2.getEndDateTime())) {
                    return 1;
                }
                
                return 0;
            }
        };
    }
    
    /**
     * Set task as done
     * Mark task as not overdue
     */
    public void setDone() {
        isDone = true;
        setNotOverdue();
    }
    /**
     * Set done task as undone
     * Mark it as not overdue
     */
    public void setContinue() {
    	isDone = false;
    	setNotOverdue();
    }
    
    public void setUndone() {
        isDone = false;
        if (end.before(new Date())) {
            isOverdue = true;
        }
    }
    
    /**
     * Mark task as not overdue
     */
    public void setNotOverdue() {
        isOverdue = false;
    }
    
   
    @Override
    public boolean isDone() {
        return isDone;
    }
    
 
    //@@author
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

    @Override
    public boolean isOverdue() {
        return isOverdue;
    }

}
