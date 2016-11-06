package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.jimi.commons.core.Messages;
import seedu.jimi.logic.commands.AddCommand;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.testutil.TestDeadlineTask;
import seedu.jimi.testutil.TestEvent;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TestUtil;
import seedu.jimi.testutil.TypicalTestDeadlineTasks;
import seedu.jimi.testutil.TypicalTestEvents;
import seedu.jimi.testutil.TypicalTestFloatingTasks;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

// @@author A0143471L
public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //set up expected task list for floating tasks and today's tasks/events
        ReadOnlyTask[] currentList = td.getTypicalTasks();
        ReadOnlyTask[] todayList = dt.getTodayTasks();
        todayList = TestUtil.addTasksToList(todayList, e.getTodayTasks());
        System.out.println(todayList.length);
        
        //add one task
        TestFloatingTask taskToAdd = TypicalTestFloatingTasks.dream;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestFloatingTasks.night;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        Arrays.sort(currentList, 0, currentList.length, NameComparator);
        
        //add duplicate task
        commandBox.runCommand(TypicalTestFloatingTasks.dream.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add deadline task
        TestDeadlineTask deadlineTaskToAdd = TypicalTestDeadlineTasks.submission;
        assertAddSuccess(deadlineTaskToAdd, todayList);
        todayList = TestUtil.addTasksToList(todayList, deadlineTaskToAdd);
        Arrays.sort(todayList, 0, todayList.length, NameComparator);
        
        //add events
        TestEvent eventToAdd = TypicalTestEvents.nightClass;
        assertAddSuccess(eventToAdd, todayList);
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestFloatingTasks.beach);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(String.format(Messages.MESSAGE_UNKNOWN_COMMAND, "adds"));
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
    
    // @@author A0143471L
    private void assertAddSuccess(TestDeadlineTask deadlineTaskToAdd, ReadOnlyTask... todayList) {
        commandBox.runCommand(deadlineTaskToAdd.getAddCommand());
        
        //confirm the result message
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, deadlineTaskToAdd));
        
        //confirm the new card contains the right data
        commandBox.runCommand("show today");
        FloatingTaskCardHandle addedCard = todayTaskListPanel.navigateToTask(deadlineTaskToAdd.getName().fullName);
        assertMatching(deadlineTaskToAdd, addedCard);
        
        //confirm the list now contains all the previous tasks plus the new task
        ReadOnlyTask[] expectedList = TestUtil.addTasksToList(todayList, deadlineTaskToAdd);
        Arrays.sort(expectedList, 0, expectedList.length, DateComparator);
        assertTrue(todayTaskListPanel.isListMatching(expectedList));
    }
    
    private void assertAddSuccess(TestEvent eventToAdd, ReadOnlyTask... todayList) {
        commandBox.runCommand(eventToAdd.getAddCommand());
        
        //confirm the result message
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, eventToAdd));
    
        //confirm the new card contains the right data
        commandBox.runCommand("show today");
        FloatingTaskCardHandle addedCard = todayTaskListPanel.navigateToTask(eventToAdd.getName().fullName);
        assertMatching(eventToAdd, addedCard);
        
        //confirm the list now contains all the previous tasks plus the new task
        ReadOnlyTask[] expectedList = TestUtil.addTasksToList(todayList, eventToAdd);
        Arrays.sort(expectedList, 0, expectedList.length, DateComparator);
        for (int i = 0; i<expectedList.length; i++) {
            System.out.println(expectedList[i].getAsText());
        }
        assertTrue(todayTaskListPanel.isListMatching(expectedList));
    }
    
    public static Comparator<ReadOnlyTask> NameComparator = new Comparator<ReadOnlyTask>() {

        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {

          String taskName1 = task1.getName().toString().toUpperCase();
          String taskName2 = task2.getName().toString().toUpperCase();

          //ascending order
          return taskName1.compareTo(taskName2);
        }
    };
    
    public static Comparator<ReadOnlyTask> DateComparator = new Comparator<ReadOnlyTask>() {
        
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            DateTime taskDate1 = new DateTime(), taskDate2 = new DateTime();
            
            if (task1 instanceof TestEvent) {
                taskDate1 = ((TestEvent) task1).getStart();
            } else if (task1 instanceof TestDeadlineTask) {
                taskDate1 = ((TestDeadlineTask) task1).getDeadline();
            } 
                
            if (task2 instanceof TestEvent) {
                taskDate2 = ((TestEvent) task2).getStart();
            } else if (task2 instanceof TestDeadlineTask) {
                taskDate2 = ((TestDeadlineTask) task2).getDeadline();
            }
            
            return taskDate1.compareTo(taskDate2);
        }
    };
                        
    //@@author
}
