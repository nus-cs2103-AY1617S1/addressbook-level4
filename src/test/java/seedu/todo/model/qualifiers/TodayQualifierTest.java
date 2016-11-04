//@@author A0138967J
package seedu.todo.model.qualifiers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.expressions.PredicateExpression;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;

public class TodayQualifierTest {
    
    private TestDataHelper helper;
    private DoDoBird ddb;
    private TodayDateQualifier tdq;
    
    @Before
    public void setup() {
        helper = new TestDataHelper();
        ddb = new DoDoBird();
        tdq = new TodayDateQualifier(LocalDateTime.now());
    }
    
    @Test
    public void todayQualifer_test() throws Exception {
        FilteredList<Task> todayTasks = new FilteredList<>(ddb.getTasks());
        Task todayTask = helper.generateFullTaskToday(1);
        ddb.addTask(todayTask);
        todayTasks.setPredicate((new PredicateExpression(
                new TodayDateQualifier(LocalDateTime.now()))::satisfies));
        assertEquals(todayTasks.size(), 1);
        assertEquals(tdq.run(todayTask), true);
        
        Task tmrTask = helper.generateFullTaskTmr(0);
        ddb.addTask(tmrTask);
        todayTasks.setPredicate((new PredicateExpression(
                new TodayDateQualifier(LocalDateTime.now()))::satisfies));
        assertEquals(todayTasks.size(), 1);
        assertEquals(tdq.run(tmrTask), false);
    }
}
