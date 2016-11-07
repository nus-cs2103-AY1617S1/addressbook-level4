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
        testDays();
        testCompare();
        testStateType();
    }
    
    //@@author A0143378Y
    private void testSetType(GenericMemory testMethod) { 
        testMethod.setType("cat");
        assertEquals("type", testMethod.getType(), "cat");
    }
    
    //@@author A0143378Y
    private void testSetName(GenericMemory testMethod) { 
        testMethod.setName("Animals");
        assertEquals("Name", testMethod.getName(),"Animals");
    }
    
    //@@author A0143378Y
    private void testSetDescription(GenericMemory testMethod) { 
        testMethod.setDescription("An organism");
        assertEquals("Description", testMethod.getDescription(), "An organism");
    }
    
    //@@author A0143378Y
    private void testTime(GenericMemory testMethod) { 
        //2 parameters
        testMethod.setStartTime(03, 14);
        testMethod.setEndTime(07, 11);
        
        //3 parameters
        testMethod.setStartTime(03, 14, 19);
        testMethod.setEndTime(07, 11, 20);
    }
    
    //@@author A0143378Y
    private void testDate(GenericMemory testMethod) {
        testMethod.setStartDate(2014, 03, 27);
        testMethod.setEndDate(2015, 02, 05);
    }
    
    //@@author A0143378Y
    private void testNewState(GenericMemory testMethod) { 
        testMethod.setState(0);
        assertEquals("State", testMethod.getState(), 0);
    }
    
    //@@author A0143378Y
    private void testToString() { 
        taskToString();
        deadlineToString();
        eventToString();
    }
    
    //@@author A0143378Y
    private void taskToString() { 
        //Task
        GenericMemory testTask = new GenericMemory("Task", "name2", "description2");
        String taskResult = "Type: Task" + "\n" + "Name: name2" + "\n" + "Description : description2" + "\n" + "Status: Incomplete";
        assertEquals("Task to String", testTask.toString(), taskResult);
        
        //Task description
        String descriptionResult = "description2" + "\n" + "Description : description2";
        assertEquals("Description", testTask.descriptionToString(testTask.getDescription()), descriptionResult);
        
        //Task deadline to string
        String taskDeadline = "Type: Task" + "\n" + "Name: name2" + "\n" + "Description : description2"
                                + "\n" + "Status: Incomplete" + "\n" + "Status: Incomplete";
        assertEquals("Deadline", testTask.taskDeadlineStateToString(testTask.toString()), taskDeadline);
        
        //Set state to Completed
        testTask.setState(1);
        String taskDeadline2 = "Type: Task" + "\n" + "Name: name2" + "\n" + "Description : description2"
                                + "\n" + "Status: Completed" + "\n" + "Status: Completed";
        assertEquals("Deadline", testTask.taskDeadlineStateToString(testTask.toString()), taskDeadline2);
        
        //Set state to overdue
        testTask.setState(2);
        String taskDeadline3 = "Type: Task" + "\n" + "Name: name2" + "\n" + "Description : description2"
                                + "\n" + "Status: Overdue" + "\n" + "Status: Overdue";
        assertEquals("Deadline", testTask.taskDeadlineStateToString(testTask.toString()), taskDeadline3);
    }
    
    //@@author A0143378Y
    private void deadlineToString() { 
        //Deadline
        GenericMemory testDeadline = new GenericMemory("Deadline", "name1", "description1", end);
        String deadlineResult = "Type: Deadline" + "\n" + "Name: name1" + "\n" + "Description : description1" 
                                    + "\n" + "Due by: 5/3/15 Thurs 07:11 AM" + "\n" + "Status: Incomplete";
        assertEquals("Deadline to String", testDeadline.toString(), deadlineResult);
        
        //Deadline date
        String deadlineEndDateResult = testDeadline.deadlineDateToString(testDeadline.getEnd().toString());
        assertEquals("Deadline End Date", testDeadline.deadlineDateToString(testDeadline.getEnd().toString()), deadlineEndDateResult);
        
    }
    
    //@@author A0143378Y
    private void eventToString() { 
        //Event
        GenericMemory testEvent = new GenericMemory("Event", "name", "description", start, end, 0);
        String eventResult = "Type: Event" + "\n" + "Name: name" + "\n" + "Description : description"
                              + "\n" + "Start: 27/4/14 Sun 03:14 AM" + "\n" + "End: 5/3/15 Thurs 07:11 AM"
                              + "\n" + "Status: Upcoming";
        assertEquals("Event to String", testEvent.toString(), eventResult);
        
        //Event dates to string 
        String eventDates = "Type: Event" + "\n" + "Name: name" + "\n" + "Description : description" 
                             + "\n" + "Start: 27/4/14 Sun 03:14 AM" + "\n" + "End: 5/3/15 Thurs 07:11 AM"
                             + "\n" + "Status: Upcoming"
                             + "\n" + "Start: 27/4/14 Sun 03:14 AM" + "\n" + "End: 5/3/15 Thurs 07:11 AM";
        assertEquals("Event dates to String", testEvent.eventDatesToString(testEvent.toString()), eventDates);
        
        //Event state to string 
        String eventState = "Type: Event" + "\n" + "Name: name" + "\n" + "Description : description" 
                            + "\n" + "Start: 27/4/14 Sun 03:14 AM" + "\n" + "End: 5/3/15 Thurs 07:11 AM"
                            + "\n" + "Status: Upcoming" + "\n" + "Status: Upcoming";
        assertEquals("Event state to String", testEvent.eventStateToString(testEvent.toString()), eventState);
        
        //Set State to over 
        testEvent.setState(1);
        String eventState2 = "Type: Event" + "\n" + "Name: name" + "\n" + "Description : description" 
                + "\n" + "Start: 27/4/14 Sun 03:14 AM" + "\n" + "End: 5/3/15 Thurs 07:11 AM"
                + "\n" + "Status: Over" + "\n" + "Status: Over";
        assertEquals("Event state to String", testEvent.eventStateToString(testEvent.toString()), eventState2);
        
        //Set state to be ongoing
        testEvent.setState(2);
        String eventState3 = "Type: Event" + "\n" + "Name: name" + "\n" + "Description : description" 
                + "\n" + "Start: 27/4/14 Sun 03:14 AM" + "\n" + "End: 5/3/15 Thurs 07:11 AM"
                + "\n" + "Status: Ongoing" + "\n" + "Status: Ongoing";
        assertEquals("Event state to String", testEvent.eventStateToString(testEvent.toString()), eventState3);
        
        
        //test getDate
        assertEquals("Event date", testEvent.getDate(start), "27/4/14 Sun");
        //test getDate null 
        Calendar invalidDate = new GregorianCalendar();
        invalidDate = null;
        assertEquals("Invalid event Date", testEvent.getDate(invalidDate), null);
        
        //test getTime
        assertEquals("Event end time", testEvent.getTime(end), "07:11 AM");
        //test getTime null
        Calendar invalidTime = new GregorianCalendar();
        invalidTime = null;
        assertEquals("Invalid event time", testEvent.getTime(invalidTime), null);
    }
    
    //@@author A0143378Y
    private void testDays() { 
        Calendar Sunday = new GregorianCalendar();
        Sunday.set(2014, 03, 20, 03, 14, 20);
        assertEquals("Sunday", GenericMemory.dayOfTheWeek(Sunday), "Sun");
        //test AM_PM
        assertEquals("AM", GenericMemory.AM_PM(Sunday), "AM");
        
        Calendar Monday = new GregorianCalendar();
        Monday.set(2014, 03, 21, 13, 01, 20);
        assertEquals("Monday", GenericMemory.dayOfTheWeek(Monday), "Mon");
        //test AM_PM
        assertEquals("PM", GenericMemory.AM_PM(Monday), "PM");
        //Test HH 
        assertEquals("Hour", GenericMemory.hour(Monday), "01");
        //Test MM 
        assertEquals("Minute", GenericMemory.min(Monday), "01");
        
        Calendar Tuesday = new GregorianCalendar();
        Tuesday.set(2014, 03, 22, 03, 14, 20);
        assertEquals("Tuesday", GenericMemory.dayOfTheWeek(Tuesday), "Tues");
        //Test HH 
        assertEquals("Hour", GenericMemory.hour(Tuesday), "03");
        //Test MM 
        assertEquals("Minute", GenericMemory.min(Tuesday), "14");
        
        Calendar Wednesday = new GregorianCalendar();
        Wednesday.set(2014, 03, 23, 12, 14, 20);
        assertEquals("Wednesday", GenericMemory.dayOfTheWeek(Wednesday), "Wed");
        //Test HH 
        assertEquals("Hour", GenericMemory.hour(Wednesday), "12");
        
        Calendar Thursday = new GregorianCalendar();
        Thursday.set(2014, 03, 24, 03, 14, 20);
        assertEquals("Thursday", GenericMemory.dayOfTheWeek(Thursday), "Thurs");
        
        Calendar Friday = new GregorianCalendar();
        Friday.set(2014, 03, 25, 03, 14, 20);
        assertEquals("Friday", GenericMemory.dayOfTheWeek(Friday), "Fri");
        
        Calendar Saturday = new GregorianCalendar();
        Saturday.set(2014, 03, 26, 03, 14, 20);
        assertEquals("Saturday", GenericMemory.dayOfTheWeek(Saturday), "Sat");  
    }
    
    //@@author A0143378Y
    private void testCompare() { 
        testCompareTask();
        testCompareDeadline();
        testCompareEvent();
    }
    
    //@@author A0143378Y
    private void testCompareTask() { 
        GenericMemory testTask1 = new GenericMemory("task", "name2", "description2");
        GenericMemory testTask2 = new GenericMemory("task", "name3", "description3");
        assertEquals("Tasks", testTask1.compareTo(testTask2), -1);
    }
    
    //@@author A0143378Y
    private void testCompareDeadline() { 
        GenericMemory testDeadline1 = new GenericMemory("deadline", "name1", "description1", end);
        GenericMemory testDeadline2 = new GenericMemory("deadline2", "name2", "description2", end);
        assertEquals("Deadlines", testDeadline1.compareTo(testDeadline2), 0);
    }
    
    //@@author A0143378Y
    private void testCompareEvent() { 
        GenericMemory testEvent1 = new GenericMemory("event", "name", "description", start, end, 0);
        GenericMemory testEvent2 = new GenericMemory("event2", "name2", "description2", start, end, 0);
        assertEquals("Events", testEvent1.compareTo(testEvent2), 0);
        assertEquals("Event Comparison", testEvent1.eventCompare(testEvent2), 0);
    }
    
    //@@author A0143378Y
    private void testStateType() { 
        testTaskState();
        testDeadlineState();
        testEventState();
        testIncorrectState();
    }
    
    //@@author A0143378Y
    private void testTaskState() { 
        GenericMemory testTask1 = new GenericMemory("Task", "name2", "description2");
        assertEquals("No state yet", testTask1.getStateType(), "Incomplete");
    }
    
    //@@author A0143378Y
    private void testDeadlineState() { 
        GenericMemory testDeadline = new GenericMemory("Deadline", "name1", "description1", end);
        
        //Set state to incomplete
        testDeadline.setState(0);
        assertEquals("Incomplete", testDeadline.getStateType(), "Incomplete");
        
        //Set state to Completed
        testDeadline.setState(1);
        assertEquals("Completed", testDeadline.getStateType(), "Completed");
        
        //Set state to Overdue
        testDeadline.setState(2);
        assertEquals("Overdue", testDeadline.getStateType(), "Overdue");
    }
    
    //@@author A0143378Y
    private void testEventState() { 
        GenericMemory testEvent = new GenericMemory("Event", "name", "description", start, end, 0);
        
        //Upcoming
        assertEquals("Upcoming", testEvent.getStateType(), "Upcoming");
        
        //Set state to over
        testEvent.setState(1);
        assertEquals("Over", testEvent.getStateType(), "Over");
        
        //Set state to ongoing 
        testEvent.setState(2);
        assertEquals("Ongoing", testEvent.getStateType(), "Ongoing");
    }
    
    //@@author A0143378Y
    private void testIncorrectState() { 
        GenericMemory wrongState = new GenericMemory("aask", "name2", "description2");
        assertEquals("Wrong", wrongState.getStateType(), null);
    }
}