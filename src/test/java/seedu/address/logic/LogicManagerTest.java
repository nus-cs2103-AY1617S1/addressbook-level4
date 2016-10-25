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
import seedu.address.history.UndoableCommandHistoryManager;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.model.TaskManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.item.*;
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
    private UndoableCommandHistoryManager history;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedTaskManager = new TaskManager(abce.data);
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
        history = UndoableCommandHistoryManager.getInstance();
        String tempAddressBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempAddressBookFile, tempPreferencesFile), history);
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date before.
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
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredUndoneTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }
    
    /**
     * Executes the done command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing lists shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertDoneCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownUndoneList,
                                       List<? extends ReadOnlyTask> expectedShownDoneList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownUndoneList, model.getFilteredUndoneTaskList());
        assertEquals(expectedShownDoneList, model.getFilteredDoneTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }
    
    /**
     * Sends the inputCommand to the Logic component to generate a tooltip that will be compared against the expectedTooltip
     * 
     * @param inputCommand the user input
     * @param expectedTooltip expected tool tip to be shown to user
     */
    private void assertToolTipBehavior(String inputCommand, String expectedToolTip) {
        String generatedToolTip = logic.generateToolTip(inputCommand);
        assertEquals(expectedToolTip, generatedToolTip);
    }

    /* Test removed as we take add as a default command, so this will add an item with that name.
    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }
    */

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
        model.addTask(helper.generateFloatingTask(1));
        model.addTask(helper.generateFloatingTask(2));
        model.addTask(helper.generateFloatingTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }

    /*
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
    public void execute_add_invalidPersonData() throws Exception {
        /* Don't need to validate name?
        assertCommandBehavior(
                "add []\\[;] p/12345 e/valid@e.mail a/valid, address", Name.MESSAGE_NAME_CONSTRAINTS);
                */
        /* This test is failing because our program converts "-LOL" to "medium" priority. This test should be removed since it's
         * not an invalid person data.
        assertCommandBehavior(
                "add Valid Name -LOL", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));*/

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getUndoneTaskList());

    }

    /* Duplicate is allowed?
    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addFloatingTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_FLOATING_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }
    */


    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getUndoneTaskList();

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
        String expectedMessage = MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> personList = helper.generateFloatingTaskList(2);
        Collections.sort(personList);

        // set AB state to 2 persons
        model.resetData(new TaskManager());
        for (Task p : personList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskManager(), personList);
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
        List<Task> threePersons = helper.generateFloatingTaskList(3);
        Collections.sort(threePersons);

        TaskManager expectedAB = helper.generateTaskManager(threePersons);
        helper.addToModel(model, threePersons);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
                expectedAB,
                expectedAB.getUndoneTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredUndoneTaskList().get(1), threePersons.get(1));
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
        List<Task> threePersons = helper.generateFloatingTaskList(3);
        // TODO: check if this is ok
        Collections.sort(threePersons);

        TaskManager expectedAB = helper.generateTaskManager(threePersons);
        
        expectedAB.removeFloatingTask(threePersons.get(1));
        helper.addToModel(model, threePersons);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, threePersons.get(1)),
                expectedAB,
                expectedAB.getUndoneTaskList());
    }

    @Test
    public void execute_doneInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }
    
    @Test
    public void execute_done_archivesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generateFloatingTaskList(3);
        Collections.sort(threePersons);
        
        TaskManager expectedAB = helper.generateTaskManager(threePersons);
        
        expectedAB.removeFloatingTask(threePersons.get(1));
        expectedAB.addDoneTask(threePersons.get(1));
        helper.addToModel(model, threePersons);

        assertDoneCommandBehavior("done 2",
                String.format(DoneCommand.MESSAGE_DONE_ITEM_SUCCESS, threePersons.get(1)),
                expectedAB,
                expectedAB.getUndoneTaskList(),
                expectedAB.getDoneTaskList());
        
    }
    
    @Test
    public void execute_doneIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
    }
    

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateFloatingTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateFloatingTaskWithName("KE Y");
        Task p2 = helper.generateFloatingTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourPersons = helper.generateFloatingTaskList(p1, pTarget1, p2, pTarget2);
        Collections.sort(fourPersons);
        TaskManager expectedAB = helper.generateTaskManager(fourPersons);
        List<Task> expectedList = helper.generateFloatingTaskList(pTarget1, pTarget2);
        Collections.sort(expectedList);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateFloatingTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateFloatingTaskWithName("key key");
        Task p4 = helper.generateFloatingTaskWithName("KEy sduauo");

        List<Task> fourPersons = helper.generateFloatingTaskList(p3, p1, p4, p2);
        Collections.sort(fourPersons);
        TaskManager expectedAB = helper.generateTaskManager(fourPersons);
        List<Task> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateFloatingTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateFloatingTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateFloatingTaskWithName("key key");
        Task p1 = helper.generateFloatingTaskWithName("sduauo");

        List<Task> fourPersons = helper.generateFloatingTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedAB = helper.generateTaskManager(fourPersons);
        List<Task> expectedList = helper.generateFloatingTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    
    
    //TODO: do i need to have a test case for null string 
    @Test
    public void toolTip_invalidCommandInput_incorrectCommandTooltip() {
        assertToolTipBehavior("", MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT);
        assertToolTipBehavior("   ", MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT);    
    }
    
    
    @Test
    public void toolTip_commandBeginningSubstringsOfAdd_addToolTip() {
        assertToolTipBehavior("a", AddCommand.TOOL_TIP);
        assertToolTipBehavior("ad", AddCommand.TOOL_TIP);
        assertToolTipBehavior("add", AddCommand.TOOL_TIP);
        assertToolTipBehavior("add f", AddCommand.TOOL_TIP);
        assertToolTipBehavior("meet akshay at 1pm", AddCommand.TOOL_TIP);
        assertToolTipBehavior("do cs2103 tests", AddCommand.TOOL_TIP);
        assertToolTipBehavior("    add", AddCommand.TOOL_TIP);    
        assertToolTipBehavior("   lolo l ", AddCommand.TOOL_TIP);    
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfClear_clearToolTip() {
        assertToolTipBehavior("c", ClearCommand.TOOL_TIP);
        assertToolTipBehavior("cl", ClearCommand.TOOL_TIP);
        assertToolTipBehavior("cle", ClearCommand.TOOL_TIP);
        assertToolTipBehavior("clea", ClearCommand.TOOL_TIP);
        assertToolTipBehavior("clear", ClearCommand.TOOL_TIP);
        assertToolTipBehavior("clear a", ClearCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfDelete_deleteToolTip() {
        assertToolTipBehavior("d", DeleteCommand.TOOL_TIP + "\n" + DoneCommand.TOOL_TIP);
        assertToolTipBehavior("de", DeleteCommand.TOOL_TIP);
        assertToolTipBehavior("del", DeleteCommand.TOOL_TIP);
        assertToolTipBehavior("dele", DeleteCommand.TOOL_TIP);
        assertToolTipBehavior("delet", DeleteCommand.TOOL_TIP);
        assertToolTipBehavior("delete", DeleteCommand.TOOL_TIP);
        assertToolTipBehavior("delete 0", DeleteCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfDone_doneToolTip() {
        assertToolTipBehavior("d", DeleteCommand.TOOL_TIP + "\n" + DoneCommand.TOOL_TIP);
        assertToolTipBehavior("do", DoneCommand.TOOL_TIP);
        assertToolTipBehavior("don", DoneCommand.TOOL_TIP);
        assertToolTipBehavior("done", DoneCommand.TOOL_TIP);
        assertToolTipBehavior("done 0", DoneCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfEdit_editToolTip() {
        assertToolTipBehavior("e", EditCommand.TOOL_TIP + "\n" + ExitCommand.TOOL_TIP);
        assertToolTipBehavior("ed", EditCommand.TOOL_TIP);
        assertToolTipBehavior("edi", EditCommand.TOOL_TIP);
        assertToolTipBehavior("edit", EditCommand.TOOL_TIP);
        assertToolTipBehavior("edit 0", EditCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfExit_exitToolTip() {
        assertToolTipBehavior("e", EditCommand.TOOL_TIP + "\n" + ExitCommand.TOOL_TIP);
        assertToolTipBehavior("ex", ExitCommand.TOOL_TIP);
        assertToolTipBehavior("exi", ExitCommand.TOOL_TIP);
        assertToolTipBehavior("exit", ExitCommand.TOOL_TIP);
        assertToolTipBehavior("exit 0", ExitCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfFind_findToolTip() {
        assertToolTipBehavior("f", FindCommand.TOOL_TIP);
        assertToolTipBehavior("fi", FindCommand.TOOL_TIP);
        assertToolTipBehavior("fin", FindCommand.TOOL_TIP);
        assertToolTipBehavior("find", FindCommand.TOOL_TIP);
        assertToolTipBehavior("find 0", FindCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfHelp_helpToolTip() {
        assertToolTipBehavior("h", HelpCommand.TOOL_TIP);
        assertToolTipBehavior("he", HelpCommand.TOOL_TIP);
        assertToolTipBehavior("hel", HelpCommand.TOOL_TIP);
        assertToolTipBehavior("help", HelpCommand.TOOL_TIP);
        assertToolTipBehavior("help r", HelpCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfList_listToolTip() {
        assertToolTipBehavior("l", ListCommand.TOOL_TIP);
        assertToolTipBehavior("li", ListCommand.TOOL_TIP);
        assertToolTipBehavior("lis", ListCommand.TOOL_TIP);
        assertToolTipBehavior("list", ListCommand.TOOL_TIP);
        assertToolTipBehavior("list done", ListCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfRedo_redoToolTip() {
        assertToolTipBehavior("r", RedoCommand.TOOL_TIP);
        assertToolTipBehavior("re", RedoCommand.TOOL_TIP);
        assertToolTipBehavior("red", RedoCommand.TOOL_TIP);
        assertToolTipBehavior("redo", RedoCommand.TOOL_TIP);
        assertToolTipBehavior("redo done", RedoCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfSelect_selectToolTip() {
        assertToolTipBehavior("s", SelectCommand.TOOL_TIP);
        assertToolTipBehavior("se", SelectCommand.TOOL_TIP);
        assertToolTipBehavior("sel", SelectCommand.TOOL_TIP);
        assertToolTipBehavior("sele", SelectCommand.TOOL_TIP);
        assertToolTipBehavior("selec", SelectCommand.TOOL_TIP);
        assertToolTipBehavior("select", SelectCommand.TOOL_TIP);
        assertToolTipBehavior("select 1", SelectCommand.TOOL_TIP);
    }
    
    @Test
    public void toolTip_commandBeginningSubstringsOfUndo_undoToolTip() {
        assertToolTipBehavior("u", UndoCommand.TOOL_TIP);
        assertToolTipBehavior("un", UndoCommand.TOOL_TIP);
        assertToolTipBehavior("und", UndoCommand.TOOL_TIP);
        assertToolTipBehavior("undo", UndoCommand.TOOL_TIP);
        assertToolTipBehavior("undo done", UndoCommand.TOOL_TIP);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("Meet Adam Brown");
            Priority priority = Priority.LOW;
            return new Task(name, priority);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         */
        Task generateFloatingTask(int seed) throws Exception {
            Priority[] randomArr = {Priority.LOW, Priority.MEDIUM, Priority.HIGH};
            
            return new Task(
                    new Name("Task " + seed),
                    randomArr[seed%3]
            );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" -").append(p.getPriorityValue().toString().toLowerCase());

            return cmd.toString();
        }

        /**
         * Generates an TaskManager with auto-generated persons.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        TaskManager generateTaskManager(List<Task> floatingTasks) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, floatingTasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateFloatingTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToTaskManager(TaskManager addressBook, List<Task> floatingTasksToAdd) throws Exception{
            for(Task p: floatingTasksToAdd){
                addressBook.addTask(p);
            }
        }

        /**
         * Adds auto-generated Person objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateFloatingTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> floatingTasksToAdd) throws Exception{
            for(Task f: floatingTasksToAdd){
                model.addTask(f);
            }
        }

        /**
         * Generates a list of FloatingTask based on the flags.
         */
        List<Task> generateFloatingTaskList(int numGenerated) throws Exception{
            List<Task> floatingTasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                floatingTasks.add(generateFloatingTask(i));
            }
            return floatingTasks;
        }

        List<Task> generateFloatingTaskList(Task... floatingTasks) {
            return Arrays.asList(floatingTasks);
        }

        /**
         * Generates a FloatingTask object with given name. Other fields will have some dummy values.
         */
        Task generateFloatingTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    Priority.LOW
            );
        }
    }
}
