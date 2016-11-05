package seedu.dailyplanner.model.task;

/**
 * Represents time in the daily planner. Guarantees: immutable; is
 * valid as declared in {@link #isValidAddress(String)}
 */
public class Time implements Comparable<Time> {

    public final String m_value;
    public final int m_hour;
    public final int m_minute;
    public final String m_meridiem;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException
     *             if given address string is invalid.
     */
    // @@author A0146749N
    public Time(String value) {
	assert value != null;
	m_value = value;
	m_hour = hour;
	m_minute = minute;
	m_meridiem = meridiem;
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
	    if (m_meridiem.equals("am") && o.m_meridiem.equals("pm")) {
		return -1;
	    } else {
		return 1;
	    }
	}
	if (m_hour != o.m_hour) {
	    return m_hour - o.m_hour;
	}
	if (m_minute != o.m_minute) {
	    return m_minute - o.m_minute;
	}
	return 0;
    }
}