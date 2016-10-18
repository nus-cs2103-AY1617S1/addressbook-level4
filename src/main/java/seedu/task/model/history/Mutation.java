//@@author A0141052Y
package seedu.task.model.history;

/**
 * Represents an object state change.
 * @author Syed Abdullah
 *
 * @param <T> the object's Class
 */
public class Mutation<T> {
    
    final private T previousState;
    final private T presentState;
    
    /**
     * Constructs a Mutation.
     * 
     * @param previousState the state of the instance before mutation
     * @param presentState the state of the instance after mutation
     */
    public Mutation(T previousState, T presentState) {
        this.previousState = previousState;
        this.presentState = presentState;
    }
    
    /**
     * Get the state before mutation
     * @return state of object before mutation took place
     */
    public T getPreviousState() {
        return previousState;
    }
    
    /**
     * Get the state before mutation
     * @return state of object before mutation took place
     */
    public T getPresentState() {
        return presentState;
    }
}
