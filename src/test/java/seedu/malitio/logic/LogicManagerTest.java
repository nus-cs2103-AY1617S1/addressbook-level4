package seedu.malitio.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.malitio.commons.core.EventsCenter;
import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.events.ui.ShowHelpRequestEvent;
import seedu.malitio.logic.Logic;
import seedu.malitio.logic.LogicManager;
import seedu.malitio.logic.commands.*;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.Model;
import seedu.malitio.model.ModelManager;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.*;
import seedu.malitio.storage.StorageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.malitio.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyMalitio latestSavedMalitio;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(MalitioChangedEvent abce) {
        latestSavedMalitio = new Malitio(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }
    
    @Before
    public void setup() {
        model = new ModelManager();
        String tempmalitioFile = saveFolder.getRoot().getPath() + "Tempmalitio.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempmalitioFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedMalitio = new Malitio(model.getMalitio()); // last saved assumed to be up to date before.
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
     * Both the 'malitio' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyMalitio, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new Malitio(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal malitio data are same as those in the {@code expectedmalitio} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedmalitio} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyMalitio expectedmalitio,
                                       List<? extends ReadOnlyFloatingTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredFloatingTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedmalitio, model.getMalitio());
        assertEquals(expectedmalitio, latestSavedMalitio);
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
        model.addFloatingTask(helper.generateTask(1));
        model.addFloatingTask(helper.generateTask(2));
        model.addFloatingTask(helper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new Malitio(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add Valid Name p/12345", expectedMessage);
    }

    @Test
    public void execute_add_invalidTask() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add dd//invalid ", expectedMessage);
        assertCommandBehavior(
                "add Valid t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        FloatingTask toBeAdded = helper.adam();
        Malitio expectedAB = new Malitio();
        expectedAB.addFloatingTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getFloatingTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        FloatingTask toBeAdded = helper.adam();
        Malitio expectedAB = new Malitio();
        expectedAB.addFloatingTask(toBeAdded);

        // setup starting state
        model.addFloatingTask(toBeAdded); // task already in internal Malitio

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getFloatingTaskList());

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Malitio expectedAB = helper.generateMalitio(2);
        List<? extends ReadOnlyFloatingTask> expectedList = expectedAB.getFloatingTaskList();

        // prepare malitio state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.ALL_MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
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
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<FloatingTask> floatingTaskList = helper.generateFloatingTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new Malitio());
        for (FloatingTask p : floatingTaskList) {
            model.addFloatingTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getMalitio(), floatingTaskList);
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
        List<FloatingTask> threeTasks = helper.generateFloatingTaskList(3);

        Malitio expectedAB = helper.generateMalitio(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete f2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getFloatingTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        FloatingTask pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        FloatingTask p1 = helper.generateTaskWithName("KE Y");
        FloatingTask p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<FloatingTask> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        Malitio expectedAB = helper.generateMalitio(fourTasks);
        List<FloatingTask> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask p1 = helper.generateTaskWithName("bla bla KEY bla");
        FloatingTask p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        FloatingTask p3 = helper.generateTaskWithName("key key");
        FloatingTask p4 = helper.generateTaskWithName("KEy sduauo");

        List<FloatingTask> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        Malitio expectedAB = helper.generateMalitio(fourTasks);
        List<FloatingTask> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        FloatingTask pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        FloatingTask pTarget3 = helper.generateTaskWithName("key key");
        FloatingTask p1 = helper.generateTaskWithName("sduauo");

        List<FloatingTask> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        Malitio expectedAB = helper.generateMalitio(fourTasks);
        List<FloatingTask> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

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
            Name task = new Name("Eat lunch");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new FloatingTask(task, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        FloatingTask generateTask(int seed) throws Exception {
            return new FloatingTask(
                    new Name("Task " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
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
         * Generates Malitio with auto-generated tasks.
         */
        Malitio generateMalitio(int numGenerated) throws Exception{
            Malitio malitio = new Malitio();
            addToMalitio(malitio, numGenerated);
            return malitio;
        }

        /**
         * Generates Malitio based on the list of Tasks given.
         */
        Malitio generateMalitio(List<FloatingTask> tasks) throws Exception{
            Malitio malitio = new Malitio();
            addToMalitio(malitio, tasks);
            return malitio;
        }

        /**
         * Adds auto-generated Task objects to the given Malitio
         * @param The malitio to which the Tasks will be added
         */
        void addToMalitio(Malitio malitio, int numGenerated) throws Exception{
            addToMalitio(malitio, generateFloatingTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given Malitio
         */
        void addToMalitio(Malitio malitio, List<FloatingTask> tasksToAdd) throws Exception{
            for(FloatingTask p: tasksToAdd){
                malitio.addFloatingTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateFloatingTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<FloatingTask> tasksToAdd) throws Exception{
            for(FloatingTask p: tasksToAdd){
                model.addFloatingTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<FloatingTask> generateFloatingTaskList(int numGenerated) throws Exception{
            List<FloatingTask> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<FloatingTask> generateTaskList(FloatingTask... floatingtasks) {
            return Arrays.asList(floatingtasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        FloatingTask generateTaskWithName(String name) throws Exception {
            return new FloatingTask(
                    new Name(name),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
