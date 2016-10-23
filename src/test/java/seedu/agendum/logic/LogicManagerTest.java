package seedu.agendum.logic;

import com.google.common.eventbus.Subscribe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.agendum.commons.core.Config;
import seedu.agendum.commons.core.EventsCenter;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.logic.commands.*;
import seedu.agendum.commons.events.ui.JumpToListRequestEvent;
import seedu.agendum.commons.events.ui.ShowHelpRequestEvent;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.commons.events.model.ToDoListChangedEvent;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.Model;
import seedu.agendum.model.ModelManager;
import seedu.agendum.model.ReadOnlyToDoList;
import seedu.agendum.model.task.*;
import seedu.agendum.storage.StorageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.agendum.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyToDoList latestSavedToDoList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(ToDoListChangedEvent tdl) {
        latestSavedToDoList = new ToDoList(tdl.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempToDoListFile = saveFolder.getRoot().getPath() + "TempToDoList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempToDoListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedToDoList = new ToDoList(model.getToDoList()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'to do list' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyToDoList, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new ToDoList(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal to do list data are same as those in the {@code expectedToDoList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedToDoList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyToDoList expectedToDoList,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        // Generate a sorted and UnmodifiableObservableList from expectedShownList for comparison
        TestDataHelper helper = new TestDataHelper();
        assertEquals(helper.generateSortedList(expectedShownList), model.getFilteredTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedToDoList, model.getToDoList());
        assertEquals(expectedToDoList, latestSavedToDoList);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new ToDoList(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        // TODO
        // currently, there are no invalid add argument format
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        // TODO
        // check for invalid task data e.g. empty name invalid time

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        ToDoList expectedTDL = new ToDoList();
        expectedTDL.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTDL,
                expectedTDL.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        ToDoList expectedTDL = new ToDoList();
        expectedTDL.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal to do list

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedTDL,
                expectedTDL.getTaskList());

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        ToDoList expectedTDL = helper.generateToDoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedTDL.getTaskList();

        // prepare to do list state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedTDL,
                expectedList);
    }


    /**
     * Confirms the 'incorrect index format behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     * @param wordsAfterIndex contains a string that will usually follow the command
     * 
     * This (overloaded) method is created for rename/schedule
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage, String wordsAfterIndex)
            throws Exception {
        assertCommandBehavior(commandWord + " " + wordsAfterIndex, expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1 " + wordsAfterIndex, expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1 " + wordsAfterIndex, expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0 " + wordsAfterIndex, expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number " + wordsAfterIndex, expectedMessage);
    }
    
    /**
     * Confirms the 'incorrect index format behaviour' for the given command
     * targeting a single/multiple task(s) in the shown list, using visible indices.
     * @param commandWord to test assuming it targets a single/multiple task(s) in the shown list, using visible indices.
     * 
     * This (overloaded) method is created for delete/mark/unmark.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertIncorrectIndexFormatBehaviorForCommand(commandWord, expectedMessage, " ");
        
        // multiple indices
        assertCommandBehavior(commandWord + " +1 2 3", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 1 2 -3", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 1 not_a_number 3 4", expectedMessage); //index cannot be a string
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     * @param wordsAfterIndex contains a string that will usually follow the command
     * 
     * This (overloaded) method is created for rename/schedule
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord, String wordsAfterIndex) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new ToDoList());
        for (Task p : taskList) {
            model.addTask(p);
        }
        // test boundary value (one-based index is 3 when list is of size 2)
        assertCommandBehavior(commandWord + " 3 " + wordsAfterIndex, expectedMessage, model.getToDoList(), taskList);
    }
    
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single/multiple task(s) in the shown list, using visible indices.
     * @param commandWord to test assuming it targets tasks in the last shown list based on visible indices.
     * 
     * This (overloaded) method is created for delete/mark/unmark.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        assertIndexNotFoundBehaviorForCommand(commandWord, "");

        // multiple indices
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(5);

        // set AB state to 5 tasks
        model.resetData(new ToDoList());
        for (Task p : taskList) {
            model.addTask(p);
        }
        // test boundary value (one-based index is 6 when list is of size 5)
        //invalid index is the last index given
        assertCommandBehavior(commandWord + " 1 6", expectedMessage, model.getToDoList(), taskList);
        //invalid index is not the first index
        assertCommandBehavior(commandWord + " 1 6 2", expectedMessage, model.getToDoList(), taskList);
        //invalid index is part of range
        assertCommandBehavior(commandWord + " 1-6", expectedMessage, model.getToDoList(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage, " ");
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select", " ");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        ToDoList expectedTDL = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedTDL,
                expectedTDL.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectSingleTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        // prepare expected TDL
        ToDoList expectedTDL = helper.generateToDoList(threeTasks);
        expectedTDL.removeTask(threeTasks.get(2));
        
        // prepare model
        helper.addToModel(model, threeTasks);

        // prepare for message
        List<Integer> deletedTaskVisibleIndices = helper.generateNumberList(3);
        List<ReadOnlyTask> deletedTasks = helper.generateReadOnlyTaskList(threeTasks.get(2));
        String tasksAsString = CommandResult.tasksToString(deletedTasks, deletedTaskVisibleIndices);
        
        // test boundary value (last task in the list)
        assertCommandBehavior("delete 3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }    

    public void execute_delete_removesCorrectRangeOfTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(4);

        // prepare expected TDL
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        expectedTDL.removeTask(fourTasks.get(2));
        expectedTDL.removeTask(fourTasks.get(1));

        // prepare model
        helper.addToModel(model, fourTasks);

        //prepare for message
        List<Integer> deletedTaskVisibleIndices = helper.generateNumberList(2, 3);
        List<ReadOnlyTask> deletedTasks = helper.generateReadOnlyTaskList(
                fourTasks.get(1), fourTasks.get(2));
        String tasksAsString = CommandResult.tasksToString(deletedTasks, deletedTaskVisibleIndices);

        // Delete tasks with visible index in range [startIndex, endIndex] = [2, 3]
        // Checks if the new to do list contains Task 1 and Task 4 from the last visible list
        assertCommandBehavior("delete 2-3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }

    @Test
    public void execute_delete_removesCorrectMultipleTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(4);

        // prepare expected TDL
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        expectedTDL.removeTask(fourTasks.get(3));
        expectedTDL.removeTask(fourTasks.get(2));
        expectedTDL.removeTask(fourTasks.get(1));

        // prepare model
        helper.addToModel(model, fourTasks);

        // prepare for message
        List<Integer> deletedTaskVisibleIndices = helper.generateNumberList(2, 3, 4);
        List<ReadOnlyTask> deletedTasks = helper.generateReadOnlyTaskList(
                fourTasks.get(1), fourTasks.get(2), fourTasks.get(3));
        String tasksAsString = CommandResult.tasksToString(deletedTasks, deletedTaskVisibleIndices);

        assertCommandBehavior("delete 2,3 4",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }


    @Test
    public void execute_store_successful() throws Exception {
        // setup expectations
        ToDoList expectedTDL = new ToDoList();
        String location = "data/test.xml";

        // execute command and verify result
        assertCommandBehavior("store " + location,
                String.format(StoreCommand.MESSAGE_SUCCESS, location),
                expectedTDL,
                expectedTDL.getTaskList());

        // execute command and verify result
        assertCommandBehavior("store default",
                String.format(StoreCommand.MESSAGE_LOCATION_DEFAULT, Config.DEFAULT_SAVE_LOCATION),
                expectedTDL,
                expectedTDL.getTaskList());
    }
    
    public void execute_store_fail_fileExists() throws Exception {
        // setup expectations
        ToDoList expectedTDL = new ToDoList();
        String location = "data/test.xml";

        // create file
        FileUtil.createIfMissing(new File(location));
        
        // error that file already exists
        assertCommandBehavior("store " + location,
                String.format(StoreCommand.MESSAGE_FILE_EXISTS, location),
                expectedTDL,
                expectedTDL.getTaskList());

        // delete file
        FileUtil.deleteFile(location);
    }


    @Test
    public void execute_markInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("mark", expectedMessage);
    }

    @Test
    public void execute_markIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("mark");
    }

    @Test
    public void execute_mark_marksCorrectSingleTaskAsCompleted() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        // prepared expected TDL
        ToDoList expectedTDL = helper.generateToDoList(threeTasks);
        expectedTDL.markTask(threeTasks.get(0));

        // prepare model
        helper.addToModel(model, threeTasks);

        // prepare for message
        List<Integer> markedTaskVisibleIndices = helper.generateNumberList(1);
        List<ReadOnlyTask> markedTasks = helper.generateReadOnlyTaskList(threeTasks.get(0));
        String tasksAsString = CommandResult.tasksToString(markedTasks, markedTaskVisibleIndices);

        // test boundary value (first task in the list)
        assertCommandBehavior("mark 1",
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }
    
    @Test
    public void execute_mark_marksCorrectRangeOfTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(4);

        // prepare expected TDL
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        expectedTDL.markTask(fourTasks.get(3));
        expectedTDL.markTask(fourTasks.get(2));

        // prepare model
        helper.addToModel(model, fourTasks);

        // prepare for message
        List<Integer> markedTaskVisibleIndices = helper.generateNumberList(3, 4);
        List<ReadOnlyTask> markedTasks = helper.generateReadOnlyTaskList(
                fourTasks.get(2), fourTasks.get(3));
        String tasksAsString = CommandResult.tasksToString(markedTasks, markedTaskVisibleIndices);
 
        // test boundary value (up to last task in the list)
        assertCommandBehavior("mark 3-4",
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }

    @Test
    public void execute_mark_marksCorrectMultipleTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(4);

        // prepare expected TDL
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        expectedTDL.markTask(fourTasks.get(3));
        expectedTDL.markTask(fourTasks.get(2));
        expectedTDL.markTask(fourTasks.get(1));

        // prepare model
        helper.addToModel(model, fourTasks);

        // prepare for message
        List<Integer> markedTaskVisibleIndices = helper.generateNumberList(2, 3, 4);
        List<ReadOnlyTask> markedTasks = helper.generateReadOnlyTaskList(
                fourTasks.get(1), fourTasks.get(2), fourTasks.get(3));
        String tasksAsString = CommandResult.tasksToString(markedTasks, markedTaskVisibleIndices);

        assertCommandBehavior("mark 2,3 4",
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }


    @Test
    public void execute_unmarkInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("unmark", expectedMessage);
    }

    @Test
    public void execute_unmarkIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("unmark");
    }

    @Test
    public void execute_unmark_UnmarksCorrectSingleTaskFromCompleted() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(2);
        threeTasks.add(helper.generateCompletedTask(3));

        // prepare expectedTDL - does not have any tasks marked as completed
        ToDoList expectedTDL = helper.generateToDoList(threeTasks);
        expectedTDL.unmarkTask(threeTasks.get(2));

        // prepare model
        helper.addToModel(model, threeTasks);

        // prepare for message
        List<Integer> unmarkedTaskVisibleIndices = helper.generateNumberList(3);
        List<ReadOnlyTask> unmarkedTasks = helper.generateReadOnlyTaskList(threeTasks.get(2));
        String tasksAsString = CommandResult.tasksToString(unmarkedTasks, unmarkedTaskVisibleIndices);

        // test boundary value - last task in the list
        assertCommandBehavior("unmark 3",
                String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }

    @Test
    public void execute_unmark_unmarksCorrectRangeOfTasks() throws Exception {
        // indexes provided are startIndex-endIndex.
        // Tasks with visible index in range [startIndex, endIndex] are marked
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(helper.generateTask(1), helper.generateTask(2),
                helper.generateCompletedTask(3), helper.generateCompletedTask(4));

        // prepare expectedTDL - does not have any tasks marked as completed
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        // Completed tasks will be at the bottom of the list
        expectedTDL.unmarkTask(fourTasks.get(2));
        expectedTDL.unmarkTask(fourTasks.get(3));

        // prepare model
        helper.addToModel(model, fourTasks);

        // prepare for message
        List<Integer> unmarkedTaskVisibleIndices = helper.generateNumberList(3, 4);
        List<ReadOnlyTask> unmarkedTasks = helper.generateReadOnlyTaskList(
                fourTasks.get(2), fourTasks.get(3));
        String tasksAsString = CommandResult.tasksToString(unmarkedTasks, unmarkedTaskVisibleIndices);

        assertCommandBehavior("unmark 3-4",
                String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }

    @Test
    public void execute_unmark_unmarksCorrectMultipleTasks() throws Exception {
        // unmark multiple indices specified (separated by space/comma)
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(helper.generateTask(1), helper.generateCompletedTask(2),
                helper.generateCompletedTask(3), helper.generateCompletedTask(4));

        // prepare expectedTDL - does not have any tasks marked as completed
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        expectedTDL.unmarkTask(fourTasks.get(3));
        expectedTDL.unmarkTask(fourTasks.get(2));
        expectedTDL.unmarkTask(fourTasks.get(1));

        // prepare model
        helper.addToModel(model, fourTasks);

        // prepare for message
        List<Integer> unmarkedTaskVisibleIndices = helper.generateNumberList(2, 3, 4);
        List<ReadOnlyTask> unmarkedTasks = helper.generateReadOnlyTaskList(
                fourTasks.get(1), fourTasks.get(2), fourTasks.get(3));
        String tasksAsString = CommandResult.tasksToString(unmarkedTasks, unmarkedTaskVisibleIndices);

        assertCommandBehavior("unmark 2,3 4",
                String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, tasksAsString),
                expectedTDL,
                expectedTDL.getTaskList());
    }


    @Test
    public void execute_renameInvalidArgsFormat_errorMessageShown() throws Exception {
        // invalid index format
        // a valid name is provided since invalid input values must be tested one at a time
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("rename", expectedMessage, "new task name");
        
        // invalid new task name format e.g. task name is not provided
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new ToDoList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        // a valid index is provided since we are testing for invalid name (empty string) here
        assertCommandBehavior("rename 1 ", expectedMessage, model.getToDoList(), taskList);
        
    }

    @Test
    public void execute_renameIndexNotFound_errorMessageShown() throws Exception {
        // a valid name is provided to only test for invalid index
        assertIndexNotFoundBehaviorForCommand("rename", "new task name");
    }

    @Test
    public void  execute_renameToGetDuplicate_notAllowed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task toBeDuplicated = helper.adam();
        Task toBeRenamed = helper.generateTask(1);
        List<Task> twoTasks = helper.generateTaskList(toBeDuplicated, toBeRenamed);
        ToDoList expectedTDL = helper.generateToDoList(twoTasks);

        helper.addToModel(model, twoTasks);

        // execute command and verify result
        // a valid index must be provided to check if the name is invalid (due to a duplicate)
        assertCommandBehavior(
                "rename 2 " + toBeDuplicated.getName().toString(),
                RenameCommand.MESSAGE_DUPLICATE_TASK,
                expectedTDL,
                expectedTDL.getTaskList());
    }

    @Test
    public void execute_rename_RenamesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(2);
        Task taskToRename = helper.generateCompletedTask(3);
        //TODO: replace taskToRename with a task with deadlines etc. Check if other attributes are preserved
        threeTasks.add(taskToRename);

        // prepare expected TDL
        ToDoList expectedTDL = helper.generateToDoList(threeTasks);
        Task renamedTask = new Task(taskToRename);
        String newTaskName = "a brand new task name";
        renamedTask.setName(new Name(newTaskName));
        expectedTDL.updateTask(taskToRename, renamedTask);

        // prepare model
        helper.addToModel(model, threeTasks);

        //boundary value: use the last task
        assertCommandBehavior("rename 3 " + newTaskName,
                String.format(RenameCommand.MESSAGE_SUCCESS, "3", newTaskName),
                expectedTDL,
                expectedTDL.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        ToDoList expectedTDL = helper.generateToDoList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }


    @Test
    public void execute_undo_identifiesNoPreviousCommand() throws Exception {
        assertCommandBehavior("undo", UndoCommand.MESSAGE_FAILURE, new ToDoList(), Collections.emptyList());
    }

    @Test
    public void execute_undo_reversePreviousMutatingCommand() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("old name");
        List<Task> listWithOneTask = helper.generateTaskList(p1);
        ToDoList expectedTDL = helper.generateToDoList(listWithOneTask);
        ArrayList<ReadOnlyTask> arrayListWithOneTask = new ArrayList<ReadOnlyTask>();
        arrayListWithOneTask.add(p1);

        //Undo add command
        model.addTask(p1);
        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, new ToDoList(), Collections.emptyList());

        //Undo delete command
        model.addTask(p1);
        model.deleteTasks(arrayListWithOneTask);
        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedTDL, listWithOneTask);

        //Undo clear command
        model.resetData(new ToDoList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedTDL, listWithOneTask);

        //Undo rename command
        Task p2 = new Task(p1);
        p2.setName(new Name("new name"));
        model.updateTask(p1, p2);
        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedTDL, listWithOneTask);

        //Undo mark command
        model.markTasks(arrayListWithOneTask);
        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedTDL, listWithOneTask);

        //Undo unmark command
        model.markTasks(arrayListWithOneTask);
        Task p3 = new Task(p1); //p1 clone
        p3.markAsCompleted();
        listWithOneTask = helper.generateTaskList(p3);
        expectedTDL = helper.generateToDoList(listWithOneTask);
        arrayListWithOneTask.clear();
        arrayListWithOneTask.add(p3);
        model.unmarkTasks(arrayListWithOneTask);
        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedTDL, listWithOneTask);

    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            return new Task(name);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed)
            );
        }
        
        /**
         * Generates a valid completed task with the given seed
         */
        Task generateCompletedTask(int seed) throws Exception {
            Task newTask = generateTask(seed);
            newTask.markAsCompleted();
            return newTask;
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name)
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());

            return cmd.toString();
        }

        /**
         * Generates an ToDoList with auto-generated tasks.
         */
        ToDoList generateToDoList(int numGenerated) throws Exception{
            ToDoList toDoList = new ToDoList();
            addToToDoList(toDoList, numGenerated);
            return toDoList;
        }

        /**
         * Generates an ToDoList based on the list of Tasks given.
         */
        ToDoList generateToDoList(List<Task> tasks) throws Exception{
            ToDoList toDoList = new ToDoList();
            addToToDoList(toDoList, tasks);
            return toDoList;
        }

        /**
         * Adds auto-generated Task objects to the given ToDoList
         * @param toDoList The ToDoList to which the Tasks will be added
         */
        void addToToDoList(ToDoList toDoList, int numGenerated) throws Exception{
            addToToDoList(toDoList, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given ToDoList
         */
        void addToToDoList(ToDoList toDoList, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                toDoList.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }
  
        List<ReadOnlyTask> generateReadOnlyTaskList(ReadOnlyTask... tasks) {
            return Arrays.asList(tasks);
        }

        List<Integer> generateNumberList(Integer... numbers){
            return Arrays.asList(numbers);
        }

        /**
         * Generate a sorted UnmodifiableObservableList from expectedShownList
         */
        UnmodifiableObservableList<Task> generateSortedList(List<? extends ReadOnlyTask> expectedShownList) throws Exception {
            List<Task> taskList = new ArrayList<Task>();
            for (int i = 0; i < expectedShownList.size(); i++) {
                taskList.add(new Task(expectedShownList.get(i)));
            }
            ToDoList toDoList = generateToDoList(taskList); 
            return new UnmodifiableObservableList<>(toDoList.getTasks().sorted());
        }

    }
}
