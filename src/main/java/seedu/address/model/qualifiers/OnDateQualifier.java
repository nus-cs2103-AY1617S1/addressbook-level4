package seedu.address.model.qualifiers;

import java.time.LocalDateTime;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDate;

public class OnDateQualifier implements Qualifier{
    private LocalDateTime datetime;

    public OnDateQualifier(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        if (task.getOnDate().getDate() != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), task.getOnDate().getTime());
            return onDateTime.equals(datetime);
        } else {
            return false;
        }
        
    }

    @Override
    public String toString() {
        return "datetime=" + datetime.toString();
    }
}
