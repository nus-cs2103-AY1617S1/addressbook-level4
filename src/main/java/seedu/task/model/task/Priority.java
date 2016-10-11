package seedu.task.model.task;

public enum Priority {
	LOW("LOW"), MEDIUM("MEDIUM"), HIGH("HIGH");

	private final String text;

	private Priority(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
