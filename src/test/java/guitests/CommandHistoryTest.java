package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author A0146107M
public class CommandHistoryTest extends TaskListGuiTest {
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
		assertEquals("add Old1", commandBox.getCommandInput());

		//assert last command displayed
		commandBox.pressUpKey();
		assertEquals("add Old2", commandBox.getCommandInput());

		//traverse back to newer command
		commandBox.pressDownKey();
		assertEquals("add Old1", commandBox.getCommandInput());
	}

	@Test
	public void commandHistory_textHalfEntered_textSaved() {
		populateList();
		//type command halfway
		commandBox.enterCommand("Incomplete command...");

		//assert previous command displayed
		commandBox.pressUpKey();
		assertEquals("add Old1", commandBox.getCommandInput());

		//assert typed command saved
		commandBox.pressDownKey();
		assertEquals("Incomplete command...", commandBox.getCommandInput());
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
		assertEquals("add Old2", commandBox.getCommandInput());
		
		//try traversing up, assert nothing happens
		commandBox.pressUpKey();
		assertEquals("add Old2", commandBox.getCommandInput());
	}

	private void populateList(){
		//populate list
		commandBox.runCommand("add Old2");
		commandBox.runCommand("add Old1");
	}
}
