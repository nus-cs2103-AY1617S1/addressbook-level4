package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;

public class FloatingTask implements ReadOnlyFloatingTask {
    
    public static final String VARIABLE_CONNECTOR = ". Rank: ";
    public static final String DEFAULT_PRIORITY_VALUE = "5";

    
    protected final Name name;
    private final Priority priorityValue;
    
    public FloatingTask(Name taskName) throws IllegalValueException {
        this(taskName, new Priority(DEFAULT_PRIORITY_VALUE));
    }    
    
    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public FloatingTask(ReadOnlyFloatingTask source) {
        this(source.getName(), source.getPriorityValue());
    }
    
    /**
     * Validates given value.
     *
     * @throws IllegalValueException if given value is invalid.
     */
    public FloatingTask(Name taskName, Priority priorityValue) {
        assert !CollectionUtil.isAnyNull(taskName, priorityValue);
        this.name = taskName;
        this.priorityValue = priorityValue;
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
    public Name getName() {      
        return name;
    }

    @Override
    public Priority getPriorityValue() {
        return priorityValue;
    }  
}
