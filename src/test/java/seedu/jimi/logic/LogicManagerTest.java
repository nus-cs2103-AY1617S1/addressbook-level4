package seedu.jimi.logic;

import com.google.common.eventbus.Subscribe;

import seedu.jimi.commons.core.EventsCenter;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.ui.JumpToListRequestEvent;
import seedu.jimi.commons.events.ui.ShowHelpRequestEvent;
import seedu.jimi.logic.Logic;
import seedu.jimi.logic.LogicManager;
import seedu.jimi.logic.commands.*;
import seedu.jimi.model.TaskBook;
import seedu.jimi.model.Model;
import seedu.jimi.model.ModelManager;
import seedu.jimi.model.ReadOnlyTaskBook;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.*;
import seedu.jimi.storage.StorageManager;

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
import static seedu.jimi.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskBook latestSavedTaskBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(AddressBookChangedEvent abce) {
        latestSavedTaskBook = new TaskBook(abce.data);
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

        latestSavedTaskBook = new TaskBook(model.getTaskBook()); // last saved assumed to be up to date before.
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
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
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
        model.addFloatingTask(helper.generateFloatingTask(1));
        model.addFloatingTask(helper.generateFloatingTask(2));
        model.addFloatingTask(helper.generateFloatingTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBook(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add do dishes t/impt t/asd", expectedMessage);
        assertCommandBehavior(
                "add wash //plates", expectedMessage);
    }

    @Test
    public void execute_add_invalidPersonData() throws Exception {
        assertCommandBehavior(
                "add Valid task t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        FloatingTask toBeAdded = helper.adam();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addFloatingTask(toBeAdded);

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
        FloatingTask toBeAdded = helper.adam();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addFloatingTask(toBeAdded);

        // setup starting state
        model.addFloatingTask(toBeAdded); // person already in internal address book

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
        TaskBook expectedAB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
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
        List<FloatingTask> floatingTaskList = helper.generateFloatingTaskList(2);

        // set AB state to 2 persons
        model.resetData(new TaskBook());
        for (FloatingTask p : floatingTaskList) {
            model.addFloatingTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskBook(), floatingTaskList);
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
        List<FloatingTask> threeFloatingTasks = helper.generateFloatingTaskList(3);

        TaskBook expectedAB = helper.generateFloatingTaskBook(threeFloatingTasks);
        helper.addToModel(model, threeFloatingTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeFloatingTasks.get(1));
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
        List<FloatingTask> threeFloatingTasks = helper.generateFloatingTaskList(3);

        TaskBook expectedAB = helper.generateFloatingTaskBook(threeFloatingTasks);
        expectedAB.removeTask(threeFloatingTasks.get(1));
        helper.addToModel(model, threeFloatingTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeFloatingTasks.get(1)),
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
        FloatingTask pTarget1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        FloatingTask pTarget2 = helper.generateFloatingTaskWithName("bla KEY bla bceofeia");
        FloatingTask p1 = helper.generateFloatingTaskWithName("KE Y");
        FloatingTask p2 = helper.generateFloatingTaskWithName("KEYKEYKEY sduauo");

        List<FloatingTask> fourFloatingTasks = helper.generateFloatingTaskList(p1, pTarget1, p2, pTarget2);
        TaskBook expectedAB = helper.generateFloatingTaskBook(fourFloatingTasks);
        List<FloatingTask> expectedList = helper.generateFloatingTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourFloatingTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask p1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        FloatingTask p2 = helper.generateFloatingTaskWithName("bla KEY bla bceofeia");
        FloatingTask p3 = helper.generateFloatingTaskWithName("key key");
        FloatingTask p4 = helper.generateFloatingTaskWithName("KEy sduauo");

        List<FloatingTask> fourFloatingTasks = helper.generateFloatingTaskList(p3, p1, p4, p2);
        TaskBook expectedAB = helper.generateFloatingTaskBook(fourFloatingTasks);
        List<FloatingTask> expectedList = fourFloatingTasks;
        helper.addToModel(model, fourFloatingTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask pTarget1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        FloatingTask pTarget2 = helper.generateFloatingTaskWithName("bla rAnDoM bla bceofeia");
        FloatingTask pTarget3 = helper.generateFloatingTaskWithName("key key");
        FloatingTask p1 = helper.generateFloatingTaskWithName("sduauo");

        List<FloatingTask> fourFloatingTasks = helper.generateFloatingTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskBook expectedAB = helper.generateFloatingTaskBook(fourFloatingTasks);
        List<FloatingTask> expectedList = helper.generateFloatingTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourFloatingTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        FloatingTask adam() throws Exception {
            Name name = new Name("Adam Brown");
            Tag tag1 = new Tag("tag1");
            UniqueTagList tags = new UniqueTagList(tag1);
            return new FloatingTask(name, tags);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique FloatingTask object.
         *
         * @param seed used to generate the person data field values
         */
        FloatingTask generateFloatingTask(int seed) throws Exception {
            return new FloatingTask(
                    new Name("FloatingTask " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(FloatingTask p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskBook with auto-generated persons.
         */
        TaskBook generateTaskBook(int numGenerated) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, numGenerated);
            return taskBook;
        }

        /**
         * Generates an TaskBook based on the list of Persons given.
         */
        TaskBook generateFloatingTaskBook(List<FloatingTask> floatingTasks) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToFloatingTaskBook(taskBook, floatingTasks);
            return taskBook;
        }

        /**
         * Adds auto-generated FloatingTask objects to the given TaskBook
         * @param taskBook The TaskBook to which the Persons will be added
         */
        void addToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
            addToFloatingTaskBook(taskBook, generateFloatingTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given TaskBook
         */
        void addToFloatingTaskBook(TaskBook taskBook, List<FloatingTask> floatingTasksToAdd) throws Exception{
            for(FloatingTask p: floatingTasksToAdd){
                taskBook.addFloatingTask(p);
            }
        }

        /**
         * Adds auto-generated FloatingTask objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateFloatingTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<FloatingTask> floatingTasksToAdd) throws Exception{
            for(FloatingTask p: floatingTasksToAdd){
                model.addFloatingTask(p);
            }
        }

        /**
         * Generates a list of Persons based on the flags.
         */
        List<FloatingTask> generateFloatingTaskList(int numGenerated) throws Exception{
            List<FloatingTask> floatingTasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                floatingTasks.add(generateFloatingTask(i));
            }
            return floatingTasks;
        }

        List<FloatingTask> generateFloatingTaskList(FloatingTask... persons) {
            return Arrays.asList(persons);
        }

        /**
         * Generates a FloatingTask object with given name. Other fields will have some dummy values.
         */
        FloatingTask generateFloatingTaskWithName(String name) throws Exception {
            return new FloatingTask(
                    new Name(name),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
