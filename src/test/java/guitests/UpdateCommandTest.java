package guitests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class UpdateCommandTest extends AddressBookGuiTest {

    @Test
    public void update() throws IllegalValueException {
        // update one task
        TestTask[] currentList = td.getTypicalTasks();
        int taskToUpdateIndex = 0;
        TestTask taskToUpdate = currentList[taskToUpdateIndex];
        TestTask newTask = new TestTask(taskToUpdate);
        newTask.setDeadline(new Deadline(new Date(0)));
        assertUpdateSuccess(taskToUpdateIndex, taskToUpdate, newTask, new HashSet<String>(),
                new HashSet<String>(), currentList);
        currentList = TestUtil.updateTaskInList(currentList, taskToUpdateIndex, newTask);

        // update another task
        taskToUpdateIndex = 1;
        taskToUpdate = currentList[taskToUpdateIndex];
        newTask = new TestTask(taskToUpdate);
        newTask.setName(new Name("Delete Github Repo"));
        assertUpdateSuccess(taskToUpdateIndex, taskToUpdate, newTask, new HashSet<String>(),
                new HashSet<String>(), currentList);
        currentList = TestUtil.updateTaskInList(currentList, taskToUpdateIndex, newTask);

        // update an empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("update 1 name \"New Name\"");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand("updates 1 \"hahaha\"");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertUpdateSuccess(int taskIndex, TestTask oldTask, TestTask newTask, Set<String> newTags,
            Set<String> deleteTags, TestTask... currentList) {
        commandBox.runCommand(newTask.getUpdateCommand(taskIndex, oldTask, newTags, deleteTags));

        // confirm the updated card contains the right data
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(newTask.getName().getName());
        assertMatching(newTask, updatedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.updateTaskInList(currentList, taskIndex, newTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
