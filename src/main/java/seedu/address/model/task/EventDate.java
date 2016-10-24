package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's dates
 * Guarantees: is valid as declared in {@link #isValidDate(String)}
 */
public class EventDate implements Date{
    
    public static final String MESSAGE_EVENT_DATE_CONSTRAINTS = "Event date should follow DD.MM.YYYY[-Time(in 24 hrs)]";
    
    private String date;
    
    private String startDate;
    private String endDate;
    
    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given dates string is invalid.
     */
    public EventDate(String startDate, String endDate) throws IllegalValueException {
        assert startDate != null && endDate != null;
        startDate = startDate.trim();
        endDate = endDate.trim();
        if (!isValidDate(startDate) || !isValidDate(endDate)) {
            throw new IllegalValueException(MESSAGE_EVENT_DATE_CONSTRAINTS);
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.date = startDate + " to " + endDate;
    }
    
    /**
     * Returns true if a given string is a valid event date.
     */
    private static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }
    
    @Override
    public String getValue() {
        return date;
    }
    
    @Override
    public String toString() {
        return date;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDate // instanceof handles nulls
                && this.startDate.equals(((EventDate) other).startDate) // state check
                && this.endDate.equals(((EventDate) other).endDate)); 
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void updateDate(String start,String end){
        this.startDate=start;
        this.endDate=end;
        this.date=start+" to "+end;
    }
    
}
