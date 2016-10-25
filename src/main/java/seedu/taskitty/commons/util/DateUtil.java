package seedu.taskitty.commons.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import seedu.taskitty.model.task.TaskDate;

//@@author A0139930B-unused
//Code was written before switching over to using Natty
/**
 * Converts a String to Date and vice versa.
 */
public class DateUtil {
    private static final int DATE_COMPONENT_COUNT = 3;
    private static final int DATE_COMPONENT_DAY = 0;
    private static final int DATE_COMPONENT_MONTH = 1;
    private static final int DATE_COMPONENT_YEAR = 2;
    
    //minimum 3 characters are required to accurately identify different months
    private static final String MONTH_NAME_SHORT_JANUARY = "JAN";
    private static final String MONTH_NAME_SHORT_FEBRUARY = "FEB";
    private static final String MONTH_NAME_SHORT_MARCH = "MAR";
    private static final String MONTH_NAME_SHORT_APRIL = "APR";
    private static final String MONTH_NAME_SHORT_MAY = "MAY";
    private static final String MONTH_NAME_SHORT_JUNE = "JUN";
    private static final String MONTH_NAME_SHORT_JULY = "JUL";
    private static final String MONTH_NAME_SHORT_AUGUST = "AUG";
    private static final String MONTH_NAME_SHORT_SEPTEMBER = "SEP";
    private static final String MONTH_NAME_SHORT_OCTOBER = "OCT";
    private static final String MONTH_NAME_SHORT_NOVEMBER = "NOV";
    private static final String MONTH_NAME_SHORT_DECEMBER = "DEC";   
    
    private static final String MONTH_NAME_LONG_JANUARY = "JANUARY";
    private static final String MONTH_NAME_LONG_FEBRUARY = "FEBRUARY";
    private static final String MONTH_NAME_LONG_MARCH = "MARCH";
    private static final String MONTH_NAME_LONG_APRIL = "APRIL";
    private static final String MONTH_NAME_LONG_MAY = "MAY";
    private static final String MONTH_NAME_LONG_JUNE = "JUNE";
    private static final String MONTH_NAME_LONG_JULY = "JULY";
    private static final String MONTH_NAME_LONG_AUGUST = "AUGUST";
    private static final String MONTH_NAME_LONG_SEPTEMBER = "SEPTEMBER";
    private static final String MONTH_NAME_LONG_OCTOBER = "OCTOBER";
    private static final String MONTH_NAME_LONG_NOVEMBER = "NOVEMBER";
    private static final String MONTH_NAME_LONG_DECEMBER = "DECEMBER";   
    
    /**
     * Returns a LocalDate object representing the date from the string
     * 
     * @param dateString cannot be null
     * @throws DateTimeException if Date is invalid
     */
    public static LocalDate parseDate(String dateString) throws DateTimeException {
        assert dateString != null;
        
        LocalDate localDate;
        
        //TODO make clean (ask tutor?)
        //This method should ONLY be called by TaskDate.. so where should I put it?
        //parseFormats will be already validated by TaskDate.
        if (StringUtil.isInteger(dateString)) {
            localDate = parseFormat2(dateString);
        } else {
            String[] dateSplitBySlash = dateString.split("/");
            if (dateSplitBySlash.length == DATE_COMPONENT_COUNT) {
                localDate = parseFormat1(dateSplitBySlash);
            } else {
                localDate = parseFormat3(dateString);
            }
        }
        
        return localDate;
    }
    
    //TODO how to make clean?
    private static LocalDate parseFormat1(String[] dateComponents) throws DateTimeException {
        int day = Integer.parseInt(dateComponents[DATE_COMPONENT_DAY]);
        int month = Integer.parseInt(dateComponents[DATE_COMPONENT_MONTH]);
        int year = Integer.parseInt(dateComponents[DATE_COMPONENT_YEAR]);
        
        return LocalDate.of(year, month, day);
    }
    
