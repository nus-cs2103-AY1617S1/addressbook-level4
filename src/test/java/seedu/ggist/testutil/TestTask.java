package seedu.ggist.testutil;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.parser.DateTimeParser;

import java.util.Comparator;
import java.util.Date;

import seedu.ggist.model.task.*;

/**
 * A mutable person object. For testing only.
 */
//@@author A0147994J
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
    
    
    //@@author A0138411N
    public String getAddCommand() {
        
        String[] startDate = null;
        String[] endDate = null;
        if (!this.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)) {
             startDate = this.getStartDate().value.split(",");
        }
        if (!this.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) {
            endDate = this.getEndDate().value.split(",");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTaskName().taskName);
        //floating task, append nothing
        if (this.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED) 
            && this.getEndTime().value.equals(Messages.MESSAGE_NO_END_TIME_SET)
            && this.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
            && this.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)) {
            
            // deadline task with no time, append end date only
        }  else if (this.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
                    && this.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)
                    && this.getEndTime().value.equals(Messages.MESSAGE_NO_END_TIME_SET)) {
                    sb.append(", " + endDate[1].trim().substring(0,endDate[1].trim().length()-2));  
                    
        // deadline task, append end date and end time
        } else if (this.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
                    && this.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)) {
        	        sb.append("," + this.getEndTime().value + " ");
        	        sb.append(endDate[1].trim());
        	        
          //event task with no date, append time only
        }  else if (this.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)
                   && this.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)) {
                   sb.append(", " + this.getStartTime().value);
                   sb.append(", " + this.getEndTime().value);
                   
        //event task with no time, append date only
        }  else if (this.getEndTime().value.equals(Messages.MESSAGE_NO_END_TIME_SET)
                   && this.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)) {
                sb.append(", " + startDate[1].trim().substring(0,endDate[1].trim().length()-2));
                sb.append(", " + endDate[1].trim().substring(0,endDate[1].trim().length()-2));
                
        //event task with no start time, append start date only
        }  else if (this.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)) {
                sb.append(", " + startDate[1].trim().substring(0,endDate[1].trim().length()-2)); 
                sb.append(this.getEndTime().value + " ");
                sb.append( endDate[1].trim());
                
        //event task with no end time, append end date only
        }  else if (this.getEndTime().value.equals(Messages.MESSAGE_NO_END_TIME_SET)) {
                sb.append(" ,"+ this.getStartTime().value+ " ");
                sb.append(startDate[1].trim().substring(0,endDate[1].trim().length()-2));
                sb.append(", " + endDate[1].trim().substring(0,endDate[1].trim().length()-2));
                
        //event task with no start date, append start time only
        }  else if (this.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)) {
                sb.append(", " + this.getStartTime().value);
                sb.append( ", " + this.getEndTime().value);
                sb.append( " " + endDate[1].trim());
                
        //event task with no end date, append end time only
        }  else if (this.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) {
                sb.append(", " + this.getStartTime().value);
                sb.append( " " + startDate[1].trim());
                sb.append( ", " + this.getEndTime().value);
        } else {
            
        	// event task, append everything
        	sb.append(" ,"+ this.getStartTime().value+ " ");
        	sb.append(startDate[1].trim() + ",");
        	sb.append(this.getEndTime().value + " ");
        	sb.append( endDate[1].trim());
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
    public void setContinue() {
    	done = false;
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

    @Override
    public void constructStartDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void constructEndDateTime(TaskDate date, TaskTime time) throws IllegalValueException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void checkTimeOverdue() throws IllegalValueException {
        // TODO Auto-generated method stub
        
    }

}
