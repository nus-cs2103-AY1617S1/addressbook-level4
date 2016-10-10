package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ImmutableTask;

public class PinCommandTest extends CommandTest {

    @Override
    protected BaseCommand commandUnderTest() {
    return new PinCommand();
    }

    @Before
    public void setUp() throws Exception {
        model.add("Task 3");
        model.add("Task 2");
        model.add("Task 1", task->{task.setPinned(true);});
    }

    @Test
    public void testPinFirst() throws IllegalValueException {
        ImmutableTask toPin = getTaskAt(3);
        setParameter("3");
        execute();
        assertTrue(toPin.isPinned());
    }
    
    @Test
    public void testUnpinFirst() throws IllegalValueException {
        ImmutableTask toUnpin = getTaskAt(1);
        setParameter("1");
        execute();
        assertFalse(toUnpin.isPinned());
    }
}
