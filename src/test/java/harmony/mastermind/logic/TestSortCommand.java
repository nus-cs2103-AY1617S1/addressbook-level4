package harmony.mastermind.logic;

import org.junit.Test;

import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.logic.commands.Sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestSortCommand { 
    
    private ArrayList<GenericMemory> list = new ArrayList<GenericMemory>();
    
    //@@author A0143378Y
    @Test
    public void test() { 
        initializeList();
        testSplitMemory(list);
    }
    
    //@@author A0143378Y
    private void testSplitMemory(ArrayList<GenericMemory> list) { 
        Sort.sort(list);

        System.out.println(list.get(1));
        System.out.println(list.get(2));
        
        test_Event(list);
    }
    
    //@@author A0143378Y
    private void test_Event(ArrayList<GenericMemory> list) { 
        //Check if the type is Event
        assertEquals("Check if it is Event", list.get(0).getType(), "Event");
        
        //Check if the name is correct
        assertEquals("Check the name", list.get(0).getName(), "test3");
        
        //Check the description
        assertEquals("Check description", list.get(0).getDescription(), "do this3");
    }
    
    //@@author A0143378Y
    private void test_Deadline(ArrayList<GenericMemory> list) { 
        
    }
    
    //@@author A0143378Y
    private void initializeList() { 
        Calendar start = new GregorianCalendar();
        start.set(1990, 10, 1);
        
        Calendar end = new GregorianCalendar();
        end.set(1990, 10, 9);
        
        //Adding Task
        list.add(new GenericMemory("Task", "test1", "do this"));
        
        //Adding Deadlines
        list.add(new GenericMemory("Deadline", "test2", "do this2", end));
        
        //Adding Events
        list.add(new GenericMemory("Event", "test3", "do this3", start, end, 0));
    }
}