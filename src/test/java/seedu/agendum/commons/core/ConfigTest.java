package seedu.agendum.commons.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigTest {

    @Test
    public void toString_defaultObject_stringReturned() {
        String sb = "App title : Agendum" +
                "\nCurrent log level : INFO" +
                "\nPreference file Location : " + Config.DEFAULT_USER_PREFS_FILE +
                "\nLocal data file location : " + Config.DEFAULT_SAVE_LOCATION +
                "\nToDoList name : MyToDoList";

        assertEquals(sb, new Config().toString());
    }

    @Test
    public void equalsMethod(){
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }


}
