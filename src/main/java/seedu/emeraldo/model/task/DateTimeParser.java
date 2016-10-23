package seedu.emeraldo.model.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeParser {

    public static final String ON_KEYWORD_VALIDATION_REGEX = "on "
            + "(?<day>(0?[1-9]|[12][0-9]|3[01]))"
            + "(?<DayMonthSeparator>( |/|-))"
            + "(?<month>((0?[1-9]|[1][0-2])|[\\p{Alpha}]+))"
            + "(?<MonthYearSeparator>( |/|-))"
            + "(?<year>(([0-9][0-9])?[0-9][0-9]))";

    public static final String BY_KEYWORD_VALIDATION_REGEX = "by "
            + "(?<day>(0?[1-9]|[12][0-9]|3[01]))"
            + "/(?<month>(0?[1-9]|[1][0-2]))/"
            + "(?<year>(([0-9][0-9])?[0-9][0-9]))"
            + "( (?<hour>([01][0-9]|[2][0-3])))"
            + "(:(?<minute>([0-5][0-9])))";

    public static final String FROM_KEYWORD_VALIDATION_REGEX = "from "
            + "(?<day>(0?[1-9]|[12][0-9]|3[01]))"
            + "/(?<month>(0?[1-9]|[1][0-2]))/"
            + "(?<year>(([0-9][0-9])?[0-9][0-9]))"
            + "( (?<hour>([01][0-9]|[2][0-3])))"
            + "(:(?<minute>([0-5][0-9])))"
            + "( (?<aftKeyword>(to )))"
            + "(?<dayEnd>(0?[1-9]|[12][0-9]|3[01]))"
            + "(/(?<monthEnd>(0?[1-9]|[1][0-2]))/)"
            + "(?<yearEnd>(([0-9][0-9])?[0-9][0-9]))"
            + "( (?<hourEnd>([01][0-9]|[2][0-3])))"
            + "(:(?<minuteEnd>([0-5][0-9])))";

    public static final Pattern DATETIME_VALIDATION_REGEX = Pattern.compile(
            "(?<preKeyword>((by )|(on )|(from )))"
            + "(?<day>(0?[1-9]|[12][0-9]|3[01]))"
            + "(?<DayMonthSeparator>( |/|-))"       //Supports separator to be " ", "/" or "-"
            + "(?<monthInNumbers>(0?[1-9]|[1][0-2])?)"
            + "(?<monthInWords>([\\p{Alpha}]{3,})?)"
            + "(?<MonthYearSeparator>( |/|-))"
            + "(?<year>(([0-9][0-9])?[0-9][0-9]))"
            + "( (?<hour>([01][0-9]|[2][0-3])))?"
            + "(:(?<minute>([0-5][0-9])))?"
            + "( (?<aftKeyword>(to )))?"
            + "(?<dayEnd>(0?[1-9]|[12][0-9]|3[01]))?"
            + "(?<monthEndInNumbers>(0?[1-9]|[1][0-2])?)"
            + "(?<monthEndInWords>([\\p{Alpha}]{3,})?)"
            + "(?<yearEnd>(([0-9][0-9])?[0-9][0-9]))?"
            + "( (?<hourEnd>([01][0-9]|[2][0-3])))?"
            + "(:(?<minuteEnd>([0-5][0-9])))?"
            );

    public static LocalDate valueDateFormatter(Matcher matcher, String keyword){
        
        String day = matcher.group("day");
        String month = matcher.group("monthInNumbers");
        String year = matcher.group("year");

        if(keyword.equals("to")){
            day = matcher.group("dayEnd");
            month = matcher.group("monthEndInNumbers");
            year = matcher.group("yearEnd");
        }
        
        //TODO: catch monthWords and monthEndWords that are shorter than 3 characters
        if(month.isEmpty()){
            month = matcher.group("monthInWords").toLowerCase().substring(0,3);
            if(keyword.equals("to"))
                month = matcher.group("monthEndInWords").toLowerCase().substring(0,3);
            month = convertMonthFromWordsToNumbers(month);
        }
        
        int yearParsed = Integer.parseInt(year);
        int monthParsed = Integer.parseInt(month);
        int dayParsed = Integer.parseInt(day);
        
        return LocalDate.of(yearParsed, monthParsed, dayParsed);
    }
    
    public static LocalTime valueTimeFormatter(Matcher matcher, String keyword){
        
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
    
    public static String valueFormatter(Matcher matcher, String keyword){
        
        String day = matcher.group("day");
        String month = matcher.group("monthInNumbers");
        String year = matcher.group("year");
        String hour = matcher.group("hour");
        String minute = matcher.group("minute");
        
        if(keyword.equals("to")){
            day = matcher.group("dayEnd");
            month = matcher.group("monthEndInNumbers");
            year = matcher.group("yearEnd");
            hour = matcher.group("hourEnd");
            minute = matcher.group("minuteEnd");
        }
        
        if(month.isEmpty()){
            month = matcher.group("monthInWords").toLowerCase().substring(0,3);
            if(keyword.equals("to"))
                month = matcher.group("monthEndInWords").toLowerCase().substring(0,3);
            month = convertMonthFromWordsToNumbers(month);
        }
        
        int monthParsed = Integer.parseInt(month);
        
        if(keyword.equals("on"))
            return keyword + " " + day + " " + convertMonthFromIntToWords(monthParsed) + " " + year;
        else{
            return keyword + " " + day + " " + convertMonthFromIntToWords(monthParsed) +  " " 
                    + year + ", " + hour + ":" + minute;
        }
    }
    
    private static String convertMonthFromIntToWords(int monthParsed){
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
    
    private static String convertMonthFromWordsToNumbers(String monthInWords){
        String monthInNumbers;
        switch(monthInWords){
            case "jan":
                monthInNumbers = "1";
                break;
            case "feb":
                monthInNumbers = "2";
                break;
            case "mar":
                monthInNumbers = "3";
                break;
            case "apr":
                monthInNumbers = "4";
                break;
            case "may":
                monthInNumbers = "5";
                break;
            case "jun":
                monthInNumbers = "6";
                break;
            case "jul":
                monthInNumbers = "7";
                break;
            case "aug":
                monthInNumbers = "8";
                break;
            case "sep":
                monthInNumbers = "9";
                break;
            case "oct":    
                monthInNumbers = "10";
                break;
            case "nov":
                monthInNumbers = "11";
                break;
            case "dec":
                monthInNumbers = "12";
                break;             
            default: monthInNumbers = "Invalid month";
        }
    
        return monthInNumbers;
    }
}