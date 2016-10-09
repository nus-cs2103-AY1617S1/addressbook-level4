package seedu.todo.model;

import java.util.*;

import seedu.todo.commons.exceptions.RecordInvalidException;

public class CalendarRecord implements ActiveRecordBase {
    public static ActiveRecordLibrary<CalendarRecord> library = new ActiveRecordLibrary<CalendarRecord>();
    
    public Date calendarDate;
    public String displayLabel;
    
    CalendarRecord() {
        library.add(this);
    }

    @Override
    public boolean validate() {
        return true;
    }
}
