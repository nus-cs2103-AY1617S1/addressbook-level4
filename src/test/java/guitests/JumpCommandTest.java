package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JumpCommandTest extends TaskManagerGuiTest {
    
    @Test
    public void jumpCommand_deadline_deadlineIsFocused(){
        commandBox.runCommand("d");
        assertTrue(filterPanel.deadlineIsFocused());
    }
    
    @Test
    public void jumpCommand_startDate_startDateIsFocused(){
        commandBox.runCommand("s");
        assertTrue(filterPanel.startDateIsFocused());
    }
    
    @Test
    public void jumpCommand_endDate_endDateIsFocused(){
        commandBox.runCommand("e");
        assertTrue(filterPanel.endDateIsFocused());
    }
    
    @Test
    public void jumpCommand_recurring_recurringIsFocused(){
        commandBox.runCommand("r");
        assertTrue(filterPanel.recurringIsFocused());
    }
    
    @Test
    public void jumpCommand_tag_tagIsFocused(){
        commandBox.runCommand("t");
        assertTrue(filterPanel.tagIsFocused());
    }
    
    @Test
    public void jumpCommand_priority_priorityIsFocused(){
        commandBox.runCommand("p");
        assertTrue(filterPanel.priorityIsFocused());
    }

}
