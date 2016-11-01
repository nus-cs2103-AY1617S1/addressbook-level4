package guitests;

import org.junit.Test;

import seedu.address.logic.commands.DoneCommand;
import seedu.address.testutil.TestTask;

//@@author A0121261Y
public class MarkCommandTest extends AddressBookGuiTest {

    @Test
    public void mark() {

        //mark the first in the list
        TestTask[] currentList = td.getTypicalTask();
        commandBox.runCommandAndConfirm("clear");
        commandBox.runCommand(td.hoon.getAddCommand());
        int targetIndex = 1;
        commandBox.runCommand("done " + targetIndex);
        td.hoon.setComplete(!td.hoon.getCompleted());
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, td.hoon.getName(),
                td.hoon.getCompleted() == true ? "Completed" : "Incomplete"));
    }
}
