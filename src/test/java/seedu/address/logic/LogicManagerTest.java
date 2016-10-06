package seedu.address.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.EventsCenter;
import seedu.address.logic.commands.*;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.item.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.storage.StorageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyAddressBook latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(AddressBookChangedEvent abce) {
        latestSavedAddressBook = new AddressBook(abce.data);
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

        latestSavedAddressBook = new AddressBook(model.getAddressBook()); // last saved assumed to be up to date before.
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
        assertCommandBehavior(inputCommand, expectedMessage, new AddressBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyAddressBook expectedAddressBook,
                                       List<? extends ReadOnlyToDo> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredPersonList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getAddressBook());
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
        model.addItem(helper.generateItem(1));
        model.addItem(helper.generateItem(2));
        model.addItem(helper.generateItem(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new AddressBook(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid ItemType 12345 e/valid@email.butNoPhonePrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid ItemType p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid ItemType p/12345 e/valid@email.butNoAddressPrefix valid, address", expectedMessage);
    }

    @Test
    public void execute_add_invalidPersonData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] p/12345 e/valid@e.mail a/valid, address", ItemType.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid ItemType p/not_numbers e/valid@e.mail a/valid, address", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid ItemType p/12345 e/notAnEmail a/valid, address", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid ItemType p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Item toBeAdded = helper.adam();
        AddressBook expectedAB = new AddressBook();
        expectedAB.addItem(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getPersonList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Item toBeAdded = helper.adam();
        AddressBook expectedAB = new AddressBook();
        expectedAB.addItem(toBeAdded);

        // setup starting state
        model.addItem(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_PERSON,
                expectedAB,
                expectedAB.getPersonList());

    }


    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expectedAB = helper.generateAddressBook(2);
        List<? extends ReadOnlyToDo> expectedList = expectedAB.getPersonList();

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
        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Item> itemList = helper.generateItemList(2);

        // set AB state to 2 persons
        model.resetData(new AddressBook());
        for (Item p : itemList) {
            model.addItem(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getAddressBook(), itemList);
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
        List<Item> threeItems = helper.generateItemList(3);

        AddressBook expectedAB = helper.generateAddressBook(threeItems);
        helper.addToModel(model, threeItems);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
                expectedAB,
                expectedAB.getPersonList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredPersonList().get(1), threeItems.get(1));
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
        List<Item> threeItems = helper.generateItemList(3);

        AddressBook expectedAB = helper.generateAddressBook(threeItems);
        expectedAB.removeItem(threeItems.get(1));
        helper.addToModel(model, threeItems);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, threeItems.get(1)),
                expectedAB,
                expectedAB.getPersonList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Item pTarget1 = helper.generateItemWithName("bla bla KEY bla");
        Item pTarget2 = helper.generateItemWithName("bla KEY bla bceofeia");
        Item p1 = helper.generateItemWithName("KE Y");
        Item p2 = helper.generateItemWithName("KEYKEYKEY sduauo");

        List<Item> fourItems = helper.generateItemList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAB = helper.generateAddressBook(fourItems);
        List<Item> expectedList = helper.generateItemList(pTarget1, pTarget2);
        helper.addToModel(model, fourItems);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Item p1 = helper.generateItemWithName("bla bla KEY bla");
        Item p2 = helper.generateItemWithName("bla KEY bla bceofeia");
        Item p3 = helper.generateItemWithName("key key");
        Item p4 = helper.generateItemWithName("KEy sduauo");

        List<Item> fourItems = helper.generateItemList(p3, p1, p4, p2);
        AddressBook expectedAB = helper.generateAddressBook(fourItems);
        List<Item> expectedList = fourItems;
        helper.addToModel(model, fourItems);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Item pTarget1 = helper.generateItemWithName("bla bla KEY bla");
        Item pTarget2 = helper.generateItemWithName("bla rAnDoM bla bceofeia");
        Item pTarget3 = helper.generateItemWithName("key key");
        Item p1 = helper.generateItemWithName("sduauo");

        List<Item> fourItems = helper.generateItemList(pTarget1, p1, pTarget2, pTarget3);
        AddressBook expectedAB = helper.generateAddressBook(fourItems);
        List<Item> expectedList = helper.generateItemList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourItems);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Item adam() throws Exception {
            Type type = new Type("event");
            Name privateName = new Name("go win some stuff");
            TodoDate startDate = new TodoDate("2016-01-01");
            TodoTime startTime = new TodoTime("18:00");
            TodoDate endDate = new TodoDate("2016-01-01");
            TodoTime endTime = new TodoTime("19:00");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Item(type, privateName, startDate, startTime, endDate, endTime, tags);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Item object.
         *
         * @param seed used to generate the person data field values
         */
        Item generateItem(int seed) throws Exception {
            return new Item(
                    new Type("Item " + seed),
                    new Name("" + Math.abs(seed)),
                    new TodoDate("2016-06-06"),
                    new TodoTime("18:00"),
                    new TodoDate("2016-07-07"),
                    new TodoTime("19:00"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Item p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getType().toString());
            if (p.getType().toString().equals(Type.TASK_WORD)) {
                cmd.append(" n/").append(p.getName());
            } else if (p.getType().toString().equals(Type.DEADLINE_WORD)) {
                cmd.append(" n/").append(p.getName());
                cmd.append(" d/").append(p.getEndDate());
                cmd.append(" t/").append(p.getEndTime());
            } else {
                cmd.append(" n/").append(p.getName());
                cmd.append(" sd/").append(p.getStartDate());
                cmd.append(" st/").append(p.getStartTime());
                cmd.append(" ed/").append(p.getEndDate());
                cmd.append(" et/").append(p.getEndTime());
            }
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         */
        AddressBook generateAddressBook(int numGenerated) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, numGenerated);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        AddressBook generateAddressBook(List<Item> items) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, items);
            return addressBook;
        }

        /**
         * Adds auto-generated Item objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         */
        void addToAddressBook(AddressBook addressBook, int numGenerated) throws Exception{
            addToAddressBook(addressBook, generateItemList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToAddressBook(AddressBook addressBook, List<Item> itemsToAdd) throws Exception{
            for(Item p: itemsToAdd){
                addressBook.addItem(p);
            }
        }

        /**
         * Adds auto-generated Item objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateItemList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Item> itemsToAdd) throws Exception{
            for(Item p: itemsToAdd){
                model.addItem(p);
            }
        }

        /**
         * Generates a list of Persons based on the flags.
         */
        List<Item> generateItemList(int numGenerated) throws Exception{
            List<Item> items = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                items.add(generateItem(i));
            }
            return items;
        }

        List<Item> generateItemList(Item... persons) {
            return Arrays.asList(persons);
        }

        /**
         * Generates a Item object with given name. Other fields will have some dummy values.
         */
        Item generateItemWithName(String name) throws Exception {
            return new Item(
                    new Type(name),
                    new Name("1"),
                    new TodoDate("2016-06-06"),
                    new TodoTime("18:00"),
                    new TodoDate("2016-07-07"),
                    new TodoTime("19:00"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
