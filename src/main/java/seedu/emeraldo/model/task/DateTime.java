package seedu.emeraldo.model.task;


import seedu.emeraldo.commons.exceptions.IllegalValueException;
import java.time.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Task's date and time in Emeraldo.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {
    
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Task date and time must follow this format DD/MM/YYYY HH:MM in 24 hours format";
    public static final Pattern DATETIME_VALIDATION_REGEX =
            Pattern.compile("(?<preKeyword>((by )|(on )|(from )))"      //Preceeding keyword regex
                    + "(?<day>(0?[1-9]|[12][0-9]|3[01]))"               //Day regex
                    + "/(?<month>(0?[1-9]|[1][0-2]))/"                  //Month regex
                    + "(?<year>(([0-9][0-9])?[0-9][0-9]))"              //Year regex
                    + "( (?<hour>([01][0-9]|[2][0-3])))?"               //Hour regex
                    + "(:(?<minute>([0-5][0-9])))?"                     //Minute regex
                    + "( (?<aftKeyword>(to )))?"                           //2nd keyword regex
                    + "(?<dayEnd>(0?[1-9]|[12][0-9]|3[01]))?"               //Day regex
                    + "(/(?<monthEnd>(0?[1-9]|[1][0-2]))/)?"                  //Month regex
                    + "(?<yearEnd>(([0-9][0-9])?[0-9][0-9]))?"              //Year regex
                    + "( (?<hourEnd>([01][0-9]|[2][0-3])))?"               //Hour regex
                    + "(:(?<minuteEnd>([0-5][0-9])))?");                     //Minute regex
    
    public final String value;
    public final String valueFormatted;
    public final LocalDate valueDate;
    public final LocalTime valueTime;
    public final LocalDate valueDateEnd;
    public final LocalTime valueTimeEnd;

    /**
     * Validates given date and time.
     *
     * @throws IllegalValueException if given date and time string is invalid.
     */
    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        final Matcher matcher = DATETIME_VALIDATION_REGEX.matcher(dateTime);
        
        if (!dateTime.isEmpty() && !matcher.matches()) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        
        if(dateTime.isEmpty()){
            this.valueDate = null;
            this.valueTime = null;
            this.valueDateEnd = null;
            this.valueTimeEnd = null;
            this.value = "";
            this.valueFormatted = "Not specified";
        } else {
            final String preKeyword = matcher.group("preKeyword").trim();
            final String day = matcher.group("day");
            final String month = matcher.group("month");
            final String year = matcher.group("year");
            
            int yearParsed = Integer.parseInt(year);
            int monthParsed = Integer.parseInt(month);
            int dayParsed = Integer.parseInt(day);
            this.valueDate = LocalDate.of(yearParsed, monthParsed, dayParsed);
            this.value = dateTime;
            
            if(preKeyword.equals("on")){
                this.valueTime = null;
                this.valueFormatted = preKeyword + " " + day + " "
                                    + returnMonthInWords(monthParsed) + " " + year;
                this.valueDateEnd = null;
                this.valueTimeEnd = null;
            
            }else{
                final String hour = matcher.group("hour");
                final String minute = matcher.group("minute");
                
                int hourParsed = Integer.parseInt(hour);
                int minuteParsed = Integer.parseInt(minute);
                this.valueTime = LocalTime.of(hourParsed, minuteParsed);
                
                if(preKeyword.equals("by")){
                    this.valueFormatted = preKeyword + " " + day + " "
                                    + returnMonthInWords(monthParsed) +  " " 
                                    + year + ", " + hour + ":" + minute;
                    this.valueDateEnd = null;
                    this.valueTimeEnd = null;
                   
                }else{
                    final String aftKeyword = matcher.group("aftKeyword");
                    final String dayEnd = matcher.group("dayEnd");
                    final String monthEnd = matcher.group("monthEnd");
                    final String yearEnd = matcher.group("yearEnd");
                    final String hourEnd = matcher.group("hourEnd");
                    final String minuteEnd = matcher.group("minuteEnd");
                    
                    int yearEndParsed = Integer.parseInt(yearEnd);
                    int monthEndParsed = Integer.parseInt(monthEnd);
                    int dayEndParsed = Integer.parseInt(dayEnd);
                    this.valueDateEnd = LocalDate.of(yearEndParsed, monthEndParsed, dayEndParsed);
                    
                    int hourEndParsed = Integer.parseInt(hourEnd);
                    int minuteEndParsed = Integer.parseInt(minuteEnd);
                    this.valueTimeEnd = LocalTime.of(hourEndParsed, minuteEndParsed);
                    
                    this.valueFormatted = preKeyword + " " + day + " "
                                    + returnMonthInWords(monthParsed) +  " " 
                                    + year + ", " + hour + ":" + minute + " "
                                    + aftKeyword + dayEnd + " "
                                    + returnMonthInWords(monthEndParsed) +  " " 
                                    + yearEnd + ", " + hourEnd + ":" + minuteEnd + " ";
                }
            }
        }
    }
   
    @Override
    public String toString() {
        return valueFormatted;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    private String returnMonthInWords(int monthParsed){
        String monthInWords;
        
        switch(monthParsed){
            case 1:
                monthInWords = "Jan";
                break;
            case 2:
                monthInWords = "Feb";
                break;
            case 3:
                monthInWords = "Mar";
                break;
            case 4:
                monthInWords = "Apr";
                break;
            case 5:
                monthInWords = "May";
                break;
            case 6:
                monthInWords = "Jun";
                break;
            case 7:
                monthInWords = "Jul";
                break;
            case 8:
                monthInWords = "Aug";
                break;
            case 9:
                monthInWords = "Sep";
                break;
            case 10:
                monthInWords = "Oct";
                break;
            case 11:
                monthInWords = "Nov";
                break;
            case 12:
                monthInWords = "Dec";
                break;             
            default: monthInWords = "Invalid month";
        }
        
        return monthInWords;
    }
    
}
