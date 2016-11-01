//@@author A0093896H
package seedu.todo.model.qualifiers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.expressions.PredicateExpression;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;

/**
 * Test class for OnDateQualifier used in model when searching
 * for tasks that fall on a certain date
 */
public class OnDateQualifierTest {
    
    TestDataHelper helper;
    DoDoBird ddb;
    
    @Before
    public void setup() {
        helper = new TestDataHelper();
        ddb = new DoDoBird();
    }
    
    @Test
    public void onDateQualifer_test() throws Exception {
        FilteredList<Task> filteredTasks = new FilteredList<>(ddb.getTasks());
        Task toAdd = helper.generateFullTask(1);
        ddb.addTask(toAdd);
        
        filteredTasks.setPredicate((new PredicateExpression(
                new OnDateQualifier(LocalDateTime.now(), SearchCompletedOption.ALL)))::satisfies);
        assertEquals(filteredTasks.size(), 0);
        
        Task toAddToday = helper.generateTaskWithDates("today", "4 days later");
        ddb.addTask(toAddToday);
        
        Task toAddNoDate = helper.generateTaskWithDates(null, null);
        ddb.addTask(toAddNoDate);
        
        filteredTasks.setPredicate((new PredicateExpression(
                new OnDateQualifier(LocalDateTime.now(), SearchCompletedOption.ALL)))::satisfies);
        assertEquals(filteredTasks.size(), 1);
    }
    
    
    
}
