package seedu.address.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidation {

    private static Pattern pattern;
    private static Matcher matcher;
    public static final String DATE_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)";

    public void DateValidator() {

    }

    /**
     * Validate date format with regular expression
     * 
     * @param date
     * @return true valid date format, false invalid date format
     */
    public static boolean validate(String date) {

        pattern = Pattern.compile(DATE_VALIDATION_REGEX);
        matcher = pattern.matcher(date);

        if (matcher.matches()) {

            matcher.reset();

            if (matcher.find()) {

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") && ((month.equals("4") || month.equals("6") || month.equals("9")
                        || month.equals("11") || month.equals("04") || month.equals("06") || month.equals("09")))) {
                    return false; // 4,6,9,11th month only have 30 days
                } else if (month.equals("2") || month.equals("02")) {
                    // leap year
                    if ((year % 4 == 0) && (year % 400 == 0) && (year % 100 != 0)) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Return today's date
    public static String TodayDate() {
        String todaydate;
        todaydate = DateFormatChange();
        return todaydate;
    }

    // Return tomorrow's date
    public static String TomorrowDate() {
        String tomorrowdate;
        tomorrowdate = DateFormatChange();
        return tomorrowdate;
    }

    public static String DateFormatChange() {
        String strDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        return strDate;

    }

}
