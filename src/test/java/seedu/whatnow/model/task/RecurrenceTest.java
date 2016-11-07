package seedu.whatnow.model.task;

import org.junit.Test;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.UniqueTagList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class RecurrenceTest {
    
    @Test
    public void getterForClass_recurrenceObjectInstantiated_allAttributesCorrect() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
       
        assertTrue("day".equals(test.getPeriod()));
        assertTrue("12/12/2222".equals(test.getTaskDate()));
        assertTrue("12/12/2222".equals(test.getStartDate()));
        assertTrue("14/12/2222".equals(test.getEndDate()));
        assertTrue("14/12/2222".equals(test.getEndPeriod()));
    }
    
    @Test
    public void setterForClass_recurrenceObjectInstantiated_allAttributesCorrect() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        test.setPeriod("week");
        test.setTaskDate("22/22/2222");
        test.setStartDate("22/22/2222");
        test.setEndDate("22/22/2222");
        test.setEndPeriod("22/22/2222");
       
        assertTrue("week".equals(test.getPeriod()));
        assertTrue("22/22/2222".equals(test.getTaskDate()));
        assertTrue("22/22/2222".equals(test.getStartDate()));
        assertTrue("22/22/2222".equals(test.getEndDate()));
        assertTrue("22/22/2222".equals(test.getEndPeriod()));
    }
    
    @Test
    public void hasRecurring_recurrenceObjectInstantiated_recurringTaskExists() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertTrue(test.hasRecurring());
    }
    
    @Test
    public void hasNextTask_pastEndPeriod_noNextTask() {
        Recurrence test = new Recurrence("week", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertFalse(test.hasNextTask("15/12/2222"));
    }
    
    @Test
    public void hasNextTask_beforeEndPeriod_noNextTask() {
        Recurrence test = new Recurrence("week", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertTrue(test.hasNextTask("13/12/2222"));
    }
    
    @Test
    public void hasNextTask_invalidDate_noNextTask() {
        Recurrence test = new Recurrence("week", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertFalse(test.hasNextTask("rubbish"));
    }
    
    @Test
    public void getNextDay_validDate_nextDayFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
       
        assertEquals(test.getNextDay("13/12/2222"), "14/12/2222");
    }
    
    @Test
    public void getNextDay_lastDayOfMonth_nextDayFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextDay("31/01/2222"), "01/02/2222");
    }
    
    @Test
    public void getNextDay_lastDayofYear_nextDayFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextDay("31/12/2222"), "01/01/2223");
    }
    
    @Test
    public void getNextDay_leapYear_nextDayFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextDay("28/02/2224"), "29/02/2224");
    }
    
    @Test
    public void getNextWeek_validDate_nextWeekDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
       
        assertEquals(test.getNextWeek("12/12/2224"), "19/12/2224");
    }
    
    @Test
    public void getNextWeek_lastDayOfMonth_nextWeekDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextWeek("31/01/2222"), "07/02/2222");
    }
    
    @Test
    public void getNextWeek_lastDayofYear_nextWeekDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextWeek("31/12/2222"), "07/01/2223");
    }
    
    @Test
    public void getNextWeek_nextDateOnleapYearDay_nextWeekDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextWeek("22/02/2224"), "29/02/2224");
    }
    
    @Test
    public void getNextWeek_currentDateOnDayBeforeLeapDay_nextWeekDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextWeek("28/02/2224"), "06/03/2224");
    }
    
    @Test
    public void getNextMonth_validDate_nextMonthDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
     
        assertEquals(test.getNextMonth("12/10/2224"), "12/11/2224");
    }
    
    @Test
    public void getNextMonth_lastDayOfMonth_nextMonthDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
       
        assertEquals(test.getNextMonth("31/01/2222"), "28/02/2222");
    }
   
    @Test
    public void getNextMonth_lastDayofYear_nextMonthDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextMonth("31/12/2222"), "31/01/2223");
    }
    
    @Test
    public void getNextYear_validDate_nextYearDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        
        assertEquals(test.getNextYear("12/10/2224"), "12/10/2225");
    }
    
    @Test
    public void getNextYear_leapYear_nextYearDateFound() {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
       
        assertEquals(test.getNextYear("29/02/2224"), "28/02/2225");
    }
    
    @Test
    public void getNextTask_dailyRecurring_nextTaskReturned() throws IllegalValueException {
        Recurrence test = new Recurrence("day", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        Task current = new Task(new Name("current"), "12/12/2222", "12/12/2222",
                "14/12/2222", "12:00am", "12:00am", "11:59pm", 
                "12/12/2222", "12/12/2222", new UniqueTagList(), "incomplete", "not_floating");
        Task next = test.getNextTask(current);
        
        assertEquals(next.getTaskDate(), "13/12/2222");
    }
    
    @Test
    public void getNextTask_weeklyRecurring_nextTaskReturned() throws IllegalValueException {
        Recurrence test = new Recurrence("week", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        Task current = new Task(new Name("current"), "12/12/2222", "12/12/2222",
                "14/12/2222", "12:00am", "12:00am", "11:59pm", 
                "12/12/2222", "12/12/2222", new UniqueTagList(), "incomplete", "not_floating");
        Task next = test.getNextTask(current);
        
        assertEquals(next.getTaskDate(), "19/12/2222");
    }
    
    @Test
    public void getNextTask_monthlyRecurring_nextTaskReturned() throws IllegalValueException {
        Recurrence test = new Recurrence("month", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        Task current = new Task(new Name("current"), "12/12/2222", "12/12/2222",
                "14/12/2222", "12:00am", "12:00am", "11:59pm", 
                "12/12/2222", "12/12/2222", new UniqueTagList(), "incomplete", "not_floating");
        Task next = test.getNextTask(current);
        
        assertEquals(next.getTaskDate(), "12/01/2223");
    }
    
    @Test
    public void getNextTask_monthlyRecurringEvent_nextTaskReturned() throws IllegalValueException {
        Recurrence test = new Recurrence("month", null, "12/12/2222", "14/12/2222", "14/12/2222");
        Task current = new Task(new Name("current"), "12/12/2222", "12/12/2222",
                "14/12/2222", "12:00am", "12:00am", "11:59pm", 
                "12/12/2222", "12/12/2222", new UniqueTagList(), "incomplete", "not_floating");
        Task next = test.getNextTask(current);
        
        assertEquals(next.getStartDate(), "12/01/2223");
        assertEquals(next.getEndDate(), "14/01/2223");
    }
    
    @Test
    public void getNextTask_yearlyRecurring_nextTaskReturned() throws IllegalValueException {
        Recurrence test = new Recurrence("year", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        Task current = new Task(new Name("current"), "12/12/2222", "12/12/2222",
                "14/12/2222", "12:00am", "12:00am", "11:59pm", 
                "12/12/2222", "12/12/2222", new UniqueTagList(), "incomplete", "not_floating");
        Task next = test.getNextTask(current);
        
        assertEquals(next.getTaskDate(), "12/12/2223");
    }
    
    @Test
    public void getNextTask_yearlyRecurringEvent_nextTaskReturned() throws IllegalValueException {
        Recurrence test = new Recurrence("year", null, "12/12/2222", "14/12/2222", "14/12/2222");
        Task current = new Task(new Name("current"), "12/12/2222", "12/12/2222",
                "14/12/2222", "12:00am", "12:00am", "11:59pm", 
                "12/12/2222", "12/12/2222", new UniqueTagList(), "incomplete", "not_floating");
        Task next = test.getNextTask(current);
        
        assertEquals(next.getStartDate(), "12/12/2223");
        assertEquals(next.getEndDate(), "14/12/2223");
    }
    
    @Test
    public void toString_objectInstantiated_recurrenceTaskAsStringReturned() {
        Recurrence test = new Recurrence("year", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        assertEquals(test.toString(), "12/12/2222 to 14/12/2222 every year till 14/12/2222");
    }
    
    @Test
    public void equal_twoEqualRecurringTask_tasksAreEqual() {
        Recurrence test1 = new Recurrence("year", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        Recurrence test2 = new Recurrence("year", "12/12/2222", "12/12/2222", "14/12/2222", "14/12/2222");
        assertEquals(test1, test2);
    }
}
