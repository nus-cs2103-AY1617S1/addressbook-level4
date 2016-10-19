//@@author A0141052Y
package seedu.task.model.history;

import java.util.HashMap;

/**
 * Represents a state change for an indexed List
 * @author Syed Abdullah
 *
 * @param <T>
 */
public class ListMutation<T> {
    private final HashMap<Integer, Mutation<T>> mutationMap;
    
    /**
     * Constructs an empty ListMutation
     */
    public ListMutation() {
        this.mutationMap = new HashMap<Integer, Mutation<T>>();
    }
    
    /**
     * Add a Mutation of a specified index in the list
     * 
     * @param index the index of the element in the list
     * @param mutation the Mutation of the element in the list
     */
    public void addMutation(int index, Mutation<T> mutation) {
        this.mutationMap.put(index, mutation);
    }
    
    /**
     * Mutates an element in the specified index to the next state
     * 
     * @param index the index of the element in the list
     * @param currentState the current state of the element
     * @param nextState the next state of the element
     */
    public void mutateElement(int index, T nextState) {
        Mutation<T> newMutation;
        if (this.mutationMap.containsKey(index)) {
            Mutation<T> currentMutation = this.mutationMap.get(index);
            newMutation = currentMutation.transitionToNextState(nextState);
        } else {
            newMutation = new Mutation<T>(null, nextState);
        }
        this.mutationMap.put(index, newMutation);
    }
    
    /**
     * Clears the ListMutation
     */
    public void clear() {
        this.mutationMap.clear();
    }
}
