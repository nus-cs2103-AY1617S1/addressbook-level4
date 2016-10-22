package seedu.todo.model;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Rule;

import static org.junit.Assert.assertFalse;

public class RecurrenceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void test_isValidRecurrenceDesc() throws IllegalValueException {
        Recurrence recurrence = new Recurrence(null);
        
        String[] validInputs = {"every monday", "every week", "every year", "everyday", 
                                "every friday 16:00 to 18:00", null};
        String[] invalidInputs = {"tomorrow", "randome", "every happy hour"};
        
        
        for (String input : validInputs) {
            assertTrue(recurrence.isValidRecurrenceDesc(input));
        }
        
        for (String input : invalidInputs) {
            assertFalse(recurrence.isValidRecurrenceDesc(input));
        }
    }
        
    @Test
    public void test_updateTaskDateWeek() throws IllegalValueException {
        
        Task tEveryWeek = new Task(new Name("tEveryMonday"), new Detail(""), 
                new TaskDate("today", TaskDate.TASK_DATE_ON), 
                new TaskDate("", TaskDate.TASK_DATE_BY), 
                new Recurrence("every week"));
        
        LocalDate expectedDate = tEveryWeek.getOnDate().getDate().plusWeeks(1);
        tEveryWeek.getRecurrence().updateTaskDate(tEveryWeek);
        LocalDate actualDate = tEveryWeek.getOnDate().getDate();
        
        assertEquals(expectedDate, actualDate);
    }
    
    @Test
    public void test_updateTaskDateYear() throws IllegalValueException {
        
        Task tEveryWeek = new Task(new Name("tEveryMonday"), new Detail(""), 
                new TaskDate("today", TaskDate.TASK_DATE_ON), 
                new TaskDate("", TaskDate.TASK_DATE_BY), 
                new Recurrence("every year"));
        
        LocalDate expectedDate = tEveryWeek.getOnDate().getDate().plusYears(1);
        tEveryWeek.getRecurrence().updateTaskDate(tEveryWeek);
        LocalDate actualDate = tEveryWeek.getOnDate().getDate();
        
        assertEquals(expectedDate, actualDate);
    }
    
    @Test
    public void test_updateTaskDateDay() throws IllegalValueException {
        
        Task tEveryWeek = new Task(new Name("tEveryMonday"), new Detail(""), 
                new TaskDate("today", TaskDate.TASK_DATE_ON), 
                new TaskDate("", TaskDate.TASK_DATE_BY), 
                new Recurrence("every day"));
        
        LocalDate expectedDate = tEveryWeek.getOnDate().getDate().plusDays(1);
        tEveryWeek.getRecurrence().updateTaskDate(tEveryWeek);
        LocalDate actualDate = tEveryWeek.getOnDate().getDate();
        
        assertEquals(expectedDate, actualDate);
    }
    
}
