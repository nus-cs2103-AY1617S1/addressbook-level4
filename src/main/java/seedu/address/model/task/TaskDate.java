package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskDate {
    public static final int DATE_NOT_PRESENT = -1;
    private long date;
    
    public TaskDate(long date) {
        this.date = date;
    }
        
    public TaskDate(TaskDate copy) {
        this.date = copy.date;
    }
    
    //For sake of testing, not implemeted in main app
    public TaskDate(String inputDate) {
        this.date = new com.joestelmach.natty.Parser().parse(inputDate).get(0).getDates().get(0).getTime();
    }
    
    public String getFormattedDate() {
        if (date == DATE_NOT_PRESENT) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d hh.mma");
        return formatter.format(new Date(date));
    }

    public long getDate() {
        if (date == DATE_NOT_PRESENT) {
            return DATE_NOT_PRESENT;
        }
        return date;
    } 
    
    public Date getParsedDate(){
    	return new Date(date);
    }
}
