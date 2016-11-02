package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
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
import seedu.address.commons.exceptions.IllegalValueException;
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
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
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
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;
    private TaskBook expectedTB;
    private TestDataHelper helper;

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

        helper = new TestDataHelper();      
        expectedTB = new TaskBook();
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
                "add Valid Task Name 12345 d/Valid description date/11.11.11", Datetime.MESSAGE_DATETIME_CONTAINS_DOTS);
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
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded) + "\n" + AddCommand.MESSAGE_DUPLICATE_TASK,
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
        assertCommandBehavior(commandWord + " B 1", expectedMessage); //index should be typed together
        //assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " A0", expectedMessage); //index cannot be 0
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

        assertCommandBehavior(commandWord + " A3", expectedMessage, model.getTaskBook(), datedTaskList, undatedTaskList);
        assertCommandBehavior(commandWord + " A10", expectedMessage, model.getTaskBook(), datedTaskList, undatedTaskList);
        assertCommandBehavior(commandWord + " B3", expectedMessage, model.getTaskBook(), datedTaskList, undatedTaskList);
        assertCommandBehavior(commandWord + " B11", expectedMessage, model.getTaskBook(), datedTaskList, undatedTaskList);
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
        List<Task> threeUndatedTask = helper.generateUndatedTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook(threeUndatedTask);
        helper.addToModel(model, threeUndatedTask);

        assertCommandBehavior("select A2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, "A2"),
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredUndatedTaskList().get(1), threeUndatedTask.get(1));
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

        TaskBook expectedAB = helper.generateTaskBook(threeDatedTasks);
        helper.addToTaskBook(expectedAB, threeUndatedTasks);

        helper.addToModel(model, threeDatedTasks);
        helper.addToModel(model, threeUndatedTasks);
        expectedAB.removeTask(threeDatedTasks.get(1));

        assertCommandBehavior("delete B2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeDatedTasks.get(1)),
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());

        expectedAB.removeTask(threeUndatedTasks.get(1));
        assertCommandBehavior("delete A2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeUndatedTasks.get(1)),
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());
    }

    //@@author A0139145E
    @Test 
    public void execute_done_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }
    //@@author

    //@@author A0139145E
    @Test 
    public void execute_done_invalidIndexData() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
    }
    //@@author

  //@@author A0139145E
    @Test
    public void execute_done_alreadyCompleted() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> expectedDatedTasks = helper.generateTaskList(helper.deadlineA(), helper.eventA());
        List<Task> expectedUndatedTasks = helper.generateTaskList(helper.floatTaskA());
        TaskBook expectedAB = helper.generateTaskBook(expectedDatedTasks, expectedUndatedTasks);
        helper.addToModel(model, helper.generateTaskList(helper.deadlineA(), helper.eventA()));
        helper.addToModel(model, helper.generateTaskList(helper.floatTaskA()));

        Task completeDated = expectedDatedTasks.get(1);
        Task completeUndated = expectedUndatedTasks.get(0);
        expectedAB.completeTask(completeDated);
        expectedAB.completeTask(completeUndated);
        model.completeTask(completeDated);
        model.completeTask(completeUndated);

        assertCommandBehavior("list done",
                String.format(ListCommand.MESSAGE_SUCCESS, "completed"),
                expectedAB, Arrays.asList(completeDated),
                Arrays.asList(completeUndated));
        assertCommandBehavior("done A1",
                DoneCommand.MESSAGE_TASK_ALREADY_DONE,
                expectedAB, Arrays.asList(completeDated),
                Arrays.asList(completeUndated));
    }
    //@@author
    
    //@@author A0139145E
    @Test 
    public void execute_done_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> expectedDatedTasks = helper.generateTaskList(helper.deadlineA(), helper.eventA());
        List<Task> expectedUndatedTasks = helper.generateTaskList(helper.floatTaskA());
        TaskBook expectedAB = helper.generateTaskBook(expectedDatedTasks);
        helper.addToTaskBook(expectedAB, expectedUndatedTasks);

        List<Task> toAddDatedTasks = helper.generateTaskList(helper.deadlineA(), helper.eventA());
        List<Task> toAddUndatedTasks = helper.generateTaskList(helper.floatTaskA());
        helper.addToModel(model, toAddDatedTasks);
        helper.addToModel(model, toAddUndatedTasks);

        Task completeDated = expectedDatedTasks.get(1);
        Task completeUndated = expectedUndatedTasks.get(0);
        expectedAB.completeTask(completeDated);
        expectedDatedTasks = helper.generateTaskList(helper.deadlineA());

        assertCommandBehavior("done B2",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, completeDated),
                expectedAB, expectedDatedTasks,
                expectedUndatedTasks);

        expectedAB.completeTask(completeUndated);
        expectedUndatedTasks = helper.generateTaskList();
        assertCommandBehavior("done A1",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, completeUndated),
                expectedAB, expectedDatedTasks,
                expectedUndatedTasks);
    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_list_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_LIST_USAGE);
        assertCommandBehavior("list", expectedMessage);
        assertCommandBehavior("list none", expectedMessage);
        assertCommandBehavior("list all all", expectedMessage);
        assertCommandBehavior("list oddoneall", expectedMessage);
    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_listAll_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> expectedDatedTasks = helper.generateTaskList(helper.deadlineA(), helper.eventA());
        List<Task> expectedUndatedTasks = helper.generateTaskList(helper.floatTaskA());
        TaskBook expectedAB = helper.generateTaskBook(expectedDatedTasks, expectedUndatedTasks);
        helper.addToModel(model, helper.generateTaskList(helper.deadlineA(), helper.eventA()));
        helper.addToModel(model, helper.generateTaskList(helper.floatTaskA()));

        assertCommandBehavior("list all",
                String.format(ListCommand.MESSAGE_SUCCESS, "all"),
                expectedAB, expectedAB.getDatedTaskList(),
                expectedAB.getUndatedTaskList());

        Task completeUndated = expectedUndatedTasks.get(0);
        expectedAB.completeTask(completeUndated);
        model.completeTask(completeUndated);
        assertCommandBehavior("list all", String.format(ListCommand.MESSAGE_SUCCESS, "all"), 
                expectedAB, expectedDatedTasks, Collections.emptyList());

    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_listCompleted_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> expectedDatedTasks = helper.generateTaskList(helper.deadlineA(), helper.eventA());
        List<Task> expectedUndatedTasks = helper.generateTaskList(helper.floatTaskA());
        TaskBook expectedAB = helper.generateTaskBook(expectedDatedTasks, expectedUndatedTasks);
        helper.addToModel(model, helper.generateTaskList(helper.deadlineA(), helper.eventA()));
        helper.addToModel(model, helper.generateTaskList(helper.floatTaskA()));

        assertCommandBehavior("list done", String.format(ListCommand.MESSAGE_SUCCESS, "completed"),
                expectedAB, Collections.emptyList(), Collections.emptyList());

        Task completeDated = expectedDatedTasks.get(1);
        Task completeUndated = expectedUndatedTasks.get(0);
        expectedAB.completeTask(completeDated);
        expectedAB.completeTask(completeUndated);
        model.completeTask(completeDated);
        model.completeTask(completeUndated);

        assertCommandBehavior("list done",
                String.format(ListCommand.MESSAGE_SUCCESS, "completed"),
                expectedAB, Arrays.asList(completeDated),
                Arrays.asList(completeUndated));
    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_listOverdue_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> expectedDatedTasks = helper.generateTaskList(helper.deadlineA(), helper.eventA());
        List<Task> expectedUndatedTasks = helper.generateTaskList(helper.floatTaskA());
        helper.addToModel(model, helper.generateTaskList(helper.deadlineA(), helper.eventA()));
        helper.addToModel(model, helper.generateTaskList(helper.floatTaskA()));
        TaskBook expectedAB = new TaskBook();
        Task overdueDeadline = helper.overdueA();
        expectedAB.addTask(overdueDeadline);
        helper.addToTaskBook(expectedAB, expectedDatedTasks);
        helper.addToTaskBook(expectedAB, expectedUndatedTasks);
        
        model.addTask(overdueDeadline);
        List<ReadOnlyTask> expectedOverdue = new ArrayList<>();
        expectedOverdue.add(overdueDeadline);

        assertCommandBehavior("list od",
                String.format(ListCommand.MESSAGE_SUCCESS, "overdue and expired"),
                expectedAB, expectedOverdue,
                Collections.emptyList());

    }
    //@@author

    @Test
    public void execute_edit_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertCommandBehavior("edit ", expectedMessage);
    }
    
    //@@author A0143884W
    @Test
    public void execute_editName_successful() throws Exception {
        genericEdit("A1", 1, "new name");
    }

    @Test
    public void execute_editDescription_sucessful() throws Exception {
        genericEdit("A1", 2, "new description");
    }

    @Test
    public void execute_editDate_sucessful() throws Exception {
        genericEdit("A1", 3, "today");
    }
    
    @Test
    public void execute_view_successful() throws Exception {
    	List<Task> taskList = helper.generateDatedTaskList(9);
    	taskList.forEach(temp -> {
    		model.addTask(temp);
    	});
    	
    	assertViewCommand("tmr", 0);
    	assertViewCommand("12-Nov-2018", 1);
    	assertViewCommand("14 Nov 2018", 2);
    	assertViewCommand("16-11-2018", 3);
    }
    
    private void assertViewCommand(String date, int listSize) {
    	CommandResult result = logic.execute("view " + date);
    	assertEquals(logic.getFilteredDatedTaskList().size(), listSize);   
    }
    
    //@@author

    private void genericEdit(String index, int type, String field) throws Exception, DuplicateTaskException, IllegalValueException {
        // actual to be edited
        Task toBeEdited = helper.floatTaskA();
        toBeEdited.setTags(new UniqueTagList());
        model.addTask(toBeEdited);

        // expected result after edit
        // NOTE: can't simply set description of toBeEdited; need to create new copy,
        // since it will edit the task in model (model's task is simply a reference)
        Task edited = copyTask(toBeEdited);

        switch (type){
        case 1:
            edited.setName(new Name(field));
            break;
        case 2:
            edited.setDescription(new Description(field));
            break;	
        case 3:	
            edited.setDatetime(new Datetime(field));
            break;
        case 4:
            String [] StringArray = field.split(" ");
            Tag [] tagsArray = new Tag [StringArray.length];
            for (int i = 0; i < tagsArray.length; i++){
                tagsArray[i] = new Tag(StringArray[i]);
            }
            edited.setTags(new UniqueTagList(tagsArray));
            break;	
        }

        expectedTB.addTask(edited);

        // execute command and verify result
        assertCommandBehavior(helper.generateEditCommand(index, type, field),
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, edited),
                expectedTB, expectedTB.getDatedTaskList(),
                expectedTB.getUndatedTaskList());
    }

    private Task copyTask(Task toBeEdited){
        Task edited = new Task(toBeEdited.getName(), toBeEdited.getDescription(), toBeEdited.getDatetime(),
                toBeEdited.getStatus(), toBeEdited.getTags());
        return edited;
    }

    // TODO: currently, edits that don't include old tags removes all tags 
    // masterlist of tags in TaskBook also need to be changed
    @Test
    public void execute_edit_dated_successful() throws Exception {

        // initial task in actual model to be edited
        Task original = new Task (new Name("adam"), new Description("111111"),
                new Datetime("11-11-2018 1111"), new Status(State.NONE), 
                new UniqueTagList(new Tag("tag1"), new Tag("tag2")));

        model.addTask(original);

        String [] editInputs = new String [] {
                "edit B1 name changed t/tag1 t/tag2", // edit name
                "edit B1 d/change description too t/tag1 t/tag2", // edit description
                "edit B1 date/12-11-2018 1111 t/tag1 t/tag2", // edit date
                "edit B1 t/tag3 t/tag4", // edit tags
                "edit B1 date/ t/tag3 t/tag4" // edit dated -> undated
        };

        Task editedTasks [] = new Task [] {
                new Task (new Name("name changed"), new Description("111111"), new Datetime("11-11-2018 1111"),
                        new Status(State.NONE), new UniqueTagList(new Tag("tag1"), new Tag("tag2"))),
                new Task (new Name("name changed"), new Description("change description too"), new Datetime("11-11-2018 1111"),
                        new Status(State.NONE), new UniqueTagList(new Tag("tag1"), new Tag("tag2"))),
                new Task (new Name("name changed"), new Description("change description too"), new Datetime("12-11-2018 1111"),
                        new Status(State.NONE), new UniqueTagList(new Tag("tag1"), new Tag("tag2"))),
                new Task (new Name("name changed"), new Description("change description too"), new Datetime("12-11-2018 1111"),
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
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                expectedTB, expectedTB.getDatedTasks(),
                expectedTB.getUndatedTaskList());
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1a = helper.generateDatedTaskWithName("bla bla KEY bla");
        Task pTarget1b = helper.generateDatedTaskWithName("bla key bla");
        Task pTarget2a = helper.generateUndatedTaskWithName("bla KEY bla bceofeia");
        Task pTarget2b = helper.generateUndatedTaskWithName("bla key bceofeia");
        Task p1 = helper.generateDatedTaskWithName("KE Y");

        List<Task> threeDated = helper.generateTaskList(p1, pTarget1a, pTarget1b);
        List<Task> threeUndated = helper.generateTaskList(pTarget2a, pTarget2b);
        TaskBook expectedAB = new TaskBook();
        helper.addToTaskBook(expectedAB, threeUndated);
        helper.addToTaskBook(expectedAB, threeDated);
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


        List<Task> fourDated = helper.generateTaskList(p1, pTarget1a, pTarget1b, pTarget1c);
        List<Task> fourUndated = helper.generateTaskList(pTarget2a, pTarget2b, pTarget2c);
        TaskBook expectedAB = new TaskBook();
        helper.addToTaskBook(expectedAB, fourUndated);
        helper.addToTaskBook(expectedAB, fourDated);
        List<Task> expectedDatedTaskList = helper.generateTaskList(pTarget1a, pTarget1b, pTarget1c);
        List<Task> expectedUndatedTaskList = helper.generateTaskList(pTarget2a, pTarget2b, pTarget2c);
        helper.addToModel(model, fourUndated);
        helper.addToModel(model, fourDated);

        assertCommandBehavior("find key rAnDoM",
                (Command.getMessageForPersonListShownSummary(
                        expectedDatedTaskList.size()+expectedUndatedTaskList.size())),
                expectedAB, expectedDatedTaskList, expectedUndatedTaskList);
    }
    
  //@@author A0139145E
    @Test
    public void execute_undo_emptyStack() throws Exception {
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_NOT_POSSIBLE, new TaskBook(), 
                Collections.emptyList(), Collections.emptyList());
    }
    //@@author
    
    //@@author A0139145E
    @Test
    public void execute_undoAdd_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        helper.addToModel(model, 2);
        Task toUndo = helper.generateUndatedTaskWithName("Buy milk");

        expectedAB.addTask(toUndo);
        model.addTask(toUndo);
        expectedAB.removeTask(toUndo);
        model.addUndo("add", toUndo);
        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_SUCCESS, "add"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_NOT_POSSIBLE, expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_undoDelete_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        Task toUndo = new Task(expectedAB.getDatedTaskList().get(1));
        helper.addToModel(model, 2);    

        expectedAB.removeTask(toUndo);
        model.deleteTask(toUndo);
        expectedAB.addTask(toUndo);
        model.addUndo("delete", toUndo);
        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_SUCCESS, "delete"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_NOT_POSSIBLE, expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_undoDone_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        Task toUndo = new Task(expectedAB.getDatedTaskList().get(1));
        helper.addToModel(model, 2);    

        model.completeTask(toUndo);
        model.addUndo("done", toUndo);
        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_SUCCESS, "done"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_NOT_POSSIBLE, expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_undoEdit_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        helper.addToModel(model, 2);  

        Task orig = new Task(helper.generateDatedTaskWithName("Homework due"));
        expectedAB.addTask(orig);
        model.addTask(orig);
        Task edited = new Task(helper.generateDatedTaskWithName("Homework not due"));
        model.addTask(edited);
        model.deleteTask(orig);
        model.addUndo("edit", edited, orig);

        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_SUCCESS, "edit"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_NOT_POSSIBLE, expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_undoMultiple_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        Task toUndo = new Task(expectedAB.getDatedTaskList().get(0));
        helper.addToModel(model, 2);  

        expectedAB.removeTask(toUndo);
        model.deleteTask(toUndo);
        model.addUndo("delete", toUndo);
        model.addTask(toUndo);
        model.addUndo("add", toUndo);
        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_SUCCESS, "add"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
        expectedAB.addTask(toUndo);
        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_SUCCESS, "delete"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_NOT_POSSIBLE, expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
    }
    //@@author

    //@@author A0139145E
    @Test
    public void execute_redo_emptyStack() throws Exception {
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_NOT_POSSIBLE, new TaskBook(), 
                Collections.emptyList(), Collections.emptyList());
    }
    //@@author
    
    //@@author A0139145E
    @Test
    public void execute_redoUndoAdd_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        Task toUndo = helper.generateUndatedTaskWithName("Buy milk");
        helper.addToModel(model, 2);

        expectedAB.addTask(toUndo);
        model.addTask(toUndo);
        expectedAB.removeTask(toUndo);
        model.addUndo("add", toUndo);
        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_SUCCESS, "add"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
        
        expectedAB.addTask(toUndo);
        assertCommandBehavior("redo", String.format(RedoCommand.MESSAGE_SUCCESS, "add"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
    }
    //@@author
    
    //@@author A0139145E
    @Test
    public void execute_redoUndoDelete_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        Task toUndo = new Task(expectedAB.getDatedTaskList().get(1));
        helper.addToModel(model, 2);    

        expectedAB.removeTask(toUndo);
        model.deleteTask(toUndo);
        expectedAB.addTask(toUndo);
        model.addUndo("delete", toUndo);
        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_SUCCESS, "delete"), expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_NOT_POSSIBLE, expectedAB, 
                expectedAB.getDatedTaskList(), expectedAB.getUndatedTaskList());
    }
    //@@author
    
    //@@author A0139528W
    //@Test
    public void execute_save_successful() throws Exception {
        assertCommandBehavior(
                "save now\\here", SaveCommand.MESSAGE_SUCCESS);
        assertCommandBehavior(
                "save where/", SaveCommand.MESSAGE_SUCCESS);
        assertCommandBehavior(
                "save there/", SaveCommand.MESSAGE_SUCCESS);
        assertCommandBehavior(
                "save data", SaveCommand.MESSAGE_SUCCESS);
    }
    //@@author

    //@@author A0139528W
    @Test
    public void execute_save_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "save", expectedMessage);
        assertCommandBehavior(
                "save    ", expectedMessage);
        assertCommandBehavior(
                "save \t  \n   ", expectedMessage);
    }
    //@@author

    //@@author A0139528W 
    @Test
    public void execute_save_invalidFilePath() throws Exception {
        assertCommandBehavior(
                "save /here\\there", SaveCommand.MESSAGE_PATH_IS_NOT_A_DIRECTORY);
        assertCommandBehavior(
                "save //here", SaveCommand.MESSAGE_PATH_IS_NOT_A_DIRECTORY);
    }
    //@@author


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

        Task overdueA() throws Exception {
            Name name = new Name("File income tax");
            Description description = new Description("tax online portal");
            Datetime datetime = new Datetime("14-JAN-2015 11pm");
            Tag tag1 = new Tag("epic");
            Tag tag2 = new Tag("tax");
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
                            (seed%2==1) ? "1" + seed + "-NOV-2018 111" + seed
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
        String generateEditCommand(String index, int field, String params) {
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
                String [] tagsArray = params.split(" ");
                for(String t: tagsArray){
                    cmd.append(" t/").append(t);
                }
            }
            return cmd.toString();
        }

        /**
         * Generates an TaskBook with auto-generated tasks.
         */
        TaskBook generateTaskBook(int numGenerated) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, numGenerated);
            return taskBook;
        }

        /**
         * Generates an TaskBook based on the list of Tasks given.
         */
        TaskBook generateTaskBook(List<Task> tasks) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, tasks);
            return taskBook;
        }

        /**
         * Generates an TaskBook based on the lists of datedTasks and undatedTasks.
         * @param datedTasks list of dated tasks
         *        undatedTasks list of undated Tasks
         */
        TaskBook generateTaskBook(List<Task> datedTasks, List<Task> undatedTasks) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, datedTasks);
            addToTaskBook(taskBook, undatedTasks);
            return taskBook;
        }
        /**
         * Adds auto-generated Tasks objects to the given TaskBook
         * @param taskBook The TaskBook to which the Tasks will be added
         */
        void addToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
            addToTaskBook(taskBook, generateDatedTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskBook
         */
        void addToTaskBook(TaskBook taskBook, List<Task> taskToAdd) throws Exception{
            for(Task p: taskToAdd){
                taskBook.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateDatedTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of dated task based on the flags.
         */
        List<Task> generateDatedTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateDatedTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a list of undated task based on the flags.
         */
        List<Task> generateUndatedTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateUndatedTask(i));
            }
            return tasks;
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
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
