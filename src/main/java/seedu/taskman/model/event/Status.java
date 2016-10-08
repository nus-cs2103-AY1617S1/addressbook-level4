package seedu.taskman.model.event;

public class Status {

	public final Boolean completed;

	public Status(String status) {
        completed = false;
	}

	@Override
	public String toString() {
		return String.valueOf(completed);
	}

	@Override
	public int hashCode() {
        return completed.hashCode();
	}
}
