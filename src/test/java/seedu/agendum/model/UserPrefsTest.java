package seedu.agendum.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.agendum.commons.core.GuiSettings;

//@@author A0148095X
public class UserPrefsTest {

    private UserPrefs one, another;

    @Before
    public void setUp() {
        one = new UserPrefs();
        another = new UserPrefs();
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        assertFalse(one.equals(new Object()));
    }

    @Test
    public void equals_symmetric_returnsTrue() {
        // equals to itself and object with same parameters
        assertTrue(one.equals(one));
        assertTrue(one.equals(another));
    }

    @Test
    public void hashcode_symmetric_returnsTrue() {
        assertEquals(one.hashCode(), another.hashCode());
    }

    @Test
    public void setGuiSettings_validInputs_successful() {
        final double expectedWidth = 222;
        final double expectedHeight = 333;
        final int expectedX = 444;
        final int expectedY = 555;
        final GuiSettings expectedGuiSettings = new GuiSettings(expectedWidth, expectedHeight, expectedX, expectedY);

        final UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(expectedWidth, expectedHeight, expectedX, expectedY);
        GuiSettings actualGuiSettings = userPrefs.getGuiSettings();

        assertEquals(actualGuiSettings, expectedGuiSettings);
    }

}
