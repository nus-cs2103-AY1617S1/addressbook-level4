package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Datetime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Status.State;
import seedu.address.model.task.Task;
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
    private ReadOnlyTaskBook latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskBookChangedEvent abce) {
        latestSavedAddressBook = new TaskBook(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    // The annotation @Before marks a method to be executed before EACH test
    @Before
    public void setup() {
        model = new ModelManager();
        String tempTaskBookFile = saveFolder.getRoot().getPath() + "TempTaskBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new TaskBook(model.getTaskBook()); // last saved assumed to be up to date before.
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
        assertCommandBehavior(inputCommand, expectedMessage, new TaskBook(),
                Collections.emptyList(), Collections.emptyList());
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
            List<? extends ReadOnlyTask> expectedDatedList,
            List<? extends ReadOnlyTask> expectedUndatedList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedDatedList, model.getFilteredDatedTaskList());
        assertEquals(expectedUndatedList, model.getFilteredUndatedTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedAddressBook);
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
        model.addTask(helper.generateDatedTask(1));
        model.addTask(helper.generateDatedTask(2));
        model.addTask(helper.generateDatedTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBook(), 
                Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add Valid Task Name 12345 d/Valid description date/11.11.11", Datetime.MESSAGE_DATE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Task Name e/Wrong parameter for description date/tmr", expectedMessage);
        assertCommandBehavior(
                "add Valid Task Name d/Valid description dte/tmr", expectedMessage);
        assertCommandBehavior(
                "add Valid Task Name d/Valid description date/tmr tags/wrong_tag_prefix", expectedMessage);
    }

    @Test
    public void execute_add_invalidPersonData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] d/task description", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add []\\[;] d/task description date/11-11-2018 1111", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/can_be_anything date/ab-cd-ef", Datetime.MESSAGE_DATETIME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/can_be_anything date/11-11-2018 1111 t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/can_be_anything date/11-11-2018 1111 t/invalid tag", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        List<Task> toBeAdded = helper.generateTaskList(
                helper.eventA(), helper.deadlineA(), helper.floatTaskA());
        TaskBook expectedAB = new TaskBook();

        for (Task toAdd : toBeAdded) {
            expectedAB.addTask(toAdd);
            // execute command and verify result
            assertCommandBehavior(helper.generateAddCommand(toAdd),
                    String.format(AddCommand.MESSAGE_SUCCESS, toAdd),
                    expectedAB, expectedAB.getDatedTaskList(),
                    expectedAB.getUndatedTaskList());
        }

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.floatTaskA();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_PERSON,
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());

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
        List<Task> datedTaskList = helper.generateDatedTaskList(2);
        List<Task> undatedTaskList = helper.generateUndatedTaskList(2);

        // set total tasks state to 4 tasks
        model.resetData(new TaskBook());
        for (Task d : datedTaskList) {
            model.addTask(d);
        }
        for (Task u : undatedTaskList) {
            model.addTask(u);
        }

        assertCommandBehavior(commandWord + " 5", expectedMessage, model.getTaskBook(), datedTaskList, undatedTaskList);
        assertCommandBehavior(commandWord + " 10", expectedMessage, model.getTaskBook(), datedTaskList, undatedTaskList);
        assertCommandBehavior(commandWord + " 15", expectedMessage, model.getTaskBook(), datedTaskList, undatedTaskList);
        assertCommandBehavior(commandWord + " 21", expectedMessage, model.getTaskBook(), datedTaskList, undatedTaskList);
    }

    //@Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    //@Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    //@Test
    public void execute_select_jumpsToCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generateDatedTaskList(3);

        TaskBook expectedAB = helper.generateAddressBook(threePersons);
        helper.addToModel(model, threePersons);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredDatedTaskList().get(1), threePersons.get(1));
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
        List<Task> threeDatedTasks = helper.generateDatedTaskList(2);
        List<Task> threeUndatedTasks = helper.generateUndatedTaskList(3);

        TaskBook expectedAB = helper.generateAddressBook(threeDatedTasks);
        helper.addToAddressBook(expectedAB, threeUndatedTasks);

        helper.addToModel(model, threeDatedTasks);
        helper.addToModel(model, threeUndatedTasks);
        expectedAB.removeTask(threeDatedTasks.get(1));

        assertCommandBehavior("delete 12",
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, threeDatedTasks.get(1)),
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());

        expectedAB.removeTask(threeUndatedTasks.get(1));
        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, threeUndatedTasks.get(1)),
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());
    }

    @Test 
    public void execute_done_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }

    @Test 
    public void execute_done_invalidIndexData() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
    }

    @Test 
    public void execute_done_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> expectedDatedTasks = helper.generateTaskList(helper.deadlineA(), helper.eventA());
        List<Task> expectedUndatedTasks = helper.generateTaskList(helper.floatTaskA());
        TaskBook expectedAB = helper.generateAddressBook(expectedDatedTasks);
        helper.addToAddressBook(expectedAB, expectedUndatedTasks);
        
        List<Task> toAddDatedTasks = helper.generateTaskList(helper.deadlineA(), helper.eventA());
        List<Task> toAddUndatedTasks = helper.generateTaskList(helper.floatTaskA());
        helper.addToModel(model, toAddDatedTasks);
        helper.addToModel(model, toAddUndatedTasks);
        
        Task completeDated = expectedDatedTasks.get(1);
        Task completeUndated = expectedUndatedTasks.get(0);
        expectedAB.completeTask(completeDated);
        expectedDatedTasks = helper.generateTaskList(helper.deadlineA());
        
        assertCommandBehavior("done 12",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, completeDated),
                expectedAB, expectedDatedTasks,
                expectedUndatedTasks);

        expectedAB.completeTask(completeUndated);
        expectedUndatedTasks = helper.generateTaskList();
        assertCommandBehavior("done 1",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, completeUndated),
                expectedAB, expectedDatedTasks,
                expectedUndatedTasks);
    }
    
    @Test
    public void execute_edit_name_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.floatTaskA();
        TaskBook expectedAB = new TaskBook();

        // actual to be edited
        toBeEdited.setTags(new UniqueTagList());
        model.addTask(toBeEdited);

        // expected result after edit
        toBeEdited.setName(new Name("new name"));
        expectedAB.addTask(toBeEdited);

        // execute command and verify result
        assertCommandBehavior(helper.generateEditCommand(1, 1, "new name"),
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, toBeEdited),
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateDatedTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateUndatedTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateDatedTaskWithName("KE Y");
        Task p2 = helper.generateUndatedTaskWithName("KEYKEYKEY sduauo");

        List<Task> twoDated = helper.generateTaskList(p1, pTarget1);
        List<Task> twoUndated = helper.generateTaskList(p2, pTarget2);
        TaskBook expectedAB = new TaskBook();
        helper.addToAddressBook(expectedAB, twoUndated);
        helper.addToAddressBook(expectedAB, twoDated);
        List<Task> expectedDatedTaskList = helper.generateTaskList(pTarget1);
        List<Task> expectedUndatedTaskList = helper.generateTaskList(pTarget2);
        helper.addToModel(model, twoUndated);
        helper.addToModel(model, twoDated);

        assertCommandBehavior("find KEY",
                (Command.getMessageForPersonListShownSummary(
                        expectedDatedTaskList.size()+expectedUndatedTaskList.size())),
                expectedAB, expectedDatedTaskList, expectedUndatedTaskList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1a = helper.generateDatedTaskWithName("bla bla KEY bla");
        Task pTarget1b = helper.generateDatedTaskWithName("bla key bla");
        Task pTarget2a = helper.generateUndatedTaskWithName("bla KEY bla bceofeia");
        Task pTarget2b = helper.generateUndatedTaskWithName("bla key bceofeia");
        Task p1 = helper.generateDatedTaskWithName("KE Y");
        Task p2 = helper.generateUndatedTaskWithName("KEYKEYKEY sduauo");

        List<Task> threeDated = helper.generateTaskList(p1, pTarget1a, pTarget1b);
        List<Task> threeUndated = helper.generateTaskList(p2, pTarget2a, pTarget2b);
        TaskBook expectedAB = new TaskBook();
        helper.addToAddressBook(expectedAB, threeUndated);
        helper.addToAddressBook(expectedAB, threeDated);
        List<Task> expectedDatedTaskList = helper.generateTaskList(pTarget1a, pTarget1b);
        List<Task> expectedUndatedTaskList = helper.generateTaskList(pTarget2a, pTarget2b);
        helper.addToModel(model, threeUndated);
        helper.addToModel(model, threeDated);

        assertCommandBehavior("find KEY",
                (Command.getMessageForPersonListShownSummary(
                        expectedDatedTaskList.size()+expectedUndatedTaskList.size())),
                expectedAB, expectedDatedTaskList, expectedUndatedTaskList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1a = helper.generateDatedTaskWithName("bla bla KEY bla");
        Task pTarget1b = helper.generateDatedTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget1c = helper.generateDatedTaskWithName("key key");
        Task pTarget2a = helper.generateUndatedTaskWithName("bla bla KEY bla");
        Task pTarget2b = helper.generateUndatedTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget2c = helper.generateUndatedTaskWithName("key key");
        Task p1 = helper.generateDatedTaskWithName("KE Y");
        Task p2 = helper.generateUndatedTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourDated = helper.generateTaskList(p1, pTarget1a, pTarget1b, pTarget1c);
        List<Task> fourUndated = helper.generateTaskList(p2, pTarget2a, pTarget2b, pTarget2c);
        TaskBook expectedAB = new TaskBook();
        helper.addToAddressBook(expectedAB, fourUndated);
        helper.addToAddressBook(expectedAB, fourDated);
        List<Task> expectedDatedTaskList = helper.generateTaskList(pTarget1a, pTarget1b, pTarget1c);
        List<Task> expectedUndatedTaskList = helper.generateTaskList(pTarget2a, pTarget2b, pTarget2c);
        helper.addToModel(model, fourUndated);
        helper.addToModel(model, fourDated);

        assertCommandBehavior("find key rAnDoM",
                (Command.getMessageForPersonListShownSummary(
                        expectedDatedTaskList.size()+expectedUndatedTaskList.size())),
                expectedAB, expectedDatedTaskList, expectedUndatedTaskList);
    }

    // TODO: currently, edits that don't include old tags removes all tags 
    // masterlist of tags in TaskBook also need to be changed
    @Test
    public void execute_edit_dated_successful() throws Exception {

        // initial task in actual model to be edited
        Task original = new Task (new Name("adam"), new Description("111111"),
                new Datetime("11-11-2011 1111"), new Status(State.NONE), 
                new UniqueTagList(new Tag("tag1"), new Tag("tag2")));
        
        model.addTask(original);
        
    	String [] editInputs = new String [] {
    		"edit 11 name changed t/tag1 t/tag2", // edit name
    		"edit 11 d/change description too t/tag1 t/tag2", // edit description
    		"edit 11 date/12-11-2011 1111 t/tag1 t/tag2", // edit date
    		"edit 11 t/tag3 t/tag4", // edit tags
    		"edit 11 date/ t/tag3 t/tag4" // edit dated -> undated
    	};
    	
    	Task editedTasks [] = new Task [] {
                new Task (new Name("name changed"), new Description("111111"), new Datetime("11-11-2011 1111"),
                                new Status(State.NONE), new UniqueTagList(new Tag("tag1"), new Tag("tag2"))),
                new Task (new Name("name changed"), new Description("change description too"), new Datetime("11-11-2011 1111"),
                                new Status(State.NONE), new UniqueTagList(new Tag("tag1"), new Tag("tag2"))),
                new Task (new Name("name changed"), new Description("change description too"), new Datetime("12-11-2011 1111"),
                                new Status(State.NONE), new UniqueTagList(new Tag("tag1"), new Tag("tag2"))),
                new Task (new Name("name changed"), new Description("change description too"), new Datetime("12-11-2011 1111"),
                                new Status(State.NONE), new UniqueTagList(new Tag("tag3"), new Tag("tag4"))),
                new Task (new Name("name changed"), new Description("change description too"), new Datetime(""),
                                new Status(State.NONE), new UniqueTagList(new Tag("tag3"), new Tag("tag4")))
        };
    	
    	// state of the TaskBook after each edit
    	// for now it's simply editing a single person in the TaskBook
    	TaskBook [] expectedTaskBooks = new TaskBook [10];
    
    	for (int i = 0; i < 3; i++){
    		expectedTaskBooks[i] = new TaskBook();
    		expectedTaskBooks[i].addTask(editedTasks[i]);
    		execute_edit(editedTasks[i], expectedTaskBooks[i], editInputs[i]);
    	}
    }
    
    private void execute_edit(Task editedTask, TaskBook expectedTB, String editInput) throws Exception {
    	
        // execute command and verify result
        assertCommandBehavior(editInput,
                    String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTask),
                    expectedTB, expectedTB.getDatedTasks(),
                    expectedTB.getUndatedTaskList());
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task floatTaskA() throws Exception {
            Name name = new Name("Buy new water bottle");
            Description description = new Description("NTUC");
            Datetime datetime = new Datetime(null);
            Tag tag1 = new Tag("NTUC");
            Tag tag2 = new Tag("waterbottle");
            Status status = new Status(Status.State.NONE);
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, description, datetime, status, tags);
        }

        Task deadlineA() throws Exception {
            Name name = new Name("File income tax");
            Description description = new Description("tax online portal");
            Datetime datetime = new Datetime("14-JAN-2017 11pm");
            Tag tag1 = new Tag("epic");
            Tag tag2 = new Tag("tax");
            Status status = new Status(Status.State.NONE);
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, description, datetime, status, tags);
        }

        Task eventA() throws Exception {
            Name name = new Name("Jim party");
            Description description = new Description("Wave house");
            Datetime datetime = new Datetime("13-OCT-2017 6pm to 9pm");
            Tag tag1 = new Tag("jim");
            Tag tag2 = new Tag("party");
            Status status = new Status(Status.State.NONE);
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, description, datetime, status, tags);
        }

        /**
         * Generates a valid dated task using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         */
        Task generateDatedTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new Description("" + Math.abs(seed)),
                    new Datetime(
                            (seed%2==1) ? "11-NOV-201" + seed + " 111" + seed
                                    : "1" + seed + "-NOV-2018 111" + seed + " to 1" + seed + "-NOV-2019 111" + seed),
                    new Status(Status.State.NONE),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
                    );
        }

        /**
         * Generates a valid dated task using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         */
        Task generateUndatedTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new Description("" + Math.abs(seed)),
                    new Datetime(null),
                    new Status(Status.State.NONE),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
                    );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            if (!p.getDescription().toString().isEmpty()) {
                cmd.append(" d/").append(p.getDescription());
            }
            if (p.getDatetime() != null && !p.getDatetime().toString().isEmpty()) {
                cmd.append(" date/").append(p.getDatetime().toString());
            }

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /** Generates the correct edit command based on the person given */
        String generateEditCommand(int index, int field, String params) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("edit " + index);

            switch(field){
            case 1:
                cmd.append(" " + params);
                break;
            case 2:
                cmd.append(" d/").append(params);
                break;
            case 3:
                cmd.append(" date/").append(params);
                break;
            case 4:
                cmd.append(" time/").append(params);
                break;
            case 5:
                String [] tagsArray = params.split(" ");
                for(String t: tagsArray){
                    cmd.append(" t/").append(t);
                }
            }
            return cmd.toString();
        }

        /**
         * Generates an TaskBook with auto-generated persons.
         */
        TaskBook generateAddressBook(int numGenerated) throws Exception{
            TaskBook addressBook = new TaskBook();
            addToAddressBook(addressBook, numGenerated);
            return addressBook;
        }

        /**
         * Generates an TaskBook based on the list of Persons given.
         */
        TaskBook generateAddressBook(List<Task> persons) throws Exception{
            TaskBook addressBook = new TaskBook();
            addToAddressBook(addressBook, persons);
            return addressBook;
        }

        /**
         * Adds auto-generated Person objects to the given TaskBook
         * @param addressBook The TaskBook to which the Persons will be added
         */
        void addToAddressBook(TaskBook addressBook, int numGenerated) throws Exception{
            addToAddressBook(addressBook, generateDatedTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given TaskBook
         */
        void addToAddressBook(TaskBook addressBook, List<Task> personsToAdd) throws Exception{
            for(Task p: personsToAdd){
                addressBook.addTask(p);
            }
        }

        /**
         * Adds auto-generated Person objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateDatedTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> personsToAdd) throws Exception{
            for(Task p: personsToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of dated task based on the flags.
         */
        List<Task> generateDatedTaskList(int numGenerated) throws Exception{
            List<Task> persons = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                persons.add(generateDatedTask(i));
            }
            return persons;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a list of undated task based on the flags.
         */
        List<Task> generateUndatedTaskList(int numGenerated) throws Exception{
            List<Task> persons = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                persons.add(generateUndatedTask(i));
            }
            return persons;
        }

        /**
         * Generates a Person object with given name. Other fields will have some dummy values.
         */
        Task generateDatedTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Description("Dated task"),
                    new Datetime("10-NOV-2019 2359"),
                    new Status(Status.State.NONE),
                    new UniqueTagList(new Tag("tag"))
                    );
        }

        /**
         * Generates a Undated task with given name. Other fields will have some dummy values.
         */
        Task generateUndatedTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Description("Undated task"),
                    new Datetime(null),
                    new Status(Status.State.NONE),
                    new UniqueTagList(new Tag("tag"))
                    );
        }

    }
}
