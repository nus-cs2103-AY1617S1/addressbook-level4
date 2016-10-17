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
    
    DateTime(LocalDateTime ldt) {
        assert ldt != null;

        this.ldt = ldt;
        this.isRecurring = false;
    }
    
    DateTime(LocalDateTime ldt, boolean isRecurring, Period interval, LocalDateTime endDate) {
        assert ldt != null;
        assert endDate != null;
               
        this.ldt = ldt;

        if(isRecurring == false) {
	        this.isRecurring = false;
	        this.interval = Period.ZERO;
        } else {
	        this.isRecurring = isRecurring;
	        this.interval = interval;
	        this.endDate = endDate;
        }
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
