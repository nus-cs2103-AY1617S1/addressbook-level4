package seedu.todo.commons;

import org.junit.*;
import static org.junit.Assert.*;

import seedu.todo.models.CalendarItem;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

public class EphemeralDBTest {
    
    @Test
    public void check_singleton() {
        EphemeralDB one = EphemeralDB.getInstance();
        EphemeralDB two = EphemeralDB.getInstance();
        assertEquals(one, two);
    }
    
    @Test
    public void test_calendar_items() {
        CalendarItem task = new Task();
        CalendarItem event = new Event();
        EphemeralDB db = EphemeralDB.getInstance();
        db.addToDisplayedCalendarItems(task);
        db.addToDisplayedCalendarItems(event);
        assertEquals(db.getCalendarItemsByDisplayedId(1), task);
        assertEquals(db.getCalendarItemsByDisplayedId(2), event);
    }
    
    @Test
    public void test_missing_calendar_item() {
        EphemeralDB db = EphemeralDB.getInstance();
        assertEquals(db.getCalendarItemsByDisplayedId(0), null);
        assertEquals(db.getCalendarItemsByDisplayedId(3), null);
    }
    
}
