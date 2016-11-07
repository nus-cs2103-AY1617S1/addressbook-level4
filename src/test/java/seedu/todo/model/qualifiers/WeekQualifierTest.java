
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


//@@author A0138967J
/**
 * Test class for WeekDateQualifier used in model when searching
 * for tasks that fall in the next 7 days, and deciding whether to include it in the next 7 days
 * list
 */
public class WeekQualifierTest {
    
    private TestDataHelper helper;
    private DoDoBird ddb;
    private WeekDateQualifier wdq;
    
    @Before
    public void setup() {
        helper = new TestDataHelper();
        ddb = new DoDoBird();
        wdq = new WeekDateQualifier(LocalDateTime.now());
    }
    
    @Test
    public void weekQualifer_test() throws Exception {
        FilteredList<Task> weekTasks = new FilteredList<>(ddb.getTasks());
        
        Task tmrTask = helper.generateFullTaskTmr(0);
        ddb.addTask(tmrTask);
        weekTasks.setPredicate((new PredicateExpression(
                new WeekDateQualifier(LocalDateTime.now()))::satisfies));
        assertEquals(weekTasks.size(), 1);
        assertEquals(wdq.run(tmrTask), true);
    }
}
