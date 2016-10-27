package seedu.todo.guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * @@author A0139812A
 */
public class ConsoleTest extends GuiTest {
    
    @Test
    public void testValidCommand_consoleInputCleared() {
        console.runCommand("list");
        assertEquals(console.getConsoleInputText(), "");
    }

    @Test
    public void testInvalidCommand_consoleInputRemains() {
        console.runCommand("invalidcommand");
        assertNotEquals(console.getConsoleInputText(), "");
    }
    
}
