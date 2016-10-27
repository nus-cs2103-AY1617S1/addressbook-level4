package seedu.forgetmenot.model.task;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;

/**
 * Represents a task's done status in the task manager.
 * @@author A0139198N
 */
public class Done {

    public Boolean value;

    /**
     * 
     * Validates given done status
     *
     * @throws IllegalValueException if given done status is invalid. i.e not true or not false
     */
    public Done(Boolean done) throws IllegalValueException {
        this.value = done;
    }
    
    public void setDone(Boolean done) {
        this.value = done;
    }
    
    public Boolean getDoneValue() {
    	return this.value;
    }
    
    @Override
    public String toString() {
        if(this.value)
            return "done";
        else
            return "not done";
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Done // instanceof handles nulls
                && this.value.equals(((Done) other).value)); // state check
    }

}
