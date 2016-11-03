//@@author A0141052Y
package seedu.task.model.history;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

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
     * Adds a new Mutation for an element, treating it as a new mutation for the list
     * 
     * @param index the index of the element in the list
     * @param mutation the Mutation of the element in the list 
     */
    public void addAsNewMutation(int index, Mutation<T> mutation) {
        this.clear();
        this.addMutation(index, mutation);
    }
    
    /**
     * Adds new Mutations for consecutive elements inserted into list. Method will create a new Mutation
     * 
     * @param startIndex the starting position (inclusive)
     * @param newElements the array of elements that are added
     */
    public void addNewElements(int startIndex, T[] newElements) {
        this.clear();
        for (int i = startIndex; i < newElements.length; i++) {
            this.mutationMap.put(i, new Mutation<T>(null, newElements[i - startIndex]));
        }
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
     * Retrieves a mapping of element mutations for the list
     * @return set of element mutations entries for the list
     */
    public Set<Entry<Integer, Mutation<T>>> getMutations() {
        return this.mutationMap.entrySet();
    }
    
    /**
     * Checks if there is any mutations that are recorded.
     * 
     * @return true if there is any mutation, else false
     */
    public boolean hasMutation() {
        return !this.mutationMap.isEmpty();
    }
    
    /**
     * Clears the ListMutation
     */
    public void clear() {
        this.mutationMap.clear();
    }
}
