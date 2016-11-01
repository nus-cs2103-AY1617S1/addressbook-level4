//@@author A0093896H
package seedu.todo.model.qualifiers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.task.ReadOnlyTask;

/**
 * A qualifier that filter tasks depending on the tasks starting 
 * and ending date.
 */
public class FromTillDateQualifier implements Qualifier{
  
    private LocalDateTime fromDateTime;
    private LocalDateTime tillDateTime;
    private SearchCompletedOption option;
    
    public FromTillDateQualifier(LocalDateTime fromDateTime, LocalDateTime tillDateTime, 
            SearchCompletedOption option) {
        this.fromDateTime = fromDateTime;
        this.tillDateTime = tillDateTime;
        this.option = option;
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
        
       
        boolean taskIsFromTill = onFrom && byTill; //must be in between the both dates
        
        if (option == SearchCompletedOption.ALL) {
            return taskIsFromTill;
        } else if (option == SearchCompletedOption.DONE) {
            return taskIsFromTill && task.getCompletion().isCompleted();
        } else {
            return taskIsFromTill && !task.getCompletion().isCompleted();
        }
            
    }

    @Override
    public String toString() {
        return "datetime=" + fromDateTime.toString() + " " + tillDateTime.toString();
    }
}
