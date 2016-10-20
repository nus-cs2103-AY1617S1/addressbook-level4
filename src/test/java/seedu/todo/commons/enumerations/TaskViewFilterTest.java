package seedu.todo.commons.enumerations;

import org.junit.Test;
import seedu.todo.commons.core.TaskViewFilter;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TaskViewFilterTest {
    @Test
    public void testNoOverlappingShortcut() {
        Set<Character> shortcuts = new HashSet<>();
        
        for (TaskViewFilter filter : TaskViewFilter.all()) {
            char shortcut = filter.name.charAt(filter.shortcutCharPosition);
            assertFalse(shortcuts.contains(shortcut));
            shortcuts.add(shortcut);
        }
    }
}
