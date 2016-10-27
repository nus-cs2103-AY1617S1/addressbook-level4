package guitests;

import static org.junit.Assert.*;

import org.junit.Test;


//@@author A0148145E

public class EditCommandTest extends TaskSchedulerGuiTest {

  @Test
  public void replace() {
      
      int indexToEdit = 1;
      String changedName =  "change name";
      //edit the name
      commandBox.runCommand("edit " + indexToEdit + " " + changedName);
      //confirm the editted card contains the right data
      assertEquals(taskListPanel.navigateToTask(indexToEdit - 1).getFullName(), changedName);

      String changedAddress =  "a changed location";
      //edit the address
      commandBox.runCommand("edit " + indexToEdit + " at " + changedAddress);
      //confirm the editted card contains the right data
      assertEquals(taskListPanel.navigateToTask(indexToEdit - 1).getLocation(), changedAddress);
      
      String changedStartDate =  "05-Oct-2016";
      String changedEndDate =  "15-Nov-2016";
      //edit the address
      commandBox.runCommand("edit " + indexToEdit + " from " + changedStartDate + " to " + changedEndDate);
      //confirm the editted card contains the right data
      assertTrue(taskListPanel.navigateToTask(indexToEdit - 1).getStartDate().contains(changedStartDate));
      assertTrue(taskListPanel.navigateToTask(indexToEdit - 1).getEndDate().contains(changedEndDate));

  }
}