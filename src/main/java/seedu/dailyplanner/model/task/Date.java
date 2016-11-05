package seedu.dailyplanner.model.task;

import java.text.SimpleDateFormat;

/**
 * Represents a Person's phone number in the address book. Guarantees:
 * immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Date implements Comparable<Date> {

    public final String m_value;
    public final int m_day;
    public final int m_month;
    public final int m_year;
    private java.util.Calendar cal = java.util.Calendar.getInstance();

    // @@author A0140124B
    public Date(String value, int day, int month, int year) {
	assert value != null;
	m_value = value;
	m_day = day;
	m_month = month;
	m_year = year;
    }

    @Override
    public String toString() {
	
	return formattedString;
    }

    private boolean isNextWeek(java.util.Date dateToday, java.util.Date formattedDate) {
	cal.
    }

    private boolean isLastWeek(java.util.Date dateToday, java.util.Date formattedDate) {
	// TODO Auto-generated method stub
	return false;
    }

    private boolean isTomorrow(java.util.Date dateToday, java.util.Date formattedDate) {
	// TODO Auto-generated method stub
	return false;
    }

    private boolean isYesterday(java.util.Date dateToday, java.util.Date formattedDate) {
	// TODO Auto-generated method stub
	return false;
    }

    private boolean isSameDay(java.util.Date dateToday, java.util.Date formattedDate) {
	// TODO Auto-generated method stub
	return false;
    }

    }

    @Override
    public boolean equals(Object other) {
	return other == this // short circuit if same object
		|| (other instanceof Date // instanceof handles nulls
			&& m_value.equals(((Date) other).m_value)); // state
								    // check
    }

    @Override
    public int hashCode() {
	return m_value.hashCode();
    }

    @Override
    public int compareTo(Date o) {
	if (m_year != o.m_year) {
	    return m_year - o.m_year;
	}
	if (m_month != o.m_month) {
	    return m_month - o.m_month;
	}
	if (m_day != o.m_day) {
	    return m_day - o.m_day;
	}
	return 0;
    }
}