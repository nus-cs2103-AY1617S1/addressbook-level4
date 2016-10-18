package seedu.tasklist.model.task;

import java.util.Calendar;

public class Recurring {
    
	private boolean isRecurring;
    private String period;
    
    public Recurring(String input) {
        switch (input) {
            case "daily": case "weekly": case "monthly": case "yearly": 
                isRecurring = true;
                this.period = input;
            default:
                isRecurring = false;
                this.period = "";
        }
    }
    
    public Calendar setRecurringTime(Calendar time) {
    	if (this.isRecurring && !this.period.equals("") && time.getTimeInMillis() < System.currentTimeMillis()) {
            time.add(Calendar.DAY_OF_MONTH, 1);
    	}
        return time;
    }
    
    public boolean isRecurring() {
        return this.isRecurring;
    }
}