package seedu.address.model.item;


public interface ReadOnlyFloatingTask {

    Name getName();
    Priority getPriorityValue();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyFloatingTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().name.equals(this.getName().name) // state checks here onwards
                && other.getPriorityValue().priorityValue.equals(this.getPriorityValue().priorityValue));
    }
    
    /**
     * Formats the floating task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName().name)
                .append(" Rank: ")
                .append(getPriorityValue().priorityValue);
        return builder.toString();
    }
}
