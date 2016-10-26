//@@author A0138967J
package seedu.todo.model.qualifiers;

import java.time.LocalDateTime;

import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.ReadOnlyTask;

public class TodayDateQualifier implements Qualifier{
    private LocalDateTime datetime;

    public TodayDateQualifier(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        if (task.getOnDate().getDate() != null && task.getByDate().getDate() != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), 
                        task.getOnDate().getTime());
            LocalDateTime byDateTime = DateTimeUtil.combineLocalDateAndTime(task.getByDate().getDate(),
                        task.getByDate().getTime());
            boolean byTodayCheck = true;
            boolean onTodayCheck = true;
            onTodayCheck = onDateTime.toLocalDate().equals(datetime.toLocalDate()) || onDateTime.toLocalDate().isBefore(datetime.toLocalDate());          
            byTodayCheck = byDateTime.toLocalDate().equals(datetime.toLocalDate()) || byDateTime.toLocalDate().isAfter(datetime.toLocalDate());
            
            return byTodayCheck || onTodayCheck;
        } else if (task.getByDate().getDate() != null) {
            LocalDateTime byDateTime = DateTimeUtil.combineLocalDateAndTime(task.getByDate().getDate(),
                    task.getByDate().getTime());
            return byDateTime.toLocalDate().equals(datetime.toLocalDate()) || byDateTime.toLocalDate().isAfter(datetime.toLocalDate());
        } else if (task.getOnDate().getDate() != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(),
                    task.getOnDate().getTime());
            return onDateTime.toLocalDate().equals(datetime.toLocalDate()) || onDateTime.toLocalDate().isBefore(datetime.toLocalDate());
        } else {
            return false;
        }
        
    }

    @Override
    public String toString() {
        return "datetime=" + datetime.toString();
    }
}
