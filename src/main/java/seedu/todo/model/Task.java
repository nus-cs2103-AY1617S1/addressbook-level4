package seedu.todo.model;

import java.util.Date;

import seedu.todo.commons.exceptions.RecordInvalidException;

public class Task extends CalendarRecord {
    public static ActiveRecordLibrary<Task> library = new ActiveRecordLibrary<Task>();
    
    public boolean completed = false;
    
    Task() {
        library.add(this);
    }

    @Override
    public boolean validate() {
        return true;
    }
}
