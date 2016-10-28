package seedu.agendum.commons.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {        
        StringBuilder sb = new StringBuilder();
        sb.append("App title : Agendum");
        sb.append("\nCurrent log level : INFO");
        sb.append("\nPreference file Location : " + Config.DEFAULT_USER_PREFS_FILE);
        sb.append("\nLocal data file location : " + Config.DEFAULT_SAVE_LOCATION);
        sb.append("\nToDoList name : MyToDoList");

        assertEquals(sb.toString(), new Config().toString());
    }

    @Test
    public void equalsMethod(){
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }


}
