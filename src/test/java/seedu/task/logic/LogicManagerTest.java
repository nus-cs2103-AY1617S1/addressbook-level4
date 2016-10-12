package seedu.task.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.task.commons.events.model.TaskBookChangedEvent;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.events.ui.ShowHelpEvent;
import seedu.task.logic.Logic;
import seedu.task.logic.LogicManager;
import seedu.task.logic.commands.*;
import seedu.task.model.TaskBook;
import seedu.task.model.item.*;
import seedu.task.model.Model;
import seedu.task.model.ModelManager;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.storage.StorageManager;
import seedu.taskcommons.core.EventsCenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.taskcommons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskBook latestSavedTaskBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskBookChangedEvent abce) {
        latestSavedTaskBook = new TaskBook(abce.data);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempTaskBookFile = saveFolder.getRoot().getPath() + "TempTaskBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskBook = new TaskBook(model.getTaskBook()); // last saved assumed to be up to date before.
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
     * Both the 'task book' and the 'last shown list' are expected to be empty.
     * @see #assertTaskCommandBehavior(String, String, ReadOnlyTaskBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertTaskCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    private void assertTaskCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        
        
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    
    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    private void assertEventCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyEvent> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredEventList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }
    

    @Ignore
    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Ignore
    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Ignore
    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Ignore
    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertTaskCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBook(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE, AddEventCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add", expectedMessage);
    }

    @Test
    public void execute_addTask_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] /desc nil /by 30-12-16", Name.MESSAGE_NAME_CONSTRAINTS);
//        assertCommandBehavior(
//                "add Valid Name /desc nil /by 30-12-111", Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
    }
    
    @Test
    public void execute_addEvent_invalidEventData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] /desc nil /from 30-12-16 31-12-16", Name.MESSAGE_NAME_CONSTRAINTS);
