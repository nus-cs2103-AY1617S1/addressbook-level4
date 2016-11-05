package seedu.dailyplanner.model.task;

import java.text.SimpleDateFormat;

/**
 * Represents a Person's phone number in the address book. Guarantees:
 * immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Date implements Comparable<Date> {

    public final String m_value;
   // public final int m_day;
   // public final int m_month;
   // public final int m_year;
    private java.util.Calendar cal = java.util.Calendar.getInstance();

    // @@author A0140124B
    public Date(String value) {
	assert value != null;
	m_value = value;
	//m_day = day;
	//m_month = month;
	//m_year = year;
    }

    @Override
    public String toString() {
	/*String formattedString = "";
	cal.set(m_year + 1900, m_month, m_day);
	java.util.Date formattedDate = cal.getTime();
	java.util.Date dateToday = new java.util.Date();
	SimpleDateFormat day = new SimpleDateFormat("EEEEE");
	if (isSameDay(dateToday, formattedDate)) {
	    formattedString += "Today, ";
	} else if (isYesterday(dateToday, formattedDate)) {
	    formattedString += "Yesterday, ";
	} else if (isTomorrow(dateToday, formattedDate)) {
	    formattedString += "Tomorrow, ";
	} else if (isLastWeek(dateToday, formattedDate)) {
	    formattedString += "Last " + day.format(formattedDate) + ", ";
	} else if (isNextWeek(dateToday, formattedDate)) {
	    formattedString += "Next " + day.format(formattedDate) + ", ";
	}*/
	return m_value;
    }
/*
    private boolean isNextWeek(java.util.Date dateToday, java.util.Date formattedDate) {
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
    }*/


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
	/*if (m_year != o.m_year) {
	    return m_year - o.m_year;
	}
	if (m_month != o.m_month) {
	    return m_month - o.m_month;
	}
	if (m_day != o.m_day) {
	    return m_day - o.m_day;
	}*/
	return 0;
    }
}