package seedu.todo.testutil;

import java.time.LocalDate;

import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.guitests.guihandles.ConsoleHandle;
import seedu.todo.guitests.guihandles.TaskListDateItemHandle;
import seedu.todo.guitests.guihandles.TaskListEventItemHandle;
import seedu.todo.guitests.guihandles.TaskListTaskItemHandle;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

import static org.junit.Assert.*;

// @@author A0139812A
/**
 * Some utility methods for comparison and hence assertion to be used in GUITests.
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
    

    /**
     * Compares disambiguation messages in the console text area.
     */
    public static void assertSameDisambiguationMessage(String disambigMessage, ConsoleHandle console) {
        String actualMessage = console.getConsoleTextArea();
        
        if (disambigMessage == null || disambigMessage.length() <= 0) {
            assertEquals(actualMessage, Renderer.MESSAGE_DISAMBIGUATE);
        } else {
            assertEquals(String.format("%s\n\n%s", Renderer.MESSAGE_DISAMBIGUATE, disambigMessage), actualMessage);
        }
    }
    
}
