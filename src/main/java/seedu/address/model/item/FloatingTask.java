package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

public class FloatingTask implements ReadOnlyFloatingTask{
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Task names should be spaces or alphanumeric characters";
    public static final String MESSAGE_VALUE_CONSTRAINTS = "Priority values should be between 1 to 10 inclusive";
    public static final String DEFAULT_PRIORITY_VALUE = "5";
    public static final String VARIABLE_CONNECTOR = ". Rank: ";
    
    protected final String name;
    private final String priorityValue;
    
    public FloatingTask(String taskName) throws IllegalValueException {
        this(taskName, DEFAULT_PRIORITY_VALUE);
    }    
    
    /**
     * Validates given value.
     *
     * @throws IllegalValueException if given value is invalid.
     */
    public FloatingTask(String taskName, String priorityValue) throws IllegalValueException {
        assert priorityValue != null;
        assert taskName != null;
        if (!isValidName(taskName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.name = taskName;
        if (!isValidValue(priorityValue)) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
        this.priorityValue = priorityValue;
    }

    /**
     * Returns true if a given value is a valid priority value.
     */
    public static boolean isValidValue(String priorityValue) {
        return Integer.valueOf(priorityValue) >= 0 && Integer.valueOf(priorityValue) <= 10;
    }
    
    /**
     * Returns true if a given value is a valid priority value.
     */
    public static boolean isValidName(String taskName) {
        return taskName.matches(NAME_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return name + VARIABLE_CONNECTOR + priorityValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FloatingTask // instanceof handles nulls
                && this.priorityValue.equals(((FloatingTask) other).priorityValue)); // state check
    }

    @Override
    public String getName() {      
        return name;
    }

    @Override
    public String getPriorityValue() {
        return priorityValue;
    }  
}
