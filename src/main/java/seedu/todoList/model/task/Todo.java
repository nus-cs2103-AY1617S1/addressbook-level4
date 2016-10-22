package seedu.todoList.model.task;

import seedu.todoList.commons.util.CollectionUtil;
import seedu.todoList.model.task.attributes.Done;
import seedu.todoList.model.task.attributes.StartDate;
import seedu.todoList.model.task.attributes.EndDate;
import seedu.todoList.model.task.attributes.Name;
import seedu.todoList.model.task.attributes.Priority;

/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Todo extends Task implements ReadOnlyTask {
    
    private StartDate startDate;
    private EndDate endDate;
    private Priority priority;
    private Done isDone;

    /**
     * Every field must be present and not null.
     * @param date 
     */
    public Todo(Name name, StartDate startDate, EndDate endDate, Priority priority, Done isDone) {
        assert !CollectionUtil.isAnyNull(name, startDate, endDate, priority);
        super.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.isDone = isDone;
    }

    /**
     * Copy constructor.
     */
    public Todo(Todo source) {
        this(source.getName(), source.getStartDate() , source.getEndDate(), source.getPriority(), source.getDone());
    }

    public StartDate getStartDate() {
        return startDate;
    }
    
    public EndDate getEndDate() {
        return endDate;
    }

    public Priority getPriority() {
        return priority;
    }
    
    public Done getDone() {
    	return isDone;
    }
    
    public Todo(ReadOnlyTask source) {
    	this((Todo) source);
    };

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Todo // instanceof handles nulls
                && super.name.equals(((Todo) other).getName()))
                && this.startDate.equals(((Todo) other).getStartDate())
                && this.endDate.equals(((Todo) other).getEndDate())
                && this.priority.equals(((Todo) other).getPriority());
    }

    @Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nStart Date: ")
                .append(getStartDate())
                .append("\nEnd Date: ")
                .append(getEndDate())
                .append("\nPriority: ")
                .append(getPriority());
        return builder.toString();
    }

	public Todo getTodo() {
		// TODO Auto-generated method stub
		return null;
	}

}