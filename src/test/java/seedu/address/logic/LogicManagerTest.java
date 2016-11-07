package seedu.address.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.*;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.ListUtil;
import seedu.address.history.UndoableCommandHistory;
import seedu.address.history.UndoableCommandHistoryManager;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.model.TaskManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.item.*;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.StorageStub;
import seedu.address.testutil.TestUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;
    private UndoableCommandHistory history;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedTaskManager = new TaskManager(abce.data);
    }


    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        history = UndoableCommandHistoryManager.getInstance();
        logic = new LogicManager(model, new StorageStub(), history);
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date before.
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
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskManager(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredUndoneTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }
    
    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehaviorOnAllData(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedUndoneList,
                                       List<? extends ReadOnlyTask> expectedDoneList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedUndoneList, model.getFilteredUndoneTaskList());
        assertEquals(expectedDoneList, model.getFilteredDoneTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }
    
    
    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
    }
    
    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    // @@author A0093960X
    @Test
    public void execute_clearInListUndone_clearsAllUndoneTaskOnly() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> undoneTasks = helper.generateFloatingTaskListRange(4, 6);
        model.addTasks(undoneTasks);

        List<Task> doneTasks = helper.generateFloatingTaskList(3);
        model.addDoneTasks(doneTasks);

        TaskManager expectedTM = helper.generateTaskManagerUndoneAndDoneList(Collections.emptyList(),
                doneTasks);

        assertCommandBehaviorOnAllData("clear", ClearCommand.MESSAGE_SUCCESS_UNDONE_LIST, expectedTM,
                expectedTM.getUndoneTaskList(), expectedTM.getDoneTaskList());
    }

    @Test
    public void execute_clearInListDone_clearsAllDoneTaskOnly() throws Exception {
        model.setCurrentListToBeDoneList();

        TestDataHelper helper = new TestDataHelper();
        List<Task> doneTasks = helper.generateFloatingTaskListRange(4, 6);
        model.addDoneTasks(doneTasks);

        List<Task> undoneTasks = helper.generateFloatingTaskList(3);
        model.addTasks(undoneTasks);

        TaskManager expectedTM = helper.generateTaskManagerUndoneAndDoneList(undoneTasks,
                Collections.emptyList());
        assertCommandBehaviorOnAllData("clear", ClearCommand.MESSAGE_SUCCESS_DONE_LIST, expectedTM,
                expectedTM.getUndoneTaskList(), expectedTM.getDoneTaskList());
    }

    @Test
    public void execute_undoWithNoPreviousUndoableCommand_nothingToUndo() throws Exception {
        // Force clear history
        while (!history.isEarliestCommand()) {
            history.undoStep();
        }
        assertCommandBehaviorOnAllData("undo", UndoCommand.MESSAGE_FAILURE, new TaskManager(),
                Collections.emptyList(), Collections.emptyList());
        assertCommandBehaviorOnAllData("undo 23", UndoCommand.MESSAGE_FAILURE, new TaskManager(),
                Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void execute_redoWithNoPreviousUndo_nothingToRedo() throws Exception {
        assertCommandBehaviorOnAllData("redo", RedoCommand.MESSAGE_FAILURE, new TaskManager(),
                Collections.emptyList(), Collections.emptyList());
        assertCommandBehaviorOnAllData("redo 123", RedoCommand.MESSAGE_FAILURE, new TaskManager(),
                Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void execute_undoAndRedoSingleAddCommand_undoAndRedoAdd() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task toAdd = helper.generateFloatingTask(1);

        List<Task> taskList = helper.generateTaskList(toAdd);
        logic.execute(helper.generateAddCommand(toAdd));

        TaskManager expectedTMAfterAdd = helper.generateTaskManagerUndoneAndDoneList(taskList,
                Collections.emptyList());

        assertCommandBehaviorOnAllData("undo", "Undid add item: " + toAdd.toString(), new TaskManager(),
                Collections.emptyList(), Collections.emptyList());
        assertCommandBehaviorOnAllData("redo", String.format(AddCommand.MESSAGE_SUCCESS, toAdd),
                expectedTMAfterAdd, expectedTMAfterAdd.getUndoneTaskList(),
                expectedTMAfterAdd.getDoneTaskList());
    }
    
    @Test
    public void execute_undoAndRedoSingleClearCommand_undoAndRedoClear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> undoneTasks = helper.generateFloatingTaskListRange(4, 6);
        model.addTasks(undoneTasks);

        List<Task> doneTasks = helper.generateFloatingTaskList(3);
        model.addDoneTasks(doneTasks);

        TaskManager expectedTMBeforeClear = helper.generateTaskManagerUndoneAndDoneList(undoneTasks,
                doneTasks);
        TaskManager expectedTMAfterClear = helper.generateTaskManagerUndoneAndDoneList(Collections.emptyList(),
                doneTasks);
        
        logic.execute("clear");
        assertCommandBehaviorOnAllData("undo", ClearCommand.MESSAGE_UNDO_SUCCESS_UNDONE_LIST, expectedTMBeforeClear,
                expectedTMBeforeClear.getUndoneTaskList(), expectedTMBeforeClear.getDoneTaskList());
        assertCommandBehaviorOnAllData("redo", ClearCommand.MESSAGE_SUCCESS_UNDONE_LIST,
                expectedTMAfterClear, expectedTMAfterClear.getUndoneTaskList(),
                expectedTMAfterClear.getDoneTaskList());
    }
    
    @Test
    public void execute_undoAndRedoSingleEditCommand_undoAndRedoAdd() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task toEdit = helper.generateFloatingTask(1);
        Task beforeEdit = new Task(toEdit);

        List<Task> taskListBeforeEdit = helper.generateTaskList(beforeEdit);
        model.addTask(toEdit);
        logic.execute(helper.generateEditCommand());
        
        //assign parameters for expectations
        Name name = new Name("Do stuff later");
        Date startDate = DateTime.convertStringToDate("10am");
        Date endDate = DateTime.convertStringToDate("12pm");
        Priority priority = Priority.HIGH;
        RecurrenceRate recurrenceRate = new RecurrenceRate("1","day");
        
        List<Task> taskList = helper.generateTaskList(toEdit);
        TaskManager expectedTMBeforeEdit = helper.generateTaskManagerUndoneAndDoneList(taskListBeforeEdit,
                Collections.emptyList());
        
        TaskManager expectedTMAfterEdit = helper.generateTaskManagerUndoneAndDoneList(taskList,
                Collections.emptyList());
        expectedTMAfterEdit.editFloatingTask(toEdit, name, startDate, endDate, priority, recurrenceRate);


        assertCommandBehaviorOnAllData("undo", String.format(EditCommand.MESSAGE_UNDO_SUCCESS, toEdit, beforeEdit), expectedTMBeforeEdit,
                expectedTMBeforeEdit.getUndoneTaskList(), expectedTMBeforeEdit.getDoneTaskList());
        
        expectedTMAfterEdit.editFloatingTask(toEdit, name, startDate, endDate, priority, recurrenceRate);
        assertCommandBehaviorOnAllData("redo", String.format(EditCommand.MESSAGE_SUCCESS, toEdit),
                expectedTMAfterEdit, expectedTMAfterEdit.getUndoneTaskList(),
                expectedTMAfterEdit.getDoneTaskList());
    }
    
    @Test
    public void execute_undoAndRedoSingleDeleteCommand_undoAndRedoDelete() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        List<Task> undoneTasks = helper.generateFloatingTaskList(1);
        model.addTasks(undoneTasks);

        TaskManager expectedTMBeforeDelete = helper.generateTaskManagerUndoneAndDoneList(undoneTasks,
                Collections.emptyList());

        TaskManager expectedTMAfterDelete = helper
                .generateTaskManagerUndoneAndDoneList(Collections.emptyList(), Collections.emptyList());

        logic.execute("delete 1");

        assertCommandBehaviorOnAllData("undo",
                String.format(DeleteCommand.MESSAGE_UNDO_SUCCESS, undoneTasks), expectedTMBeforeDelete,
                expectedTMBeforeDelete.getUndoneTaskList(), expectedTMBeforeDelete.getDoneTaskList());
        
        assertCommandBehaviorOnAllData("redo",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, ListUtil.generateDisplayString(undoneTasks)), expectedTMAfterDelete,
                expectedTMAfterDelete.getUndoneTaskList(), expectedTMAfterDelete.getDoneTaskList());
    }
    
    @Test
    public void execute_undoAndRedoSingleDoneCommand_undoAndRedoDone() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        List<Task> undoneTasks = helper.generateFloatingTaskList(1);
        model.addTasks(undoneTasks);

        TaskManager expectedTMBeforeDone = helper.generateTaskManagerUndoneAndDoneList(undoneTasks,
                Collections.emptyList());

        TaskManager expectedTMAfterDone = helper
                .generateTaskManagerUndoneAndDoneList(Collections.emptyList(), undoneTasks);

        logic.execute("done 1");

        assertCommandBehaviorOnAllData("undo",
                String.format(DoneCommand.MESSAGE_DONE_UNDO_SUCCESS, undoneTasks), expectedTMBeforeDone,
                expectedTMBeforeDone.getUndoneTaskList(), expectedTMBeforeDone.getDoneTaskList());
        
        assertCommandBehaviorOnAllData("redo",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, ListUtil.generateDisplayString(undoneTasks)), expectedTMAfterDone,
                expectedTMAfterDone.getUndoneTaskList(), expectedTMAfterDone.getDoneTaskList());
    }

    //@@author
    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM,
                expectedTM.getUndoneTaskList());

    }
    
    //@@author A0139552B
    @Test
    public void execute_edit_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.adam();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeEdited);
        
        // execute add command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeEdited),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeEdited),
                expectedTM,
                expectedTM.getUndoneTaskList());
        
        //assign parameters for expectations
        Name name = new Name("Do stuff later");
        Date startDate = DateTime.convertStringToDate("10am");
        Date endDate = DateTime.convertStringToDate("12pm");
        Priority priority = Priority.HIGH;
        RecurrenceRate recurrenceRate = new RecurrenceRate("1","day");
        expectedTM.editFloatingTask(toBeEdited, name, startDate, endDate, priority, recurrenceRate);

        // execute edit command and verify result
        assertCommandBehavior(helper.generateEditCommand(),
                String.format(EditCommand.MESSAGE_SUCCESS, toBeEdited),
                expectedTM,
                expectedTM.getUndoneTaskList());

    }
    
    @Test
    public void execute_edit_remove_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.read();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeEdited);
        
        // execute add command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeEdited),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeEdited),
                expectedTM,
                expectedTM.getUndoneTaskList());
        
        //assign parameters for expectations
        Name name = new Name("Read a book");
        Date startDate = null;
        Date endDate = null;
        Priority priority = Priority.MEDIUM;
        RecurrenceRate recurrenceRate = null;
        expectedTM.editFloatingTask(toBeEdited, name, startDate, endDate, priority, recurrenceRate);

        // execute edit command and verify result
        assertCommandBehavior(helper.generateEditCommandRemove(toBeEdited),
                String.format(EditCommand.MESSAGE_SUCCESS, toBeEdited),
                expectedTM,
                expectedTM.getUndoneTaskList());

    }

    //@@author A0139498J
    @Test
    public void execute_list_showsAllUndoneTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTM = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedTM.getUndoneTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedTM,
                expectedList);
    }

    @Test
    public void execute_listDone_showsAllDoneTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTM = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedUndoneList = expectedTM.getUndoneTaskList();
        List<? extends ReadOnlyTask> expectedDoneList = expectedTM.getDoneTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandBehaviorOnAllData("list done",
                ListCommand.DONE_MESSAGE_SUCCESS,
                expectedTM,
                expectedUndoneList, 
                expectedDoneList);
    }
    
    @Test
    public void execute_listTodayOnUndoneList_showsAllTasksTodayOnUndoneList() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasksWithTodayTask = helper.generateFloatingTaskList(3);    
        Task todayTask = helper.generateTaskWithTodayDate("todayTask");
        threeTasksWithTodayTask.add(todayTask);
        TaskManager expectedTM = helper.generateTaskManager(threeTasksWithTodayTask); 
        List<Task> expectedList = new ArrayList<Task>();
        expectedList.add(todayTask);
        
        // prepare task manager state
        helper.addToModel(model, threeTasksWithTodayTask);
        
        assertCommandBehavior("list today",
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, 1),
                expectedTM,
                expectedList);
    }
    
    //@@author
   


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> personList = helper.generateFloatingTaskList(2);
        Collections.sort(personList);

        // set AB state to 2 persons
        model.resetUndoneData(new TaskManager());
        for (Task p : personList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskManager(), personList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generateFloatingTaskList(3);
        Collections.sort(threePersons);

        TaskManager expectedTM = helper.generateTaskManager(threePersons);
        helper.addToModel(model, threePersons);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedTM,
                expectedTM.getUndoneTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredUndoneTaskList().get(1), threePersons.get(1));
    }
    
    //@@author A0139498J

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
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generateFloatingTaskList(3);
        Collections.sort(threePersons);

        TaskManager expectedTM = helper.generateTaskManager(threePersons);
        
        expectedTM.deleteUndoneTask(threePersons.get(1));
        helper.addToModel(model, threePersons);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, TestUtil.generateDisplayString(threePersons.get(1))),
                expectedTM,
                expectedTM.getUndoneTaskList());
    }

    @Test
    public void execute_doneInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }
    
    @Test
    public void execute_done_archivesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateFloatingTaskList(3);
        Collections.sort(threeTasks);
        
        TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        
        expectedTM.deleteUndoneTask(threeTasks.get(1));
        expectedTM.addDoneTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehaviorOnAllData("done 2",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, TestUtil.generateDisplayString(threeTasks.get(1))),
                expectedTM,
                expectedTM.getUndoneTaskList(),
                expectedTM.getDoneTaskList());       
    }
    
    @Test
    public void execute_doneMultipleIndexes_archivesCorrectTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateFloatingTaskList(3);
        Collections.sort(threeTasks);
        
        TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        
        expectedTM.deleteUndoneTask(threeTasks.get(0));
        expectedTM.addDoneTask(threeTasks.get(0));
        expectedTM.deleteUndoneTask(threeTasks.get(1));
        expectedTM.addDoneTask(threeTasks.get(1));
        expectedTM.deleteUndoneTask(threeTasks.get(2));
        expectedTM.addDoneTask(threeTasks.get(2));
        helper.addToModel(model, threeTasks);

        assertCommandBehaviorOnAllData("done 1 2 3",
                String.format(DoneCommand.MESSAGE_DONE_TASKS_SUCCESS, 
                        TestUtil.generateDisplayString(threeTasks.get(0), threeTasks.get(1), threeTasks.get(2))),
                expectedTM,
                expectedTM.getUndoneTaskList(),
                expectedTM.getDoneTaskList()); 
    }
    
    @Test
    public void execute_doneIndexWithRecurringTask_archivesTaskAndUpdatesRecurrence() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateFloatingTaskList(2);
        Task recurringTask = helper.generateTaskWithRecurrence("recurring", "day");
        threeTasks.add(recurringTask);
        Task updatedRecurringTask = new Task(recurringTask);

        updatedRecurringTask.updateRecurringTask();
        TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        
        expectedTM.deleteUndoneTask(recurringTask);
        expectedTM.addTask(updatedRecurringTask);
        expectedTM.addDoneTask(recurringTask);
        helper.addToModel(model, threeTasks);

        assertCommandBehaviorOnAllData("done 1",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, TestUtil.generateDisplayString(recurringTask)),
                expectedTM,
                expectedTM.getUndoneTaskList(),
                expectedTM.getDoneTaskList()); 
    }
    
    @Test
    public void execute_doneIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
    }
    
    @Test
    public void execute_store_changesStorageFilePath() throws Exception {
        // get folder path of the temporary folder
        String saveFolderPath = saveFolder.getRoot().toString();

        // get current user's storage location
        Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
        String currentStorageLocation = config.getTaskManagerFilePath();
        String currentStorageLocationWithoutExtension = 
                currentStorageLocation.substring(0, currentStorageLocation.lastIndexOf("."));
        
        // change storage location
        logic.execute("store " + saveFolderPath + "/test");
        config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
        String newStorageLocation = config.getTaskManagerFilePath();
        
        // revert storage location
        logic.execute("store " + currentStorageLocationWithoutExtension);
        
        // check if the storage location was changed earlier
        assertEquals(newStorageLocation, saveFolderPath + "/test.xml");
    }
    
    //@@author
    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateFloatingTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateFloatingTaskWithName("KE Y");
        Task p2 = helper.generateFloatingTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourPersons = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        Collections.sort(fourPersons);
        TaskManager expectedTM = helper.generateTaskManager(fourPersons);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        Collections.sort(expectedList);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateFloatingTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateFloatingTaskWithName("key key");
        Task p4 = helper.generateFloatingTaskWithName("KEy sduauo");

        List<Task> fourPersons = helper.generateTaskList(p3, p1, p4, p2);
        Collections.sort(fourPersons);
        TaskManager expectedTM = helper.generateTaskManager(fourPersons);
        List<Task> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateFloatingTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateFloatingTaskWithName("key key");
        Task p1 = helper.generateFloatingTaskWithName("sduauo");

        List<Task> fourPersons = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedTM = helper.generateTaskManager(fourPersons);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("Meet Adam Brown");
            Priority priority = Priority.LOW;
            return new Task(name, priority);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         */
        Task generateFloatingTask(int seed) throws Exception {
            Priority[] randomArr = {Priority.LOW, Priority.MEDIUM, Priority.HIGH};
            
            return new Task(
                    new Name("Task " + seed),
                    randomArr[seed%3]
            );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" -").append(p.getPriorityValue().toString().toLowerCase());

            return cmd.toString();
        }
        
        //@@author A0139552B
        /** Generates the correct edit command */
        String generateEditCommand() {
            StringBuffer cmd = new StringBuffer();

            cmd.append("edit 1 ");
            cmd.append("Do stuff later ");            
            cmd.append("from 10am ");
            cmd.append("to 12pm ");
            cmd.append("repeat every day ");
            cmd.append(" -").append("high");
            return cmd.toString();
        }
        
        Task read() throws Exception {
            Name name = new Name("Read a lot of books");
            Priority priority = Priority.HIGH;
            return new Task(name, priority);
        }
        
        /** Generates the correct edit command for removal */
        String generateEditCommandRemove(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("edit 1 ");
            cmd.append("Read a book ");     
            cmd.append("from 11am ");
            cmd.append("to 12pm ");
            cmd.append(" -").append("med ");
            cmd.append("-reset ");
            cmd.append("start ");
            cmd.append("end");
            return cmd.toString();
        }       
        //@@author
        
        /**
         * Generates an TaskManager with auto-generated persons.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        TaskManager generateTaskManager(List<Task> floatingTasks) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManagerUndoneList(taskManager, floatingTasks);
            return taskManager;
        }
        
        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        TaskManager generateTaskManagerUndoneAndDoneList(List<Task> undoneTasks, List<Task> doneTasks) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManagerUndoneList(taskManager, undoneTasks);
            addToTaskManagerDoneList(taskManager, doneTasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManagerUndoneList(taskManager, generateFloatingTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager undone list
         */
        void addToTaskManagerUndoneList(TaskManager addressBook, List<Task> floatingTasksToAdd) throws Exception{
            for(Task p: floatingTasksToAdd){
                addressBook.addTask(p);
            }
        }
        
        /**
         * Adds the given list of Tasks to the given TaskManager done list
         */
        void addToTaskManagerDoneList(TaskManager addressBook, List<Task> floatingTasksToAdd) throws Exception{
            for(Task p: floatingTasksToAdd){
                addressBook.addDoneTask(p);
            }
        }

        /**
         * Adds auto-generated Person objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateFloatingTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> floatingTasksToAdd) throws Exception{
            for(Task f: floatingTasksToAdd){
                model.addTask(f);
            }
        }

        /**
         * Generates a list of FloatingTask based on the flags.
         */
        List<Task> generateFloatingTaskList(int numGenerated) throws Exception{
            List<Task> floatingTasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                floatingTasks.add(generateFloatingTask(i));
            }
            return floatingTasks;
        }
        
        /**
         * Generates a list of FloatingTask based on the flags.
         */
        List<Task> generateFloatingTaskListRange(int numStart, int numEnd) throws Exception{
            List<Task> floatingTasks = new ArrayList<>();
            for(int i = numStart; i <= numEnd; i++){
                floatingTasks.add(generateFloatingTask(i));
            }
            return floatingTasks;
        }

        List<Task> generateTaskList(Task... floatingTasks) {
            return Arrays.asList(floatingTasks);
        }

        /**
         * Generates a FloatingTask object with given name. Other fields will have some dummy values.
         */
        Task generateFloatingTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    Priority.LOW
            );
        }
        
        /**
         * Generates a Task object with recurrence. Other fields will have some dummy values.
         */
        Task generateTaskWithRecurrence(String name, String recurrence) throws Exception {
            return new Task(
                    new Name(name),
                    new Date(),
                    new Date(),
                    new RecurrenceRate(recurrence),
                    Priority.LOW
            );
        }
        
        /**
         * Generates a Task object with current date. Other fields will have some dummy values.
         */
        Task generateTaskWithTodayDate(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Date(),
                    new Date(),
                    null,
                    Priority.LOW
            );
        }
        
    }
}
