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
        test_setTime();
        test_reduce();
        test_isUselessCommand();
    }
    
    //@@author A0143378Y
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
    
    //@@author A0143378Y
    private void test_setTime() { 
        test_Hour();
        test_Minute();
        test_setTimeIfHHMMSS();
    }
    
    //@@author A0143378Y
    private static void test_Hour() { 
        Calendar test = new GregorianCalendar();
        
        //hour == 24
        ParserMemoryMain.setProper(ParserMemoryMain.setDate("2400", test));
        assertFalse(ParserMemoryMain.setProper);
        
        //hour == 0 
        ParserMemoryMain.setProper(ParserMemoryMain.setTime("0000", test));
        assertTrue(ParserMemoryMain.setProper);
        assertEquals(0, test.get(Calendar.HOUR_OF_DAY));
        
        //hour == 23
        ParserMemoryMain.setProper(ParserMemoryMain.setTime("2300", test));
        assertTrue(ParserMemoryMain.setProper);
        assertEquals(23, test.get(Calendar.HOUR_OF_DAY));
    } 
    
    //@@author A0143378Y 
    private static void test_Minute() { 
        Calendar test = new GregorianCalendar();
        
        //Minute == 60 
        ParserMemoryMain.setProper(ParserMemoryMain.setTime("2460", test));
        assertFalse(ParserMemoryMain.setProper);
        
        //Minute == 0 
        ParserMemoryMain.setProper(ParserMemoryMain.setTime("0000", test));
        assertTrue(ParserMemoryMain.setProper);
        assertEquals("Minute is 0", test.get(Calendar.MINUTE), 0);
        
        //Minute == 59 
        ParserMemoryMain.setProper(ParserMemoryMain.setTime("2359", test));
        assertTrue(ParserMemoryMain.setProper);
        assertEquals("Minute is 59", test.get(Calendar.MINUTE), 59);
    }
    
    //@@author A0143378Y
    private static void test_setTimeIfHHMMSS() { 
        Calendar test = new GregorianCalendar();
        
        //SS == 00 
        ParserMemoryMain.setProper(ParserMemoryMain.setTime("2333", test));
        assertEquals("Second", test.get(Calendar.SECOND), 0);
        
        //SS == 59 
        ParserMemoryMain.setProper(ParserMemoryMain.setTime("2359", test));
        assertEquals("Second", test.get(Calendar.SECOND), 59);
    }
    
    //@@author A0143378Y
    private void test_reduce(){
        String abc = ParserMemoryMain.reduceToInt("iw1j2h3b4nb5h6@@@7**8((90##");
        assertEquals("A string of number!", abc, "1234567890");
        
        abc = ParserMemoryMain.reduceToIntAndChar("~~1122Angel&Demons8899~~");
        assertEquals("A string of number and char!", abc, "1122AngelDemons8899");  
    }
    
    //@@author A0143378Y
    private void test_isUselessCommand(){
        assertTrue(ParserMemoryMain.isUselessCommand("#(@("));
        assertFalse(ParserMemoryMain.isUselessCommand("a#(@("));
        assertFalse(ParserMemoryMain.isUselessCommand("0#(@("));
    }
}