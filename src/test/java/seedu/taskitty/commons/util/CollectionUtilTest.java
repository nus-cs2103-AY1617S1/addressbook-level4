package seedu.taskitty.commons.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

//@@author A0130853L
public class CollectionUtilTest {

    @Test
    public void isAnyNull_returnsTrue() {
        boolean result = CollectionUtil.isAnyNull("hi", "bye", null);
        assertEquals(result, true);
    }

    @Test
    public void elementsAreUnique_returnsTrue() {
        Collection<Object> listOfStrings = new ArrayList<Object>();
        listOfStrings.add("hi");
        listOfStrings.add("bye");
        boolean result = CollectionUtil.elementsAreUnique(listOfStrings);
        assertEquals(result, true);
    }
    @Test
    public void elementsAreUnique_returnsFalse() {
        Collection<Object> listOfStrings = new ArrayList<Object>();
        listOfStrings.add("hi");
        listOfStrings.add("hi");
        boolean result = CollectionUtil.elementsAreUnique(listOfStrings);
        assertEquals(result, false);
    }

}
