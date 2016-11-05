package seedu.dailyplanner.model.task;

public class DateTime implements Comparable<DateTime>{

    public final Date m_date;
    public final Time m_time;

    public DateTime(Date date, Time time) {
	m_date = date;
	m_time = time;
    }
    
    public DateTime(Date date) {
	m_date = date;
	m_time = null;
    }

    @Override
    public String toString() {
	return "";
    }

    @Override
    public boolean equals(Object other) {
	return false;
    }

    @Override
    public int hashCode() {
	return m_date.hashCode();
    }

    public int compareTo(Time o) {
	/*if (!m_meridiem.equals(o.m_meridiem)) {
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
	}*/
	return 0;
    }

    @Override
    public int compareTo(DateTime o) {
	// TODO Auto-generated method stub
	return 0;
    }
}
