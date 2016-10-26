package seedu.savvytasker.logic;

import com.google.common.eventbus.Subscribe;

import seedu.savvytasker.commons.core.EventsCenter;
import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.commons.events.model.SavvyTaskerChangedEvent;
import seedu.savvytasker.commons.events.ui.JumpToListRequestEvent;
import seedu.savvytasker.commons.events.ui.ShowHelpRequestEvent;
import seedu.savvytasker.logic.Logic;
import seedu.savvytasker.logic.LogicManager;
import seedu.savvytasker.logic.commands.*;
import seedu.savvytasker.logic.parser.IndexParser;
import seedu.savvytasker.model.Model;
import seedu.savvytasker.model.ModelManager;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.SavvyTasker;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.storage.StorageManager;

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
import static seedu.savvytasker.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlySavvyTasker latestSavedSavvyTasker;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(SavvyTaskerChangedEvent stce) {
        latestSavedSavvyTasker = new SavvyTasker(stce.data);
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

        latestSavedSavvyTasker = new SavvyTasker(model.getSavvyTasker()); // last saved assumed to be up to date before.
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
     * @see #assertCommandBehavior(String, String, ReadOnlyAddressBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new SavvyTasker(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlySavvyTasker expectedSavvyTasker,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        UnmodifiableObservableList<ReadOnlyTask> tasks = model.getFilteredTaskList();
        assertEquals(expectedShownList, tasks);

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedSavvyTasker, model.getSavvyTasker());
        assertEquals(expectedSavvyTasker, latestSavedSavvyTasker);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, String.format(MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));
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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new SavvyTasker(), Collections.emptyList());
    }


    /* NOT ENFORCED, NOT RUNNING TEST CASE
    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address", expectedMessage);
    }
    */

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.demo();
        SavvyTasker expectedST = new SavvyTasker();
        expectedST.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedST,
                expectedST.getReadOnlyListOfTasks());

    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        
        // the following commands outputs a different expected message dealing with
        // invalid indices.
        expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.COMMAND_FORMAT) + ": " + IndexParser.INDEX_MUST_BE_POSITIVE;
        //assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned [NOT SUPPORTED]
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
        model.resetData(new SavvyTasker());
        for (Task t : taskList) {
            model.addTask(t);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getSavvyTasker(), taskList);
    }

    /* NOT IMPLEMENTED, NOT RUNNING TEST CASE
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

        SavvyTasker expectedST = helper.generateSavvyTasker(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
                expectedST,
                expectedST.getReadOnlyListOfTasks());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }
    */


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.COMMAND_FORMAT);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        SavvyTasker expectedST = helper.generateSavvyTasker(threeTasks);
        expectedST.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedST,
                expectedST.getReadOnlyListOfTasks());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.COMMAND_FORMAT);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        pTarget1.setId(0);
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        pTarget2.setId(1);
        Task p1 = helper.generateTaskWithName("KE Y");
        p1.setId(2);
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        p2.setId(3);

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        SavvyTasker expectedST = helper.generateSavvyTasker(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find t/full KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedST,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        p1.setId(0);
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        p2.setId(1);
        Task p3 = helper.generateTaskWithName("key key");
        p3.setId(2);
        Task p4 = helper.generateTaskWithName("KEy sduauo");
        p4.setId(3);

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        SavvyTasker expectedST = helper.generateSavvyTasker(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedST,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        pTarget1.setId(0);
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        pTarget2.setId(1);
        Task pTarget3 = helper.generateTaskWithName("key key");
        pTarget3.setId(2);
        Task p1 = helper.generateTaskWithName("sduauo");
        p1.setId(3);

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        SavvyTasker expectedST = helper.generateSavvyTasker(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedST,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task demo() {
            return new Task("Demo task");
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the person data field values
         */
        Task generateTask(int seed) {
            Task t = new Task("Task " + seed);
            t.setId(seed);
            return t;
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task t) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            cmd.append(t.getTaskName().toString());

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         */
        SavvyTasker generateSavvyTasker(int numGenerated) throws Exception{
            SavvyTasker savvyTasker = new SavvyTasker();
            addToSavvyTasker(savvyTasker, numGenerated);
            return savvyTasker;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        SavvyTasker generateSavvyTasker(List<Task> tasks) throws Exception{
            SavvyTasker savvyTasker = new SavvyTasker();
            addToSavvyTasker(savvyTasker, tasks);
            return savvyTasker;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         */
        void addToSavvyTasker(SavvyTasker savvyTasker, int numGenerated) throws Exception{
            addToSavvyTasker(savvyTasker, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToSavvyTasker(SavvyTasker savvyTasker, List<Task> tasksToAdd) throws Exception{
            for(Task t : tasksToAdd){
                savvyTasker.addTask(t);
            }
        }

        /**
         * Adds auto-generated Person objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task t: tasksToAdd){
                model.addTask(t);
            }
        }

        /**
         * Generates a list of Task based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 0; i < numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String taskName) throws Exception {
            return new Task(taskName);
        }
    }
}
