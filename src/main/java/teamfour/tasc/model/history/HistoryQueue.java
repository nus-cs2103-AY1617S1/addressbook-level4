package teamfour.tasc.model.history;

import java.util.LinkedList;

import teamfour.tasc.commons.exceptions.DuplicateDataException;

/**
 * Stores and retrieves HistoryItems.
 * Automatically removes older items when max limit is reached.
 */
public class HistoryQueue<T extends HistoryItem<T>> {

    public static class OutOfHistoryException extends Exception {
        protected OutOfHistoryException() {
            super("This history queue is empty.");
        }
    }
    
    private static final int DEFAULT_MAX_NUM_OF_STATES = 10;
    private final int maxNumOfStates;
    // Uses a LinkedList so that can removeFirst.
    LinkedList<T> historyLL;
    
    public HistoryQueue() {
        this(DEFAULT_MAX_NUM_OF_STATES);
    }
    
    public HistoryQueue(int maxNumOfStates) {
        this.maxNumOfStates = maxNumOfStates;
        historyLL = new LinkedList<T>();
    }
    
    public void pushState(T item) {
        if (historyLL.size() >= maxNumOfStates) {
            historyLL.removeFirst();
        }
        historyLL.addLast(item.createStateAsDeepCopy());
    }
    
    /**
     * Retrieve the latest HistoryItem stored.
     * 
     * @return The HistoryItem object.
     * @throws OutOfHistoryException if the history queue is empty
     */
    public T popState() throws OutOfHistoryException {
        if (historyLL.size() <= 0)
            throw new OutOfHistoryException();
        
        return historyLL.removeLast();
    }
    
}
