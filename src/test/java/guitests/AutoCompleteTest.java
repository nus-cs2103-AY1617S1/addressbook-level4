package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.input.KeyCode;

//@@author A0147619W
public class AutoCompleteTest extends TaskManagerGuiTest{

	@Test
	public void autocompleteWithSpace_success_commandDisplayed() {
		//assert empty commandBox
		assertEquals(commandBox.getCommandInput(), "");
		//assert command autocompleted with SPACE
		commandBox.enterCommand("a");
		commandBox.pressKey(KeyCode.SPACE);
		assertEquals(commandBox.getCommandInput(), "add ");
	}
	
	@Test
	public void autocompleteWithTab_success_commandDisplayed() {
		//assert empty commandBox
		assertEquals(commandBox.getCommandInput(), "");
		//assert command autocompleted with TAB
		commandBox.enterCommand("f");
		commandBox.pressKey(KeyCode.TAB);
		assertEquals(commandBox.getCommandInput(), "find");
	}
	
	@Test
	public void autocomplete_noMatches_nothingHappens() {
		//type in bad starting character
		commandBox.enterCommand("z");
		//assert command nothing happens
		commandBox.pressKey(KeyCode.TAB);
		assertEquals(commandBox.getCommandInput(), "z");
	}
	
	@Test
	public void autocomplete_multipleMatches_nothingHappens() {
		//type in bad starting character
		commandBox.enterCommand("d");
		//assert command nothing happens
		commandBox.pressKey(KeyCode.TAB);
		assertEquals(commandBox.getCommandInput(), "d");
	}
}
