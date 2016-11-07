package guitests;

import guitests.guihandles.TaskCardHandle;
import seedu.stask.commons.core.Messages;
import seedu.stask.logic.commands.AddCommand;
import seedu.stask.model.TaskBook.TaskType;
import seedu.stask.testutil.TestTask;
import seedu.stask.testutil.TestUtil;
import seedu.stask.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddCommandTest extends TaskBookGuiTest {

    @Test
    public void add() {
        //add datedOne task
        TestTask[] currentDatedList = td.getTypicalDatedTasks();
        TestTask[] currentUndatedList = td.getTypicalUndatedTasks();
        
        //adds a dated task
        TestTask taskToAdd = TypicalTestTasks.datedFour;
        assertAddSuccess(TaskType.DATED, taskToAdd, currentDatedList);
        currentDatedList = TestUtil.addTasksToList(currentDatedList, taskToAdd);
        assertTrue(datedListPanel.isListMatching(currentDatedList));
        //adds a undated task
        taskToAdd = TypicalTestTasks.undatedFour;
        assertAddSuccess(TaskType.UNDATED, taskToAdd, currentUndatedList);
        currentUndatedList = TestUtil.addTasksToList(currentUndatedList, taskToAdd);
        assertTrue(undatedListPanel.isListMatching(currentUndatedList));
        
        //add another dated task
        taskToAdd = TypicalTestTasks.datedFive;
        assertAddSuccess(TaskType.DATED, taskToAdd, currentDatedList);
        currentDatedList = TestUtil.addTasksToList(currentDatedList, taskToAdd);
        assertTrue(datedListPanel.isListMatching(currentDatedList));
        //adds a undated task
        taskToAdd = TypicalTestTasks.undatedFive;
        assertAddSuccess(TaskType.UNDATED, taskToAdd, currentUndatedList);
        currentUndatedList = TestUtil.addTasksToList(currentUndatedList, taskToAdd);
        assertTrue(undatedListPanel.isListMatching(currentUndatedList));
        
        //add duplicate dated task success
        taskToAdd = TypicalTestTasks.datedFive;
        assertAddSuccess(TaskType.DATED, taskToAdd, currentDatedList);
        currentDatedList = TestUtil.addTasksToList(currentDatedList, taskToAdd);
        assertTrue(datedListPanel.isListMatching(currentDatedList));
        //adds a undated task
        taskToAdd = TypicalTestTasks.undatedFive;
        assertAddSuccess(TaskType.UNDATED, taskToAdd, currentUndatedList);
        currentUndatedList = TestUtil.addTasksToList(currentUndatedList, taskToAdd);
        assertTrue(undatedListPanel.isListMatching(currentUndatedList));
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TaskType.DATED, TypicalTestTasks.datedFour);
        assertAddSuccess(TaskType.UNDATED, TypicalTestTasks.undatedFour);

        //invalid command
        commandBox.runCommand("adds task but command is wrong");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TaskType type, TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard;
        if (type == TaskType.DATED) {
            addedCard = datedListPanel.navigateToTask(taskToAdd.getName().fullName);
        } else {
            addedCard = undatedListPanel.navigateToTask(taskToAdd.getName().fullName);
        }
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous task plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        if (type == TaskType.DATED) {
            assertTrue(datedListPanel.isListMatching(expectedList));
        } else {
            assertTrue(undatedListPanel.isListMatching(expectedList));
        }
    }

}
