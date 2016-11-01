//@@author A0138967J
package seedu.todo.model.qualifiers;

import java.time.LocalDateTime;

import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.ReadOnlyTask;

public class WeekDateQualifier implements Qualifier{
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    public WeekDateQualifier(LocalDateTime datetime) {
        this.startDatetime = datetime.plusDays(1);
        this.endDatetime = datetime.plusWeeks(1);
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        if (task.getOnDate().getDate() != null && task.getByDate().getDate() != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), 
                        task.getOnDate().getTime());
            LocalDateTime byDateTime = DateTimeUtil.combineLocalDateAndTime(task.getByDate().getDate(),
                        task.getByDate().getTime());
            boolean byWeekCheck = true;
            boolean onWeekCheck = true;
            boolean intermediateCheck = onDateTime.toLocalDate().isBefore(endDatetime.toLocalDate()) && onDateTime.toLocalDate().isAfter(startDatetime.toLocalDate());
            onWeekCheck = onDateTime.toLocalDate().equals(endDatetime.toLocalDate()) || intermediateCheck;          
            byWeekCheck = byDateTime.toLocalDate().equals(startDatetime.toLocalDate()) || byDateTime.toLocalDate().isAfter(startDatetime.toLocalDate());
            
            return byWeekCheck || onWeekCheck;
        } else if (task.getByDate().getDate() != null) {
            LocalDateTime byDateTime = DateTimeUtil.combineLocalDateAndTime(task.getByDate().getDate(),
                    task.getByDate().getTime());
            return byDateTime.toLocalDate().equals(startDatetime.toLocalDate()) || byDateTime.toLocalDate().isAfter(startDatetime.toLocalDate());
        } else if (task.getOnDate().getDate() != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(),
                    task.getOnDate().getTime());
            boolean intermediateCheck = onDateTime.toLocalDate().isBefore(endDatetime.toLocalDate()) && onDateTime.toLocalDate().isAfter(startDatetime.toLocalDate());
            return onDateTime.toLocalDate().equals(endDatetime.toLocalDate()) || intermediateCheck;
        } else {
            return false;
        }
        
    }

    @Override
    public String toString() {
        return "startDatetime=" + startDatetime.toString();
    }
}
