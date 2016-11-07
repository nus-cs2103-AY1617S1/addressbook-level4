package seedu.toDoList.model.task;

import seedu.toDoList.commons.exceptions.IllegalValueException;

//@@author A0138717X
public class Priority {
    public static final String MESSAGE_INVALID_PRIORITY_LEVEL = "Priority level must be of value 0, 1, 2 or 3";

    public final int priorityLevel;
    
    private static final int MAX = 3;
    private static final int MIN = 0;

    public Priority(int priorityLevel) throws IllegalValueException {
        if (!isValidPriorityLevel(priorityLevel)) {
            throw new IllegalValueException(MESSAGE_INVALID_PRIORITY_LEVEL);
        }
        this.priorityLevel = priorityLevel;
    }

    public static boolean isValidPriorityLevel(int priorityLevel) {
        if (priorityLevel <= MAX && priorityLevel >= MIN) {
            return true;
        }
        return false;
    }

    public boolean isEmptyPriorityLevel() {
        if (priorityLevel == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(priorityLevel);
    }

    @Override
    public boolean equals(Object other) {
        return other == this 
                || (other instanceof Priority && this.priorityLevel == ((Priority) other).priorityLevel);
    }
}
