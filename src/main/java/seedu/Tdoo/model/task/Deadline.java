package seedu.Tdoo.model.task;

import seedu.Tdoo.commons.util.CollectionUtil;
import seedu.Tdoo.model.task.attributes.EndTime;
import seedu.Tdoo.model.task.attributes.Name;
import seedu.Tdoo.model.task.attributes.StartDate;

/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Deadline extends Task implements ReadOnlyTask {

	private StartDate date;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Deadline(Name name, StartDate date, EndTime endTime, String isDone) {
        assert !CollectionUtil.isAnyNull(name, date, endTime);
        super.name = name;
        this.date = date;
        this.endTime = endTime;
        this.isDone = isDone;
    }

    /**
     * Copy constructor.
     */
    public Deadline(Deadline source) {
        this(source.getName(), source.getStartDate(), source.getEndTime(), source.getDone());
    }
    
    public Deadline(ReadOnlyTask source) {
    	this((Deadline) source);
    };

    public StartDate getStartDate() {
        return date;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && super.name.equals(((Deadline) other).getName())
                && this.date.equals(((Deadline) other).getStartDate())
				&& this.endTime.equals(((Deadline) other).getEndTime()));

    }

    @Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nDate: ")
                .append(getStartDate())
                .append("\nEndTime: ")
                .append(getEndTime());
        return builder.toString();
    }

	public Deadline getDeadline() {
		// DEADLINE Auto-generated method stub
		return null;
	}
}
