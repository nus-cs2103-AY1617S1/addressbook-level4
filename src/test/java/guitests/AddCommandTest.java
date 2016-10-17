package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.model.task.TaskDateComponent;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class AddCommandTest extends TaskListGuiTest {

    @Test
    public void add() {
        //add one floatingTask
        TestTask[] currentList = td.getTypicalTasks();
        TestTask floatingTaskToAdd = td.hoon;
        assertAddSuccess(floatingTaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, floatingTaskToAdd);

        //add another floatingTask
        floatingTaskToAdd = td.ida;
        assertAddSuccess(floatingTaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, floatingTaskToAdd);

        List<TaskDateComponent> componentList = new ArrayList<TaskDateComponent>();
        for(TestTask t : currentList) {
            componentList.addAll(t.getTaskDateComponent());
        }
        //add duplicate floatingTask
        commandBox.runCommand(td.hoon.getAddFloatingCommand());
        assertResultMessage(AddFloatingCommand.MESSAGE_DUPLICATE_TASK);
        TaskDateComponent[] taskComponents = new TaskDateComponent[componentList.size()];
        assertTrue(floatingTaskListPanel.isListMatching(componentList.toArray(taskComponents)));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.trash);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask floatingTaskToAdd, TestTask... currentList) {
        commandBox.runCommand(floatingTaskToAdd.getAddFloatingCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = floatingTaskListPanel.navigateToTask(floatingTaskToAdd.getName().fullName);
        assertMatching(floatingTaskToAdd.getTaskDateComponent().get(0), addedCard);

        //confirm the list now contains all previous floatingTasks plus the new floatingTask
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, floatingTaskToAdd);
        List<TaskDateComponent> expectedComponentList = new ArrayList<TaskDateComponent>();
        for(TestTask t : expectedList) {
            expectedComponentList.addAll(t.getTaskDateComponent());
        }
        TaskDateComponent[] taskComponents = new TaskDateComponent[expectedComponentList.size()];
        assertTrue(floatingTaskListPanel.isListMatching(expectedComponentList.toArray(taskComponents)));
    }

}
