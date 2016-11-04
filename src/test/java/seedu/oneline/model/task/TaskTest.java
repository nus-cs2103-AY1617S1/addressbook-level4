//@author: A0138848M
package seedu.oneline.model.task;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import seedu.oneline.testutil.TaskBuilder;
import seedu.oneline.testutil.TestDataHelper;

public class TaskTest {
    Task taskDate0;
    Task taskDate1;
    Task taskNameA;
    Task taskNameB;
    
    @Before
    public void setUp() throws Exception {
        taskDate0 = new Task(new TaskBuilder().withName("A").withDeadline("1").build());
        taskDate1 = new Task(new TaskBuilder().withName("A").withDeadline("2").build());
        taskNameA = TestDataHelper.generateTaskWithName("A");
        taskNameB = TestDataHelper.generateTaskWithName("B");
    }

    @Test
    public void taskComparing_task_sortsByDate() {
        assertTrue(taskDate0.compareTo(taskDate1) < 0);
    }

    @Test
    public void taskComparing_task_sortsByName() {
        assertTrue(taskNameA.compareTo(taskNameB) < 0);
    }
}
