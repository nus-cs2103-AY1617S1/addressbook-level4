package tars.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Prefix test
 * 
 * @@author A0139924W
 *
 */
public class PrefixTest {
    
    @Test
    public void equalsMethod() {
        Prefix tagPrefix = new Prefix("/tag");

        assertEquals(tagPrefix, tagPrefix);
        assertEquals(tagPrefix, new Prefix("/tag"));

        assertNotEquals(tagPrefix, "/tag");
        assertNotEquals(tagPrefix, new Prefix("/nTag"));
    }
}
