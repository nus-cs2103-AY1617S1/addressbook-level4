package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;


import seedu.todo.model.task.ImmutableTask;
import seedu.todo.commons.core.TaskViewFilter;
import seedu.todo.commons.exceptions.ValidationException;
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
    
    @Test
    public void testCompleteAll_withView() throws Exception {
        model.view(TaskViewFilter.INCOMPLETE);
        List<ImmutableTask> tasks = model.getObservableList();
        setParameter("all", "all");
        execute(true);
        for (ImmutableTask task : tasks) {
            assertTrue(task.isCompleted());
        }
    }

    @Test
    public void testCompleteAll_withAlreadyComplete() throws Exception {
        setParameter("all","all");
        execute(true);
        for (ImmutableTask task : model.getObservableList()) {
            assertTrue(task.isCompleted());
        }
    }
    
    @Test(expected = ValidationException.class)
    public void testMarkCompleteAll_invalid() throws Exception {
        model.view(TaskViewFilter.INCOMPLETE);
        setParameter("1");
        setParameter("all","all");
        execute(false);
    }

    @Test(expected = ValidationException.class)
    public void testMarkCompleteAll_empty() throws Exception {
        model.view(TaskViewFilter.DEFAULT);
        execute(false);
    }
}
