package seedu.taskmaster.model.task.stub;

import java.util.Date;

import seedu.taskmaster.model.task.TaskDate;

//@@author A0135782Y
public class TaskDateStub extends TaskDate {
    private static final int DUMMY_TIME = 0;

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
        return new Date(DUMMY_TIME);
    }
    
    @Override
    public boolean equals(Object other){
        return true;
    }

    @Override
    public boolean isPresent() {
        return true;
    }
    
    @Override
    public String toString() {
        return "";
    }   
}