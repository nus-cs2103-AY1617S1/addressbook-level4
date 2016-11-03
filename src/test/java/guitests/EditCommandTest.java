//@@author A0147335E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestTasks;

public class EditCommandTest extends TaskManagerGuiTest {
    @Test
    public void edit() {
        
        TestTask[] currentList = td.getTypicalTasks();
        
        commandBox.runCommand("edit 1 name, Accompany dad to the doctor");
        commandBox.runCommand("edit 1 tag, gwsDad");
        
        commandBox.runCommand("edit 1 start, 3pm");
        commandBox.runCommand("undo");

        commandBox.runCommand("edit 1 end, 6pm");
        commandBox.runCommand("undo");

        commandBox.runCommand("edit 1 due, 10pm");
        commandBox.runCommand("undo");
        
        assertEditSuccess(1, TypicalTestTasks.taskJ, currentList);
        
    }

    private void assertEditSuccess(int index, TestTask taskToAdd, TestTask... currentList) {
        
        TestTask[] expectedList = currentList;
        expectedList[index - 1] = taskToAdd;
        
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
