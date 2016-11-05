package seedu.oneline.logic;

import java.util.List;

import org.junit.Test;

import seedu.oneline.logic.LogicTestManager.TestDataHelper;
import seedu.oneline.logic.commands.ListCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.ReadOnlyTask;

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
}
