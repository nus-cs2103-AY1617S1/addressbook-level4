package seedu.task.model.task;


import seedu.task.commons.exceptions.IllegalValueException;

public class Description {

    public String fullDescription;
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description can't be empty.";
    
    public Description(String description) throws IllegalValueException {
        if (description.equals("Not Set")) {
            this.fullDescription = "";
        }
        else if (!isValidDate(description)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        description = description.trim();
        this.fullDescription = description;
    }
    
    public static boolean isValidDate(String description) {
    	if(description.isEmpty())
    		return false;
        return true;
      }
    
    @Override
    public String toString() {
        return fullDescription;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.fullDescription.equals(((Description) other).fullDescription)); // state check
    }

    @Override
    public int hashCode() {
        return fullDescription.hashCode();
    }
}
