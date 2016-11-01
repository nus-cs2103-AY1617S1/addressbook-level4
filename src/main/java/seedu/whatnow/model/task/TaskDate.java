package seedu.whatnow.model.task;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import java.text.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class checks for the validity of the user input date by checking with
 * currentDate, and checking if the date range is valid Throws its respective
 * message if the input is invalid
 */
// @@author A0139128A
public class TaskDate {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Task Date should be represented as one of the followings:\n"
            + "dd/mm/yyyy\n" + "day month year\n" + "today\n" + "tomorrow\n";
    public static final String EXPIRED_TASK_DATE = "Task Date cannot be in the past!";
    public static final String INVALID_TASK_DATE_RANGE_FORMAT = "The task date range is invalid!";

    public static final String DATE_ALPHA_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+[\\w\\.])+([0-9]{4})"; // To
                                                                                                         // be
                                                                                                         // updated
    public static final String DATE_ALPHA_WITHOUT_YEAR_VALIDATION_REGEX = "([0-9]{2}+[\\w\\.])";

    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+)/([0-9]{2}+)/([0-9]{4})"; // "\\d{2}/\\d{2}/\\d{4}";
                                                                                                                 // //To
                                                                                                                 // be
                                                                                                                 // updated
    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX = "([0-9]{2})/([0-9]{2})";// "\\d{2}/\\d{2}";
    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_REGEX = "([0-9]{1}+)/([0-9]{2}+)/([0-9]{4})";
    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_MONTH_REGEX = "([0-9]{2}+)/([1-9]{1}+)/([0-9]{4})";
    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_AND_MONTH_REGEX = "([0-9]{1}+)/([0-9]{1}+)/([0-9]{4})";

    public static final String DATE_NUM_SLASH_WITH_YEAR_FORMAT = "dd/MM/yyyy";
    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT = "dd/MM";
    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT = "d/MM/yyyy";
    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT = "dd/M/yyyy";
    public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT = "d/M/yyyy";

    public static final String DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT = "dd MMMM yyyy ";
    public static final String DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT = "dd MMMM";

    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_MODIFIED_REGEX = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";

    public static ArrayList<String> ListOfDateRegex;
    public static ArrayList<String> ListOfDateFormat;

    private static final String TODAY = "today";
    private static final String TMR = "tomorrow";
    private String fullDate;
    private String startDate;
    private String endDate;

    // @@author A0139128A
    /**
     * Validates given date
     *
     * @throw IllegalValueException if given date is invalid
     */
    public TaskDate(String taskDate, String startDate, String endDate)
            throws IllegalValueException, java.text.ParseException {
        performAddDateFormatRegex();

        if (taskDate == null && startDate != null && endDate != null) {
            if (!isValidDateRange(startDate, endDate)) {
                throw new IllegalValueException(INVALID_TASK_DATE_RANGE_FORMAT);
            }
        } else {
            if (!isValidDate(taskDate)) {
                throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
            }
            if (taskDate.equals(TODAY)) {
                assignTodayDate(taskDate);
            } else if (taskDate.equals(TMR)) {
                assignTmrDate(taskDate);
            }
        }
    }

    // @@author A0139128A
    /**
     * Adds all the relevant Date format and regex that is neeeded to find the
     * corresponding user input
     */
    private void performAddDateFormatRegex() {
        ListOfDateRegex = new ArrayList<String>();
        ListOfDateFormat = new ArrayList<String>();
        ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_MONTH_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_AND_MONTH_REGEX);
        ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_ALPHA_WITH_YEAR_VALIDATION_REGEX);
        ListOfDateRegex.add(DATE_ALPHA_WITHOUT_YEAR_VALIDATION_REGEX);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT);
        ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT);
        ListOfDateFormat.add(DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT);
        ListOfDateFormat.add(DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT);
    }

    // @@author A0139128A
    /** Assigns today's date to fullDate */
    private void assignTodayDate(String taskDate) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        Calendar cal = Calendar.getInstance();
        taskDate = dateFormat.format(cal.getTime());
        fullDate = taskDate;
    }

    // @@author A0139128A
    /** Assigns tmr's date to fullDate */
    private void assignTmrDate(String taskDate) {
        DateFormat dateFormat2 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        Calendar cal2 = Calendar.getInstance();

        cal2.add(Calendar.DATE, 1);
        taskDate = dateFormat2.format(cal2.getTime());
        fullDate = taskDate;
    }

    // @@author A0139128A
    /**
     * 
     * @param test
     *            is a given user date input
     * @return the validity of the user date input by passing it to methods of
     *         different regex
     * @throws java.text.ParseException
     * @throws IllegalValueException
     */
    private boolean isValidDate(String reqDate) throws java.text.ParseException, IllegalValueException {
        if (reqDate.equals(TODAY) || reqDate.equals(TMR)) {
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
     * This function finds the respective regex that matches the user input and
     * sends to isValidDateRangeValidator to check if the two dates are really
     * valid
     * 
     * @param startDate
     *            is the user input startingDate
     * @param endDate
     *            is the user input endingDate
     * @return true is valid date range, else false
     * @throws java.text.ParseException
     */
    // @@author A0319128A
    private boolean isValidDateRange(String startDate, String endDate) throws java.text.ParseException {
        for (int i = 0; i < ListOfDateFormat.size() && i < ListOfDateRegex.size(); i++) {
            if ((startDate.matches(ListOfDateRegex.get(i)) && (endDate.matches(ListOfDateRegex.get(i))))) {
                return isValidDateRangeValidator(startDate, endDate, ListOfDateFormat.get(i));
            }
        }
        return false;
    }

    // @@author A0139128A
    private boolean isValidDateRangeValidator(String beforeDate, String afterDate, String format) {
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
            if (beginDate.before(
                    finishDate)) { /**
                                    * Checks if supposedly earlier date is
                                    * indeed earlier than the later date
                                    */
                validDateRange = true;
            }
            if (beginDate
                    .equals(finishDate)) { /**
                                            * Checks if supposedly earlier date
                                            * is indeed equals to the later date
                                            */
                sameDate = true;
            }
        } catch (ParseException e) {
            return false;
        }
        /**
         * Following is done because the default date gotten from currentDate is
         * always 0000(time) i.e. always earlier than the user inputDates
         */
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
        } else if (!validDateRange && !sameDate) {
            return false;
        } else {
            startDate = beforeDate;
            endDate = afterDate;
            return true;
        }
    }

    /**
     * @param test
     *            is the date input by the user
     * @param format
     *            is the type of format that the date input will be tested
     *            against with
     * @return the validity of the user input
     * @throws java.text.ParseException
     * @throws IllegalValueException
     */
    private boolean isValidNumDate(String incDate, String format)
            throws java.text.ParseException, IllegalValueException {
        Date inputDate = null;
        try {
            DateFormat df = new SimpleDateFormat(format);
            df.setLenient(false);

            inputDate = df.parse(incDate);
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
            throw new IllegalValueException(EXPIRED_TASK_DATE);
        }

        /**
         * The following will ensure the date format to be
         * DATE_NUM_SLASH_WITH_YEAR_FORMAT
         */
        if (format.equals(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT)) {
            Calendar now = Calendar.getInstance();
            int yearInt = now.get(Calendar.YEAR);
            String year = String.valueOf(yearInt);
            incDate.concat(year);
            fullDate = incDate;
            return true;
        } else if (format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT)) {
            fullDate = "0" + incDate;
            return true;
        } else if (format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT)) {
            String toReplaceFullDate = incDate;
            String[] split = toReplaceFullDate.split("/");
            fullDate = split[0] + "/0" + split[1] + "/" + split[2];
            return true;
        } else if (format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT)) {
            String toReplaceFullDate = incDate;
            String[] split = toReplaceFullDate.split("/");
            fullDate = "0" + split[0] + "/0" + split[1] + "/" + split[2];
            return true;
        } else {
            fullDate = incDate;
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

    @Override
    public String toString() {
        if (fullDate == null) {
            return startDate + " " + endDate;
        } else {
            return fullDate;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                        && this.fullDate.equals(((TaskDate) other).fullDate)); // state
                                                                               // check
    }

    /** Returns the fullDate */
    public String getDate() {
        return fullDate;
    }

    /** Returns the startDate */
    public String getStartDate() {
        return this.startDate;
    }

    /** Returns the endDate */
    public String getEndDate() {
        return this.endDate;
    }
}