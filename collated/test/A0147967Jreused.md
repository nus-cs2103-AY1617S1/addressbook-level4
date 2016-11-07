# A0147967Jreused
###### \java\guitests\AddCommandTest.java
``` java
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskmaster.commons.core.Messages;
import seedu.taskmaster.logic.commands.AddFloatingCommand;
import seedu.taskmaster.logic.commands.AddNonFloatingCommand;
import seedu.taskmaster.model.task.TaskOccurrence;
import seedu.taskmaster.testutil.TestTask;
import seedu.taskmaster.testutil.TestUtil;

public class AddCommandTest extends TaskMasterGuiTest {

    @Test
    public void add() {
        commandBox.runCommand("list"); //switch to all tasks first
        // add one floatingTask
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add another floatingTask
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add duplicate floatingTask
        commandBox.runCommand(td.hoon.getAddFloatingCommand());
        assertResultMessage(AddFloatingCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(TestUtil.convertTasksToDateComponents(currentList)));

```
