package seedu.address.commons.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaskConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
    	/* TODO: Reenable
        String defaultConfigAsString = "App title : Address App\n" +
                "Current log level : INFO\n" +
                "Preference file Location : preferences.json\n" +
                "Local data file location : data/addressbook.xml\n" +
                "AddressBook name : MyAddressBook";

        assertEquals(defaultConfigAsString, new TaskConfig().toString());
        */
    }

    @Test
    public void equalsMethod(){
        TaskConfig defaultConfig = new TaskConfig();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }


}
