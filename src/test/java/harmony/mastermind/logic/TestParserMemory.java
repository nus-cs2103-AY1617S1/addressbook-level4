package harmony.mastermind.logic;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import harmony.mastermind.logic.parser.ParserMemoryMain;;

public class TestParserMemory { 
    
    @Test
    //@@author A0143378Y
    public void test() { 
        test_set();
    }
    
    private void test_set() { 
        ParserMemoryMain.setCommand("test1");
        assertEquals("Test set command", ParserMemoryMain.getCommand(), "test1");
        
        ParserMemoryMain.setTaskName("test2");
        assertEquals("Test set task name", ParserMemoryMain.getTaskName(), "test2");
        
        ParserMemoryMain.setDescription("test3");
        assertEquals("Test set description", ParserMemoryMain.getDescription(), "test3");
        
        ParserMemoryMain.setLength(4);
        assertEquals("Test set length", ParserMemoryMain.getLength(), 4);
        
        ParserMemoryMain.setType(5);
        assertEquals("Test set type", ParserMemoryMain.getType(), 5);
        
        //Test if setContainsDescription works properly
        ParserMemoryMain.setContainsDescription(true);
        assertTrue(ParserMemoryMain.containsDescription);

        ParserMemoryMain.setContainsDescription(false);
        assertFalse(ParserMemoryMain.containsDescription);

        //Test if setProper works properly
        ParserMemoryMain.setProper(true);
        assertTrue(ParserMemoryMain.setProper);

        ParserMemoryMain.setProper(false);
        assertFalse(ParserMemoryMain.setProper);
    }
}