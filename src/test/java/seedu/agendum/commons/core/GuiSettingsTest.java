package seedu.agendum.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

//@@author A0148095X
public class GuiSettingsTest {

    private Double windowWidth = 800.0;
    private Double windowHeight = 600.0;
    private int xPosition = 100;
    private int yPosition = 300;

    private GuiSettings one, another;
    
    @Before
    public void setUp() {
        one = new GuiSettings(windowWidth, windowHeight, xPosition, yPosition);
        another = new GuiSettings(windowWidth, windowHeight, xPosition, yPosition);
    }
    
    @Test
    public void equals_differentObject_returnsFalse() {
        assertFalse(one.equals(new Object()));
    }    

    @Test
    public void equals_symmetric_returnsTrue() {
        // equals to itself and similar object
        assertTrue(one.equals(one));
        assertTrue(one.equals(another));        
    }
    
    @Test
    public void equals_validInputDifferentSettings_returnsFalse() {
        // ----------- different settings ----------------
        GuiSettings differentSettings;

        Double differentWindowWidth = windowWidth*2;
        Double differentWindowHeight = windowHeight*2;
        int differentXPosition = xPosition*2;
        int differentYPosition = yPosition*2;

        // different width
        differentSettings  = new GuiSettings(differentWindowWidth, windowHeight, xPosition, yPosition);
        assertFalse(one.equals(differentSettings));
        
        // different height
        differentSettings  = new GuiSettings(windowWidth, differentWindowHeight, xPosition, yPosition);
        assertFalse(one.equals(differentSettings));
        
        // different x position
        differentSettings  = new GuiSettings(windowWidth, windowHeight, differentXPosition, yPosition);
        assertFalse(one.equals(differentSettings));
        
        // different y position
        differentSettings  = new GuiSettings(windowWidth, windowHeight, xPosition, differentYPosition);
        assertFalse(one.equals(differentSettings));
    }
    
    @Test
    public void hashcode_symmetric_returnsTrue() {
        assertEquals(one.hashCode(), another.hashCode());
    }    
}
