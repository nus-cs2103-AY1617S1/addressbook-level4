package seedu.address.model.task;

import java.time.LocalDate;
import java.util.ArrayList;

import seedu.address.commons.util.DateUtil;

//@@author A0146123R
/**
 * Represents a Task's date.
 * It can be deadline for tasks or event date for events.
 */
public interface Date {
    
    int hashCode();
    
    String getValue();
    
    String toString();
    
    ArrayList<LocalDate> getLocalDate();
    
    void updateDate(String... dateString);
    
    void updateRecurringDate(long numOfDays);
    
    default boolean isEmptyDate() {
        return DateUtil.isEmptyDate(getValue());
    }

}