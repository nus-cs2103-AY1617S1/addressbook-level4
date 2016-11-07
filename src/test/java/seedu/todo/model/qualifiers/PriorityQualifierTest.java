package seedu.todo.model.qualifiers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.expressions.PredicateExpression;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;
//@@author A0121643R
/**
 * Test class for priority Qualifier used in model
 */
public class PriorityQualifierTest {
    
    private TestDataHelper helper;
    private DoDoBird ddb;
    
    @Before
    public void setup() {
        helper = new TestDataHelper();
        ddb = new DoDoBird();
    }

    @Test
    public void priorityQualifer_test() throws Exception {
        FilteredList<Task> filteredTasks = new FilteredList<>(ddb.getTasks());
        Task toAdd = helper.generateFullTask(1);
        ddb.addTask(toAdd);
        
        filteredTasks.setPredicate((new PredicateExpression(
                new PriorityQualifier(new Priority(Priority.LOW), SearchCompletedOption.ALL))::satisfies));
        assertEquals(filteredTasks.size(), 1);
        
        toAdd.setPriority(new Priority(Priority.HIGH));
        
        filteredTasks.setPredicate((new PredicateExpression(
                new PriorityQualifier(new Priority(Priority.LOW), SearchCompletedOption.ALL))::satisfies));
        assertEquals(filteredTasks.size(), 0);
        
        filteredTasks.setPredicate((new PredicateExpression(
                new PriorityQualifier(new Priority(Priority.HIGH), SearchCompletedOption.UNDONE))::satisfies));
        assertEquals(filteredTasks.size(), 1);
        
    }
    
    
    
    
    
}
