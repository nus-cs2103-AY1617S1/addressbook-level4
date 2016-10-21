package teamfour.tasc.model.history;

import java.util.LinkedList;

/**
 * Stores and retrieves HistoryItems as a stack.
 * Automatically removes older items when max limit is reached.
 */
public class HistoryStack<T extends HistoryItem<T>> {

    /** Thrown when trying to pop an empty HistoryStack */
    public static class OutOfHistoryException extends Exception {
        protected OutOfHistoryException() {
            super("This history queue is empty.");
        }
    }
    
    private static final int DEFAULT_MAX_NUM_OF_STATES = 10;
    private final int maxNumOfStates;
    
    // Uses a LinkedList so that can removeFirst 
    // for states that are too old.
    private final LinkedList<T> historyLL;
    
    /**
     * Default constructor creates with default size.
     */
    public HistoryStack() {
        this(DEFAULT_MAX_NUM_OF_STATES);
    }
    
    /**
     * Precondition: maxNumOfStates is > 0.
     * Creates HistoryStack which can store up to
     * a number of history item states.
     * 
     * @param maxNumOfStates max number of history states
     */
    public HistoryStack(int maxNumOfStates) {
        assert maxNumOfStates > 0;
        this.maxNumOfStates = maxNumOfStates;
        historyLL = new LinkedList<T>();
    }
    
    /**
     * Precondition: item passed in argument is not null.
     * Pushes the object to be saved as a history state,
     * by adding its deep copy into this stack.
     * 
     * @param item Object to be saved
     */
    public void pushState(T item) {
        assert item != null;
        
        if (historyLL.size() >= maxNumOfStates) {
            historyLL.removeFirst();
        }
        historyLL.addLast(item.createStateAsDeepCopy());
    }
    
    /**
     * Pops and returns the latest history item stored.
     * 
     * @return The HistoryItem object
     * @throws OutOfHistoryException if the history queue is empty
     */
    public T popState() throws OutOfHistoryException {
        if (historyLL.size() <= 0)
            throw new OutOfHistoryException();
        
        return historyLL.removeLast();
    }
    
}
