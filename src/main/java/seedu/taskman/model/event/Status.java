package seedu.taskman.model.event;

import com.google.common.base.Objects;
import seedu.taskman.commons.exceptions.IllegalValueException;

public class Status {

	public final Boolean completed;

	public Status() {
        completed = false;
	}

	public Status(String booleanString) throws IllegalValueException {
        booleanString = booleanString.trim().toLowerCase();
        if (!booleanString.matches("(complete)|(incomplete)")) {
            throw new IllegalValueException("Status should be 'complete' or 'incomplete'");
        }
        completed = booleanString.equals("complete");
    }

	@Override
	public String toString() {
		return completed ? "Completed" : "Incomplete";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equal(completed, status.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(completed);
    }
}
