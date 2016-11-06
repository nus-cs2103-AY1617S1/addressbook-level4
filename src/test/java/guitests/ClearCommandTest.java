package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import w15c2.tusk.model.task.Task;
import w15c2.tusk.testutil.TaskTesterUtil;
import w15c2.tusk.testutil.TestUtil;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {

        List<Task> initialTasks = TestUtil.getInitialTasks().getInternalList();
        int initialListSize = initialTasks.size();
        
        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(TestUtil.getInitialTasks().getInternalList()));
        assertClearCommandSuccess(initialListSize);

        //verify other commands can work after a clear command
        commandBox.runCommand(TaskTesterUtil.getAddCommandFromTask(testTasks.get(0)));
        assertTrue(taskListPanel.isListMatching(testTasks.subList(0, 1)));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess(0);
    }

    private void assertClearCommandSuccess(int numberOfTasksCleared) {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage(numberOfTasksCleared + " tasks deleted!");
    }
}
