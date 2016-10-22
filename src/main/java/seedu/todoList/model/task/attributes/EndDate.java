package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's end date in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class EndDate {
    
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Event end date should be written in this format 'DD-MM-YYYY'";
    public static final String DATE_VALIDATION_REGEX = "^(\\d{2}-\\d{2}-\\d{4})$";
    
    public final String endDate;
    public int day_lastdigit;
    public String day;
    public String month;
    
    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date is invalid.
     */
    public EndDate(String endDate) throws IllegalValueException {
        endDate = endDate.trim();
        String [] dateArr = endDate.split("-");
        if (!endDate.equals("No End Date") && !isValidDate(endDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        
        if(!endDate.equals("No End Date")){
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
            endDate = day+month+dateArr[2];
        }
        this.endDate = endDate;
    }
    
    /**
     * Returns if a given string is a valid event date.
     */
    public static boolean isValidDate(String endDate) {
        return endDate.matches(DATE_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return endDate;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                && this.endDate.equals(((EndDate) other).endDate)); // state check
    }

    @Override
    public int hashCode() {
        return endDate.hashCode();
    }
}
