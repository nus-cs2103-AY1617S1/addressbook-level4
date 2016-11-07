package guitests;

import guitests.guihandles.SearchStatusViewHandle;
import org.junit.Test;
import seedu.todo.model.TodoList;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.TaskBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@@author A0135805H
/**
 * Tests the find command in GUI setting.
 * In particular, only the {@link SearchStatusViewHandle} will be tested for now.
 * We will rely {@link seedu.todo.logic.commands.FindCommandTest} to test the remaining part of finding.
 */
public class FindCommandTest extends TodoListGuiTest {

    @Override
    protected TodoList getInitialData() {
        List<ImmutableTask> tasks = new ArrayList<>();

        tasks.add(TaskBuilder.name("Project dynamite").build());
        tasks.add(TaskBuilder.name("Dynamite little bombarded bomb")
                .event(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(3))
                .pinned()
                .build());
        tasks.add(TaskBuilder.name("Pikachu pika pika")
                .due(LocalDateTime.now().plusDays(1))
                .build());
        tasks.add(TaskBuilder.name("Kaboom")
                .due(LocalDateTime.now().plusDays(1))
                .tagged("dynamite", "pikachu")
                .build());

        return getInitialDataHelper(tasks);
    }

    @Test
    public void find_noResults() {
        runCommand("find happy birthday to you");

        assertEquals("happy, birthday, to, you", searchStatusView.getSearchTermText());
        assertTrue(searchStatusView.isVisible());
        assertTrue(searchStatusView.doesSearchCountMatch(0));
    }

    @Test
    public void find_taskNameResults() {
        runCommand("f dynamite");

        assertEquals("dynamite", searchStatusView.getSearchTermText());
        assertTrue(searchStatusView.isVisible());
        assertTrue(searchStatusView.doesSearchCountMatch(2));
    }

    @Test
    public void find_tagResults() {
        runCommand("f /t pika dynamite");

        assertEquals("pika, dynamite", searchStatusView.getSearchTermText());
        assertTrue(searchStatusView.isVisible());
        assertTrue(searchStatusView.doesSearchCountMatch(1));
    }
}