//        assertCommandBehavior(
//                "add Valid Name /desc nil /by 30-12-111", Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
    }

    @Test
    public void execute_addTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),
                String.format(AddTaskCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_addEvent_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);

        // execute command and verify result
        assertEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),
                String.format(AddEventCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getEventList());

    }

    @Test
    public void execute_addTaskDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task book

        // execute command and verify result
        assertTaskCommandBehavior(
                helper.generateAddTaskCommand(toBeAdded),
                AddTaskCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_addEventDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);

        // setup starting state
        model.addEvent(toBeAdded); // event already in internal task book

        // execute command and verify result
        assertEventCommandBehavior(
                helper.generateAddEventCommand(toBeAdded),
                AddEventCommand.MESSAGE_DUPLICATE_EVENT,
                expectedAB,
                expectedAB.getEventList());

    }

    @Test
    public void execute_list_showsUncompletedTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithName("Task1");
        Task tTarget2 = helper.generateTaskWithName("Task2");
        Task tTarget3 = helper.completedTask();
        
        List<Task> threeTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2);

        // prepare address book state
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("list -t",
                ListTaskCommand.MESSAGE_INCOMPLETED_SUCCESS,
                expectedTB,
                expectedList);
    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithName("Task1");
        Task tTarget2 = helper.generateTaskWithName("Task2");
        Task tTarget3 = helper.completedTask();
        
        List<Task> threeTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2,tTarget3);

        // prepare address book state
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("list -t -a",
                ListTaskCommand.MESSAGE_ALL_SUCCESS,
                expectedTB,
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
    private void assertTaskIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskBook());
        for (Task t : taskList) {
            model.addTask(t);
        }

        assertTaskCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskBook(), taskList);
    }
    
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertEventIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Event> eventList = helper.generateEventList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskBook());
        for (Event t : eventList) {
            model.addEvent(t);
        }

        assertEventCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskBook(), eventList);
    }
    
    @Ignore
    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }
    @Ignore
    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertTaskIndexNotFoundBehaviorForCommand("select");
    }

    @Ignore
    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook_Tasks(threeTasks);
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    @Test
    public void execute_MarkInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("mark", expectedMessage);
    }
    
    @Test
    public void execute_MarkIndexNotFound_errorMessageShown() throws Exception {
        assertTaskIndexNotFoundBehaviorForCommand("mark");
    }
    
    @Test
    public void execute_mark_marksCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook_Tasks(threeTasks);
        expectedAB.markTask(1);
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("mark 2",
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    public void execute_deleteTaskInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteTaskIndexNotFound_errorMessageShown() throws Exception {
        assertTaskIndexNotFoundBehaviorForCommand("delete -t");
    }
    
    @Test
    public void execute_deleteEventInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteEventIndexNotFound_errorMessageShown() throws Exception {
        assertEventIndexNotFoundBehaviorForCommand("delete -e");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook_Tasks(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addTaskToModel(model, threeTasks);

        assertTaskCommandBehavior("delete -t 2",
                String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_delete_removesCorrectEvent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Event> threeEvents = helper.generateEventList(3);

        TaskBook expectedAB = helper.generateTaskBook_Events(threeEvents);
        expectedAB.removeEvent(threeEvents.get(1));
        helper.addEventToModel(model, threeEvents);

        assertTaskCommandBehavior("delete -e 2",
                String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, threeEvents.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Ignore
    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Ignore
    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskBook expectedAB = helper.generateTaskBook_Tasks(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addTaskToModel(model, fourTasks);

        assertTaskCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Ignore
    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskBook expectedAB = helper.generateTaskBook_Tasks(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addTaskToModel(model, fourTasks);

        assertTaskCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Ignore
    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskBook expectedAB = helper.generateTaskBook_Tasks(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addTaskToModel(model, fourTasks);

        assertTaskCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task computingTask() throws Exception {
            Name name = new Name("Do CS2103 Project");
//            Deadline deadline = new Deadline("01-01-16");
            Description des = new Description("post on Github");
            
            return new Task(name, des, false);
        }
        
        Task completedTask() throws Exception {
        	Name name = new Name("Run tests");
        	Description des = new Description("for task");
        	
        	return new Task(name, des, true);
        }
        
        Event computingEvent() throws Exception {
            Name name = new Name("Attend CS2103 Workshop");
            Description des = new Description("post on Github");
            EventDuration dur = new EventDuration("13 Oct 3pm > 14 Oct 4pm");
            
            return new Event(name, des, dur);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new Description("Description" + Math.abs(seed)),
                    false
                   );
        }

        /**
         * Generates a valid event using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Event object.
         *
         * @param seed used to generate the event data field values
         */
        Event generateEvent(int seed) throws Exception {
            return new Event(
                    new Name("Event " + seed),
                    new Description("Description" + Math.abs(seed))
                   );
        }
        
        /** Generates the correct add task command based on the task given */
        String generateAddTaskCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getTask().toString());
            cmd.append(" /desc ").append(p.getDescription().toString());

            return cmd.toString();
        }
        
        /** Generates the correct add event command based on the event given */
        String generateAddEventCommand(Event p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getEvent().toString());
            cmd.append(" /desc ").append(p.getDescription().toString());
            cmd.append(" /from ").append(p.getDuration().toString());

            return cmd.toString();
        }

        /**
         * Generates an TaskBook with auto-generated tasks.
         */
        TaskBook generateTaskBook_Tasks(int numGenerated) throws Exception{
            TaskBook taskBook = new TaskBook();
            addTasksToTaskBook(taskBook, numGenerated);
            return taskBook;
        }

        /**
         * Generates TaskBookook based on the list of tasks given.
         */
        TaskBook generateTaskBook_Tasks(List<Task> tasks) throws Exception{
            TaskBook taskBook = new TaskBook();
            addTasksToTaskBook(taskBook, tasks);
            return taskBook;
        }

        /**
         * Generates an TaskBook with auto-generated tasks.
         */
        TaskBook generateTaskBook_Events(int numGenerated) throws Exception{
            TaskBook taskBook = new TaskBook();
            addEventsToTaskBook(taskBook, numGenerated);
            return taskBook;
        }

        /**
         * Generates TaskBookook based on the list of tasks given.
         */
        TaskBook generateTaskBook_Events(List<Event> events) throws Exception{
            TaskBook taskBook = new TaskBook();
            addEventsToTaskBook(taskBook, events);
            return taskBook;
        }
        
        /**
         * Adds auto-generated Task objects to the given TaskBook
         * @param taskBook The TaskBook to which the Tasks will be added
         */
        void addTasksToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
            addTasksToTaskBook(taskBook, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskBook
         */
        void addTasksToTaskBook(TaskBook taskBook, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskBook.addTask(p);
            }
        }
        
        /**
         * Adds auto-generated Event objects to the given TaskBook
         * @param taskBook The TaskBook to which the Events will be added
         */
        void addEventsToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
            addEventsToTaskBook(taskBook, generateEventList(numGenerated));
        }
        
        /**
         * Adds the given list of Events to the given TaskBook
         */
        void addEventsToTaskBook(TaskBook taskBook, List<Event> eventsToAdd) throws Exception{
            for(Event p: eventsToAdd){
                taskBook.addEvent(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addTaskToModel(Model model, int numGenerated) throws Exception{
            addTaskToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addTaskToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addEventToModel(Model model, int numGenerated) throws Exception{
            addEventToModel(model, generateEventList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addEventToModel(Model model, List<Event> eventsToAdd) throws Exception{
            for(Event p: eventsToAdd){
                model.addEvent(p);
            }
        }
        
        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
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
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Description("dummy description"),
                    false
            );
        }
    }
}
