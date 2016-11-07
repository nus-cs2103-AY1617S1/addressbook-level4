package harmony.mastermind.logic;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertFalse;

import harmony.mastermind.logic.parser.ParserSearch;
import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;

public class TestParserSearch { 
    
    Memory mem;
    
    @Test
    //@@author A0143378Y
    public void test() { 
        testInit();
    }
    
    //@@author A0143378Y
    private void testInit() { 
        ParserSearch search = null;
        search.initVar();
        
        //Search Type
        assertEquals("Type", search.getType(), -1);
        
        //Search Length
        assertEquals("Length", search.getLength(), -1);
    }    
}
