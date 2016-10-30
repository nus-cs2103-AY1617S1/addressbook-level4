package seedu.todo.model.qualifiers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import javafx.collections.transformation.FilteredList;

import seedu.todo.model.DoDoBird;
import seedu.todo.model.expressions.PredicateExpression;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;

public class OnDateQualifierTest {
    
    @Test
    public void onDateQualifer_test() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        DoDoBird ddb = new DoDoBird();
        
        FilteredList<Task> filteredTasks = new FilteredList<>(ddb.getTasks());
        Task toAdd = helper.generateFullTask(1);
        ddb.addTask(toAdd);
        
        filteredTasks.setPredicate((new PredicateExpression(new OnDateQualifier(LocalDateTime.now())))::satisfies);
        assertEquals(filteredTasks.size(), 0);
        
        Task toAddToday = helper.generateTaskWithDates("today", "4 days later");
        ddb.addTask(toAddToday);
        
        Task toAddNoDate = helper.generateTaskWithDates(null, null);
        ddb.addTask(toAddNoDate);
        
        filteredTasks.setPredicate((new PredicateExpression(new OnDateQualifier(LocalDateTime.now())))::satisfies);
        assertEquals(filteredTasks.size(), 1);
    }
    
    
    
}
