package seedu.address.storage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.AddressBookGuiTest;
import seedu.address.testutil.TestActivity;

//@@author A0125680H
public class OpenCommandTest extends AddressBookGuiTest {
    private static final String TEST_OPEN_DIRECTORY = "src/test/data/testopen.xml";
    private static final String TEST_OPEN_DIRECTORY_NO_EXTENSION = "src/test/data/testopen";
    
    @Test
    public void open_withDefaultTestPath() {
        TestActivity[] newList = tdOther.getTypicalActivities();
        openFile(TEST_OPEN_DIRECTORY);
        assertOpenSuccessful(newList);
    }
    
    @Test
    public void open_withDefaultTestPath_noExtension() {
        TestActivity[] newList = tdOther.getTypicalActivities();
        openFile(TEST_OPEN_DIRECTORY_NO_EXTENSION);
        assertOpenSuccessful(newList);
    }
    
    private void openFile(String openDirectory) {
        String openCommand = "open " + openDirectory;
        
        commandBox.runCommand(openCommand);
    }
    
    private void assertOpenSuccessful(TestActivity... newList) {
        assertTrue(activityListPanel.isListMatching(newList));
    }
}
