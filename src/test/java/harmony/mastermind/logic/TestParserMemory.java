package harmony.mastermind.logic;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertFalse;

import harmony.mastermind.logic.parser.ParserMemoryMain;;

public class TestParserMemory { 
    
    @Test
    //@@author A0143378Y
    public void test() { 
        test_set();
        test_setDate();
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
    
    //@@author A0143378Y
    /*
     * Accepts only:
     * 1. 0<date<32
     * 2. 0< month<13
     */
    private void test_setDate(){
        Calendar test = new GregorianCalendar();

        //invalid date
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("0", test));
        assertFalse(ParserMemoryMain.setProper);

        test_dayValidity();
        test_invalidMonth();
    }
    
    //@@author A0143378Y
    private void test_dayValidity(){
        Calendar test = new GregorianCalendar();

        //day == 0
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("00/01/01", test));
        assertFalse(ParserMemoryMain.setProper);

        //day == 32
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("32/01/01", test));
        assertFalse(ParserMemoryMain.setProper);

        //day == 1
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("01/01/01", test));
        assertTrue(ParserMemoryMain.setProper);
        assertEquals("Day is 1", test.get(Calendar.DATE), 1);

        //day == 31
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("31/01/01", test));
        assertTrue(ParserMemoryMain.setProper);
        assertEquals("Day is 31", test.get(Calendar.DATE), 31);
    }
    
    //@@author A0143378Y
    //There is no test for negative because there won't be any sign in the number string
    private void test_invalidMonth(){
        Calendar test = new GregorianCalendar();

        //Month == 0
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("01/00/01", test));
        assertFalse(ParserMemoryMain.setProper);

        //Month == 13
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("31/13/01", test));
        assertFalse(ParserMemoryMain.setProper);

        //Month == 1
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("01/01/01", test));
        assertTrue(ParserMemoryMain.setProper);
        assertEquals("Month is 1", test.get(Calendar.MONTH), 0);

        //Month == 12
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("01/12/01", test));
        assertTrue(ParserMemoryMain.setProper);
        assertEquals("Month is 12", test.get(Calendar.MONTH), 11);
    }
}