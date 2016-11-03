package seedu.Tdoo.testutil;

import seedu.Tdoo.model.task.*;
import seedu.Tdoo.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
// @@author A0132157M reused
public class TestTask implements ReadOnlyTask {

	private Name name;
	private String priority;
	private StartDate startDate;
	private String endDate;
	private String done;

	public TestTask() {
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setStartDate(StartDate sdate) {
		this.startDate = sdate;
	}

	public void setEndDate(String edate) {
		this.endDate = edate;
	}

	public void setDone(String done) {
		this.done = done;
	}

	// @Override
	public String getPriority() {
		return priority;
	}

	@Override
	public Name getName() {
		return name;
	}

	public StartDate getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getDone() {
		return done;
	}

	public String getAddCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("add " + this.getName().name + " ");
		sb.append("p/ " + this.getPriority());
		return sb.toString();
	}

}
