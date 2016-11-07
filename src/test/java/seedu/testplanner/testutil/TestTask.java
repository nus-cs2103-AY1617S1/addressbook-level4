package seedu.testplanner.testutil;

import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.category.UniqueCategoryList;
import seedu.dailyplanner.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.dailyplanner.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

	private String name;
	private DateTime start;
	private DateTime end;
	private boolean isComplete;
	private boolean isPinned;
	private UniqueCategoryList tags;

	public TestTask() {
		tags = new UniqueCategoryList();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart(DateTime start) {
		this.start = start;
	}

	public void setEnd(DateTime end) {
		this.end = end;
	}

	public void setCompletion(boolean completeStatus) {
		this.isComplete = completeStatus;
	}

	@Override
	public void markAsComplete() {
		isComplete = true;
	}

	@Override
	public void markAsNotComplete() {
		isComplete = false;
	}

	public void setPin(boolean pinStatus) {
		this.isPinned = pinStatus;
	}

	@Override
	public void pin() {
		isPinned = true;
	}

	@Override
	public void unpin() {
		isPinned = false;
	}

	@Override
	public String getName() {
		return name;
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
	public boolean isComplete() {
		return isComplete;
	}

	@Override
	public String getCompletion() {
		return (isComplete) ? "COMPLETE" : "NOT COMPLETE";
	}

	@Override
	public boolean isPinned() {
		return isPinned;
	}

	@Override
	public UniqueCategoryList getCats() {
		return tags;
	}

	@Override
	public String getDueStatus() {
		return Task.calculateDueStatus(end);
	}

    // @@author A0146749N
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName());
        if (!this.getStart().toString().equals(""))
            sb.append(" s/" + this.getStart().toString());
        if (!this.getEnd().toString().equals(""))
            sb.append(" e/" + this.getEnd().toString());
        if (!this.getCats().toSet().isEmpty())
            this.getCats().getInternalList().stream().forEach(s -> sb.append(" c/" + s.tagName));
        return sb.toString();
    }

    public Task asTask() {
        return new Task(name, start, end, isComplete, isPinned, tags);
    }

	@Override
	public String toString() {
		return getAsText();
	}

}
