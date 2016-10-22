package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's date in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class StartDate {
    
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Event date should be written in this format 'DD-MM-YYYY'";
    public static final String DATE_VALIDATION_REGEX = "^(\\d{2}-\\d{2}-\\d{4})$";
    
    public final String date;
    public int day_lastdigit;
    public String day;
    public String month;
    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date is invalid.
     */
    public StartDate(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        String [] dateArr = date.split("-");
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        } 
        
        //Check if day is first(st),second(nd) or third(rd)
        day_lastdigit = Integer.parseInt(dateArr[0])%10;        
        switch(day_lastdigit){
            case 1:  day = day_lastdigit + "st"; break;
            case 2:  day = day_lastdigit + "nd"; break;
            case 3:  day = day_lastdigit + "rd"; break;
            case 4:  day = day_lastdigit + "th"; break;
            case 5:  day = day_lastdigit + "th"; break;
            case 6:  day = day_lastdigit + "th"; break;
            case 7:  day = day_lastdigit + "th"; break;
            case 8:  day = day_lastdigit + "th"; break;
            case 9:  day = day_lastdigit + "th"; break;
            default: day = dateArr[0] + "th";
        }
        
        //Convert month number value to string value
        switch(dateArr[1]){
            case "01" : 
                month = " January "; break;
            case "02" : month = " Febuary "; break;
            case "03" : month = " March "; break;
            case "04" : month = " April "; break;
            case "05" : month = " May "; break;
            case "06" : month = " June "; break;
            case "07" : month = " July "; break;
            case "08" : month = " August "; break;
            case "09" : month = " September "; break;
            case "10" : month = " October "; break;
            case "11" : month = " November "; break;
            default: month = " December ";
        }
        
        //Concatenate day month and year
        date = day+month+dateArr[2];
        this.date = date;
    }
    
    /**
     * Returns if a given string is a valid event date.
     */
    public static boolean isValidDate(String date) {
        return date.matches(DATE_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return date;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.date.equals(((StartDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}