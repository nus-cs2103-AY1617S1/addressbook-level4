package seedu.todoList.model.task;

import seedu.todoList.commons.util.CollectionUtil;
import seedu.todoList.model.task.attributes.Name;
import seedu.todoList.model.task.attributes.Date;
import seedu.todoList.model.task.attributes.EndDate;
import seedu.todoList.model.task.attributes.EndTime;
import seedu.todoList.model.task.attributes.StartTime;

/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event extends Task implements ReadOnlyTask {

<<<<<<< HEAD
	private Date date;
	private EndDate endDate;
=======
	private Date startDate;
	private Date endDate;
>>>>>>> a6d44dc7e69055ef975aa340b5da8a8ec532aa33
    private StartTime startTime;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
<<<<<<< HEAD
    public Event(Name name, Date date, EndDate endDate, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(name, date, startTime, endTime);
        super.name = name;
        this.date = date;
=======
    public Event(Name name, Date startDate, Date endDate, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(name, startDate, endDate, startTime, endTime);
        super.name = name;
        this.startDate = startDate;
>>>>>>> a6d44dc7e69055ef975aa340b5da8a8ec532aa33
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Copy constructor.
     */
    public Event(Event source) {
<<<<<<< HEAD
        this(source.getName(), source.getDate(), source.getEndDate(), source.getStartTime(), source.getEndTime());
=======
        this(source.getName(), source.getStartDate(), source,getEndDate(), source.getStartTime(), source.getEndTime());
>>>>>>> a6d44dc7e69055ef975aa340b5da8a8ec532aa33
    }
    
    public Event(ReadOnlyTask source) {
    	this((Event) source);
    };

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    
    public EndDate getEndDate() {
        return endDate;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                && super.name.equals(((Event) other).getName())
                && this.date.equals(((Event) other).getDate())
                && this.endDate.equals(((Event) other).getEndDate())
				&& this.startTime.equals(((Event) other).getStartTime())
				&& this.endTime.equals(((Event) other).getEndTime()));

    }

    @Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" StartTime: ")
                .append(getStartTime())
                .append(" EndTime: ")
                .append(getEndTime());
        return builder.toString();
    }

	public Event getEvent() {
		// EVENT Auto-generated method stub
		return null;
	}

}