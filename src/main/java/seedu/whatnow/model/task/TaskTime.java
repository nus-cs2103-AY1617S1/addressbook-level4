//@@author A0139128A
package seedu.whatnow.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import seedu.whatnow.commons.exceptions.IllegalValueException;

/**
 * This class checks if the user input date and time are of a valid one by
 * checking currentTime/currentDate/Range and throws its respective message if
 * the input is invalid
 */
public class TaskTime {

    public static final String TWELVE_HOUR_WITH_MINUTES_COLON_REGEX = "(((\\d|\\d\\d):\\d\\d)(am|pm))";
    public static final String TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT = "h:mma"; /*
                                                                                 * E
                                                                                 * .
                                                                                 * g.
                                                                                 * 1
                                                                                 * :
                                                                                 * 50pm
                                                                                 */
    public static final String TWELVE_HOUR_WITH_MINUTES_DOT_REGEX = "(((\\d|\\d\\d)\\.\\d\\d)(am|pm))";
    public static final String TWELVE_HOUR_WITH_MINUTES_DOT_FORMAT = "h.mma"; /*
                                                                               * E
                                                                               * .
                                                                               * g.
                                                                               * 1
                                                                               * .
                                                                               * 45pm
                                                                               */
    public static final String TWELVE_HOUR_WITHOUT_MINUTES_REGEX = "([1]*[0-9]{1}+)(am|pm)";
    public static final String TWELVE_HOUR_WITHOUT_MINUTES_EXTEND_FORMAT = "hha";

