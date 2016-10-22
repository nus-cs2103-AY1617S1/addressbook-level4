package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * Represents a Event's end date in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class EndDate {
    
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Event date should be written in this format 'DD-MM-YYYY' and date must be current or later date\n"
            + "Input the correct Day,Month and Year";
    public static final String DATE_VALIDATION_REGEX = "^(\\d{2}-\\d{2}-\\d{4})$";
    
    public final String endDate;
    public final String saveEndDate;
    public int day_lastdigit;
    public String day;
    public String month;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date is invalid.
     */
    public EndDate(String endDate) throws IllegalValueException {
    	saveEndDate = endDate.trim();
        endDate = endDate.trim();
        String [] dateArr = endDate.split("-");
        if (!endDate.equals("No End Date") && !isValidDate(endDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if(!endDate.equals("No End Date")){
            //Check if day is first(st),second(nd) or third(rd)
            day_lastdigit = Integer.parseInt(dateArr[0])%10;        
            switch(day_lastdigit){
                case 1:  day = dateArr[0] + "st"; break;
                case 2:  day = dateArr[0] + "nd"; break;
                case 3:  day = dateArr[0] + "rd"; break;
                default: day = dateArr[0] + "th";
            }
            
            //Convert month number value to string value
            switch(dateArr[1]){
                case "01" : month = " January "; break;
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
     * Returns if a given string is a valid event end date.
     */
    public static boolean isValidDate(String date) {
        boolean checkDay = true, checkMonth = true, checkYear = true;
        
        //Date object
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date dateobj = new Date();
        String [] dateArr = date.split("-");
        String [] curDate = df.format(dateobj).split("-");
        Calendar calendar = Calendar.getInstance();
        int daysOfcurrMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Check if input year is less than current year
        if(Integer.parseInt(dateArr[2]) < Integer.parseInt(curDate[2])){
            checkYear = false;
        }
        //Check if input month is less than current month or more than 12 months
        else if(Integer.parseInt(dateArr[1]) < Integer.parseInt(curDate[1]) || Integer.parseInt(dateArr[1]) > 12){
            checkMonth = false; 
        }
        //Check if input day is more than current day or more than days in current month
        else if(Integer.parseInt(dateArr[0]) < Integer.parseInt(curDate[0]) || Integer.parseInt(dateArr[0]) > daysOfcurrMonth){
            checkDay = false;
        } 
      
        //Check if date is valid
        if(date.matches(DATE_VALIDATION_REGEX) && checkDay && checkMonth && checkYear){
            return true;
        }else{
            return false;
        }
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
