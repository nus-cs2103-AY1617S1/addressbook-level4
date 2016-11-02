package tars.logic;

import java.util.List;

import org.junit.Test;

import tars.model.Tars;
import tars.model.task.Status;
import tars.model.task.Task;

/**
 * Logic command test for ud
 * 
 * @@author A0121533W
 */
public class UdLogicCommandTest extends LogicCommandTest {
    @Test
    public void execute_mark_allTaskAsUndone() throws Exception {
        Status done = new Status(Status.DONE);

        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");
        task1.setStatus(done);
        task2.setStatus(done);

        List<Task> taskList = helper.generateTaskList(task1, task2);

        Tars expectedTars = new Tars();
        helper.addToModel(model, taskList);

        Task task1Expected = helper.generateTaskWithName("task1");
        Task task2Expected = helper.generateTaskWithName("task2");

        expectedTars.addTask(task1Expected);
        expectedTars.addTask(task2Expected);

        assertCommandBehavior("ud 1 2",
                "Task: 1, 2 marked undone successfully.\n", expectedTars,
                expectedTars.getTaskList());
    }

    @Test
    public void execute_mark_alreadyUndone() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");

        List<Task> taskList = helper.generateTaskList(task1, task2);

        Tars expectedTars = new Tars();
        helper.addToModel(model, taskList);

        Task task1Expected = helper.generateTaskWithName("task1");
        Task task2Expected = helper.generateTaskWithName("task2");

        expectedTars.addTask(task1Expected);
        expectedTars.addTask(task2Expected);

        assertCommandBehavior("ud 1 2", "Task: 1, 2 already marked undone.\n",
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_mark_rangeUndone() throws Exception {
        Status done = new Status(Status.DONE);

        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");
        Task task3 = helper.generateTaskWithName("task3");

        task1.setStatus(done);
        task2.setStatus(done);
        task3.setStatus(done);

        List<Task> taskList = helper.generateTaskList(task1, task2, task3);

        Tars expectedTars = new Tars();
        helper.addToModel(model, taskList);

        Task task1Expected = helper.generateTaskWithName("task1");
        Task task2Expected = helper.generateTaskWithName("task2");
        Task task3Expected = helper.generateTaskWithName("task3");

        expectedTars.addTask(task1Expected);
        expectedTars.addTask(task2Expected);
        expectedTars.addTask(task3Expected);

        assertCommandBehavior("ud 1..3",
                "Task: 1, 2, 3 marked undone successfully.\n", expectedTars,
                expectedTars.getTaskList());
    }
}
