//@@author A0147335E
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class SortCommandTest extends TaskManagerGuiTest {
    @Test
    public void done() {
        TestTask[] currentList = td.getTypicalTasks();
        
        commandBox.runCommand("sort");
        
        assertDoneSuccess(currentList);
        
    }

    private void assertDoneSuccess(TestTask... currentList) {
        
         TestTask[] expectedList = currentList;
        
         assertEquals(taskListPanel.getNumberOfTask(), 7);
    }
}
