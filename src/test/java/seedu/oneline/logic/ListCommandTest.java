//@@author A0121657H

package seedu.oneline.logic;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.oneline.logic.LogicTestManager.TestDataHelper;
import seedu.oneline.logic.commands.ListCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;

public class ListCommandTest extends LogicTestManager {
    
    @Test
    public void listCommand_list_showsAllTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedTB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare task book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedTB,
                expectedList);
    }
   
    @Test
    public void list_undoneInput_showsAllUndoneTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("eat apple");
        Task p2 = helper.generateTaskWithName("boil water").markDone();
        Task p3 = helper.generateTaskWithName("poke pineapple");
        Task p4 = helper.generateTaskWithName("buy pen").markDone();
        
        List<Task> originalTasks = helper.generateTaskList(p3, p1, p4, p2);
        Collections.sort(originalTasks);
        TaskBook expectedAB = helper.generateTaskBook(originalTasks);
        List<Task> expectedList = helper.generateTaskList(p1, p3);
        helper.addToModel(model, originalTasks);
        model.updateFilteredListToShowAllNotDone();

        assertCommandBehavior("list undone",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }
    
    @Test
    public void list_doneInput_showsAllDoneTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("eat apple");
        Task p2 = helper.generateTaskWithName("boil water").markDone();
        Task p3 = helper.generateTaskWithName("poke pineapple");
        Task p4 = helper.generateTaskWithName("buy pen").markDone();
        
        List<Task> originalTasks = helper.generateTaskList(p3, p1, p4, p2);
        Collections.sort(originalTasks);
        TaskBook expectedAB = helper.generateTaskBook(originalTasks);
        List<Task> expectedList = helper.generateTaskList(p2, p4);
        helper.addToModel(model, originalTasks);
        model.updateFilteredListToShowAllDone();

        assertCommandBehavior("list done",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }
    //@@author
}
