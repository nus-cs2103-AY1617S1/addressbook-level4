package harmony.mastermind.storage;

import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestStorage { 
    //@@author A0143378Y
    public void test() { 
        testReadFromFile_IO();
    }
    
    private static void testReadFromFile_IO() { 
        Memory testMem = new Memory();
        Storage.checkForFileExists(testMem);
        
        testMem_getTask(testMem);
    }
    
    private static void testMem_getTask(Memory mem) { 
        GenericMemory task = new GenericMemory("Task", "I am hungry", "very hungry");
        assertEquals("test if they are the same", testTwoTasks(mem.get(0), task), true);
    }
    
    private static boolean testTwoTasks(GenericMemory a, GenericMemory b) { 
        return a.getType().equals(b.getType()) && 
                a.getDescription().equals(b.getDescription()) && 
                a.getName().equals(b.getName());
    }
}