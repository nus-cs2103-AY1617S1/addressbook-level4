package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskDate {
    private Date date;
    private String rawInputDate;
    
    public TaskDate(String inputDate, Date date) {
        this.rawInputDate = inputDate;
        this.date = date;
    }
    
    public TaskDate(Date date) {
        this.date = date;
        this.rawInputDate = getRawCommandInput();
    }
    
    public TaskDate(TaskDate copy) {
        this.date = copy.date;
        this.rawInputDate = copy.rawInputDate;
    }
    
    //For sake of testing, not implemeted in main app
    public TaskDate(String inputDate) {
        this.rawInputDate = inputDate;
        this.date = new com.joestelmach.natty.Parser().parse(inputDate).get(0).getDates().get(0);
    }
    
    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d hh.mma",Locale.ENGLISH);
            return "";
        return formatter.format(date);
    }

    public String getRawCommandInput() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM hha",Locale.ENGLISH);
        return formatter.format(date);
    }

    public long getDate() {
        if (date == null)
            return -1;
        return date.getTime();
    } 
    
    public Date getParsedDate(){
    	return date;
    }
}
