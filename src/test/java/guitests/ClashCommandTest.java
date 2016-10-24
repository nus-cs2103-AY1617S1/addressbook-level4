package guitests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ClashCommandTest extends AddressBookGuiTest {
	
	@Test
	public void clash_nonEmptyList(){
		commandBox.runCommand("clash");
		assertTrue(personListPanel.isListMatching(td.fiona, td.george));
		assertListSize(2);
	}
	
}
