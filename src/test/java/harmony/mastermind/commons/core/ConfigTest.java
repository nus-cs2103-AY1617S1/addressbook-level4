package harmony.mastermind.commons.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

public class ConfigTest {
    
    //@@author A0139194X
    private Config config;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    //@@author A0139194X
    @Before
    public void setup() {
        config = new Config();
    }

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : Mastermind\n" +
                "Current log level : INFO\n" +
                "Preference file Location : preferences.json\n" +
                "Local data file location : data/mastermind.xml\n" +
                "TaskManager name : MyTaskManager";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod(){
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }

    //@@author A0139194X
    @Test
    public void getAppTitle_success() {
        assertEquals("Mastermind", config.getAppTitle());
    }
    
    //@@author A0139194X
    @Test
    public void setAppTitle_success() {
        config.setAppTitle("Test");
        assertEquals("Test", config.getAppTitle());
    }
    
    //@@author A0139194X
    @Test
    public void setAppTitle_nullInput_assertionFailure() {
        thrown.expect(AssertionError.class);
        config.setAppTitle(null);
    }
}