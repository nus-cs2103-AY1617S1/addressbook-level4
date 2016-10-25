package guitests;

import seedu.malitio.logic.commands.EditCommand;
import seedu.malitio.testutil.TestDeadline;
import seedu.malitio.testutil.TestFloatingTask;
import seedu.malitio.testutil.TestUtil;
import seedu.malitio.ui.DeadlineListPanel;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EditCommandTest extends MalitioGuiTest{
    
    @Test
   public void editFloatingtask() {
        
        //Edit name of floating task
        TestFloatingTask[] currentList = td.getTypicalFloatingTasks();
        TestFloatingTask toEdit = td.floatingTask1;
        TestFloatingTask edited = td.editedFloatingTask1;
       commandBox.runCommand("edit f1 how are you");
       assertEditSuccess(edited, 0, currentList);
       
       //Edit tags of floating task
       toEdit = td.floatingTask2;
       edited = td.editedFloatingTask2;
       commandBox.runCommand("edit f2 t/omg");
       assertEditSuccess(edited, 1, currentList);
       
       //Edit both name and tags of floatingtask
       toEdit = td.floatingTask3;
       edited = td.editedFloatingTask3;
       commandBox.runCommand("edit f3 Tell Nobody t/heello");
       assertEditSuccess(edited, 2, currentList);
       
       //Edit with an invalid index
       commandBox.runCommand("edit f200");
       assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
       
       //Edit a task to one which already exists
       commandBox.runCommand("edit f1 Tell Nobody t/heello");
       assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
   }
    
    @Test
    public void editDeadline() {
        
        //Edit name (only) of deadline
        TestDeadline[] currentList = td.getTypicalDeadlines();
        TestDeadline toEdit = td.deadline1;
        TestDeadline edited = td.editedDeadline1;
        commandBox.runCommand("edit d1 Cut more hair ");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo"); //revert back to original state
        
        //Edit due date (only) of dateline
        toEdit = td.deadline2;
        edited = td.editedDeadline2;
        commandBox.runCommand("edit d2 by 22 dec 12am");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");

        //Edit tag (only) of deadline
        toEdit = td.deadline3;
        edited = td.editedDeadline3;
        commandBox.runCommand("edit d3 t/Pineapple t/Pen");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");
        
        //Edit name, duedate and tags of deadline
        toEdit = td.deadline4;
        edited = td.editedDeadline4;
        commandBox.runCommand("edit d4 I want to sleep by 25 oct 11pm t/damntired");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");

        
        //Edit a deadline to one which already exists
        commandBox.runCommand("edit d1 Practice singing by 12-25 12am t/Christmas t/Carols");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_DEADLINE);
    }
        
   
   /**
    * @param edited the edited floating task
    * @param index index of task which is edited
    * @param currentList
    */
   private void assertEditSuccess(TestFloatingTask edited, int index, TestFloatingTask... currentList) {
       currentList = TestUtil.replaceTaskFromList(currentList, edited, index);
       assertTrue(floatingTaskListPanel.isListMatching(currentList));      
   }

    /**
     * @param edited
     * @param toEdit
     * @param currentList
     * @return updated TestDeadline array. 
     */
    private void assertEditSuccess(TestDeadline edited, TestDeadline toEdit, TestDeadline... currentList) {
       currentList = TestUtil.removeTasksFromList(currentList, toEdit);
       currentList = TestUtil.addTasksToList(currentList, edited);
       assertTrue(deadlineListPanel.isListMatching(currentList));
    }
   
}