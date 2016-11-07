package seedu.dailyplanner.model.task;

import java.util.Objects;

import seedu.dailyplanner.commons.util.CollectionUtil;
import seedu.dailyplanner.commons.util.DateUtil;
import seedu.dailyplanner.commons.util.StringUtil;
import seedu.dailyplanner.model.category.UniqueCategoryList;

/**
 * Represents a Person in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

	private String taskName;
	private DateTime start;
	private DateTime end;
	private boolean isComplete;
	private boolean isPinned;
	private UniqueCategoryList tags;

	/**
	 * Every field must be present and not null.
	 */
	public Task(String name, DateTime start, DateTime end, boolean isComplete, boolean isPinned, UniqueCategoryList tags) {
	    assert !CollectionUtil.isAnyNull(name, start, end, isComplete, isPinned, tags);
		this.taskName = name;
		this.start = start;
		this.end = end;
		this.tags = new UniqueCategoryList(tags); // protect internal tags from
		// changes in the arg list
		this.isComplete = isComplete;
		this.isPinned = isPinned;
	}

	/**
	 * Copy constructor.
	 */
	public Task(ReadOnlyTask source) {
		this(source.getName(), source.getStart(), source.getEnd(), source.isComplete(), source.isPinned(),
				source.getCats());
	}

	@Override
	public void setName(String name) {
		this.taskName = name;
	}

	@Override
	public void setStart(DateTime startDate) {
		this.start = startDate;
	}

	@Override
	public void setEnd(DateTime endDate) {
		this.end = endDate;
	}

	@Override
	public void markAsComplete() {
		this.isComplete = true;
	}

	@Override
	public void markAsNotComplete() {
		this.isComplete = false;
	}

	public void pin() {
		this.isPinned = true;
	}

	public void unpin() {
		this.isPinned = false;
	}

	public static String calculateDueStatus(DateTime end) {
		// if there is no end date, return empty string
		if (end.getDate().equals("")) {
			return "";
		}
		DateTime nowAsDateTime = DateUtil.nowAsDateTime();
		
		// if end date is strictly before current date
		if (end.getDate().compareTo(nowAsDateTime.getDate()) < 0 ) {
			return "OVERDUE";
		}
		
		// if end date is today or later but there is no end time
		if (end.getTime().toString().equals("")) {
			return "";
		}
		
		// if end date is same as current date
		else if (end.getDate().equals(nowAsDateTime.getDate())) {
			// if end time is before or equal to current time
			if (end.getTime().compareTo(nowAsDateTime.getTime()) <= 0) {
				return "OVERDUE";
			} else {
				int endHour = DateUtil.convertTo24HrFormat(end.getTime());
				int nowHour = DateUtil.convertTo24HrFormat(nowAsDateTime.getTime());
				int overDueHours = endHour - nowHour;
				if (overDueHours <= 3) {
					return "DUE SOON";
				}
			}
		}
		return "";
	}

	@Override
	public String getName() {
		return taskName;
	}

	@Override
	public DateTime getStart() {
		return start;
	}

	@Override
	public DateTime getEnd() {
		return end;
	}

	@Override
	public String getCompletion() {
		return (isComplete) ? "COMPLETE" : "NOT COMPLETE";
	}

	@Override
	public String getDueStatus() {
		return calculateDueStatus(end);
	}

	@Override
	public boolean isComplete() {
		return isComplete;
	}

	@Override
	public void setCompletion(boolean completion) {
		isComplete = completion;
	}

	public boolean isPinned() {
		return isPinned;
	}

	@Override
	public UniqueCategoryList getCats() {
		return new UniqueCategoryList(tags);
	}

	/**
	 * Replaces this task's tags with the tags in the argument tag list.
	 */
	public void setCategories(UniqueCategoryList replacement) {
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
		return Objects.hash(taskName, start, end, isComplete, isPinned, tags);
	}

	@Override
	public String toString() {
		return getAsText();
	}

	@Override
	public int compareTo(Task o) {
		if (!start.equals(o.start)) {
			return start.compareTo(o.start);
		} else if (!end.equals(o.end)) {
			return end.compareTo(o.end);
		} else
			return taskName.compareTo(o.taskName);
	}

}
