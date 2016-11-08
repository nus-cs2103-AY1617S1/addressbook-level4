package seedu.dailyplanner.model.task;

// @@author A0140124B

/**
 * Represents a Task's start date or end date in the daily planner.
 */
public class Date implements Comparable<Date> {

    private final String m_value;
    private final int m_day;
    private final int m_month;
    private final int m_year;
   
    /**
     * Guaranteed that value is either an empty string or in the format:
     * DD/MM/YYYY
     */
    public Date(String value) {
	assert value != null;
	m_value = value;
	if (!value.equals("")) {
	    m_day = Integer.parseInt(value.substring(0, 2));
	    m_month = Integer.parseInt(value.substring(3, 5));
	    m_year = Integer.parseInt(value.substring(6));
	} 
	// default values when date not present
	else {
	    m_day = 0;
	    m_month = 0;
	    m_year = 3000;
	}
    }

    @Override
    public String toString() {
	return m_value;
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