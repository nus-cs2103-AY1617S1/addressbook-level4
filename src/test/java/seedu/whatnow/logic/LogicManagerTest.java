//@@author A0141021H
package seedu.whatnow.logic;

import com.google.common.eventbus.Subscribe;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.EventsCenter;
import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.events.ui.ShowHelpRequestEvent;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.commons.util.ConfigUtil;
import seedu.whatnow.logic.Logic;
import seedu.whatnow.logic.LogicManager;
import seedu.whatnow.logic.commands.*;
import seedu.whatnow.model.Model;
import seedu.whatnow.model.ModelManager;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.WhatNow;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.tag.UniqueTagList.DuplicateTagException;
import seedu.whatnow.model.task.*;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.storage.StorageManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.whatnow.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    // These are for checking the correctness of the events raised
    private ReadOnlyWhatNow latestSavedWhatNow;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempWhatNowFile = saveFolder.getRoot().getPath() + "TempWhatNow.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempWhatNowFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedWhatNow = new WhatNow(model.getWhatNow()); // last saved
        // assumed to be
        // up to date
        // before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void executeCommand_invalidArgument_incorrectCommandFeedback() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both 'WhatNow' and the 'last shown list' are expected to be empty.
     * 
     * @see #assertCommandBehavior(String, String, ReadOnlyWhatNow, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new WhatNow(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's
     * state are as expected:<br>
     * - the internal WhatNow data are same as those in the
     * {@code expectedWhatNow} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedWhatNow} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage, ReadOnlyWhatNow expectedWhatNow, List<? extends ReadOnlyTask> expectedShownList) throws Exception {       
        // Execute the command
        CommandResult result = logic.execute(inputCommand);
        // Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);

        if (!inputCommand.contains(FindCommand.COMMAND_WORD) && !inputCommand.contains(ChangeCommand.COMMAND_WORD) && !inputCommand.contains(UndoCommand.COMMAND_WORD) && !inputCommand.contains(RedoCommand.COMMAND_WORD)
                && !inputCommand.contains(FreeTimeCommand.COMMAND_WORD) && !inputCommand.contains(UndoCommand.COMMAND_WORD) && !inputCommand.contains(AddCommand.COMMAND_WORD)) {
            assertEquals(expectedShownList, model.getAllTaskTypeList());
        }

        // Confirm the state of data (saved and in-memory) is as expected
        if (!inputCommand.contains(ChangeCommand.COMMAND_WORD) && !inputCommand.contains(FreeTimeCommand.COMMAND_WORD)&& !inputCommand.contains(UndoCommand.COMMAND_WORD) && !inputCommand.contains(RedoCommand.COMMAND_WORD) && !inputCommand.contains(AddCommand.COMMAND_WORD)) {
            assertEquals(expectedWhatNow, model.getWhatNow());
            //assertEquals(expectedWhatNow, latestSavedWhatNow);
        }
    }

    @Test
    public void executeCommand_unknownCommandWord_unknownCommandFeedback() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void executeHelp_correctArgument_helpLaunched() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    // @@author A0139772U
    @Test
    public void executeExit_correctArgument_programExit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void executeClear_correctArgument_dataCleared() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new WhatNow(), Collections.emptyList());
    }

    @Test
    public void executeAdd_invalidArgsFormat_incorrectCommandFeedback() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior("add wrong/args wrong/args", expectedMessage);
    }

    @Test
    public void executeAdd_invalidTaskData_incorrectCommandFeedback() throws Exception {
        assertCommandBehavior("add []\\[;] p12345 evalid@e.mail avalid, whatnow",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        assertCommandBehavior("add Valid Name p12345 evalid@e.mail avalid, whatnow t/invalid_-[.tag",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

    }

    @Test
    public void executeAdd_noDuplicate_addSuccess() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.grapes();
        WhatNow expectedAB = new WhatNow();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());

    }

    @Test
    public void executeAdd_duplicated_duplicateTaskExceptionThrown() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.grapes();
        WhatNow expectedAB = new WhatNow();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal WhatNow

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK, expectedAB,
                expectedAB.getTaskList());

    }
    //@@author A0139128A
    @Test
    public void executeAdd_alphabeticalMonths_successMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        WhatNow expectedAB = new WhatNow();
        Task toBeAdded = new Task(new Name("Drink coke"), ("18/03/2017"), null , null, null, null, null, null, null, new UniqueTagList(new Tag("tag")),
                    "incomplete", null);
        expectedAB.addTask(toBeAdded);
        
        logic.execute("add \"Drink coke\" on 18 March 2017");
        
        assertCommandBehavior(helper.generateAddCommand(toBeAdded), String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());
    }
    
    //@@author A0139128A
    @Test
    public void executeList_correctArgument_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        WhatNow expectedAB = helper.generateWhatNow(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare WhatNow state
        helper.addToModel(model, 2);

        assertCommandBehavior("list", ListCommand.INCOMPLETE_MESSAGE_SUCCESS, expectedAB, expectedList);
    }
    //@@author A0139128A
    @Test
    public void executeListDone_correctArgument_showDoneTasks() throws Exception {
        //prepare expectations
        TestDataHelper helper = new TestDataHelper();
        WhatNow expectedA = helper.generateModifiedWhatNow(1);
        List<? extends ReadOnlyTask> expectedList = expectedA.getTaskList();


        //prepare WhatNow state
        helper.addToModel(model, 1);
        helper.doneToModel(model, 1);

        assertCommandBehavior("list done", ListCommand.COMPLETE_MESSAGE_SUCCESS, expectedA, expectedList);
    }
    //@@author A0139128A
    @Test
    public void executeListAll_correctArgument_showDownTasks() throws Exception {
        //prepare expectations
        TestDataHelper helper = new TestDataHelper();
        WhatNow expectedA = helper.generateModifiedWhatNow(2);

        List<? extends ReadOnlyTask> expectedList = expectedA.getTaskList();

        //prepare WhatNow state
        helper.addToModel(model, 2);
        helper.doneToModel(model, 1);

        assertCommandBehavior("list all", ListCommand.MESSAGE_SUCCESS, expectedA, expectedList);
    }

    //@@author A0139128A
    @Test
    public void execute_undoCommand_launch_doesNotExist_errorMessageShown() throws Exception {
        List<Task> expectedList = null;
        assertCommandBehavior("undo", UndoCommand.MESSAGE_FAIL, new WhatNow(), expectedList);
    }


    //@@author A0139128A
    @Test
    public void execute_redoCommand_launch_doesNotExist_errorMessageShown() throws Exception {
        List<Task> expectedList = null;
        assertCommandBehavior("redo", RedoCommand.MESSAGE_FAIL, new WhatNow(), expectedList);
    }

    //@@author A0139128A
    @Test
    public void execute_undoCommandForAdd_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        WhatNow expectedA = helper.generateWhatNow(1);
        List<? extends ReadOnlyTask> expectedList = expectedA.getTaskList();

        expectedA.removeTask(expectedList.get(0));

        logic.execute("add \"Task 1\" on 23/02/2017 t/tag1 t/tag2");
        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedA , expectedList);
    }
    //@@author A0139128A
    @Test
    public void execute_redoCommandForNewAddCommandAfterAnUndoAndRedo_failureMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        Task toAdd = helper.generateTaskWithName("Dummy");
        expectedAB.addTask(toAdd);
        addThreeTasksToLogic();
        logic.execute("undo");
        logic.execute("redo");
        logic.execute("add \"Dummy\"");
        
        assertCommandBehavior("redo", RedoCommand.MESSAGE_FAIL, expectedAB, expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_redoTheUndoForAdd_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        WhatNow expectedAB = helper.generateWhatNow(threeTasks);

        addThreeTasksToLogic();
        logic.execute("undo");

        assertCommandBehavior("redo", RedoCommand.MESSAGE_SUCCESS, expectedAB, expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_redoCommandForAdd_failMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        WhatNow expectedAB = helper.generateWhatNow(threeTasks);

        addThreeTasksToLogic();
        logic.execute("redo");

        assertCommandBehavior("redo", RedoCommand.MESSAGE_FAIL, expectedAB , expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_undoCommandForDelete_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        List<Task> expectedList = helper.generateTaskList(1);

        WhatNow expectedA = helper.generateWhatNow(expectedList);
        helper.addToModel(model, 1);
        logic.execute("delete schedule 1");

        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedA, expectedA.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_redoCommandForDelete_failMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        logic.execute("delete schedule 2");

        assertCommandBehavior("redo", RedoCommand.MESSAGE_FAIL, expectedAB, expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_undoCommandForClear_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        WhatNow expectedAB = helper.generateWhatNow(threeTasks);

        helper.addToModel(model, 3);
        logic.execute("clear");

        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedAB, expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_redoTwiceTheUndoForClear_failMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        helper.addToModel(model, 3);
        logic.execute("clear");
        logic.execute("undo");
        logic.execute("redo");
        assertCommandBehavior("redo", RedoCommand.MESSAGE_FAIL, new WhatNow(), null);
    }
    //@@author A0139128A
    @Test
    public void execute_undoDoneCommand_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        WhatNow expectedAB = helper.generateWhatNow(threeTasks);

        addThreeTasksToLogic();
        logic.execute("done schedule 1");
        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedAB, expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void excute_undoUndoneCommand_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        WhatNow expectedAB = helper.generateWhatNow(threeTasks);

        addThreeTasksToLogic();

        logic.execute("done schedule 1");
        logic.execute("list done");
        logic.execute("undone schedule 1");

        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedAB, expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_redoTheUndoForDoneCommand_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        expectedAB.removeTask(threeTasks.get(2));
        addThreeTasksToLogic();

        logic.execute("done schedule 1");
        logic.execute("undo");

        assertCommandBehavior("redo", RedoCommand.MESSAGE_SUCCESS, expectedAB, expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_undoDeleteCommand_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        addThreeTasksToLogic();

        logic.execute("update schedule 1 tag help");

        assertCommandBehavior("undo", UndoCommand.MESSAGE_SUCCESS, expectedAB, expectedAB.getTaskList());
    }
    //@@author A0139128A
    @Test
    public void execute_undoTheRedoCommand_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        Task toUpdate = new Task(threeTasks.get(0).getName(), threeTasks.get(0).getTaskDate(),
                threeTasks.get(0).getStartDate(), threeTasks.get(0).getEndDate(), "5pm",
                threeTasks.get(0).getStartTime(), threeTasks.get(0).getEndTime(), threeTasks.get(0).getPeriod(),
                threeTasks.get(0).getEndPeriod(), threeTasks.get(0).getTags(),
                threeTasks.get(0).getStatus(), threeTasks.get(0).getTaskType());
        expectedAB.updateTask(threeTasks.get(0), toUpdate);
        addThreeTasksToLogic();

        logic.execute("update schedule 1 time 5pm");
        logic.execute("undo");

        assertCommandBehavior("redo", RedoCommand.MESSAGE_SUCCESS, expectedAB, expectedAB.getTaskList());
    }
    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForUpdateCommand(String commandWord, String taskType,
            String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord + " " + taskType + " description Check if index is missing", expectedMessage); // index
        // missing
        assertCommandBehavior(commandWord + " " + taskType + " +1" + " description Check if index is unsigned",
                expectedMessage); // index should be unsigned
        assertCommandBehavior(commandWord + " " + taskType + " -1" + " description Check if index is unsigned",
                expectedMessage); // index should be unsigned
        assertCommandBehavior(commandWord + " " + taskType + " 0" + " description Check if index is zero",
                expectedMessage); // index cannot be 0
        assertCommandBehavior(
                commandWord + " " + taskType + " not_a_number" + " description Check if index is not a number",
                expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForUpdateCommand(String commandWord, String taskType) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set WN state to 2 tasks
        model.resetData(new WhatNow());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " " + taskType + " 3" + " description Check if index exists",
                expectedMessage, model.getWhatNow(), taskList);
    }

    // @@author A0126240W
    @Test
    public void executeUpdate_invalidArgsFormat_incorrectFormatFeedback() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForUpdateCommand("update", "todo", expectedMessage);
    }

    @Test
    public void executeUpdate_indexNotFound_incorrectCommandFeedback() throws Exception {
        assertIndexNotFoundBehaviorForUpdateCommand("update", "todo");
    }

    @Test
    public void executeUpdate_correctArgument_taskUpdated() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.todo("Buy milk", "23/2/2017", "lowPriority", "inProgress");
        WhatNow expectedAB = new WhatNow();
        expectedAB.addTask(toBeAdded);
        expectedAB.addTask(helper.grapes());    
        List<Task> taskList = helper.generateTaskList(toBeAdded, helper.grapes());
        helper.addToModel(model, taskList);

        // execute command and verify result
        ReadOnlyTask taskToUpdate = taskList.get(0);
        Task toUpdate = helper.todo("Buy chocolate milk", "23/2/2017", "inProgress", "lowPriority");
        expectedAB.updateTask(taskToUpdate, toUpdate);

        assertCommandBehavior(helper.generateUpdateCommand("description", "Buy chocolate milk"), String
                .format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate),
                expectedAB, expectedAB.getTaskList());

        taskToUpdate = toUpdate;
        toUpdate = helper.todo("Buy chocolate milk", "23/2/2017", "highPriority", "Completed");
        expectedAB.updateTask(taskToUpdate, toUpdate);

        assertCommandBehavior(helper.generateUpdateCommand("tag", "highPriority Completed"), String
                .format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate),
                expectedAB, expectedAB.getTaskList());

        taskToUpdate = toUpdate;
        toUpdate = helper.todo("Buy chocolate milk", "12/04/2017", "highPriority", "Completed");
        expectedAB.updateTask(taskToUpdate, toUpdate);

        assertCommandBehavior(helper.generateUpdateCommand("date", "12/04/2017"), String
                .format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate),
                expectedAB, expectedAB.getTaskList());
    }
    /**
     * Manually adds 3 tasks to the logic
     */
    private void addThreeTasksToLogic() throws Exception {
        logic.execute("add \"Task 1\" on 23/02/2017 t/tag1 t/tag2");
        logic.execute("add \"Task 2\" on 23/02/2017 t/tag2 t/tag3");
        logic.execute("add \"Task 3\" on 23/02/2017 t/tag4 t/tag3");
    }
    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String taskType,
            String expectedMessage) throws Exception {
        if (!("").equals(taskType)) {
            assertCommandBehavior(commandWord + " " + taskType, expectedMessage);
            assertCommandBehavior(commandWord + " " + taskType + " +1", expectedMessage);
            assertCommandBehavior(commandWord + " " + taskType + " -1", expectedMessage);
            assertCommandBehavior(commandWord + " " + taskType + " 0", expectedMessage);
            assertCommandBehavior(commandWord + " " + taskType + " not_a_number", expectedMessage);
        } else {
            assertCommandBehavior(commandWord, expectedMessage);
            assertCommandBehavior(commandWord + " +1", expectedMessage);
            assertCommandBehavior(commandWord + " -1", expectedMessage);
            assertCommandBehavior(commandWord + " 0", expectedMessage);
            assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
        }
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord, String taskType) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set WN state to 2 tasks
        model.resetData(new WhatNow());
        for (Task p : taskList) {
            model.addTask(p);
        }

        if (!("").equals(taskType))
            assertCommandBehavior(commandWord + " " + taskType + " 3", expectedMessage, model.getWhatNow(), taskList);
        else
            assertCommandBehavior(commandWord + " 3", expectedMessage, model.getWhatNow(), taskList);
    }

    @Test
    public void executeDelete_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", "todo", expectedMessage);
    }

    @Test
    public void executeDelete_indexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete", "todo");
    }

    @Test
    public void executeDelete_validIndex_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete schedule 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)), expectedAB,
                expectedAB.getTaskList());
    }

    //@@author A0139128A
    @Test
    public void execute_markDoneInvalidIndexFormat_errorMessageShown() throws
    Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertIncorrectIndexFormatBehaviorForCommand("done", "todo 2",
                expectedMessage);
    }

    //@@author A0139128A
    @Test
    public void execute_markUndoneInvalidIndexFormat_ErrorMessageShown() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertIncorrectIndexFormatBehaviorForCommand("undone", "todo 2", expectedMessage);
    }

    //@@author A0139128A
    @Test
    public void execute_markUndoneMissingIndexFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUndoneCommand.MESSAGE_MISSING_INDEX);
        assertIncorrectIndexFormatBehaviorForCommand("undone", "todo", expectedMessage);
    }

    //@@author A0141021H
    @Test
    public void execute_markUndone_markCorrectTask() throws Exception { 
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateCompletedTaskList(3);

        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        expectedAB.markTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("undone schedule 2",
                String.format(MarkUndoneCommand.MESSAGE_MARK_TASK_SUCCESS,
                        threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

    //@@author A0139128A
    @Test
    public void execute_markDoneIndexNotFound_errorMessageShown() throws
    Exception {
        assertIndexNotFoundBehaviorForCommand("done", "todo");
    }

    /**
     * Confirms the 'invalid argument behaviour' for the given command
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list.
     */
    private void assertIncorrectArgsFormatBehavior(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord + " description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " location" + " description Check if command is incorrect",
                expectedMessage);
        assertCommandBehavior(commandWord + " to" + " description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " C:/Users/Raul/Desktop" + " description Check if command is incorrect",
                expectedMessage);
        assertCommandBehavior(commandWord + " location" + "C:/Users/Abernathy/Documents"
                + " description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(
                commandWord + " to" + "C:/Users/Dorain/Desktop" + "description Check if command is incorrect",
                expectedMessage);
        assertCommandBehavior(
                commandWord + " locationto" + " C:/Users/Emmet/Documents" + "description Check if command is incorrect",
                expectedMessage);
        assertCommandBehavior(commandWord + " location to" + "C:/Users/Gina/Documents C:/Users/Hamlet/D"
                + "description Check if command is incorrect", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument behaviour' for the given command
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list.
     */
    private void assertInvalidPathBehavior(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord + " doesnotexistfolder" + "description Check if path is incorrect",
                expectedMessage);
        assertCommandBehavior(commandWord + " cs2103projectfolder" + "description Check if path is incorrect",
                expectedMessage);
    }

    @Test
    public void execute_changeLocationInvalidArgsFormat_errorMessageShown()
            throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeCommand.MESSAGE_USAGE);
        assertIncorrectArgsFormatBehavior("change", expectedMessage);
    }

    @Test
    public void execute_changeLocationInvalidPath_errorMessageShown() throws
    Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_PATH,
                ChangeCommand.MESSAGE_USAGE);
        assertInvalidPathBehavior("change location to", expectedMessage);
    }

    @Test
    public void execute_changeLocation_movesToCorrectPath() throws Exception
    {
        String egPath = "./docs";
        assertCommandBehavior("change location to " + egPath,
                String.format(ChangeCommand.MESSAGE_SUCCESS, egPath + "/whatnow.xml",
                        null, null));

        egPath = "./data";
        assertCommandBehavior("change location to " + egPath,
                String.format(ChangeCommand.MESSAGE_SUCCESS, egPath + "/whatnow.xml",
                        null, null));
    }
    @Test
    public void execute_undoChangeLocation_movesToCorrectPath() throws Exception {
        String egPath = "./docs";
        String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
        Config config = configOptional.orElse(new Config());
        String currPath = config.getWhatNowFilePath();
        
        logic.execute("change location to " + egPath);
        assertCommandBehavior("undo", String.format(ChangeCommand.MESSAGE_UNDO_SUCCESS, currPath), null, null);
    }
    @Test
    public void execute_redoTheundoChangeLocation_movesToCorrectPath() throws Exception {
        String egPath = "./docs";
        String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
        Config config = configOptional.orElse(new Config());
        String currPath = config.getWhatNowFilePath();
        
        logic.execute("change location to " + egPath);
        logic.execute("undo");
        assertCommandBehavior("redo", String.format(ChangeCommand.MESSAGE_REDO_SUCCESS, egPath + "/whatnow.xml"), null, null);
    }
    @Test
    public void executeFind_invalidArgsFormat_incorrectComandFeedback() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void executeFind_onlyMatchesFullWordsInNames_displayMatchedTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        WhatNow expectedAB = helper.generateWhatNow(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void executeFind_isNotCaseSensitive_displayAllFoundIgnoringCase() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        WhatNow expectedAB = helper.generateWhatNow(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void executeFind_matchesIfAnyKeywordPresent_displayAllFoundMatchingAnyKeyword() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        WhatNow expectedAB = helper.generateWhatNow(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    //@@author A0139772U
    @Test
    public void executeFreetime_noDatePresent_incorrectCommandFeedback() throws Exception {
        assertCommandBehavior("freetime", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
    }
    //@@author A0139772U
    @Test
    public void executeFreeTime_farfarIntoTheFutureDate_freeSlotFound() throws Exception {
        assertCommandBehavior("freetime 12/12/2222", FreeTimeCommand.MESSAGE_SUCCESS + "12/12/2222\n" + "[[12:00am, 11:59pm]]");
    }
    //@@author A0139772U
    @Test
    public void executeFreeTime_allFreeSlotsOnDateTaken_freeSlotNotFound() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task test = helper.generateTask(0);
        test.setTaskDate("12/12/2222");
        test.setStartTime("12:00am");
        test.setEndTime("11:59pm");

        WhatNow expectedAB = new WhatNow();
        expectedAB.addTask(test);

        model.addTask(test);

        assertCommandBehavior("freetime 12/12/2222", FreeTimeCommand.MESSAGE_NO_FREE_TIME_FOUND + "12/12/2222", 
                expectedAB, expectedAB.getTaskList());
        model.deleteTask(test);
    }
    //@@author A0139772U
    @Test
    public void executeFreeTime_blockPeriodWithStartEndDateNotTaken_freeSlotNotFound() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Task test = helper.generateTask(0);
        test.setTaskDate(null);
        test.setStartDate("12/12/2222");
        test.setEndDate("14/12/2222");
        test.setStartTime("12:00am");
        test.setEndTime("11:59pm");

        model.addTask(test);

        assertCommandBehavior("freetime 14/12/2222", FreeTimeCommand.MESSAGE_NO_FREE_TIME_FOUND + "14/12/2222");
    }
    //@@author A0139772U
    @Test
    public void executeFreeTime_blockPeriodWithStartAndEndDateTaken_freeSlotNotFound() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Task preloaded1 = helper.generateTask(1);
        preloaded1.setTaskDate("12/12/2222");
        preloaded1.setStartTime("08:00am");
        preloaded1.setEndTime("09:00am");

        Task preloaded2 = helper.generateTask(2);
        preloaded2.setTaskDate("14/12/2222");
        preloaded2.setStartTime("08:00am");
        preloaded2.setEndTime("09:00am");

        Task test = helper.generateTask(0);
        test.setTaskDate(null);
        test.setStartDate("12/12/2222");
        test.setEndDate("14/12/2222");
        test.setStartTime("12:00am");
        test.setEndTime("11:59pm");

        model.addTask(preloaded1);
        model.addTask(preloaded2);
        model.addTask(test);

        assertCommandBehavior("freetime 14/12/2222", FreeTimeCommand.MESSAGE_NO_FREE_TIME_FOUND + "14/12/2222");
        model.deleteTask(preloaded1);
        model.deleteTask(preloaded2);
        model.deleteTask(test);
    }
    //@@author A0139772U
    @Test
    public void getPinnedItems_pinByTag_pinnedCorrectly() throws Exception {
        model.updatePinnedItemsToShowMatchKeywords("date", "none");

        TestDataHelper helper = new TestDataHelper();

        Task preload1 = helper.generateTask(0);
        String preload1Tag = preload1.getTags().getInternalList().get(0).tagName;

        Task preload2 = helper.generateTask(1);
        preload2.setTags(new UniqueTagList(new Tag(preload1Tag)));
        preload2.setTaskDate(null);        

        model.addTask(preload1);
        model.addTask(preload2);

        UnmodifiableObservableList<ReadOnlyTask> testList = model.getPinnedItems("tag", preload1Tag);
        assertTrue(testList.size() == 2);

        model.deleteTask(preload1);
        model.deleteTask(preload2);
    }
    //@@author A0139772U
    @Test
    public void getPinnedItems_pinToday_pinnedCorrectly() throws Exception {
        model.updatePinnedItemsToShowMatchKeywords("date", "none");

        TestDataHelper helper = new TestDataHelper();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        String today = df.format(cal.getTime());

        Task preload1 = helper.generateTask(0);
        preload1.setTaskDate(today);

        Task preload2 = helper.generateTask(1);
        preload2.setTaskDate("12/12/2222");

        model.addTask(preload1);
        model.addTask(preload2);

        UnmodifiableObservableList<ReadOnlyTask> testList = model.getPinnedItems("date", "today");
        assertTrue(testList.size() == 1);

        model.deleteTask(preload1);
        model.deleteTask(preload2);
    }
    //@@author A0139772U
    @Test
    public void getPinnedItems_pinByDate_pinnedCorrectly() throws Exception {
        model.updatePinnedItemsToShowMatchKeywords("date", "none");

        TestDataHelper helper = new TestDataHelper();

        Task preload1 = helper.generateTask(0);
        preload1.setTaskDate("12/12/2222");

        Task preload2 = helper.generateTask(1);
        preload2.setTaskDate("12/12/2222");

        Task preload3 = helper.generateTask(2);
        preload3.setTaskDate("11/11/2222");

        model.addTask(preload1);
        model.addTask(preload2);
        model.addTask(preload3);

        UnmodifiableObservableList<ReadOnlyTask> testList = model.getPinnedItems("date", "12/12/2222");

        assertTrue(testList.size() == 2);

        model.deleteTask(preload1);
        model.deleteTask(preload2);
        model.deleteTask(preload3);
    }

    //@@author A0139772U
    @Test
    public void getPinnedItems_rubbishCondition_invalidCommandFeedback() throws Exception {
        assertCommandBehavior("pin rubbish", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
    }

    //@@author A0139772U
    @Test
    public void list_listTodoTask_todoTaskDisplayedCorrectly() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Task preload1 = helper.generateTask(0);
        preload1.setTaskDate(null);
        preload1.setTaskType("floating");

        Task preload2 = helper.generateTask(1);
        preload2.setTaskDate("12/12/2222");
        preload2.setStartTime("04:00pm");
        preload2.setEndTime("06:00pm");
        preload2.setTaskType("not_floating");

        model.addTask(preload1);
        model.addTask(preload2);

        UnmodifiableObservableList<ReadOnlyTask> todo = model.getFilteredTaskList();
        UnmodifiableObservableList<ReadOnlyTask> schedule = model.getFilteredScheduleList();

        assertTrue(todo.size() == 1);
        assertTrue(schedule.size() == 1);

        model.deleteTask(preload1);
        model.deleteTask(preload2);

    }

    //@@author A0139772U
    @Test
    public void getOverdueScheduleList_noOverdue_listSizeZero() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Task preload1 = helper.generateTask(0);
        preload1.setTaskDate("12/12/2222");

        UnmodifiableObservableList<ReadOnlyTask> overdue = model.getOverdueScheduleList();
        assertTrue(overdue.size() == 0);
    }

    //@@author A0139772U
    @Test
    public void addTask_duplicatedTag_exceptionThrown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task test = helper.generateTask(0);
        try {
            test.setTags(new UniqueTagList(new Tag("high"), new Tag("high")));
        } catch (DuplicateTagException e) {
            assertEquals(e.getMessage(), "Operation would result in duplicate tags");
        }
    }

    //@@author A0126240W
    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task grapes() throws IllegalValueException {
            Name name = new Name("Grapes Brown");
            String date = "12/12/2017";
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, date, null, null, null, null, null, null, null, tags, "incomplete", null);
        }

        Task todo(String description, String dateString, String tag01, String tag02) throws Exception {
            Name name = new Name(description);
            String date = dateString;
            Tag tag1 = new Tag(tag01);
            Tag tag2 = new Tag(tag02);
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, date, null, null, null, null, null, null, null, tags, "incomplete", null);
        }

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(new Name("Task " + seed), "23/02/2017", null, null, null, null, null, null, null,
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    "incomplete", null);
        }

        /**
         * Generates a valid completed task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         */
        Task generateCompletedTask(int seed) throws Exception {
            return new Task(new Name("Task " + seed), "23/02/2017", null, null, null, null, null, null, null,
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    "completed", null);
        }

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTaskForSelect(int seed) throws Exception {
            return new Task(new Name("Task " + seed), null, null, null, null, null, null, null, null,
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    "incomplete", null);
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append("\"" + p.getName().toString() + "\"");

            if (p.getTaskDate() != null)
                cmd.append(" on " + p.getTaskDate());

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates the correct update command based on the parameters given
         */
        String generateUpdateCommand(String type, String value) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("update schedule 1 ");

            if (("description").equals(type)) {
                cmd.append(type + " ");
                cmd.append(value);
            } else if (("date".equals(type))) {
                cmd.append(type + " ");
                cmd.append(value);
            } else if (("tag").equals(type)) {
                cmd.append(type + " ");
                cmd.append(value);
            }

            return cmd.toString();
        }

        /**
         * Generates an WhatNow with auto-generated tasks.
         */
        WhatNow generateWhatNow(int numGenerated) throws Exception {
            WhatNow whatNow = new WhatNow();
            addToWhatNow(whatNow, numGenerated);
            return whatNow;
        }

        /**
         * Generates an WhatNow with 1 completed task 
         */
        WhatNow generateModifiedWhatNow(int numGenerated) throws Exception {
            WhatNow whatNow = new WhatNow();
            addToWhatNow(whatNow, numGenerated);
            return whatNow;
        }

        /**
         * Generates an WhatNow based on the list of Tasks given.
         */
        WhatNow generateWhatNow(List<Task> tasks) throws Exception {
            WhatNow whatNow = new WhatNow();
            addToWhatNow(whatNow, tasks);
            return whatNow;
        }

        /**
         * Adds auto-generated Task objects to the given WhatNow
         * 
         * @param whatNow
         *            The WhatNow to which the Tasks will be added
         */
        void addToWhatNow(WhatNow whatNow, int numGenerated) throws Exception {
            addToWhatNow(whatNow, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given WhatNow
         */
        void addToWhatNow(WhatNow whatNow, List<Task> tasksToAdd) throws DuplicateTaskException {
            for (Task p : tasksToAdd) {
                whatNow.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * 
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                model.addTask(p);
            }
        }
        /**
         * Marks the given list of Tasks to the given WhatNow as done
         */
        void doneToWhatNow(WhatNow whatNow, int numGenerated) throws Exception {
            doneToWhatNow(whatNow, generateTaskList(numGenerated));
        }
        /**
         * Marks the given list of tasks to the given WhatNow as done
         */
        void doneToWhatNow(WhatNow whatNow, List<Task> list) throws Exception {
            for(Task p : list) {
                whatNow.markTask(p);
            }
        }
        /**
         * Adds auto-generated Task objects to the given model
         * Then marks them as completed
         */
        void doneToModel(Model model, int numGenerated) throws Exception {
            doneToModel(model, generateTaskList(numGenerated));
        }
        /**
         * Marks the model list of tasks to be done
         */
        void doneToModel(Model model, List<Task> tasksToDone) throws Exception {
            for(Task p : tasksToDone) {
                model.markTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        /**
         * Generates a list of completed Tasks based on the flags.
         */
        List<Task> generateCompletedTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateCompletedTask(i));
            }
            return tasks;
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskListForSelect(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTaskForSelect(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(new Name(name), null, null, null, null, null, null, null, null, new UniqueTagList(new Tag("tag")),
                    "incomplete", null);
        }
    }
}  