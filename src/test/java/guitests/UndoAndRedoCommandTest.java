package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import java.util.Arrays;

//@@author A0093960X
public class UndoAndRedoCommandTest extends DearJimGuiTest {
    
    private TestTask[] originalUndoneList = td.getTypicalUndoneTasks();
    private TestTask[] originalDoneList = td.getTypicalDoneTasks();

    @Override
    protected TaskManager getInitialData() {
        TaskManager ab = TestUtil.generateEmptyTaskManager();
        TypicalTestTasks.loadTaskManagerUndoneListWithSampleData(ab);
        TypicalTestTasks.loadTaskManagerDoneListWithSampleDate(ab);
        return ab;
    }
    
    @Test
    public void undoCommand_noPreviousUndoableCommand_nothingToUndo() {
        commandBox.runCommand("list");
        assertUndoSuccess(td.getTypicalUndoneTasks());
    }
    
    @Test
    public void redoCommand_noUndoToRedo_nothingToRedo() {
        commandBox.runCommand("list");
        assertRedoSuccess(td.getTypicalUndoneTasks());
    }
    
    @Test
    public void undoAndRedoCommand_addTask_deleteForUndoReaddForRedo() {
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, originalUndoneList);
        
        // undo the add
        assertUndoSuccess(originalUndoneList);
        
        // redo the add
        TestTask[] withHoon = TestUtil.addTasksToList(originalUndoneList, taskToAdd);
        assertRedoSuccess(withHoon);
        
