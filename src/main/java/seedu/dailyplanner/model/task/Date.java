package seedu.dailyplanner.model.task;

/**
 * Represents a Person's phone number in the address book. Guarantees:
 * immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Date implements Comparable<Date> {

    public final String m_value;
    public final int m_day;
    public final int m_month;
    public final int m_year;

    // @@author A0140124B
    /** Guarantees that value is in the format: DD/MM/YYYY */
    public Date(String value) {
        assert value != null;
        m_value = value;
        if (!value.equals("")) {
            m_day = Integer.parseInt(value.substring(0, 2));
            m_month = Integer.parseInt(value.substring(3, 5));
            m_year = Integer.parseInt(value.substring(6));
        } else {
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