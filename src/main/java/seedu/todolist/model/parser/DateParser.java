package seedu.todolist.model.parser;

import java.time.DateTimeException;
import java.time.LocalDate;

//@@author A0138601M
/**
 * Converts a String to Date and vice versa.
 */
public class DateParser {
     
    private static final int DATE_COMPONENT_INDEX_YEAR = 2;
    private static final int DATE_COMPONENT_INDEX_MONTH = 1;
    private static final int DATE_COMPONENT_INDEX_DAY = 0;
    
    private static final String MONTH_NOT_FOUND = "Unable to find month ";
    private static final String[] MONTH_LIST = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", 
                                                "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private static final int MONTH_DUMMY_VALUE = 0;
    private static final int MONTH_OFFSET_INDEX = 1;
       
    public static final String DATE_DELIMITER_SLASH = "/";
    public static final String DATE_DELIMITER_SPACE = " ";
    
    
    /**
     * Parses string date input into LocalDate date.
     *
     * @param string date input
     * @return LocalDate date based on the string date input
     */
    public static LocalDate parseDate(String date) throws DateTimeException {
        assert date != null;
        LocalDate parsedDate;
        if (date.contains(DATE_DELIMITER_SLASH)) {
            parsedDate = parseDateWithSlash(date);
        } else {
            parsedDate = parseDateWithMonthName(date);
        }
        return parsedDate;
    }
    
    /**
     * Parses string date input with slash into LocalDate date.
     *
     * @param string date input with slash
     * @return LocalDate date based on the string date input
     */
    private static LocalDate parseDateWithSlash(String date) throws DateTimeException {
        String[] dateComponents = date.split(DATE_DELIMITER_SLASH);
        
        int day, month, year;
        day = Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_DAY]);
        month = Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_MONTH]);  
        year = Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_YEAR]);
              
        return LocalDate.of(year, month, day);
    }
    
    /**
     * Parses string date input with month name into LocalDate date.
     *
     * @param string date input with month name
     * @return LocalDate date based on the string date input
     */
    private static LocalDate parseDateWithMonthName(String date) throws DateTimeException {
        String[] dateComponents = date.split(DATE_DELIMITER_SPACE);
        
        int day, month, year;
        day = Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_DAY]);
        month = getMonthValue(dateComponents[DATE_COMPONENT_INDEX_MONTH]);
        year = Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_YEAR]);
        
        return LocalDate.of(year, month, day);
    }
    
    /**
     * Parses string month name into int month value.
     *
     * @param string month name
     * @return int month value based on string month name
     */
    private static int getMonthValue(String month) {
        int monthValue = MONTH_DUMMY_VALUE;
        for (int i = 0; i < MONTH_LIST.length; i++) {
            if (MONTH_LIST[i].contains(month.toUpperCase())) {
                monthValue = i + MONTH_OFFSET_INDEX;
            }
        }
        if (monthValue == MONTH_DUMMY_VALUE) {
            throw new DateTimeException(MONTH_NOT_FOUND + month);
        }
        return monthValue;
    }  
}
