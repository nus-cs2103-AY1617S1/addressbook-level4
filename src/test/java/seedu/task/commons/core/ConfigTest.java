package seedu.task.commons.core;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todolist.commons.core.Config;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : Task!t\n" +
                "Current log level : INFO\n" +
                "Preference file Location : preferences.json\n" +
                "Local data file location : data/todolist.xml\n" +
                "ToDoList name : MyToDoList";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod(){
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }

}
