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
    
}
