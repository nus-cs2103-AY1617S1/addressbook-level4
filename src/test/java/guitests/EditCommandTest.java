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
        
        commandBox.runCommand("edit 1 Accompany dad to the doctor, from 2016-10-25 02:00 to 2016-10-26 17:00 by 2016-10-27 15:00 #gwsDad");

        
        assertEditSuccess(1, TypicalTestTasks.taskJ, currentList);
        
    }

    private void assertEditSuccess(int index, TestTask taskToAdd, TestTask... currentList) {
        
        TestTask[] expectedList = currentList;
        expectedList[index - 1] = taskToAdd;
        
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
