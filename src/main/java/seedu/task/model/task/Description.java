package seedu.task.model.task;

public class Description {

    public final String fullDescription;
    
    public Description(String description) {
        description = description.trim();
        this.fullDescription = description;
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
