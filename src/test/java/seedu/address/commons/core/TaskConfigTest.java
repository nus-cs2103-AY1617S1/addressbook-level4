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

    //@@author A0138978E
    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : Task Manager\n" +
                "Current log level : INFO\n" +
                "Preference file Location : task-userpreferences.json\n" +
                "Local data file location : data/tasks.xml\n" +
                "Local alias file location : " + "data/alias.xml\n" +
                "AddressBook name : TaskManager";

        assertEquals(defaultConfigAsString, new TaskConfig().toString());
    }

    @Test
    public void equalsMethod(){
        TaskConfig defaultConfig = new TaskConfig();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }
    
    @Test
    public void setStorageLocationMethod() {
    	TaskConfig config = new TaskConfig();
    	String validPath = System.getProperty("user.home");
    	config.setStorageLocation(validPath);
    	
    	assertEquals(config.getTasksFilePath(), validPath + "/tasks.xml");
    	assertEquals(config.getAliasFilePath(), validPath + "/alias.xml");
    }


}
