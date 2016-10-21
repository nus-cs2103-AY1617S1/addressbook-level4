package teamfour.tasc.model.history;

/**
 * An item that can be reverted to past states,
 * must be constructed as a deep copy.
 * 
 * @param <T> The class of the item
 */
public interface HistoryItem<T> {
    /** creates the state this object as a deep copy */
    T createStateAsDeepCopy();
}
