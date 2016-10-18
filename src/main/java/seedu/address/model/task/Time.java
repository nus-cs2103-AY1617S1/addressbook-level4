package seedu.address.model.task;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Pattern;
import java.util.Objects;
import java.util.regex.Matcher;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should either be in 24H format or given as a Day of the Week\n"
          + "Eg. 9:11, 09:11, thursday, Thursday, THURSDAY, thu, Thur, THURS";
    public static final String TIME_VALIDATION_REGEX = "^\\s*$" // Empty String
            + "|^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$" // 24H Time
            // Day Of Week and Optional 24H Time
            + "|^(?i)(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?"
            + "( ([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])?$";


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

        final Pattern DAYTIME24H_FORMAT = Pattern.compile("(?i)((?<Day>(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?)) (?<Hour>([0-9]|0[0-9]|1[0-9]|2[0-3])):(?<Minute>[0-5][0-9])");
        final Pattern TIME24H_FORMAT = Pattern.compile("(?<Hour>[0-9]|0[0-9]|1[0-9]|2[0-3]):(?<Minute>[0-5][0-9])");
        final Pattern DAY_FORMAT = Pattern.compile("(?i)(?<Day>(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?)");


        final Matcher matcher_DAYTIME24H = DAYTIME24H_FORMAT.matcher(time);
        final Matcher matcher_TIME24H = TIME24H_FORMAT.matcher(time);
        final Matcher matcher_DAY = DAY_FORMAT.matcher(time);



        if (matcher_DAYTIME24H.matches()) {
            DayOfWeek dayEnum = convertToDayEnum(matcher_DAYTIME24H.group("Day"));
            Integer hour = Integer.parseInt(matcher_DAYTIME24H.group("Hour"));
            Integer minute = Integer.parseInt(matcher_DAYTIME24H.group("Minute"));
            this.value = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute)).with(TemporalAdjusters.next(dayEnum));
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

    public boolean isEndBeforeStart(Time other) {
    	if(this.value==null ||other.value == null)
    		return false;

    	if(this.value.isBefore(other.value))
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
        return (value != null) ? value.getDayOfWeek().toString() + " " + value.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
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
