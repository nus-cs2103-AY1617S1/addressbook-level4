package guitests;

import org.junit.Test;

import seedu.malitio.logic.commands.ListAllCommand;

public class ListAllCommandTest extends MalitioGuiTest {
	
	@Test
    public void completeFloatingtask() {
		
		//list all tasks from the beginning of time
		commandBox.runCommand("listall");
		assertResultMessage(String.format(ListAllCommand.LISTALL_MESSAGE_SUCCESS));
		

		//list all tasks from the beginning of time
		commandBox.runCommand("listall asdf");
		assertResultMessage(String.format(ListAllCommand.LISTALL_MESSAGE_SUCCESS));
		
    }

}
