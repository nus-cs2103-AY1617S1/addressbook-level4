package guitests;

import org.junit.Test;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.CommandGeneratorUtil;
import seedu.todo.testutil.TaskFactory;

import static org.junit.Assert.assertEquals;

//@@author A0139021U

/**
 * Test the preview function through the GUI.
 * Note:
 *      Order-ness of the tasks is not tested.
 *      Invalid preview output is not tested.
 */
public class CommandPreviewViewTest extends TodoListGuiTest {

    @Test
    public void testPreviewEmptyString() {
        int expected = 0;
        int actual = commandPreviewView.getRowsDisplayed();

        assertEquals(expected, actual);
    }

    @Test
    public void testPreviewAddCommand() throws InterruptedException {
        //Add a task
        ImmutableTask task = TaskFactory.task();
        enterCommand(CommandGeneratorUtil.generateAddCommand(task));

        int expected = 2;
        int actual = commandPreviewView.getRowsDisplayed();

        assertEquals(expected, actual);
    }
}