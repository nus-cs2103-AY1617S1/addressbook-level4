package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.commons.events.ui.AgendaTimeRangeChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.logic.commands.AddNonFloatingCommand;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.logic.commands.ChangeDirectoryCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskMaster;
import seedu.address.model.TaskMaster;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;
import seedu.address.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskMaster latestSavedTaskList;
    private boolean helpShown;
    private int targetedJumpIndex;
    private TaskDate checkDate;
    private List<TaskComponent> checkList;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskListChangedEvent abce) {
        latestSavedTaskList = new TaskMaster(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }
    
    @Subscribe
    private void handleAgendaTimeRangeChangedEvent(AgendaTimeRangeChangedEvent ae){
    	checkDate = ae.getInputDate();
    	checkList = ae.getData();
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempTaskListFile = saveFolder.getRoot().getPath() + "TempTaskList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskList = new TaskMaster(model.getTaskMaster()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }
    
    

    @After
    public void teardown() throws DataConversionException, IOException {
    	Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
		config.setTaskListFilePath("data\\tasklist.xml");
		ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
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
     * Both the 'task list' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskMaster, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskMaster(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task list data are same as those in the {@code expectedTaskList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskMaster expectedTaskList,
                                       List<? extends TaskComponent> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        List<TaskComponent> componentList = model.getFilteredTaskComponentList();
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, componentList);

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskList, model.getTaskMaster());
        assertEquals(expectedTaskList, latestSavedTaskList);
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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskMaster(), Collections.emptyList());
    }
    


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFloatingCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add t/hihi", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;]", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddFloatingCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_add_successful_non_floating_from_date_to_date() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.nonFloatingFromDateToDate();
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_add_successful_non_floating_by_date() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.nonFloatingByDate();
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskComponentList());        
    }
    
    
    //@@author A0147967J
    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);
        
        // setup starting state
        model.addTask(toBeAdded); // task already in internal task list
       
        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddFloatingCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
     
    @Test
    public void execute_addOverlapSlot_allowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = new Task(new Name("Task one"), new UniqueTagList(),
        						  new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"), 
        						  RecurringType.NONE);
        Task toBeAddedAfter = new Task(new Name("Task two"), new UniqueTagList(),
				  new TaskDate("2 oct 10am"), new TaskDate("2 oct 11am"), 
				  RecurringType.NONE);
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);
        expectedAB.addTask(toBeAddedAfter);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task list

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAddedAfter),
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toBeAddedAfter),
                expectedAB,
                expectedAB.getTaskComponentList());

    }
    
    @Test
    public void execute_addDeadlineOverlap_Successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = new Task(new Name("Task one"), new UniqueTagList(),
        						  new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"),
        						  RecurringType.NONE);
        Task toBeAddedAfter = new Task(new Name("Task two"), new UniqueTagList(),
				  new TaskDate(TaskDate.DATE_NOT_PRESENT), new TaskDate("2 oct 11am"),
				  RecurringType.NONE);
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);
        expectedAB.addTask(toBeAddedAfter);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task list

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAddedAfter),
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toBeAddedAfter),
                expectedAB,
                expectedAB.getTaskComponentList());

    }
    
    @Test
    public void execute_addIllegalSlot_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = new Task(new Name("Task one"), new UniqueTagList(),
        						  new TaskDate("2 oct 6am"), new TaskDate("2 oct 5am"),
        						  RecurringType.NONE);
        TaskMaster expectedAB = new TaskMaster();

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddNonFloatingCommand.MESSAGE_ILLEGAL_TIME_SLOT,
                expectedAB,
                expectedAB.getTaskComponentList());

    }
    //@@author
    
    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskMaster expectedAB = helper.generateTaskList(2);
        List<? extends TaskComponent> expectedList = expectedAB.getTaskComponentList();

        // prepare task list state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }
    
    //@@author A0147967J    
    /**
     * The logic for block command is actually the same as add-non=floating commands.
     * */     
    @Test
    public void execute_block_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = new Task(new Name(BlockCommand.DUMMY_NAME), new UniqueTagList(),
				  new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"), 
				  RecurringType.NONE);
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateBlockCommand(toBeAdded),
                String.format(BlockCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskComponentList());

    }
    
    @Test
    public void execute_blockOverlapSlot_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeBlocked = new Task(new Name(BlockCommand.DUMMY_NAME), new UniqueTagList(),
        						  new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"), 
        						  RecurringType.NONE);
        Task toBeAddedAfter = new Task(new Name(BlockCommand.DUMMY_NAME), new UniqueTagList(),
				  new TaskDate("2 oct 10am"), new TaskDate("2 oct 11am"),
				  RecurringType.NONE);
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeBlocked);

        // setup starting state
        model.addTask(toBeBlocked); // task already in internal task list

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAddedAfter),
                BlockCommand.MESSAGE_TIMESLOT_OCCUPIED,
                expectedAB,
                expectedAB.getTaskComponentList());

    }
    
    @Test
    public void execute_blockOverlapWithExistingTask_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeBlocked = new Task(new Name("Test Task"), new UniqueTagList(),
        						  new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"), 
        						  RecurringType.NONE);
        Task toBeAddedAfter = new Task(new Name(BlockCommand.DUMMY_NAME), new UniqueTagList(),
				  new TaskDate("2 oct 10am"), new TaskDate("2 oct 11am"),
				  RecurringType.NONE);
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeBlocked);

        // setup starting state
        model.addTask(toBeBlocked); // task already in internal task list

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAddedAfter),
                BlockCommand.MESSAGE_TIMESLOT_OCCUPIED,
                expectedAB,
                expectedAB.getTaskComponentList());

    }
    
    @Test
    public void execute_blockIllegalSlot_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeBlocked = new Task(new Name(BlockCommand.DUMMY_NAME), new UniqueTagList(),
        						  new TaskDate("2 oct 6am"), new TaskDate("2 oct 5am"),
        						  RecurringType.NONE);
        TaskMaster expectedAB = new TaskMaster();

        // execute command and verify result
        assertCommandBehavior(
                helper.generateBlockCommand(toBeBlocked),
                BlockCommand.MESSAGE_ILLEGAL_TIME_SLOT,
                expectedAB,
                expectedAB.getTaskComponentList());

    }
    
    /**
     * Tests for undo/redo commands.
     */   
    @Test
    public void execute_undoredoNothing_notAllowed() throws Exception {
    	// setup expectations
        TaskMaster expectedAB = new TaskMaster();

        // execute command and verify result
        assertCommandBehavior(
                "u",
                UndoCommand.MESSAGE_FAIL,
                expectedAB,
                expectedAB.getTaskComponentList());
        
        assertCommandBehavior(
                "r",
                RedoCommand.MESSAGE_FAIL,
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
      
    @Test
    public void execute_undoredo_Successful() throws Exception {
    	// setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddFloatingCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskComponentList());
        
        expectedAB = new TaskMaster();
        assertCommandBehavior("u",
                UndoCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedAB.getTaskComponentList());
        
        expectedAB.addTask(toBeAdded);
        assertCommandBehavior(
                "r",
                String.format(AddFloatingCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskComponentList());        
    }
    
    @Test
    public void execute_undoredoReachMaxTimes_notAllowed() throws Exception{
    	TestDataHelper helper = new TestDataHelper();
    	TaskMaster expectedAB = new TaskMaster();
    	for(int i = 1; i < 5; i++){
    		Task t = helper.generateTask(i);
    		logic.execute(helper.generateAddCommand(t));
    	}
    	for(int i = 0; i < 3; i++)
    		logic.execute("u");
    	
    	expectedAB.addTask(helper.generateTask(1));
    	assertCommandBehavior(
                "u",
                UndoCommand.MESSAGE_FAIL,
                expectedAB,
                expectedAB.getTaskComponentList());
    	
    	for(int i = 0; i < 3; i++){
    		logic.execute("r");
    		expectedAB.addTask(helper.generateTask(2+i));
    	}
    	
    	assertCommandBehavior(
                "r",
                RedoCommand.MESSAGE_FAIL,
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_undoInvalidCommand_notAllowed() throws Exception{
    	
    	TaskMaster expectedAB = new TaskMaster();
    	logic.execute("adds t");
    	assertCommandBehavior(
                "u",
                UndoCommand.MESSAGE_FAIL,
                expectedAB,
                expectedAB.getTaskComponentList());
    	
    }
    
    @Test
    public void execute_undoFailedCommand_notAllowed() throws Exception{
    	
    	TestDataHelper helper = new TestDataHelper();
    	TaskMaster expectedAB = new TaskMaster();
    	Task toBeAdded = helper.adam();
    	
    	expectedAB.addTask(toBeAdded);
    	model.addTask(toBeAdded);
    	
    	logic.execute(helper.generateAddCommand(toBeAdded));
    	
    	assertCommandBehavior(
                "u",
                UndoCommand.MESSAGE_FAIL,
                expectedAB,
                expectedAB.getTaskComponentList());
    	
    }
    
    
    /***
     * Tests for ChangeDirectoryCommand
     */
    @Test
    public void execute_changeDirectoryIllegalDirectory_notAllowed() throws Exception{
    	
    	TaskMaster expectedAB = new TaskMaster();
    	assertCommandBehavior(
    			"cd random path",
                ChangeDirectoryCommand.MESSAGE_CONVENSION_ERROR,
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_changeDirectoryWrongFileType_notAllowed() throws Exception{
    	
    	TaskMaster expectedAB = new TaskMaster();
    	assertCommandBehavior(
    			"cd "+saveFolder.getRoot().getPath()+"cdtest.txt",
                ChangeDirectoryCommand.MESSAGE_CONVENSION_ERROR,
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_changeDirectory_Successful() throws Exception{
    	
    	TestDataHelper helper = new TestDataHelper();
    	TaskMaster expectedAB = new TaskMaster();
    	assertCommandBehavior(
    			"cd "+ saveFolder.getRoot().getPath()+"cdtest.xml",
    			String.format(ChangeDirectoryCommand.MESSAGE_SUCCESS, saveFolder.getRoot().getPath()+"cdtest.xml"),
                expectedAB,
                expectedAB.getTaskComponentList());
    	
    	//Ensure model writes to this file
    	expectedAB.addTask(helper.adam());
    	logic.execute(helper.generateAddCommand(helper.adam()));
        ReadOnlyTaskMaster retrieved = new StorageManager(saveFolder.getRoot().getPath()+"cdtest.xml", 
        		                                        saveFolder.getRoot().getPath() + "TempPreferences.json").readTaskList().get();
        assertEquals(expectedAB, new TaskMaster(retrieved));
        assertEquals(model.getTaskMaster(), new TaskMaster(retrieved));
        
    }
    //@@author

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
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
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTasks(2);
        List<TaskComponent> taskComponentList = helper.buildTaskComponentsFromTaskList(taskList);

        // set AB state to 2 tasks
        model.resetData(new TaskMaster());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskMaster(), taskComponentList);
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
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTasks(3);

        TaskMaster expectedAB = helper.generateTaskList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskComponentList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getTaskList().get(1), threeTasks.get(1));
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
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTasks(3);

        TaskMaster expectedAB = helper.generateTaskList(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    //@@author A0147967J
    @Test
    public void execute_completeInvalidArgsFormat_errorMessageShown() throws Exception {
    	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE);
    	assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }
    
    @Test
    public void execute_completeIndexNotFound_errorMessageShown() throws Exception {
    	assertIndexNotFoundBehaviorForCommand("done");
    }
    
    @Test
    public void execute_complete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Task toComplete = helper.adam();
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toComplete);
        model.addTask(toComplete);

        assertCommandBehavior("done 1", 
        		String.format(CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS, toComplete),
        	    expectedAB,
        	    new TaskMaster().getTaskComponentList());

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
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTasks(p1, pTarget1, p2, pTarget2);
        TaskMaster expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTasks(pTarget1, pTarget2);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedList);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedComponentList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTasks(p3, p1, p4, p2);
        TaskMaster expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = fourTasks;
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedList);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedComponentList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTasks(pTarget1, p1, pTarget2, pTarget3);
        TaskMaster expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTasks(pTarget1, pTarget2, pTarget3);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedList);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedComponentList);
    }
    
    //@@author A0147967J
    @Test
    public void execute_findByDateTimeBoundary() throws Exception{
    	TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");
        Task test = helper.nonFloatingByDate();
        Task test2 = helper.nonFloatingFromDateToDate();

        List<Task> fourTasks = helper.generateTasks(pTarget1, p1, pTarget2, pTarget3);
        TaskMaster expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTasks(test);
        
        expectedAB.addTask(test);
        expectedAB.addTask(test2);
        helper.addToModel(model, fourTasks);
        model.addTask(test);
        model.addTask(test2);
        
        List<TaskComponent> componentList = helper.buildTaskComponentsFromTaskList(expectedList);
        
        //find by exact time successful
        assertCommandBehavior("find by 20 oct 11am",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                componentList);
        //find by earlier time boundary lists nothing
        assertCommandBehavior("find by 20 oct 10.59am",
                Command.getMessageForTaskListShownSummary(0),
                expectedAB,
                new TaskMaster().getTaskComponentList());
        //find by later time boundary successful
        assertCommandBehavior("find by 20 oct 11.01pm",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                componentList);
    }
    
    @Test
    public void execute_findFromDateBoundaryToDateBoundary() throws Exception{
    	TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");
        Task test = helper.nonFloatingFromDateToDate();
        Task test2 = helper.nonFloatingByDate();

        List<Task> fourTasks = helper.generateTasks(pTarget1, p1, pTarget2, pTarget3);
        TaskMaster expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTasks(test);
        
        expectedAB.addTask(test);
        expectedAB.addTask(test2);

        helper.addToModel(model, fourTasks);
        model.addTask(test);
        model.addTask(test2);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedList);
        
        //find by exact boundary successful
        assertCommandBehavior("find from 19 oct 10pm to 20 oct 11am",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedComponentList);
        //find by smaller boundary lists nothing
        assertCommandBehavior("find from 19 oct 10.01pm to 20 oct 11am",
                Command.getMessageForTaskListShownSummary(0),
                expectedAB,
                new TaskMaster().getTaskComponentList());
        
        assertCommandBehavior("find from 19 oct 10pm to 20 oct 10.59am",
                Command.getMessageForTaskListShownSummary(0),
                expectedAB,
                new TaskMaster().getTaskComponentList());
        //find by lax boundary successful
        assertCommandBehavior("find from 19 oct 9pm to 20 oct 1pm",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedComponentList);
    }
    
    @Test
    public void execute_findFloatingTasksbyType_Successful() throws Exception{
    	TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");
        Task test = helper.nonFloatingFromDateToDate();

        List<Task> threeTasks = helper.generateTasks(pTarget1, pTarget2, pTarget3);
        TaskMaster expectedAB = helper.generateTaskList(threeTasks);
        List<Task> expectedList = helper.generateTasks(pTarget1, pTarget2, pTarget3);
        
        expectedAB.addTask(test);
        expectedAB.addTask(p1);

        helper.addToModel(model, threeTasks);
        model.addTask(test);
        model.addTask(p1);
        logic.execute("done 5");
        
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedList);

        assertCommandBehavior("find -F",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedComponentList);
        
    }
    
    @Test
    public void execute_findCompletedTasksbyType_Successful() throws Exception{
    	TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task test = helper.nonFloatingFromDateToDate();

        TaskMaster expectedAB = new TaskMaster();
        List<Task> expectedList = helper.generateTasks(test);
        
        expectedAB.addTask(pTarget1);
        expectedAB.addTask(test);

        model.addTask(pTarget1);
        model.addTask(test);
        
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedList);
        
        logic.execute("done 2");
        
        assertCommandBehavior("find -C",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedComponentList);
        
    }
    
    @Test
    public void execute_findbyTag_Successful() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task test = helper.nonFloatingFromDateToDate();

        TaskMaster expectedAB = new TaskMaster();
        List<Task> expectedList = helper.generateTasks(test);
        
        expectedAB.addTask(pTarget1);
        expectedAB.addTask(test);

        model.addTask(pTarget1);
        model.addTask(test);
        
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedList);
        
        assertCommandBehavior("find t/tag1",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedComponentList);
        
    }
    
    /**
     * Tests for view command. 
     */
    @Test
    public void execute_view_InvalidInputDate_notAllowed() throws Exception{
    	String expectedMessage = Messages.MESSAGE_ILLEGAL_DATE_INPUT;
    	assertCommandBehavior("view random input", expectedMessage);
    }
    
    @Test
    public void execute_view_successful() throws Exception {
    	String test = "23 oct 12am";
    	TaskDate testDate = new TaskDate(test);
    	assertCommandBehavior("view 23 oct 12am",
    			String.format(ViewCommand.MESSAGE_UPDATE_AGENDA_SUCCESS, testDate.getFormattedDate()));
    	assertEquals(testDate, checkDate);
    	assertEquals(latestSavedTaskList.getTaskComponentList(), checkList);
    	assertEquals(model.getTaskMaster().getTaskComponentList(), checkList);
    }
    //@@author
    
    @Test
    public void execute_add_recurringTask_byDate_unsuccessful_addAsNonFloatingTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskMaster expectedTM = new TaskMaster();
        Task toAdd = helper.nonFloatingByDate();
        expectedTM.addTask(toAdd);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedTM.getTasks());
        assertCommandBehavior("add non floating task by XXXX by 20 oct 11am dai t/tag1 t/tag2", 
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toAdd),
                expectedTM,
                expectedComponentList);
    }
    
    @Test
    public void execute_add_recurringTask_byDate_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskMaster expectedTM = new TaskMaster();
        Task toAdd = helper.nonFloatingRecurringByDate(RecurringType.DAILY);
        expectedTM.addTask(toAdd);
        RecurringTaskManager.getInstance().correctAddingOverdueTasks(toAdd);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedTM.getTasks());
        assertCommandBehavior("add non floating task by XXXX by 20 oct 11am daily t/tag1 t/tag2",
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toAdd),
                expectedTM,
                expectedComponentList);
    }
    
    @Test
    public void execute_add_recurringTask_daily_ByDate_daily_caseInsensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskMaster expectedTM = new TaskMaster();
        Task toAdd = helper.nonFloatingRecurringByDate(RecurringType.DAILY);
        expectedTM.addTask(toAdd);
        RecurringTaskManager.getInstance().correctAddingOverdueTasks(toAdd);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedTM.getTasks());
        assertCommandBehavior("add non floating task by XXXX by 20 oct 11am dAIly t/tag1 t/tag2",
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toAdd),
                expectedTM,
                expectedComponentList);
    }    
    
    @Test
    public void execute_add_recurringTask_FromDateToDate_unsuccessful_addAsNonFloatingTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskMaster expectedTM = new TaskMaster();
        Task toAdd = helper.nonFloatingFromDateToDate();
        expectedTM.addTask(toAdd);
        RecurringTaskManager.getInstance().correctAddingOverdueTasks(toAdd);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedTM.getTasks());
        assertCommandBehavior("add non floating task from XXXX to XXXX from 19 oct 10pm to 20 oct 11am dai t/tag1 t/tag2", 
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toAdd),
                expectedTM,
                expectedComponentList);
    }
    
    @Test
    public void execute_add_recurringTask_FromDateToDate_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskMaster expectedTM = new TaskMaster();
        Task toAdd = helper.nonFloatingRecurringFromDateToDate(RecurringType.DAILY);
        expectedTM.addTask(toAdd);
        RecurringTaskManager.getInstance().correctAddingOverdueTasks(toAdd);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedTM.getTasks());
        assertCommandBehavior("add non floating task from XXXX to XXXX from 19 oct 10pm to 20 oct 11am daily t/tag1 t/tag2",
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toAdd),
                expectedTM,
                expectedComponentList);
    }
    
    @Test
    public void execute_add_recurringTask_daily_FromDateToDate_daily_caseInsensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskMaster expectedTM = new TaskMaster();
        Task toAdd = helper.nonFloatingRecurringFromDateToDate(RecurringType.DAILY);
        expectedTM.addTask(toAdd);
        RecurringTaskManager.getInstance().correctAddingOverdueTasks(toAdd);
        List<TaskComponent> expectedComponentList = helper.buildTaskComponentsFromTaskList(expectedTM.getTasks());
        assertCommandBehavior("add non floating task from XXXX to XXXX from 19 oct 10pm to 20 oct 11am dAIly t/tag1 t/tag2",
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toAdd),
                expectedTM,
                expectedComponentList);
    }
    
    /** tests for edit command*/   
    @Test
    public void execute_edit_invalidTaskData() throws Exception {
        Task toBeAdded = new Task(new Name("anything"), new UniqueTagList(),
        						  new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"), RecurringType.NONE);
       
        TaskMaster expectedAB = new TaskMaster();
        expectedAB.addTask(toBeAdded);
    	model.addTask(toBeAdded);
        assertCommandBehavior(
                "edit 1 []\\[;]", Name.MESSAGE_NAME_CONSTRAINTS, expectedAB, expectedAB.getTaskComponentList());
        assertCommandBehavior(
        		"edit 1 t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS, expectedAB, expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_edit_fromDateIsBehindToDate_notAllowed() throws Exception {
        // setup expectations
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList());
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
		expectedAB.addTask(beforeModification);

        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 from 2 oct 1pm to 2 oct 1am",
                String.format(EditCommand.MESSAGE_ILLEGAL_TIME_SLOT),
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_edit_timeSlotOccupied_notAllowed() throws Exception {
        // setup expectations
    	Task dummyTask = new Task(new Name("dummy"), new UniqueTagList(),
									new TaskDate("10 oct 2pm"), new TaskDate("10 oct 5pm"), RecurringType.NONE);
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList(),
    										new TaskDate("10 oct 10am"), new TaskDate("10 oct 12am"), RecurringType.NONE);
		
    	model.addTask(dummyTask);
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
    	expectedAB.addTask(dummyTask);
		expectedAB.addTask(beforeModification);

        // execute command and verify result
        assertCommandBehavior(
        		"edit 2 from 10 oct 1pm to 10 oct 6pm",
                String.format(EditCommand.MESSAGE_TIMESLOT_OCCUPIED),
                expectedAB,
                expectedAB.getTaskComponentList());
    }
  
    @Test
    public void execute_edit_name_for_task_Successful() throws Exception {
        // setup expectations
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList());
    	Task afterModification = new Task(new Name("changed"), new UniqueTagList());
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
		expectedAB.addTask(afterModification);

        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 changed",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterModification),
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_edit_tag_for_taskWithoutTag_Successful() throws Exception {
        // setup expectations
    	Set<Tag> tagSet = new HashSet<Tag>();
    	tagSet.add(new Tag("anytag"));
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList());
    	Task afterModification = new Task(new Name("anything"), new UniqueTagList(tagSet));
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
		expectedAB.addTask(afterModification);

        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 t/anytag",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterModification),
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_edit_tag_for_taskWithTag_Successful() throws Exception {
        // setup expectations
    	Set<Tag> tagSet = new HashSet<Tag>();
    	Set<Tag> newTagSet = new HashSet<Tag>();
    	tagSet.add(new Tag("anytag"));
    	newTagSet.add(new Tag("anothertag"));
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList(tagSet));
    	Task afterModification = new Task(new Name("anything"), new UniqueTagList(newTagSet));
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
    	expectedAB.getUniqueTagList().add(new Tag("anytag"));
		expectedAB.addTask(afterModification);

        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 t/anothertag",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterModification),
                expectedAB,
                expectedAB.getTaskComponentList());
    }
    
    @Test
    public void execute_edit_change_fromDateToDate_for_nonFloatingTask_Successful() throws Exception {
        // setup expectations
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList(), new TaskDate("2 oct 3am"), new TaskDate("2 oct 1pm"), RecurringType.NONE);
    	Task afterModification = new Task(new Name("anything"), new UniqueTagList(), new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"), RecurringType.NONE);
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
		expectedAB.addTask(afterModification);

		TestDataHelper helper = new TestDataHelper();
        List<TaskComponent> expectedComponentList = helper.buildReadOnlyTaskComponentsFromTaskList(expectedAB.getTaskList());
        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 from 2 oct 2am to 2 oct 1pm",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterModification),
                expectedAB,
                expectedComponentList);
    }
    
    @Test
    public void execute_edit_change_byDate_for_nonfloatingTask_Successful() throws Exception {
        // setup expectations
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList(), new TaskDate(TaskDate.DATE_NOT_PRESENT), new TaskDate("2 oct 2pm"), RecurringType.NONE);
    	Task afterModification = new Task(new Name("anything"), new UniqueTagList(), new TaskDate(TaskDate.DATE_NOT_PRESENT), new TaskDate("2 oct 1pm"), RecurringType.NONE);
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
		expectedAB.addTask(afterModification);
        TestDataHelper helper = new TestDataHelper();
        List<TaskComponent> expectedComponentList = helper.buildReadOnlyTaskComponentsFromTaskList(expectedAB.getTaskList());
        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 by 2 oct 1pm",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterModification),
                expectedAB,
                expectedComponentList);
    }
    
    @Test
    public void execute_edit_switch_between_byDate_and_fromDateToDate_for_nonFloatingTask_Successful() throws Exception {
        // setup expectations
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList(), new TaskDate("2 oct 4am"), new TaskDate("2 oct 1pm"), RecurringType.NONE);
    	Task afterModification = new Task(new Name("anything"), new UniqueTagList(), new TaskDate(TaskDate.DATE_NOT_PRESENT), new TaskDate("2 oct 1pm"), RecurringType.NONE);
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
		expectedAB.addTask(afterModification);
        TestDataHelper helper = new TestDataHelper();
        List<TaskComponent> expectedComponentList = helper.buildReadOnlyTaskComponentsFromTaskList(expectedAB.getTaskList());
        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 by 2 oct 1pm",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterModification),
                expectedAB,
                expectedComponentList);
    }
    
    @Test
    public void execute_edit_add_fromDateToDate_for_floatingTask_Successful() throws Exception {
        // setup expectations
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList());
    	Task afterModification = new Task(new Name("anything"), new UniqueTagList(), new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"), RecurringType.NONE);
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
		expectedAB.addTask(afterModification);

	    TestDataHelper helper = new TestDataHelper();
	    List<TaskComponent> expectedComponentList = helper.buildReadOnlyTaskComponentsFromTaskList(expectedAB.getTaskList());
        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 from 2 oct 2am to 2 oct 1pm",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterModification),
                expectedAB,
                expectedComponentList);
    }
    
    @Test
    public void execute_edit_add_byDate_for_floatingTask_Successful() throws Exception {
        // setup expectations
    	Task beforeModification = new Task(new Name("anything"), new UniqueTagList());
    	Task afterModification = new Task(new Name("anything"), new UniqueTagList(), new TaskDate(TaskDate.DATE_NOT_PRESENT), new TaskDate("2 oct 1pm"), RecurringType.NONE);
		
    	model.addTask(beforeModification);
    	TaskMaster expectedAB = new TaskMaster();
		expectedAB.addTask(afterModification);
        TestDataHelper helper = new TestDataHelper();
        List<TaskComponent> expectedComponentList = helper.buildReadOnlyTaskComponentsFromTaskList(expectedAB.getTaskList());
        // execute command and verify result
        assertCommandBehavior(
        		"edit 1 by 2 oct 1pm",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, afterModification),
                expectedAB,
                expectedComponentList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("go shopping with Adam Brown");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, tags);
        }
        
        public List<TaskComponent> buildReadOnlyTaskComponentsFromTaskList(List<ReadOnlyTask> taskList) {
            List<TaskComponent> dateComponentList = new ArrayList<TaskComponent>();
            for(ReadOnlyTask t : taskList) {
                dateComponentList.addAll(t.getTaskDateComponent());
            }
            return dateComponentList;
        }

        public List<TaskComponent> buildTaskComponentsFromTaskList(List<Task> taskList) {
            List<TaskComponent> dateComponentList = new ArrayList<TaskComponent>();
            for(Task t : taskList) {
                dateComponentList.addAll(t.getTaskDateComponent());
            }
            return dateComponentList;
        }

        public Task nonFloatingFromDateToDate() throws Exception {
            Name name = new Name("non floating task from XXXX to XXXX");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            TaskDate startDate = new TaskDate("19 oct 10pm");
            TaskDate endDate = new TaskDate("20 oct 11am");
            return new Task(name, tags, startDate, endDate, RecurringType.NONE);
        }
        
        public Task nonFloatingRecurringFromDateToDate(RecurringType recurringType) throws Exception {
            Task nonFloatingRecurringTask = nonFloatingFromDateToDate();
            nonFloatingRecurringTask.setRecurringType(recurringType);
            return nonFloatingRecurringTask;
        }

        public Task nonFloatingByDate() throws Exception {
            Name name = new Name(" non floating task by XXXX");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            TaskDate startDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
            TaskDate endDate = new TaskDate("20 oct 11am");
            return new Task(name, tags, startDate, endDate, RecurringType.NONE);
        }
        
        public Task nonFloatingRecurringByDate(RecurringType recurringType) throws Exception {
            Task nonFloatingRecurringTask = nonFloatingByDate();
            nonFloatingRecurringTask.setRecurringType(recurringType);
            return nonFloatingRecurringTask;
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
                    new Name("Task " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();
            cmd.append("add ");
            cmd.append(p.getName().toString());
            if(p.getTaskType().equals(TaskType.NON_FLOATING)){
                generateAddNonFloatingCommand(p, cmd);
            }
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }
            return cmd.toString();
        }
        
        /** Generates the correct block command based on the task given */
        String generateBlockCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("block ");
            
            generateCommandComponentFromDateToDate(p, cmd);

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }
            System.out.println(cmd.toString());
            return cmd.toString();
        }

        private void generateAddNonFloatingCommand(Task p, StringBuffer cmd) {
            assert p.getRecurringType() == RecurringType.NONE : "generatingAddNonFloatingCommand does not support recurring tasks";
            if (p.getComponentForNonRecurringType().hasOnlyEndDate()) {
                generateAddNonFloatingCommandByDate(p, cmd);
            } else {
                generateCommandComponentFromDateToDate(p, cmd);   
            }
        }

        private void generateCommandComponentFromDateToDate(Task p, StringBuffer cmd) {
            assert p.getRecurringType() == RecurringType.NONE : "generatingCommandComponentFromDatetoDate does not support recurring tasks";
            cmd.append(" from ");
            cmd.append(p.getComponentForNonRecurringType().getStartDate().getInputDate());
            cmd.append(" to ");
            cmd.append(p.getComponentForNonRecurringType().getEndDate().getInputDate());
        }

        private void generateAddNonFloatingCommandByDate(Task p, StringBuffer cmd) {
            cmd.append(" by ").append(p.getComponentForNonRecurringType().getEndDate().getInputDate());
        }
        
        

        /**
         * Generates an TaskList with auto-generated tasks.
         */
        TaskMaster generateTaskList(int numGenerated) throws Exception{
            TaskMaster taskList = new TaskMaster();
            addToTaskList(taskList, numGenerated);
            return taskList;
        }

        /**
         * Generates an TaskList based on the list of Tasks given.
         */
        TaskMaster generateTaskList(List<Task> tasks) throws Exception{
            TaskMaster taskList = new TaskMaster();
            addToTaskList(taskList, tasks);
            return taskList;
        }

        /**
         * Adds auto-generated Task objects to the given TaskList
         * @param taskList The TaskList to which the Tasks will be added
         */
        void addToTaskList(TaskMaster taskList, int numGenerated) throws Exception{
            addToTaskList(taskList, generateTasks(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskList
         */
        void addToTaskList(TaskMaster taskList, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskList.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTasks(numGenerated));
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
        List<Task> generateTasks(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTasks(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
