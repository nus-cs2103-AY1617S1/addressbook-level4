package seedu.taskmanager.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmanager.commons.events.ui.ShowHelpRequestEvent;
import seedu.taskmanager.logic.Logic;
import seedu.taskmanager.logic.LogicManager;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.ClearCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.CommandResult;
import seedu.taskmanager.logic.commands.DeleteCommand;
import seedu.taskmanager.logic.commands.ExitCommand;
import seedu.taskmanager.logic.commands.FindCommand;
import seedu.taskmanager.logic.commands.HelpCommand;
import seedu.taskmanager.logic.commands.ListCommand;
import seedu.taskmanager.logic.commands.SelectCommand;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.Model;
import seedu.taskmanager.model.ModelManager;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.item.ItemDate;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.ItemTime;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.storage.StorageManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedAddressBook = new TaskManager(abce.data);
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
    public void setUp() {
        model = new ModelManager();
        String tempAddressBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempAddressBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void tearDown() {
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
                                       ReadOnlyTaskManager expectedAddressBook,
                                       List<? extends ReadOnlyItem> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredItemList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getTaskManager());
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
        model.addItem(helper.generateItem(1), String.format(AddCommand.MESSAGE_SUCCESS, helper.generateItem(1)));
        model.addItem(helper.generateItem(2), String.format(AddCommand.MESSAGE_SUCCESS, helper.generateItem(2)));
        model.addItem(helper.generateItem(3), String.format(AddCommand.MESSAGE_SUCCESS, helper.generateItem(3)));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        // Missing Prefix
        assertCommandBehavior(
        		"add event n/12345 ed/2016-08-08 et/18:00", expectedMessage);
        // Additional Prefix
        // assertCommandBehavior(
        // 		"add task n/12345 ed/2016-08-08 et/18:00", expectedMessage);
        // Have Name Prefix
        // assertCommandBehavior(
        //         "add deadline n/12345 ed/2016-08-08 et/18:00", expectedMessage);
        // No EndDate Prefix
        assertCommandBehavior(
                "add deadline n/12345 2016-08-08 et/18:00", expectedMessage);
        // No EndTime Prefix
        // assertCommandBehavior(
        //         "add deadline 12345 ed/2016-08-08 18:00", expectedMessage);
    }

    @Test
    public void execute_add_invalidItemData() throws Exception {
        // Invalid ItemType
        assertCommandBehavior(
                "add []\\[;] n/12345 ed/2016-08-08 et/18:00", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        // Invalid Name
        assertCommandBehavior(
                "add deadline n/# ed/2016-08-08 et/18:00", Name.MESSAGE_NAME_CONSTRAINTS);
        // Invalid EndDate
        assertCommandBehavior(
                "add deadline n/12345 ed/notADate et/18:00", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDate.MESSAGE_DATE_CONSTRAINTS));
        // Invalid EndTime
        assertCommandBehavior(
                "add deadline n/12345 ed/2016-08-08 et/notATime", ItemTime.MESSAGE_TIME_CONSTRAINTS);
        // Invalid Tag
        assertCommandBehavior(
                "add deadline n/12345 ed/2016-08-08 et/18:00 #invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
        // Invalid Event endDate and endTime
        assertCommandBehavior(
                "add event n/12345 sd/2016-08-08 st/19:00 ed/2016-08-08 et/18:00", String.format(MESSAGE_INVALID_COMMAND_FORMAT, Command.MESSAGE_END_DATE_TIME_BEFORE_START_DATE_TIME));
       // Invalid Date
        assertCommandBehavior(
                "add event n/12345 sd/2016-02-30 st/19:00 ed/2016-08-08 et/18:00", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDate.MESSAGE_DATE_CONSTRAINTS));
       // Invalid Date
        assertCommandBehavior(
                "add deadline n/12345 ed/2016-11-31 et/18:00", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDate.MESSAGE_DATE_CONSTRAINTS));

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Item toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addItem(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getItemList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Item toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addItem(toBeAdded);

        // setup starting state
        model.addItem(toBeAdded, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded)); // item already in internal task manager

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_ITEM,
                expectedAB,
                expectedAB.getItemList());

    }


    @Test
    public void execute_list_showsAllItems() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyItem> expectedList = expectedAB.getItemList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single item in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single item in the last shown list based on visible index.
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
     * targeting a single item in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single item in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Item> itemList = helper.generateItemList(2);

        // set AB state to 2 items
        model.resetData(new TaskManager(), ClearCommand.MESSAGE_SUCCESS);
        for (Item p : itemList) {
            model.addItem(p, String.format(AddCommand.MESSAGE_SUCCESS, p));
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskManager(), itemList);
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
    public void execute_select_jumpsToCorrectItem() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Item> threeItems = helper.generateItemList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeItems);
        helper.addToModel(model, threeItems);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_ITEM_SUCCESS, 2),
                expectedAB,
                expectedAB.getItemList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredItemList().get(1), threeItems.get(1));
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
    public void execute_delete_removesCorrectItem() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Item> threeItems = helper.generateItemList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeItems);
        expectedAB.removeItem(threeItems.get(1));
        helper.addToModel(model, threeItems);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, threeItems.get(1)),
                expectedAB,
                expectedAB.getItemList());
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
        TaskManager expectedAB = helper.generateTaskManager(fourItems);
        List<Item> expectedList = helper.generateItemList(pTarget1, pTarget2);
        helper.addToModel(model, fourItems);

        assertCommandBehavior("find KEY",
                Command.getMessageForItemListShownSummary(expectedList.size()),
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
        TaskManager expectedAB = helper.generateTaskManager(fourItems);
        List<Item> expectedList = fourItems;
        helper.addToModel(model, fourItems);

        assertCommandBehavior("find KEY",
                Command.getMessageForItemListShownSummary(expectedList.size()),
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
        TaskManager expectedAB = helper.generateTaskManager(fourItems);
        List<Item> expectedList = helper.generateItemList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourItems);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForItemListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Item adam() throws Exception {
            ItemType itemType = new ItemType("deadline");
            Name privateName = new Name("111111");
            ItemDate startDate = new ItemDate("");
            ItemTime startTime = new ItemTime("");
            ItemDate endDate = new ItemDate("2016-08-08");
            ItemTime endTime = new ItemTime("01:59");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Item(itemType, privateName, startDate, startTime, endDate, endTime, tags);
        }

        /**
         * Generates a valid item using the given seed.
         * Running this function with the same parameter values guarantees the returned item will have the same state.
         * Each unique seed will generate a unique Item object.
         *
         * @param seed used to generate the item data field values
         */
        Item generateItem(int seed) throws Exception {
            String dateFormat = "MM-dd";
            String timeFormat = "HH:mm";
            LocalDateTime ldt = LocalDateTime.now();
            String startDate = ldt.format(DateTimeFormatter.ofPattern(dateFormat));
            String startTime = ldt.format(DateTimeFormatter.ofPattern(timeFormat));
            LocalDateTime after = ldt.plusHours(12);
            String endDate = after.format(DateTimeFormatter.ofPattern(dateFormat));
            String endTime = after.format(DateTimeFormatter.ofPattern(timeFormat));
            String itemTypes[] = {"task", "deadline", "event"};
            String itemType = itemTypes[seed%3];
            if (itemType == ItemType.TASK_WORD) {
                return new Item(
                    new ItemType(itemType),
                    new Name("" + Math.abs(seed)),
                    new ItemDate(""),
                    new ItemTime(""),
                    new ItemDate(""),
                    new ItemTime(""),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
                );
            } else if (itemType == ItemType.DEADLINE_WORD) {
                return new Item(
                    new ItemType(itemType),
                    new Name("" + Math.abs(seed)),
                    new ItemDate(""),
                    new ItemTime(""),
                    new ItemDate(endDate),
                    new ItemTime(endTime),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
                );
            } else {
                return new Item(
                    new ItemType(itemType),
                    new Name("" + Math.abs(seed)),
                    new ItemDate(startDate),
                    new ItemTime(startTime),
                    new ItemDate(endDate),
                    new ItemTime(endTime),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
                );
            }
        }

        /** Generates the correct add command based on the item given */
        String generateAddCommand(Item p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            
            if (p.getItemType().toString().equals(ItemType.TASK_WORD)) {
                cmd.append(p.getItemType().toString());
                cmd.append(" n/").append(p.getName());
            } else if (p.getItemType().toString().equals(ItemType.DEADLINE_WORD)) {
                cmd.append(p.getItemType().toString());
                cmd.append(" n/").append(p.getName());
                cmd.append(" ed/").append(p.getEndDate());
                cmd.append(" et/").append(p.getEndTime());
            } else if (p.getItemType().toString().equals(ItemType.EVENT_WORD)) {
                cmd.append(p.getItemType().toString());
                cmd.append(" n/").append(p.getName());
                cmd.append(" sd/").append(p.getStartDate());
                cmd.append(" st/").append(p.getStartTime());
                cmd.append(" ed/").append(p.getEndDate());
                cmd.append(" et/").append(p.getEndTime());
            }

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }

        //@@author A0140060A-reused
        /**
         * Generates a TaskManager with auto-generated items.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates a TaskManager based on the list of Items given.
         */
        TaskManager generateTaskManager(List<Item> items) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, items);
            return taskManager;
        }

        /**
         * Adds auto-generated Item objects to the given TaskManager
         * @param taskManager The TaskManager to which the Items will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateItemList(numGenerated));
        }

        /**
         * Adds the given list of Items to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Item> itemsToAdd) throws Exception{
            for(Item p: itemsToAdd){
                taskManager.addItem(p);
            }
        }

        //@@author A0140060A
        /**
         * Adds auto-generated Item objects to the given model
         * @param model The model to which the Items will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateItemList(numGenerated));
        }

        /**
         * Adds the given list of Items to the given model
         */
        void addToModel(Model model, List<Item> itemsToAdd) throws Exception{
            for(Item p: itemsToAdd){
                model.addItem(p, String.format(AddCommand.MESSAGE_SUCCESS, p));
            }
        }
        
        //@@author A0140060A-reused
        /**
         * Generates a list of Items based on the flags.
         */
        List<Item> generateItemList(int numGenerated) throws Exception{
            List<Item> items = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                items.add(generateItem(i));
            }
            return items;
        }

        List<Item> generateItemList(Item... items) {
            return Arrays.asList(items);
        }

        /**
         * Generates a Item object with given name. Other fields will have some dummy values.
         */
        Item generateItemWithName(String name) throws Exception {
        	String itemType = "deadline";
            return new Item(
                    new ItemType(itemType),
                    new Name(name),
                    new ItemDate(""),
                    new ItemTime(""),
                    new ItemDate("2016-12-15"),
                    new ItemTime("01:39"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
