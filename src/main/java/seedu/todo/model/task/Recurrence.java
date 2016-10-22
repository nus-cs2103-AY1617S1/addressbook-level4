package seedu.todo.model.task;

import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.util.regex.Matcher;

import seedu.todo.MainApp;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;


public class Recurrence {

    private String desc;
    
    public Recurrence(String desc) throws IllegalValueException {
        if (!isValidRecurrenceDesc(desc)) {
            throw new IllegalValueException("Invalid Recurrence description");
        }
        this.desc = desc;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public boolean isNull() {
        return this.desc == null;
    }
    
    public boolean isValidRecurrenceDesc(String desc) {
       return true;
    }
    
    
    
    @Override
    public String toString() {
        return this.desc;
    }
    
}
