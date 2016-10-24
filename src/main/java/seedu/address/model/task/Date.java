package seedu.address.model.task;



/**
 * Represents a Task's date
 * It can be deadline for tasks or event date for events.
 */
public interface Date{
    
    public static final String DATE_VALIDATION_REGEX = "^[0-3]?[0-9].[0-1]?[0-9].([0-9]{4})(-[0-2]?[0-9]?)?";
    // EXAMPLE = "15.10.2016-14"

    String getValue();
    
    String toString();
  

    int hashCode();
    
    default boolean isEmptyDate() {
        return getValue().equals("");
    }

}