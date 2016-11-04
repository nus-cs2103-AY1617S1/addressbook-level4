package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.commands.AddCommand;
import seedu.oneline.testutil.TestTask;
import seedu.oneline.testutil.TestUtil;
import seedu.oneline.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskBookGuiTest {
    
    @Test
    public void addCommand_validTask_success() {
        TestTask[] currentList = td.getTypicalTasks();
        Arrays.sort(currentList);
        TestTask taskToAdd = TypicalTestTasks.eventExtra;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
    }
    
    @Test
    public void addCommand_duplicateTask_duplicateMessage() {
        TestTask[] currentList = td.getTypicalTasks();
        Arrays.sort(currentList);

        TestTask taskToAdd = TypicalTestTasks.eventExtra;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        Arrays.sort(currentList);
        
        //add duplicate task
        commandBox.runCommand(TypicalTestTasks.eventExtra.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskPane.isListMatching(currentList));
    }
    
    @Test
    public void addCommand_addToEmpty_success() {
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.event1);
    }
    
    @Test
    public void addCommand_invalidCommand_unknownMessage() {
        commandBox.runCommand("adds Task");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskPane.navigateToTask(taskToAdd.getName().toString());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        Arrays.sort(expectedList);
        
        assertTrue(taskPane.isListMatching(expectedList));
    }

}
