package seedu.todo.model.qualifiers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.expressions.PredicateExpression;
import seedu.todo.model.task.Completion;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;
//@@author A0093896H
/**
 * Test class for FoatingQualifier used in model when searching for floating tasks
 */
public class FloatingQualifierTest {
    private TestDataHelper helper;
    private DoDoBird ddb;
    
    @Before
    public void setup() {
        helper = new TestDataHelper();
        ddb = new DoDoBird();
    }

    @Test
    public void floatingQualifer_test() throws Exception {
        FilteredList<Task> filteredTasks = new FilteredList<>(ddb.getTasks());
        Task toAdd = helper.generateTaskWithDates(null, null);
        ddb.addTask(toAdd);
        
        filteredTasks.setPredicate((new PredicateExpression(
                new FloatingQualifier(SearchCompletedOption.ALL))::satisfies));
        assertEquals(filteredTasks.size(), 1);
        
        toAdd.setCompletion(new Completion(true));
        
        filteredTasks.setPredicate((new PredicateExpression(
                new FloatingQualifier(SearchCompletedOption.UNDONE))::satisfies));
        assertEquals(filteredTasks.size(), 0);
        
        filteredTasks.setPredicate((new PredicateExpression(
                new FloatingQualifier(SearchCompletedOption.DONE))::satisfies));
        assertEquals(filteredTasks.size(), 1);
        
    }
}
