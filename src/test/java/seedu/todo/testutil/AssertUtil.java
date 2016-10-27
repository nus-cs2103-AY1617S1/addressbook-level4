package seedu.todo.testutil;

import java.time.LocalDate;

import seedu.todo.guitests.guihandles.TaskListDateItemHandle;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * @@author A0139812A
 */
public class AssertUtil {

    public static void assertSameDate(LocalDate date, TaskListDateItemHandle dateItemHandle) {
        assertNotNull(date);
        assertNotNull(dateItemHandle.getDate());
        assertTrue(dateItemHandle.getDate().isEqual(date));
    }
    
}
