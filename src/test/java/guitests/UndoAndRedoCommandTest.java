package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.TimePeriod;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_DONE_ITEM_SUCCESS;

//@@author A0093960X
public class UndoAndRedoCommandTest extends DearJimGuiTest {
    
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
        TestTask[] originalUndoneList = td.getTypicalUndoneTasks();
        TestTask[] originalDoneList = td.getTypicalDoneTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, originalUndoneList);
        
        // undo the add
        assertUndoSuccess(originalUndoneList);
        
        // redo the add
        TestTask[] withHoon = TestUtil.addFloatingTasksToList(originalUndoneList, taskToAdd);
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
        TestTask[] originalList = td.getTypicalUndoneTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, originalList);
        TestTask[] withHoon = TestUtil.addFloatingTasksToList(originalList, taskToAdd);
        assertDeleteSuccess(8, withHoon);
        
        // undo the delete
        assertUndoSuccess(withHoon);
        
        // delete hoon again
        assertRedoSuccess(originalList);
    }
    
    @Test
    public void undoAndRedoCommand_deleteDoneTask_readdForUndoDeleteForRedo() {
        TestTask[] currentUndoneList = td.getTypicalUndoneTasks();
        TestTask[] currentDoneList = td.getTypicalDoneTasks();  
        
        TestTask[] withGeorge = TestUtil.addFloatingTasksToList(currentDoneList, td.george);
        
        assertDoneSuccess(7, currentUndoneList, currentDoneList);
        assertUndoSuccess(currentUndoneList);
        assertRedoSuccess(withGeorge);
    }
    
    @Test
    public void undoAndRedoCommand_clearUndoneTasks_unclearForUndoReclearForRedo() {
        TestTask[] currentUndoneList = td.getTypicalUndoneTasks();
        TestTask[] currentDoneList = td.getTypicalDoneTasks();
        assertUndoneListClearCommandSuccess();
        assertUndoSuccess(currentUndoneList);
        assertRedoSuccess();
        
        // check if targets undone tasks even on done view
        commandBox.runCommand("list done");
        assertUndoSuccess(currentDoneList);
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(currentUndoneList));
        commandBox.runCommand("list done");
        assertRedoSuccess(currentDoneList);
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching());
    }

    
    @Test
    public void undoAndRedoCommand_clearDoneTasks_unclearForUndoReclearForRedo() {
        TestTask[] currentUndoneList = td.getTypicalUndoneTasks();
        TestTask[] currentDoneList = td.getTypicalDoneTasks();      
        assertDoneSuccess(1, currentUndoneList, currentDoneList);
        
        
    }
    
    
    /*
    @Test
    public void undoAndRedo() {
        // TODO: test delete more than one at once, done more than one at once
  
        
        
        // add alice
        TestTask aliceTask = new TestTask(td.alice);
        assertAddSuccess(aliceTask);
        
        // primitive edit undo and redo testing until the assertEditSuccess is complete
        TestTask aliceTaskBackup = new TestTask(aliceTask);
        commandBox.runCommand("edit 1 Call Alice from 2pm repeat every day -high");
        //commandBox.runCommand("edit 1 Call Alice from 2pm to 3pm repeat every day -high");
        aliceTask.setName(new Name("Call Alice"));
        //TODO: Edited by Zhi Yuan because I edited DateTime. Edmund take note :)
        try {
            aliceTask.setStartDate(DateTime.convertStringToDate("2pm"));
        } catch (IllegalValueException e1) {
            assert false : "The test data provided cannot be invalid";
        }
        //aliceTask.setEndDate(DateTime.convertStringToEndDate("3pm", aliceTask.getStartDate().get()));
        try {
            aliceTask.setRecurrence(new RecurrenceRate("1", "day"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        aliceTask.setPriority(Priority.HIGH);
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        assertUndoSuccess(aliceTaskBackup);
        assertRedoSuccess(aliceTask);        

        TestTask recurredAliceTask = new TestTask(aliceTask);
        try {
            recurredAliceTask.setStartDate(DateTime.convertStringToDate("tomorrow 2pm"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }

        // primitive done undo and redo testing until the assertDoneSuccess is complete
        commandBox.runCommand("done 1");
        assertTrue(personListPanel.isListMatching(recurredAliceTask));
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        // automatically directs me back to undone view
        assertUndoSuccess();
        commandBox.runCommand("list");
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        // automatically directs me back to undone view
        assertRedoSuccess(recurredAliceTask);
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(aliceTask));

        commandBox.runCommand("list");
        
        // test whether the redo resets properly
        assertClearCommandSuccess();
        assertAddSuccess(taskToAdd);
        
        
        // this shows that the redo has reset
        assertUndoSuccess();
        assertAddSuccess(aliceTaskBackup);
        assertRedoSuccess(aliceTaskBackup);
        
    }
    */
    
    
    
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
        TestTask[] expectedList = TestUtil.addFloatingTasksToList(currentList, taskToAdd);
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
        assertResultMessage(String.format(MESSAGE_DELETE_ITEM_SUCCESS, personToDelete));
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
    
    /**
     * Runs the done command to archive the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to archive the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before archiving).
     * @param currentDoneList A copy of the current list of done tasks (before archiving).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList, final TestTask[] currentDoneList) {
        TestTask taskToDone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        TestTask[] expectedDoneTaskList = TestUtil.addFloatingTasksToList(currentDoneList, taskToDone);
        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the archived task
        assertTrue(personListPanel.isListMatching(expectedRemainder));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_ITEM_SUCCESS, taskToDone));

        //confirm the done list now contains all previous done tasks plus the new done task
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(expectedDoneTaskList));
        
        //switch back to normal list view
        commandBox.runCommand("list");
    }

}
