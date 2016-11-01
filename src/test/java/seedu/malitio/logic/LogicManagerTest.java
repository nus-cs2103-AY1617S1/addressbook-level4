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
import seedu.malitio.logic.parser.Parser;
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
        assertCommandBehavior(inputCommand, expectedMessage, new Malitio(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
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
                                       List<? extends ReadOnlyFloatingTask> expectedTaskShownList, 
                                       List<? extends ReadOnlyDeadline> expectedDeadlineShownList,
                                       List<? extends ReadOnlyEvent> expectedEventShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedTaskShownList, model.getFilteredFloatingTaskList());
        assertEquals(expectedDeadlineShownList, model.getFilteredDeadlineList());
        assertEquals(expectedEventShownList, model.getFilteredEventList());

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
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));
        model.addTask(helper.generateDeadline(1));
        model.addTask(helper.generateDeadline(2));
        model.addTask(helper.generateDeadline(3));
        model.addTask(helper.generateEvent(1));
        model.addTask(helper.generateEvent(2));
        model.addTask(helper.generateEvent(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new Malitio(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
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
    
    //@@author A0129595N
    @Test
    public void execute_add_invalidDeadline() throws Exception {
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        assertCommandBehavior(
                "add do this by todayyy", expectedMessage);
    }
    
    @Test
    public void execute_add_invalidEvent() throws Exception {
        String expectedMessage = Event.MESSAGE_INVALID_EVENT;
        assertCommandBehavior(
                "add do now start today end yesterday", expectedMessage);        
    }
    
    @Test
    public void execute_add_unclearTask() throws Exception {
        String expectedMessage = Parser.MESSAGE_CONFLICTING_ARG;
        assertCommandBehavior(
                "add do now by today start tomorrow", expectedMessage);
        assertCommandBehavior(
                "add do now by today end tomorrow", expectedMessage);       
    }
   

    /**
     * Test to make sure all three types of task can be added
     * @throws Exception
     */
    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        FloatingTask floatingTaskToBeAdded = helper.sampleFloatingTask();
        Deadline deadlineToBeAdded = helper.sampleDeadline();
        Event eventToBeAdded = helper.sampleEvent();
        Malitio expectedAB = new Malitio();
        expectedAB.addTask(floatingTaskToBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(floatingTaskToBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, floatingTaskToBeAdded),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());

        expectedAB.addTask(deadlineToBeAdded);
        assertCommandBehavior(helper.generateAddCommand(deadlineToBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, deadlineToBeAdded),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
        
        expectedAB.addTask(eventToBeAdded);
        assertCommandBehavior(helper.generateAddCommand(eventToBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, eventToBeAdded),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        FloatingTask floatingTaskToBeAdded = helper.sampleFloatingTask();
        Deadline deadlineToBeAdded = helper.sampleDeadline();
        Event eventToBeAdded = helper.sampleEvent();
        Malitio expectedAB = new Malitio();
        expectedAB.addTask(floatingTaskToBeAdded);
        expectedAB.addTask(deadlineToBeAdded);
        expectedAB.addTask(eventToBeAdded);

        // setup starting state
        model.addTask(floatingTaskToBeAdded); // floating task already in internal Malitio
        model.addTask(deadlineToBeAdded); // deadline already in internal Malitio
        model.addTask(eventToBeAdded); // event already in internal Malitio

        // execute command and verify result for floating task
        assertCommandBehavior(
                helper.generateAddCommand(floatingTaskToBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
        
        // execute command and verify result for deadline
        assertCommandBehavior(
                helper.generateAddCommand(deadlineToBeAdded),
                AddCommand.MESSAGE_DUPLICATE_DEADLINE,
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
        
        // execute command and verify result for event
        assertCommandBehavior(
                helper.generateAddCommand(eventToBeAdded),
                AddCommand.MESSAGE_DUPLICATE_EVENT,
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
    }
//@@author

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Malitio expectedAB = helper.generateMalitio(2);
        List<? extends ReadOnlyFloatingTask> expectedFloatingTaskList = expectedAB.getFloatingTaskList();
        List<? extends ReadOnlyDeadline> expectedDeadlineList = expectedAB.getDeadlineList();
        List<? extends ReadOnlyEvent> expectedEventList = expectedAB.getEventList();
 
        // prepare malitio state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.ALL_MESSAGE_SUCCESS,
                expectedAB,
                expectedFloatingTaskList,
                expectedDeadlineList,
                expectedEventList);
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
        List<Deadline> deadlineList = helper.generateDeadlineList(2);
        List<Event> eventList = helper.generateEventList(2);

        // set AB state to 2 tasks each for floating tasks, deadlines and events
        model.resetData(new Malitio());
        for (FloatingTask f : floatingTaskList) {
            model.addTask(f);
        }
        for (Deadline d : deadlineList) {
            model.addTask(d);
        }
        for (Event e : eventList) {
            model.addTask(e);
        }

        assertCommandBehavior(commandWord + " d3", expectedMessage, model.getMalitio(), floatingTaskList, deadlineList, eventList);
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

    //@@author A0129595N
    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<FloatingTask> threeFloatingTasks = helper.generateFloatingTaskList(3);
        List<Deadline> fiveDeadlines = helper.generateDeadlineList(5);
        List<Event> fourEvents = helper.generateEventList(4);
        Malitio expectedAB = helper.generateMalitio(threeFloatingTasks, fiveDeadlines, fourEvents);
        expectedAB.removeTask(threeFloatingTasks.get(1));
        helper.addToModel(model, threeFloatingTasks, fiveDeadlines, fourEvents);

        // execute command and verify result for floating task
        assertCommandBehavior("delete f2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeFloatingTasks.get(1)),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
        
        // execute command and verify result for deadline (boundary case)
        expectedAB.removeTask(fiveDeadlines.get(0));
        assertCommandBehavior("delete d1",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, fiveDeadlines.get(0)),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
        
        // execute command and verify result for event (boundary case)
        expectedAB.removeTask(fourEvents.get(3));
        assertCommandBehavior("delete e4",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, fourEvents.get(3)),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
    }
    //@@author

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    //@@author A0129595N
    @Test
    public void execute_find_matchesPartialWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask fTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        FloatingTask fTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        FloatingTask fTarget3 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        FloatingTask f1 = helper.generateTaskWithName("KE Y");
        
        Deadline dTarget1 = helper.generateDeadlineWithName("bla hey KEY bla");
        Deadline dTarget2 = helper.generateDeadlineWithName("KEY asdalksjdjas");
        Deadline d1 = helper.generateDeadlineWithName("K E Y");
        
        Event eTarget1 = helper.generateEventWithName("askldj KEY");
        Event e1 = helper.generateEventWithName("LOL KLEY");

        List<FloatingTask> fourTasks = helper.generateFloatingTaskList(f1, fTarget1, fTarget2, fTarget3);
        List<Deadline> threeDeadlines = helper.generateDeadlineList(dTarget1, dTarget2, d1);
        List<Event> twoEvents = helper.generateEventList(eTarget1, e1);
        Malitio expectedAB = helper.generateMalitio(fourTasks, threeDeadlines, twoEvents);
        List<FloatingTask> expectedFloatingTaskList = helper.generateFloatingTaskList(fTarget1, fTarget2, fTarget3);
        List<Deadline> expectedDeadlineList = helper.generateDeadlineList(dTarget1, dTarget2);
        List<Event> expectedEventList = helper.generateEventList(eTarget1);
        helper.addToModel(model, fourTasks, threeDeadlines, twoEvents);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedFloatingTaskList.size() + expectedDeadlineList.size() + expectedEventList.size()),
                expectedAB,
                expectedFloatingTaskList,
                expectedDeadlineList,
                expectedEventList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask f1 = helper.generateTaskWithName("bla bla KEY bla");
        FloatingTask f2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        FloatingTask f3 = helper.generateTaskWithName("key key");
        FloatingTask f4 = helper.generateTaskWithName("KEy sduauo");
        
        Deadline d1 = helper.generateDeadlineWithName("KeY");
        Deadline d2 = helper.generateDeadlineWithName("KeY KEY keY");
        Deadline d3 = helper.generateDeadlineWithName("Ksd KEY");
        
        Event e1 = helper.generateEventWithName("KeY keY");
        Event e2 = helper.generateEventWithName("Kasdasd key");

        List<FloatingTask> fourTasks = helper.generateFloatingTaskList(f3, f1, f4, f2);
        List<Deadline> threeDeadlines = helper.generateDeadlineList(d1, d2, d3);
        List<Event> twoEvents = helper.generateEventList(e1, e2);
        Malitio expectedAB = helper.generateMalitio(fourTasks, threeDeadlines, twoEvents);
        List<FloatingTask> expectedFloatingTaskList = fourTasks;
        List<Deadline> expectedDeadlineList = threeDeadlines;
        List<Event> expectedEventList = twoEvents;
        helper.addToModel(model, fourTasks, threeDeadlines, twoEvents);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedFloatingTaskList.size() + expectedDeadlineList.size() + expectedEventList.size()),
                expectedAB,
                expectedFloatingTaskList,
                expectedDeadlineList,
                expectedEventList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask fTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        FloatingTask fTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        FloatingTask fTarget3 = helper.generateTaskWithName("key key");
        FloatingTask f1 = helper.generateTaskWithName("sduauo");
        
        Deadline dTarget1 = helper.generateDeadlineWithName("bla bla KEY");
        Deadline dTarget2 = helper.generateDeadlineWithName("hehe rAnDoM");
        Deadline d1 = helper.generateDeadlineWithName("hello");
        
        Event eTarget1 = helper.generateEventWithName("bla heyyy rAnDoM");
        Event eTarget2 = helper.generateEventWithName("rAnDoM lol");
        Event e1 = helper.generateEventWithName("i want to sleep");

        List<FloatingTask> fourTasks = helper.generateFloatingTaskList(fTarget1, f1, fTarget2, fTarget3);
        List<Deadline> threeDeadlines = helper.generateDeadlineList(dTarget1, dTarget2, d1);
        List<Event> threeEvents = helper.generateEventList(eTarget1, eTarget2, e1);
        Malitio expectedAB = helper.generateMalitio(fourTasks, threeDeadlines, threeEvents);
        List<FloatingTask> expectedFloatingTaskList = helper.generateFloatingTaskList(fTarget1, fTarget2, fTarget3);
        List<Deadline> expectedDeadlineList = helper.generateDeadlineList(dTarget1, dTarget2);
        List<Event> expectedEventList = helper.generateEventList(eTarget1, eTarget2);
        helper.addToModel(model, fourTasks, threeDeadlines, threeEvents);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedFloatingTaskList.size() + expectedDeadlineList.size() + expectedEventList.size()),
                expectedAB,
                expectedFloatingTaskList,
                expectedDeadlineList,
                expectedEventList);
    }

    @Test
    public void execute_find_withinEachTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask fTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        FloatingTask fTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        FloatingTask fTarget3 = helper.generateTaskWithName("key key");
        FloatingTask f1 = helper.generateTaskWithName("sduauo");
        
        Deadline dTarget1 = helper.generateDeadlineWithName("bla bla KEY");
        Deadline dTarget2 = helper.generateDeadlineWithName("hehe rAnDoM");
        Deadline d1 = helper.generateDeadlineWithName("hello");
        
        Event eTarget1 = helper.generateEventWithName("bla heyyy KEY");
        Event eTarget2 = helper.generateEventWithName("rAnDoM lol");
        Event e1 = helper.generateEventWithName("i want to sleep");
        
        //Setup Malitio to have 4 floating tasks, 3 deadlines and 3 events.
        List<FloatingTask> fourTasks = helper.generateFloatingTaskList(fTarget1, f1, fTarget2, fTarget3);
        List<Deadline> threeDeadlines = helper.generateDeadlineList(dTarget1, dTarget2, d1);
        List<Event> threeEvents = helper.generateEventList(eTarget1, eTarget2, e1);
        Malitio expectedAB = helper.generateMalitio(fourTasks, threeDeadlines, threeEvents);
        
        //Find within floating tasks
        List<FloatingTask> expectedFloatingTaskList = helper.generateFloatingTaskList(fTarget1, fTarget2, fTarget3);
        List<Deadline> expectedDeadlineList = helper.generateDeadlineList(dTarget1, dTarget2, d1); // deadline list is unchanged when finding other task
        List<Event> expectedEventList = helper.generateEventList(eTarget1, eTarget2, e1); // event list is unchanged when finding other task
        helper.addToModel(model, fourTasks, threeDeadlines, threeEvents);
        
        assertCommandBehavior("find f key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedFloatingTaskList.size()),
                expectedAB,
                expectedFloatingTaskList,
                expectedDeadlineList,
                expectedEventList); 
        
        //Find within deadlines
        expectedDeadlineList = helper.generateDeadlineList(dTarget1, dTarget2); 
                assertCommandBehavior("find d key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedDeadlineList.size()),
                expectedAB,
                expectedFloatingTaskList,
                expectedDeadlineList,
                expectedEventList);
        
        //Find within events
        expectedEventList = helper.generateEventList(eTarget1, eTarget2); 
      
        //Find within events
        assertCommandBehavior("find e key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedDeadlineList.size()),
                expectedAB,
                expectedFloatingTaskList,
                expectedDeadlineList,
                expectedEventList);
        
    }
    
    @Test
    public void execute_undoThenRedo_afterAdd() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        FloatingTask floatingTaskToBeAdded = helper.sampleFloatingTask();
        Malitio expectedAB = new Malitio();
        expectedAB.addTask(floatingTaskToBeAdded);

        //Since floating task, deadline and event are similar in terms of the way they are created
        //and added to their respective list, we shall only test one of them (floating task) to save resources.
        
        //Confirm add floating task succeeds
        assertCommandBehavior(helper.generateAddCommand(floatingTaskToBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, floatingTaskToBeAdded),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
        
        // remove task from expected
        expectedAB.removeTask(floatingTaskToBeAdded);
        // execute undo command and verify result
        assertCommandBehavior("undo", 
                String.format(UndoCommand.MESSAGE_UNDO_ADD_SUCCESS, floatingTaskToBeAdded),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());   
        
        // add the task back to expected
        expectedAB.addTask(floatingTaskToBeAdded);
        // execute redo command and verify result
        assertCommandBehavior("redo",
                String.format(RedoCommand.MESSAGE_REDO_ADD_SUCCESS, floatingTaskToBeAdded),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
    }
    
    @Test
    public void execute_undoThenRedo_afterDelete() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<FloatingTask> tasks = helper.generateFloatingTaskList(2);
        List<Deadline> deadlines = helper.generateDeadlineList(2);
        List<Event> events = helper.generateEventList(2);
        Malitio expectedAB = helper.generateMalitio(tasks, deadlines, events);
        helper.addToModel(model, tasks, deadlines, events);

        //Since floating task, deadline and event are similar in terms of the way they are deleted
        //from their respective list, we shall only test one of them (deadline) to save resources.
        
        expectedAB.removeTask(deadlines.get(0));
        //Confirm delete deadline succeeds
        assertCommandBehavior("delete d1",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, deadlines.get(0)),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
        
        // add task to expected
        expectedAB.addTask(deadlines.get(0));
        // execute command and verify result
        assertCommandBehavior("undo", 
                String.format(UndoCommand.MESSAGE_UNDO_DELETE_SUCCESS, deadlines.get(0)),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());
        
        // remove task from expected
        expectedAB.removeTask(deadlines.get(0));
        
        // execute command and verify result
        assertCommandBehavior("redo",
                String.format(RedoCommand.MESSAGE_REDO_DELETE_SUCCESS, deadlines.get(0)),
                expectedAB,
                expectedAB.getFloatingTaskList(),
                expectedAB.getDeadlineList(),
                expectedAB.getEventList());       
    }
    
    @Test
    public void execute_undoThenRedo_afterClear() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        // Initialize model with some tasks
        List<FloatingTask> tasks = helper.generateFloatingTaskList(3);
        List<Deadline> deadlines = helper.generateDeadlineList(3);
        List<Event> events = helper.generateEventList(3);
        Malitio expectedAB = helper.generateMalitio(tasks, deadlines, events);
        helper.addToModel(model, tasks, deadlines, events);

        // Confirm model is cleared
        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new Malitio(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());

        // execute command and verify result
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_CLEAR_SUCCESS, expectedAB,
                expectedAB.getFloatingTaskList(), expectedAB.getDeadlineList(), expectedAB.getEventList());

        // execute command and verify result
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_CLEAR_SUCCESS, new Malitio(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
    }
    
    @Test
    public void execute_undoThenRedo_afterEdit() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        // Initialize model with some tasks
        List<FloatingTask> tasks = helper.generateFloatingTaskList(2);
        List<Deadline> deadlines = helper.generateDeadlineList(2);
        List<Event> events = helper.generateEventList(2);
        Malitio expectedAB = helper.generateMalitio(tasks, deadlines, events);
        helper.addToModel(model, tasks, deadlines, events);
        
        // Confirm deadline is edited
        Deadline editedDeadline = helper.generateDeadlineWithName("new deadlines");
        expectedAB.removeTask(deadlines.get(0));
        expectedAB.addTask(editedDeadline);
        assertCommandBehavior("edit d1 new deadlines t/tag", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, deadlines.get(0), editedDeadline),
                expectedAB, expectedAB.getFloatingTaskList(), expectedAB.getDeadlineList(), expectedAB.getEventList());
        
        // Revert expected to previous state before edit
        expectedAB.addTask(deadlines.get(0));
        expectedAB.removeTask(editedDeadline);
        
        // Execute command and verify result
        assertCommandBehavior("undo", String.format(UndoCommand.MESSAGE_UNDO_EDIT_SUCCESS, editedDeadline, deadlines.get(0)), expectedAB,
                expectedAB.getFloatingTaskList(), expectedAB.getDeadlineList(), expectedAB.getEventList());
        
        // Revert expected to previous state before undo
        expectedAB.addTask(editedDeadline);
        expectedAB.removeTask(deadlines.get(0));
        
        // Execute command and verify result
        assertCommandBehavior("redo", String.format(RedoCommand.MESSAGE_REDO_EDIT_SUCCESS, deadlines.get(0), editedDeadline), expectedAB,
                expectedAB.getFloatingTaskList(), expectedAB.getDeadlineList(), expectedAB.getEventList());
        
    }
    //@@author
    
    
    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        FloatingTask sampleFloatingTask() throws Exception {
            Name task = new Name("Eat lunch");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new FloatingTask(task, tags);
        }
        
        Deadline sampleDeadline() throws Exception {
            Name deadline = new Name("Buy food");
            DateTime due = new DateTime("tomorrow 3pm");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Deadline(deadline, due, tags);
        }
        
        Event sampleEvent() throws Exception {
            Name event = new Name("lecture");
            DateTime start = new DateTime("next week 12pm");
            DateTime end = new DateTime("next week 2pm");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Event(event, start, end, tags);
        }

        /**
         * Generates a valid Floating Task using the given seed.
         * Running this function with the same parameter values guarantees the returned Floating Task will have the same state.
         * Each unique seed will generate a unique Floating Task object.
         *
         * @param seed used to generate the task data field values
         */
        FloatingTask generateTask(int seed) throws Exception {
            return new FloatingTask(new Name("Task " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }
        
        /**
         * Generates a valid deadline using the given seed.
         * Running this function with the same parameter values guarantees the returned Deadline will have the same state.
         * Each unique seed will generate a unique Deadline object.
         *
         * @param seed used to generate the task data field values
         */
        Deadline generateDeadline(int seed) throws Exception {
            return new Deadline(new Name("Deadline " + seed), new DateTime("tomorrow 3pm"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }
        
        /**
         * Generates a valid event using the given seed.
         * Running this function with the same parameter values guarantees the returned event will have the same state.
         * Each unique seed will generate a unique Event object.
         *
         * @param seed used to generate the task data field values
         */
        Event generateEvent(int seed) throws Exception {
            return new Event(new Name("Deadline " + seed), new DateTime("tomorrow 3pm"), new DateTime("next week 4pm"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }
        
        //@@author A0129595N
        /** Generates the correct add command based on the task given */
        String generateAddCommand(Object p) {
            StringBuffer cmd = new StringBuffer();
            cmd.append("add ");
            if (isFloatingTask(p)) {
                cmd.append(getArgFromFloatingTaskObj(p));
            } else if (isDeadline(p)) {
                cmd.append(getArgFromDeadlineObj(p));
            } else {
                cmd.append(getArgFromEventObj(p));
            }
            return cmd.toString();
        }

        /**
         * Helper method to get the arguments for Floating Task
         * @param p Floating Task Object
         */
        private String getArgFromFloatingTaskObj(Object p) {
            StringBuffer arg = new StringBuffer();
            arg.append(((FloatingTask) p).getName().fullName);
            UniqueTagList tags = ((FloatingTask) p).getTags();
            for (Tag t : tags) {
                arg.append(" t/").append(t.tagName);
            }
            return arg.toString();
        }
        
        /**
         * Helper method to get the arguments for Deadline
         * @param p Deadline Object
         */
        private String getArgFromDeadlineObj(Object p) {
            StringBuffer arg = new StringBuffer();
            arg.append(((Deadline) p).getName().fullName);
            arg.append(" by ");
            arg.append(((Deadline) p).getDue().toString());
            UniqueTagList tags = ((Deadline) p).getTags();
            for (Tag t : tags) {
                arg.append(" t/").append(t.tagName);
            }
            return arg.toString();
        }

        /**
         * Helper method to get the arguments for Event
         * @param p Event Object
         */
        private String getArgFromEventObj(Object p) {
            StringBuffer arg = new StringBuffer();
            arg.append(((Event) p).getName().fullName);
            arg.append(" start ");
            arg.append(((Event) p).getStart().toString());
            arg.append(" end ");
            arg.append(((Event) p).getEnd().toString());
            UniqueTagList tags = ((Event) p).getTags();
            for (Tag t : tags) {
                arg.append(" t/").append(t.tagName);
            }
            return arg.toString();
        }

        boolean isFloatingTask(Object p) {
            return p instanceof FloatingTask;
        }
        
        boolean isDeadline(Object p) {
            return p instanceof Deadline;
        }
        //@@author
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
        Malitio generateMalitio(List<FloatingTask> tasks, List<Deadline> deadlines, List<Event> events) throws Exception{
            Malitio malitio = new Malitio();
            addToMalitio(malitio, tasks, deadlines, events);
            return malitio;
        }

        /**
         * Adds auto-generated Task objects to the given Malitio
         * @param The malitio to which the Tasks will be added
         */
        void addToMalitio(Malitio malitio, int numGenerated) throws Exception{
            addToMalitio(malitio, generateFloatingTaskList(numGenerated), generateDeadlineList(numGenerated), generateEventList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given Malitio
         */
        void addToMalitio(Malitio malitio, List<FloatingTask> tasksToAdd, List<Deadline> deadlinesToAdd, List<Event> eventsToAdd) throws Exception{
            for(FloatingTask p: tasksToAdd){
                malitio.addTask(p);
            }
            for (Deadline d: deadlinesToAdd) {
                malitio.addTask(d);
            }
            for (Event e: eventsToAdd) {
                malitio.addTask(e);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateFloatingTaskList(numGenerated), generateDeadlineList(numGenerated), generateEventList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<FloatingTask> tasksToAdd, List<Deadline> deadlinesToAdd, List<Event> eventsToAdd) throws Exception{
            for(FloatingTask p: tasksToAdd){
                model.addTask(p);
            }
            for (Deadline d: deadlinesToAdd) {
                model.addTask(d);
            }
            for (Event e: eventsToAdd) {
                model.addTask(e);
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

        List<FloatingTask> generateFloatingTaskList(FloatingTask... floatingtasks) {
            return Arrays.asList(floatingtasks);
        }
        
        /**
         * Generates a list of Deadlines based on the flags.
         */
        List<Deadline> generateDeadlineList(int numGenerated) throws Exception{
            List<Deadline> deadlines = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                deadlines.add(generateDeadline(i));
            }
            return deadlines;
        }

        List<Deadline> generateDeadlineList(Deadline... deadlines) {
            return Arrays.asList(deadlines);
        }
        
        /**
         * Generates a list of Events based on the flags.
         */
        List<Event> generateEventList(int numGenerated) throws Exception{
            List<Event> events = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                events.add(generateEvent(i));
            }
            return events;
        }

        List<Event> generateEventList(Event... events) {
            return Arrays.asList(events);
        }

        /**
         * Generates a Floating Task object with given name. Other fields will have some dummy values.
         */
        FloatingTask generateTaskWithName(String name) throws Exception {
            return new FloatingTask(
                    new Name(name),
                    new UniqueTagList(new Tag("tag"))
            );
        }
        
        /**
         * Generates a Deadline object with given name. Other fields will have some dummy values
         */
        Deadline generateDeadlineWithName(String name) throws Exception {
            return new Deadline(new Name(name), 
                    new DateTime("tomorrow 3pm"), 
                    new UniqueTagList(new Tag("tag"))
            );
        }
        
        /**
         * Generates a Event object with given name. Other fields will have some dummy values
         */
        Event generateEventWithName(String name) throws Exception {
            return new Event(new Name(name), 
                    new DateTime("tomorrow 3pm"),
                    new DateTime("next week 5pm"),
                    new UniqueTagList(new Tag("tag"))
            );
        }        
    }
}
    
