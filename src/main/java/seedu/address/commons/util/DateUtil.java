package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateUtil {
    private static List<SimpleDateFormat> DATE_FORMATS; // validate date format
                                                        // if both time and date
                                                        // are present
    private static List<SimpleDateFormat> DATE_FORMATS1; // validate date format
                                                         // if only date is
                                                         // present
    private static List<SimpleDateFormat> DATE_FORMATS2; // convert
                                                         // DATE_FORMATS1 date
                                                         // to date and time
    private static List<SimpleDateFormat> TIME_FORMATS; // convert DATE_FORMATS1
                                                        // date to date and time
    public static final String INVALID_FORMAT = "Invalid Format";
    public static final String INVALID_TIME = "Time Input is missing";

    public DateUtil() {
        DATE_FORMATS = new ArrayList<>();
        DATE_FORMATS1 = new ArrayList<>();
        DATE_FORMATS2 = new ArrayList<>();
        TIME_FORMATS = new ArrayList<>();
        TIME_FORMATS.add(new SimpleDateFormat("h:mm a"));
        TIME_FORMATS.add(new SimpleDateFormat("HHmm"));
        TIME_FORMATS.add(new SimpleDateFormat("HH:mm"));
        TIME_FORMATS.add(new SimpleDateFormat("H:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d.MM.yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h.mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d.MM.yyyy h.mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HHmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d.MM.yyyy HHmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy Hmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d.MM.yyyy Hmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d.MM.yyyy HH:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy H:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d.MM.yyyy H:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HH.mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d.MM.yyyy HH.mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy H.mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d.MM.yyyy H.mm"));
        DATE_FORMATS.add(new SimpleDateFormat("EEE, MMM d, yyyy h:mm a"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d.MM.yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d.MM.yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d.MM.yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d.MM.yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d.MM.yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d.MM.yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d.MM.yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d.MM.yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("EEE, MMM d, yyyy"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d.MM.yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy H:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d.MM.yyyy H:mm"));
    }

    // @@author A0125680H
    /**
     * Validate date format with regular expression
     * 
     * @param date
     * @return true valid date format, false invalid date format
     * 
     */
    public static boolean validate(String date) {
        Date validDate;

        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        for (SimpleDateFormat sdf : DATE_FORMATS1) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        return false;
    }

    /**
     * Convert valid reminder date input into date format Optional to contain
     * time of the day in hour and mins
     * 
     * @param date
     * @return the date in valid date format
     * @throws IllegalValueException
     */
    // @@author A0131813R

    public static Date parseDateTime(String date) throws IllegalValueException {
        Date validDate;
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        throw new IllegalValueException(INVALID_FORMAT);
    }

    public static boolean hasPassed(Date date) {
        Date today = Calendar.getInstance().getTime();

        if (date.before(today)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid task reminder.
     */
    public static boolean isValidDate(String test) {
        if (validate(test) || test.equals("") ||test.contains("today") || test.contains("tomorrow")||test.contains("mon")||test.contains("tue")||test.contains("wed")||test.contains("thu")||test.contains("fri")||test.contains("sat")||test.contains("sun"))
            return true;
        else
            return false;
    }

    /**
     * Convert today's date into date format Must contain time of the day in
     * hour and mins
     * 
     * @param string
     *            "tomorrow"
     * @return tomorrow in valid date format
     */

    public static String FixedTime(String date) throws IllegalValueException {
        String[] timeparts = date.split(" ");
        Date today = new Date();
        String strDate;
        int dayIndex;
        if (date.contains("today"))
            strDate = new SimpleDateFormat("d-MM-yyyy").format(new Date());
        else if (date.contains("tomorrow")) {
            new Date(today.getTime() + TimeUnit.DAYS.toMillis(1));
            strDate = new SimpleDateFormat("d-MM-yyyy").format(today.getTime() + TimeUnit.DAYS.toMillis(1));
        } else if (date.contains("mon") || date.contains("tue") || date.contains("wed") || date.contains("thu")
                || date.contains("fri") || date.contains("sat") || date.contains("sun")) {
            dayIndex = DayOfTheWeek(date);
            strDate = new SimpleDateFormat("d-MM-yyyy").format(today.getTime() + TimeUnit.DAYS.toMillis(dayIndex));
        } else
            throw new IllegalValueException(INVALID_FORMAT);
        return ConcatDateTime(strDate, date);

    }

    private static String ConcatDateTime(String strDate, String date) throws IllegalValueException {
        String[] timeparts = date.split(" ");
        String part1 = strDate;
        String part2 = strDate;
        if (timeparts.length != 1) {
            part2 = timeparts[1];
            if (timeparts.length == 3) {
                String part3 = timeparts[2];
                part2 = part2.concat(" " + part3);
            }
            part2 = strDate.concat(" " + part2);
        } else
            throw new IllegalValueException(INVALID_TIME);
        return part2;
    }

    public static int DayOfTheWeek(String date) {
        int dayindex = 0;
        int diff = 0;
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (date.contains("mon"))
            dayindex = Calendar.MONDAY;
        else if (date.contains("tue"))
            dayindex = Calendar.TUESDAY;
        else if (date.contains("wed"))
            dayindex = Calendar.WEDNESDAY;
        else if (date.contains("thu"))
            dayindex = Calendar.THURSDAY;
        else if (date.contains("fri"))
            dayindex = Calendar.FRIDAY;
        else if (date.contains("sat"))
            dayindex = Calendar.SATURDAY;
        else if (date.contains("sun"))
            dayindex = Calendar.SUNDAY;
        return diff = (today > dayindex) ? (7 - (today - dayindex)) : (dayindex - today);
    }

    public static Date DueDateConvert(String date) throws IllegalValueException {
        if (date.split(" ").length == 1) {
            date = date.concat(" 2359");
        }
        if (date.contains("today") || date.contains("tomorrow") || date.contains("mon") || date.contains("tue")
                || date.contains("wed") || date.contains("thu") || date.contains("fri") || date.contains("sat")
                || date.contains("sun")) { // allow user to key in "today"
                                           // instead of today's date
            date = FixedTime(date);
        }
        Date taskDate = parseDateTime(date);
        return taskDate;
    }

    public static Date FixedDateConvert(String date) throws IllegalValueException {
        if (date.contains("today") || date.contains("tomorrow") || date.contains("mon") || date.contains("tue")
                || date.contains("wed") || date.contains("thu") || date.contains("fri") || date.contains("sat")
                || date.contains("sun")) { // allow user to key in "today"
                                           // instead of today's date
            date = FixedTime(date);
        }
        Date taskDate = parseDateTime(date);
        return taskDate;
    }

    public static Calendar EndDateTime(Date date) throws IllegalValueException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal;

    }

    // these following methods used only for testing purposes only.

    /**
     * Convert a given calendar object into a string format
     * 
     * @param string
     *            ""
     * @return tomorrow in valid date format
     */

    public String outputDateTimeAsString(Calendar dateTime, String format) {
        assert (isValidFormat(format));

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(dateTime.getTime());
    }

    // @@author A0125680H
    /**
     * Checks whether the format entered will be accepted by LifeKeeper
     * 
     * @param format
     * @return boolean indicating whether format is accepted.
     */
    public boolean isValidFormat(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        for (SimpleDateFormat a : DATE_FORMATS) {
            if (a.equals(formatter)) {
                return true;
            }
        }
        return false;
    }
    /*
     * public static String everyMonth(String date) throws IllegalValueException
     * { String[] recurday = date.split(" ", 2); String day = recurday[0];
     * String months = ""; Calendar cal = Calendar.getInstance(); if
     * (recurday.length != 1) { int month = cal.get(Calendar.MONTH); if (month <
     * 10) months = "0" + month; else months = "" + month; int year =
     * cal.get(Calendar.YEAR); day = day.concat("-" + months + "-" + year + " "
     * + recurday[1]); } else throw new IllegalValueException(INVALID_TIME);
     * return day; }
     * 
     * public static String everyYear(String date) throws IllegalValueException
     * { String[] recurday = date.split(" ", 2); if (recurday.length == 1) throw
     * new IllegalValueException(INVALID_TIME); String day = recurday[0];
     * Calendar cal = Calendar.getInstance(); int year = cal.get(Calendar.YEAR);
     * day = day.concat("-" + year + " " + recurday[1]); return day; }
     */

    public static boolean recurValidDate(String date) {
        Date validDate;

        for (SimpleDateFormat sdf : TIME_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        return false;
    }
    
    public static Calendar setDate(String date) throws IllegalValueException {
        String[] recur = date.split(" ", 2);
        String recurfreq = recur[0];
        if (!DateUtil.isValidDate(date)) {
            throw new IllegalValueException(INVALID_FORMAT);
        }
        if (recur.length != 1) {

            if (recurfreq.contains("day")) {
                date = "today " + recur[1];
            }

            if (!date.equals("")) {
                Date taskDate = DateUtil.FixedDateConvert(date);
                if (!DateUtil.isValidDate(date)) {
                    throw new IllegalValueException(INVALID_FORMAT);
                }
                if (taskDate == null) {
                    assert false : "Date should not be null";
                } /*
                   * else if (DateUtil.hasPassed(taskDate)) { throw new
                   * IllegalValueException(MESSAGE_REMINDER_INVALID);
                   */

                Calendar cal = Calendar.getInstance();
                cal.setTime(taskDate);
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.SECOND, 0);
            
            return cal;}

        }
        throw new IllegalValueException(INVALID_FORMAT);
    }
}
