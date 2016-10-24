package seedu.todo.model.qualifiers;

import java.time.LocalDateTime;

import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.TaskDate;

public class OnDateQualifier implements Qualifier{
    private LocalDateTime datetime;

    public OnDateQualifier(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        if (task.getOnDate().getDate() != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), 
                    task.getOnDate().getTime());
            return onDateTime.toLocalDate().equals(datetime.toLocalDate());
        } else {
            return false;
        }
        
    }

    @Override
    public String toString() {
        return "datetime=" + datetime.toString();
    }
}
