package tars.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import tars.commons.core.EventsCenter;
import tars.commons.core.Messages;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.ui.JumpToListRequestEvent;
import tars.commons.events.ui.ShowHelpRequestEvent;
import tars.commons.flags.Flag;
import tars.logic.Logic;
import tars.logic.LogicManager;
import tars.logic.commands.AddCommand;
import tars.logic.commands.CdCommand;
import tars.logic.commands.ClearCommand;
import tars.logic.commands.Command;
import tars.logic.commands.CommandResult;
import tars.logic.commands.DeleteCommand;
import tars.logic.commands.EditCommand;
import tars.logic.commands.ExitCommand;
import tars.logic.commands.FindCommand;
import tars.logic.commands.HelpCommand;
import tars.logic.commands.ListCommand;
import tars.logic.commands.MarkCommand;
import tars.logic.commands.SelectCommand;
import tars.logic.commands.UndoCommand;
import tars.model.Tars;
import tars.model.Model;
import tars.model.ModelManager;
import tars.model.ReadOnlyTars;
import tars.model.task.*;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.storage.StorageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tars.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    // These are for checking the correctness of the events raised
    private ReadOnlyTars latestSavedTars;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TarsChangedEvent abce) {
        latestSavedTars = new Tars(abce.data);
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
        String tempTarsFile = saveFolder.getRoot().getPath() + "TempTars.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTarsFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTars = new Tars(model.getTars()); // last saved assumed to be
                                                     // up to date before.
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
        assertCommandBehavior(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'tars' and the 'last shown list' are expected to be empty.
     * 
     * @see #assertCommandBehavior(String, String, ReadOnlyTars, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new Tars(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's
     * state are as expected:<br>
     * - the internal tars data are same as those in the {@code expectedTars}
     * <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedTars} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage, ReadOnlyTars expectedTars,
            List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTars, model.getTars());
        assertEquals(expectedTars, latestSavedTars);
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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new Tars(), Collections.emptyList());
    }

    @Test
    public void execute_undo_emptyCmdHistStack() throws Exception {
        assertCommandBehavior("undo", UndoCommand.MESSAGE_EMPTY_UNDO_CMD_HIST);
    }

    @Test
    public void execute_undo_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeUndo = helper.meetAdam();
        Tars expectedAB = new Tars();
        expectedAB.addTask(toBeUndo);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeUndo), String.format(AddCommand.MESSAGE_SUCCESS, toBeUndo),
                expectedAB, expectedAB.getTaskList());

        expectedAB.removeTask(toBeUndo);

        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_SUCCESS + "\nAction: " + String.format(AddCommand.MESSAGE_UNDO, toBeUndo),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_undo_delete_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeUndo = helper.meetAdam();
        Tars expectedAB = new Tars();
        expectedAB.addTask(toBeUndo);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeUndo), String.format(AddCommand.MESSAGE_SUCCESS, toBeUndo),
                expectedAB, expectedAB.getTaskList());

        expectedAB.removeTask(toBeUndo);

        // execute command and verify result
        assertCommandBehavior("del 1", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, toBeUndo), expectedAB,
                expectedAB.getTaskList());

        expectedAB.addTask(toBeUndo);

        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_SUCCESS + "\nAction: " + String.format(DeleteCommand.MESSAGE_UNDO, toBeUndo),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior("add -dt 22/04/2016 1400 to 23/04/2016 2200 -p h Valid Task Name", expectedMessage);
        assertCommandBehavior("add", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior("add []\\[;] -dt 05/09/2016 1400 to 06/09/2016 2200 -p m", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior("add name - hello world -dt 05/09/2016 1400 to 06/09/2016 2200 -p m",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior("add Valid Task Name -dt @@@notAValidDate@@@ -p m", Messages.MESSAGE_INVALID_DATE);
        assertCommandBehavior("add Valid Task Name -dt 05/09/2016 1400 to 06/09/2016 2200 -p medium",
                Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandBehavior("add Valid Task Name -dt 05/09/2016 1400 to 06/09/2016 2200 -p m -t invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedAB = new Tars();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());

    }

    @Test
    public void execute_add_float_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.floatTask();
        Tars expectedAB = new Tars();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedAB = new Tars();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal address book

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK, expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_list_showsAllUndoneTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Tars expectedAB = helper.generateTars(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare tars state
        helper.addToModel(model, 2);

        assertCommandBehavior("ls", ListCommand.MESSAGE_SUCCESS, expectedAB, expectedList);
    }

    @Test
    public void execute_list_showsAllDoneTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Tars expectedAB = new Tars();
        Task task1 = helper.meetAdam();
        Status done = new Status(true);
        task1.setStatus(done);
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);
        expectedAB.addTask(task1);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare tars state
        helper.addToModel(model, taskList);

        assertCommandBehavior("ls -do", ListCommand.MESSAGE_SUCCESS_DONE, expectedAB, expectedList);
    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Tars expectedAB = helper.generateTars(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare tars state
        helper.addToModel(model, 2);

        assertCommandBehavior("ls -all", ListCommand.MESSAGE_SUCCESS_ALL, expectedAB, expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandBehavior(commandWord, expectedMessage); // index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); // index
                                                                     // should
                                                                     // be
                                                                     // unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); // index
                                                                     // should
                                                                     // be
                                                                     // unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); // index
                                                                    // cannot be
                                                                    // 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * 
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new Tars());
        for (Task p : taskList) {
            model.addTask(p);
        }

        if (commandWord == "edit") { // Only For Edit Command
            assertCommandBehavior(commandWord + " 3 -n changeTaskName", expectedMessage, model.getTars(), taskList);
        } else { // For Select & Delete Commands
            assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTars(), taskList);
        }
    }

    //@@author A0124333U
    private void assertInvalidInputBehaviorForEditCommand(String inputCommand, String expectedMessage)
            throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new Tars());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(inputCommand, expectedMessage, model.getTars(), taskList);
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
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        Tars expectedAB = helper.generateTars(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("del ", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("del");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        Tars expectedAB = helper.generateTars(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("del 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        Tars expectedAB = helper.generateTars(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
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
        Tars expectedAB = helper.generateTars(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAllKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key rAnDoM");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        Tars expectedAB = helper.generateTars(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    // @@author A0124333U
    @Test
    public void execute_edit_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

        assertInvalidInputBehaviorForEditCommand("edit ", expectedMessage);
        assertInvalidInputBehaviorForEditCommand("edit 1 -invalidFlag invalidArg", expectedMessage);
    }

    @Test
    public void execute_edit_indexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("edit");
    }

    @Test
    public void execute_edit_invalidTaskData() throws Exception {
        assertInvalidInputBehaviorForEditCommand("edit 1 -n []\\[;]", Name.MESSAGE_NAME_CONSTRAINTS);
        assertInvalidInputBehaviorForEditCommand("edit 1 -dt @@@notAValidDate@@@", Messages.MESSAGE_INVALID_DATE);
        assertInvalidInputBehaviorForEditCommand("edit 1 -p medium", Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertInvalidInputBehaviorForEditCommand("edit 1 -n validName -dt invalidDate", Messages.MESSAGE_INVALID_DATE);
        assertInvalidInputBehaviorForEditCommand("edit 1 -tr $#$", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void execute_edit_editsCorrectTask() throws Exception {

        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task taskToAdd = helper.meetAdam();
        List<Task> listToEdit = new ArrayList<Task>();
        listToEdit.add(taskToAdd);
        Tars expectedAB = new Tars();
        expectedAB.addTask(taskToAdd);

        Flag nameOpt = new Flag(Flag.NAME, false);
        Flag priorityOpt = new Flag(Flag.PRIORITY, false);
        Flag dateTimeOpt = new Flag(Flag.DATETIME, false);
        Flag addTagOpt = new Flag(Flag.ADDTAG, true);
        Flag removeTagOpt = new Flag(Flag.REMOVETAG, true);

        // edit task
        HashMap<Flag, String> argsToEdit = new HashMap<Flag, String>();
        argsToEdit.put(nameOpt, "-n Meet Betty Green");
        argsToEdit.put(dateTimeOpt, "-dt 20/09/2016 1800 to 21/09/2016 1800");
        argsToEdit.put(priorityOpt, "-p h");
        argsToEdit.put(addTagOpt, "-ta tag3");
        argsToEdit.put(removeTagOpt, "-tr tag2");

        Task taskToEdit = taskToAdd;
        Task editedTask = expectedAB.editTask(taskToEdit, argsToEdit);
        helper.addToModel(model, listToEdit);

        String inputCommand = "edit 1 -n Meet Betty Green -dt 20/09/2016 1800 "
                + "to 21/09/2016 1800 -p h -tr tag2 -ta tag3";
        // execute command
        assertCommandBehavior(inputCommand, String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_cd_incorrectArgsFormat_errorMessageShown() throws Exception {
        assertCommandBehavior("cd ", CdCommand.MESSAGE_INVALID_FILEPATH);
    }

    @Test
    public void execute_cd_success() throws Exception {
        assertCommandBehavior("cd testTars.xml", String.format(CdCommand.MESSAGE_SUCCESS, "testTars.xml"));
    }

    @Test
    public void execute_mark_allTaskAsDone() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");

        List<Task> taskList = helper.generateTaskList(task1, task2);

        Tars expectedAB = new Tars();
        helper.addToModel(model, taskList);

        Status done = new Status(true);
        task1.setStatus(done);
        task2.setStatus(done);

        expectedAB.addTask(task1);
        expectedAB.addTask(task2);

        assertCommandBehavior("mark -do 1 2", MarkCommand.MESSAGE_MARK_SUCCESS, expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_mark_allTaskAsUndone() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");
        Status done = new Status(true);
        task1.setStatus(done);
        task2.setStatus(done);

        List<Task> taskList = helper.generateTaskList(task1, task2);

        Tars expectedAB = new Tars();
        helper.addToModel(model, taskList);

        Status undone = new Status(false);
        task1.setStatus(undone);
        task2.setStatus(undone);

        expectedAB.addTask(task1);
        expectedAB.addTask(task2);

        assertCommandBehavior("mark -ud 1 2", MarkCommand.MESSAGE_MARK_SUCCESS, expectedAB, expectedAB.getTaskList());
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task meetAdam() throws Exception {
            Name name = new Name("Meet Adam Brown");
            DateTime dateTime = new DateTime("01/09/2016 1400", "01/09/2016 1500");
            Priority priority = new Priority("m");
            Status status = new Status(false);
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, dateTime, priority, status, tags);
        }

        Task floatTask() throws Exception {
            Name name = new Name("Do homework");
            DateTime dateTime = new DateTime("", "");
            Priority priority = new Priority("");
            Status status = new Status(false);
            UniqueTagList tags = new UniqueTagList();
            return new Task(name, dateTime, priority, status, tags);
        }

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            int seed2 = (seed + 1) % 31 + 1; // Generate 2nd seed for DateTime
                                             // value
            return new Task(new Name("Task " + seed), new DateTime(seed + "/01/2016 1400", seed2 + "/01/2016 2200"),
                    new Priority("h"), new Status(false),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ").append(p.getName().toString());

            if (p.getDateTime().toString().length() > 0) {
                cmd.append(" -dt ").append(p.getDateTime().toString());
            }

            if (p.getPriority().toString().length() > 0) {
                cmd.append(" -p ").append(p.getPriority().toString());
            }

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" -t ").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an Tars with auto-generated undone tasks.
         */
        Tars generateTars(int numGenerated) throws Exception {
            Tars tars = new Tars();
            addToTars(tars, numGenerated);
            return tars;
        }

        /**
         * Generates an Tars based on the list of Tasks given.
         */
        Tars generateTars(List<Task> tasks) throws Exception {
            Tars tars = new Tars();
            addToTars(tars, tasks);
            return tars;
        }

        /**
         * Adds auto-generated Task objects to the given Tars
         * 
         * @param tars
         *            The Tars to which the Tasks will be added
         */
        void addToTars(Tars tars, int numGenerated) throws Exception {
            addToTars(tars, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given Tars
         */
        void addToTars(Tars tars, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                tars.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * 
         * @param model
         *            The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(new Name(name), new DateTime("05/09/2016 1400", "06/09/2016 2200"), new Priority("h"),
                    new Status(false), new UniqueTagList(new Tag("tag")));
        }
    }
}
