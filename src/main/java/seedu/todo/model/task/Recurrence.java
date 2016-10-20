package seedu.todo.model.task;

import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import seedu.todo.MainApp;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;


public class Recurrence {
    
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    
    public static final Pattern RECURRENCE_END_SAME_DAY = Pattern
            .compile("every (?<fromDateTime>.+) to (?<tillDateTime>[\\d|(p|a)m]+)", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern RECURRENCE_END_DIFF_DAY = Pattern
            .compile("every (?<fromDateTime>.+) to (?<tillDateTime>.+)");
    
    public static final Pattern RECURRENCE_WEEK_DAY = Pattern
            .compile("every (monday|tuesday|wednesday|thursday|friday|saturday|sunday|mon|tue|wed|thurs|fri|sat|sun)");
    
    private String desc;
    
    public Recurrence(String desc) {
        this.desc = desc;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public void updateTaskDate(Task task) {
        Pattern[] rangePatterns = {RECURRENCE_END_SAME_DAY, RECURRENCE_END_DIFF_DAY, RECURRENCE_WEEK_DAY};
        Matcher matcher;
        try {
            for (Pattern p : rangePatterns) {
                matcher = p.matcher(this.desc);
                if (matcher.matches()) {
                    if (p.equals(RECURRENCE_END_SAME_DAY) || p.equals(RECURRENCE_WEEK_DAY)) {
                        task.setOnDate(new TaskDate(this.desc));
                    } else if (p.equals(RECURRENCE_END_DIFF_DAY)) {
                        task.setOnDate(new TaskDate(matcher.group("fromDateTime")));
                        task.setByDate(new TaskDate(matcher.group("tillDateTime")));
                    } 
                }
            }
        } catch (IllegalValueException e) {
            logger.info(e.getMessage());
        }
        
        
        LocalDateTime oldDateTime;
        LocalDateTime newDateTime;
        
        switch (this.desc) {
            case "everyday":
            case "every day":
                oldDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), 
                                                                   task.getOnDate().getTime());
                newDateTime = oldDateTime.plusDays(1);
                task.setOnDate(new TaskDate(newDateTime));
                break;
            case "every week":
                oldDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), 
                                                                   task.getOnDate().getTime());
                newDateTime = oldDateTime.plusWeeks(1);
                task.setOnDate(new TaskDate(newDateTime));
                break;
            case "every year":
                oldDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), 
                                                                   task.getOnDate().getTime());
                newDateTime = oldDateTime.plusYears(1);
                task.setOnDate(new TaskDate(newDateTime));
                break;
        }
    }
    
}
