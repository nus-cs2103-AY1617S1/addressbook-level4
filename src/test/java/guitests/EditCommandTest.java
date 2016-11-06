package guitests;

import static org.junit.Assert.*;
import static seedu.jimi.logic.commands.EditCommand.MESSAGE_EDIT_SUCCESS;

import java.util.Comparator;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import guitests.guihandles.FloatingTaskCardHandle;
import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TestUtil;

//@@author A0138915X
public class EditCommandTest extends AddressBookGuiTest{

    @Test
    public void edit_floatingTaskNameChanges() throws IllegalValueException {
        ReadOnlyTask[] currentList = td.getTypicalTasks();
        Arrays.sort(currentList, 0, currentList.length, NameComparator);

        // edit the first task in list with all details
        int targetIndex = 1;
        
        // task to be changed to
        String newName = "Get rich or die coding";

        // edit the first task in list with only name changes
        assertEditTaskSuccess_Name(currentList, targetIndex, newName);
        
        // edit last task in list with only name changes
        newName = "Don't die poor";
        targetIndex = currentList.length;
        assertEditTaskSuccess_Name(currentList, targetIndex, newName);

        // invalid index
        commandBox.runCommand("edit t" + currentList.length + 1 + " \"Change invalid stuff\"");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    // @@author
    
    // @@author A0143471L
    @Test
    public void edit_floatingTaskOtherChanges() throws IllegalValueException {
        ReadOnlyTask[] currentList = td.getTypicalTasks();
        Arrays.sort(currentList, 0, currentList.length, NameComparator);
        
        // edit the second task in list with tag changes
        String newTag = "impt";
        int targetIndex = 2;
        assertEditSuccess_Tag(currentList, targetIndex, newTag);
        
        // edit the third task in list with priority changes
        String newPriority = "med";
        targetIndex = 3;
        assertEditSuccess_Priority(currentList, targetIndex, newPriority);
        
        
        //TODO: edit the first event in the list with all details
    }
    // @@author 
    
    // @@author A0138915X
    /**
     * Runs the edit command on the targetTask and checks if the edited task matches with changedTask.
     * Only the name of the task will be changed
     * 
     * @param targetIndex Index of target task to be edited.
     * @param currentList List of tasks to be edited.
     * @throws IllegalValueException 
     */
    private void assertEditTaskSuccess_Name(ReadOnlyTask[] currentList, int targetIndex, String newName ) throws IllegalValueException{

        //task to be replaced with
        TestFloatingTask expectedTask = new TestFloatingTask();
        expectedTask.setName(new Name(newName));
        
        if (targetIndex <= currentList.length && targetIndex > 0) {
            expectedTask.setTags(currentList[targetIndex - 1].getTags());
            expectedTask.setPriority(currentList[targetIndex - 1].getPriority());
        }

        //edit the name of the target task with the newName
        commandBox.runCommand("edit " + "t" + targetIndex + " \"" + newName + "\"");
        
        //confirm the edited card contains the right data
        FloatingTaskCardHandle addedCard = taskListPanel.navigateToTask(expectedTask.getName().fullName);
        assertMatching(expectedTask, addedCard);

        //confirm the list now contains all previous tasks plus the edited task
        ReadOnlyTask[] expectedList = TestUtil.replaceTaskFromList(currentList, expectedTask, targetIndex - 1);
        Arrays.sort(expectedList, 0, expectedList.length, NameComparator);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_SUCCESS, expectedTask));
    }
    // @@author 
    
    // @@author A0143471L
    /**
     * Runs the edit command on the targetTask and checks if the edited task matches with changedTask.
     * Only the tag of the task will be changed
     * 
     * Expected new task is to be at the top of the list. 
     * 
     * @param targetIndex Index of target task to be edited.
     * @param currentList List of tasks to be edited.
     * @throws IllegalValueException
     ***/
    private void assertEditSuccess_Tag(ReadOnlyTask[] currentList, int targetIndex, String newTag) throws IllegalValueException {
        // Set expected task
        TestFloatingTask expectedTask = new TestFloatingTask();
        if (targetIndex <= currentList.length && targetIndex > 0) {
            expectedTask.setName(currentList[targetIndex - 1].getName());
            expectedTask.setPriority(currentList[targetIndex - 1].getPriority());
        }
        expectedTask.setTags(new UniqueTagList(new Tag(newTag)));
        
        // Run command to edit the tag of the task
        commandBox.runCommand("edit " + "t" + targetIndex + " t/" + newTag);
        
        // Confirm that edited card contains the right data
        FloatingTaskCardHandle addedCard = taskListPanel.navigateToTask(expectedTask.getName().fullName);
        assertMatching(expectedTask, addedCard);
        assertTrue(addedCard.getTag().equals("[" + newTag + "]"));
        
        //confirm the list now contains all previous tasks plus the edited task
        ReadOnlyTask[] expectedList = TestUtil.replaceTaskFromList(currentList, expectedTask, targetIndex - 1);
        Arrays.sort(expectedList, 0, expectedList.length, NameComparator);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_SUCCESS, expectedTask));
    }
    
    private void assertEditSuccess_Priority(ReadOnlyTask[] currentList, int targetIndex, String newPriority) throws IllegalValueException {
        // Set expected task
        TestFloatingTask expectedTask = new TestFloatingTask();
        if (targetIndex <= currentList.length && targetIndex > 0) {
            expectedTask.setName(currentList[targetIndex - 1].getName());
            expectedTask.setTags(currentList[targetIndex - 1].getTags());
        }
        expectedTask.setPriority(new Priority(newPriority));
        
        // Run command to edit the priority of the task
        commandBox.runCommand("edit " + "t" + targetIndex + " p/" + newPriority);
        
        //confirm the list now contains all previous tasks plus the edited task
        ReadOnlyTask[] expectedList = TestUtil.replaceTaskFromList(currentList, expectedTask, targetIndex - 1);
        Arrays.sort(expectedList, 0, expectedList.length, NameComparator);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_SUCCESS, expectedTask));
        
    }
    
    /**
     * Sorts expected floatingTaskListPanel in alphabetical order
     */
    public static Comparator<ReadOnlyTask> NameComparator = new Comparator<ReadOnlyTask>() {

        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {

            String taskName1 = task1.getName().toString().toUpperCase();
            String taskName2 = task2.getName().toString().toUpperCase();

            // ascending order
            return taskName1.compareTo(taskName2);
        }
    };
    // @@author
}
