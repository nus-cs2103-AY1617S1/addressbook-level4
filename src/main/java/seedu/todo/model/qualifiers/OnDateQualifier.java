//@@author A0093896H
package seedu.todo.model.qualifiers;

import java.time.LocalDateTime;

import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.task.ReadOnlyTask;

/**
 * A qualifier that filter tasks based on whether their
 * starting date is on a certain date.
 */
public class OnDateQualifier implements Qualifier{
    private LocalDateTime datetime;
    private SearchCompletedOption option;
    
    public OnDateQualifier(LocalDateTime datetime, SearchCompletedOption option) {
        this.datetime = datetime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        boolean taskIsOnDate;
        
        if (task.getOnDate().getDate() != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(task.getOnDate().getDate(), 
                    task.getOnDate().getTime());
            taskIsOnDate = onDateTime.toLocalDate().equals(datetime.toLocalDate());
        } else {
            taskIsOnDate = false;
        }
        
        
        if (option == SearchCompletedOption.ALL) {
            return taskIsOnDate;
        } else if (option == SearchCompletedOption.DONE) {
            return taskIsOnDate && task.getCompletion().isCompleted();
        } else {
            return taskIsOnDate && !task.getCompletion().isCompleted();
        }
        
    }

    @Override
    public String toString() {
        return "datetime=" + datetime.toString();
    }
}
