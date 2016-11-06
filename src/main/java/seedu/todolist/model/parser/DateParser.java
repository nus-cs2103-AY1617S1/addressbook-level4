package seedu.todolist.model.parser;

import java.time.DateTimeException;
import java.time.LocalDate;

//@@author A0138601M
/**
 * Converts a String to Date and vice versa.
 */
public class DateParser {
    
    private static final int DATE_COMPONENT_TOTAL = 3;
    
    private static final int DATE_COMPONENT_INDEX_YEAR = 2;
    private static final int DATE_COMPONENT_INDEX_MONTH = 1;
    private static final int DATE_COMPONENT_INDEX_DAY = 0;
    
    private static final String[] MONTH_LIST = {"JANUARY", "FEBRUARY", "MARCH", "APRIL" , 
            "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private static final int MONTH_OFFSET_INDEX = 1;
    
    /**
     * Parses string date input into LocalDate date.
     *
     * @param string date input
     * @return LocalDate date based on the string date input
     */
    public static LocalDate parseDate(String date) throws DateTimeException {
        assert date != null;
        LocalDate parsedDate;
        if (date.contains("/")) {
            parsedDate = parseDateWithSlash(date);
        }
        else {
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
        String[] dateComponents = date.split("/");
        
        int day, month, year;
        day = Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_DAY]);
        month = Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_MONTH]);  
        year = getYearValue(dateComponents);
              
        return LocalDate.of(year, month, day);
    }
    
    /**
     * Parses string date input with month name into LocalDate date.
     *
     * @param string date input with month name
     * @return LocalDate date based on the string date input
     */
    private static LocalDate parseDateWithMonthName(String date) throws DateTimeException {
        String[] dateComponents = date.split(" ");
        
        int day, month, year;
        day = Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_DAY]);
        month = getMonthValue(dateComponents[DATE_COMPONENT_INDEX_MONTH]);
        year = getYearValue(dateComponents);
        
        return LocalDate.of(year, month, day);
    }
    
    /**
     * Parses string month name into int month value.
     *
     * @param string month name
     * @return int month value based on string month name
     */
    private static int getMonthValue(String month) {
        int monthValue = 0;
        for (int i = 0; i < MONTH_LIST.length; i++) {
            if (MONTH_LIST[i].contains(month.toUpperCase())) {
                monthValue = i + MONTH_OFFSET_INDEX;
            }
        }
        if (monthValue == 0) {
            throw new DateTimeException("Unable to find month " + month);
        }
        return monthValue;
    }
    
    /**
     * Get year in date components. If year does not exist, get the current year.
     *
     * @param string date components input
     * @return int year value
     */
    private static int getYearValue(String[] dateComponents) {
        if (isYearMissing(dateComponents)) {
            return getCurrentYear();
        }
        else {
            return Integer.parseInt(dateComponents[DATE_COMPONENT_INDEX_YEAR]);
        }
    }
    
    private static boolean isYearMissing(String[] dateComponents) {
        if (dateComponents.length < DATE_COMPONENT_TOTAL) {
            return true;
        }
        return false;
    }
    
    private static int getCurrentYear() {
        LocalDate now = LocalDate.now();
        return now.getYear();
    }

}
