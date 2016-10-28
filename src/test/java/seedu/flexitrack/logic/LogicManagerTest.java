package seedu.flexitrack.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.flexitrack.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flexitrack.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.flexitrack.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.flexitrack.commons.core.EventsCenter;
import seedu.flexitrack.commons.events.model.FlexiTrackChangedEvent;
import seedu.flexitrack.commons.events.ui.JumpToListRequestEvent;
import seedu.flexitrack.commons.events.ui.ShowHelpRequestEvent;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.logic.commands.ClearCommand;
import seedu.flexitrack.logic.commands.Command;
import seedu.flexitrack.logic.commands.CommandResult;
import seedu.flexitrack.logic.commands.DeleteCommand;
import seedu.flexitrack.logic.commands.ExitCommand;
import seedu.flexitrack.logic.commands.FindCommand;
import seedu.flexitrack.logic.commands.HelpCommand;
import seedu.flexitrack.logic.commands.ListCommand;
import seedu.flexitrack.logic.commands.SelectCommand;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.Model;
import seedu.flexitrack.model.ModelManager;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.Name;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.storage.StorageManager;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TypicalTestTasks;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    // These are for checking the correctness of the events raised
    private ReadOnlyFlexiTrack latestSavedFlexiTracker;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelrChangedEvent(FlexiTrackChangedEvent abce) {
        latestSavedFlexiTracker = new FlexiTrack(abce.data);
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
        String tempFlexiTrackerFile = saveFolder.getRoot().getPath() + "TempFlexiTracker.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempFlexiTrackerFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedFlexiTracker = new FlexiTrack(model.getFlexiTrack()); // last saved assumed to be up to date before.
        
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
        assertCommandBehavior(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'FlexiTracker' and the 'last shown list' are expected to be
     * empty.
     * 
     * @see #assertCommandBehavior(String, String, ReadOnlyFlexiTrack, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new FlexiTrack(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's
     * state are as expected:<br>
     * - the internal FlexiTracker data are same as those in the
     * {@code expectedFlexiTracker} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedFlexiTracker} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
            ReadOnlyFlexiTrack expectedFlexiTracker, List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data;
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedFlexiTracker, model.getFlexiTrack());
        assertEquals(expectedFlexiTracker, latestSavedFlexiTracker);
    }

    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.HELP_MESSAGE_USAGE);
        // assertTrue(helpShown);
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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new FlexiTrack(), Collections.emptyList());
    }

    // TODO: What is the limitation of add????
    // @Test
    // public void execute_add_invalidArgsFormat() throws Exception {
    // String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
    // AddCommand.MESSAGE_USAGE);
    // assertCommandBehavior(
    // "adds wrong args wrong args", expectedMessage);
    // assertCommandBehavior(
    // "add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, address",
    // expectedMessage);
    // assertCommandBehavior(
    // "add Valid Name p/12345 valid@email.butNoPrefix a/valid, address",
    // expectedMessage);
    // assertCommandBehavior(
    // "add Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address",
    // expectedMessage);
    // }

    @Test
    public void execute_add_invalidTimeData() throws Exception {
        assertCommandBehavior("add Apply for job by/ mondat", DateTimeInfo.MESSAGE_DATETIMEINFO_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.midterm();
        FlexiTrack expectedAB = new FlexiTrack();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.midterm();
        FlexiTrack expectedAB = new FlexiTrack();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal FlexiTracker

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK, expectedAB,
                expectedAB.getTaskList());

    }

    // TODO: need to change all the test casses
    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        FlexiTrack expectedAB = helper.generateFlexiTracker(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare FlexiTracker state
        helper.addToModel(model, 2);

        assertCommandBehavior("list", ListCommand.MESSAGE_SUCCESS, expectedAB, expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandBehavior(commandWord, expectedMessage); // index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); // index
                                                                     // should
                                                                     // be
                                                                     // unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); // index
                                                                     // should
                                                                     // be
                                                                     // unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); // index
                                                                    // cannot be
                                                                    // 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 Tasks
        model.resetData(new FlexiTrack());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getFlexiTrack(), taskList);
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

    // TODO: need to change all the test casses
    @Test
    public void execute_select_jumpsToCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generateTaskList(3);

        FlexiTrack expectedAB = helper.generateFlexiTracker(threePersons);
        helper.addToModel(model, threePersons);

        assertCommandBehavior("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threePersons.get(1));
    }
    
    @Test
    public void sort(){
        TypicalTestTasks td = new TypicalTestTasks();
        TestTask[] currentArray = td.getTypicalUnsortedTasks();
        List<TestTask> currentList = Arrays.asList(currentArray);
        
        Arrays.sort(currentArray);
        Collections.sort(currentList);
        
        TestTask[] expectedArray = td.getTypicalSortedTasks();
        List<TestTask> expectedList = Arrays.asList(expectedArray);
        
        // Test Arrays Sort Correctly
        assertTrue(containsInOrder(currentList, expectedList));
        
        // Test Lists Sort Correctly
        currentList = Arrays.asList(currentArray);
        assertTrue(containsInOrder(currentList, expectedList));
    }

    private boolean containsInOrder(List<TestTask> currentList, List<TestTask> expectedList) {

        if (currentList.size() != expectedList.size()) {
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < currentList.size(); i++) {
            if (!currentList.get(i).getName().toString().equals(expectedList.get(i).getName().toString())) {
                return false;
            }
        }

        return true;
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
        List<Task> threePersons = helper.generateTaskList(3);
        Collections.sort(threePersons);
        FlexiTrack expectedAB = helper.generateFlexiTracker(threePersons);
        expectedAB.removeTask(threePersons.get(1));
        helper.addToModel(model, threePersons);

        assertCommandBehavior("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threePersons.get(1)),
                expectedAB, expectedAB.getTaskList());
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

        List<Task> fourPersons = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        Collections.sort(fourPersons);
        FlexiTrack expectedAB = helper.generateFlexiTracker(fourPersons);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        Collections.sort(expectedList);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourPersons = helper.generateTaskList(p3, p1, p4, p2);
        Collections.sort(fourPersons);
        FlexiTrack expectedAB = helper.generateFlexiTracker(fourPersons);
        List<Task> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourPersons = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        Collections.sort(fourPersons);
        FlexiTrack expectedAB = helper.generateFlexiTracker(fourPersons);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task midterm() throws Exception {
            Name name = new Name("Midter cs 2101");
            DateTimeInfo dueDate = new DateTimeInfo("Mar 23 2017 09:00");
            DateTimeInfo startingTime = new DateTimeInfo("Feb 29 2000 00:00");
            DateTimeInfo endingTime = new DateTimeInfo("Feb 29 2000 00:00");
            return new Task(name, dueDate, startingTime, endingTime);
        }

        /**
         * Generates a valid person using the given seed. Running this function
         * with the same parameter values guarantees the returned person will
         * have the same state. Each unique seed will generate a unique Person
         * object.
         *
         * @param seed
         *            used to generate the person data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(new Name("Person " + seed), new DateTimeInfo("" + Math.abs(seed)),
                    new DateTimeInfo(seed + "@email"), new DateTimeInfo("House of " + seed));
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            if (p.getIsTask()) {
                cmd.append(" by/ ").append(p.getDueDate());
            } else if (p.getIsEvent()) {
                cmd.append(" from/ ").append(p.getStartTime());
                cmd.append(" to/ ").append(p.getEndTime());
            }
            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated tasks.
         */
        FlexiTrack generateFlexiTracker(int numGenerated) throws Exception {
            FlexiTrack addressBook = new FlexiTrack();
            addToAddressBook(addressBook, numGenerated);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of tasks given.
         */
        FlexiTrack generateFlexiTracker(List<Task> tasks) throws Exception {
            FlexiTrack flexiTracker = new FlexiTrack();
            addToFlexiTracker(flexiTracker, tasks);
            return flexiTracker;
        }

        /**
         * Adds auto-generated task objects to the given AddressBook
         * 
         * @param addressBook
         *            The AddressBook to which the tasks will be added
         */
        void addToAddressBook(FlexiTrack addressBook, int numGenerated) throws Exception {
            addToFlexiTracker(addressBook, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given AddressBook
         */
        void addToFlexiTracker(FlexiTrack flexiTracker, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                flexiTracker.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * 
         * @param model
         *            The model to which the Tasks will be added
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
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
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
            return new Task(new Name(name), new DateTimeInfo("1/1/2011"), new DateTimeInfo("2/2/2012"),
                    new DateTimeInfo("3/3/2013"));
        }
    }
}
