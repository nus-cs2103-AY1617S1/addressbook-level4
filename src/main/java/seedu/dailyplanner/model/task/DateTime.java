package seedu.dailyplanner.model.task;

public class DateTime implements Comparable<DateTime> {
	//@@author A0146749N
	public final Date m_date;
	public final Time m_time;

	public DateTime(Date date, Time time) {
		m_date = date;
		m_time = time;
	}

	@Override
	public String toString() {
		if (m_date.toString().equals(""))
			return "";
		else if (m_time.toString().equals(""))
			return m_date.toString();
		else
			return m_date.toString() + " " + m_time.toString();
	}

	public Date getDate() {
		return m_date;
	}

	public Time getTime() {
		return m_time;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof DateTime // instanceof handles nulls
						&& m_date.equals(((DateTime) other).m_date) && m_time.equals(((DateTime) other).m_time)); // state
		// check
	}

	@Override
	public int hashCode() {
		return m_date.hashCode();
	}

	@Override
	public int compareTo(DateTime o) {
		if (!m_date.equals(o.m_date)) {
			return m_date.compareTo(o.m_date);
		} else {
			return m_time.compareTo(o.m_time);
		}

	}
}
