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
}
