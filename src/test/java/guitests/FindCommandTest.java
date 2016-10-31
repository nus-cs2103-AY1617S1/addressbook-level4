package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.Tdoo.testutil.TaskBuilder;
import seedu.Tdoo.testutil.TestTask;
import seedu.Tdoo.testutil.TestUtil;
import seedu.Tdoo.testutil.TypicalTestTask;
import seedu.Tdoo.commons.core.Messages;
import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.testutil.TestTask;


import static org.junit.Assert.assertTrue;

public class FindCommandTest extends ListGuiTest {


    @Test
    //@@author A0132157M reused
    public void find_nonEmptyList() throws IllegalValueException {
        
        TestTask[] currentList = td.getTypicaltasks();
        TestTask taskToAdd = new TaskBuilder().withName("assignment 1").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("1").withDone("false").build();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        TestTask taskToAdd1 = new TaskBuilder().withName("assignment 2").withStartDate("29-11-2016").withEndDate("30-11-2016").withPriority("1").withDone("false").build();
        assertAddSuccess(taskToAdd1, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd1);
        //assertFindResult("find to priority 999"); //no results
        assertFindResult("find todo assignment 1", taskToAdd1);

        //find after deleting one result
//        commandBox.runCommand("delete 1");
//        assertFindResult("find project 1",td.a2);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear todo");
        assertFindResult("find assignment 99"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findassignment");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        if (expectedHits.length == 0) {
            assertResultMessage("Invalid command format! \n" + 
                    "find: Finds all tasks whose names contain any of the specified keywords (case-sensitive) and displays them as a list with index numbers.\n" +
                    "Parameters: TASK_TYPE KEYWORD [MORE_KEYWORDS]...\n" +
                    "Example: find all homework urgent");
        }
        else {
            assertResultMessage(expectedHits.length + " tasks listed!");
        }
        //assertTrue(taskListPanel.isListMatching(expectedHits));
    }
    
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        addAllDummyTodoTasks(currentList);
        commandBox.runCommand(taskToAdd.getAddCommand());
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getName().name);
        assertMatching(taskToAdd, addedCard);
        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    private void addAllDummyTodoTasks(TestTask... currentList) {
        for(TestTask t:currentList ) {
            commandBox.runCommand(t.getAddCommand());
        }
    }
}
