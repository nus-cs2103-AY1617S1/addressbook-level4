package guitests;

import static org.junit.Assert.assertTrue;
import static jym.manager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import jym.manager.testutil.TestTask;
import jym.manager.testutil.TestTaskList;
import jym.manager.commons.core.Messages;
import jym.manager.logic.commands.UndoCommand;

//@@author a0153617e
public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        //without any last operation
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_WITHOUT_PREVIOUS_OPERATION);
    	
        //run add and edit operations
        commandBox.runCommand(td.doLaundry.getAddCommand());
        
        //undo an add operation
        TestTask[] currentList = td.getTypicalTasks();
//        currentList.addTasksToList(taskToAdd);
//        assertUndoCommandSuccess(currentList);
//              
        //undo an add operation after undoing an edit operation
 //       currentList = new TestTaskList(td.getTypicalTasks());
        assertUndoCommandSuccess(currentList);
   
        }
    
    private void assertUndoCommandSuccess(TestTask... expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
