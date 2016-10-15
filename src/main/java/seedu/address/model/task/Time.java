package seedu.address.model.task;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    							+ "|^(([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])?( (to|-) )?([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$" // Proper 24h representation
    							// 3+ char day representation    
    							+ "|^(?i)(mon|tue(s)?|wed(nes)?|thu(r(s)?)?|fri|sat(ur)?|sun)(day)?$"; 
    

    private LocalDateTime startValue;
    private LocalDateTime endValue;
    private boolean dayAdded = false;
    private boolean startTimeAdded = false;
    
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
        
        final Pattern TIME24H_FORMAT = Pattern.compile("^((?<StartHour>[0-9]|0[0-9]|1[0-9]|2[0-3]):(?<StartMinute>[0-5][0-9]))?( (to|-) )?(?<EndHour>[0-9]|0[0-9]|1[0-9]|2[0-3]):(?<EndMinute>[0-5][0-9])$");
        final Pattern DAY_FORMAT = Pattern.compile("^(?i)(?<Day>(mon|tue|wed|thu|fri|sat|sun))");
        
        
        final Matcher matcher_TIME24H = TIME24H_FORMAT.matcher(time);
        final Matcher matcher_DAY = DAY_FORMAT.matcher(time);
        
       
        if (matcher_DAY.matches()) {
            dayAdded = true;
            DayOfWeek dayEnum = convertToDayEnum(matcher_DAY.group("Day"));
            this.endValue = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(dayEnum));
        }
        else if (matcher_TIME24H.matches()) {
           if (matcher_TIME24H.group("StartHour") != null && matcher_TIME24H.group("StartMinute") != null) {
               Integer startHour = Integer.parseInt(matcher_TIME24H.group("StartHour"));
               Integer startMinute = Integer.parseInt(matcher_TIME24H.group("StartMinute"));
               this.startValue = LocalDateTime.of(LocalDate.now(), LocalTime.of(startHour, startMinute));
               this.startTimeAdded = true;
           }
    	   Integer endHour = Integer.parseInt(matcher_TIME24H.group("EndHour"));
    	   Integer endMinute = Integer.parseInt(matcher_TIME24H.group("EndMinute"));
    	   this.endValue = LocalDateTime.of(LocalDate.now(), LocalTime.of(endHour, endMinute));
       }
       
       
    }

    /**
     * Returns if a given string is a valid person time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return (endValue != null) ? 
        		(dayAdded) ? endValue.getDayOfWeek().toString()
        		: (startTimeAdded) ?  startValue.toLocalTime().toString() + " - " + endValue.toLocalTime().toString()
        		: endValue.toLocalTime().toString() 
        		: "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && (this.startValue != null) ? this.startValue.equals(((Time) other).startValue) // state check
                   : ((Time) other).startValue == null
                && this.endValue.equals(((Time) other).endValue)
                && this.dayAdded == ((Time) other).dayAdded
                && this.startTimeAdded == ((Time) other).startTimeAdded); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(startValue, endValue, dayAdded, startTimeAdded);
    }
    
    private DayOfWeek convertToDayEnum(String day) {
    	assert day != null; 
    	switch (day.toLowerCase()) {
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
