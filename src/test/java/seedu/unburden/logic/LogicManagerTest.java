package seedu.unburden.logic;

import com.google.common.eventbus.Subscribe;

import seedu.unburden.commons.core.EventsCenter;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.events.model.ListOfTaskChangedEvent;
import seedu.unburden.commons.events.ui.JumpToListRequestEvent;
import seedu.unburden.commons.events.ui.ShowHelpRequestEvent;
import seedu.unburden.logic.Logic;
import seedu.unburden.logic.LogicManager;
import seedu.unburden.logic.commands.*;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.Model;
import seedu.unburden.model.ModelManager;
import seedu.unburden.model.ReadOnlyListOfTask;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.*;
import seedu.unburden.storage.StorageManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.unburden.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyListOfTask latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(ListOfTaskChangedEvent abce) {
        latestSavedAddressBook = new ListOfTask(abce.data);
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
        String tempTaskListFile = saveFolder.getRoot().getPath() + "TempTaskList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new ListOfTask(model.getListOfTask()); // last saved assumed to be up to date before.
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
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyListOfTask, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new ListOfTask(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI max5ches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyListOfTask expectedAddressBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getListOfTask());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.HELP_MESSAGE_HELP);
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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new ListOfTask(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add Valid Name 12-12-2010 s/2300 e/2359", expectedMessage);
        assertCommandBehavior(
                "add Valid Name d/12-12-2010 s/2300 2359", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
    	//TODO : add test case to check if start time later than end time
        assertCommandBehavior(
                "add []\\[;] i/Valid Task Description d/12-12-2016 s/2300 e/2359", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
        		"add Valid Name i/[]\\[;] d/12-12-2016 s/2300 e/2359", TaskDescription.MESSAGE_TASK_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name i/Valid Task Description d/12-12-2010 s/2300 e/2359", Date.MESSAGE_DATE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name i/Valid Task Description d/12-12-2016 s/2300 e/2400", Time.MESSAGE_TIME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name i/Valid Task Description d/12-12-2016 s/2400 e/2359", Time.MESSAGE_TIME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name i/Valid Task Description d/12-12-2010 s/2300 e/2359 t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
    }
    
	//@@author A0139678J
	@Test
	public void execute_add_deadline() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task t1 = helper.generateDeadlineTask("Hi hi", "bye bye", "11-10-2016", "bored");
		Task t2 = helper.generateDeadlineTask("Hello", "Hello1", "12-12-2016", "Words");
		ListOfTask expectedAB = new ListOfTask();
		expectedAB.addTask(t1);
		expectedAB.addTask(t2);

		assertCommandBehavior(helper.generateAddCommand(t2), String.format(AddCommand.MESSAGE_SUCCESS, t2), expectedAB,
				expectedAB.getTaskList());

	}

	//@@author A0139678J
	@Test
	public void execute_add_floatingTask() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task t1 = helper.generateFloatingTask("I'm so tired", "I haven't sleep", "sleep");
		Task t2 = helper.generateFloatingTask("I need to study", "I want to find a job", "finals");
		ListOfTask expectedAB = new ListOfTask();
		expectedAB.addTask(t1);
		expectedAB.addTask(t2);

		assertCommandBehavior(helper.generateAddCommand(t2), String.format(AddCommand.MESSAGE_SUCCESS, t2), expectedAB,
				expectedAB.getTaskList());
	}


    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        ListOfTask expectedAB = new ListOfTask();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        ListOfTask expectedAB = new ListOfTask();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }


    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        ListOfTask expectedAB = helper.generateListOfTask(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list all",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


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
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 persons
        model.resetData(new ListOfTask());
        for (Task p : taskList) {
            model.addTask(p);
        }
        
        if (commandWord.equals(EditCommand.COMMAND_WORD)) {
        	assertCommandBehavior(commandWord + " 3 e/2359", expectedMessage, model.getListOfTask(), taskList);
        }
        else {
        	assertCommandBehavior(commandWord + " 3", expectedMessage, model.getListOfTask(), taskList);
        }
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
        List<Task> threeTasks = helper.generateTaskList(3);

        ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
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
    public void execute_delete_removesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    //@@author A0139714B
    @Test
    public void execute_editIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("edit");
    }
    
    //@@author A0139714B 
    @Test
    public void execute_edit_validDate() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500" , "1800", "tag");
        Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500" , "1800", "blah");
        Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500" , "1800", "hi");
        Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500" , "1800", "bye");
        Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500" , "1800", "yo");
    	Task toEdit = helper.generateEventTaskWithAll("", "", "16-10-2016", "", "", "yo");
    	Task updatedTask = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "16-10-2016", "1500" , "1800", "yo");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, updatedTask);
    
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
  
    	expectedAB.editTask((ReadOnlyTask)fiveTasks.get(4), toEdit);
    	
    	assertCommandBehavior("edit 5 d/16-10-2016",
    			String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask), 
    					expectedAB,
    					expectedList);
    }
    
    //@@author A0139714B 
    @Test
    public void execute_edit_validStartTime() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500" , "1800", "tag");
        Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500" , "1800", "blah");
        Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500" , "1800", "hi");
        Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500" , "1800", "bye");
        Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500" , "1800", "yo");
    	Task toEdit = helper.generateEventTaskWithAll("", "", "", "1200", "", "blah");
    	Task updatedTask = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1200" , "1800", "blah");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(p1, updatedTask, p3, p4, p5);
    	
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
  
    	expectedAB.editTask((ReadOnlyTask)fiveTasks.get(2), toEdit);
    	
    	assertCommandBehavior("edit 2 s/1200",
    			String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask), 
    					expectedAB,
    					expectedList);
    }
    
	// @@author A0139714B
	@Test
	public void execute_edit_validEndTime() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500", "1800",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500", "1800", "hi");
		Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1800", "bye");
		Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500", "1800", "yo");
		Task toEdit = helper.generateEventTaskWithAll("", "", "", "", "1900", "blah");
		Task updatedTask = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500",
				"1900", "blah");

		List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(p1, updatedTask, p3, p4, p5);

		model.resetData(new ListOfTask());
		for (Task t : fiveTasks) {
			model.addTask(t);
		}

		expectedAB.editTask((ReadOnlyTask) fiveTasks.get(2), toEdit);

		assertCommandBehavior("edit 2 e/1900", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask),
				expectedAB, expectedList);
	}
    
    //@@author A0139714B
    @Test
    public void execute_edit_fail_addEndTimeToATaskWithNoDate() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateFloatingTask("bla bla KEY bla", "blah blah blah", "tag");
        Task p2 = helper.generateFloatingTask("bla KEY bla bceofeia", "hello world", "blah");
        Task p3 = helper.generateFloatingTask("KE Y", "say goodbye", "hi");
        Task p4 = helper.generateFloatingTask("keyKEY sduauo", "move", "bye");
        Task p5 = helper.generateFloatingTask("K EY sduauo", "high kneel", "yo");
    	Task updatedTask = helper.generateFloatingTask("K EY sduauo", "high kneel", "yo");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, updatedTask);
    	
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
    	
    	assertCommandBehavior("edit 5 e/1900",
    						  String.format(Messages.MESSAGE_CANNOT_ADD_ENDTIME_WITH_NO_DATE),
    						  expectedAB,
    						  expectedList
    						  );  
    }

    //@@author A0139714B
    @Test
    public void execute_edit_fail_addStartTimeToATaskWithoutEndTime() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateDeadlineTask("bla bla KEY bla", "blah blah blah", "11-10-2016", "tag");
        Task p2 = helper.generateDeadlineTask("bla KEY bla bceofeia", "hello world", "12-10-2016", "blah");
        Task p3 = helper.generateDeadlineTask("KE Y", "say goodbye", "13-10-2016", "hi");
        Task p4 = helper.generateDeadlineTask("keyKEY sduauo", "move", "14-10-2016","bye");
        Task p5 = helper.generateDeadlineTask("K EY sduauo", "high kneel", "15-10-2016","yo");

    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
    	
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
    	
    	assertCommandBehavior("edit 5 s/1900",
    						  String.format(Messages.MESSAGE_CANNOT_ADD_STARTTIME_WITH_NO_ENDTIME),
    						  expectedAB,
    						  expectedList
    						  );  
    }
    
    //@@author A0139714B 
    @Test
    public void execute_edit_fail_startTimeAfterEndTime() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500" , "1800", "tag");
        Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500" , "1800", "blah");
        Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500" , "1800", "hi");
        Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500" , "1800", "bye");
        Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500" , "1800", "yo");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
    	
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
    	
    	assertCommandBehavior("edit 2 s/2000",
    			String.format(MESSAGE_STARTTIME_AFTER_ENDTIME), 
    					expectedAB,
    					expectedList);
    }
  
  	//@@author A0139714B 
    @Test
    public void execute_edit_removeDate() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateDeadlineTask("bla bla KEY bla", "blah blah blah", "11-10-2016", "tag");
        Task p2 = helper.generateDeadlineTask("bla KEY bla bceofeia", "hello world", "12-10-2016", "blah");
        Task p3 = helper.generateDeadlineTask("KE Y", "say goodbye", "13-10-2016", "hi");
        Task p4 = helper.generateDeadlineTask("keyKEY sduauo", "move", "14-10-2016", "bye");
        Task p5 = helper.generateDeadlineTask("K EY sduauo", "high kneel", "15-10-2016", "yo");
    	Task updatedTask = helper.generateFloatingTask("K EY sduauo", "high kneel", "yo");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, updatedTask);
    
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
    	
    	assertCommandBehavior("edit 5 d/rm",
    			String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask), 
    					expectedAB,
    					expectedList);
    }
    
    //@@author A0139714B 
    @Test
    public void execute_edit_removeEndTime() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateDeadlineTaskWithEndTime("bla bla KEY bla", "blah blah blah", "11-10-2016", "1900", "tag");
        Task p2 = helper.generateDeadlineTaskWithEndTime("bla KEY bla bceofeia", "hello world", "12-10-2016", "1800", "blah");
        Task p3 = helper.generateDeadlineTaskWithEndTime("KE Y", "say goodbye", "13-10-2016", "1900", "hi");
        Task p4 = helper.generateDeadlineTaskWithEndTime("keyKEY sduauo", "move", "14-10-2016", "1900", "bye");
        Task p5 = helper.generateDeadlineTaskWithEndTime("K EY sduauo", "high kneel", "15-10-2016", "1500",  "yo");
    	Task updatedTask = helper.generateDeadlineTask("bla bla KEY bla", "blah blah blah", "11-10-2016", "tag");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(updatedTask, p2, p3, p4, p5);

    
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
   
    	assertCommandBehavior("edit 1 e/rm",
    			String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask), 
    					expectedAB,
    					expectedList);
    }
    

    //@@author A0139714B 
    @Test
    public void execute_edit_removeTaskDescription() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1800", "1900", "tag");
        Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500","1800", "blah");
        Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1600", "1900", "hi");
        Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1900", "bye");
        Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1000", "1500",  "yo");
    	Task updatedTask = helper.generateEventTaskWithoutTaskDescription("bla bla KEY bla", "11-10-2016", "1800", "1900", "tag");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(updatedTask, p2, p3, p4, p5);

    
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
   
    	assertCommandBehavior("edit 1 i/rm",
    			String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask), 
    					expectedAB,
    					expectedList);
    }
    
    //@@author A0139714B 
    @Test
    public void execute_edit_removeStartTime() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1800", "1900", "tag");
        Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500","1800", "blah");
        Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1600", "1900", "hi");
        Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500", "1900", "bye");
        Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1000", "1500",  "yo");
    	Task updatedTask = helper.generateDeadlineTaskWithEndTime("bla bla KEY bla", "blah blah blah", "11-10-2016", "1900", "tag");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(updatedTask, p2, p3, p4, p5);

    
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
    	
   
    	assertCommandBehavior("edit 1 s/rm",
    			String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, updatedTask), 
    					expectedAB,
    					expectedList);
    }
    
    
    
    //@@author A0139714B 
    @Test
    public void execute_edit_InvalidIndex() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500" , "1800", "tag");
        Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "12-10-2016", "1500" , "1800", "blah");
        Task p3 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "13-10-2016", "1500" , "1800", "hi");
        Task p4 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "14-10-2016", "1500" , "1800", "bye");
        Task p5 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "15-10-2016", "1500" , "1800", "yo");
    	
    	List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
    	ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
    	List<Task> expectedList = helper.generateTaskList(p1, p2, p3, p4, p5);
    	
    	model.resetData(new ListOfTask());
    	for (Task t : fiveTasks) {
    		model.addTask(t);
    	}
    	
    	assertCommandBehavior("edit 7 e/1900",
    			String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX), 
    					expectedAB,
    					expectedList);
    }

	@Test
	public void execute_find_invalidArgsFormat() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
		assertCommandBehavior("find ", expectedMessage);
	}

	@Test
	public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500",
				"1800", "tag");
		Task pTarget2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "11-10-2016", "1500",
				"1800", "blah");
		Task p1 = helper.generateEventTaskWithAll("KE Y", "say goodbye", "11-10-2016", "1500", "1800", "hi");
		Task pTarget3 = helper.generateEventTaskWithAll("keyKEY sduauo", "move", "11-10-2016", "1500", "1800", "bye");
		Task p2 = helper.generateEventTaskWithAll("K EY sduauo", "high kneel", "11-10-2016", "1500", "1800", "yo");

		List<Task> fiveTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2, pTarget3);
		ListOfTask expectedAB = helper.generateListOfTask(fiveTasks);
		List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fiveTasks);

		assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}

	@Test
	public void execute_find_isNotCaseSensitive() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task p1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500", "1800",
				"tag");
		Task p2 = helper.generateEventTaskWithAll("bla KEY bla bceofeia", "hello world", "06-12-2016", "1800", "1900",
				"blah");
		Task p3 = helper.generateEventTaskWithAll("key key", "say goodbye", "03-10-2016", "1300", "1400", "hi");
		Task p4 = helper.generateEventTaskWithAll("KEy sduauo", "move", "10-09-2016", "1200", "1800", "bye");

		List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
		ListOfTask expectedAB = helper.generateListOfTask(fourTasks);
		List<Task> expectedList = fourTasks;
		helper.addToModel(model, fourTasks);

		assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}

	@Test
	public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEventTaskWithAll("bla bla KEY bla", "blah blah blah", "11-10-2016", "1500",
				"1800", "tag");
		Task pTarget2 = helper.generateEventTaskWithAll("bla rAnDoM bla bceofeia", "hello world", "22-09-2016", "1100",
				"1800", "blah");
		Task pTarget3 = helper.generateEventTaskWithAll("key key", "move around", "06-10-2017", "1100", "1200", "hi");
		Task p1 = helper.generateEventTaskWithAll("sduauo", "jump", "02-03-2016", "1300", "1400", "bye");

		List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
		ListOfTask expectedAB = helper.generateListOfTask(fourTasks);
		List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourTasks);

		assertCommandBehavior("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
				expectedAB, expectedList);
	}

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            Date date = new Date("23-06-2016");
            Time startTime = new Time("1900");
            Time endTime = new Time("2200");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name,date,startTime,endTime, tags);
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
                    new Date( (seed%2==1) ? "1" + seed + "-12-2" + seed + "22" : "1" + seed + "-12-212" + seed ),
                    new Time( "0" + seed + "00" ),
                    new Time( "0" + seed + "0" + seed ),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }
        
        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" d/").append(p.getDate().toString());
            cmd.append(" s/").append(p.getStartTime().toString());
            cmd.append(" e/").append(p.getEndTime().toString());
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an ListOfTask with auto-generated persons.
         */
        ListOfTask generateListOfTask(int numGenerated) throws Exception{
            ListOfTask listOfTask = new ListOfTask();
            addToListOfTask(listOfTask, numGenerated);
            return listOfTask;
        }

        /**
         * Generates an ListOfTask based on the list of Persons given.
         */
        ListOfTask generateListOfTask(List<Task> tasks) throws Exception{
            ListOfTask listOfTask = new ListOfTask();
            addToListOfTask(listOfTask, tasks);
            return listOfTask;
        }

        /**
         * Adds auto-generated Task objects to the given ListOfTask
         * @param listOfTask The ListOfTask to which the Persons will be added
         */
        void addToListOfTask(ListOfTask listOfTask, int numGenerated) throws Exception{
            addToListOfTask(listOfTask, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given ListOfTask
         */
        void addToListOfTask(ListOfTask listOfTask, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                listOfTask.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Persons based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... persons) {
            return Arrays.asList(persons);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateEventTaskWithAll(String name, String taskDescription, String date, String startTime, String endTime, String tag) throws Exception {
            return new Task(
                    new Name(name),
                    new TaskDescription(taskDescription),
                    new Date(date),
                    new Time(startTime),
                    new Time(endTime),
                    new UniqueTagList(new Tag(tag))
            );
        }
        
        Task generateEventTaskWithAllWithoutTag(String name, String taskDescription, String date, String startTime, String endTime) throws Exception {
            return new Task(
                    new Name(name),
                    new TaskDescription(taskDescription),
                    new Date(date),
                    new Time(startTime),
                    new Time(endTime),
                    new UniqueTagList()
            );
        }
        
        Task generateEventTaskWithoutTaskDescription(String name, String date, String startTime, String endTime, String tag) throws Exception {
            return new Task(
                    new Name(name),
                    new Date(date),
                    new Time(startTime),
                    new Time(endTime),
                    new UniqueTagList(new Tag(tag))
            );
        }
        
        Task generateEventTaskWithoutTaskDescriptionWithoutTag(String name, String date, String startTime, String endTime) throws Exception {
            return new Task(
                    new Name(name),
                    new Date(date),
                    new Time(startTime),
                    new Time(endTime),
                    new UniqueTagList()
            );
        }
        
        
        Task generateDeadlineTaskWithEndTime(String name, String taskDescription, String date, String endTime, String tag) throws Exception {
        	return new Task(
                    new Name(name),
                    new TaskDescription(taskDescription),
                    new Date(date),
                    new Time(endTime),
                    new UniqueTagList(new Tag(tag))
            );
        }
        
        Task generateDeadlineTaskWithEndTimeWithoutTag(String name, String taskDescription, String date, String endTime, String tag) throws Exception {
        	return new Task(
                    new Name(name),
                    new TaskDescription(taskDescription),
                    new Date(date),
                    new Time(endTime),
                    new UniqueTagList(new Tag(tag))
            );
        }
        
        Task generateDeadlineTaskWithEndTimeWithoutTaskDescription(String name, String date, String endTime, String tag) throws Exception {
        	return new Task(
                    new Name(name),
                    new Date(date),
                    new Time(endTime),
                    new UniqueTagList(new Tag(tag))
            );
        }
        Task generateDeadlineTaskWithEndTimeWithoutTaskDescriptionWithoutTag(String name, String date, String endTime) throws Exception {
        	return new Task(
                    new Name(name),
                    new Date(date),
                    new Time(endTime),
                    new UniqueTagList()
            );
        }
        
        Task generateDeadlineTask(String name, String taskDescription, String date, String tag) throws Exception {
        	return new Task(
                    new Name(name),
                    new TaskDescription(taskDescription),
                    new Date(date),
                    new UniqueTagList(new Tag(tag))
            );
        }
        
        Task generateDeadlineTask(String name, String taskDescription, String date) throws Exception {
        	return new Task(
                    new Name(name),
                    new TaskDescription(taskDescription),
                    new Date(date),
                    new UniqueTagList()
            );
        }
        
        Task generateDeadlineTaskWithoutTaskDescription(String name, String date, String tag) throws Exception {
        	return new Task(
                    new Name(name),
                    new Date(date),
                    new UniqueTagList(new Tag(tag))
            );
        }
        
        Task generateDeadlineTaskWithoutTaskDescriptionWithoutTag(String name, String date) throws Exception {
        	return new Task(
                    new Name(name),
                    new Date(date),
                    new UniqueTagList()
            );
        }
        
        Task generateFloatingTask(String name, String taskDescription, String tag) throws Exception {
        	return new Task(
                    new Name(name),
                    new TaskDescription(taskDescription),
                    new UniqueTagList(new Tag(tag))
            );
        }
        
        Task generateFloatingTaskWithoutTag(String name, String taskDescription) throws Exception {
        	return new Task(
                    new Name(name),
                    new TaskDescription(taskDescription),
                    new UniqueTagList()
            );
        }
        
        Task generateFloatingTaskWithoutTaskDescription(String name, String tag) throws Exception {
        	return new Task(
                    new Name(name),
                    new UniqueTagList(new Tag(tag))
            );
        }
        
        Task generateFloatingTaskWithoutTaskDescriptionWithoutTag(String name) throws Exception {
        	return new Task(
                    new Name(name),
                    new UniqueTagList()
            );
        }
     }
}