        // check if works on list done view
        commandBox.runCommand("list done");
        assertUndoSuccess(originalDoneList);
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(originalUndoneList));
        commandBox.runCommand("list done");
        assertRedoSuccess(originalDoneList);
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(withHoon));
    }
    
    @Test
    public void undoAndRedoCommand_deleteUndoneTask_readdForUndoDeleteForRedo() {
        
        TestTask[] undoneListWithoutCarl = TestUtil.removeTaskFromList(originalUndoneList, 3);
        
        assertDeleteSuccess(3, originalUndoneList);
                
        // undo the delete
        assertUndoSuccess(originalUndoneList);
        
        // delete hoon again
        assertRedoSuccess(undoneListWithoutCarl);
        
        // check if works on list done view
        commandBox.runCommand("list done");
        assertUndoSuccess(originalDoneList);
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(originalUndoneList));
        commandBox.runCommand("list done");
        assertRedoSuccess(originalDoneList);
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(undoneListWithoutCarl));
    }
    
    @Test
    public void undoAndRedoCommand_deleteDoneTask_readdForUndoDeleteForRedo() {
       
        TestTask[] doneListWithoutGeorge = TestUtil.removeTaskFromList(originalDoneList, 7);
               
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(originalDoneList));
        assertDeleteSuccess(7, originalDoneList);
        assertUndoSuccess(originalDoneList);
        assertRedoSuccess(doneListWithoutGeorge);
        
        // check if works on list undone view
        commandBox.runCommand("list");
        assertUndoSuccess(originalUndoneList);
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(originalDoneList));
        commandBox.runCommand("list");
        assertRedoSuccess(originalUndoneList);
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(doneListWithoutGeorge));
    }
    
    @Test
    public void undoAndRedoCommand_clearUndoneTasks_unclearForUndoReclearForRedo() {

        assertUndoneListClearCommandSuccess();
        assertUndoSuccess(originalUndoneList);
        assertRedoSuccess();
        
        // check if targets undone tasks even on done view
        commandBox.runCommand("list done");
        assertUndoSuccess(originalDoneList);
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(originalUndoneList));
        commandBox.runCommand("list done");
        assertRedoSuccess(originalDoneList);
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching());
    }

    
    @Test
    public void undoAndRedoCommand_clearDoneTasks_unclearForUndoReclearForRedo() {
        
        commandBox.runCommand("list done");
        assertDoneListClearCommandSuccess();
        assertUndoSuccess(originalDoneList);
        assertRedoSuccess();

        // check if targets done task even on undone view
        commandBox.runCommand("list");
        assertUndoSuccess(originalUndoneList);
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(originalDoneList));
        commandBox.runCommand("list");
        assertRedoSuccess(originalUndoneList);
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching());
    }
    
    @Test
    public void undoAndRedoCommand_editTask_reverseEditForUndoReeditforRedo() {
        TestTask aliceEdit = new TestTask(td.alice);
        TestTask aliceTaskBackup = new TestTask(td.alice);
        
        aliceEdit.setName(new Name("Meet Alice at jUnit mall"));
        try {
            aliceEdit.setStartDate(DateTime.convertStringToDate("2pm"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        try {
            aliceEdit.setEndDate(DateTime.convertStringToDate("3pm"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        try {
            aliceEdit.setRecurrence(new RecurrenceRate("3", "days"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        aliceEdit.setPriority(Priority.HIGH);
        
        originalUndoneList[0] = aliceEdit;
                
        commandBox.runCommand("edit 1 Meet Alice at jUnit mall from 2pm to 3pm repeat every 3 days -high");
        assertTrue(personListPanel.isListMatching(originalUndoneList));
        
        originalUndoneList[0] = aliceTaskBackup;
        assertUndoSuccess(originalUndoneList);
        originalUndoneList[0] = aliceEdit;
        assertRedoSuccess(originalUndoneList);
        
        // check if this works on the list done view
        commandBox.runCommand("list done");
        assertUndoSuccess(originalDoneList);
        originalUndoneList[0] = aliceTaskBackup;
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(originalUndoneList));
        commandBox.runCommand("list done");
        assertRedoSuccess(originalDoneList);
        commandBox.runCommand("list");
        originalUndoneList[0] = aliceEdit;
        assertTrue(personListPanel.isListMatching(originalUndoneList));
        
    }
    
    @Test
    public void undoAndRedoCommand_doneTask_undoneForUndoDoneForRedo() {
        //Setup for done
        TestTask aliceEdit = new TestTask(td.alice);

        aliceEdit.setName(new Name("Meet Alice at jUnit mall"));
        try {
            aliceEdit.setStartDate(DateTime.convertStringToDate("today 2pm"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        try {
            aliceEdit.setEndDate(DateTime.convertStringToDate("tomorrow 3pm"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        try {
            aliceEdit.setRecurrence(new RecurrenceRate("3", "days"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        aliceEdit.setPriority(Priority.HIGH);
        
        TestTask aliceRecur = new TestTask(aliceEdit);
        
        try {
            aliceRecur.setStartDate(DateTime.convertStringToDate("2pm 3 days later"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        
        try {
            aliceRecur.setEndDate(DateTime.convertStringToDate("3pm 4 days later"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        
        commandBox.runCommand("list done");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("list");
        
        originalUndoneList[0] = aliceEdit;
        commandBox.runCommand("edit 1 Meet Alice at jUnit mall from today 2pm to tomorrow 3pm repeat every 3 days -high");
        assertTrue(personListPanel.isListMatching(originalUndoneList));

        // Real done test
        commandBox.runCommand("done 1");
        originalUndoneList[0] = aliceRecur;
        assertTrue(personListPanel.isListMatching(originalUndoneList));
        originalUndoneList[0] = aliceEdit;
        assertUndoSuccess(originalUndoneList);
        originalUndoneList[0] = aliceRecur;
        assertRedoSuccess(originalUndoneList);
        
        TestTask[] doneListBeforeDone = TestUtil.removeTaskFromList(originalDoneList, 1);
        TestTask[] newDoneList = Arrays.copyOf(originalDoneList, originalDoneList.length);
        newDoneList[0] = aliceEdit;
        
        // check if it works on the done list too
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(newDoneList));
        assertUndoSuccess(doneListBeforeDone);
        commandBox.runCommand("list");
        originalUndoneList[0] = aliceEdit;
        assertTrue(personListPanel.isListMatching(originalUndoneList));
        commandBox.runCommand("list done");
        assertRedoSuccess(newDoneList);
        commandBox.runCommand("list");
        originalUndoneList[0] = aliceRecur;
        assertTrue(personListPanel.isListMatching(originalUndoneList));

    }
    
    @Test
    public void undoAndRedoCommand_undoableCommandAfterUndo_resetUndo() {
        
        assertUndoneListClearCommandSuccess();
        assertUndoSuccess(originalUndoneList);
        assertAddSuccess(td.hoon, originalUndoneList);
        
        TestTask[] withHoon = TestUtil.addTasksToList(originalUndoneList, td.hoon);
        assertRedoSuccess(withHoon);
        
    }
    
    /**
     * Runs the undo command to undo the previous undoable command and confirms the result is correct.
     * @param expectedList A copy of the expected list after the undo command executes successfully.
     */
    private void assertUndoSuccess(TestTask... expectedList) {
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    
    /**
     * Runs the redo command to redo the previous undone command and confirms the result is correct.
     * @param expectedList A copy of the expected list after the redo command executes successfully.
     */
    private void assertRedoSuccess(TestTask... expectedList) {
        commandBox.runCommand("redo");
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    
    /**
     * Runs the add command to add the specified task and confirms the result is correct.
     * @param taskToAdd the task to be added
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToFloatingTask(taskToAdd.getName().getTaskName());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    
    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the 
     *        target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask personToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(personListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, TestUtil.generateDisplayString(personToDelete)));
    }
        
    /**
     * Runs the clear command and confirms the result is correct (that undone list is cleared).
     */
    private void assertUndoneListClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager undone list has been cleared!");
    }

    /**
     * Runs the clear command and confirms the result is correct (that done list is cleared).
     */
    private void assertDoneListClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager done list has been cleared!");
    }
    

}
