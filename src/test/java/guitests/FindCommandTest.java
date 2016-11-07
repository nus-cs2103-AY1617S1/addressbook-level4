package guitests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.testutil.TestUtil;

import static org.junit.Assert.*;

//@@author A0138978E
public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void findCommand_nonEmptyList() {
        List<Task> emptyResults = new ArrayList<>();
        List<Task> currentTaskList = TestUtil.getInitialTasks().getInternalList();
        
        List<Task> results = new ArrayList<>();
        results.add(currentTaskList.get(9));
        results.add(currentTaskList.get(10));
        
        assertFindResult("find totallynonexistenttask", emptyResults); //no results
        assertFindResult("find 10 11", results); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 2");
        results.remove(1);
        assertFindResult("find 10 11", results);
    }

    @Test
    public void findCommand_emptyList(){
        List<Task> emptyResults = new ArrayList<>();
        commandBox.runCommand("clear");
        assertFindResult("find Jean", emptyResults); //no results
    }

    @Test
    public void findCommand_invalidCommand() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, List<Task> expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.size());
        assertResultMessage(expectedHits.size() + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
