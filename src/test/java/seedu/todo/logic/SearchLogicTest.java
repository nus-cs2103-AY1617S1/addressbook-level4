package seedu.todo.logic;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.Command;
import seedu.todo.logic.commands.SearchCommand;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.logic.commands.SearchCommand.SearchIndex;
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
    public void execute_search_all() throws IllegalValueException {
        List<Task> expectedList = helper.generateReverseTaskList(t3, t1, t4, t2);
        
        helper.addToModel(model, fourTasks);
        expectedTDL = helper.generateToDoList(fourTasks);
        
        assertCommandBehavior("search all",
                String.format(SearchCommand.MESSAGE_SUCCESS, "", SearchIndex.ALL, "")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
        
    }
    
    
    @Test
    public void execute_search_onlyMatchesFullWordsInNames() throws IllegalValueException {
        t2.setCompletion(new Completion(true));
        List<Task> expectedList = helper.generateReverseTaskList(t1);
        List<Task> expectedListDone = helper.generateReverseTaskList(t2);
        
        helper.addToModel(model, fourTasks);
        expectedTDL = helper.generateToDoList(fourTasks);
        
        assertCommandBehavior("search KEY",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE, 
                        SearchIndex.KEYWORD, "KEY")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
        
        assertCommandBehavior("search KEY done",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.DONE,
                        SearchIndex.KEYWORD, "KEY")
                      + Command.getMessageForTaskListShownSummary(expectedListDone.size()),
                expectedTDL,
                expectedListDone);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws IllegalValueException {
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        helper.addToModel(model, fourTasks);
        expectedTDL = helper.generateToDoList(fourTasks);
        
        assertCommandBehavior("search KEY",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE, 
                        SearchIndex.KEYWORD, "KEY")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }

    @Test
    public void execute_search_matchesIfTagPresent() throws IllegalValueException {        
        t1.addTag(new Tag("school"));
        t2.addTag(new Tag("school"));
        t2.setCompletion(new Completion(true));
        
        DoDoBird expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateTaskList(t1);
        List<Task> expectedListDone = helper.generateTaskList(t2);
        
        helper.addToModel(model, twoTasks);
        expectedTDL = helper.generateToDoList(twoTasks);
        
        assertCommandBehavior("search tag school",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE,
                        SearchIndex.TAG, "school")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
        
        assertCommandBehavior("search tag school done",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.DONE,
                        SearchIndex.TAG, "school")
                      + Command.getMessageForTaskListShownSummary(expectedListDone.size()),
                expectedTDL,
                expectedListDone);
        
    }
  
    
    @Test
    public void execute_search_matchesIfBefore() throws IllegalValueException {
        DoDoBird expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        
        helper.addToModel(model, twoTasks);
        expectedTDL = helper.generateToDoList(twoTasks);
        
        assertCommandBehavior("search before 12/12/2019",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE,
                        SearchIndex.BEFORE, "12/12/2019")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
        
        t1.setCompletion(new Completion(true));
        List<Task> expectedListDone = helper.generateReverseTaskList(t1);
        assertCommandBehavior("search before 12/12/2019 done",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.DONE,
                        SearchIndex.BEFORE, "12/12/2019")
                      + Command.getMessageForTaskListShownSummary(expectedListDone.size()),
                expectedTDL,
                expectedListDone);
        
        assertCommandBehavior("search before 12/12/2019 all",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.ALL,
                        SearchIndex.BEFORE, "12/12/2019 ")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfAfter() throws IllegalValueException {
        expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search after 12/12/2013",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE,
                        SearchIndex.AFTER, "12/12/2013")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
        
        t1.setCompletion(new Completion(true));
        List<Task> expectedListDone = helper.generateReverseTaskList(t1);
        assertCommandBehavior("search after 12/12/2013 done",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.DONE,
                        SearchIndex.AFTER, "12/12/2013")
                      + Command.getMessageForTaskListShownSummary(expectedListDone.size()),
                expectedTDL,
                expectedListDone);
        
        assertCommandBehavior("search after 12/12/2013 all",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.ALL,
                        SearchIndex.AFTER, "12/12/2013 ")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfFromTill() throws IllegalValueException {
        expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search from 12/12/2013 to 12/12/2019",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE,
                        SearchIndex.FT, "12/12/2013@12/12/2019")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesIfOn() throws IllegalValueException {
        Task t1 = helper.generateTaskWithDates("today", "2 days later");
        Task t2 = helper.generateTaskWithDates("today", "tomorrow");
        t2.setCompletion(new Completion(true));
        
        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        DoDoBird expectedTDL = helper.generateToDoList(twoTasks);
        
        List<Task> expectedList = helper.generateReverseTaskList(t1);
        List<Task> expectedListDone = helper.generateReverseTaskList(t2);
        List<Task> expectedListAll = helper.generateReverseTaskList(t1, t2);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search on today",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE,
                        SearchIndex.ON, "today")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
        
        assertCommandBehavior("search on today 1400",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE,
                        SearchIndex.ON, "today 1400")
                      + Command.getMessageForTaskListShownSummary(0),
                expectedTDL,
                (new DoDoBird()).getTaskList());
        
        assertCommandBehavior("search on today done",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.DONE,
                        SearchIndex.ON, "today")
                      + Command.getMessageForTaskListShownSummary(expectedListDone.size()),
                expectedTDL,
                expectedListDone);
        
        assertCommandBehavior("search on today all",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.ALL,
                        SearchIndex.ON, "today ")
                      + Command.getMessageForTaskListShownSummary(expectedListAll.size()),
                expectedTDL,
                expectedListAll);
    }
    
    @Test
    public void execute_search_matchesIfFloating() throws IllegalValueException {
        Task t1 = helper.generateTaskWithDates(null, null);
        Task t2 = helper.generateTaskWithDates("today", "tomorrow");
        
        List<Task> twoTasks = helper.generateTaskList(t1, t2);
        DoDoBird expectedTDL = helper.generateToDoList(twoTasks);
        
        List<Task> expectedList = helper.generateReverseTaskList(t1);
        List<Task> expectedListDone = helper.generateReverseTaskList(t1);
        
        helper.addToModel(model, twoTasks);
        
        assertCommandBehavior("search floating",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE,
                        SearchIndex.FLOATING, "")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
        
        t1.setCompletion(new Completion(true));
        assertCommandBehavior("search floating done",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.DONE,
                        SearchIndex.FLOATING, "")
                      + Command.getMessageForTaskListShownSummary(expectedListDone.size()),
                expectedTDL,
                expectedListDone);
        
    }
    
    
    @Test
    public void execute_search_matchesIfDone() throws IllegalValueException {        
        t1.setCompletion(new Completion(true));
        expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t1);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search done",
                String.format(SearchCommand.MESSAGE_SUCCESS, "", SearchIndex.DONE, "")
                    + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    
    @Test
    public void execute_search_matchesIfUndone() throws IllegalValueException {        
        t1.setCompletion(new Completion(true));
        expectedTDL = helper.generateToDoList(twoTasks);
        List<Task> expectedList = helper.generateReverseTaskList(t2);
        
        helper.addToModel(model, twoTasks);

        assertCommandBehavior("search",
                String.format(SearchCommand.MESSAGE_SUCCESS, "", SearchIndex.UNDONE, "")
                    + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
    }
    
    @Test
    public void execute_search_matchesPriority() throws IllegalValueException {        
        Task t1 = helper.generateFullTaskPriorityMid(1);
        Task t2 = helper.generateFullTaskPriorityMid(2);
        Task t3 = helper.generateFullTaskPriorityHigh(1);
        
        List<Task> threeTasks = helper.generateTaskList(t1, t2, t3);
        DoDoBird expectedTDL = helper.generateToDoList(threeTasks);
        
        List<Task> expectedList = helper.generateReverseTaskList(t1, t2);
        List<Task> expectedListDone = helper.generateReverseTaskList(t1);
        
        helper.addToModel(model, threeTasks);
        
        assertCommandBehavior("search priority mid",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.UNDONE,
                        SearchIndex.PRIORITY, "mid")
                      + Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTDL,
                expectedList);
        
        t1.setCompletion(new Completion(true));
        assertCommandBehavior("search priority mid done",
                String.format(SearchCommand.MESSAGE_SUCCESS, SearchCompletedOption.DONE,
                        SearchIndex.PRIORITY, "mid")
                      + Command.getMessageForTaskListShownSummary(expectedListDone.size()),
                expectedTDL,
                expectedListDone);
    }
    
    
    
}
