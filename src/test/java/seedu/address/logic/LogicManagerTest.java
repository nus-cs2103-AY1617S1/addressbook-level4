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
import seedu.address.model.TaskBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
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
    private ReadOnlyTaskBook latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(AddressBookChangedEvent abce) {
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

    @Before
    public void setup() {
        model = new ModelManager();
        String tempAddressBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempAddressBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new TaskBook(model.getAddressBook()); // last saved assumed to be up to date before.
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
                                       ReadOnlyTaskBook expectedAddressBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

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
        model.addTask(helper.generatePerson(1));
        model.addTask(helper.generatePerson(2));
        model.addTask(helper.generatePerson(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBook(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add", expectedMessage);
    }

    @Test
    public void execute_add_invalidPersonData() throws Exception {
        assertCommandBehavior(
                "add [events;]", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add deadline; 123123", Date.MESSAGE_DATE_CONSTRAINTS);
        assertCommandBehavior(
                "add [events; 121212; 5555]", Start.MESSAGE_START_CONSTRAINTS);
        assertCommandBehavior(
                "add deadlines; 121212; 12.30am #..", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addPerson(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.EVENT_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getPersonList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addPerson(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getPersonList());

    }


    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateAddressBook(2, 2, 2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getPersonList();

        // prepare address book state
        helper.addToModel(model, 2, 2, 2);

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
        assertCommandBehavior(commandWord + " 1", expectedMessage); //index cannot just be a number (must have E/D/T in front)
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
        List<Task> personList = helper.generatePersonList(2);

        // set AB state to 2 persons
        model.resetData(new TaskBook());
        for (Task p : personList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getAddressBook(), personList);
    }

   /* @Test
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
        List<Task> threePersons = helper.generatePersonList(3);

        TaskBook expectedAB = helper.generateAddressBook(threePersons);
        helper.addToModel(model, threePersons);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
                expectedAB,
                expectedAB.getPersonList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredPersonList().get(1), threePersons.get(1));
    }
*/

    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete E5");
    }

    @Test
    public void execute_delete_removesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removePerson(threePersons.get(1));
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

<<<<<<< HEAD
        assertCommandBehavior("delete 2",
=======
        assertCommandBehavior("delete E2",
>>>>>>> new_add_branch
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threePersons.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesPartialWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Task pTarget2 = helper.generatePersonWithName("KEYKEYKEY sduauo");
        Task p1 = helper.generatePersonWithName("KE Y");
        Task p2 = helper.generatePersonWithName("blaK EY bla bceofeia");
        
        List<Task> fourPersons = helper.generatePersonList(p1, p2, pTarget1, pTarget2);
        List<Task> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        
        Task DTarget1 = helper.generateDeadlineWithName("bla KEY bla");
        Task DTarget2 = helper.generateDeadlineWithName("KEYKEY sduauo");
        Task D1 = helper.generateDeadlineWithName("K EY");
        Task D2 = helper.generateDeadlineWithName("blaK EY bla bcfeia");
        
        List<Task> fourDeadlines = helper.generateDeadlineList(D1, D2, DTarget1, DTarget2);
        List<Task> expectedDeadlineList = helper.generateDeadlineList(DTarget1, DTarget2);

        Task TTarget1 = helper.generateTodoWithName("bla bla KEY");
        Task TTarget2 = helper.generateTodoWithName("KEYKEYKEY sduo");
        Task T1 = helper.generateTodoWithName("KE Y dfd");
        Task T2 = helper.generateTodoWithName("blaK EYceofeia");
        
        List<Task> fourTodos = helper.generateTodoList(T1, T2, TTarget1, TTarget2);
        List<Task> expectedTodoList = helper.generateTodoList(TTarget1, TTarget2);
        helper.addToModel(model, fourPersons, fourDeadlines, fourTodos);
        
        TaskBook expectedAB = helper.generateAddressBook(fourPersons, fourDeadlines, fourTodos);
        
        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size(), expectedDeadlineList.size(), expectedTodoList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generatePersonWithName("bla bla KEY bla");
        Task p2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Task p3 = helper.generatePersonWithName("key key");
        Task p4 = helper.generatePersonWithName("KEy sduauo");

        Task d1 = helper.generateDeadlineWithName("bla KEY bla");
        Task d2 = helper.generateDeadlineWithName("bla KEY bceofeia");
        Task d3 = helper.generateDeadlineWithName("ke key");
        Task d4 = helper.generateDeadlineWithName("KEy uauo");

        Task t1 = helper.generateTodoWithName("blla KEY bla");
        Task t2 = helper.generateTodoWithName("bla KEYofeia");
        Task t3 = helper.generateTodoWithName("key");
        Task t4 = helper.generateTodoWithName("KEy skeyduauo");

        List<Task> fourEvents = helper.generatePersonList(p3, p1, p4, p2);
        List<Task> fourDeadlines = helper.generateDeadlineList(d3, d1, d4, d2);
        List<Task> fourTodos = helper.generateTodoList(t3, t1, t4, t2);
        TaskBook expectedAB = helper.generateAddressBook(fourEvents, fourDeadlines, fourTodos);
        List<Task> expectedList = fourEvents;
        List<Task> expectedDeadlineList = fourDeadlines;
        List<Task> expectedTodoList = fourTodos;
        helper.addToModel(model, fourEvents, fourDeadlines, fourTodos);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size(), expectedDeadlineList.size(), expectedTodoList.size()),
                expectedAB,
                expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("Events");
            Date date = new Date("12.12.16");
            Start start = new Start("2am");
            End end = new End("3pm");
            int task_cat = 1;
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, date, start, end, task_cat, tags);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the person data field values
         */
        Task generatePerson(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new Date("12121" + Math.abs(seed)),
                    new Start("125" + seed),
                    new End("235" + seed),
                    1,
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /**
         * Generates a valid deadline using the given seed.
         * Running this function with the same parameter values guarantees the returned deadline will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the deadline data field values
         */
        Task generateDeadline(int seed) throws Exception {
            return new Task(
                    new Name("DTask " + seed),
                    new Date("12121" + Math.abs(seed)),
                    new Start("no start"),
                    new End("235" + seed),
                    2,
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }
        /**
         * Generates a valid deadline using the given seed.
         * Running this function with the same parameter values guarantees the returned deadline will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the deadline data field values
         */
        Task generateTodo(int seed) throws Exception {
            return new Task(
                    new Name("TTask " + seed),
                    new Date("no date"),
                    new Start("no start"),
                    new End("no end"),
                    3,
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }


        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            cmd.append("[").append(p.getName().toString());
            cmd.append("; ").append(p.getDate());
            cmd.append("; ").append(p.getStart());
            cmd.append("; ").append(p.getEnd());
            cmd.append("]");
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         */
        TaskBook generateAddressBook(int numGenerated, int numGenerated2, int numGenerated3) throws Exception{
            TaskBook addressBook = new TaskBook();
            addToAddressBook(addressBook, numGenerated, numGenerated2, numGenerated3);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        TaskBook generateAddressBook(List<Task> persons, List<Task> deadlines, List<Task> todos) throws Exception{
            TaskBook addressBook = new TaskBook();
            addToAddressBook(addressBook, persons, deadlines, todos);
            return addressBook;
        }

        /**
         * Adds auto-generated Task objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         */
        void addToAddressBook(TaskBook addressBook, int numGenerated, int numGenerated2, int numGenerated3) throws Exception{
            addToAddressBook(addressBook, generatePersonList(numGenerated), generateDeadlineList(numGenerated2), generateTodoList(numGenerated3));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToAddressBook(TaskBook addressBook, List<Task> personsToAdd, List<Task> deadlinesToAdd, List<Task> todoToAdd) throws Exception{
            for(Task p: personsToAdd){
                addressBook.addPerson(p);
            }
            for(Task p: deadlinesToAdd){
                addressBook.addPerson(p);
            }
            for(Task p: todoToAdd){
                addressBook.addPerson(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated, int numGenerated2, int numGenerated3) throws Exception{
            addToModel(model, generatePersonList(numGenerated), generateDeadlineList(numGenerated2), generateTodoList(numGenerated3));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> personsToAdd, List<Task> deadlinesToAdd, List<Task> todosToAdd) throws Exception{
            for(Task p: personsToAdd){
                model.addTask(p);
            }
            for(Task p: deadlinesToAdd){
                model.addTask(p);
            }
            for(Task p: todosToAdd){
                model.addTask(p);
            }

        }

        /**
         * Generates a list of Persons based on the flags.
         */
        List<Task> generatePersonList(int numGenerated) throws Exception{
            List<Task> persons = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                persons.add(generatePerson(i));
            }
            return persons;
        }

        List<Task> generatePersonList(Task... persons) {
            return Arrays.asList(persons);
        }
        
        /*
         * Generates a list of deadlines based on flags.
         */
        List<Task> generateDeadlineList(int numGenerated) throws Exception{
            List<Task> deadlines = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                deadlines.add(generateDeadline(i));
            }
            return deadlines;
        }

        List<Task> generateDeadlineList(Task... deadlines) {
            return Arrays.asList(deadlines);
        }

        /*
         * Generates a list of todos based on flags.
         */
        List<Task> generateTodoList(int numGenerated) throws Exception{
            List<Task> todos = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                todos.add(generateTodo(i));
            }
            return todos;
        }

        List<Task> generateTodoList(Task... todo) {
            return Arrays.asList(todo);
        }

        /**
         * Generates a Task event object with given name. Other fields will have some dummy values.
         */
        Task generatePersonWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Date("31/12/16"),
                    new Start("12.21am"),
                    new End("1300"),
                    1,
                    new UniqueTagList(new Tag("tag"))
            );
        }
        
        /**
         * Generates a Task deadline object with given name. Other fields will have some dummy values.
         */
        Task generateDeadlineWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Date("31/12/16"),
                    new Start("no start"),
                    new End("1300"),
                    2,
                    new UniqueTagList(new Tag("tag"))
            );
        }
        
        /**
         * Generates a Task todo object with given name. Other fields will have some dummy values.
         */
        Task generateTodoWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Date("no date"),
                    new Start("no start"),
                    new End("no end"),
                    3,
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
