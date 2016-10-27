package seedu.task.model.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
//@@author A0153411W
//@@author A0148083A
public class Task implements ReadOnlyTask {


	private Title title;
	private Description description;
	private StartDate startDate;
	private DueDate dueDate;
	private Interval interval;
	private TimeInterval timeInterval;
	private Status status;
	private UniqueTagList tags;
	
	public Task(Title title, Description description, StartDate startDate, DueDate dueDate, Interval interval,
			TimeInterval timeInterval, Status status, UniqueTagList tags) {
		assert !CollectionUtil.isAnyNull(title, description, startDate, dueDate, interval, timeInterval, tags);
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.interval = interval;
		this.timeInterval = timeInterval;
		this.status = status;
		this.tags = new UniqueTagList(tags); // protect internal tags from
												// changes in the arg list
	}

	/**
	 * Copy constructor.
	 */
	public Task(ReadOnlyTask source) {
		this(source.getTitle(), source.getDescription(), source.getStartDate(), source.getDueDate(),
				source.getInterval(), source.getTimeInterval(), source.getStatus(), source.getTags());
	}

	@Override
	public Title getTitle() {
		return title;
	}

	@Override
	public Description getDescription() {
		return description;
	}

	@Override
	public StartDate getStartDate() {
		return startDate;
	}

	@Override
	public DueDate getDueDate() {
		return dueDate;
	}

	@Override
	public Interval getInterval() {
		return interval;
	}

	@Override
	public TimeInterval getTimeInterval() {
		return timeInterval;
	}

	@Override
	public Status getStatus() {
		return status;
	}
	
	@Override
    public void setStatus(Status status) {
        this.status = status;
    }

	@Override
	public UniqueTagList getTags() {
		return new UniqueTagList(tags);
	}

	/**
	 * Replaces this task's tags with the tags in the argument tag list.
	 */
	public void setTags(UniqueTagList replacement) {
		tags.setTags(replacement);
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ReadOnlyTask // instanceof handles nulls
						&& this.isSameStateAs((ReadOnlyTask) other));
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, description, startDate, dueDate, interval, timeInterval, tags);
	}

	@Override
	public String toString() {
		return getAsText();
	}

	public StartDate getStartDateWithInterval(int days) {
		return new StartDate(addDays(this.getStartDate().startDate, days));
	}

	public DueDate getDueDateWithInterval(int days) {
		return new DueDate(addDays(this.getDueDate().dueDate, days));
	}

	/**
	 * Add days to given date.
	 * 
	 */
	private static Date addDays(Date date, int days) {
		if(date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
}
//@@author


