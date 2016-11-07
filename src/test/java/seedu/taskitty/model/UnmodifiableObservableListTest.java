package seedu.taskitty.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskitty.commons.core.UnmodifiableObservableList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;
import static seedu.taskitty.testutil.TestUtil.assertThrows;

public class UnmodifiableObservableListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Integer> backing;
    private UnmodifiableObservableList<Integer> list;
    private Collection<Integer> col;

    @Before
    public void setup() {
        backing = new ArrayList<>();
        backing.add(10);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
        col = new ArrayList<Integer>();
    }

    @Test
    public void transformationListGenerators_correctBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    @Test
    public void mutatingMethods_disabled() {

        final Class<UnsupportedOperationException> ex = UnsupportedOperationException.class;

        assertThrows(ex, () -> list.add(0, 2));
        assertThrows(ex, () -> list.add(3));

        assertThrows(ex, () -> list.addAll(2, 1));
        assertThrows(ex, () -> list.addAll(backing));
        assertThrows(ex, () -> list.addAll(0, backing));

        assertThrows(ex, () -> list.set(0, 2));

        assertThrows(ex, () -> list.setAll(new ArrayList<Number>()));
        assertThrows(ex, () -> list.setAll(1, 2));
        
        assertThrows(ex, () -> list.setAll(col));
        assertThrows(ex, () -> list.remove(0, 1));
        assertThrows(ex, () -> list.remove(null));
        assertThrows(ex, () -> list.remove(0));

        assertThrows(ex, () -> list.removeAll(backing));
        assertThrows(ex, () -> list.removeAll(1, 2));

        assertThrows(ex, () -> list.retainAll(backing));
        assertThrows(ex, () -> list.retainAll(1, 2));

        assertThrows(ex, () -> list.replaceAll(i -> 1));

        assertThrows(ex, () -> list.sort(Comparator.naturalOrder()));

        assertThrows(ex, () -> list.clear());

        final Iterator<Integer> iter = list.iterator();
        iter.next();
        assertThrows(ex, iter::remove);

        final ListIterator<Integer> liter = list.listIterator();
        liter.next();
        assertTrue(liter.hasPrevious());
        assertEquals(new Integer(10), liter.previous());
        assertEquals(0, liter.nextIndex());
        assertEquals(-1, liter.previousIndex());
        assertThrows(ex, liter::remove);
        assertThrows(ex, () -> liter.add(5));
        assertThrows(ex, () -> liter.set(3));
        assertThrows(ex, () -> list.removeIf(i -> true));
    }
    
    //@@author A0130853L
    @Test
    public void hasNullBackingList_ExceptionThrown() throws NullPointerException {
        thrown.expect(NullPointerException.class);
        List<Integer> backingList = null;
        UnmodifiableObservableList newlist = new UnmodifiableObservableList((ObservableList)backingList);
    }
    
    @Test
    public void unmodifiableObservableList_containsObject_returnsTrue() {
        assertTrue(list.contains(10));
    }
    
    @Test
    public void unmodifiableObservableList_containsAll_returnsTrue() {
        col.add(10);
        assertTrue(list.containsAll(col));
    }
    
    @Test
    public void indexOfObject_returnsTrue() {
        assertEquals(list.indexOf(10), 0);
    }
    
    @Test
    public void lastIndexOfObject_returnsTrue() {
        assertEquals(list.lastIndexOf(10), 0);
    }
    
    @Test
    public void toArray_returnsTrue() {
        assertEquals(Object[].class, list.toArray().getClass());
    }
    
    @Test
    public void toIntegerArray_returnsTrue() {
        assertEquals(Integer[].class, list.toArray(new Integer[list.size()]).getClass());
    }
    
    @Test
    public void equalsUnmodifiableObservableList_returnsTrue() {
        assertTrue(list.equals(list));
    }
    
    @Test
    public void equalsBackingList_returnsTrue() {
        assertTrue(list.equals(backing));
    }
}
