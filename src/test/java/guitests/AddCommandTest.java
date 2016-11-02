package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.jimi.commons.core.Messages;
import seedu.jimi.logic.commands.AddCommand;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.testutil.TestDeadlineTask;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TestUtil;
import seedu.jimi.testutil.TypicalTestDeadlineTasks;
import seedu.jimi.testutil.TypicalTestFloatingTasks;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        ReadOnlyTask[] currentList = td.getTypicalTasks();
        
        //add one task
        TestFloatingTask taskToAdd = TypicalTestFloatingTasks.dream;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
/*
        //add another task
        taskToAdd = TypicalTestFloatingTasks.night;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        Arrays.sort(currentList, 0, currentList.length, NameComparator);
        
        //add duplicate task
        commandBox.runCommand(TypicalTestFloatingTasks.dream.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
*/        
        //add deadline task
        TestDeadlineTask deadlineTaskToAdd = TypicalTestDeadlineTasks.homework;
        assertAddSuccess(deadlineTaskToAdd);
        currentList = TestUtil.addTasksToList(currentList, deadlineTaskToAdd);
        Arrays.sort(currentList, 0, currentList.length, NameComparator);
       
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestFloatingTasks.beach);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestFloatingTask taskToAdd, ReadOnlyTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        ReadOnlyTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        Arrays.sort(expectedList, 0, expectedList.length, NameComparator);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    //@@author A0143471L
    private void assertAddSuccess(TestDeadlineTask deadlineTaskToAdd) {
        System.out.print(deadlineTaskToAdd.getAddCommand());
        commandBox.runCommand(deadlineTaskToAdd.getAddCommand());

        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, deadlineTaskToAdd));
    }
    
    public static Comparator<ReadOnlyTask> NameComparator
                          = new Comparator<ReadOnlyTask>() {

        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {

          String taskName1 = task1.getName().toString().toUpperCase();
          String taskName2 = task2.getName().toString().toUpperCase();

          //ascending order
          return taskName1.compareTo(taskName2);
        }
    };
    //@@author
}
