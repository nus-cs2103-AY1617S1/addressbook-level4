package seedu.jimi.model.tag;

import seedu.jimi.commons.exceptions.IllegalValueException;

/**
 * 
 * @@author A0143471L
 * 
 * Represents the priority of the tasks & events in Jimi.
 * 
 * Priorities can be classified into: LOW, MED & HIGH
 **/

public class Priority extends Tag {
    
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Priority names should only be: LOW, MED or HIGH. ";
    
    public final static String PRIO_LOW = "LOW";
    public final static String PRIO_MED = "MED";
    public final static String PRIO_HIGH = "HIGH";
    public final static String PRIO_NONE = "None";
    
    /**
     * Instantiate with no priority tag.
     */
    public Priority() {
        this.tagName = PRIO_NONE;
    }
    
    /**
     * Validates given priority name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Priority(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidPriorityName(name)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.tagName = name;
    }
    
    /**
     * Returns true if a given string is a valid priority name.
     */
    public static boolean isValidPriorityName(String test) {
        return test.equals(PRIO_LOW) || test.equals(PRIO_MED) || test.equals(PRIO_HIGH) || test.equals(PRIO_NONE);
    }
}
