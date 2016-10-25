package seedu.todo.model.qualifiers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.ReadOnlyTask;

public class AfterDateQualifier implements Qualifier{
    private LocalDateTime datetime;

    public AfterDateQualifier(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        LocalDate onDate = task.getOnDate().getDate(); 
        LocalTime onTime = task.getOnDate().getTime();
        
        LocalDate byDate = task.getByDate().getDate(); 
        LocalTime byTime = task.getByDate().getTime();
        
        boolean onAfter = false;
        boolean byAfter = false;
        
        if (onDate != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(onDate, onTime);
            onAfter = onDateTime.isAfter(datetime);
        }
        
        if (byDate != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(byDate, byTime);
            byAfter = onDateTime.isAfter(datetime);
        }
        
        return onAfter || byAfter;
            
    }

    @Override
    public String toString() {
        return "datetime=" + datetime.toString();
    }
}
