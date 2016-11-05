package seedu.oneline.logic;

import static org.junit.Assert.assertEquals;
import static seedu.oneline.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.events.model.TaskBookChangedEvent;
import seedu.oneline.commons.events.ui.JumpToListRequestEvent;
import seedu.oneline.commons.events.ui.ShowHelpRequestEvent;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.model.Model;
import seedu.oneline.model.ModelManager;
import seedu.oneline.model.ReadOnlyTaskBook;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.TaskRecurrence;
import seedu.oneline.model.task.TaskTime;
import seedu.oneline.storage.StorageManager;

public class LogicTestManager {


    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    protected Model model;
    protected Logic logic;

    //These are for checking the correctness of the events raised
    protected ReadOnlyTaskBook latestSavedTaskBook;
    protected boolean helpShown;
    protected int targetedJumpIndex;

    @Subscribe
    protected void handleLocalModelChangedEvent(TaskBookChangedEvent abce) {
        latestSavedTaskBook = new TaskBook(abce.data);
    }

    @Subscribe
    protected void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    public void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
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

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskBook, List)
     */
    protected void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task book data are same as those in the {@code expectedTaskBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskBook} was saved to the storage file. <br>
     */
    protected void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        if (!expectedShownList.equals(model.getFilteredTaskList())) {
            assert false : "\nExpected: " + Arrays.toString(expectedShownList.toArray()) + "\nShown: " + Arrays.toString(model.getFilteredTaskList().toArray());
        }
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestSavedTaskBook);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    protected void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
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
    protected void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        TestDataHelper helper = new TestDataHelper();
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        List<Task> taskList = helper.generateTaskList(2);

        // set Task book state to 2 tasks
        model.resetData(new TaskBook());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskBook(), taskList);
    }


    /**
     * A utility class to generate test data.
     */
    protected class TestDataHelper {

        Task myTask() throws Exception {
            TaskName name = new TaskName("Adam Brown");
            TaskTime startTime = TaskTime.getDefault();
            TaskTime endTime = TaskTime.getDefault();
            TaskTime deadline = new TaskTime("Sun Oct 16 21:35:45");
            TaskRecurrence recurrence = new TaskRecurrence("X");
            Tag tag = Tag.getTag("tag1");
            return new Task(name, startTime, endTime, deadline, recurrence, tag);
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
                    new TaskName("Task " + seed),
                    new TaskTime(""),
                    new TaskTime(""),
                    new TaskTime(""),
                    new TaskRecurrence(""),
                    Tag.getTag("tag" + Math.abs(seed))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" .from ").append(p.getStartTime());
            cmd.append(" .to ").append(p.getEndTime());
            cmd.append(" .due ").append(p.getDeadline());
            cmd.append(" .every ").append(p.getRecurrence());
            cmd.append(" #").append(p.getTag().getTagName());

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
         * Generates an AddressBook based on the list of Tasks given.
         */
        TaskBook generateTaskBook(List<Task> tasks) throws Exception{
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, tasks);
            return taskBook;
        }

        /**
         * Adds auto-generated Task objects to the given Task Book
         * @param taskBook The Task Book to which the Tasks will be added
         */
        void addToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
            addToTaskBook(taskBook, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given Task Book
         */
        void addToTaskBook(TaskBook taskBook, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskBook.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
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
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new TaskName(name),
                    new TaskTime(""),
                    new TaskTime(""),
                    new TaskTime(""),
                    new TaskRecurrence(""),
                    Tag.getTag("tag")
            );
        }
    }
    
}
