package seedu.emeraldo.model.task;


import seedu.emeraldo.commons.exceptions.IllegalValueException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;

/**
 * Represents a Task's date and time in Emeraldo.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {
    
    private static final String MESSAGE_KEYWORD_FROM_CONSTRAINTS = "Invalid format! It should be "
            + "'from DD/MM/YYYY HH:MM to DD/MM/YYYY HH:MM'";

    private static final String MESSAGE_KEYWORD_BY_CONSTRAINTS = "Invalid format! It should be "
            + "'by DD/MM/YYYY HH:MM'";

    private static final String MESSAGE_KEYWORD_ON_CONSTRAINTS = "Invalid format! It should be "
            + "'on DD/MM/YYYY'";

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Date must follow this format DD/MM/YYYY "
            + "and time must follow this format HH:MM in 24 hours format";
    
    public final String value;
    public final String context;
    public final String overdueContext;
    public final String eventContext;
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
        final Matcher matcher = DateTimeParser.DATETIME_VALIDATION_REGEX.matcher(dateTime);
        
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
            this.context = "";
            this.overdueContext = "";
            this.eventContext = "";
            
        } else {

            final String preKeyword = matcher.group("preKeyword").trim();
            
            if(preKeyword.equals("on")){
                if(!isValidFormatFor_GivenKeyword(dateTime, preKeyword))
                    throw new IllegalValueException(MESSAGE_KEYWORD_ON_CONSTRAINTS);
                
                this.valueDate = valueDateFormatter(matcher, preKeyword);
                this.context = setContext(valueDate, null);
                this.overdueContext = setOverdueContext(valueDate, null);
                this.eventContext = "";
                this.valueFormatted = valueFormatter(matcher, preKeyword) + context;
                

                
                this.valueTime = null;
                this.valueDateEnd = null;
                this.valueTimeEnd = null;
                
            }else if(preKeyword.equals("by")){
                if(!isValidFormatFor_GivenKeyword(dateTime, preKeyword))
                    throw new IllegalValueException(MESSAGE_KEYWORD_BY_CONSTRAINTS);
                

                
                this.valueDate = valueDateFormatter(matcher, preKeyword);                
                this.valueTime = valueTimeFormatter(matcher, preKeyword); 
                this.context = setContext(valueDate, valueTime);
                this.overdueContext = setOverdueContext(valueDate, valueTime);  
                this.eventContext = "";
                this.valueFormatted = valueFormatter(matcher, preKeyword) + context;

                
                this.valueDateEnd = null;
                this.valueTimeEnd = null;
                
            }else{
                if(!isValidFormatFor_GivenKeyword(dateTime, preKeyword))
                    throw new IllegalValueException(MESSAGE_KEYWORD_FROM_CONSTRAINTS);
                
                final String aftKeyword = matcher.group("aftKeyword").trim();
                
                this.valueDate = valueDateFormatter(matcher, preKeyword);                
                this.valueTime = valueTimeFormatter(matcher, preKeyword);
                this.context = setContext(valueDate, valueTime);

                this.valueDateEnd = valueDateFormatter(matcher, aftKeyword);
                this.valueTimeEnd = valueTimeFormatter(matcher, aftKeyword);
                this.overdueContext = setOverdueContext(valueDateEnd, valueTimeEnd); 
                this.eventContext = setEventContext(valueDate, valueTime, valueDateEnd, valueTimeEnd);
                
                this.valueFormatted = valueFormatter(matcher, preKeyword) + " "
                                    + valueFormatter(matcher, aftKeyword) + context + eventContext;                     
            }
            this.value = dateTime;
        }
    }

    private boolean isValidFormatFor_GivenKeyword(String dateTime, String keyword){
        switch(keyword){
            case "on":
                return dateTime.matches(DateTimeParser.ON_KEYWORD_VALIDATION_REGEX);
            case "by": 
                return dateTime.matches(DateTimeParser.BY_KEYWORD_VALIDATION_REGEX);
            case "from":
                return dateTime.matches(DateTimeParser.FROM_KEYWORD_VALIDATION_REGEX);
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
    

    public String setContext(LocalDate valueDate, LocalTime valueTime) {
    	String context = ""; 
    	Boolean timeIsNow = valueTime != null && valueTime.getHour() == LocalTime.now().getHour() && valueTime.getMinute() == LocalTime.now().getMinute();
    	Boolean dayIsToday = valueDate.isEqual(LocalDate.now());
    	Boolean timeIsLater = valueTime != null && valueTime.isAfter(LocalTime.now());
    	Boolean noTimeSpecified = valueTime == null;
    	Boolean dayIsTomorrow = valueDate.minusDays(1).isEqual(LocalDate.now());
    	Boolean dayIsBeforeToday = valueDate.isBefore(LocalDate.now());
    	String stringHours = ""; 
    	
    	//For tasks due today, now
    	if (dayIsToday && timeIsNow)   	
        	context = "(Today; Now)";
    		
        //For tasks that is due today, after current time
        else if (dayIsToday && timeIsLater){
        	stringHours = Long.toString(LocalTime.now().until(valueTime, ChronoUnit.HOURS));
            context = " (Today; in " + stringHours + " hours) ";
        }
    	
    	//For tasks due today at unspecified times
    	else if (dayIsToday && noTimeSpecified)
    		context = " (Today)";
        
    	//For tasks due tomorrow
        else if (dayIsTomorrow)
        	context = " (Tomorrow)";
    	
        else if (dayIsBeforeToday)
        	context = "";
    	
        else
            context = "";
    	
    	return context;
    }
    
    public String setOverdueContext(LocalDate valueDate, LocalTime valueTime) {
    	String overdueContext = "";
    	Boolean dayIsBeforeToday = valueDate.isBefore(LocalDate.now());
    	Boolean dayIsToday = valueDate.isEqual(LocalDate.now());
    	Boolean dayIsAfterToday = valueDate.isEqual(LocalDate.now());
    	Boolean timeIsBeforeNow = valueTime != null && valueTime.isBefore(LocalTime.now());
    	
    	//For tasks due before today
        if (dayIsBeforeToday){
        	int monthsDue = valueDate.until(LocalDate.now()).getMonths();
        	int yearsDue = valueDate.until(LocalDate.now()).getYears();
        	int daysDue = valueDate.until(LocalDate.now()).getDays();
        	String stringDaysDue = Integer.toString(daysDue);
        	String stringMonthsDue = Integer.toString(monthsDue);
        	String stringYearsDue = Integer.toString(yearsDue);
        	String periodDue = "";
        	
        	if (monthsDue > 0 && yearsDue > 0)
        		periodDue = stringYearsDue + " years, " + stringMonthsDue + " months and " + stringDaysDue + " days";
        	
        	else if (monthsDue > 0 && yearsDue == 0)
        		periodDue = stringMonthsDue + " months and " + stringDaysDue + " days ";
            
        	else if (monthsDue == 0 && yearsDue == 0)
        		periodDue = valueDate.until(LocalDate.now()).getDays() + " days";
        	
        	else 
        		periodDue = "";
        	
        	overdueContext = "Overdue by " + periodDue;
        }
        
        //For tasks that is due today, at or before current time
        else if (dayIsToday && timeIsBeforeNow) {
        	String stringHoursDue = Long.toString(valueTime.until(LocalTime.now(), ChronoUnit.HOURS));       	
        	String periodDue = stringHoursDue + " hours ";
			
			overdueContext = "Due just now, " + periodDue + "ago"; 
		} 
			
		else if (dayIsAfterToday) {
			overdueContext = "";
		}
        
        return overdueContext;
    }
    
    public String setEventContext(LocalDate valueDate, LocalTime valueTime, LocalDate valueDateEnd, LocalTime valueTimeEnd) {
    	String eventContext = "";
    	LocalDateTime valueDateTime = LocalDateTime.of(valueDate, valueTime);
    	LocalDateTime valueDateTimeEnd = LocalDateTime.of(valueDateEnd, valueTimeEnd);
    	Boolean duringEventTime = valueDateTime.isBefore(LocalDateTime.now()) && valueDateTimeEnd.isAfter(LocalDateTime.now());
    	
    	if (duringEventTime)
    		eventContext = " (Today; Now)";
    	
    	else 
    		eventContext = "";
    	
    	return eventContext;
    }
    
    public String getOverdueContext(){
    	return overdueContext;
    }
    
    public String getEventContext(){
    	return eventContext;
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
