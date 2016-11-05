package seedu.agendum.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ConfigTest {

    private Config one;
    private Config another;

    @Before
    public void setUp() {
        one = new Config();
        another = new Config();
    }

    @Test
    public void toString_defaultObject_stringReturned() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : Agendum");
        sb.append("\nCurrent log level : INFO");
        sb.append("\nAlias Table file location: " + Config.DEFAULT_ALIAS_TABLE_FILE);
        sb.append("\nPreference file Location : " + Config.DEFAULT_USER_PREFS_FILE);
        sb.append("\nLocal data file location : " + Config.DEFAULT_SAVE_LOCATION);
        sb.append("\nToDoList name : MyToDoList");

        assertEquals(sb.toString(), new Config().toString());
    }
    
    //@@author A0148095X
    @Test
    public void setAliasTableFilePath_validPath_returnsTrue() {
        Config config = new Config();
        String validPath = "dropbox/table.xml";
        config.setAliasTableFilePath(validPath);
        
        assertEquals(validPath, config.getAliasTableFilePath());
    }

    @Test
    public void equals_differentObjectType_returnsFalse() {
        assertFalse(one.equals(new Object()));
    }

    @Test
    public void equals_same_returnsTrue() {
        assertTrue(one.equals(one));
    }

    @Test
    public void equals_symmetric_returnsTrue() {
        assertTrue(one.equals(another) && another.equals(one));
    }

    @Test
    public void hashCode_symmetric_returnsTrue() {
        assertTrue(one.hashCode() == another.hashCode());
    }
}
