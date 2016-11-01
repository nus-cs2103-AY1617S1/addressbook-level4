package seedu.address.model.task.stub;

import java.util.Date;

import seedu.address.model.task.TaskDate;

public class TaskDateStub extends TaskDate {
    public String getFormattedDate() {
        return "";
    }
    
    //For sake of testing
    @Override
    public String getInputDate() {
        return "";
    }
    
    @Override
    public long getDateInLong() {
        return TaskDate.DATE_NOT_PRESENT;
    } 
    
    /**
     * Parses the date in Long and provides it in the Date class format
     */
    @Override
    public Date getDate() {
        return new Date(0);
    }
    
    @Override
    public boolean equals(Object other){
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }
    
    @Override
    public String toString() {
        return "";
    }   
}