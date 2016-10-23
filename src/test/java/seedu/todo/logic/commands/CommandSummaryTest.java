package seedu.todo.logic.commands;

import org.junit.Test;

import static org.junit.Assert.*;

//@@author A0135817B
public class CommandSummaryTest {
    @Test
    public void testConstructor() {
        CommandSummary summary = new CommandSummary(" Hello  ", "World");
        // Check trim 
        assertEquals("Hello", summary.scenario);
        // Check command is lowercase 
        assertEquals("world", summary.command);
        // Check constructor without third argument
        assertEquals("", summary.arguments);
    }
}
