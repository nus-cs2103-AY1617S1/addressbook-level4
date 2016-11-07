package harmony.mastermind.memory;

import java.util.Calendar;
import java.util.GregorianCalendar;

import harmony.mastermind.memory.GenericMemory;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestGenericMemory {
    
    private Calendar start = new GregorianCalendar();
    private Calendar end = new GregorianCalendar();
    
    @Test
    //@@author A0143378Y
    public void test() { 
        start.set(2014, 03, 27, 03, 14, 20);
        end.set(2015, 02, 05, 07, 11, 13);
        
        addEvent();
        addDeadline();
        addTask();
        
        testOtherMethods();
    }
    
    //@@author A0143378Y
    private void addEvent() { 
        GenericMemory testEvent = new GenericMemory("event", "name", "description", start, end, 0);
        assertEquals("Type", testEvent.getType(), "event");
        assertEquals("Name", testEvent.getName(), "name");
        assertEquals("Description", testEvent.getDescription(), "description");
        assertEquals("start time and date", testEvent.getStart(), start);
        assertEquals("end time and date", testEvent.getEnd(), end);
    }
    
    //@@author A0143378Y
    private void addDeadline() { 
        GenericMemory testDeadline = new GenericMemory("deadline", "name1", "description1", end);
        assertEquals("Type", testDeadline.getType(), "deadline");
        assertEquals("Name", testDeadline.getName(), "name1");
        assertEquals("Description", testDeadline.getDescription(), "description1");
        assertEquals("start time and date", testDeadline.getStart(), null);
        assertEquals("end time and date", testDeadline.getEnd(), end);
    }
    
    //@@author A0143378Y
    private void addTask() { 
        GenericMemory testTask = new GenericMemory("task", "name2", "description2");
        assertEquals("Type", testTask.getType(), "task");
        assertEquals("Name", testTask.getName(), "name2");
        assertEquals("Description", testTask.getDescription(), "description2");
        assertEquals("start time and date", testTask.getStart(), null);
        assertEquals("end time and date", testTask.getEnd(), null);
    }
    
    //@@author A0143378Y
    private void testOtherMethods() { 
        GenericMemory testMethod = new GenericMemory("Method", "name3", "description3");
        assertEquals("start time and date", testMethod.getStart(), null);
        testMethod.initStart();
        assertFalse("start time and date", testMethod.getStart() == null);
        
        assertEquals("end time and date", testMethod.getEnd(), null);
        testMethod.initEnd();
        assertFalse("start time and date", testMethod.getEnd() == null);
        
        testSetType(testMethod);        
        testSetName(testMethod);
        testSetDescription(testMethod);
        testTime(testMethod);
        testDate(testMethod);
        testNewState(testMethod);
        testToString();
    }
    
    private void testSetType(GenericMemory testMethod) { 
        testMethod.setType("cat");
        assertEquals("type", testMethod.getType(), "cat");
    }
    
    private void testSetName(GenericMemory testMethod) { 
        testMethod.setName("Animals");
        assertEquals("Name", testMethod.getName(),"Animals");
    }
    
    private void testSetDescription(GenericMemory testMethod) { 
        testMethod.setDescription("An organism");
        assertEquals("Description", testMethod.getDescription(), "An organism");
    }
    
    private void testTime(GenericMemory testMethod) { 
        //2 parameters
        testMethod.setStartTime(03, 14);
        testMethod.setEndTime(07, 11);
        
        //3 parameters
        testMethod.setStartTime(03, 14, 19);
        testMethod.setEndTime(07, 11, 20);
    }
    
    private void testDate(GenericMemory testMethod) {
        testMethod.setStartDate(2014, 03, 27);
        testMethod.setEndDate(2015, 02, 05);
    }
    
    private void testNewState(GenericMemory testMethod) { 
        testMethod.setState(0);
        assertEquals("State", testMethod.getState(), 0);
    }
    
    private void testToString() { 
        taskToString();
        deadlineToString();
        eventToString();
    }
    
    private void taskToString() { 
        //Task
        GenericMemory testTask = new GenericMemory("task", "name2", "description2");
        String taskResult = "Type: task" + "\n" + "Name: name2" + "\n" + "Description : description2";
        assertEquals("Task to String", testTask.toString(), taskResult);
        
        //Task description
        String descriptionResult = "description2" + "\n" + "Description : description2";
        assertEquals("Description", testTask.descriptionToString(testTask.getDescription()), descriptionResult);
        
        //Task deadline to string
        String taskDeadline = "Type: task" + "\n" + "Name: name2" + "\n" + "Description : description2"
                                + "\n" + "Status: Incomplete";
        assertEquals("Deadline", testTask.taskDeadlineStateToString(testTask.toString()), taskDeadline);
    }
    
    private void deadlineToString() { 
        //Deadline
        GenericMemory testDeadline = new GenericMemory("deadline", "name1", "description1", end);
        String deadlineResult = "Type: deadline" + "\n" + "Name: name1" + "\n" + "Description : description1";
        assertEquals("Deadline to String", testDeadline.toString(), deadlineResult);
        
        //Deadline date
        String deadlineEndDateResult = testDeadline.deadlineDateToString(testDeadline.getEnd().toString());
        assertEquals("Deadline End Date", testDeadline.deadlineDateToString(testDeadline.getEnd().toString()), testDeadline.deadlineDateToString(testDeadline.getEnd().toString()));
        
    }
    
    private void eventToString() { 
        //Event
        GenericMemory testEvent = new GenericMemory("event", "name", "description", start, end, 0);
        String eventResult = "Type: event" + "\n" + "Name: name" + "\n" + "Description : description";
        assertEquals("Event to String", testEvent.toString(), eventResult);
        
        //Event dates to string 
        String eventDates = "Type: event" + "\n" + "Name: name" + "\n" + "Description : description" 
                                + "\n" + "Start: 27/4/14 Sun 03:14 AM" + "\n" + "End: 5/3/15 Thurs 07:11 AM";
        assertEquals("Event dates to String", testEvent.eventDatesToString(testEvent.toString()), eventDates);
        
        //Event state to string 
        String eventState = "Type: event" + "\n" + "Name: name" + "\n" + "Description : description" + "\n"
                                + "Status: Upcoming";
        assertEquals("Event state to String", testEvent.eventStateToString(testEvent.toString()), eventState);
        
        //test getDate
        assertEquals("Event date", testEvent.getDate(start), "27/4/14 Sun");
        
        //test getTime
        assertEquals("Event end time", testEvent.getTime(end), "07:11 AM");
        
    }
}