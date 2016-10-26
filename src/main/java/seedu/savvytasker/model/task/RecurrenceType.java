//@@author A0139916U
package seedu.savvytasker.model.task;

/**
 * This enum represents the type of recurrence of a recurring task.
 */
public enum RecurrenceType {
    /**
     * Specifies no recurrence i.e. the task is a one-time task.
     */
    None,
    /**
     * Specifies daily recurrence.
     */
    Daily,
    /**
     * Specifies weekly recurrence.
     */
    Weekly,
    /**
     * Specifies monthly recurrence.
     */
    Monthly,
    /**
     * Specifies yearly recurrence.
     */
    Yearly;     
    
    /**
     * Gets a RecurrenceType enum object from its enum name, ignoring cases.
     * @param name the name of the RecurrenceType enum object
     * @return the corresponding RecurrenceType enum object
     */
    public static RecurrenceType valueOfIgnoreCase(String name) {
        for (RecurrenceType type : RecurrenceType.values()) {
            if (type.toString().equalsIgnoreCase(name))
                return type;
        }
        
        throw new IllegalArgumentException("Unknown recurrence type: " + name);
    }
}
