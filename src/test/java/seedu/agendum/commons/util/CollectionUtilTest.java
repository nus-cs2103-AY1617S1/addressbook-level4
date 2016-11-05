package seedu.agendum.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

//@@author A0148095X
public class CollectionUtilTest {

    public String string = "string";
    public int number = 1;
    public double decimal = 2.0;
    public boolean bool = true;
    public Object obj = new Object();
    
    public ArrayList<Object> noNullUniqueArrayList;
    
    @Before
    public void setUp() {
        noNullUniqueArrayList = new ArrayList<Object>();
        noNullUniqueArrayList.add(string);
        noNullUniqueArrayList.add(number);
        noNullUniqueArrayList.add(decimal);
        noNullUniqueArrayList.add(bool);
        noNullUniqueArrayList.add(obj);
    }
    
    @Test
    public void isNotNull() {        
        // No nulls
        assertTrue(CollectionUtil.isNotNull(string, number, decimal, bool, obj));
        
        // One null
        assertFalse(CollectionUtil.isNotNull(string, number, null, decimal, bool, obj));
    }    

    @Test
    public void assertNoNullNoNull() {
        // No assertion errors; all non-null
        CollectionUtil.assertNoNullElements(noNullUniqueArrayList);
    }
    
    @Test(expected = AssertionError.class)
    public void assertNoNullElementsNullCollection(){
        // The collection is null
        CollectionUtil.assertNoNullElements(null);
    }
    
    @Test
    public void elementsAreUnique() {
        // Unique
        assertTrue(CollectionUtil.elementsAreUnique(noNullUniqueArrayList));
        
        // Not unique
        ArrayList<Object> notUniqueArrayList = new ArrayList<>(noNullUniqueArrayList);
        notUniqueArrayList.add(string);
        assertFalse(CollectionUtil.elementsAreUnique(notUniqueArrayList));        
    }
}
