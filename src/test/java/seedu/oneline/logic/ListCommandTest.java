// @@author A0140156R
// Refactored individual command tests from LogicManagerTest into individual classes
//@@author A0121657H

package seedu.oneline.logic;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.logic.LogicTestManager.TestDataHelper;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.logic.commands.ListCommand;
import seedu.oneline.logic.commands.ListTagCommand;
import seedu.oneline.logic.commands.LocationCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;

public class ListCommandTest extends LogicTestManager {
    
    @Test
    public void listCommand_invalidInput_showsErrorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedTB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare task book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list abc",
                ListCommand.MESSAGE_INVALID,
                expectedTB,
                expectedList);      
    }
    
    @Test
    public void listCommand_list_showsAllTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedTB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare task book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                String.format(ListCommand.MESSAGE_SUCCESS, "all"),
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
                String.format(ListCommand.MESSAGE_SUCCESS, "undone"),
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
                String.format(ListCommand.MESSAGE_SUCCESS, "done"),
                expectedAB,
                expectedList);
    }
    
    @Test
    public void list_invalidTagInput_showsErrorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedTB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare task book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list #fruit",
                String.format(Tag.MESSAGE_INVALID_TAG, "fruit"),
                expectedTB,
                expectedList); 
    }
    
    @Test
    public void list_validTagInput_showsAllTasksWithTag() throws Exception {
        Tag fruitTag= Tag.getTag("fruit");
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("eat apple").updateTag(fruitTag);
        Task p2 = helper.generateTaskWithName("boil water");
        Task p3 = helper.generateTaskWithName("poke pineapple").updateTag(fruitTag);
        Task p4 = helper.generateTaskWithName("buy pen");
        
        List<Task> originalTasks = helper.generateTaskList(p3, p1, p4, p2);
        Collections.sort(originalTasks);
        TaskBook expectedAB = helper.generateTaskBook(originalTasks);
        List<Task> expectedList = helper.generateTaskList(p1, p3);
        helper.addToModel(model, originalTasks);

        assertCommandBehavior("list #fruit",
                String.format(ListTagCommand.MESSAGE_SUCCESS, "fruit"),
                expectedAB,
                expectedList);
    }
    //@@author
}
