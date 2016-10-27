package seedu.todo.guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConsoleTest extends GuiTest {

    @Test
    public void testValidCommand_consoleInputCleared() {
        console.runCommand("list");
        assertEquals(console.getConsoleInputText(), "");
    }
    
}
