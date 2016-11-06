package guitests;

import org.junit.Test;

import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.logic.commands.Sort;

import static org.junit.Assert.assertEquals;

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
        
        test_Event(list);
        test_Deadline(list);
        test_Task(list);
    }
    
    //@@author A0143378Y
    private void test_Event(ArrayList<GenericMemory> list) { 
        //Check if the type is Event
        assertEquals("Check if it is Event", list.get(0).getType(), "Event");
        
        //Check if the name is correct
        assertEquals("Check the name", list.get(0).getName(), "test3");
        
        //Check the description
        assertEquals("Check description", list.get(0).getDescription(), "do this3");
        
        //Check status
        assertEquals("Check if it is incomplete", list.get(0).getState(), 0);
    }
    
    //@@author A0143378Y
    private void test_Deadline(ArrayList<GenericMemory> list) { 
        //Check if the type is Deadline
        assertEquals("Check if it is Deadline", list.get(1).getType(), "Deadline");
        
        //Check the name
        assertEquals("Check the name", list.get(1).getName(), "test2");
        
        //Check description
        assertEquals("Check description", list.get(1).getDescription(), "do this2");
        
        //Check status
        assertEquals("Check status", list.get(1).getState(), 0);
    }
    
    //@@author A0143378Y
    private void test_Task(ArrayList<GenericMemory> list) { 
        //Check if the type is Deadline
        assertEquals("Check if it is Task", list.get(2).getType(), "Task");
        
        //Check if the type is Deadline
        assertEquals("Check the name", list.get(2).getName(), "test1");
        
        //Check description
        assertEquals("Check description", list.get(2).getDescription(), "do this");
        
        //Check status
        assertEquals("Check status", list.get(2).getState(), 0);
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