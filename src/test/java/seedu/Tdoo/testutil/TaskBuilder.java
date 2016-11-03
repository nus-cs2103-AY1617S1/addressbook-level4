package seedu.Tdoo.testutil;

import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.model.task.attributes.*;

/**
 *
 */
// @@author A0132157M reused
public class TaskBuilder {

	private TestTask task;

	public TaskBuilder() {
		this.task = new TestTask();
	}

	public TaskBuilder withName(String name) throws IllegalValueException {
		this.task.setName(new Name(name));
		return this;
	}

	public TaskBuilder withStartDate(String date) throws IllegalValueException {
		this.task.setStartDate(new StartDate(date));
		return this;
	}

	public TaskBuilder withEndDate(String date) throws IllegalValueException {
		this.task.setEndDate(date);
		return this;
	}

	public TaskBuilder withDone(String dd) throws IllegalValueException {
		this.task.setDone(dd);
		return this;
	}

	public TaskBuilder withPriority(String priority) throws IllegalValueException {
		this.task.setPriority(priority);
		return this;
	}

	public TestTask build() {
		return this.task;
	}

}
