package seedu.ggist.testutil;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.parser.DateTimeParser;

import java.util.Date;

import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskName;
    private TaskDate startDate;
    private TaskTime startTime;
    private TaskDate endDate;    
    private TaskTime endTime;
    private Priority priority;
    private Date start;
    private Date end;
    private boolean done;
    private boolean undo;
    private boolean overdue;

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }

    public void setEndDate(TaskDate endDate) {
        this.endDate = endDate;
    }
    
    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
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
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTaskName().taskName);
        if (this.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) {
        	//floating task, append nothing
        } else if (this.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)) {
        	// deadline task, append end date and end time
        	sb.append("," + this.getEndDate().getTestValue() + " ");
            sb.append(this.getEndTime().value);
        } else {
        	// event task, append everything
        	sb.append(" ,"+ this.getStartDate().getTestValue() + " ");
        	sb.append(this.getStartTime().value + ",");
        	sb.append(this.getEndDate().getTestValue() + " ");
        	sb.append(this.getEndTime().value);
        }
        if (!(this.getPriority() == null))
        	sb.append(" -" + this.getPriority().value);
        return sb.toString();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void setDone() {
        done = true;
        setNotOverdue();
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
    public boolean isOverdue() {
        return overdue;
    }

    @Override
    public void setNotOverdue() {
        overdue = false;
    }

    @Override
    public void setUndone() {
        done = false;
        
    }

    public void constructStartDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        if ((date.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || date.value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) && 
            (time.value.equals(Messages.MESSAGE_NO_START_TIME_SET) || time.value.equals(Messages.MESSAGE_NO_END_TIME_SET))) {
            Date date4 = new DateTimeParser("1st January 2050 11:59pm").getDateTime();
            start =  date4;
        } else if ((date.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || date.value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED))){
            Date date1 = new DateTimeParser("1st January 2050 " + time.value).getDateTime();
            start =  date1;
        } else if ((time.value.equals(Messages.MESSAGE_NO_START_TIME_SET) || time.value.equals(Messages.MESSAGE_NO_END_TIME_SET))) {
            Date date2 = new DateTimeParser("11:59 pm " + date.value).getDateTime();
            start =  date2;
        } else {
            Date date3 = new DateTimeParser(time.value + " " + date.value).getDateTime();
            start =  date3;
        }
    }
    
    public void constructEndDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        if ((date.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || date.value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) && 
            (time.value.equals(Messages.MESSAGE_NO_START_TIME_SET) || time.value.equals(Messages.MESSAGE_NO_END_TIME_SET))) {
            Date date4 = new DateTimeParser("1st January 2050 11:59pm").getDateTime();
            end =  date4;
        } else if ((date.value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) || date.value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED))){
            Date date1 = new DateTimeParser("1st January 2050 " + time.value).getDateTime();
            end =  date1;
        } else if ((time.value.equals(Messages.MESSAGE_NO_START_TIME_SET) || time.value.equals(Messages.MESSAGE_NO_END_TIME_SET))) {
            Date date2 = new DateTimeParser("11:59 pm " + date.value).getDateTime();
            end =  date2;
        } else {
            Date date3 = new DateTimeParser(time.value + " " + date.value).getDateTime();
            end =  date3;
        }
    }

    @Override
    public void checkTimeClash() throws IllegalValueException {
        Date currentDate  = new Date();
        if (end.before(currentDate) && done == false) {
            overdue = true;
        } else if (!end.before(currentDate)) {
            overdue = false;
        }
        if(end.before(start)) {
            throw new IllegalValueException("End cannot be earlier than start!");
        }      
    }


}
