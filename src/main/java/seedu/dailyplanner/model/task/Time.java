package seedu.dailyplanner.model.task;

//@@author A0146749N

/**
 * Represents a task's start or end time in the daily planner.
 */
public class Time implements Comparable<Time> {

    public final String m_value;
    public final int m_hour;
    public final int m_minute;
    public final String m_meridiem; // am or pm

    /**
     * Guaranteed that value is either an empty string or in the 12 hour format:
     * HH.MMam or HH.MMpm
     */
    public Time(String value) {
	assert value != null;
	m_value = value;
	if (!value.equals("")) {
	    m_hour = Integer.parseInt(value.substring(0, 2));
	    m_minute = Integer.parseInt(value.substring(3, 5));
	    m_meridiem = value.substring(5);
	}
	// default values when time not present
	else {
	    m_hour = 20;
	    m_minute = 0;
	    m_meridiem = "PM";
	}
    }

    @Override
    public String toString() {
	return m_value;
    }

    @Override
    public boolean equals(Object other) {
	return other == this // short circuit if same object
		|| (other instanceof Time // instanceof handles nulls
			&& m_value.equals(((Time) other).m_value)); // state
								    // check
    }

    @Override
    public int hashCode() {
	return m_value.hashCode();
    }

    @Override
    public int compareTo(Time o) {

	if (!m_meridiem.equals(o.m_meridiem)) {
	    if (m_meridiem.equals("AM") && o.m_meridiem.equals("PM")) {
		return -1;
	    } else {
		return 1;
	    }
	}
	if (m_hour != o.m_hour) {
	    if (m_hour == 12) {
		return -1;
	    } else if (o.m_hour == 12) {
		return 1;
	    } else {
		return m_hour - o.m_hour;
	    }
	}
	return m_minute - o.m_minute;
    }
}