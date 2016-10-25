//@@author A0148096W

package teamfour.tasc.model.history;

/**
 * An object that can be reverted to past states.
 * Must be able to be create deep copies of itself.
 * 
 * @param <T> The class of the item
 */
public interface HistoryItem<T> {
    /** creates the state of this object itself as a deep copy */
    T createStateAsDeepCopy();
}
