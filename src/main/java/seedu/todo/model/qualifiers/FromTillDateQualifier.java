//@@author A0093896H
package seedu.todo.model.qualifiers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.TaskDate;

public class FromTillDateQualifier implements Qualifier{
    private LocalDateTime fromDateTime;
    private LocalDateTime tillDateTime;

    public FromTillDateQualifier(LocalDateTime fromDateTime, LocalDateTime tillDateTime) {
        this.fromDateTime = fromDateTime;
        this.tillDateTime = tillDateTime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        LocalDate fromDate = task.getOnDate().getDate(); 
        LocalTime fromTime = task.getOnDate().getTime();
        
        LocalDate tillDate = task.getByDate().getDate(); 
        LocalTime tillTime = task.getByDate().getTime();
        
        boolean onFrom = false;
        boolean byTill = false;
        
        if (fromDate != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(fromDate, fromTime);
            onFrom = onDateTime.isAfter(fromDateTime);
        }
        
        if (tillDate != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(tillDate, tillTime);
            byTill = onDateTime.isBefore(tillDateTime);
        }
        
        return onFrom && byTill;
            
    }

    @Override
    public String toString() {
        return "datetime=" + fromDateTime.toString() + " " + tillDateTime.toString();
    }
}