    public static final String DATE_NUM_SLASH_WITH_YEAR_FORMAT = "dd/MM/yyyy";
    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+)/([0-9]{2}+)/([0-9]{4})";

    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT = "d/MM/yyyy";
    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_VALIDATION_REGEX = "([1-9]{1}+)/([0-9]{2}+)/([0-9]{4})";

    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT = "dd/M/yyyy";
    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_VALIDATION_REGEX = "([1-9]{2}+)/([1-9]{1}+)/([0-9]{4})";

    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT = "d/M/yyyy";
    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_VALIDATION_REGEX = "([1-9]{1}+)/([1-9]{1}+)/([0-9]{4})";

    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT = "dd/MM";
    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX = "([1-9]{2}+)/([0-9]{2}+)";

    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_FORMAT = "d/MM";
    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_VALIDATION_REGEX = "([1-9]{1}+)/([0-9]{2}+)";

    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENND_DAY_MONTH_FORMAT = "d/M";
    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_MONTH_REGEX = "([1-9]{1}+)/([1-9]{1}+)";

    public static final String DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT = "dd MMMM yyyy ";
    public static final String DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT = "dd MMMM";

    public static final String DATE_NUM_REGEX_WITH_YEAR = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    public static ArrayList<String> ListOfDateRegex;
    public static ArrayList<String> ListOfDateFormat;
    public static ArrayList<String> ListOfTimeRegex;
    public static ArrayList<String> ListOfTimeFormat;
    public final String INVALID_TIME_MESSAGE = "Entered an invalid time format";
    public final String INVALID_TIME_RANGE_MESSAGE = "Entered an invalid time range format";
    public final String INVALID_DATE_MESSAGE = "Entered an invalid date format";
    public final String INVALID_DATE_RANGE_MESSAGE = "Entered an invalid date range format";

    private String time = null;
    private String startTime = null;
    private String endTime = null;
    private String date = null;
    private String startDate = null;
    private String endDate = null;

    private static String TODAY = "today";
    private static String TMR = "tmr";

    public TaskTime(String time, String startTime, String endTime, String date, String startDate, String endDate)
            throws IllegalValueException {
        prepareListOfDateAndTimeFormatRegex();
        if (!isValidDate(date)) {
            throw new IllegalValueException(INVALID_DATE_MESSAGE);
        }
        if (!isValidDateRange(startDate, endDate)) {
            throw new IllegalValueException(INVALID_DATE_RANGE_MESSAGE);
        }
        if (!isValidTime(time)) {
            throw new IllegalValueException(INVALID_TIME_MESSAGE);
        }
        if (!isValidTimeRange(startTime, endTime)) {
            throw new IllegalValueException(INVALID_TIME_RANGE_MESSAGE);
        }
    }

    /**
     * Prepares the list of format and regex that will be used to find the
     * corresponding format
     */
    private void prepareListOfDateAndTimeFormatRegex() {

        ListOfDateRegex = new ArrayList<String>();
        ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_MONTH_REGEX);

        ListOfDateFormat = new ArrayList<String>();
        ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENND_DAY_MONTH_FORMAT);

        ListOfTimeRegex = new ArrayList<String>();
        ListOfTimeRegex.add(TWELVE_HOUR_WITH_MINUTES_COLON_REGEX);
        ListOfTimeRegex.add(TWELVE_HOUR_WITH_MINUTES_DOT_REGEX);
        ListOfTimeRegex.add(TWELVE_HOUR_WITHOUT_MINUTES_REGEX); // ListOfTimeRegex.add(TWELVE_HOUR_WITHOUT_MINUTES_EXTEND_REGEX);

        ListOfTimeFormat = new ArrayList<String>();
        ListOfTimeFormat.add(TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT);
        ListOfTimeFormat.add(TWELVE_HOUR_WITH_MINUTES_DOT_FORMAT);
        // ListOfTimeFormat.add(TWELVE_HOUR_WITHOUT_MINUTES_FORMAT);
        ListOfTimeFormat.add(TWELVE_HOUR_WITHOUT_MINUTES_EXTEND_FORMAT);
    }

    public String getFullString() {
        return (date + " " + startDate + " " + endDate + " " + time + " " + startTime + " " + endTime);
    }

    /** returns time */
    public String getTime() {
        return this.time;
    }

    /** returns startTime */
    public String getStartTime() {
        return this.startTime;
    }

    /** returns endTime */
    public String getEndTime() {
        return this.endTime;
    }

    /** returns currentDate */
    public String getDate() {
        return this.date;
    }

    /** returns startDate */
    public String getStartDate() {
        return this.startDate;
    }

    /** returns endDate */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * This function checks if the reqTime by the user is valid by finding the
     * corresponding format and passes to the method isValidTimeSeq upon finding
     * a match
     * 
     * @param reqTime
     *            is the user inputTime
     * @return true if the userInput time is valid, else false
     */
    public boolean isValidTime(String reqTime) {

        if (reqTime == null) {
            return true;
        } else {
            for (int j = 0; j < ListOfTimeRegex.size(); j++) {
                if (reqTime.matches(ListOfTimeRegex.get(j))) {
                    return isValidTimeSeq(reqTime, ListOfTimeFormat.get(j));
                }
            }
            return false;
        }
    }

    /**
     * Checks if given time given by the user is valid
     * 
     * @param reqTime
     *            is the time input by the user
     * @param format
     *            is the format that matches the reqTime's format
     * @return true if the format is valid, else false
     */
    public boolean isValidTimeSeq(String reqTime, String format) {
        /** First checks if it is indeed valid */
        boolean currEarlierThanInput = false;
        Date inputTime = null;
        Date todayTime = null;
        try {
            String currentTime = new SimpleDateFormat(format).format(new Date());
            DateFormat tf = new SimpleDateFormat(format);
            tf.setLenient(false);

            inputTime = tf.parse(reqTime);
            todayTime = tf.parse(currentTime);
            if (todayTime.before(inputTime)) {
                currEarlierThanInput = true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
        /**
         * Attempts to put today's date, if currentTime is earlier than
         * inputTime, put tomorrow's date instead
         */
        if (startDate == null && endDate == null && date == null) {
            return appendTodayOrTmr(currEarlierThanInput, reqTime);
        } else if (date != null) { /** Implies that only 1 date exists */
            if (date.toLowerCase().equals(TODAY)) {
                if (currEarlierThanInput) {
                    DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
                    Calendar cal = Calendar.getInstance();
                    String taskDate = dateFormat.format(cal.getTime());
                    date = taskDate;
                    time = reqTime;
                    return true;
                } else
                    return false;
            } else if (date.toLowerCase().equals(TMR)) {
                DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                date = dateFormat.format(cal.getTime());
                time = reqTime;
                return true;
            }
        } else {
            time = reqTime;
            return true;
        }
        return false;
    }

    /**
     * Appends today or tomorrow's date depending on currentTime if a date was
     * not input by the user
     * 
     * @param currEarlierThanInput
     *            indicates if a currentTime is indeed earlier than an input
     * @param reqTime
     *            indicates the inputTime by the user
     * @return true regardless as a single inputTime by the user is treated by a
     *         valid
     */
    public boolean appendTodayOrTmr(boolean currEarlierThanInput, String reqTime) {
        if (currEarlierThanInput) {
            DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
            Calendar cal = Calendar.getInstance();
            String taskDate = dateFormat.format(cal.getTime());
            date = taskDate;
            time = reqTime;
            return true;
        } else {
            DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            date = dateFormat.format(cal.getTime());
            time = reqTime;
            return true;
        }
    }

    /**
     * This function checks if the givenTimeRange is valid by finding the
     * corresponding regex and passes its corresponding format to a method
     * isValidNumTime
     * 
     * @param beforeTime
     *            is the startTime input by the user
     * @param afterTime
     *            is the endTime input by the user
     * @return true if the timeRangeIsValid, else false
     */
    public boolean isValidTimeRange(String beforeTime, String afterTime) {
        if (beforeTime == null && afterTime == null && time != null) {
            return true;
        } else {
            for (int i = 0; i < ListOfTimeRegex.size() && i < ListOfTimeFormat.size(); i++) {
                if (beforeTime.matches(ListOfTimeRegex.get(i)) && afterTime.matches(ListOfTimeRegex.get(i))) {
                    return isValidNumTime(beforeTime, afterTime, ListOfTimeFormat.get(i));
                }
            }
        }
        return false;
    }

    /**
     * This function checks the validity of the time range and finds
     * 
     * @param beforeTime
     *            is the user input starting time
     * @param afterTime
     *            is the user input ending time
     * @param format
     *            is the format that matches the format of the startTime
     * @return true if valid timeFormat range, else return false
     */
    public boolean isValidNumTime(String beforeTime, String afterTime, String format) {
        boolean currEarlierThanInput = false;
        boolean beforeEarlierThanAfter = false;
        Date inputBeforeTime = null;
        Date inputAfterTime = null;
        Date todayTime = null;
        try {
            String currentTime = new SimpleDateFormat(format).format(new Date());
            DateFormat tf = new SimpleDateFormat(format);
            tf.setLenient(false);

            inputBeforeTime = tf.parse(beforeTime);
            inputAfterTime = tf.parse(afterTime);
            todayTime = tf.parse(currentTime);
            if (inputBeforeTime.before(todayTime) || inputAfterTime.before(todayTime)) {
                currEarlierThanInput = true;
            }
            if (inputBeforeTime.before(inputAfterTime)) {
                beforeEarlierThanAfter = true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }

        if (beforeEarlierThanAfter) {
            return performBeforeEarlierThanAfter(currEarlierThanInput, beforeTime, afterTime);
        } else {
            if (startDate != null && endDate != null) {
                if (!startDate.equals(endDate)) {
                    startTime = beforeTime;
                    endTime = afterTime;
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * If user input earlierTime is indeed earlier than afterTime, compares with
     * currTime and appends the correct date and respective time
     * 
     * @return true if Time range is indeed valid , else false
     */
    private boolean performBeforeEarlierThanAfter(boolean currEarlierThanInput, String beforeTime, String afterTime) {
        if (startDate == null && endDate == null && date == null) {
            if (!currEarlierThanInput) {
                DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
                Calendar cal = Calendar.getInstance();
                String taskDate = dateFormat.format(cal.getTime());
                date = taskDate;
                startTime = beforeTime;
                endTime = afterTime;
                return true;
            } else {
                DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                date = dateFormat.format(cal.getTime());
                startTime = beforeTime;
                endTime = afterTime;
                return true;
            }
        } else if (date != null) {
            if (date.equals(TODAY)) {
                if (!currEarlierThanInput) {
                    DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
                    Calendar cal = Calendar.getInstance();
                    String taskDate = dateFormat.format(cal.getTime());
                    date = taskDate;
                    startTime = beforeTime;
                    endTime = afterTime;
                    return true;
                } else {
                    return false;
                }
            } else if (date.equals(TMR)) {
                DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                date = dateFormat.format(cal.getTime());
                startTime = beforeTime;
                endTime = afterTime;
                return true;
            } else {
                startTime = beforeTime;
                endTime = afterTime;
                return true;
            }
        } else {
            startTime = beforeTime;
            endTime = afterTime;
            return true;
        }
    }

    /**
     * Checks if a particular date is valid i.e. not before currentDate and is a
     * valid sequence num is to be used to indicate what date im referring to
     * i.e. 0 for variable date, 1 for variable startDate, 2 for variable
     * endDate
     * 
     * @return true if valid, else return false
     */
    public boolean isValidDate(String reqDate) {
        if (reqDate == null) {
            date = null;
            return true;
        } else if (reqDate.toLowerCase().equals(TODAY)) {
            date = TODAY;
            return true;
        } else if (reqDate.toLowerCase().equals(TMR)) {
            date = TMR;
            return true;
        } else {
            for (int i = 0; i < ListOfDateFormat.size() && i < ListOfDateRegex.size(); i++) {
                if (reqDate.matches(ListOfDateRegex.get(i))) {
                    return isValidNumDate(reqDate, ListOfDateFormat.get(i));
                }
            }
            return false;
        }
    }

    /**
     * This function checks whether the givenDate by the user is valid by
     * placing it through different checks.
     * 
     * @param reqDate
     *            is the userInput time
     * @param format
     *            is the format that matches the user inputTime
     * @return true if the time is valid, else false
     */
    // @@author A0139128A-reused
    public boolean isValidNumDate(String reqDate, String format) {
        /** First check: whether if this date is of a valid format */
        Date inputDate = null;
        try {
            DateFormat df = new SimpleDateFormat(format);
            df.setLenient(false);

            inputDate = df.parse(reqDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
        Calendar input = new GregorianCalendar();
        input = setGregorian(input, inputDate);
        inputDate = input.getTime();

        /**
         * Following checks if the user input date is invalid i.e before today's
         * date
         */
        Calendar current = new GregorianCalendar();
        current = setGregorianCurrent(current);
        Date currDate = current.getTime();
        if (currDate.compareTo(inputDate) > 0) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a particular Date range is valid i.e. startDate is before
     * endDate
     * 
     * @return true if range is valid, false if range is invalid
     */
    // @@author A0139128A-reused
    public boolean isValidDateRange(String beforeDate, String afterDate) {
        if (beforeDate == null && afterDate == null) {
            return true;
        }
        boolean validDateRange = false;
        boolean sameDate = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date beginDate = null;
        Date finishDate = null;
        try {
            beginDate = sdf.parse(beforeDate);
            finishDate = sdf.parse(afterDate);
            if (beginDate.before(finishDate)) {
                validDateRange = true;
            }
            if (beginDate.equals(finishDate)) {
                sameDate = true;
            }
        } catch (ParseException e) {
            return false;
        }
        Calendar before = new GregorianCalendar();
        before = setGregorian(before, beginDate);
        beginDate = before.getTime();

        Calendar after = new GregorianCalendar();
        after = setGregorian(after, finishDate);
        finishDate = after.getTime();

        /**
         * Following checks if the user input date is invalid i.e before today's
         * date
         */
        Calendar current = new GregorianCalendar();
        current = setGregorianCurrent(current);
        Date currDate = current.getTime();

        if (currDate.compareTo(beginDate) > 0 || currDate.compareTo(finishDate) > 0) {
            return false;
        }
        if (!validDateRange && !sameDate) {
            return false;
        } else {
            startDate = beforeDate;
            endDate = afterDate;
            return true;
        }
    }

    /**
     * This method sets the date to be of the latest time as a date always comes
     * attached with a default time and there is a need to overwrite this timing
     * to the latest so that it can be compared with the current date
     */
    private Calendar setGregorian(Calendar cal, Date reqDate) {
        cal.setTime(reqDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal;
    }

    /** Gets the current Date and set it to earliest */
    private Calendar setGregorianCurrent(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        return cal;
    }
}
