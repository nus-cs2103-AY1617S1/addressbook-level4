package seedu.task.model.task;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Pattern;

import seedu.task.commons.exceptions.IllegalValueException;

import java.util.Objects;
import java.util.regex.Matcher;

/**
 * Represents a task's time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time{

    //@@ author A0139860X
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time parameters should be in the given order: \"DayOfWeek, Date, Time\"\n"
          + "Parameter formats:\n"
          + "\tDayOfWeek: thursday, Thursday, THURSDAY, thu, Thur, THURS\n"
          + "\t\t    Date: 01.01.2011, 1.1.2011\n"
          + "\t\t    Time: 9:11, 09:11\n"
          + "You need at least one parameter to specify the time.\n"
          + "DayOfWeek is ignored if Date is specified.";
    public static final String TIME_VALIDATION_REGEX = "^\\s*$" // Empty String
            + "|^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$" // 24H Time
            // Day Of Week and Optional 24H Time
            + "|^(?i)(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?"
            + "(,? ([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])?$"
            // Day of Week (Opt), Date and 24H Time (Opt)
            + "|^((?i)(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?,? )?"
            + "([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1]).([1-9]|0[1-9]|1[0-2]).2\\d{3}"
            + "(,? ([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])?$";


    private LocalDateTime value;

    //public String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time is invalid.
     */
    public Time(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }

        final Pattern DATETIME24H_FORMAT = Pattern.compile("^((?i)(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?,? )?"
                                                            + "(?<Day>[1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1]).(?<Month>[1-9]|0[1-9]|1[0-2]).(?<Year>2\\d{3})"
                                                            + ",? (?<Hour>[0-9]|0[0-9]|1[0-9]|2[0-3]):(?<Minute>[0-5][0-9])$");
        final Pattern DAYTIME24H_FORMAT = Pattern.compile("(?i)((?<Day>(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?)),? "
                                                        + "(?<Hour>([0-9]|0[0-9]|1[0-9]|2[0-3])):(?<Minute>[0-5][0-9])");
        final Pattern TIME24H_FORMAT = Pattern.compile("(?<Hour>[0-9]|0[0-9]|1[0-9]|2[0-3]):(?<Minute>[0-5][0-9])");
        final Pattern DAY_FORMAT = Pattern.compile("(?i)(?<Day>(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?)");
        final Pattern DATE_FORMAT = Pattern.compile("^((?i)(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?,? )?"
                                                  + "(?<Day>([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1])).(?<Month>([1-9]|0[1-9]|1[0-2])).(?<Year>2\\d{3})$");

        final Matcher matcher_DATETIME24H = DATETIME24H_FORMAT.matcher(time);
        final Matcher matcher_DAYTIME24H = DAYTIME24H_FORMAT.matcher(time);
        final Matcher matcher_TIME24H = TIME24H_FORMAT.matcher(time);
        final Matcher matcher_DAY = DAY_FORMAT.matcher(time);
        final Matcher matcher_DATE = DATE_FORMAT.matcher(time);

        
        if (matcher_DATETIME24H.matches()) {
            Integer day = Integer.parseInt(matcher_DATETIME24H.group("Day"));
            Integer month = Integer.parseInt(matcher_DATETIME24H.group("Month"));
            Integer year = Integer.parseInt(matcher_DATETIME24H.group("Year"));
            Integer hour = Integer.parseInt(matcher_DATETIME24H.group("Hour"));
            Integer minute = Integer.parseInt(matcher_DATETIME24H.group("Minute"));
            this.value = LocalDateTime.of(year, month, day, hour, minute);
        }
        
        if (matcher_DATE.matches()) {
            Integer day = Integer.parseInt(matcher_DATE.group("Day"));
            Integer month = Integer.parseInt(matcher_DATE.group("Month"));
            Integer year = Integer.parseInt(matcher_DATE.group("Year"));
            this.value = LocalDateTime.of(year, month, day, 0, 0);
        }

        else if (matcher_DAYTIME24H.matches()) {
            DayOfWeek dayEnum = convertToDayEnum(matcher_DAYTIME24H.group("Day"));
            Integer hour = Integer.parseInt(matcher_DAYTIME24H.group("Hour"));
            Integer minute = Integer.parseInt(matcher_DAYTIME24H.group("Minute"));
            LocalDateTime input = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute));
            this.value = input.with((input.isBefore(LocalDateTime.now())) ? TemporalAdjusters.next(dayEnum)
                                                                          : TemporalAdjusters.nextOrSame(dayEnum));
        }

        else if (matcher_DAY.matches()) {
            DayOfWeek dayEnum = convertToDayEnum(matcher_DAY.group("Day"));
            this.value = LocalDateTime.now().with(TemporalAdjusters.next(dayEnum));
        }

        else if (matcher_TIME24H.matches()) {

    	   Integer hour = Integer.parseInt(matcher_TIME24H.group("Hour"));
    	   Integer minute = Integer.parseInt(matcher_TIME24H.group("Minute"));
    	   this.value = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute));
       }


    }

    public boolean isBefore(Time other) {
    	if (this.value == null || other.value == null)
    		return false;

    	if (this.value.isBefore(other.value))
    		return true;

		return false;
    }

    /**
     * Returns if a given string is a valid task time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return (value != null) ? value.getDayOfWeek().toString() 
                + ", " + value.toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.YYYY"))
                + " " + value.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        		: "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && (this.value != null) ? this.value.equals(((Time) other).value) // state check
                        : ((Time) other).value == null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    
    private DayOfWeek convertToDayEnum(String day) {
    	assert day != null;
    	switch (day.toLowerCase().substring(0, Math.min(day.length(), 3))) {
		case "sun":
			return DayOfWeek.SUNDAY;
		case "mon":
			return DayOfWeek.MONDAY;
		case "tue":
			return DayOfWeek.TUESDAY;
		case "wed":
			return DayOfWeek.WEDNESDAY;
		case "thu":
			return DayOfWeek.THURSDAY;
		case "fri":
			return DayOfWeek.FRIDAY;
		case "sat":
			return DayOfWeek.SATURDAY;
		}
		return null;

    }

}
