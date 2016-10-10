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
    
    public TaskDate(TaskDate copy) {
        this.date = copy.date;
        this.rawInputDate = copy.rawInputDate;
    }
    
    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d hhmma");
        return formatter.format(date);
    }
}
