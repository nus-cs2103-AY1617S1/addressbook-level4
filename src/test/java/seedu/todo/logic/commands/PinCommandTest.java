package seedu.todo.logic.commands;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.events.ui.HighlightTaskEvent;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.EventsCollector;


//@@author A0092382A
public class PinCommandTest extends CommandTest {

    @Override
    protected BaseCommand commandUnderTest() {
        return new PinCommand();
    }

    @Before
    public void setUp() throws Exception {
        model.add("Task 3");
        model.add("Task 2");
        model.add("Task 1", task -> task.setPinned(true));
    }
    
    private long getPinnedCount() {
        return model.getObservableList().stream().filter(ImmutableTask::isPinned).count();
    }
    
    @Test
    public void testPinFirst() throws Exception {
        setParameter("3");
        EventsCollector eventsCollector = new EventsCollector();
        execute(true);

        assertEquals(2, getPinnedCount());
        assertThat(eventsCollector.get(0), instanceOf(HighlightTaskEvent.class));
    }
    
    @Test
    public void testUnpinFirst() throws Exception {
        setParameter("1");
        execute(true);
            
        assertEquals(0, getPinnedCount());
    }
}
