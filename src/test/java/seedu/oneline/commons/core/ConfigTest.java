package seedu.oneline.commons.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.oneline.commons.core.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : OneLine\n" +
                "Current log level : INFO\n" +
                "Preference file Location : preferences.json\n" +
                "Local data file location : data\\taskbook.xml\n" +
                "TaskBook name : MyTaskBook";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod_nullObject_returnFalse(){
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(null));
    }

    @Test
    public void equalsMethod_selfObject_returnTrue(){
        Config defaultConfig = new Config();
        assertTrue(defaultConfig.equals(defaultConfig));
    }


}
