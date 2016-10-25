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
        if (task.getOnDate().getDate() != null || task.getByDate().getDate() != null) {
            LocalDateTime onDateTime = datetime;
            LocalDateTime byDateTime = datetime;
            try{
                onDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), 
                        task.getOnDate().getTime());
            } catch(Exception e){}
            try{
                byDateTime = DateTimeUtil.combineLocalDateAndTime(task.getByDate().getDate(),
                        task.getByDate().getTime());
            } catch(Exception e){}
            boolean byTodayCheck = true;
            byTodayCheck = onDateTime.toLocalDate().equals(datetime.toLocalDate()) || onDateTime.toLocalDate().isBefore(datetime.toLocalDate());
            boolean onTodayCheck = true;
            onTodayCheck = byDateTime.toLocalDate().equals(datetime.toLocalDate()) || onDateTime.toLocalDate().isAfter(datetime.toLocalDate());
            
            return byTodayCheck || onTodayCheck;
        } else {
            return false;
        }
        
    }

    @Override
    public String toString() {
        return "datetime=" + datetime.toString();
    }
}
