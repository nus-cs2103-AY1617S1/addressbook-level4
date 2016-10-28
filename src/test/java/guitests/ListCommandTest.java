package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListCommand;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0142325R
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
        
        //list all done items
        commandBox.runCommand("done 1");
        td.friend.markAsDone();
        assertListResult("list done", td.friend);
        assertResultMessage(String.format(ListCommand.MESSAGE_LIST_DONE_TASK_SUCCESS));
        
        // list all undone items
        assertListResult("list undone", td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting, td.travel);
        assertResultMessage(String.format(ListCommand.MESSAGE_LIST_UNDONE_TASK_SUCCESS));
        
        //list empty lists
        commandBox.runCommand("clear");
        assertListResult("list");
        
        //invalid command
        commandBox.runCommand("lists");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("list unknown");
        assertResultMessage(ListCommand.MESSAGE_INVALID_LIST_COMMAND);
        
    }

    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
