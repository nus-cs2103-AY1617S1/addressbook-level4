package seedu.todo.model.qualifiers;

import java.time.LocalDate;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.task.ReadOnlyTask;

//@@author A0093896H
/**
 * A qualifier that filter floating tasks.
 */
public class FloatingQualifier implements Qualifier {
    
    private SearchCompletedOption option;
    
    public FloatingQualifier(SearchCompletedOption option) {
        this.option = option;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        LocalDate onDate = task.getOnDate().getDate(); 
        LocalDate byDate = task.getByDate().getDate(); 
        
        boolean isFloating = onDate == null && byDate == null;
     
        if (option == SearchCompletedOption.ALL) {
            return isFloating;
        } else if (option == SearchCompletedOption.DONE) {
            return isFloating && task.getCompletion().isCompleted();
        } else {
            return isFloating && !task.getCompletion().isCompleted();
        }
    }

    @Override
    public String toString() {
        return "floating";
    }
}
