package seedu.whatnow.logic;

import com.google.common.eventbus.Subscribe;

import seedu.whatnow.commons.core.EventsCenter;
import seedu.whatnow.commons.events.model.WhatNowChangedEvent;
import seedu.whatnow.commons.events.ui.JumpToListRequestEvent;
import seedu.whatnow.commons.events.ui.ShowHelpRequestEvent;
import seedu.whatnow.logic.Logic;
import seedu.whatnow.logic.LogicManager;
import seedu.whatnow.logic.commands.*;
import seedu.whatnow.model.Model;
import seedu.whatnow.model.ModelManager;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.WhatNow;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.*;
import seedu.whatnow.storage.StorageManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.whatnow.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyWhatNow latestSavedWhatNow;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(WhatNowChangedEvent abce) {
        latestSavedWhatNow = new WhatNow(abce.data);
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
        String tempWhatNowFile = saveFolder.getRoot().getPath() + "TempWhatNow.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempWhatNowFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedWhatNow = new WhatNow(model.getWhatNow()); // last saved assumed to be up to date before.
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
     * Both 'WhatNow' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyWhatNow, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new WhatNow(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal WhatNow data are same as those in the {@code expectedWhatNow} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedWhatNow} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyWhatNow expectedWhatNow,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {
        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        if (!inputCommand.contains("find") && !inputCommand.contains("change"))
            assertEquals(expectedShownList, model.getAllTaskTypeList());
        
        //Confirm the state of data (saved and in-memory) is as expected
        if (!inputCommand.contains("change")) {
            assertEquals(expectedWhatNow, model.getWhatNow());
            assertEquals(expectedWhatNow, latestSavedWhatNow);
        }
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

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new WhatNow(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong/args wrong/args", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] p12345 evalid@e.mail avalid, whatnow", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        assertCommandBehavior(
                "add Valid Name p12345 evalid@e.mail avalid, whatnow t/invalid_-[.tag", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        WhatNow expectedAB = new WhatNow();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        WhatNow expectedAB = new WhatNow();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal WhatNow

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
        WhatNow expectedAB = helper.generateWhatNow(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare WhatNow state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForUpdateCommand(String commandWord, String taskType, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord + " " + taskType + " description Check if index is missing", expectedMessage); //index missing
        assertCommandBehavior(commandWord + " " + taskType + " +1" + " description Check if index is unsigned", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " " + taskType + " -1" + " description Check if index is unsigned", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " " + taskType + " 0" + " description Check if index is zero", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " " + taskType + " not_a_number" + " description Check if index is not a number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForUpdateCommand(String commandWord, String taskType) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new WhatNow());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " " + taskType + " 3" + " description Check if index exists", expectedMessage, model.getWhatNow(), taskList);
    }
    
    @Test
    public void execute_update_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForUpdateCommand("update", "todo", expectedMessage);
    }

    @Test
    public void execute_update_indexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForUpdateCommand("update", "todo");
    }

    @Test
    public void execute_update_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.todo("Buy milk", "23/2/2017", "lowPriority", "inProgress");
        WhatNow expectedAB = new WhatNow();
        expectedAB.addTask(helper.adam());
        expectedAB.addTask(toBeAdded);    
        List<Task> taskList = helper.generateTaskList(toBeAdded, helper.adam());
        helper.addToModel(model, taskList);
        
        // execute command and verify result
        ReadOnlyTask taskToUpdate = taskList.get(0);
        Task toUpdate = helper.todo("Buy chocolate milk", "23/2/2017", "inProgress", "lowPriority");
        expectedAB.updateTask(taskToUpdate, toUpdate);
        
        assertCommandBehavior(helper.generateUpdateCommand("description", "Buy chocolate milk"),
                String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate),
                expectedAB,
                expectedAB.getTaskList());
        
        taskToUpdate = toUpdate;
        toUpdate = helper.todo("Buy chocolate milk", "23/2/2017", "highPriority", "Completed");
        expectedAB.updateTask(taskToUpdate, toUpdate);
        
        assertCommandBehavior(helper.generateUpdateCommand("tag", "highPriority Completed"),
                String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate),
                expectedAB,
                expectedAB.getTaskList());
        
        taskToUpdate = toUpdate;
        toUpdate = helper.todo("Buy chocolate milk", "12/04/2017", "highPriority", "Completed");
        expectedAB.updateTask(taskToUpdate, toUpdate);
        
        assertCommandBehavior(helper.generateUpdateCommand("date", "12/04/2017"),
                String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate),
                expectedAB,
                expectedAB.getTaskList());
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String taskType, String expectedMessage) throws Exception {
        if (!taskType.equals("")) {
            assertCommandBehavior(commandWord + " " + taskType, expectedMessage); //index missing
            assertCommandBehavior(commandWord + " " + taskType + " +1", expectedMessage); //index should be unsigned
            assertCommandBehavior(commandWord + " " + taskType + " -1", expectedMessage); //index should be unsigned
            assertCommandBehavior(commandWord + " " + taskType + " 0", expectedMessage); //index cannot be 0
            assertCommandBehavior(commandWord + " " + taskType + " not_a_number", expectedMessage);
        } else {
            assertCommandBehavior(commandWord, expectedMessage); //index missing
            assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
            assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
            assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
            assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
        }
        
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord, String taskType) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new WhatNow());
        for (Task p : taskList) {
            model.addTask(p);
        }

        if (!taskType.equals(""))
            assertCommandBehavior(commandWord + " " + taskType + " 3", expectedMessage, model.getWhatNow(), taskList);
        else
            assertCommandBehavior(commandWord + " 3", expectedMessage, model.getWhatNow(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", "", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select", "");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskListForSelect(3);

        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        helper.addToModel(model, threeTasks);
        
        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", "todo", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete", "todo");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);
        
        assertCommandBehavior("delete schedule 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

//    @Test
//    public void execute_markDoneInvalidArgsFormat_errorMessageShown() throws Exception {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE);
//        assertIncorrectIndexFormatBehaviorForCommand("done", "todo", expectedMessage);
//    }
//
//    @Test
//    public void execute_markDoneIndexNotFound_errorMessageShown() throws Exception {
//        assertIndexNotFoundBehaviorForCommand("done", "todo");
//    }
//
//    @Test
//    public void execute_markDone_marksCorrectTask() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        List<Task> threeTasks = helper.generateTaskList(3);
//
//        WhatNow expectedAB = helper.generateWhatNow(threeTasks);
//        expectedAB.markTask(threeTasks.get(1));
//        helper.addToModel(model, threeTasks);
//        
//        assertCommandBehavior("done schedule 2",
//                String.format(MarkDoneCommand.MESSAGE_MARK_TASK_SUCCESS, threeTasks.get(1)),
//                expectedAB,
//                expectedAB.getTaskList());
//    }
    /**
     * Confirms the 'invalid argument behaviour' for the given command
     * @param commandWord to test assuming it targets a single task in the last shown list.
     */
    private void assertIncorrectArgsFormatBehavior(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord + " description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " location" + " description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " to" + " description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " C:/Users/Raul/Desktop"+ " description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " location" + "C:/Users/Abernathy/Documents"+ " description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " to" + "C:/Users/Dorain/Desktop" + "description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " locationto" + " C:/Users/Emmet/Documents" + "description Check if command is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " location to" + "C:/Users/Gina/Documents C:/Users/Hamlet/D" + "description Check if command is incorrect", expectedMessage);
    }
    
    /**
     * Confirms the 'invalid argument behaviour' for the given command
     * @param commandWord to test assuming it targets a single task in the last shown list.
     */
    private void assertInvalidPathBehavior(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord + " doesnotexistfolder" + "description Check if path is incorrect", expectedMessage);
        assertCommandBehavior(commandWord + " cs2103projectfolder" + "description Check if path is incorrect", expectedMessage);
    }
    
    @Test
    public void execute_changeLocationInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE);
        assertIncorrectArgsFormatBehavior("change", expectedMessage);
    }

    @Test
    public void execute_changeLocationInvalidPath_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_PATH, ChangeCommand.MESSAGE_USAGE);
        assertInvalidPathBehavior("change location to", expectedMessage);
    }

    @Test
    public void execute_changeLocation_movesToCorrectPath() throws Exception {
        String egPath = "./docs";
        assertCommandBehavior("change location to " + egPath,
                String.format(ChangeCommand.MESSAGE_SUCCESS, egPath + "/whatnow.xml", null, null));
        
        egPath = "./data";
        assertCommandBehavior("change location to " + egPath,
                String.format(ChangeCommand.MESSAGE_SUCCESS, egPath + "/whatnow.xml", null, null));
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
        WhatNow expectedAB = helper.generateWhatNow(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
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
        WhatNow expectedAB = helper.generateWhatNow(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);
        
        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        WhatNow expectedAB = helper.generateWhatNow(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
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

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            String date = "12/12/2017";
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, date, null, null, null, null, null, tags, "incomplete", null);
        }
        
        Task todo(String description, String dateString, String tag01, String tag02) throws Exception {
            Name name = new Name(description);
            String date = dateString;
            Tag tag1 = new Tag(tag01);
            Tag tag2 = new Tag(tag02);
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, date, null, null, null, null, null, tags, "incomplete", null);
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
                    "23/2/2017",
                    null,
                    null,
                    null,
                    null,
                    null,
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    "incomplete",
                    null
            );
        }
        
        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTaskForSelect(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    "incomplete",
                    null
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append("\"" + p.getName().toString() + "\"");
            
            if (p.getTaskDate() != null)
                cmd.append(" on " + p.getTaskDate());

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }
        
        /** Generates the correct update command based on the parameters given */
        String generateUpdateCommand(String type, String value) {
            StringBuffer cmd = new StringBuffer();
            
            cmd.append("update schedule 2 ");
            
            if (type.equals("description")) {
                cmd.append(type + " ");
                cmd.append(value);
            } else if (type.equals("date")) {
                cmd.append(type + " ");
                cmd.append(value);
            } else if (type.equals("tag")) {
                cmd.append(type + " ");
                cmd.append(value);
            }

            return cmd.toString();
        }
        
        /**
         * Generates an WhatNow with auto-generated tasks.
         */
        WhatNow generateWhatNow(int numGenerated) throws Exception{
            WhatNow whatNow = new WhatNow();
            addToWhatNow(whatNow, numGenerated);
            return whatNow;
        }

        /**
         * Generates an WhatNow based on the list of Tasks given.
         */
        WhatNow generateWhatNow(List<Task> tasks) throws Exception{
            WhatNow whatNow = new WhatNow();
            addToWhatNow(whatNow, tasks);
            return whatNow;
        }

        /**
         * Adds auto-generated Task objects to the given WhatNow
         * @param whatNow The WhatNow to which the Tasks will be added
         */
        void addToWhatNow(WhatNow whatNow, int numGenerated) throws Exception{
            addToWhatNow(whatNow, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given WhatNow
         */
        void addToWhatNow(WhatNow whatNow, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                whatNow.addTask(p);
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

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskListForSelect(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTaskForSelect(i));
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
                    new Name(name),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    new UniqueTagList(new Tag("tag")),
                    "incomplete",
                    null
            );
        }
    }
}
