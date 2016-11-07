package seedu.address.model;

import javafx.collections.FXCollections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.SortedObservableArrayList;

import java.util.*;

import static org.junit.Assert.assertSame;

//@@author A0135812L
public class SortedObservableArrayListTest<E extends Comparable<? super E>> {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    List<Integer> backing;
    List<Integer> dummy;
    SortedObservableArrayList<Integer> list;

    @Before
    public void setup() {
        dummy = new ArrayList<>();
        backing = new ArrayList<>();
        list = new SortedObservableArrayList<>(FXCollections.observableList(backing));
    }

    @Test
    public void transformationListGenerators_correctBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    @Test
    public void anyMethod_remainSorted() {

        list.add(3);
        dummy.add(3);
        assertSorted(list, dummy);

        list.addAll(2, 1);
        dummy.add(2);
        dummy.add(1);
        assertSorted(list, dummy);
        list.addAll(backing);
        dummy.addAll(backing);
        assertSorted(list, dummy);
        

        list.set(0, 2);
        dummy.set(0, 2);
        assertSorted(list, dummy);

        list.setAll(new ArrayList<Integer>());
        dummy=new ArrayList<Integer>();
        assertSorted(list, dummy);
        list.setAll(1, 2);
        dummy.add(1);
        dummy.add(2);
        assertSorted(list, dummy);

        list.remove(0, 1);
        dummy.subList(0, 1).clear();
        assertSorted(list, dummy);
        list.remove(null);
        dummy.remove(null);
        assertSorted(list, dummy);
        list.remove(0);
        dummy.remove(0);
        assertSorted(list, dummy);

        list.removeAll(backing);
        dummy.removeAll(backing);
        assertSorted(list, dummy);

        list.removeAll(1, 2);
        dummy.remove(new Integer(1));
        dummy.remove(new Integer(2));
        assertSorted(list, dummy);

        list.retainAll(backing);
        dummy.retainAll(backing);
        assertSorted(list, dummy);

        list.clear();
        dummy.clear();
        assertSorted(list, dummy);

    }
    
    @Test
    public void insertionWithIndexSpecified_disabled(){
    }
    
    public void assertSorted(SortedObservableArrayList<Integer> list, List<Integer> dummy){
        assert list.size()==dummy.size();
        dummy.sort(null);
        for(int i=0; i<list.size();i++)
        {
            assert(list.get(i).equals(dummy.get(i)));
        }
    }
}
//@@author
