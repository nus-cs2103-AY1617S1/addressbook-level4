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
    
    //@@author A0139194X
    @Test
    public void getUserPrefsFilePath_success() {
        assertEquals("preferences.json", config.getUserPrefsFilePath());
    }
    
    //@@author A0139194X
    @Test
    public void setUserPrefsFilePath_success() {
        config.setUserPrefsFilePath("TestTest");
        assertEquals("TestTest", config.getUserPrefsFilePath());
    }
    
    //@@author A0139194X
    @Test
    public void setUserPrefsFilePath_nullInput_assertionFailure() {
        thrown.expect(AssertionError.class);
        config.setUserPrefsFilePath(null);
    }
    
    //@@author A0139194X
    @Test
    public void getTaskManagerFilePath_success() {
        assertEquals("data/mastermind.xml", config.getTaskManagerFilePath());
    }
    
    //@@author A0139194X
    @Test
    public void setTaskManagerFilePath_nullInput_assertionFailure() {
        thrown.expect(AssertionError.class);
        config.setTaskManagerFilePath(null);
    }
    
    //@@author A0139194X
    @Test
    public void setTaskManagerFilePath_success() {
        config.setTaskManagerFilePath("TestTestTest");
        assertEquals("TestTestTest", config.getTaskManagerFilePath());
    }
    
    //@@author A0139194X
    @Test
    public void getTaskManagerName_success() {
        assertEquals("MyTaskManager", config.getTaskManagerName());
    }
    
    //@@author A0139194X
    @Test
    public void setTaskManagerName_nullInput_assertionFailure() {
        thrown.expect(AssertionError.class);
        config.setTaskManagerName(null);
    }
    
    //@@author A0139194X
    @Test
    public void setTaskManagerName_success() {
        config.setTaskManagerName("TestTestTestTest");
        assertEquals("TestTestTestTest", config.getTaskManagerName());
    }
    
}