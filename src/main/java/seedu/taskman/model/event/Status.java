package seedu.taskman.model.event;

import com.google.common.base.Objects;

public class Status {

	public final Boolean completed;

	public Status() {
        completed = false;
	}

	@Override
	public String toString() {
		return String.valueOf(completed);
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
