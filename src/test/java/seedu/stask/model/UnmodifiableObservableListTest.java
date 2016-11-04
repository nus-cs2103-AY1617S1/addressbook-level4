package seedu.stask.model;

import javafx.collections.FXCollections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.assertSame;

import seedu.stask.commons.core.UnmodifiableObservableList;
import seedu.stask.testutil.TestUtil;

public class UnmodifiableObservableListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    List<Integer> backing;
    UnmodifiableObservableList<Integer> list;

    @Before
    public void setup() {
        backing = new ArrayList<>();
        backing.add(10);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
    }

    @Test
    public void transformationListGenerators_correctBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    @Test
    public void mutatingMethods_disabled() {

        final Class<UnsupportedOperationException> ex = UnsupportedOperationException.class;

        TestUtil.assertThrows(ex, () -> list.add(0, 2));
        TestUtil.assertThrows(ex, () -> list.add(3));

        TestUtil.assertThrows(ex, () -> list.addAll(2, 1));
        TestUtil.assertThrows(ex, () -> list.addAll(backing));
        TestUtil.assertThrows(ex, () -> list.addAll(0, backing));

        TestUtil.assertThrows(ex, () -> list.set(0, 2));

        TestUtil.assertThrows(ex, () -> list.setAll(new ArrayList<Number>()));
        TestUtil.assertThrows(ex, () -> list.setAll(1, 2));

        TestUtil.assertThrows(ex, () -> list.remove(0, 1));
        TestUtil.assertThrows(ex, () -> list.remove(null));
        TestUtil.assertThrows(ex, () -> list.remove(0));

        TestUtil.assertThrows(ex, () -> list.removeAll(backing));
        TestUtil.assertThrows(ex, () -> list.removeAll(1, 2));

        TestUtil.assertThrows(ex, () -> list.retainAll(backing));
        TestUtil.assertThrows(ex, () -> list.retainAll(1, 2));

        TestUtil.assertThrows(ex, () -> list.replaceAll(i -> 1));

        TestUtil.assertThrows(ex, () -> list.sort(Comparator.naturalOrder()));

        TestUtil.assertThrows(ex, () -> list.clear());

        final Iterator<Integer> iter = list.iterator();
        iter.next();
        TestUtil.assertThrows(ex, iter::remove);

        final ListIterator<Integer> liter = list.listIterator();
        liter.next();
        TestUtil.assertThrows(ex, liter::remove);
        TestUtil.assertThrows(ex, () -> liter.add(5));
        TestUtil.assertThrows(ex, () -> liter.set(3));
        TestUtil.assertThrows(ex, () -> list.removeIf(i -> true));
    }
}
