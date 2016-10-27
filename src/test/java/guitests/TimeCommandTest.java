package guitests;

import org.junit.Test;

//@@author A0153837X
public class TimeCommandTest extends TaskListGuiTest{
	
	@Test
    public void time(){
		
		//Overdue Task
		commandBox.runCommand("time 1");
        assertResultMessage("Task is overdue!");
		
        //Task with no endDate
		commandBox.runCommand("time 7");
        assertResultMessage("Task has no date time specification!");
        
	}
}