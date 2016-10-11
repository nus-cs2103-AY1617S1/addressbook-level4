package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ImmutableTask;

public class CompleteCommandTest extends CommandTest {

    @Override
    protected BaseCommand commandUnderTest() {
    return new CompleteCommand();
    }

    @Before
    public void setUp() throws Exception {
        model.add("Task 3");
        model.add("Task 2");
        model.add("Task 1", task->{task.setCompleted(true);});
    }

    @Test
    public void testMarkComplete() throws IllegalValueException {
        ImmutableTask toMarkComplete = getTaskAt(3);
        setParameter("3");
        execute();
        toMarkComplete = getTaskAt(3);
        assertTrue(toMarkComplete.isCompleted());
    }
    
    @Test
    public void testMarkIncomplete() throws IllegalValueException {
        ImmutableTask toMarkIncomplete = getTaskAt(1);
        setParameter("1");
        execute();
        toMarkIncomplete = getTaskAt(3);
        assertFalse(toMarkIncomplete.isPinned());
    }
}
