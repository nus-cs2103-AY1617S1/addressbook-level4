package guitests;

import org.junit.Test;

//@@author A0139198N

public class ShowCommandTest extends TaskManagerGuiTest{
    @Test
    public void show() {

        //verify a non-empty list can be shown
        commandBox.runCommand("show");
        assertShowSuccess();
        
        commandBox.runCommand("show done");
        assertShowDoneSuccess();
        
        commandBox.runCommand("show all");
        assertShowAllSuccess();
        
        commandBox.runCommand("show overdue");
        assertShowOverdueSuccess();
        
        commandBox.runCommand("show floating");
        assertShowFloatingSuccess();

        //verify other commands can work after a show command
        commandBox.runCommand(td.hide.getAddCommand());
        commandBox.runCommand("delete 1");

        //verify show command works when the list is empty
        commandBox.runCommand("clear");
        commandBox.runCommand("show");
        assertShowSuccess();
    }

    private void assertShowSuccess() {
        commandBox.runCommand("show");
        assertResultMessage("Shown all undone tasks");
    }
    
    private void assertShowDoneSuccess() {
        commandBox.runCommand("show done");
        assertResultMessage("Shown all done tasks");
    }
    
    private void assertShowAllSuccess() {
        commandBox.runCommand("show all");
        assertResultMessage("Shown all tasks");
    }
    
    private void assertShowOverdueSuccess() {
        commandBox.runCommand("show overdue");
        assertResultMessage("Shown all overdue tasks");
    }
    
    private void assertShowFloatingSuccess() {
        commandBox.runCommand("show floating");
        assertResultMessage("Shown all floating tasks");
    }
}
