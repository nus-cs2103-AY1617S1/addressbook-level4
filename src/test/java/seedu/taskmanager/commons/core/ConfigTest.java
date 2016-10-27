package seedu.taskmanager.commons.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.taskmanager.commons.core.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : IvoryTasks\n" +
                "Current log level : FINE\n" +
                "Preference file Location : preferences.json\n" +
                "Local data file location : data/todo.xml\n" +
                "Task Manager name : MyTaskManager";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod(){
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }


}
