package seedu.cmdo.model.task;

//@@author A0141006B 
/**
 * Represents if a Task's is done in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDone(String)}
 */
public class Done {

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
}
