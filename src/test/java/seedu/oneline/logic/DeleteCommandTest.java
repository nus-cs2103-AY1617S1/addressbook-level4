// @@author A0140156R
// Refactored individual command tests from LogicManagerTest into individual classes
// @@author

package seedu.oneline.logic;

import static seedu.oneline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.oneline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.LogicTestManager.TestDataHelper;
import seedu.oneline.logic.commands.DeleteCommand;
import seedu.oneline.logic.commands.HelpCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;

public class DeleteCommandTest extends LogicTestManager {

    @Test
    public void deleteCommand_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("del", expectedMessage);
    }

    @Test
    public void deleteCommand_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("del");
    }

    @Test
    public void deleteCommand_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("del 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }   
    
    //@@author A0121657H
    @Test
    public void deleteCommand_invalidTagInput_showsErrorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedTB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare task book state
        helper.addToModel(model, 2);

        assertCommandBehavior("del #fruit",
                String.format(Tag.MESSAGE_INVALID_TAG, "#fruit"),
                expectedTB,
                expectedList); 
    }
    
    @Test
    public void deleteCommand_validTagInput_removesCorrectTag() throws Exception {
        Tag fruitTag = Tag.getTag("fruit");
        Tag defaultTag = Tag.getDefault();
        
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("eat apple").updateTag(fruitTag);
        Task p2 = helper.generateTaskWithName("boil water").updateTag(defaultTag);
        Task p3 = helper.generateTaskWithName("poke pineapple").updateTag(fruitTag);
        Task p4 = helper.generateTaskWithName("buy pen").updateTag(defaultTag);
        
        List<Task> originalTasks = helper.generateTaskList(p3, p1, p4, p2);
        Collections.sort(originalTasks);
        TaskBook expectedAB = helper.generateTaskBook(originalTasks);
        
        Task p5 = helper.generateTaskWithName("eat apple").updateTag(defaultTag);
        Task p6 = helper.generateTaskWithName("poke pineapple").updateTag(defaultTag);
        List<Task> expectedList = helper.generateTaskList(p2, p4, p5, p6);
        helper.addToModel(model, originalTasks);
        expectedAB.removeTask(p1);
        expectedAB.addTask(p5);
        expectedAB.removeTask(p3);
        expectedAB.addTask(p6);
        
        assertCommandBehavior("del #fruit",
                String.format(DeleteCommand.MESSAGE_DELETE_CAT_SUCCESS, "#fruit"),
                expectedAB,
                expectedList);
    }
    
    //@@author

}
