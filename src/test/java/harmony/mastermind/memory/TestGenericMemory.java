package harmony.mastermind.memory;

import java.util.Calendar;
import java.util.GregorianCalendar;

import harmony.mastermind.memory.GenericMemory;

import org.junit.Test;

public class TestGenericMemory {
    
    private Calendar start = new GregorianCalendar();
    private Calendar end = new GregorianCalendar();
    
    //@@author A0143378Y
    public void test() { 
        start.set(2014, 03, 27, 03, 14, 20);
        end.set(2015, 02, 05, 07, 11, 13);
    }
}