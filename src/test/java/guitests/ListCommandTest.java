package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListCommand;
import seedu.address.testutil.TestTask;


import static org.junit.Assert.assertTrue;

public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void list_all_Tasks_Events() {
    	TestTask[] currentList = td.getTypicalTasks();
        //list all tasks
        assertListResult("list tasks",td.friend,td.friendEvent,td.lunch,
        		td.book,td.work,td.movie);
        assertResultMessage(String.format(ListCommand.MESSAGE_TASK_SUCCESS));
        
        //list all events
        assertListResult("list events", td.meeting,td.travel);
        assertResultMessage(String.format(ListCommand.MESSAGE_EVENT_SUCCESS));

        //list all items
        assertListResult("list",currentList);
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS));
        
        //list empty lists
        commandBox.runCommand("clear");
        assertListResult("list");
        
        //invalid command
        commandBox.runCommand("lists");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        
    }

    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
