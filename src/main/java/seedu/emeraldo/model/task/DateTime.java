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
    
    private static final String OPTIONAL_TIME_REGEX = "( (?<hour>([01][0-9]|[2][0-3])))?"
                        + "(:(?<minute>([0-5][0-9])))?";

    private static final String OPTIONAL_END_DATE_TIME_REGEX = "( (?<aftKeyword>(to )))?"
                        + "(?<dayEnd>(0?[1-9]|[12][0-9]|3[01]))?"
                        + "(/(?<monthEnd>(0?[1-9]|[1][0-2]))/)?"
                        + "(?<yearEnd>(([0-9][0-9])?[0-9][0-9]))?"
                        + "( (?<hourEnd>([01][0-9]|[2][0-3])))?"
                        + "(:(?<minuteEnd>([0-5][0-9])))?";

    private static final String END_DATE_TIME_REGEX = "( (?<aftKeyword>(to )))"
                        + "(?<dayEnd>(0?[1-9]|[12][0-9]|3[01]))"
                        + "(/(?<monthEnd>(0?[1-9]|[1][0-2]))/)"
                        + "(?<yearEnd>(([0-9][0-9])?[0-9][0-9]))"
                        + "( (?<hourEnd>([01][0-9]|[2][0-3])))"
                        + "(:(?<minuteEnd>([0-5][0-9])))";

    private static final String TIME_VALIDATION_REGEX = "( (?<hour>([01][0-9]|[2][0-3])))"
                    + "(:(?<minute>([0-5][0-9])))";

    private static final String DATE_VALIDATION_REGEX = "(?<day>(0?[1-9]|[12][0-9]|3[01]))"
                    + "/(?<month>(0?[1-9]|[1][0-2]))/"
                    + "(?<year>(([0-9][0-9])?[0-9][0-9]))";

    private static final String MESSAGE_KEYWORD_FROM_CONSTRAINTS = "Invalid format! It should be "
                    + "'from DD/MM/YYYY HH:MM to DD/MM/YYYY HH:MM'";

    private static final String MESSAGE_KEYWORD_BY_CONSTRAINTS = "Invalid format! It should be "
                    + "'by DD/MM/YYYY HH:MM'";

    private static final String MESSAGE_KEYWORD_ON_CONSTRAINTS = "Invalid format! It should be "
                    + "'on DD/MM/YYYY'";

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Date must follow this format DD/MM/YYYY"
                    + "and time must follow this format HH:MM in 24 hours format";
    
    public static final String ON_KEYWORD_VALIDATION_REGEX = "on " + DATE_VALIDATION_REGEX;
    
    public static final String BY_KEYWORD_VALIDATION_REGEX = "by " + DATE_VALIDATION_REGEX
                    + TIME_VALIDATION_REGEX;
    
    public static final String FROM_KEYWORD_VALIDATION_REGEX = "from " + DATE_VALIDATION_REGEX
                    + TIME_VALIDATION_REGEX
                    + END_DATE_TIME_REGEX;
    
    public static final Pattern DATETIME_VALIDATION_REGEX =
            Pattern.compile("(?<preKeyword>((by )|(on )|(from )))"      //Preceding keyword regex
                    + DATE_VALIDATION_REGEX
                    + OPTIONAL_TIME_REGEX
                    + OPTIONAL_END_DATE_TIME_REGEX);
    
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

            if(preKeyword.equals("on")){
                if(!isValidFormatFor_GivenKeyword(dateTime, preKeyword))
                    throw new IllegalValueException(MESSAGE_KEYWORD_ON_CONSTRAINTS);
                
                this.valueDate = valueDateFormatter(matcher, preKeyword);
                this.valueFormatted = valueFormatter(matcher, preKeyword);
                
                this.valueTime = null;
                this.valueDateEnd = null;
                this.valueTimeEnd = null;
                
            }else if(preKeyword.equals("by")){
                if(!isValidFormatFor_GivenKeyword(dateTime, preKeyword))
                    throw new IllegalValueException(MESSAGE_KEYWORD_BY_CONSTRAINTS);
                
                this.valueDate = valueDateFormatter(matcher, preKeyword);                
                this.valueTime = valueTimeFormatter(matcher, preKeyword);                
                this.valueFormatted = valueFormatter(matcher, preKeyword);
                
                this.valueDateEnd = null;
                this.valueTimeEnd = null;
                
            }else{
                if(!isValidFormatFor_GivenKeyword(dateTime, preKeyword))
                    throw new IllegalValueException(MESSAGE_KEYWORD_FROM_CONSTRAINTS);
                
                final String aftKeyword = matcher.group("aftKeyword").trim();
                
                this.valueDate = valueDateFormatter(matcher, preKeyword);                
                this.valueTime = valueTimeFormatter(matcher, preKeyword);
                
                this.valueDateEnd = valueDateFormatter(matcher, aftKeyword);
                this.valueTimeEnd = valueTimeFormatter(matcher, aftKeyword);
                
                this.valueFormatted = valueFormatter(matcher, preKeyword) + " "
                                    + valueFormatter(matcher, aftKeyword);                     
            }
            this.value = dateTime;
        }
    }

    private boolean isValidFormatFor_GivenKeyword(String dateTime, String keyword){
        switch(keyword){
            case "on":
                return dateTime.matches(ON_KEYWORD_VALIDATION_REGEX);
            case "by": 
                return dateTime.matches(BY_KEYWORD_VALIDATION_REGEX);
            case "from":
                return dateTime.matches(FROM_KEYWORD_VALIDATION_REGEX);
            default:
                return false;
        }
    }
    
    private LocalDate valueDateFormatter(Matcher matcher, String keyword){
        
        String day = matcher.group("day");
        String month = matcher.group("month");
        String year = matcher.group("year");
        
        if(keyword.equals("to")){
            day = matcher.group("dayEnd");
            month = matcher.group("monthEnd");
            year = matcher.group("yearEnd");
        }
        
        int yearParsed = Integer.parseInt(year);
        int monthParsed = Integer.parseInt(month);
        int dayParsed = Integer.parseInt(day);
            
        return LocalDate.of(yearParsed, monthParsed, dayParsed);
    }
    
    private LocalTime valueTimeFormatter(Matcher matcher, String keyword){
        
        String hour = matcher.group("hour");
        String minute = matcher.group("minute");
        
        if(keyword.equals("to")){
            hour = matcher.group("hourEnd");
            minute = matcher.group("minuteEnd");
        }
        
        int hourParsed = Integer.parseInt(hour);
        int minuteParsed = Integer.parseInt(minute);
        
        return LocalTime.of(hourParsed, minuteParsed);
    }
    
    private String valueFormatter(Matcher matcher, String keyword){
        
        String day = matcher.group("day");
        String month = matcher.group("month");
        String year = matcher.group("year");
        String hour = matcher.group("hour");
        String minute = matcher.group("minute");
        
        if(keyword.equals("to")){
            day = matcher.group("dayEnd");
            month = matcher.group("monthEnd");
            year = matcher.group("yearEnd");
            hour = matcher.group("hourEnd");
            minute = matcher.group("minuteEnd");
        }
        
        int monthParsed = Integer.parseInt(month);
        
        if(keyword.equals("on"))
            return keyword + " " + day + " " + returnMonthInWords(monthParsed) + " " + year;
        else{
            return keyword + " " + day + " " + returnMonthInWords(monthParsed) +  " " 
                    + year + ", " + hour + ":" + minute;
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
