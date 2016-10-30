package harmony.mastermind.storage;

import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestStorage { 
    //@@author A0143378Y
    @Test
    public void test() { 
        testReadFromFile_IO();
    }
    
    //@@author A0143378Y
    private static void testReadFromFile_IO() { 
        Memory testMem = new Memory();
        Storage.checkForFileExists(testMem);
        
        testMem_getTask(testMem);
        testMem_getDeadline(testMem);
    }
    
    //@@author A0143378Y
    private static void testMem_getTask(Memory mem) { 
        GenericMemory task = new GenericMemory("Task", "I am hungry", "very hungry");
        assertEquals("test if they are the same", testTwoTasks(mem.get(0), task), true);
    }
    
    //@@author A0143378Y
    private static void testMem_getDeadline(Memory mem) { 
        Calendar end = new GregorianCalendar();
        end.set(2014, 1, 27, 23, 59);
        
        GenericMemory deadline = new GenericMemory("Deadline", "still hungry", "more food needed", end);
        assertEquals("test if they are the same", testTwoDeadlines(mem.get(1), deadline), true);
        
    }
    
    //@@author A0143378Y
    private static boolean testTwoTasks(GenericMemory a, GenericMemory b) { 
        return a.getType().equals(b.getType()) && 
                a.getDescription().equals(b.getDescription()) && 
                a.getName().equals(b.getName());
    }
    
    //@@author A0143378Y
    private static boolean testTwoDeadlines(GenericMemory a, GenericMemory b) { 
        return testTwoTasks(a, b) && 
                testTwoCalendar(a.getEnd(), b.getEnd());
    }
    
    //@@author A0143378Y
    private static boolean testTwoCalendar(Calendar a, Calendar b) { 
        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR) && 
                a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && 
                a.get(Calendar.DATE) == b.get(Calendar.DATE);
    }
}