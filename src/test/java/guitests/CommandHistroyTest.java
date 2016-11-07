package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

//@@author A0147619W
public class CommandHistroyTest extends TaskManagerGuiTest{

	@Test
	public void commandHistory_noHistory_nothingHappens() {
		//assert empty commandBox
		assertEquals("", commandBox.getCommandInput());

		//assert commandBox still empty
		commandBox.pressUpKey();
		assertEquals("", commandBox.getCommandInput());

		//assert commandBox still empty
		commandBox.pressDownKey();
		assertEquals("", commandBox.getCommandInput());
	}

	@Test
	public void commandHistory_succeed_commandDisplayed() {
		populateList();

		//assert previous command displayed
		commandBox.pressUpKey();
		assertEquals("add watch Dr. Strange", commandBox.getCommandInput());

		//assert last command displayed
		commandBox.pressUpKey();
		assertEquals("add meet Jim", commandBox.getCommandInput());

		//traverse back to newer command
		commandBox.pressDownKey();
		assertEquals("add watch Dr. Strange", commandBox.getCommandInput());
	}

	@Test
	public void commandHistory_textHalfEntered_textSaved() {
		populateList();
		//type command halfway
		commandBox.enterCommand("find Jim");

		//assert previous command displayed
		commandBox.pressUpKey();
		assertEquals("add watch Dr. Strange", commandBox.getCommandInput());

		//assert typed command saved
		commandBox.pressDownKey();
		assertEquals("find Jim", commandBox.getCommandInput());
	}
	
	@Test
	public void commandHistory_endOfStack_nothingHappens() {
		populateList();
		
		//try traversing down, assert nothing happens
		assertEquals("", commandBox.getCommandInput());
		commandBox.pressDownKey();
		assertEquals("", commandBox.getCommandInput());
		
		//traverse to oldest command
		commandBox.pressUpKey();
		commandBox.pressUpKey();
		assertEquals("add meet Jim", commandBox.getCommandInput());
		
		//try traversing up, assert nothing happens
		commandBox.pressUpKey();
		assertEquals("add meet Jim", commandBox.getCommandInput());
	}

	private void populateList(){
		//populate list
		commandBox.runCommand("add meet Jim");
		commandBox.runCommand("add watch Dr. Strange");
	}
}
