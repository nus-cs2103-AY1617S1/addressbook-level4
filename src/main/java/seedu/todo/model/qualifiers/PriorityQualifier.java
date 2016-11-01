package seedu.todo.model.qualifiers;

import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.ReadOnlyTask;

//@@author A0121643R

public class PriorityQualifier implements Qualifier {
    
    private Priority priority;
    private SearchCompletedOption option;

    public PriorityQualifier(Priority priority, SearchCompletedOption option) {
        this.priority = priority;
        this.option = option;
    }

    @Override
    public boolean run(ReadOnlyTask task) {

        boolean taskOfPriority = task.getPriority().equals(priority);
        
        if (option == SearchCompletedOption.ALL) {
            return taskOfPriority;
        } else if (option == SearchCompletedOption.DONE) {
            return taskOfPriority && task.getCompletion().isCompleted();
        } else {
            return taskOfPriority && !task.getCompletion().isCompleted();
        }
    }

    @Override
    public String toString() {
        return "priority= " + priority.toString();
    }

}
