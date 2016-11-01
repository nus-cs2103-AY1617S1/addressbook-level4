package seedu.address.model.task;

import seedu.address.commons.util.DateUtil;

//@@author A0146123R
/**
 * Represents a Task's date
 * It can be deadline for tasks or event date for events.
 */
public interface Date {
    
    String getValue();
    
    String toString();
  
    int hashCode();
    
    default boolean isEmptyDate() {
        return DateUtil.isEmptyDate(getValue());
    }
}