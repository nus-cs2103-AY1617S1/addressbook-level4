package tars.logic;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import tars.logic.commands.ListCommand;
import tars.model.Tars;
import tars.model.task.DateTime;
import tars.model.task.Priority;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Task;

/**
 * Logic command test for list
 * 
 * @@author A0140022H
 */
public class ListLogicCommandTest extends LogicCommandTest {

    @Test
    public void execute_list_invalidFlagsErrorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("ls -", expectedMessage);
    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Tars expectedTars = helper.generateTars(2);
        List<? extends ReadOnlyTask> expectedList = expectedTars.getTaskList();

        // prepare tars state
        helper.addToModel(model, 2);

        assertCommandBehavior("ls", ListCommand.MESSAGE_SUCCESS, expectedTars,
                expectedList);
    }

    @Test
    public void execute_list_showsAllTasksByPriority() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");
        Task task3 = helper.generateTaskWithName("task3");
        task1.setPriority(new Priority("l"));
        task2.setPriority(new Priority("m"));
        task3.setPriority(new Priority("h"));
        Tars expectedTars = new Tars();
        expectedTars.addTask(task3);
        expectedTars.addTask(task2);
        expectedTars.addTask(task1);
        List<Task> listToSort = helper.generateTaskList(task3, task2, task1);
        List<Task> expectedList = helper.generateTaskList(task1, task2, task3);
        helper.addToModel(model, listToSort);

        assertCommandBehaviorForList("ls /p",
                ListCommand.MESSAGE_SUCCESS_PRIORITY, expectedTars,
                expectedList);
    }

    @Test
    public void execute_list_showsAllTasksByPriorityDescending()
            throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");
        Task task3 = helper.generateTaskWithName("task3");
        task1.setPriority(new Priority("l"));
        task2.setPriority(new Priority("m"));
        task3.setPriority(new Priority("h"));
        Tars expectedTars = new Tars();
        expectedTars.addTask(task1);
        expectedTars.addTask(task2);
        expectedTars.addTask(task3);
        List<Task> listToSort = helper.generateTaskList(task1, task2, task3);
        List<Task> expectedList = helper.generateTaskList(task3, task2, task1);
        helper.addToModel(model, listToSort);

        assertCommandBehaviorForList("ls /p dsc",
                ListCommand.MESSAGE_SUCCESS_PRIORITY_DESCENDING, expectedTars,
                expectedList);
    }

    @Test
    public void execute_list_showsAllTasksByDatetime() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");
        Task task3 = helper.generateTaskWithName("task3");
        task1.setDateTime(new DateTime("", "01/02/2016 1600"));
        task2.setDateTime(new DateTime("", "02/02/2016 1600"));
        task3.setDateTime(new DateTime("", "03/02/2016 1600"));
        Tars expectedTars = new Tars();
        expectedTars.addTask(task3);
        expectedTars.addTask(task2);
        expectedTars.addTask(task1);
        List<Task> listToSort = helper.generateTaskList(task3, task2, task1);
        List<Task> expectedList = helper.generateTaskList(task1, task2, task3);
        helper.addToModel(model, listToSort);

        assertCommandBehaviorForList("ls /dt",
                ListCommand.MESSAGE_SUCCESS_DATETIME, expectedTars,
                expectedList);
    }

    @Test
    public void execute_list_showsAllTasksByDatetimeDescending()
            throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");
        Task task3 = helper.generateTaskWithName("task3");
        task1.setDateTime(new DateTime("", "01/02/2016 1600"));
        task2.setDateTime(new DateTime("", "02/02/2016 1600"));
        task3.setDateTime(new DateTime("", "03/02/2016 1600"));
        Tars expectedTars = new Tars();
        expectedTars.addTask(task1);
        expectedTars.addTask(task2);
        expectedTars.addTask(task3);
        List<Task> listToSort = helper.generateTaskList(task1, task2, task3);
        List<Task> expectedList = helper.generateTaskList(task3, task2, task1);
        helper.addToModel(model, listToSort);

        assertCommandBehaviorForList("ls /dt dsc",
                ListCommand.MESSAGE_SUCCESS_DATETIME_DESCENDING, expectedTars,
                expectedList);
    }
}
