package seedu.address.model.datetime;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.Duration;

/**
 * struct for LocalDateTime and some extra member vars for recurring events
 * @author Darren
 *
 */
public class DateTime {

    private LocalDateTime ldt;

    // for recurrence
    private boolean isRecurring;
    private Period interval; // in days
    private LocalDateTime endDate;
    
    /**
     * constructor for DateTime without recurrence
     * @param ldt
     */
    DateTime(LocalDateTime ldt) {
        assert ldt != null;

        this.ldt = ldt;
        this.isRecurring = false;
        this.interval = Period.ZERO;
    }
    
    /**
     * constructor for DateTime with recurrence
     * @param ldt
     * @param isRecurring
     * @param interval
     * @param endDate
     */
    DateTime(LocalDateTime ldt, boolean isRecurring, Period interval, LocalDateTime endDate) {
        assert ldt != null;
        assert endDate != null;
               
        this.ldt = ldt;

        if(isRecurring == false) {
            throw new IllegalArgumentException("Wrong constructor for non-recurring event!");
        } else {
	        this.isRecurring = isRecurring;
	        this.interval = interval;
	        this.endDate = endDate;
        }
    }
    
    public LocalDateTime getDateTime() {
        return this.ldt;
    }

    public boolean isRecurring() {
        return this.isRecurring;
    }
    
    public Period getInterval() {
        return this.interval;
    }
    
    public LocalDateTime getEndDate() {
        return this.endDate;
    }
}
