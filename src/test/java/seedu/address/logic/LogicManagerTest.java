package seedu.address.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.menion.commons.core.EventsCenter;
import seedu.menion.commons.events.model.ActivityManagerChangedEvent;
import seedu.menion.commons.events.ui.JumpToListRequestEvent;
import seedu.menion.commons.events.ui.ShowHelpRequestEvent;
import seedu.menion.logic.Logic;
import seedu.menion.logic.LogicManager;
import seedu.menion.logic.commands.*;
import seedu.menion.model.Model;
import seedu.menion.model.ModelManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.*;
import seedu.menion.model.ActivityManager;
import seedu.menion.storage.StorageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.menion.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyActivityManager latestSavedMenion;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(ActivityManagerChangedEvent abce) {
        latestSavedMenion = new ActivityManager(abce.data);
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
        String tempAddressBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempAddressBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedMenion = new ActivityManager(model.getActivityManager()); // last saved assumed to be up to date before.
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
     * Both the 'Menion' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyActivityManager, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new ActivityManager(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyActivityManager expectedTaskManager,
                                       List<? extends ReadOnlyActivity> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getActivityManager());
        assertEquals(expectedTaskManager, latestSavedMenion);
    }

    //Pass
    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }
    //Pass
    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }
    //Pass
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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new ActivityManager(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);

    }

    @Test
    public void execute_add_invalidActivityData() throws Exception {
       
        assertCommandBehavior(
                "add Valid Name by: 40-13-2016 1900 n: hello", ActivityDate.MESSAGE_ACTIVITYDATE_INVALID);
        assertCommandBehavior(
        		"add Valid Name by: 12-12-2016 2600 n: hello", ActivityTime.ACTIVITY_TIME_CONSTRAINTS);
       
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Activity toBeAdded = helper.exampletask();
        ActivityManager expectedAB = new ActivityManager();
        expectedAB.addTask(toBeAdded);
        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }
    


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        ActivityManager expectedAB = helper.generateMenion(2);
        List<? extends ReadOnlyActivity> expectedList = expectedAB.getTaskList();
        // prepare address book state
        helper.addToModel(model, 2);
        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS_ALL,
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
        String expectedMessage = MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Activity> taskList = helper.generateTaskList(2);

        // set AB state to 2 persons
        model.resetData(new ActivityManager());
        for (Activity p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getActivityManager(), taskList);
    }



    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    /*
    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }
*/
    
    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Activity> threeTasks = helper.generateTaskList(3);
        ActivityManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);
        assertCommandBehavior("delete task 2",
                String.format(DeleteCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    
    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Activity pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Activity p1 = helper.generateTaskWithName("KE Y");
        Activity p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        List<Activity> fourPersons = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        ActivityManager expectedAB = helper.generateTaskManager(fourPersons);
        List<Activity> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourPersons);
        assertCommandBehavior("find KEY",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
	
    
    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity p1 = helper.generateTaskWithName("bla bla KEY bla");
        Activity p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Activity p3 = helper.generateTaskWithName("key key");
        Activity p4 = helper.generateTaskWithName("KEy sduauo");
        List<Activity> fourPersons = helper.generateTaskList(p3, p1, p4, p2);
        ActivityManager expectedAB = helper.generateTaskManager(fourPersons);
        List<Activity> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);
        assertCommandBehavior("find KEY",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Activity pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Activity pTarget3 = helper.generateTaskWithName("key key");
        Activity p1 = helper.generateTaskWithName("sduauo");
        List<Activity> fourPersons = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        ActivityManager expectedAB = helper.generateTaskManager(fourPersons);
        List<Activity> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourPersons);
        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Activity exampletask() throws Exception {
        	String activityType = "task";
            ActivityName name = new ActivityName("complete cs2103t");
            Note note = new Note("test note");
            ActivityDate startDate = new ActivityDate("18-09-2016");
            ActivityTime startTime = new ActivityTime("1900");
            Completed status = new Completed(Completed.UNCOMPLETED_ACTIVITY);
            
            return new Activity(activityType, name, note, startDate, startTime, status);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         */
        Activity generateTask(int seed) throws Exception {
            return new Activity(
            		"task",
                    new ActivityName("task " + seed),
                    new Note("" + Math.abs(seed)),
                    new ActivityDate("18-08-1994"),
                    new ActivityTime("1900"),
                    new Completed(Completed.UNCOMPLETED_ACTIVITY)
            );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Activity p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            cmd.append(p.getActivityName().toString());
            cmd.append(" by: ").append(p.getActivityStartDate().toString());
            cmd.append(" ").append(p.getActivityStartTime().toString());
            cmd.append(" n:").append(p.getNote().toString());

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         */
        ActivityManager generateMenion(int numGenerated) throws Exception{
            ActivityManager menion = new ActivityManager();
            addToTaskManager(menion, numGenerated);
            return menion;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        ActivityManager generateTaskManager(List<Activity> tasks) throws Exception{
            ActivityManager taskManager = new ActivityManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param taskManager The AddressBook to which the Persons will be added
         */
        void addToTaskManager(ActivityManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Task to the given TaskManager
         */
        void addToTaskManager(ActivityManager taskManager, List<Activity> taskToAdd) throws Exception{
            for(Activity p: taskToAdd){
                taskManager.addTask(p);
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
         * Adds the given list of Task to the given model
         */
        void addToModel(Model model, List<Activity> taskToAdd) throws Exception{
            for(Activity p: taskToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Task based on the flags.
         */
        List<Activity> generateTaskList(int numGenerated) throws Exception{
            List<Activity> taskList = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                taskList.add(generateTask(i));
            }
            return taskList;
        }

        List<Activity> generateTaskList(Activity... task) {
            return Arrays.asList(task);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Activity generateTaskWithName(String taskName) throws Exception {
            return new Activity(
            		"task",
                    new ActivityName(taskName),
                    new Note("test note"),
                    new ActivityDate("18-06-2016"),
                    new ActivityTime("1900"),
                    new Completed(Completed.UNCOMPLETED_ACTIVITY));
        }
    }
}