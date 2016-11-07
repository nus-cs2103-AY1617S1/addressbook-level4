package seedu.todo.commons;

import org.junit.*;
import static org.junit.Assert.*;

import seedu.todo.models.CalendarItem;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

//@@author A0093907W
public class EphemeralDBTest {
    
    @Test
    public void ephemeral_testSingleton_match() {
        EphemeralDB one = EphemeralDB.getInstance();
        EphemeralDB two = EphemeralDB.getInstance();
        assertEquals(one, two);
    }
    
    @Test
    public void ephemeral_testCalendarItems_found() {
        CalendarItem task = new Task();
        CalendarItem event = new Event();
        EphemeralDB db = EphemeralDB.getInstance();
        db.addToDisplayedCalendarItems(task);
        db.addToDisplayedCalendarItems(event);
        assertEquals(db.getCalendarItemsByDisplayedId(1), task);
        assertEquals(db.getCalendarItemsByDisplayedId(2), event);
    }
    
    @Test
    public void ephemeral_missingCalendarItem_notFound() {
        EphemeralDB db = EphemeralDB.getInstance();
        assertEquals(db.getCalendarItemsByDisplayedId(0), null);
        assertEquals(db.getCalendarItemsByDisplayedId(3), null);
    }
    
    @Test
    public void ephemeral_clearCalendarItems_notFound() {
        CalendarItem task = new Task();
        CalendarItem event = new Event();
        EphemeralDB db = EphemeralDB.getInstance();
        db.addToDisplayedCalendarItems(task);
        db.addToDisplayedCalendarItems(event);
        db.clearDisplayedCalendarItems();
        assertEquals(db.getCalendarItemsByDisplayedId(1), null);
    }
}
