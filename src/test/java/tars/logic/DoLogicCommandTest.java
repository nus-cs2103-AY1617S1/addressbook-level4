package tars.logic;

import java.util.List;

import org.junit.Test;

import tars.model.Tars;
import tars.model.task.Status;
import tars.model.task.Task;

/**
 * Logic command test for do
 */
public class DoLogicCommandTest extends LogicCommandTest {
    @Test
    public void execute_mark_allTaskAsDone() throws Exception {
        Status done = new Status(Status.DONE);
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");

        List<Task> taskList = helper.generateTaskList(task1, task2);

        Tars expectedTars = new Tars();
        helper.addToModel(model, taskList);

        Task task1Expected = helper.generateTaskWithName("task1");
        Task task2Expected = helper.generateTaskWithName("task2");
        task1Expected.setStatus(done);
        task2Expected.setStatus(done);

        expectedTars.addTask(task1Expected);
        expectedTars.addTask(task2Expected);

        assertCommandBehavior("do 1 2",
                "Task: 1, 2 marked done successfully.\n", expectedTars,
                expectedTars.getTaskList());
    }

    @Test
    public void execute_mark_alreadyDone() throws Exception {
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
        task1Expected.setStatus(done);
        task2Expected.setStatus(done);

        expectedTars.addTask(task1Expected);
        expectedTars.addTask(task2Expected);

        assertCommandBehavior("do 1 2", "Task: 1, 2 already marked done.\n",
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_mark_rangeDone() throws Exception {
        Status done = new Status(Status.DONE);
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task task1 = helper.generateTaskWithName("task1");
        Task task2 = helper.generateTaskWithName("task2");
        Task task3 = helper.generateTaskWithName("task3");

        List<Task> taskList = helper.generateTaskList(task1, task2, task3);

        Tars expectedTars = new Tars();
        helper.addToModel(model, taskList);

        Task task1Expected = helper.generateTaskWithName("task1");
        Task task2Expected = helper.generateTaskWithName("task2");
        Task task3Expected = helper.generateTaskWithName("task3");
        task1Expected.setStatus(done);
        task2Expected.setStatus(done);
        task3Expected.setStatus(done);

        expectedTars.addTask(task1Expected);
        expectedTars.addTask(task2Expected);
        expectedTars.addTask(task3Expected);

        assertCommandBehavior("do 1..3",
                "Task: 1, 2, 3 marked done successfully.\n", expectedTars,
                expectedTars.getTaskList());
    }
}
