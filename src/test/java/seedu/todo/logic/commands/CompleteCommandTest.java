package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;


import seedu.todo.model.task.ImmutableTask;
import seedu.todo.logic.commands.CompleteCommand;

public class CompleteCommandTest extends CommandTest {
    private static final String VERB_COMPLETE = "marked complete";
    private static final String VERB_INCOMPLETE = "marked incomplete";

    @Override
    protected BaseCommand commandUnderTest() {
        return new CompleteCommand();
    }

    @Before
    public void setUp() throws Exception {
        model.add("Task 3");
        model.add("Task 2");
        model.add("Task 1", task -> task.setCompleted(true));
    }

    @Test
    public void testMarkComplete() throws Exception {
        setParameter("3");
        execute(true);
        ImmutableTask markedComplete = getTaskAt(1);
        assertThat(result.getFeedback(), containsString(VERB_COMPLETE));
        assertEquals(markedComplete, markedComplete);
        assertTrue(markedComplete.isCompleted());
    }

    @Test
    public void testMarkIncomplete() throws Exception {
        ImmutableTask toMarkIncomplete = getTaskAt(1);
        setParameter("1");
        execute(true);
        ImmutableTask markedIncomplete = getTaskAt(1);
        assertThat(result.getFeedback(), containsString(VERB_INCOMPLETE));
        assertEquals(markedIncomplete, toMarkIncomplete);
        assertFalse(toMarkIncomplete.isCompleted());
    }
}
