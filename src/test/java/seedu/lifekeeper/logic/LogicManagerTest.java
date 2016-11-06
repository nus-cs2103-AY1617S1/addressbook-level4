package seedu.lifekeeper.logic;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.lifekeeper.commons.core.EventsCenter;
import seedu.lifekeeper.commons.events.model.AddressBookChangedEvent;
import seedu.lifekeeper.commons.events.ui.JumpToListRequestEvent;
import seedu.lifekeeper.commons.events.ui.ShowHelpRequestEvent;
import seedu.lifekeeper.logic.Logic;
import seedu.lifekeeper.logic.LogicManager;
import seedu.lifekeeper.logic.commands.*;
import seedu.lifekeeper.model.LifeKeeper;
import seedu.lifekeeper.model.Model;
import seedu.lifekeeper.model.ModelManager;
import seedu.lifekeeper.model.ReadOnlyLifeKeeper;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.Name;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.Reminder;
import seedu.lifekeeper.model.activity.task.*;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.model.tag.UniqueTagList;
import seedu.lifekeeper.storage.StorageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.lifekeeper.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyLifeKeeper latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(AddressBookChangedEvent abce) {
        latestSavedAddressBook = new LifeKeeper(abce.data);
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

        latestSavedAddressBook = new LifeKeeper(model.getLifekeeper()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }
  //@@author A0131813R
    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyLifeKeeper, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new LifeKeeper(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyLifeKeeper expectedAddressBook,
                                       List<? extends ReadOnlyActivity> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getLifekeeper());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
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
        model.addTask(helper.generateActivity(1));
        model.addTask(helper.generateActivity(2));
        model.addTask(helper.generateActivity(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new LifeKeeper(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessageInvalidFormat = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        String expectedMessageInvalidActivity = Command.MESSAGE_INVALID_ACTIVITY_TYPE;
        String expectedMessageInvalidPriority = Priority.MESSAGE_PRIORITY_CONSTRAINTS;
        String expectedMessageInvalidReminder = Reminder.MESSAGE_REMINDER_CONSTRAINTS;
        assertCommandBehavior(
                "add task d/11-11-2103 1000 e/12-11-2103", expectedMessageInvalidActivity);
        assertCommandBehavior(
                "add Valid Name 12345 p/isNotInteger", expectedMessageInvalidPriority);
        assertCommandBehavior(
                "add Valid Name s/12/11 2016", expectedMessageInvalidFormat);
        assertCommandBehavior(
                "add Valid Name r/laterIsNotAccepted", expectedMessageInvalidReminder);
    }

    @Test
    public void execute_add_invalidActivityData() throws Exception {
        assertCommandBehavior(
                "add []\\[;]", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/11111212 p/1 r/11-11-2016 1200", DueDate.MESSAGE_DUEDATE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/11-11-2017 12:00 p/-99 r/11-11-2016 1200", Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/11-11-2017 12:00 p/1 r/11-11-2016 1200 t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Activity toBeAdded = helper.assignment();
        LifeKeeper expectedAB = new LifeKeeper();
        expectedAB.addActivity(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getActivityList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Activity toBeAdded = helper.assignment();
        LifeKeeper expectedAB = new LifeKeeper();
        expectedAB.addActivity(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // activity already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getActivityList());

    }


    @Test
    public void execute_list_showsAllActivities() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        LifeKeeper expectedAB = helper.generateAddressBook(2);
        List<? extends ReadOnlyActivity> expectedList = expectedAB.getActivityList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single activity in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single activity in the last shown list based on visible index.
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
     * targeting a single activity in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single activity in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Activity> personList = helper.generateActivityList(2);

        // set AB state to 2 persons
        model.resetData(new LifeKeeper());
        for(int i = personList.size()-1; i >= 0; i--){
            Activity  p = personList.get(i);
        	model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getLifekeeper(), personList);
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
    public void execute_select_jumpsToCorrectActivity() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Activity> threeActivities = helper.generateActivityList(3);

        LifeKeeper expectedAB = helper.generateAddressBook(threeActivities);
        helper.addToModel(model, threeActivities);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
                expectedAB,
                expectedAB.getActivityList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeActivities.get(1));
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
    public void execute_delete_removesCorrectActivity() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Activity> threeActivities = helper.generateActivityList(3);

        LifeKeeper expectedAB = helper.generateAddressBook(threeActivities);
        expectedAB.removeActivity(threeActivities.get(1));
        helper.addToModel(model, threeActivities);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeActivities.get(1)),
                expectedAB,
                expectedAB.getActivityList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity pTarget1 = helper.generateActivityWithName("bla bla KEY bla");
        Activity pTarget2 = helper.generateActivityWithName("bla KEY bla bceofeia");
        Activity p1 = helper.generateActivityWithName("KE Y");
        Activity p2 = helper.generateActivityWithName("KEYKEYKEY sduauo");

        List<Activity> fourActivities = helper.generateActivityList(p1, pTarget1, p2, pTarget2);
        LifeKeeper expectedAB = helper.generateAddressBook(fourActivities);
        List<Activity> expectedList = Lists.reverse(helper.generateActivityList(pTarget1, pTarget2));
        helper.addToModel(model, fourActivities);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity p1 = helper.generateActivityWithName("bla bla KEY bla");
        Activity p2 = helper.generateActivityWithName("bla KEY bla bceofeia");
        Activity p3 = helper.generateActivityWithName("key key");
        Activity p4 = helper.generateActivityWithName("KEy sduauo");

        List<Activity> fourActivities = helper.generateActivityList(p3, p1, p4, p2);
        LifeKeeper expectedAB = helper.generateAddressBook(fourActivities);
        List<Activity> expectedList = Lists.reverse(fourActivities);
        helper.addToModel(model, fourActivities);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity pTarget1 = helper.generateActivityWithName("bla bla KEY bla");
        Activity pTarget2 = helper.generateActivityWithName("bla rAnDoM bla bceofeia");
        Activity pTarget3 = helper.generateActivityWithName("key key");
        Activity p1 = helper.generateActivityWithName("sduauo");

        List<Activity> fourActivities = helper.generateActivityList(pTarget1, p1, pTarget2, pTarget3);
        LifeKeeper expectedAB = helper.generateAddressBook(fourActivities);
        List<Activity> expectedList = Lists.reverse(helper.generateActivityList(pTarget1, pTarget2, pTarget3));
        helper.addToModel(model, fourActivities);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Activity assignment() throws Exception {
            Name name = new Name("assignment");
            Reminder reminder = new Reminder("11-11-2211 1000");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Activity(name, reminder, tags);
        }

        /**
         * Generates a valid activity using the given seed.
         * Running this function with the same parameter values guarantees the returned activity will have the same state.
         * Each unique seed will generate a unique Activity object.
         *
         * @param seed used to generate the activity data field values
         */
        Activity generateActivity(int seed) throws Exception {
            return new Activity(
                    new Name("Activity " + seed),
                    new Reminder("10-10-2019 " + seed + "00"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the activity given */
        String generateAddCommand(Activity p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" r/").append(p.getReminder());
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         */
        LifeKeeper generateAddressBook(int numGenerated) throws Exception{
            LifeKeeper lifeKeeper = new LifeKeeper();
            addToAddressBook(lifeKeeper, numGenerated);
            return lifeKeeper;
        }

        /**
         * Generates an AddressBook based on the list of Activities given.
         */
        LifeKeeper generateAddressBook(List<Activity> persons) throws Exception{
            LifeKeeper lifeKeeper = new LifeKeeper();
            addToAddressBook(lifeKeeper, persons);
            return lifeKeeper;
        }

        /**
         * Adds auto-generated Activity objects to the given AddressBook
         * @param lifeKeeper The AddressBook to which the Activities will be added
         */
        void addToAddressBook(LifeKeeper lifeKeeper, int numGenerated) throws Exception{
            addToAddressBook(lifeKeeper, generateActivityList(numGenerated));
        }

        /**
         * Adds the given list of Activities to the given AddressBook
         */
        void addToAddressBook(LifeKeeper lifeKeeper, List<Activity> personsToAdd) throws Exception{
            for(Activity p: personsToAdd){
                lifeKeeper.addActivity(p);
            }
        }

        /**
         * Adds auto-generated Activity objects to the given model
         * @param model The model to which the Activities will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateActivityList(numGenerated));
        }

        /**
         * Adds the given list of Activities to the given model
         */
        void addToModel(Model model, List<Activity> personsToAdd) throws Exception{
            for(Activity p: personsToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Activities based on the flags.
         */
        List<Activity> generateActivityList(int numGenerated) throws Exception{
            List<Activity> persons = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                persons.add(generateActivity(i));
            }
            return persons;
        }

        List<Activity> generateActivityList(Activity... persons) {
            return Arrays.asList(persons);
        }

        /**
         * Generates a Activity object with given name. Other fields will have some dummy values.
         */
        Activity generateActivityWithName(String name) throws Exception {
            return new Activity(
                    new Name(name),
                    new Reminder("11-11-2103 1100"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
