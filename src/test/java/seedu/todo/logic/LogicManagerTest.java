package seedu.todo.logic;

import com.google.common.eventbus.Subscribe;

import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.model.ToDoListChangedEvent;
import seedu.todo.commons.events.ui.JumpToListRequestEvent;
import seedu.todo.commons.events.ui.ShowHelpRequestEvent;
import seedu.todo.logic.commands.*;
import seedu.todo.model.Model;
import seedu.todo.model.ModelManager;
import seedu.todo.model.ReadOnlyToDoList;
import seedu.todo.model.ToDoList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.*;
import seedu.todo.storage.StorageManager;

import seedu.todo.testutil.TestDataHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.todo.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;
    private Config config;

    //These are for checking the correctness of the events raised
    private ReadOnlyToDoList latestSavedToDoList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(ToDoListChangedEvent abce) {
        latestSavedToDoList = new ToDoList(abce.data);
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
        logic = new LogicManager(model, config, new StorageManager(tempAddressBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedToDoList = new ToDoList(model.getToDoList()); // last saved assumed to be up to date before.
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
        model.addTask(helper.generateFullTask(1));
        model.addTask(helper.generateFullTask(2));
        model.addTask(helper.generateFullTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new ToDoList(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] on 12/15/2015 ; a line of details", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name on vdvd ; a line of details", TaskDate.MESSAGE_DATETIME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name on 12/12/1234 by asdasdsad ; a line of details", TaskDate.MESSAGE_DATETIME_CONSTRAINTS);

    }

    @Test
    public void execute_add_fullTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFullTask(0);
        ToDoList expectedAB = new ToDoList();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getName()),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_add_floatingTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFloatingTask(0);
        ToDoList expectedAB = new ToDoList();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getName()),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_add_deadlineTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateDeadlineTask(0);
        ToDoList expectedAB = new ToDoList();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded.getName()),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateFullTask(0);
        ToDoList expectedAB = new ToDoList();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }
    

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        ToDoList expectedAB = helper.generateToDoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("see",
                SeeCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
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
        List<Task> threeTasks = helper.generateTaskList(3);

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1).getName()),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_mark_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeMarked = helper.generateFullTask(0);
        ToDoList expectedAB = new ToDoList();
        expectedAB.addTask(toBeMarked);
        
        toBeMarked.setCompletion(new Completion(true));
        model.addTask(helper.generateFullTask(0));
        
        // execute command and verify result
        assertCommandBehavior("mark 1",
                String.format(MarkCommand.MESSAGE_SUCCESS, toBeMarked.getName()),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_markInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("mark", expectedMessage);
    }

    @Test
    public void execute_markIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("mark");
    }
    
    
    @Test
    public void execute_unmark_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeMarked = helper.generateFullTask(0);
        ToDoList expectedAB = new ToDoList();
        expectedAB.addTask(toBeMarked);
        
        Task toBeMarked2 = helper.generateFullTask(0);
        toBeMarked2.setCompletion(new Completion(true));
        toBeMarked2.addTag(new Tag("done"));
        model.addTask(toBeMarked2);        
       
        // execute command and verify result
        assertCommandBehavior("unmark 1",
                String.format(UnmarkCommand.MESSAGE_SUCCESS, toBeMarked.getName()),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_unmarkInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("unmark", expectedMessage);
    }

    @Test
    public void execute_unmarkIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("unmark");
    }
    
    @Test
    public void execute_tag_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeTagged = helper.generateFullTask(0);
        ToDoList expectedAB = new ToDoList();
        expectedAB.addTask(toBeTagged);
        
        toBeTagged.addTag(new Tag("yay"));
        
        Task toBeTagged2 = helper.generateFullTask(0);
        model.addTask(toBeTagged2);        
        
        // execute command and verify result
        assertCommandBehavior("tag 1 yay",
                String.format(TagCommand.MESSAGE_SUCCESS, toBeTagged.getName()),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_tagInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("tag", expectedMessage);
    }

    @Test
    public void execute_tagIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("tag");
    }
    
    
    @Test
    public void execute_untag_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeUntagged = helper.generateFullTask(0);
        ToDoList expectedAB = new ToDoList();
        expectedAB.addTask(toBeUntagged);
        
        Task toBeUntagged2 = helper.generateFullTask(0);
        toBeUntagged2.addTag(new Tag("yay"));
        model.addTask(toBeUntagged2);        
        
        // execute command and verify result
        assertCommandBehavior("untag 1 yay",
                String.format(UntagCommand.MESSAGE_SUCCESS, toBeUntagged.getName()),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_untagInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("untag", expectedMessage);
    }

    @Test
    public void execute_untagIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("untag");
    }
        

    @Test
    public void execute_search_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE);
        assertCommandBehavior("search ", expectedMessage);
    }

    @Test
    public void execute_search_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("search KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("search KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_search_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("search key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfTagPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithName("bla bla KEY bla");
        Task t2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        
        t1.addTag(new Tag("school"));

        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        ToDoList expectedAB = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateTaskList(t1);
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search tag school",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
  
    
    @Test
    public void execute_search_matchesIfBefore() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithName("bla bla KEY bla");
        Task t2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");

        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        ToDoList expectedAB = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateTaskList(t1, t2);
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search before 12/12/2019",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfAfter() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithName("bla bla KEY bla");
        Task t2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");

        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        ToDoList expectedAB = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateTaskList(t1, t2);
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search after 12/12/2013",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfFromTill() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithName("bla bla KEY bla");
        Task t2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");

        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        ToDoList expectedAB = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateTaskList(t1, t2);
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search from 12/12/2013 to 12/12/2019",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfDone() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithName("bla bla KEY bla");
        Task t2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        
        t1.setCompletion(new Completion(true));

        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        ToDoList expectedAB = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateTaskList(t1);
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search done",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    
    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyToDoList, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new ToDoList(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyToDoList expectedAddressBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getUnmodifiableFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getToDoList());
        assertEquals(expectedAddressBook, latestSavedToDoList);
    }
    
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) 
            throws Exception {
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
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new ToDoList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        if ("tag".equals(commandWord) || "untag".equals(commandWord)) {
            assertCommandBehavior(commandWord + " 3 hello", expectedMessage, model.getToDoList(), taskList);
        } else {
            assertCommandBehavior(commandWord + " 3", expectedMessage, model.getToDoList(), taskList);
        }
        
    }
    
}
