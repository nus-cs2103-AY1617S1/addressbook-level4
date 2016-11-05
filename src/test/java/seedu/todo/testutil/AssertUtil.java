package seedu.todo.testutil;

import java.time.LocalDate;

import seedu.todo.guitests.guihandles.TaskListDateItemHandle;
import seedu.todo.guitests.guihandles.TaskListEventItemHandle;
import seedu.todo.guitests.guihandles.TaskListTaskItemHandle;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

import static org.junit.Assert.*;

/**
 * @@author A0139812A
 */
public class AssertUtil {
    
    public static void assertSameDate(LocalDate dateToTest, TaskListDateItemHandle dateItemHandle) {
        assertNotNull(dateToTest);
        assertNotNull(dateItemHandle);
        assertNotNull(dateItemHandle.getDate());
        assertTrue(dateItemHandle.getDate().isEqual(dateToTest));
    }
    
    public static void assertSameTaskName(Task taskToTest, TaskListTaskItemHandle taskItemHandle) {
        assertNotNull(taskToTest);
        assertNotNull(taskItemHandle);
        assertEquals(taskItemHandle.getName(), taskToTest.getName());
    }

    public static void assertSameEventName(Event eventToTest, TaskListEventItemHandle eventItemHandle) {
        assertNotNull(eventToTest);
        assertNotNull(eventItemHandle);
        assertEquals(eventItemHandle.getName(), eventToTest.getName());
    }
    
}
