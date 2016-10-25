package teamfour.tasc.model.history;

/**
 * An object that can be reverted to past states.
 * Must be constructed as a deep copy.
 * 
 * @param <T> The class of the item
 */
public interface HistoryItem<T> {
    /** creates the state of this object as a deep copy */
    T createStateAsDeepCopy();
}
