package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author A0146107M
public class CommandHistoryTest extends TaskListGuiTest {
	@Test
	public void commandHistory_noHistory_nothingHappens() {
		//assert empty commandBox
		assertEquals(commandBox.getCommandInput(), "");

		//assert commandBox still empty
		commandBox.getPressUpKey();
		assertEquals(commandBox.getCommandInput(), "");

		//assert commandBox still empty
		commandBox.getPressDownKey();
		assertEquals(commandBox.getCommandInput(), "");
	}

	@Test
	public void commandHistory_succeed_commandDisplayed() {
		populateList();

		//assert previous command displayed
		commandBox.getPressUpKey();
		assertEquals(commandBox.getCommandInput(), "add Old1");

		//assert last command displayed
		commandBox.getPressUpKey();
		assertEquals(commandBox.getCommandInput(), "add Old2");

		//traverse back to newer command
		commandBox.getPressDownKey();
		assertEquals(commandBox.getCommandInput(), "add Old1");
	}

	@Test
	public void commandHistory_textHalfEntered_textSaved() {
		populateList();
		//type command halfway
		commandBox.enterCommand("Incomplete command...");

		//assert previous command displayed
		commandBox.getPressUpKey();
		assertEquals(commandBox.getCommandInput(), "add Old1");

		//assert typed command saved
		commandBox.getPressDownKey();
		assertEquals(commandBox.getCommandInput(), "Incomplete command...");
	}
	
	@Test
	public void commandHistory_endOfStack_nothingHappens() {
		populateList();
		
		//try traversing down, assert nothing happens
		assertEquals(commandBox.getCommandInput(), "");
		commandBox.getPressDownKey();
		assertEquals(commandBox.getCommandInput(), "");
		
		//traverse to oldest command
		commandBox.getPressUpKey();
		commandBox.getPressUpKey();
		assertEquals(commandBox.getCommandInput(), "add Old2");
		
		//try traversing up, assert nothing happens
		commandBox.getPressUpKey();
		assertEquals(commandBox.getCommandInput(), "add Old2");
	}

	private void populateList(){
		//populate list
		commandBox.runCommand("add Old2");
		commandBox.runCommand("add Old1");
	}
}
