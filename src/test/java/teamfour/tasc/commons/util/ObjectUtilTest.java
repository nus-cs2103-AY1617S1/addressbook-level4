package teamfour.tasc.commons.util;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import teamfour.tasc.commons.util.ObjectUtil;

public class ObjectUtilTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void isEquivalentOrBothNull_bothNull_returnsTrue() {
        assertTrue(ObjectUtil.isEquivalentOrBothNull(null, null));
    }
    
    @Test
    public void isEquivalentOrBothNull_nullWithNonNull_returnsFalse() {
        assertFalse(ObjectUtil.isEquivalentOrBothNull(null, "Hi"));
        assertFalse(ObjectUtil.isEquivalentOrBothNull("Bye", null));
    }
    
    @Test
    public void isEquivalentOrBothNull_sameString_returnsTrue() {
        assertTrue(ObjectUtil.isEquivalentOrBothNull(new String("Hi"), new String("Hi")));
    }

    @Test
    public void isEquivalentOrBothNull_differentString_returnsFalse() {
        assertFalse(ObjectUtil.isEquivalentOrBothNull("Hi", "Bye"));
    }
}
