package seedu.todo.testutil;

import java.time.LocalDate;

import seedu.todo.guitests.guihandles.TaskListDateItemHandle;

import static org.junit.Assert.assertTrue;

public class AssertUtil {

    public static void assertSameDate(LocalDate date, TaskListDateItemHandle dateItemHandle) {
        assertTrue(dateItemHandle.getDate().isEqual(date));
    }
    
}
