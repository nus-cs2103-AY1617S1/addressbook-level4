//@@author A0148096W

package teamfour.tasc.model.history;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.model.history.HistoryStack;
import teamfour.tasc.model.history.HistoryStack.OutOfHistoryException;

public class HistoryStackTest {
    
    /*
     * Set up with HistoryStack of size 3,
     * containing objects of type HistoryItemStub. 
     * (Dependency injection)
     */
    private class HistoryItemStub implements HistoryItem<HistoryItemStub> {
        private int data;
        
        public HistoryItemStub(int data) {
            this.data = data;
        }
        
        @Override
        public HistoryItemStub createStateAsDeepCopy() {
            return new HistoryItemStub(data);
        }
        
        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof HistoryItemStub // instanceof handles nulls
                    && this.data == ((HistoryItemStub)other).data);
        }
    }

    private HistoryStack<HistoryItemStub> historyStack;
    
    @Before
    public void setUp_withSizeThree() {
        historyStack = new HistoryStack<HistoryItemStub>(3);
    }
    
    /*
     * Equivalence Partitions:
     * pushState (null)
     *          assertion error
     *          
     * pushState (HistoryItem)
     *     - currentSize less than maxSize: 
     *          push to stack
     *     - currentSize more than or equal to maxSize: 
     *          push to stack, oldest element removed
     *          
     * popState ()
     *     - currentSize less than or equal to zero:
     *          throws OutOfHistoryException
     *     - currentSize more than zero:
     *          pops and returns from the stack
     *          
     * Boundary Values:
     * For maxSize 3:
     *     pushState
     *         - add 3 items: has 3 items
     *         - add 4 items: has 3 newest items
     *         
     *     popState
     *         - currentSize 3, pop 3: 
     *             retrieves newest to oldest
     *         - currentSize 3, pop 4: 
     *             retrieves newest to oldest, 
     *             last pop throws OutOfHistoryException
     */
    
    @Test (expected = AssertionError.class)
    public void pushState_nullArg_throwsAssertionError() {
        historyStack.push(null);
    }
    
    @Test (expected = OutOfHistoryException.class)
    public void popState_emptyHistoryStack_throwsException() throws OutOfHistoryException {
        historyStack.pop();
    }
    
    @Test
    public void pushFromZeroToMaxSize_popsNewestToOldest_success() throws OutOfHistoryException {
        historyStack.push(new HistoryItemStub(1));
        historyStack.push(new HistoryItemStub(2));
        historyStack.push(new HistoryItemStub(3));
        assertEquals(historyStack.pop(), new HistoryItemStub(3));
        assertEquals(historyStack.pop(), new HistoryItemStub(2));
        assertEquals(historyStack.pop(), new HistoryItemStub(1));
    }
    
    @Test
    public void pushFromZeroToOverMaxSize_popsNewestToOldest_success() throws OutOfHistoryException {
        historyStack.push(new HistoryItemStub(1));
        historyStack.push(new HistoryItemStub(2));
        historyStack.push(new HistoryItemStub(3));
        historyStack.push(new HistoryItemStub(4));
        assertEquals(historyStack.pop(), new HistoryItemStub(4));
        assertEquals(historyStack.pop(), new HistoryItemStub(3));
        assertEquals(historyStack.pop(), new HistoryItemStub(2));
    }
    
    @Test (expected = OutOfHistoryException.class)
    public void pushFromZeroToMaxSize_popsTooMuch_throwsException() throws OutOfHistoryException {
        historyStack.push(new HistoryItemStub(1));
        historyStack.push(new HistoryItemStub(2));
        historyStack.push(new HistoryItemStub(3));
        assertEquals(historyStack.pop(), new HistoryItemStub(3));
        assertEquals(historyStack.pop(), new HistoryItemStub(2));
        assertEquals(historyStack.pop(), new HistoryItemStub(1));
        assertEquals(historyStack.pop(), new HistoryItemStub(0));
    }
}
