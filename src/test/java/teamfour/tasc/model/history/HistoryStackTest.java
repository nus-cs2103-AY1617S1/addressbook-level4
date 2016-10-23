package teamfour.tasc.model.history;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.ModelManager;
import teamfour.tasc.model.TaskList;
import teamfour.tasc.model.history.HistoryStack;
import teamfour.tasc.model.history.HistoryStack.OutOfHistoryException;
import teamfour.tasc.model.task.Period;

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

    private static final Logger logger = LogsCenter.getLogger(HistoryStackTest.class);
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
        historyStack.pushState(null);
    }
    
    @Test (expected = OutOfHistoryException.class)
    public void popState_emptyHistoryStack_throwsException() throws OutOfHistoryException {
        historyStack.popState();
    }
    
    @Test
    public void pushFromZeroToMaxSize_popsNewestToOldest_success() throws OutOfHistoryException {
        historyStack.pushState(new HistoryItemStub(1));
        historyStack.pushState(new HistoryItemStub(2));
        historyStack.pushState(new HistoryItemStub(3));
        assertEquals(historyStack.popState(), new HistoryItemStub(3));
        assertEquals(historyStack.popState(), new HistoryItemStub(2));
        assertEquals(historyStack.popState(), new HistoryItemStub(1));
    }
    
    @Test
    public void pushFromZeroToOverMaxSize_popsNewestToOldest_success() throws OutOfHistoryException {
        historyStack.pushState(new HistoryItemStub(1));
        historyStack.pushState(new HistoryItemStub(2));
        historyStack.pushState(new HistoryItemStub(3));
        historyStack.pushState(new HistoryItemStub(4));
        assertEquals(historyStack.popState(), new HistoryItemStub(4));
        assertEquals(historyStack.popState(), new HistoryItemStub(3));
        assertEquals(historyStack.popState(), new HistoryItemStub(2));
    }
    
    @Test (expected = OutOfHistoryException.class)
    public void pushFromZeroToMaxSize_popsTooMuch_throwsException() throws OutOfHistoryException {
        historyStack.pushState(new HistoryItemStub(1));
        historyStack.pushState(new HistoryItemStub(2));
        historyStack.pushState(new HistoryItemStub(3));
        assertEquals(historyStack.popState(), new HistoryItemStub(3));
        assertEquals(historyStack.popState(), new HistoryItemStub(2));
        assertEquals(historyStack.popState(), new HistoryItemStub(1));
        assertEquals(historyStack.popState(), new HistoryItemStub(0));
    }
}
