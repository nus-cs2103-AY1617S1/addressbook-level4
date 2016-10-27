package seedu.todo.models;

import java.time.LocalDateTime;

import org.junit.*;
import static org.junit.Assert.*;

public class CalendarItemTests {
    
    @Test
    public void test_event_startdate() {
        Event event = new Event();
        LocalDateTime time = LocalDateTime.now();
        event.setStartDate(time);
        assertEquals(event.getStartDate(), time);
    }
    
    @Test
    public void test_event_enddate() {
        Event event = new Event();
        LocalDateTime time = LocalDateTime.now();
        event.setEndDate(time);
        assertEquals(event.getEndDate(), time);
    }
    
    @Test
    public void test_event_name() {
        Event event = new Event();
        String name = "abcdef";
        event.setName(name);
        assertEquals(event.getName(), name);
    }
    
    @Test
    public void test_event_calendardt() {
        Event event = new Event();
        LocalDateTime time = LocalDateTime.now();
        event.setCalendarDT(time);
        assertEquals(event.getCalendarDT(), time);
    }
    
    @Test
    public void test_event_calendardt_is_start() {
        Event event = new Event();
        LocalDateTime time1 = LocalDateTime.now();
        event.setCalendarDT(time1);
        assertEquals(event.getStartDate(), time1);
        
        LocalDateTime time2 = LocalDateTime.now();
        event.setStartDate(time2);
        assertEquals(event.getCalendarDT(), time2);
    }
    
    @Test
    public void test_event_is_over() {
        Event event = new Event();
        assertEquals(event.isOver(), false);
        
        LocalDateTime time = LocalDateTime.now().minusSeconds(10);
        event.setEndDate(time);
        assertEquals(event.isOver(), true);
    }
    
    @Test
    public void test_task_duedate() {
        Task task = new Task();
        LocalDateTime time = LocalDateTime.now();
        task.setDueDate(time);
        assertEquals(task.getDueDate(), time);
    }
    
    @Test
    public void test_task_name() {
        Task task = new Task();
        String name = "abcdef";
        task.setName(name);
        assertEquals(task.getName(), name);
    }
    
    @Test
    public void test_task_calendardt() {
        Task task = new Task();
        LocalDateTime time = LocalDateTime.now();
        task.setCalendarDT(time);
        assertEquals(task.getCalendarDT(), time);
    }
    
    @Test
    public void test_task_calendardt_is_duedate() {
        Task task = new Task();
        LocalDateTime time1 = LocalDateTime.now();
        task.setCalendarDT(time1);
        assertEquals(task.getDueDate(), time1);
        
        LocalDateTime time2 = LocalDateTime.now();
        task.setDueDate(time2);
        assertEquals(task.getCalendarDT(), time2);
    }
    
    @Test
    public void test_task_is_over() {
        Task task = new Task();
        assertEquals(task.isOver(), false);
        
        LocalDateTime time = LocalDateTime.now().minusSeconds(10);
        task.setDueDate(time);
        assertEquals(task.isOver(), true);
    }
    
    @Test
    public void test_task_complete() {
        Task task = new Task();
        assertEquals(task.isCompleted(), false);
        
        task.setCompleted();
        assertEquals(task.isCompleted(), true);
        
        task.setIncomplete();
        assertEquals(task.isCompleted(), false);
    }
    
    @Test
    public void test_task_tag_list_not_null() {
        Task task = new Task();
        assertNotNull(task.getTagList());
    }
    
    @Test
    public void test_task_tag_list_add_tag() {
        Task task = new Task();
        assertEquals(task.getTagList().size(), 0);
        assertTrue(task.addTag("A"));
        assertTrue(task.addTag("B"));
        assertEquals(task.getTagList().size(), 2);
    }
    
    @Test
    public void test_task_tag_list_add_tag_to_boundary() {
        Task task = new Task();
        for (int i = 0; i < 20; i ++) {
            task.addTag(Integer.toString(i));
        }
        assertEquals(task.getTagList().size(), 20);
        assertFalse(task.addTag("A"));
    }
    
    @Test
    public void test_task_tag_list_remove_tag_succesfully() {
        Task task = new Task();
        task.addTag("A");
        task.addTag("B");
        assertTrue(task.removeTag("A"));
    }
    
    @Test
    public void test_task_tag_list_remove_tag_unsuccessfully() {
        Task task = new Task();
        assertFalse(task.removeTag("A"));
    }
}
