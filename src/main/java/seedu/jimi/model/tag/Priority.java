package seedu.jimi.model.tag;

import java.util.Arrays;

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
    public final static String PRIO_LOW = "low";
    public final static String PRIO_MED = "med";
    public final static String PRIO_HIGH = "high";
    public final static String PRIO_NONE = "none";
    

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = 
            "Invalid priority! Valid priority (case-insensitive) names include: \n"
            + "> " + String.join(", ", Arrays.asList(PRIO_NONE, PRIO_LOW, PRIO_MED, PRIO_HIGH));
    
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
        return test.equalsIgnoreCase(PRIO_LOW) 
                || test.equalsIgnoreCase(PRIO_MED) 
                || test.equalsIgnoreCase(PRIO_HIGH)
                || test.equalsIgnoreCase(PRIO_NONE);
    }
}
