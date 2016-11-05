package seedu.agendum.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static seedu.agendum.testutil.TestUtil.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import seedu.agendum.commons.core.UnmodifiableObservableList;

public class UnmodifiableObservableListTest {

    private final int ITEM_ZERO = 10;
    private final int ITEM_ONE = 11;
    private final int ITEM_TWO = 12;
    private final int ITEM_THREE = 13;
    private final int ITEM_FOUR = 14;
    
    
    private List<Integer> backing;
    private UnmodifiableObservableList<Integer> list;

    @Before
    public void setUp() {
        backing = new ArrayList<>();
        backing.add(ITEM_ZERO);
        backing.add(ITEM_ONE);
        backing.add(ITEM_TWO);
        backing.add(ITEM_THREE);
        backing.add(ITEM_FOUR);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
    }

    @Test
    public void transformationListGeneratorsCorrectBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    //@@author A0148095X
    @Test
    public void mutatingMethodsDisabled() {

        final Class<UnsupportedOperationException> ex = UnsupportedOperationException.class;

        assertThrows(ex, () -> list.add(3));
        assertThrows(ex, () -> list.add(1, 2));
        assertThrows(ex, () -> list.add(1, null));

        assertThrows(ex, () -> list.addAll(2, 1));
        assertThrows(ex, () -> list.addAll(null, 1));
        assertThrows(ex, () -> list.addAll(backing));
        assertThrows(ex, () -> list.addAll(0, backing));

        assertThrows(ex, () -> list.set(0, 2));
        assertThrows(ex, () -> list.set(1, null));

        assertThrows(ex, () -> list.setAll(1, 2));
        assertThrows(ex, () -> list.setAll(1, null));
        assertThrows(ex, () -> list.setAll(backing));

        assertThrows(ex, () -> list.remove(0, 1));
        assertThrows(ex, () -> list.remove(null));
        assertThrows(ex, () -> list.remove(0));

        assertThrows(ex, () -> list.removeAll(backing));
        assertThrows(ex, () -> list.removeAll(1, 2));
        assertThrows(ex, () -> list.removeAll(null, 2));

        assertThrows(ex, () -> list.retainAll(backing));
        assertThrows(ex, () -> list.retainAll(1, 2));
        assertThrows(ex, () -> list.retainAll(1, null));

        assertThrows(ex, () -> list.replaceAll(i -> 1));

        assertThrows(ex, () -> list.sort(Comparator.naturalOrder()));
        assertThrows(ex, () -> list.sort(null));

        assertThrows(ex, () -> list.clear());

        final Iterator<Integer> iter = list.iterator();
        iter.next();
        assertThrows(ex, iter::remove);

        final ListIterator<Integer> liter = list.listIterator();
        liter.next();
        assertThrows(ex, liter::remove);
        assertThrows(ex, () -> liter.add(5));
        assertThrows(ex, () -> liter.set(3));
        assertThrows(ex, () -> list.removeIf(i -> true));
    }
    
    @SuppressWarnings("unused")
    @Test (expected = NullPointerException.class)
    public void unmodifiableObservableList_nullList_nullPointerExceptionThrown() {
        UnmodifiableObservableList<Object> nullList = new UnmodifiableObservableList<>(null);
    }
    
    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        assertFalse(list.isEmpty());
    }

    @Test
    public void contains_existingItem_returnsTrue() {
        assertTrue(list.contains(ITEM_TWO));
    }

    @Test
    public void containsAll_sameList_returnsTrue() {
        assertTrue(list.containsAll(backing));
    }
    
    @Test
    public void containsAll_listWhichContainsOneExtraItem_returnsFalse() {
        final List<Integer> backingWithOneExtraItem = new ArrayList<>(backing);
        backingWithOneExtraItem.add(15);
        final UnmodifiableObservableList<Object> listWithOneExtraItem = new UnmodifiableObservableList<>(FXCollections.observableList(backingWithOneExtraItem));
        
        assertFalse(list.containsAll(listWithOneExtraItem));
    }

    @Test
    public void indexOf_validItem_returnsCorrectIndex() {
        final int itemToFind = ITEM_ONE;
        final int index = list.indexOf(itemToFind);
        
        assertEquals(index, 1);
    }
    
    @Test
    public void indexOf_invalidItem_returnsErrorIndex() {
        final int itemToFind = 999;
        final int index = list.indexOf(itemToFind);
        
        assertEquals(index, -1);
    }
    
    @Test
    public void lastIndexOf_validItem_returnsCorrectIndex() {
        final int itemToFind = ITEM_ZERO;
        final int itemToAdd = ITEM_ZERO; // add a duplicate so it become last object
        
        final List<Integer> backingWithDuplicate = new ArrayList<>(backing);
        backingWithDuplicate.add(itemToAdd);
        final UnmodifiableObservableList<Object> listWithDuplicate = new UnmodifiableObservableList<>(FXCollections.observableList(backingWithDuplicate));
        
        final int expectedIndex = listWithDuplicate.size()-1;
        final int actualIndex = listWithDuplicate.lastIndexOf(itemToFind);
        
        assertEquals(expectedIndex, actualIndex);
    }
    
    @Test
    public void lastIndexOf_invalidItem_returnsErrorIndex() {
        final int itemToFind = 888;
        final int index = list.lastIndexOf(itemToFind);
        
        assertEquals(index, -1);
    }
    
    @Test
    public void subList_sameItems_returnsTrue() {
        final int startIndex = 1;
        final int endIndex = 3; 
        List<Integer> subListOfBacking = backing.subList(startIndex, endIndex);
        List<Integer> subListOfList = list.subList(startIndex, endIndex);
        
        assertTrue(subListOfBacking.equals(subListOfList));
    }
    
    @Test
    public void toArray_sameItems_returnsTrue() {
        final Integer[] arrayWithSameItems = new Integer[]{ITEM_ZERO, ITEM_ONE, ITEM_TWO, ITEM_THREE, ITEM_FOUR};
        final Object[] convertedToObjectArray =  list.toArray();        
        final Integer[] convertedToIntegerArray = list.toArray(new Integer[0]);

        assertTrue(Arrays.deepEquals(arrayWithSameItems, convertedToObjectArray));
        assertTrue(Arrays.equals(arrayWithSameItems, convertedToIntegerArray));
    }
    
    @Test
    public void equals_symmetricList_returnsTrue() {
        final UnmodifiableObservableList<Object> one = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
        final UnmodifiableObservableList<Object> another = new UnmodifiableObservableList<>(FXCollections.observableList(backing));

        assertTrue(one.equals(another) && another.equals(one));
        assertTrue(one.hashCode() == another.hashCode());
    }
    
    @Test
    public void listIterator_iterateWholeList_listMatches() {
        final ListIterator<Integer> liter = list.listIterator();
        int currentItem;
        int index;
        
        // cursor position 0 -> 1 (index 0)
        assertTrue(liter.hasNext());
        
        index = liter.nextIndex();
        assertEquals(index, 0);
        
        currentItem = liter.next();
        assertEquals(currentItem, ITEM_ZERO);
        
        // move cursor position 1 -> 2 -> 3
        liter.next();
        liter.next();
        
        // cursor position 3 -> 2 (index 2)
        assertTrue(liter.hasPrevious());
        
        index = liter.previousIndex();
        assertEquals(index, 2);
        
        currentItem = liter.previous();
        assertEquals(currentItem, ITEM_TWO);
    }
}