    private static LocalDate parseFormat2(String dateString) throws DateTimeException {
        assert dateString.length() == 8; //is it necessary to defend here?
        
        int day = Integer.parseInt(dateString.substring(0, 2));
        int month = Integer.parseInt(dateString.substring(2, 4));
        int year = Integer.parseInt(dateString.substring(4));
        
        return LocalDate.of(year, month, day);
    }
    
    private static LocalDate parseFormat3(String dateString) throws DateTimeException {
        //trim all spaces away
        dateString = dateString.replaceAll("\\s+", "");
        
        int day;
        int monthComponentStartIndex;
        if (StringUtil.isInteger(dateString.substring(0, 2))) {
            day = Integer.parseInt(dateString.substring(0, 2));
            monthComponentStartIndex = 2;
        } else {
            day = Integer.parseInt(dateString.substring(0, 1));
            monthComponentStartIndex = 1;
        }
        
        int monthComponentEndIndex = StringUtil.findNextOccuranceOfInteger(dateString, monthComponentStartIndex);
        
        Month month = Month.valueOf(getFullMonthName(dateString.substring(monthComponentStartIndex, monthComponentEndIndex)));
        
        int year = Integer.parseInt(dateString.substring(monthComponentEndIndex));
        
        return LocalDate.of(year, month, day);
    }
    
    /**
     * Matches the short form of a month to the full month name
     * Returns the full String of the month name or blank String if no match is found.
     * 
     * @param month cannot be null
     */
    public static String getFullMonthName(String month) throws DateTimeException {
        assert month != null;
        
        String fullMonthName;
        month = month.toUpperCase();
        if (month.startsWith(MONTH_NAME_SHORT_JANUARY)) {
            fullMonthName = MONTH_NAME_LONG_JANUARY;
        } else if (month.startsWith(MONTH_NAME_SHORT_FEBRUARY)) {
            fullMonthName = MONTH_NAME_LONG_FEBRUARY;
        } else if (month.startsWith(MONTH_NAME_SHORT_MARCH)) {
            fullMonthName = MONTH_NAME_LONG_MARCH;
        } else if (month.startsWith(MONTH_NAME_SHORT_APRIL)) {
            fullMonthName = MONTH_NAME_LONG_APRIL;
        } else if (month.startsWith(MONTH_NAME_SHORT_MAY)) {
            fullMonthName = MONTH_NAME_LONG_MAY;
        } else if (month.startsWith(MONTH_NAME_SHORT_JUNE)) {
            fullMonthName = MONTH_NAME_LONG_JUNE;
        } else if (month.startsWith(MONTH_NAME_SHORT_JULY)) {
            fullMonthName = MONTH_NAME_LONG_JULY;
        } else if (month.startsWith(MONTH_NAME_SHORT_AUGUST)) {
            fullMonthName = MONTH_NAME_LONG_AUGUST;
        } else if (month.startsWith(MONTH_NAME_SHORT_SEPTEMBER)) {
            fullMonthName = MONTH_NAME_LONG_SEPTEMBER;
        } else if (month.startsWith(MONTH_NAME_SHORT_OCTOBER)) {
            fullMonthName = MONTH_NAME_LONG_OCTOBER;
        } else if (month.startsWith(MONTH_NAME_SHORT_NOVEMBER)) {
            fullMonthName = MONTH_NAME_LONG_NOVEMBER;
        } else if (month.startsWith(MONTH_NAME_SHORT_DECEMBER)) {
            fullMonthName = MONTH_NAME_LONG_DECEMBER;
        } else {
            throw new DateTimeException("Unable to find month " + month);
        }
        return fullMonthName;
    }
    //@@author A0130853L
    public static LocalDate createCurrentDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	Date dateobj = new Date();
    	String date = df.format(dateobj);
    	return LocalDate.parse(date, TaskDate.DATE_FORMATTER);
	}
}
