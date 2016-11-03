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
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.model.TaskManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskManager;
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

public class CommandTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    protected Model model;
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

    /*************************Pre and Post setup******************************************/
    
    @Before
    public void setup() {
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
    public void teardown() {
        EventsCenter.clearSubscribers();
    }
    
    /*************************test cases***********************************************/

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
    
    
    /*********************Utility methods*****************************************/

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskManager, List)
     */
    protected void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskManager(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    protected void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedAddressBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getTaskManager());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
    }

    

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    protected void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);
        
        // set AB state to 2 tasks
        model.resetData(new TaskManager());
        for (Task p : taskList) {
            model.addTask(p);
        }
        
        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskManager(), taskList);
    }
    
    /**
     * Confirms the 'absence argument keyword behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index or name.
     */
    protected void assertAbsenceKeywordFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //keyword missing
    }
    
   /***********************test cases to be copy pasted to other places***************************/

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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
       // assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{
        
        Task getFloatingTask() throws Exception {
            Name name = new Name("Visit grandma");
            Tag tag1 = new Tag("tag1");
            UniqueTagList tags = new UniqueTagList(tag1);
            return new Task(name,tags, new Priority(0));
        }
        
        Task getDeadlineTask() throws Exception{
            Name name=new Name("project due");
            Date deadline=new Deadline("01.01.2016");
            return new Task(name,deadline,new UniqueTagList(), new Priority(0));
        }
        
        Task getDuplicateDeadlineTask() throws Exception{
            Name name=new Name("Visit grandma");
            Date deadline=new Deadline("01.01.2016-14");
            return new Task(name,deadline,new UniqueTagList(), new Priority(0));
        }
        
        Task getEvent() throws Exception{
            Name name=new Name("do homework");
            Date date=new EventDate("01.01.2016","02.01.2016");
            return new Task(name,date,new UniqueTagList(), new Priority(0));
        }
        
        Task getRecurringDeadlineTask() throws Exception{
            Name name=new Name("post on GitHub");
            Date deadline=new Deadline("02.03.2016");
            Recurring recurring=new Recurring("weekly");
            return new Task(name,deadline,new UniqueTagList(),recurring, new Priority(0));
        }
        
        Task getRecurringEvent() throws Exception{
            Name name=new Name("eat lunch");
            Date date=new EventDate("01.01.2016-14","02.01.2016-16");
            Recurring recurring=new Recurring("monthly");
            return new Task(name,date,new UniqueTagList(),recurring, new Priority(0));
        }
        
        

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(new Name("Task " + seed), new Deadline("16.10.2016-14"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    new Priority(0));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();
            cmd.append("add n/");
            cmd.append(p.getName().toString());
            if(p.isEvent()){
                EventDate date=(EventDate) p.getDate();
                cmd.append(" s/ ").append(date.getStartDate());
                cmd.append(" e/ ").append(date.getEndDate());
            }else{
            cmd.append(" d/").append(p.getDate());
            }
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }
            if(p.isRecurring())
                cmd.append("r/ "+p.getRecurring().recurringFrequency);
            return cmd.toString();
        }
    
        
        /** Generates the correct flexi add command based on the task given */
        public String generateFlexiAddCommand(Task p) {
            StringBuilder sb = new StringBuilder();
            sb.append("add ");
            if (p.getDate() instanceof EventDate) {
                EventDate eventDate = (EventDate) p.getDate();
                sb.append("e/" + eventDate.getEndDate() + " ");
                sb.append("s/" + eventDate.getStartDate() + " ");
            } else {
                assert p.getDate() instanceof Deadline;
                String deadline = p.getDate().getValue();
                if (!deadline.equals("")) {
                    sb.append("d/" + deadline + " ");
                }
            }
            sb.append("n/"+p.getName().taskName + " ");
            if(p.isRecurring()){
                sb.append("r/ "+p.getRecurring().recurringFrequency);
            }
            p.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
            return sb.toString();
        }

        /**
         * Generates an TaskManager with auto-generated tasks.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of Tasks given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskManager.addTask(p);
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
         * Generates an undone Task object with given name. Other fields will have some dummy values.
         */
        Task generateUndoneTaskWithName(String name) throws Exception {
            return new Task(new Name(name), new Deadline("11.11.2016"), new UniqueTagList(new Tag("tag")),
                    new Priority(0));
        }
        
        /**
         * Generates an undone Task object with given deadline. Other fields will have some dummy values.
         */
        Task generateUndoneTaskWithDeadline(String deadline) throws Exception {
            return new Task(new Name("name"), new Deadline(deadline), new UniqueTagList(new Tag("tag")),
                    new Priority(0));
        }
        
        /**
         * Generates an undone Task object with given tag. Other fields will have some dummy values.
         */
        Task generateUndoneTaskWithTag(String tag) throws Exception {
            return new Task(new Name("name"), new Deadline("11.11.2016"), new UniqueTagList(new Tag(tag)),
                    new Priority(0));
        }
        
        /**
         * Generates a done Task object with given name. Other fields will have some dummy values.
         */
        Task generateDoneTaskWithName(String name) throws Exception {
            return new Task(new Name(name), new Deadline("11.11.2016"), new UniqueTagList(new Tag("tag")), true, false,
                    new Priority(0));
        }
        
        /**
         * Generates an undone Event object with given name. Other fields will have some dummy values.
         */
        Task generateUndoneEventWithName(String name) throws Exception {
            return new Task(new Name(name), new EventDate("11.11.2016", "12.11.2016"),
                    new UniqueTagList(new Tag("tag")), new Priority(0));
        }
        
        /**
         * Generates an undone Event object with given start date. Other fields will have some dummy values.
         */
        Task generateUndoneEventWithStartDate(String startDate) throws Exception {
            return new Task(new Name("name"), new EventDate(startDate, "12.11.2016"), new UniqueTagList(new Tag("tag")),
                    new Priority(0));
        }
        
        /**
         * Generates an undone Event object with given start date. Other fields will have some dummy values.
         */
        Task generateUndoneEventWithEndDate(String endDate) throws Exception {
            return new Task(new Name("name"), new EventDate("11.11.2016", endDate), new UniqueTagList(new Tag("tag")),
                    new Priority(0));
        }
        
        /**
         * Generates a done Event object with given name. Other fields will have some dummy values.
         */
        Task generateDoneEventWithName(String name) throws Exception {
            return new Task(new Name(name), new EventDate("11.11.2016", "12.11.2016"),
                    new UniqueTagList(new Tag("tag")), true, false, new Priority(0));
        }

    }
}
