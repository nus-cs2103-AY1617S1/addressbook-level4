package tars.model.task;

import tars.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in tars.
 */
public class Priority {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be h / m / l";
    public static final String PRIORITY_VALIDATION_REGEX = "[\\p{Lower} ]+";

    public static final String PRIORITY_HIGH = "high";
    public static final String PRIORITY_MEDIUM = "medium";
    public static final String PRIORITY_LOW = "low";

    public static final String PRIORITY_H = "h";
    public static final String PRIORITY_M = "m";
    public static final String PRIORITY_L = "l";

    private String priorityLevel;

    /**
     * Validates given task priority level.
     *
     * @throws IllegalValueException
     *             if given priority level string is invalid.
     */
    public Priority(String priorityLevel) throws IllegalValueException {
        assert priorityLevel != null;
        priorityLevel = priorityLevel.trim();
        if (!isValidPriorityLevel(priorityLevel)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.priorityLevel = priorityLevel;
    }

    /**
     * Returns true if a given string is a valid task priority level.
     */
    public static boolean isValidPriorityLevel(String level) {
        return level.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String level = "";

        switch (priorityLevel) {
        case PRIORITY_H:
            level = PRIORITY_HIGH;
            break;
        case PRIORITY_M:
            level = PRIORITY_MEDIUM;
            break;
        case PRIORITY_L:
            level = PRIORITY_LOW;
            break;
        }

        return level;
    }

    public void setLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

}
