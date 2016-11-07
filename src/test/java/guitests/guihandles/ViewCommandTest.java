package guitests.guihandles;

import guitests.TodoListGuiTest;
import org.apache.commons.lang.WordUtils;
import org.junit.Test;
import seedu.todo.model.property.TaskViewFilter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

//@@author A0135805H
/**
 * Tests the display of the {@link seedu.todo.ui.view.FilterBarView} with the
 * {@link seedu.todo.logic.commands.ViewCommand}
 *
 * {@link seedu.todo.logic.commands.ViewCommandTest} ensures that the task view itself matches the filter.
 */
public class ViewCommandTest extends TodoListGuiTest {

    @Test
    public void view_checkAllFilterViews_allDisplayedInView() {

        List<String> displayedViewFilter = filterBarView.getFilterBarViewLabels();
        List<String> expectedViewFilter = new ArrayList<>();
        for (TaskViewFilter filter : TaskViewFilter.all()) {
            String capitalisedText = WordUtils.capitalize(filter.name);
            expectedViewFilter.add(capitalisedText);
        }
        assertEquals(expectedViewFilter, displayedViewFilter);
    }

    @Test
    public void view_defaultSelected_viewAllSelected() {
        assertSelection(TaskViewFilter.DEFAULT);
    }

    @Test
    public void view_switchViews_selectionCorrect() {
        //With full name, switching view works.
        runCommand("view completed");
        assertSelection(TaskViewFilter.COMPLETED);

        runCommand("view incomplete");
        assertSelection(TaskViewFilter.INCOMPLETE);

        runCommand("view events");
        assertSelection(TaskViewFilter.EVENTS);

        runCommand("view due soon");
        assertSelection(TaskViewFilter.DUE_SOON);

        runCommand("view all");
        assertSelection(TaskViewFilter.DEFAULT);

        runCommand("view today");
        assertSelection(TaskViewFilter.TODAY);

        //With short forms, switching view works.
        runCommand("view c");
        assertSelection(TaskViewFilter.COMPLETED);

        runCommand("view i");
        assertSelection(TaskViewFilter.INCOMPLETE);

        runCommand("view e");
        assertSelection(TaskViewFilter.EVENTS);

        runCommand("view d");
        assertSelection(TaskViewFilter.DUE_SOON);

        runCommand("view a");
        assertSelection(TaskViewFilter.DEFAULT);

        runCommand("view t");
        assertSelection(TaskViewFilter.TODAY);
    }

    /**
     * Helper method to check if the selection is as {@code expected}.
     */
    private void assertSelection(TaskViewFilter expected) {
        String actualSelection = filterBarView.getSelectedFilterView();
        String expectedSelection = WordUtils.capitalize(expected.name);
        assertEquals(expectedSelection, actualSelection);
    }
}
