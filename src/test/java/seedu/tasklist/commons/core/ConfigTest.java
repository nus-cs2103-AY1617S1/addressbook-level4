package seedu.tasklist.commons.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.tasklist.commons.core.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigTest {
	private String defaultConfigAsString = "App title : Lazyman's Friend\n" +
            "Current log level : INFO\n" +
            "Preference file Location : preferences.json\n" +
            "Local data file location : data/tasklist.xml\n" +
            "TaskList name : MyTaskList";
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        
        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod(){
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }

    public void setFilePath(String newFilePath){
    	String[] str = defaultConfigAsString.split(":");
    	str[0]=newFilePath;
    	String finalString="";
    	for(String appendstr:str){
    	    finalString = finalString.concat(appendstr);
    	}
    	defaultConfigAsString = finalString;
    }

}
