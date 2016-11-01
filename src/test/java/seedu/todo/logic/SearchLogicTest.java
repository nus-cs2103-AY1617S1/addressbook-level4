package seedu.todo.logic;

import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.Command;
import seedu.todo.logic.commands.SearchCommand;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.Completion;
import seedu.todo.model.task.Task;

/**
 * Test class for the search command's logic
 */
public class SearchLogicTest extends CommandLogicTest {
    
    private Task t1, t2, t3, t4;
    List<Task> fourTasks, twoTasks;
    
    @Before
    public void search_setup() throws IllegalValueException {
        t1 = helper.generateTaskWithName("bla bla KEY bla");
        t2 = helper.generateTaskWithName("bla key bla bceofeia");
        t3 = helper.generateTaskWithName("KE Y");
        t4 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        
        fourTasks = helper.generateTaskList(t3, t1, t4, t2);
        twoTasks = helper.generateTaskList(t1, t2);
        
    }
    
    @Test
    public void execute_search_invalidArgsFormat() throws IllegalValueException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE);
        assertCommandBehavior("search ", expectedMessage);
    }

    @Test
    public void execute_search_onlyMatchesFullWordsInNames() throws IllegalValueException {
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        helper.addToModel(model, fourTasks);
        expectedTDL = helper.generateToDoList(fourTasks);
        
        assertCommandBehavior("search KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws IllegalValueException {
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        helper.addToModel(model, fourTasks);
        expectedTDL = helper.generateToDoList(fourTasks);
        
        assertCommandBehavior("search KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }

    @Test
    public void execute_search_matchesIfTagPresent() throws IllegalValueException {        
        t1.addTag(new Tag("school"));

        DoDoBird expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateTaskList(t1);
        
        helper.addToModel(model, twoTasks);
        expectedTDL = helper.generateToDoList(twoTasks);
        
        assertCommandBehavior("search tag school",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
  
    
    @Test
    public void execute_search_matchesIfBefore() throws IllegalValueException {
        DoDoBird expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        
        helper.addToModel(model, twoTasks);
        expectedTDL = helper.generateToDoList(twoTasks);
        
        assertCommandBehavior("search before 12/12/2019",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfAfter() throws IllegalValueException {
        expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search after 12/12/2013",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfFromTill() throws IllegalValueException {
        expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search from 12/12/2013 to 12/12/2019",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfOn() throws IllegalValueException {
        Task t1 = helper.generateTaskWithName("bla bla KEY bla");
        Task t2 = helper.generateTaskWithDates("today", "tomorrow");

        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        DoDoBird expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t2);
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search on today",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    
    @Test
    public void execute_search_matchesIfDone() throws IllegalValueException {        
        t1.setCompletion(new Completion(true));
        expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t1);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search done",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    
    @Test
    public void execute_search_matchesIfUndone() throws IllegalValueException {        
        t1.setCompletion(new Completion(true));
        expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t2);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search undone",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
}
