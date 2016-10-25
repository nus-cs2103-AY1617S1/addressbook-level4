//@author: A0138848M
package seedu.oneline.model.task;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import seedu.oneline.testutil.TestDataHelper;

public class TaskTest {
    Task taskDate0;
    Task taskDate1;
    Task taskNameA;
    Task taskNameB;
    
    @Before
    public void setUp() throws Exception {
        taskDate0 = TestDataHelper.generateTask(0);
        taskDate1 = TestDataHelper.generateTask(1);
        taskNameA = TestDataHelper.generateTaskWithName("A");
        taskNameB = TestDataHelper.generateTaskWithName("B");
    }

    @Test
    public void task_sortsByDate() {
        assertTrue(taskDate0.compareTo(taskDate1) < 0);
    }

    @Test
    public void task_sortsByName() {
        assertTrue(taskNameA.compareTo(taskNameB) < 0);
    }
}
