package guitests;

import com.google.common.collect.Sets;
import org.junit.Test;
import seedu.todo.model.TodoList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.CollectionUtil;
import seedu.todo.testutil.TaskBuilder;
import seedu.todo.testutil.TaskFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@@author A0135805H
/**
 * Tests the tag command, including:
 *      1. Display the global list of tags.
 *      2. TODO: CRUD of tags.
 */
public class TagCommandTest extends TodoListGuiTest {

    @Override
    protected TodoList getInitialData() {
        List<ImmutableTask> tasks = new ArrayList<>();

        //Initialise some dummy tasks with tags.
        for (int numOfTags = 0; numOfTags <= 5; numOfTags ++) {
            String[] listOfTagNames = TaskFactory.randomTags(numOfTags);

            ImmutableTask task = TaskBuilder.name(TaskFactory.taskTitle())
                    .tagged(listOfTagNames).build();

            tasks.add(task);
        }

        return getInitialDataHelper(tasks);
    }

    /* Actual Tests */
    @Test
    public void tags_displayGlobalTagList() {
        //Check that all the tags are displayed, and they are unique.
        runCommand("tag");
        List<String> displayedTagNames = globalTagView.getDisplayedTags();
        assertNoDuplicate(displayedTagNames);
        assertEqualTagNames(getTagList(todoListView.getImmutableTaskList()),
                Sets.newHashSet(displayedTagNames));
    }

    /* Helper Method */
    /**
     * Get the list of tags given a listof {@code tasks}
     */
    private Set<String> getTagList(List<ImmutableTask> tasks) {
        Set<String> listOfTagNames = new HashSet<>();
        tasks.forEach(task -> listOfTagNames.addAll(Tag.getLowerCaseNames(task.getTags())));
        return listOfTagNames;
    }

    /**
     * Asserts that two sets are equal (with no case sensitivity)
     */
    private void assertEqualTagNames(Set<String> tagNames1, Set<String> tagNames2) {
        tagNames1 = tagNames1.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        tagNames2 = tagNames2.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        assertEquals(tagNames1, tagNames2);
    }

    /**
     * Asserts that there are no duplicate strings
     */
    private void assertNoDuplicate(List<String> strings) {
        assertTrue(CollectionUtil.elementsAreUnique(strings));
    }
}
