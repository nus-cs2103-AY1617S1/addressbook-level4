//@@author A0139916U
package seedu.savvytasker.model.task;

/**
 * This enum represents the priority level of tasks.
 */
public enum PriorityLevel {
    Low,
    Medium,
    High;

    /**
     * Gets a PriorityLevel enum object from its enum name, ignoring cases.
     * @param name the name of the PriorityLevel enum object
     * @return the corresponding PriorityLevel enum object
     */
    public static PriorityLevel valueOfIgnoreCase(String name) {
        for (PriorityLevel type : PriorityLevel.values()) {
            if (type.toString().equalsIgnoreCase(name))
                return type;
        }
        
        throw new IllegalArgumentException("Unknown priority level: " + name);
    }
}
