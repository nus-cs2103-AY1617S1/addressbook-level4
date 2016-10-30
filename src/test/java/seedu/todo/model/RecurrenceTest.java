//@@author A0093896H
package seedu.todo.model;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.Recurrence;
import seedu.todo.model.task.Recurrence.Frequency;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import org.junit.Rule;
import static org.junit.Assert.assertEquals;

public class RecurrenceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    LocalDateTime ldt = LocalDateTime.now();
    
    @Test
    public void recurrence_init_test() throws IllegalValueException {
        Recurrence r = new Recurrence(Frequency.NONE);
        assertEquals(r.getFreq(), Recurrence.Frequency.NONE);
        
        
    }
    
    @Test
    public void execute_updateTaskDateWeek_successful() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        Task toBeRecur = helper.generateFullTask(0);
        
        toBeRecur.setRecurrence(new Recurrence(Frequency.WEEK));
        LocalDateTime oldOnDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getOnDate().getDate(), toBeRecur.getOnDate().getTime());
        
        LocalDateTime oldByDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getByDate().getDate(), toBeRecur.getByDate().getTime());
        
        toBeRecur.getRecurrence().updateTaskDate(toBeRecur);
        
        LocalDateTime newOnDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getOnDate().getDate(), toBeRecur.getOnDate().getTime());
        
        LocalDateTime newByDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getByDate().getDate(), toBeRecur.getByDate().getTime());
        
        assertEquals(oldOnDateTime.plusWeeks(1), newOnDateTime);
        assertEquals(oldByDateTime.plusWeeks(1), newByDateTime);
        
    }
    
    @Test
    public void execute_updateTaskDateDay_successful() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        Task toBeRecur = helper.generateFullTask(0);
        
        toBeRecur.setRecurrence(new Recurrence(Frequency.DAY));
        LocalDateTime oldOnDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getOnDate().getDate(), toBeRecur.getOnDate().getTime());
        
        LocalDateTime oldByDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getByDate().getDate(), toBeRecur.getByDate().getTime());
        
        toBeRecur.getRecurrence().updateTaskDate(toBeRecur);
        
        LocalDateTime newOnDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getOnDate().getDate(), toBeRecur.getOnDate().getTime());
        
        LocalDateTime newByDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getByDate().getDate(), toBeRecur.getByDate().getTime());
        
        assertEquals(oldOnDateTime.plusDays(1), newOnDateTime);
        assertEquals(oldByDateTime.plusDays(1), newByDateTime);
        
    }
    
    @Test
    public void execute_updateTaskDateMonth_successful() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        Task toBeRecur = helper.generateFullTask(0);
        
        toBeRecur.setRecurrence(new Recurrence(Frequency.MONTH));
        LocalDateTime oldOnDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getOnDate().getDate(), toBeRecur.getOnDate().getTime());
        
        LocalDateTime oldByDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getByDate().getDate(), toBeRecur.getByDate().getTime());
        
        toBeRecur.getRecurrence().updateTaskDate(toBeRecur);
        
        LocalDateTime newOnDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getOnDate().getDate(), toBeRecur.getOnDate().getTime());
        
        LocalDateTime newByDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getByDate().getDate(), toBeRecur.getByDate().getTime());
        
        assertEquals(oldOnDateTime.plusMonths(1), newOnDateTime);
        assertEquals(oldByDateTime.plusMonths(1), newByDateTime);
        
    }
    
    @Test
    public void execute_updateTaskDateYear_successful() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        Task toBeRecur = helper.generateFullTask(0);
        
        toBeRecur.setRecurrence(new Recurrence(Frequency.YEAR));
        LocalDateTime oldOnDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getOnDate().getDate(), toBeRecur.getOnDate().getTime());
        
        LocalDateTime oldByDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getByDate().getDate(), toBeRecur.getByDate().getTime());
        
        toBeRecur.getRecurrence().updateTaskDate(toBeRecur);
        
        LocalDateTime newOnDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getOnDate().getDate(), toBeRecur.getOnDate().getTime());
        
        LocalDateTime newByDateTime = DateTimeUtil.combineLocalDateAndTime(
                toBeRecur.getByDate().getDate(), toBeRecur.getByDate().getTime());
        
        assertEquals(oldOnDateTime.plusYears(1), newOnDateTime);
        assertEquals(oldByDateTime.plusYears(1), newByDateTime);
        
    }
     
    
}
