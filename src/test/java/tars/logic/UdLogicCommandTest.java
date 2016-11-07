package tars.logic;

import java.util.List;

import org.junit.Test;

import tars.commons.exceptions.DuplicateTaskException;
import tars.model.Tars;
import tars.model.task.Status;
import tars.model.task.Task;

// @@author A0121533W
/**
 * Logic command test for ud
 */
public class UdLogicCommandTest extends LogicCommandTest {
    
    private static final int NUM_TASKS_IN_LIST = 2;
    private static final int NUM_TASKS_IN_RANGE = 5;
    
    @Test
    public void execute_mark_allTaskAsUndone() throws Exception {

        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        generateTestTars(NUM_TASKS_IN_LIST, helper, true);

        Tars expectedTars = new Tars();

        generateExpectedTars(NUM_TASKS_IN_LIST, helper, expectedTars);

        assertCommandBehavior("ud 1 2",
                "Task: 1, 2 marked undone successfully.\n", expectedTars,
                expectedTars.getTaskList());
    }

    @Test
    public void execute_mark_alreadyUndone() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        generateTestTars(NUM_TASKS_IN_LIST, helper, false);

        Tars expectedTars = new Tars();
        generateExpectedTars(NUM_TASKS_IN_LIST, helper, expectedTars);

        assertCommandBehavior("ud 1 2", "Task: 1, 2 already marked undone.\n",
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_mark_rangeUndone() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        generateTestTars(NUM_TASKS_IN_RANGE, helper, true);

        Tars expectedTars = new Tars();

        generateExpectedTars(NUM_TASKS_IN_RANGE, helper, expectedTars);

        assertCommandBehavior("ud 1..5",
                "Task: 1, 2, 3, 4, 5 marked undone successfully.\n", expectedTars,
                expectedTars.getTaskList());
    }

    private void generateTestTars(int numTasks, TypicalTestDataHelper helper, boolean status)
            throws Exception {
        Status s = new Status(status);
        
        Task[] taskArray = new Task[numTasks];
        for (int i = 1; i < numTasks + 1; i++) {
            String name = "task " + String.valueOf(i);
            Task taskI = helper.generateTaskWithName(name);
            taskI.setStatus(s);
            taskArray[i-1] = taskI;
        }

        List<Task> taskList = helper.generateTaskList(taskArray);

        helper.addToModel(model, taskList);
    }

    private void generateExpectedTars(int numTasks, TypicalTestDataHelper helper,
            Tars expectedTars) throws Exception, DuplicateTaskException {
        
        for (int i = 1; i < numTasks + 1; i++) {
            String name = "task " + String.valueOf(i);
            Task taskI = helper.generateTaskWithName(name);
            expectedTars.addTask(taskI);
        }
    }
}
