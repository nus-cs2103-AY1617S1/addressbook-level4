package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.scene.input.KeyCode;

//@@author A0146107M
public class AutocompleteTest extends TaskListGuiTest {

	@Test
	public void autocompleteWithSpace_success_commandDisplayed() {
		//assert empty commandBox
		assertEquals(commandBox.getCommandInput(), "");
		//assert command autocompleted with SPACE
		commandBox.enterCommand("ad");
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
		commandBox.enterCommand("u");
		//assert command nothing happens
		commandBox.pressKey(KeyCode.TAB);
		assertEquals(commandBox.getCommandInput(), "u");
	}

}
