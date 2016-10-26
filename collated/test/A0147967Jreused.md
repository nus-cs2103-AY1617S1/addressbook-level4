# A0147967Jreused
###### \java\guitests\AddCommandTest.java
``` java
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.logic.commands.AddNonFloatingCommand;
import seedu.address.model.task.TaskComponent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends TaskMasterGuiTest {

    @Test
    public void add() {
        //add one floatingTask
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another floatingTask
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add duplicate floatingTask
        commandBox.runCommand(td.hoon.getAddFloatingCommand());
        assertResultMessage(AddFloatingCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(TestUtil.convertTasksToDateComponents(currentList)));
        
```
