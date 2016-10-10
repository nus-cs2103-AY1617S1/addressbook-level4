package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d hh.mma");
        return formatter.format(date);
    }

    public String getRawCommandInput() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM hh.mma");
        return formatter.format(date);
    }

    public long getDate() {
        return date.getTime();
    }   
}
