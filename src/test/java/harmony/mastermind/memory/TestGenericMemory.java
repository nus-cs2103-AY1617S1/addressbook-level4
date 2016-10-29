package harmony.mastermind.memory;

import java.util.Calendar;
import java.util.GregorianCalendar;

import harmony.mastermind.memory.GenericMemory;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGenericMemory {
    
    private Calendar start = new GregorianCalendar();
    private Calendar end = new GregorianCalendar();
    
    @Test
    //@@author A0143378Y
    public void test() { 
        start.set(2014, 03, 27, 03, 14, 20);
        end.set(2015, 02, 05, 07, 11, 13);
        
        addEvent();
    }
    
    private void addEvent() { 
        GenericMemory testEvent = new GenericMemory("event", "name", "description", start, end, 0);
        assertEquals("Type", testEvent.getType(), "event");
        assertEquals("Name", testEvent.getName(), "name");
        assertEquals("Description", testEvent.getDescription(), "description");
        assertEquals("start time and date", testEvent.getStart(), start);
        assertEquals("end time and date", testEvent.getEnd(), end);
    }
}