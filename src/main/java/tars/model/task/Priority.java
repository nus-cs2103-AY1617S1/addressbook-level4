package tars.model.task;

import tars.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in tars.
 */
public class Priority implements Comparable<Priority> {


    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be h / m / l";
    public static final String PRIORITY_VALIDATION_REGEX = "^[hml]$";
    
    private static final String PRIORITY_EMPTY_STRING = "";

    private static final String PRIORITY_LEVEL_LOW = "l";
    private static final String PRIORITY_LEVEL_MEDIUM = "m";
    private static final String PRIORITY_LEVEL_HIGH = "h";
    
    private static final int PRIORITY_LEVEL_ONE = 1;
    private static final int PRIORITY_LEVEL_TWO = 2;
    private static final int PRIORITY_LEVEL_THREE = 3;
    
    private static final int PRIORITY_COMPARE_EQUAL = 0;
    private static final int PRIORITY_COMPARE_SMALLER = -1;
    private static final int PRIORITY_COMPARE_BIGGER = 1;

    public String priorityLevel;
    public int level;

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

        switch (this.priorityLevel) {
        case PRIORITY_LEVEL_LOW:
            level = PRIORITY_LEVEL_ONE;
            break;
        case PRIORITY_LEVEL_MEDIUM:
            level = PRIORITY_LEVEL_TWO;
            break;
        case PRIORITY_LEVEL_HIGH:
            level = PRIORITY_LEVEL_THREE;
            break;
        }
    }

    /**
     * Returns true if a given string is a valid task priority level.
     */
    public static boolean isValidPriorityLevel(String level) {
        return level.equals(PRIORITY_EMPTY_STRING) ? true : level.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return priorityLevel;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                        && this.toString().equals(((Priority) other).toString())); // state
                                                                                   // check
    }

    @Override
    public int compareTo(Priority o) {
        if (this.level > o.level) {
            return PRIORITY_COMPARE_BIGGER;
        } else if (this.level < o.level) {
            return PRIORITY_COMPARE_SMALLER;
        } else {
            return PRIORITY_COMPARE_EQUAL;
        }
    }

}
