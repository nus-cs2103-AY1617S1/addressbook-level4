//@@author A0093896H
package seedu.todo.model.qualifiers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.task.ReadOnlyTask;

/**
 *  A qualifier that filters tasks if they are to be done after
 * a certain datetime
 */
public class AfterDateQualifier implements Qualifier{
    private LocalDateTime datetime;
    private SearchCompletedOption option;
    
    public AfterDateQualifier(LocalDateTime datetime, SearchCompletedOption option) {
        this.datetime = datetime;
        this.option = option;
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
        
        boolean taskIsAfter = onAfter || byAfter; //true if either the starting date or ending is after
        
        if (option == SearchCompletedOption.ALL) {
            return taskIsAfter;
        } else if (option == SearchCompletedOption.DONE) {
            return taskIsAfter && task.getCompletion().isCompleted();
        } else {
            return taskIsAfter && !task.getCompletion().isCompleted();
        }
    }

    @Override
    public String toString() {
        return "datetime=" + datetime.toString();
    }
}
