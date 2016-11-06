package guitests;


import org.junit.Test;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.logic.commands.ShowCommand;
import seedu.jimi.model.task.ReadOnlyTask;
//@@author A0138915X
public class ShowCommandTest extends AddressBookGuiTest {

    
    @Test
    public void show_floatingTasks() {
        ReadOnlyTask[] currentList = td.getTypicalTasks();
        int listSize = currentList.length;
        assertShowResult("show floating", listSize);
    }
    
    //tests dynamic day titles
    @Test
    public void show_Tuesday() {
        assertShowResult("show Tuesday", 0);
    }
    
    @Test
    public void show_invalidDay() {
        commandBox.runCommand("show floatingtasks");
        assertResultMessage(String.format(ShowCommand.MESSAGE_INVALID_SECTION));
    }

    private void assertShowResult(String command, int listSize) {
        commandBox.runCommand(command);
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, listSize));
    }
}
