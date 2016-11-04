package seedu.cmdo.model.task;

//@@author A0141006B 
/**
 * Represents if a Task's is done in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDone(String)}
 */
public class Done {

    public static final String MESSAGE_DONE_CONSTRAINTS = "";
    public static final String DONE_VALIDATION_REGEX = "";

    public Boolean value;

    /**
     * Done is false by default.
     */
    public Done(){
        this.value = false;
    }
    
    /**
     * This is for the case where Done needs to take on a previously saved state
     */
    public Done(boolean value) {
    	this.value = value;
    }
    
    /*
     * method to set Done as true upon completion
     */
    public void setDone(){
    	value = true;
    }

    /**
     * Returns true if a given string is a valid done.
     */
    public static boolean isValidDone(String test) {
        return test.matches(DONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
