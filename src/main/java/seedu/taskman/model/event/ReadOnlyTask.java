package seedu.taskman.model.event;

import java.util.Optional;

public interface ReadOnlyTask extends ReadOnlyEvent {
    Status getStatus();
    Optional<Deadline> getDeadline();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getFrequency().equals(this.getFrequency())
                && other.getSchedule().equals(this.getSchedule())
                && other.getDeadline().equals(this.getDeadline())
                && other.getStatus().equals(this.getStatus())
        );
    }

    /**
     * Formats the event as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Status: ")
                .append(getStatus())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Frequency: ")
                .append(getFrequency())
                .append(" Schedule: ")
                .append(getSchedule())
                .append(" Tags: ");

        getTags().forEach(builder::append);
        return builder.toString();
    }
}
