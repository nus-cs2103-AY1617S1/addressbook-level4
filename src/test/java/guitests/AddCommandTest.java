package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.AddCommand;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;
import seedu.tasklist.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskListGuiTest {

    @Test
    public void add() {
        //add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask personToAdd = TypicalTestTasks.task8;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, personToAdd);

        //add another person
        personToAdd = TypicalTestTasks.task9;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, personToAdd);

        //add overlapping person
        personToAdd = TypicalTestTasks.task8;
        commandBox.runCommand(personToAdd.getAddCommand());
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS + AddCommand.MESSAGE_OVERLAP, personToAdd.toString()));
        currentList = TestUtil.addTasksToList(currentList, personToAdd);
        TestTask[] overlappingList = new TestTask[0];
        overlappingList = TestUtil.addTasksToList(overlappingList, personToAdd);
        assertTrue(taskListPanel.isListMatching(overlappingList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.task1);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = taskListPanel.navigateToPerson(personToAdd.getTaskDetails().toString());
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
