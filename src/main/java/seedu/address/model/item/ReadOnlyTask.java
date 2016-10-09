package seedu.address.model.item;

import java.util.Optional;

public interface ReadOnlyTask {

    Name getName();
    Priority getPriorityValue();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().name.equals(this.getName().name) // state checks here onwards
                && other.getPriorityValue() == this.getPriorityValue());
    }
    
    /**
     * Formats the floating task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName().name)
                .append(", Priority: ")
                .append(getPriorityValue());
        return builder.toString();
    }
}
